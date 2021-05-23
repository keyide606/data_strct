package com.lwl.list;


public class Test {
    public static void main(String[] args) {
        // testArrayList();
        // testLinkedList();
        // testSingleLinkedList();
        testSimpleSkipList();
    }


    private static void testArrayList() {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        list.add(125);
        System.out.println(list.get(1));
        System.out.println(list.set(20, 200));
        System.out.println(list.indexOf(15));
        System.out.println(list.toString());
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.contains(250));

        System.out.println(list.size());
        System.out.println(list.remove(3));
        System.out.println(list.size());
        System.out.println(list.toString());
        list.clear();
        System.out.println(list.toString());
    }


    private static void testLinkedList() {
        LinkedList<Person> personList = new LinkedList<>();

        System.out.println(personList.size());

        System.out.println(personList.isEmpty());

        personList.add(new Person("zs", 20));
        personList.add(new Person("ls", 21));
        personList.add(new Person("ww", 22));
        System.out.println(personList.size());

        System.out.println(personList.isEmpty());

        System.out.println(personList.toString());
        System.out.println("set方法");
        personList.set(1, new Person("ls", 29));
        System.out.println(personList.toString());
        personList.add(0, new Person("long", 18));
        System.out.println(personList.toString());
        System.out.println(personList.contains(new Person("zs", 20)));
        System.out.println(personList.contains(new Person("zs", 30)));
    }


    private static void testSingleLinkedList() {
        List<Person> list = new SingleLinkedList<>();

        System.out.println(list.isEmpty());
        System.out.println(list.size());

        list.add(new Person("ww", 22));
        list.add(new Person("ls", 21));
        list.add(new Person("zs", 20));
        System.out.println(list.isEmpty());
        System.out.println(list.size());


        System.out.println("set方法");
        System.out.println(list.toString());
        list.set(1, new Person("zl", 29));
        System.out.println(list.toString());
        list.add(2, new Person("long", 18));
        System.out.println(list.toString());
        System.out.println(list.contains(new Person("zs", 30)));
        System.out.println(list.contains(new Person("zs", 20)));
        list.remove(2);
        System.out.println(list.toString());
    }


    private static void testSimpleSkipList() {
        SimpleSkipList<String, Integer> skipList = new SimpleSkipList<>();
        System.out.println(skipList.isEmpty());
        System.out.println(skipList.size());
        skipList.put("hello", 1);
        skipList.put("world", 2);
        skipList.put("java", 3);
        skipList.put("good", 4);
        skipList.put("nice", 5);
        System.out.println(skipList.isEmpty());
        System.out.println(skipList.size());
        System.out.println(skipList.get("world"));
        System.out.println(skipList.remove("good"));
        System.out.println(skipList.size());
    }
}
