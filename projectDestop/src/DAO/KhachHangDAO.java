/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
