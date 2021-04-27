package com.lwl.list;

public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{name=" + name + ",age=" + age + "}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Person)) {
            return false;
        } else {
            Person person = (Person) o;
            if (this.name.equals(person.getName()) && this.age.equals(person.getAge())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
