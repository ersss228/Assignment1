package sports.club.management.system.domain;

public interface Printable {
    void printInfo();

    default void printTitle(){
        System.out.println("===INFO===");
    }
}
