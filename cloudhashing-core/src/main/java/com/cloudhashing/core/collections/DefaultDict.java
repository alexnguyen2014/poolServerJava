package com.cloudhashing.core.collections;

import java.util.HashMap;

import com.cloudhashing.core.factory.DefaultDictFactory;

public class DefaultDict<K, V> extends HashMap<K, V> {

   private static final long serialVersionUID = 2472322655606193253L;

   private DefaultDictFactory defaultFactory;

   public DefaultDict(DefaultDictFactory defaultFactory) {
      this.defaultFactory = defaultFactory;
   }
   

   public DefaultDict(DefaultDictFactory defaultFactory, DefaultDictVisitor<K, V> visitor) {
      this.defaultFactory = defaultFactory;
      visitor.visit(this);
   }

   @Override
   @SuppressWarnings("unchecked")
   public V get(Object key) {
      V value = super.get(key);
      if (value == null) {
         value = (V) defaultFactory.lambda();
      }
      return value;
   }
}
