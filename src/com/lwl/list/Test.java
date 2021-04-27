package com.lwl.list;


public class Test {
    public static void main(String[] args) {
       // testArrayList();
      testLinkedList();
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
}
