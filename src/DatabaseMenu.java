import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseMenu {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()){
            while(true){
                System.out.println("MENU");
                System.out.println("1. Add Sport");
                System.out.println("2. Show Sports");
                System.out.println("3. Update Sport");
                System.out.println("4. Delete Sport");
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
                        default : System.out.println("Wrong option");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
