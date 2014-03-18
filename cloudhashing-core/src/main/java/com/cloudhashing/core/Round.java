package com.cloudhashing.core;

import java.sql.Timestamp;

import com.cloudhashing.core.collections.DefaultDict;
import com.cloudhashing.core.factory.DefaultStringZeroFactory;

public class Round {
   //second COMMIT
   private Long id;
   private DefaultDict<String, Long> shares = new DefaultDict<String, Long>(new DefaultStringZeroFactory());
   private Long sharesStop;
   private Timestamp completed;

   public Round(Long id, Long sharesStop, DefaultDict<String, Long> shares) {
      this.id = id;
      this.sharesStop = sharesStop;
      this.shares = shares;
   }

   public Round(Long id, Long sharesStop) {
      this.id = id;
      this.sharesStop = sharesStop;
   }


   public Round(Long sharesStop, Timestamp completed) {
      this.sharesStop = sharesStop;
      this.completed = completed;
   }
   
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getSharesStop() {
      return sharesStop;
   }

   public void setSharesStop(Long sharesStop) {
      this.sharesStop = sharesStop;
   }

   public DefaultDict<String, Long> getShares() {
      return shares;
   }

   public void setShares(DefaultDict<String, Long> shares) {
      this.shares = shares;
   }

   public Timestamp getCompleted() {
      return completed;
   }

   public void setCompleted(Timestamp completed) {
      this.completed = completed;
   }

}
