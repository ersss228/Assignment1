package sports.club.management.system.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public class SportsClub {
    private String clubName;
    private Athlete[] athletes;

    public SportsClub(String clubName, Athlete[] athletes) {
       this.clubName=clubName;
       this.athletes=athletes;
    }
    public void printAllAthletes(){
        System.out.println("sports.club.management.system.domain.SportsClub:" + clubName);
        for (Athlete athlete : athletes){
            athlete.printInfo();
        }
    }
    public Athlete findOldestAthlete(){
        Athlete oldest =  athletes[0];
        for (Athlete athlete : athletes){
            if(athlete.getAge() > oldest.getAge()){
                oldest=athlete;
            }
        }
        return oldest;
    }

    public void printTeamSportAthletes(){
        System.out.println("Team sport athletes:");
        for (Athlete athlete : athletes){
            if (athlete.getSport().isTeamSport()){
                athlete.printInfo();
            }
        }
    }

    public void printFilteredAthletes(Predicate<Athlete> condition){
        Arrays.stream(athletes).filter(condition).forEach(Athlete::printInfo);
    }

}
