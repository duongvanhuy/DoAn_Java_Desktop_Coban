/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.HoaDon;
import Connection.Connection_to_SQLServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dell
 */
public class HoaDonDao {
//    lấy ra tên các mặt hàng bằng nối chuỗi
    public static String getHoaDon(int maDH) {

      String tenMH = "";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.SoDH,  B.TenMH\n" +
"from ChiTietDonHang A, MatHang B, DonHang C\n" +
"where A.MaMH = B.MaMH and \n" +
"A.SoDH = C.SoDH \n" +
"and C.SoDH = ?\n" +
"group by A.SoDH, B.TenMH";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maDH);
            rs = ps.executeQuery();

            while (rs.next()) {
                tenMH += rs.getString("tenMH");
         
                 
            }
//            close(conn, ps, rs);
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tenMH;
    }
    
    
//    lấy ra tổng tiền tính cả phí VC
    public static int getTongTien(int maDH){
        
        int tongTien =0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.SoDH, C.phiVC, (sum(B.GiaBan* A.soluong) + C.phiVC )as 'thanhTien'\n" +
"from ChiTietDonHang A, MatHang B, DonHang C\n" +
"where A.MaMH = B.MaMH and \n" +
"A.SoDH = C.SoDH \n" +
"and C.SoDH = ?\n" +
"group by A.SoDH,C.phiVC";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
             ps.setInt(1, maDH);
            rs = ps.executeQuery();
            while(rs.next()){
                  tongTien = rs.getInt("thanhTien");
            }

          
//            close(conn, ps, rs);
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tongTien;
    }
//    lấy ra 1 hóa đơn
    public static HoaDon getTTHoaDon(int maHD){
        
        
        HoaDon hoadon= new HoaDon();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.SoDH,  A.phiVC, D.TrangThai\n" +
"from DonHang A, ChiTietDonHang B, MatHang C, TinhTrangDonHang D\n" +
"where A.SoDH = B.SoDH and B.MaMH = C.MaMH and A.SoDH = D.SoDonHang and A.SoDH = ?\n" +
"group by  A.SoDH,  A.phiVC, D.TrangThai";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();
            
            while(rs.next()){
                 hoadon.setSoDH(rs.getInt("soDH"));
            hoadon.setPhiVC(rs.getInt("phiVC"));
            hoadon.setTrangThai(rs.getInt("TrangThai"));
            hoadon.setTongTien(getTongTien(maHD));
            hoadon.setMatHang(getHoaDon(maHD));
            }
           
            

           
//            close(conn, ps, rs);
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hoadon;
    }
//    lấy ra thông tin hóa đơn xuất
    
}
