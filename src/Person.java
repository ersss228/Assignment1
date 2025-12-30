public class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public void printInfo() {
        System.out.println(name+" "+age);
    }
}
