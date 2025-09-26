/** Group members

TUYIZERE Elie     223007197
HABUMUGISHA Eric  223009063
NIKUBWAYO Leandre 223015716
**/
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

public static Connection getConnection()throws Exception{
	
	Class.forName("com.mysql.cj.jdbc.Driver");
	
	return DriverManager.getConnection("jdbc:mysql://localhost:3306/hostelmanagementsystem","root","");
}

}
/** public class DB {

    public static Connection getConnection() throws Exception {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to database
        String url = "jdbc:mysql://localhost:3306/hostelmanagementsystem";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }
}**/
