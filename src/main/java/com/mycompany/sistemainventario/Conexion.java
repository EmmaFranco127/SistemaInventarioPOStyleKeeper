package com.mycompany.sistemainventario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author franc
 */
public class Conexion {
    Connection con; 
    private String driver= "com.mysql.cj.jdbc.Driver";
    
    
    public Connection getConnection (){
    try{
        Class.forName(driver);
            String myBD = "jdbc:mysql://localhost:3306/StyleKeeper?serverTimezone=UTC";
            con = DriverManager.getConnection(myBD, "root", "Parkjisung127");
            System.out.println("Conexión exitosa");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
