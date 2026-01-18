import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Sport football = new Sport("Football",true);
        Sport boxing = new  Sport("Boxing",false);

        Athlete a1 = new Athlete("Yersultan",17,football);
        Athlete a2 = new Athlete("Rayana",18,boxing);
        Athlete a3 = new Athlete("Miras",19,football);

        Athlete[] athletes = {a1,a2,a3};


        SportsClub club = new SportsClub("AITU Sports Club",athletes);

        club.printAllAthletes();

        System.out.println("\nOldest athlete");
        club.findOldestAthlete().printInfo();

        System.out.println();
        club.printTeamSportAthletes();
    }

}

public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/Sport";
    String user = "postgres";
    String password = "naz30june";

    try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Sport","postgres","naz30june");
            Statement stmt = conn.createStatement()){

        stmt.executeUpdate("DELETE from sport");
        stmt.executeUpdate("DELETE from athlete");
        stmt.executeUpdate("DELETE from sportsclub");
        stmt.executeUpdate("INSERT INTO sport(id,name,isteamsport) VALUES (1,'football',true)"
        );
        stmt.executeUpdate("INSERT INTO sport(id,name,isteamsport) VALUES (2,'boxing',false)"
        );
        stmt.executeUpdate("INSERT INTO sport(id,name,isteamsport) VALUES (3,'wrestling',false)"
        );
        stmt.executeUpdate("INSERT INTO athlete(id,name,age) VALUES (1,'Yersultan',17)"
        );
        stmt.executeUpdate("INSERT INTO athlete(id,name,age) VALUES (2,'Rauan',18)"
        );
        stmt.executeUpdate("INSERT INTO athlete(id,name,age) VALUES (3,'Miras',19)"
        );
        stmt.executeUpdate("INSERT INTO sportsclub(id,name,numberofathletes) VALUES (1,'AITU Sports club',22)"
        );
        stmt.executeUpdate("INSERT INTO sportsclub(id,name,numberofathletes) VALUES (2,'NU Sports club',23)"
        );
        stmt.executeUpdate("INSERT INTO sportsclub(id,name,numberofathletes) VALUES (3,'MNU Spotrs club',18)"
        );

        ResultSet rs = stmt.executeQuery("SELECT * FROM sportsclub");
        while(rs.next()){
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("name"));
            System.out.println(rs.getInt("numberofathletes"));
        }
        stmt.executeUpdate("UPDATE athlete SET age = 18 WHERE name = 'Yersultan'");
        stmt.executeUpdate("DELETE FROM sport WHERE name = 'boxing'");
    }catch (Exception e){
        e.printStackTrace();
    }

}



































































































































































































































