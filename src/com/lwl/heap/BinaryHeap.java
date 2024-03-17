package com.lwl.heap;

import java.util.Comparator;
import java.util.Objects;

/**
 * 二叉堆的实现
 *
 * @param <E>
 */
public class BinaryHeap<E> implements Heap<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;

    private int size;

    private Comparator<E> comparator;

    public BinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        if (Objects.isNull(element)) {
            return;
        }
        ensureCapacity();
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E replace(E element) {
        return null;
    }


    private int compare(E o1, E o2) {
        return comparator != null ? comparator.compare(o1, o2) : ((Comparable) o1).compareTo(o2);
    }

    private void emptyCheck() {
        if (size <= 0) {
            throw new ArrayIndexOutOfBoundsException("heap is empty");
        }
    }

    private void ensureCapacity() {
        if (elements.length > size) {
            return;
        } else {
            int oldCapacity = elements.length;

            int newCapacity = elements.length + (elements.length >> 1);

            E[] newElements = (E[]) new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;

            System.out.println("list进行了扩容,原capacity=" + oldCapacity + ",新的capacity=" + newCapacity);
        }
    }

    /**
     * 二叉堆上滤
     * @param index
     */
    private void siftUp(int index) {
        E e = elements[index];
        while (index > 0) {
            int parent = (index - 1) >> 2;
            E p = elements[parent];
            if (compare(e, p) <= 0) {
                break;
            }
            elements[index] = p;
            index = parent;
        }
        elements[index] = e;
    }


}
