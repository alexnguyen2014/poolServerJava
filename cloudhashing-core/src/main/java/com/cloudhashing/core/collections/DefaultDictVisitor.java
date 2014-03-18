package com.cloudhashing.core.collections;

public interface DefaultDictVisitor<K, V> {
   
   void visit(DefaultDict<K, V> dict);
}
