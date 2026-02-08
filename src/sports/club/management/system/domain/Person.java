package sports.club.management.system.domain;

public class Person implements Printable {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    @Override
    public void printInfo() {
        printTitle();
        System.out.println(name+" "+age);
    }

}
