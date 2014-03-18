package com.cloudhashing.core;

import java.util.HashMap;

public class DefaultDict<K, V> extends HashMap<K, V> {

   private static final long serialVersionUID = 1208883672910443294L;
   
   private Class<V> clazz;

   public DefaultDict(Class<V> clazz) {
      this.clazz = clazz;
   }

   public V get(Object key) {
      V returnValue = super.get(key);
      if (returnValue == null) {
         try {
            returnValue = clazz.newInstance();
         }
         catch (Exception e) {
            throw new RuntimeException(e);
         }
         this.put((K) key, returnValue);
      }
      return returnValue;
   }
}
