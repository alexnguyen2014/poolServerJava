package com.cloudhashing.core.bin;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DBService {

  @Autowired
  private DataSource dataSource;

//  @Async
  public JdbcTemplate getJdbcTemplate() {
    return new JdbcTemplate(dataSource);
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  //
  //  public void execute(String sql, DBCallback dbCallback) {
  //    try (Connection con = dataSource.getConnection(); Statement st = con.createStatement()) {
  //      try (ResultSet res = st.executeQuery("…")) {
  //        dbCallback.callback(res);
  //      }
  //      catch (SQLException sqlx) {
  //        LOG.error(sqlx);
  //      }
  //    }
  //    catch (SQLException ex) {
  //      LOG.error(ex);
  //    }
  //    finally {
  //      
  //    }
  //  }
}
