package com.cloudhashing.core.factory;

public class DefaultStringZeroFactory implements DefaultDictFactory {

   @Override
   public Object lambda() {
      return String.valueOf(0L);
   }

}
