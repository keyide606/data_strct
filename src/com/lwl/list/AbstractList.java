package com.lwl.list;

public abstract class AbstractList<E> implements List<E> {
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return !(size > 0);
    }

    @Override
    public void add(E element) {
        this.add(size, element);
    }

    protected void check(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界了,index=" + index + ",size=" + size);
        }
    }

    protected void addCheck(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界了,index=" + index + ",size=" + size);
        }
    }
}
