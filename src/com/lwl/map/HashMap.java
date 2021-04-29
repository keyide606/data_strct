package com.lwl.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author liwenlong
 * HashMap是基于AVL树和数组实现的
 * @date 2021-04-20 17:46
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;
    private Node<K, V>[] table;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) {
            return;
        }
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        checkKey(key);
        // 扩容
        resize();
        int index = index(key);
        Node<K, V> root = table[index];
        // 添加的是根节点
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            return null;
        }
        // 添加的不是跟节点
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int compare = 0;
        int oneHashCode = key == null ? 0 : key.hashCode();
        while (node != null) {
            int cmp = compareNode(key, node.getKey(), oneHashCode, node.getHashcode());
            compare = cmp;
            parent = node;
            if (cmp > 0) {
                node = node.getRight();
            } else if (cmp < 0) {
                node = node.getLeft();
            } else {
                // 还有一些问题,比如hashcode一样但是equals不一样
                V v = node.getValue();
                node.setKey(key);
                node.setValue(value);
                return v;
            }
        }
        if (compare > 0) {
            Node<K, V> rightNode = createNode(key, value, parent);
            parent.right = rightNode;
            afterAdd(rightNode);
            size++;
        } else {
            Node<K, V> leftNode = createNode(key, value, parent);
            parent.left = leftNode;
            afterAdd(leftNode);
            size++;
        }
        return null;
    }

    private void resize() {
        if (size <= ((int) (table.length * DEFAULT_LOAD_FACTOR))) {
            return;
        }
        // 进行扩容
        Node[] oldTable = table;
        table = new Node[table.length << 1];
        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) {
                continue;
            }
            Node root = oldTable[i];
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 相当于在新的数组中添加元素
                Node temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
                moveNode(temp);
            }
        }
        System.out.println("原来长度:" + oldTable.length + ",当前长度:" + table.length);
        // 将原来的数组释放
        for (int i = 0; i < oldTable.length; i++) {
            oldTable[i] = null;
        }
    }

    private void moveNode(Node<K, V> node) {
        int index = index(node);
        node.parent = null;
        node.left = null;
        node.right = null;

        Node<K, V> root = table[index];
        // 添加的是根节点
        if (root == null) {
            root = node;
            table[index] = root;
            return;
        }
        // 添加的不是跟节点
        Node<K, V> temp = root;
        Node<K, V> parent = root;
        int compare = 0;
        int oneHashCode = node.hashcode;
        while (temp != null) {
            int cmp = compareNode(node.getKey(), temp.getKey(), oneHashCode, temp.getHashcode());
            compare = cmp;
            parent = temp;
            if (cmp > 0) {
                temp = temp.getRight();
            } else if (cmp < 0) {
                temp = temp.getLeft();
            }
        }
        if (compare > 0) {
            Node<K, V> rightNode = node;
            node.setParent(parent);
            parent.right = rightNode;
            afterAdd(rightNode);
        } else {
            Node<K, V> leftNode = node;
            node.setParent(parent);
            parent.left = leftNode;
            afterAdd(leftNode);
        }
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.getValue();
    }

    @Override
    public V remove(K key) {
        Node<K, V> node = node(key);
        remove(node);
        return node.getValue();
    }

    @Override
    public boolean containsKey(K key) {
        Node<K, V> node = node(key);
        if (node == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            // 采用中序遍历的方式遍历AVL树
            Node<K, V> node = table[i];
            queue.offer(node);
            while (!queue.isEmpty()) {
                Node<K, V> temp = queue.poll();
                if (Objects.equals(temp.getValue(), value)) {
                    return true;
                }
                if (temp.getLeft() != null) {
                    queue.offer(temp.getLeft());
                }
                if (temp.getRight() != null) {
                    queue.offer(temp.getRight());
                }
            }
        }
        return false;
    }

    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hashcode = key.hashCode();

        hashcode = hashcode ^ hashcode >> 16;

        return hashcode & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return index(node.getKey());
    }

    private Node<K, V> node(K key) {
        int index = index(key);
        Node<K, V> node = table[index];
        int hashcode = key == null ? 0 : key.hashCode();
        while (node != null) {
            int cmp = compareNode(key, node.getKey(), hashcode, node.hashcode);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 检查key是否为空
     *
     * @param key
     */
    private void checkKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private int compareNode(K one, K two, int oneHashCode, int twoHashCode) {
        int result = oneHashCode - twoHashCode;
        // hashcode不相等直接返回
        if (result != 0) {
            return result;
        }
        // hashcode相等,判断equals是否相等,相等的话直接返回
        if (Objects.equals(one, two)) {
            return 0;
        }

        // hashcode相等,equals不相同,比较类名看是否是同一个类
        if (one != null && two != null) {
            String oneClass = one.getClass().getName();
            String twoClass = two.getClass().getName();
            result = oneClass.compareTo(twoClass);
            // 不是同一个类直接返回结果
            if (result != 0) {
                return result;
            }
            // 同一个类如果有比较性,直接返回他们比较的结果
            if (one instanceof Comparable) {
                return ((Comparable) one).compareTo(two);
            }
        }
        /* 来到这的有三种情况:
            1. 两个对象是同一类型的但是不具有比较性
            2. one == null  two != null
            3. one != null  two == null
            这三种情况直接用内存地址比较
         */
        return System.identityHashCode(one) - System.identityHashCode(two);

    }

    private Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node(key, value, parent);
    }

    private void afterAdd(Node<K, V> node) {
        while ((node = node.getParent()) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                reBalance(node);
                break;
            }
        }
    }

    private void updateHeight(Node<K, V> node) {
        node.updateHeight();
    }

    private boolean isBalanced(Node<K, V> node) {
        int balanceFactor = node.getBalanceFactor();
        return Math.abs(balanceFactor) <= 1;
    }

    private void reBalance(Node<K, V> grand) {
        Node<K, V> parent = grand.getTallerChildren();
        Node<K, V> children = parent.getTallerChildren();
        if (parent.isLeftChild() && children.isLeftChild()) {
            turnRight(grand, parent);
        } else if (parent.isLeftChild() && children.isRightChild()) {
            turnLeftAndTurnRight(grand, parent, children);
        } else if (parent.isRightChild() && children.isRightChild()) {
            turnLeft(grand, parent);
        } else {
            turnRightAndTurnLeft(grand, parent, children);
        }
    }

    private void remove(Node<K, V> node) {
        if (node.hasTwoChildren()) {
            Node<K, V> precursorNode = getPrecursorNode(node);
            // 先将找打的前驱节点中的值element值赋给node节点,然后将node节点删除
            node.key = precursorNode.key;
            node.value = precursorNode.value;
            node.hashcode = precursorNode.hashcode;
            node = precursorNode;
        }
        // 准备将前驱节点删除,也就是将node节点删除,获取可以替代它的节点
        Node<K, V> replacementNode = node.getLeft() != null ? node.getLeft() : node.getRight();

        if (replacementNode != null) {
            replacementNode.setParent(node.getParent());
            if (node.getParent().getRight() == node) {
                node.getParent().setRight(replacementNode);
            } else {
                node.getParent().setLeft(replacementNode);
            }
            afterRemove(node, replacementNode);
        } else if (node.getParent() == null) {
            table[index(node.getKey())] = null;
            afterRemove(node, null);
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(null);
            afterRemove(node, null);
        } else if (node.getParent().getRight() == node) {
            node.getParent().setRight(null);
            afterRemove(node, null);
        }
        size--;

    }


    protected void afterRemove(Node<K, V> node, Node<K, V> replacement) {
        while ((node = node.getParent()) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                reBalance(node);
            }
        }
    }

    // 获取中序遍历中一个节点的前驱节点
    public Node<K, V> getPrecursorNode(Node<K, V> node) {
        if (node.getLeft() != null) {
            Node<K, V> temp = node.getLeft();
            while (temp.getRight() != null) {
                temp = temp.getRight();
            }
            return temp;
        }
        if (node.getParent() != null) {
            while (node.getParent() != null && node.getParent().right != node) {
                node = node.getParent();
            }
        }
        return node.parent;
    }

    /**
     * RR情况,需要将grand节点左转
     *
     * @param grand
     * @param parent
     */
    private void turnLeft(Node<K, V> grand, Node<K, V> parent) {
        // parent的父节点先变
        parent.setParent(grand.getParent());
        // 将grand节点对应的父节点它的一个孩子节点指向parent
        if (grand.isLeftChild()) {
            grand.getParent().setLeft(parent);
        } else if (grand.isRightChild()) {
            grand.getParent().setRight(parent);
        } else {
            table[index(grand.getKey())] = parent;
        }
        // parent的左孩子节点变为grand的右孩子
        grand.setRight(parent.getLeft());
        if (parent.getLeft() != null) {
            parent.getLeft().setParent(grand);
        }
        parent.setLeft(grand);
        grand.setParent(parent);

        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * LL情况,需要将grand节点右转
     *
     * @param grand
     * @param parent
     */
    private void turnRight(Node<K, V> grand, Node<K, V> parent) {

        parent.setParent(grand.getParent());
        if (grand.isRightChild()) {
            grand.getParent().setRight(parent);
        } else if (grand.isLeftChild()) {
            grand.getParent().setLeft(parent);
        } else {
            table[index(grand.getKey())] = parent;
        }
        grand.setLeft(parent.getRight());
        if (parent.getRight() != null) {
            parent.getRight().setParent(grand);
        }
        parent.setRight(grand);
        grand.setParent(parent);

        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * LR情况先将parent左转,然后将grand右转
     */
    private void turnLeftAndTurnRight(Node<K, V> grand, Node<K, V> parent, Node<K, V> children) {
        turnLeft(parent, children);

        turnRight(grand, children);
    }

    /**
     * LR情况先将parent左转,然后将grand右转
     */
    private void turnRightAndTurnLeft(Node<K, V> grand, Node<K, V> parent, Node<K, V> children) {
        turnRight(parent, children);

        turnLeft(grand, children);
    }

    /**
     * AVL树的节点
     *
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        int height = 1;
        int hashcode;
        K key;
        V value;
        Node<K, V> left;

        Node<K, V> right;

        Node<K, V> parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hashcode = key == null ? 0 : key.hashCode();
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public void setLeft(Node<K, V> left) {
            this.left = left;
        }

        public Node<K, V> getRight() {
            return right;
        }

        public void setRight(Node<K, V> right) {
            this.right = right;
        }

        public Node<K, V> getParent() {
            return parent;
        }

        public void setParent(Node<K, V> parent) {
            this.parent = parent;
        }

        public int getHashcode() {
            return hashcode;
        }

        public void setHashcode(int hashcode) {
            this.hashcode = hashcode;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> getSibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        public int getBalanceFactor() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<K, V> getTallerChildren() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            if (leftHeight > rightHeight) {
                return left;
            } else if (leftHeight < rightHeight) {
                return right;
            } else {
                return isLeftChild() ? left : right;
            }
        }
    }
}
