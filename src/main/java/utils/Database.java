package utils;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    final String URL = "jdbc:mysql://localhost:3306/gym_app";
    final String USERNAME = "root";
    final String PASSWORD = "";
    static Database instance;
    Connection connection;
    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection etablie");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static Database getInstance(){
        if (instance==null){
           instance= new Database();
        }
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
