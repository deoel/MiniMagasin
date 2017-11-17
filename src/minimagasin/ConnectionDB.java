/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimagasin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Deoel
 */
public class ConnectionDB {
    private static Connection connection;
    
    private ConnectionDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306/facture_magasin";
            String user = "root";
            String passwd = "";
            
            ConnectionDB.connection = DriverManager.getConnection(url, user,passwd);
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        if(ConnectionDB.connection == null)
            new ConnectionDB();
        return ConnectionDB.connection;
    }
    
}
