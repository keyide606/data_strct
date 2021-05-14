package com.lwl.list;

/**
 * @author liwenlong  单链表实现linkedlist
 * @date 2021-05-14 17:33
 */
public class SingleLinkedList<E> extends AbstractList<E> {

    private Node<E> head;

    @Override
    public boolean contains(E element) {

        return indexOf(element) != -1;
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
        node.element = element;
        E oldValue = node.element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        addCheck(index);
        // 在链表首部增加元素
        if (index == 0) {
            Node<E> node = new Node<>(element, head);
            head = node;
        } else {
            Node<E> prev = getNode(index - 1);
            Node<E> node = new Node<>(element, prev.next);
            prev.next = node;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        check(index);
        E element;
        if (index == 0) {
            // 删除的是头元素,直接将头部指向下一元素
            element = head.element;
            head = head.next;
        } else {
            Node prev = getNode(index - 1);
            Node<E> node = getNode(index);
            prev.next = node.next;
            element = node.element;
        }
        size--;
        return element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = head;
        int j = 0;
        while (node != null) {
            if (element.equals(node.element)) {
                return j;
            }
            ++j;
            node = node.next;
        }
        return -1;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }


    private Node<E> getNode(int index) {
        Node node = head;
        // 调用了check方法,索引一定是合理的
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "()";
        }
        StringBuilder string = new StringBuilder();
        string.append("(");
        Node<E> node = head;
        while (node != null) {
            E value = node.element;
            string.append(value.toString() + ", ");
            node = node.next;
        }
        string.setCharAt(string.length() - 1, ')');
        return string.toString();
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
