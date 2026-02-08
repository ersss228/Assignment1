package sports.club.management.system.domain;

public class Athlete extends Person {
    private Sport sport;

    public Athlete(String name, int age, Sport sport) {
        super(name, age);
        this.sport = sport;
    }
    public Sport getSport() {
        return sport;
    }

    @Override
    public void printInfo() {
        System.out.println(name + " " + age + " " + sport);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return age == athlete.age && name.equals(athlete.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }

    public static class Builder {
        private String name;
        private int age;
        private Sport sport;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSport(Sport sport) {
            this.sport = sport;
            return this;
        }

        public Athlete build() {
            return new Athlete(name, age, sport);
        }
    }
}