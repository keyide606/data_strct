package com.lwl.heap;

public interface Heap<E> {

    /**
     * 返回堆的大小
     * @return
     */
    int size();

    /**
     * 判断堆是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 清除堆中元素
     */
    void clear();

    /**
     * 堆中增加元素
     * @param element
     */
    void add(E element);

    /**
     * 获取堆顶元素
     * @return
     */
    E get();

    /**
     * 删除堆顶元素
     * @return
     */
    E remove();

    /**
     * 取代堆顶元素
     * @param element
     * @return
     */
    E replace(E element);

}
