package ru.job4j.gc;

/**
 * @author Vladimir Likhachev
 */
public class User {
    private int age;
    private String name;

    public User() {
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.printf("Removed %d %s%n", age, name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i < 10000; i++) {
            new User(i * i, String.valueOf(i * i));
        }
    }
}
