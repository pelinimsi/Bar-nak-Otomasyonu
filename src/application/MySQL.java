package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

public class MySQL {
    static String url = "jdbc:mysql://127.0.0.1:3306/animalsdb";
    static String kullaniciAdi = "root";
    static String sifre = "";

    public static ArrayList<Animal> get_animals(String query) {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre)) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int age = resultSet.getInt("age");
                    String name = resultSet.getString("name");
                    String kind = resultSet.getString("kind");
                    String photo_url = resultSet.getString("url");
                    Boolean gender = resultSet.getBoolean("gender");
                    Boolean isdog = resultSet.getBoolean("is_dog");
                    if(isdog)
                    	animals.add(new Dog(id, name, kind, photo_url, age, gender));
                    else
                    	animals.add(new Cat(id, name, kind, photo_url, age, gender));
                }
            } catch (SQLException e) {
                System.out.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
        }
        return animals;
    }
    
    public static Animal getAnimalById(int id) {
        Animal animal = null;
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
            Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM ANIMALS WHERE ID = " + id;
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    int animalId = resultSet.getInt("id");
                    int age = resultSet.getInt("age");
                    String name = resultSet.getString("name");
                    String kind = resultSet.getString("kind");
                    String photoUrl = resultSet.getString("url");
                    boolean gender = resultSet.getBoolean("gender");
                    boolean isDog = resultSet.getBoolean("is_dog");
                    if (isDog)
                        animal = new Dog(animalId, name, kind, photoUrl, age, gender);
                    else 
                        animal = new Cat(animalId, name, kind, photoUrl, age, gender);
                    query = "SELECT * FROM MEDICINES WHERE ID = " + id;
                    try (ResultSet rSet = statement.executeQuery(query)) {
                        while (rSet.next()) {
                            String namee = rSet.getString("name");
                            String date = rSet.getString("date");
                            boolean isVaccine = rSet.getBoolean("is_vaccine");
                            if(isVaccine)
                            	animal.medicines.add(new Vaccine(namee,date));
                            else
                            	animal.medicines.add(new Pill(namee,date));
                    }
                       
                    } catch (SQLException e) {
                        System.err.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
                    }

                }  
            } catch (SQLException e) {
                System.err.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
        }
        return animal;
    }
    
    public static void commit(String query) {
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Sorgu çalýþtýrýlýrken veya commit iþlemi yapýlýrken bir hata oluþtu: " + e.getMessage());
        }
    }
    
    public static User getUser(String username,String password) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
            Statement statement = connection.createStatement()) {
            String query = "SELECT id,is_admin FROM USERS WHERE username = '" + username + "' AND userpassword = '" + password + "'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    user = new User(resultSet.getInt("id"),username,password,resultSet.getBoolean("is_admin"));
                } else {
                    System.out.println("Kullanýcý bulunamadý.");
                }
            } catch (SQLException e) {
                System.err.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
        }
        return user;
    }
    
    public static boolean checkUser(String username) {
    	try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
               Statement statement = connection.createStatement()) {
               String query = "SELECT is_admin FROM USERS WHERE username = '" + username + "'";
               try (ResultSet resultSet = statement.executeQuery(query)) {
                   if (resultSet.next()) {
                       return true;
                   } else {
                       System.out.println("Kullanýcý bulunamadý.");
                   }
               } catch (SQLException e) {
                   System.err.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
               }
           } catch (SQLException e) {
               System.err.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
           }
    	return false;
    }
    public static String getUsernameById(int id) {
    	try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre);
               Statement statement = connection.createStatement()) {
               String query = "SELECT USERNAME FROM USERS WHERE ID = " + id;
               try (ResultSet resultSet = statement.executeQuery(query)) {
                   if (resultSet.next()) {
                       return resultSet.getString("USERNAME");
                   } else {
                       System.out.println("Kullanýcý bulunamadý.");
                   }
               } catch (SQLException e) {
                   System.err.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
               }
           } catch (SQLException e) {
               System.err.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
           }
    	return "";
    }
    public static ArrayList<request> get_requests(String query) {
        ArrayList<request> reqs = new ArrayList<request>();
        try (Connection connection = DriverManager.getConnection(url, kullaniciAdi, sifre)) {
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int user_id = resultSet.getInt("user_id");
                    int animal_id = resultSet.getInt("animal_id");
                    Date req_date = resultSet.getDate("req_date");
                    Boolean is_pending = resultSet.getBoolean("is_pending");
                    Boolean is_approved = resultSet.getBoolean("is_approved");
                    reqs.add(new request(user_id,animal_id,is_pending,is_approved,req_date));
                }
            } catch (SQLException e) {
                System.out.println("Sorgu çalýþtýrýlýrken bir hata oluþtu: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Veritabanýna baðlanýrken bir hata oluþtu: " + e.getMessage());
        }
        return reqs;
    }
    
}
