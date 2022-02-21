/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.KhachHang;
import Connection.Connection_to_SQLServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class KhachHangDAO {

    public static KhachHang getKhachHang(int maDH) {

        KhachHang khachHang = new KhachHang();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.HoTen, A.SoDienThoai, B.DCGiaoHang\n"
                + "from KhachHang A, DonHang B\n"
                + "where A.MaKH = B.KhachHang and B.SoDH = ?";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maDH);
            rs = ps.executeQuery();

            while (rs.next()) {
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setDiaChi(rs.getString("DCGiaoHang"));
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
            }
//            close(conn, ps, rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return khachHang;
    }
    public static void insert_new_KhachHang(String hoTen, String sdt, Date ngayTao){
        
        
        java.util.Date ngayGio = ngayTao;
        java.sql.Date sqlStartDate = new java.sql.Date(ngayGio.getTime());
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "insert into khachhang(hoTen,soDienThoai,NgayTao) values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hoTen);
            ps.setString(2, sdt);
            ps.setDate(3, sqlStartDate);
            
            int result = ps.executeUpdate();
            System.out.println("khách hàng : " + result);
            
            
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
    }
     public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
