package com.lwl.list;

public interface Queue<E> {
    int size();

    boolean isEmpty();

    void enQueue(E element);

    E deQueue();

    E front();
    
}
