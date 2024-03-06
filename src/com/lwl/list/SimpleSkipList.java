package com.lwl.list;


import java.util.Comparator;


/**
 * @author liwenlong
 * 此跳表实现和JavaConcurrentSkipListMap不相同
 * @date 2021-05-14 18:23
 */
public class SimpleSkipList<K, V> {
    // 跳表最高层32层
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;
    // 首节点,虚拟头结点
    private Node head;
    private int size;
    // 有效层数
    private int level;
    private Comparator<K> comparator;

    public SimpleSkipList(Comparator<K> comparator) {
        head = new Node();
        head.nexts = new Node[MAX_LEVEL];
        this.comparator = comparator;
    }

    public SimpleSkipList() {
        head = new Node();
        head.nexts = new Node[MAX_LEVEL];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public V put(K key, V value) {
        keyCheck(key);
        Node<K, V> node = head;
        Node<K, V>[] pres = new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                V oldValue = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldValue;
            }
            pres[i] = node;
        }
        // 来到这里node就是前驱节点
        int nodeLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, nodeLevel);
        for (int i = 0; i < nodeLevel; i++) {
            if (i >= level) {
                head.nexts[i] = newNode;
            } else {
                Node<K, V> pre = pres[i];
                newNode.nexts[i] = pre.nexts[i];
                pre.nexts[i] = newNode;
            }
        }
        size++;
        level = Math.max(level, nodeLevel);
        return null;
    }

    public V get(K key) {
        Node<K, V> node = find(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node<K, V> find(K key) {
        keyCheck(key);
        Node<K, V> node = head;
        int cmp;
        for (int i = level - 1; i >= 0; i--) {
            while (node.nexts[i] != null) {
                cmp = compare(key, node.nexts[i].key);
                if (cmp == 0) {
                    return node.nexts[i];
                } else if (cmp > 0) {
                    node = node.nexts[i];
                } else {
                    break;
                }
            }
        }
        return null;
    }

    public V remove(K key) {
        keyCheck(key);
        Node<K, V> node = head;
        Node<K, V>[] pres = new Node[level];
        boolean exists = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            pres[i] = node;
            if (cmp == 0) {
                exists = true;
            }
        }
        Node<K, V> removeNode = pres[0].nexts[0];
        if (exists) {
            for (int j = 0; j < removeNode.nexts.length; j++) {
                pres[j].nexts[j] = removeNode.nexts[j];
            }
            // 更新跳表的有效层数
            for (int k = 0; k < head.nexts.length; k++) {
                if (head.nexts[k] == null) {
                    level--;
                }
            }
            size--;
            return removeNode.value;
        }
        return null;

    }

    private void keyCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为null");
        }
    }

    private int compare(K one, K two) {
        if (comparator != null) {
            return comparator.compare(one, two);
        } else {
            return ((Comparable<K>) one).compareTo(two);
        }
    }

    // 新加入的节点有多少层，参考redis的写法
    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private static class Node<K, V> {
        Node<K, V>[] nexts;
        K key;
        V value;

        public Node() {
        }

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.nexts = new Node[level];
        }
    }

}
