package com.lwl.list;

public class ArrayList<E> extends AbstractList<E> {

    private E[] elements;

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        this.elements = (E[]) (new Object[capacity]);
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean contains(E element) {
        boolean flag = false;

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public E get(int index) {
        check(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        check(index);
        E res = elements[index];
        elements[index] = element;
        return res;
    }

    @Override
    public void add(int index, E element) {

        ensureCapacity();
        addCheck(index);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[size++] = element;
    }

    @Override
    public E remove(int index) {
        check(index);
        E res = elements[index];

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return res;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < size; i++) {
            sb.append(elements[i].toString() + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
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
}
