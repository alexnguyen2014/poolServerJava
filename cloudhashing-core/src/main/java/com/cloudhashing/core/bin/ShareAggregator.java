package com.cloudhashing.core.bin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cloudhashing.core.Round;
import com.cloudhashing.core.Share;
import com.cloudhashing.core.collections.DefaultDict;
import com.cloudhashing.core.collections.DefaultDictVisitor;
import com.cloudhashing.core.factory.DefaultStringZeroFactory;

@Service
public class ShareAggregator {

  private static final Log LOG = LogFactory.getLog(ShareAggregator.class);

  @Autowired
  private DBService dbService;

  public ShareAggregator() {
    //    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
  }

  public Round nextRound() {
    JdbcTemplate jdbcTemplate = dbService.getJdbcTemplate();
    jdbcTemplate.setMaxRows(1);
    List<Round> rounds = jdbcTemplate.query("SELECT id, shares FROM rounds WHERE completed is NULL",
      new RowMapper<Round>() {
        public Round mapRow(ResultSet rs, int rowNum) throws SQLException {
          return new Round(rs.getLong("id"), rs.getLong("shares"));
        }
      });

    if (rounds.size() > 0) {
      return nextOneRound(rounds.get(0));
    }
    return nextRounds();
  }

  private Round nextOneRound(Round round) {
    JdbcTemplate jdbcTemplate = dbService.getJdbcTemplate();
    final List<Share> shares = jdbcTemplate.query(
      "SELECT miner_id, shares FROM shares WHERE round_id=" + round.getId(), new RowMapper<Share>() {
        public Share mapRow(ResultSet rs, int rowNum) throws SQLException {
          return new Share(rs.getString("miner_id"), rs.getLong("shares"));
        }
      });
    DefaultDict<String, Long> rShares = new DefaultDict<String, Long>(new DefaultStringZeroFactory(),
      new DefaultDictVisitor<String, Long>() {
        public void visit(DefaultDict<String, Long> dict) {
          for (Share s : shares) {
            dict.put(s.getMinerId(), s.getShares());
          }
        }
      });
    return new Round(round.getId(), round.getSharesStop(), rShares);
  }

  private Round nextRounds() {
    JdbcTemplate jdbcTemplate = dbService.getJdbcTemplate();
    jdbcTemplate.setMaxRows(2);
    List<Round> rounds = jdbcTemplate.query("SELECT shares, completed FROM rounds ORDER BY id DESC LIMIT 2",
      new RowMapper<Round>() {
        public Round mapRow(ResultSet rs, int rowNum) throws SQLException {
          return new Round(rs.getLong("shares"), rs.getTimestamp("completed"));
        }
      });

    Long sharesStop = 0L;
    if (rounds.size() == 2) {
      Round lastRound = rounds.get(0);
      Round previousRound = rounds.get(1);
      LOG.info("round stats last_round: " + lastRound.toString() + " previous_round: " + previousRound.toString());

      Long sharePerSeconds = lastRound.getSharesStop()
        / ((lastRound.getCompleted().getTime() - previousRound.getCompleted().getTime()) / 1000);
      LOG.info("shares_per_second " + sharePerSeconds);
      sharesStop = sharePerSeconds * 60 * 60;// # 60 minutes
    }
    else {
      sharesStop = 1000000L;
    }

    sharesStop = Math.max(1000000L, sharesStop);

    jdbcTemplate.setMaxRows(1);
    Long roundId = jdbcTemplate.queryForObject("INSERT INTO rounds(value, shares) VALUES(0," + sharesStop
      + ") RETURNING id", Long.class);

    return new Round(roundId, sharesStop, new DefaultDict<String, Long>(new DefaultStringZeroFactory()));
  }
}
