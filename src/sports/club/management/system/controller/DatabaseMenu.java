package sports.club.management.system.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import sports.club.management.system.config.DBConnection;

public class DatabaseMenu {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()){
            while(true){
                System.out.println("\n=== MENU ===");
                System.out.println("SPORT");
                System.out.println("1. Add Sports");
                System.out.println("2. Show Sports");
                System.out.println("3. Update Sports");
                System.out.println("4. Delete Sports");

                System.out.println("ATHLETE");
                System.out.println("5. Add Athlete");
                System.out.println("6. Show Athletes");
                System.out.println("7. Update Athlete");
                System.out.println("8. Delete Athlete");

                System.out.println("SPORTS CLUB");
                System.out.println("9. Add Club");
                System.out.println("10. Show Clubs");
                System.out.println("11. Update Club");
                System.out.println("12. Delete Club");

                System.out.println("0. Exit");
                System.out.print("Choose:");

                int choice=scanner.nextInt();
                scanner.nextLine();

                if(choice==0) break;

                    switch(choice){
                        case 1:{
                            System.out.println("Sport name:");
                            String sport=scanner.nextLine();
                            System.out.print("Is team sport(true/false):");
                            boolean isTeam=scanner.nextBoolean();

                            stmt.executeUpdate(
                                    "INSERT INTO sport(name, isTeamSport) VALUES ('" + sport + "', " + isTeam + ")");
                                    System.out.println("Added");
                            break;
                        }

                        case 2:{
                            ResultSet rs = stmt.executeQuery("SELECT * FROM SPORT");
                            while (rs.next()){
                                System.out.println(
                                        rs.getInt("id")+" "+
                                        rs.getString("name")+" "+
                                        rs.getBoolean("isTeamSport")
                                );
                            }
                            break;
                        }
                        case 3:{
                            System.out.println("Sport id: ");
                            int idUpdate=scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("New name: ");
                            String newName=scanner.nextLine();

                            stmt.executeUpdate("UPDATE sport SET name='" + newName + "' WHERE id=" + idUpdate);
                                    System.out.println("Uptated. ");
                            break;
                        }
                        case 4:{
                            System.out.println("Sport id: ");
                            int idDelete=scanner.nextInt();

                            stmt.executeUpdate("DELETE FROM sport WHERE id=" + idDelete);
                            System.out.println("Deleted. ");
                            break;
                        }
                        case 5:{
                            System.out.println("Athlete name: ");
                            String athleteName=scanner.nextLine();
                            System.out.println("Athlete age: ");
                            int age=scanner.nextInt();

                            stmt.executeUpdate("INSERT INTO athlete(name, age) VALUES ('" + athleteName + "', " + age + ")");
                            System.out.println("Athlete added.");
                            break;
                        }
                        case 6:{
                            ResultSet rs = stmt.executeQuery("SELECT * FROM ATHLETE");
                            while (rs.next()){
                                System.out.println(
                                        rs.getInt("id") + " " +
                                                rs.getString("name") + " " +
                                                rs.getInt("age")
                                );
                            }
                            break;
                        }
                        case 7:{
                            System.out.print("Athlete id: ");
                            int id = scanner.nextInt();
                            System.out.print("New age: ");
                            int newAge = scanner.nextInt();

                            stmt.executeUpdate(
                                    "UPDATE athlete SET age=" + newAge + " WHERE id=" + id
                            );
                            System.out.println("Athlete updated.");
                            break;
                        }

                        case 8:{
                            System.out.print("Athlete id: ");
                            int id = scanner.nextInt();

                            stmt.executeUpdate("DELETE FROM athlete WHERE id=" + id);
                            System.out.println("Athlete deleted.");
                            break;
                        }
                        case 9:{
                            System.out.print("Club name: ");
                            String clubName = scanner.nextLine();
                            System.out.print("Number of athletes: ");
                            int num = scanner.nextInt();

                            stmt.executeUpdate(
                                    "INSERT INTO sportsclub(name, numberofathletes) VALUES ('" + clubName + "', " + num + ")"
                            );
                            System.out.println("SportsClub added.");
                            break;
                        }
                        case 10:{
                            ResultSet rs = stmt.executeQuery("SELECT * FROM sportsclub");
                            while (rs.next()) {
                                System.out.println(
                                        rs.getInt("id") + " " +
                                                rs.getString("name") + " " +
                                                rs.getInt("numberofathletes")
                                );
                            }
                            break;
                        }
                        case 11:{
                            System.out.print("SportsClub id: ");
                            int id = scanner.nextInt();
                            System.out.print("New numberOfAthletes: ");
                            int newNum = scanner.nextInt();

                            stmt.executeUpdate(
                                    "UPDATE sportsclub SET numberofathletes=" + newNum + " WHERE id=" + id
                            );
                            System.out.println("SportsClub updated.");
                            break;
                        }
                        case 12:{
                            System.out.print("SportsClub id: ");
                            int id = scanner.nextInt();

                            stmt.executeUpdate("DELETE FROM sportsclub WHERE id=" + id);
                            System.out.println("SportsClub deleted.");
                            break;
                        }
                        default : System.out.println("Wrong option");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
