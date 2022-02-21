/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class Connection_to_SQLServer {
     static Connection conn;

    public static Connection innit() {

        var server = "DESKTOP-V3UDUKA\\\\SQLEXPRESS";
        var user = "sa";
        var password = "123456";
        var db = "Hine";
        var port = 1433;
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);

        try {
            conn = ds.getConnection();
//            System.out.println("Kết nối thành công");
//            System.out.println(conn.getCatalog());
         return  conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
