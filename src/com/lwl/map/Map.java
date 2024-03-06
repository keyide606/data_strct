package com.lwl.map;

/** 定义map接口
 * @author liwenlong
 * @date 2021-04-28 14:01
 */
public interface Map<K, V> {
    int size();

    boolean isEmpty();

    void clear();

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);
}
