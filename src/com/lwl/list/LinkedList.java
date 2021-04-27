package com.lwl.list;


public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    @Override
    public boolean contains(E element) {

        return !(indexOf(element) == -1);
    }

    @Override
    public E get(int index) {
        check(index);
        return getNode(index).element;
    }

    @Override
    public E set(int index, E element) {
        check(index);
        Node<E> node = getNode(index);
        E oldElement = node.element;
        node.element = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        addCheck(index);
        Node<E> newNode = new Node<>(element, null, null);
        // 考虑第一个元素
        if (index == 0 && first == null) {
            first = newNode;
            last = newNode;
            // 考虑有元素时插入第一个元素
        } else if (index == 0 && first != null) {
            Node<E> node = getNode(index);
            newNode.next = node;
            node.prev = newNode;
            first = newNode;
            //  考虑有元素，插入最后一个元素
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }else {
            Node<E> node = getNode(index);
            // 先让新加节点的上一个节点和下一个节点确定
            newNode.next = node;
            newNode.prev = node.prev;
            // 李文龙你真是个小天才
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        check(index);
        Node<E> node = getNode(index);
        if (index == 0 && index == size - 1) {
            first = null;
            last = null;
        } else if (index == 0) {
            first = node.next;
            node.next.prev = null;
        } else if (index == size - 1) {
            last = node.prev;
            node.prev.next = null;
        }
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        int i = 0;
        while (node != null) {
            if (element.equals(node.element)) {
                return i;
            }
            i++;
            node = node.next;
        }
        return -1;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    // 获取节点
    private Node<E> getNode(int index) {
        check(index);
        Node<E> node;
        if (index < (size >> 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder string = new StringBuilder();
        string.append("[");

        Node<E> node = first;
        while (node != null) {
            E element = node.element;
            string.append(element.toString() + ",");
            node = node.next;
        }
        string.setCharAt(string.length() - 1, ']');
        return string.toString();
    }

    // 静态内部类
    static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(E element, Node prev, Node next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }


}
