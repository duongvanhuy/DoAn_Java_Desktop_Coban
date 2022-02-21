/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.User;
import Connection.Connection_to_SQLServer;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class UserDao {
     public static boolean getUser(User user ) {

//        User user = new User();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from Login";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
               if(user.getUserName().equals(rs.getString("username")) && user.getPassWord().equalsIgnoreCase(rs.getString("pass"))){
                   return true;
               }
            }
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
