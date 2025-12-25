public class Sport {
private String name;
private boolean isTeamSport;

public Sport(String name, boolean isTeamSport) {
    this.name = name;
    this.isTeamSport = isTeamSport;
}

public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public boolean isTeamSport() {
    return isTeamSport;
}
public String toString() {
    String type = isTeamSport ? "Team sport" : "Individual sport" ;
    return name + " " + type;
}
public void setTeamSport(boolean teamSport) {
    isTeamSport = teamSport;
}
public void printSport(){
    System.out.println(name+" "+isTeamSport);
}
}