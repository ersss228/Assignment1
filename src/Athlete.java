public class Athlete {
    private String name;
    private int age;
    private Sport sport;

    public Athlete(String name, int age, Sport sport) {
        this.name = name;
        this.age = age;
        this.sport = sport;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    public Sport getSport() {
        return sport;
    }
    public void setSport(Sport sport) {
        this.sport = sport;
    }
    public void printSport(){
        System.out.println(name+" "+age+" "+sport);
    }
    public void printInfo(){
        System.out.println(name+" "+age+" "+sport);
    }

}