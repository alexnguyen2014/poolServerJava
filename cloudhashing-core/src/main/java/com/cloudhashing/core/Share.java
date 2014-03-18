package com.cloudhashing.core;

public class Share {

   private String minerId;
   private Long shares;

   public Share(String minerId, Long shares) {
      super();
      this.minerId = minerId;
      this.shares = shares;
   }

   public String getMinerId() {
      return minerId;
   }

   public void setMinerId(String minerId) {
      this.minerId = minerId;
   }

   public Long getShares() {
      return shares;
   }

   public void setShares(Long shares) {
      this.shares = shares;
   }

}
