package com.lwl.map;

/**
 * @author liwenlong
 * @date 2021-04-20 17:47
 */
public class Main {
    public static void main(String[] args) {
        Map<Object, Integer> map = new HashMap<>();
        testSize(map);
        testGet(map);
        testContainsKey(map);
        testContainsValue(map);

        testHashcode(map);
        testClear(map);
        testRemove(map);
    }


    static void testSize(Map map) {
        System.out.println(map.size());
        map.put("jack", 1);
        System.out.println(map.size());
    }

    static void testEmpty(Map map) {
        System.out.println(map.isEmpty());
    }

    static void testGet(Map<Object, Integer> map) {
        map.put("rose", 2);
        System.out.println(map.get("rose"));
        System.out.println("测试数量");
        System.out.println(map.size());
    }

    static void testClear(Map<Object, Integer> map) {
        map.clear();
        System.out.println(map.size());
        System.out.println(map.isEmpty());
    }

    static void testContainsKey(Map<Object, Integer> map) {
        map.put("jerry", 3);
        System.out.println("测试containsKey");
        System.out.println(map.containsKey("jerry"));
        System.out.println(map.containsKey("lol"));
    }

    static void testContainsValue(Map<Object, Integer> map) {
        System.out.println(map.containsValue(3));
        map.put("ferry", 4);
        System.out.println("测试containsValue");
        System.out.println(map.containsValue(5));
        System.out.println(map.containsValue(4));
    }

    static void testHashcode(Map<Object, Integer> map) {
        Person one = new Person("bob", 24);
        Person two = new Person("bob", 24);
        map.put(one, 7);
        map.put(two, 8);
        System.out.println(map.get(one));
        System.out.println(map.get(two));
        System.out.println(map.size());
    }

    static void testRemove(Map<Object, Integer> map) {
        map.put("rng", 12);
        map.put("eng", 13);
        map.put("omg", 14);
        map.put("ig", 15);
        map.put("fpx", 16);
        map.remove("omg");
        System.out.println(map.get("omg"));
        System.out.println(map.size());
    }
}
