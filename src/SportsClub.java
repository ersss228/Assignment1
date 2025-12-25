public class SportsClub {
    private String clubName;
    private Athlete[] athletes;

    public SportsClub(String clubName, Athlete[] athletes) {
       this.clubName=clubName;
       this.athletes=athletes;
    }
    public String getClubName() {
        return clubName;
    }
    public Athlete[] getAthletes() {
        return athletes;
    }
    public void printAllAthletes(){
        System.out.println("SportsClub:" + clubName);
        for (int i = 0; i<athletes.length; i++){
            athletes[i].printInfo();
        }
    }
    public Athlete findOldestAthlete(){
        Athlete oldest =  athletes[0];
        for (int i = 1; i<athletes.length; i++){
            if(athletes[i].getAge() > oldest.getAge()){
                oldest=athletes[i];
            }
        }
        return oldest;
    }

}
