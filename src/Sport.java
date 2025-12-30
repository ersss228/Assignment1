public class Sport {
private String name;
private boolean isTeamSport;

public Sport(String name, boolean isTeamSport) {
    this.name = name;
    this.isTeamSport = isTeamSport;
}
public boolean isTeamSport() {
    return isTeamSport;
}

@Override
public String toString() {
    return name + " (" + (isTeamSport ? "Team sport" : "Individual sport") + ")";
}

@Override
    public boolean equals (Object o){
    if (o == this) return true;
    if (o == null || o.getClass() != this.getClass()) return false;
    Sport sport = (Sport) o;
    return name.equals(sport.name);
}
@Override
public int hashCode (){
    return name.hashCode();
}
}