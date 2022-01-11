/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.MatHang;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class SanPhamDAO {

    public static List<MatHang> getAllSanPham() {

        List<MatHang> mathangs = new ArrayList<MatHang>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from matHang";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                MatHang mathang = new MatHang();

                mathang.setMaMH(rs.getString("MaMH"));
                mathang.setTenMH(rs.getString("TenMH"));
                mathang.setSoLuong(rs.getInt("SoLuong"));
                mathang.setLoai(rs.getInt("Loai"));
                mathang.setNhaSX(rs.getString("NhaSX"));
                mathang.setNgayTao(rs.getDate("NgayTao"));
                mathang.setGiaNhap(rs.getInt("GiaNhap"));
                mathang.setGiaBan(rs.getInt("giaBan"));
                mathang.setHinhAnh(rs.getBytes("hinhAnh"));
               

                mathangs.add(mathang);
            }
            close(conn, ps, rs);
            return mathangs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static void addSanPham(MatHang mathang){
     
        java.util.Date ngayGio = mathang.getNgayTao();
        java.sql.Date sqlStartDate = new java.sql.Date(ngayGio.getTime());
        
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "insert into matHang  values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, mathang.getMaMH());
            ps.setString(2, mathang.getTenMH());
            ps.setInt(3, mathang.getSoLuong());
            ps.setInt(4, mathang.getLoai());
            ps.setString(5, mathang.getNhaSX());
            ps.setDate(6, sqlStartDate);
            ps.setInt(7, mathang.getGiaNhap());
            ps.setInt(8, mathang.getGiaBan());
            ps.setBytes(9, mathang.getHinhAnh());
            
            int result = ps.executeUpdate();
            System.out.println(result);
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
 public static MatHang getMaMH(String maMH){
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "select * from matHang where maMH =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maMH);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                MatHang mathang = new MatHang();
                mathang.setMaMH(rs.getString("MaMH"));
                mathang.setTenMH(rs.getString("TenMH"));
                mathang.setSoLuong(rs.getInt("SoLuong"));
                mathang.setLoai(rs.getInt("Loai"));
                mathang.setNhaSX(rs.getString("NhaSX"));
                mathang.setNgayTao(rs.getDate("NgayTao"));
                mathang.setGiaNhap(rs.getInt("GiaNhap"));
                mathang.setGiaBan(rs.getInt("giaBan"));
                mathang.setHinhAnh(rs.getBytes("hinhAnh"));
                return mathang;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
 public static MatHang searchProduct(String tenMH){
       
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "Select * from matHang where tenMH = ?";
        
        
        
        try {
            
            MatHang product = new MatHang();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenMH);
            ResultSet rs = ps.executeQuery();
            
            
            if(rs.next()){
                product.setMaMH(rs.getString("MaMH"));
                product.setTenMH(rs.getString("TenMH"));
                product.setSoLuong(rs.getInt("SoLuong"));
                product.setLoai(rs.getInt("Loai"));
                product.setNhaSX(rs.getString("NhaSX"));
                product.setNgayTao(rs.getDate("NgayTao"));
                product.setGiaNhap(rs.getInt("GiaNhap"));
                product.setGiaBan(rs.getInt("giaBan"));
                product.setHinhAnh(rs.getBytes("hinhAnh"));
                
                return product;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return null;
    }
    public static void updateMatHang(MatHang mathang){
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "Update matHang set tenMH =?, giaNhap =?, giaBan =?, hinhAnh = ? where maMH =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mathang.getTenMH());
            ps.setInt(2, mathang.getGiaNhap());
            ps.setInt(3, mathang.getGiaBan());
            ps.setBytes(4, mathang.getHinhAnh());
            ps.setString(5, mathang.getMaMH());
            
            
            
            int result = ps.executeUpdate();
            System.out.println(result);
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void deleteMatHang(String maMH){
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "delete from matHang where maMH =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maMH);
            
            int result  = ps.executeUpdate();
            System.out.println(result);
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
//    lấy ra thông tin hóa đơn xuất 
     public static List<MatHang> getAllmatHang_on_Donhang(int maDH) {

        List<MatHang> mathangs = new ArrayList<MatHang>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select C.SoDH, A.TenMH, B.SoLuong, A.giaBan\n" +
"from MatHang A, ChiTietDonHang B, DonHang C\n" +
"where A.MaMH = B.MaMH and B.SoDH = C.SoDH and C.SoDH = ?\n" +
"group by C.SoDH,A.TenMH, B.SoLuong, A.giaBan";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maDH);
            rs = ps.executeQuery();

            while (rs.next()) {
                MatHang mathang = new MatHang();

//                mathang.setMaMH(rs.getString("MaMH"));
                mathang.setTenMH(rs.getString("TenMH"));
                mathang.setSoLuong(rs.getInt("SoLuong"));
//                mathang.setLoai(rs.getInt("Loai"));
//                mathang.setNhaSX(rs.getString("NhaSX"));
//                mathang.setNgayTao(rs.getDate("NgayTao"));
//                mathang.setGiaNhap(rs.getInt("GiaNhap"));
                mathang.setGiaBan(rs.getInt("giaBan"));
//                mathang.setHinhAnh(rs.getBytes("hinhAnh"));
               

                mathangs.add(mathang);
            }
            close(conn, ps, rs);
            return mathangs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
//     hiện thị sale 
     public static List<MatHang> getAllMatHangSale(int trangThai){
         List<MatHang> mathangs = new ArrayList<MatHang>();
         Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.MaMH,A.TenMH, A.giaNhap, A.giaBan, (A.giaBan-A.giaNhap)*B.soluong as 'LN thuần', B.soluong, D.TrangThai\n" +
"from mathang A, ChiTietDonHang B, DonHang C, TinhTrangDonHang D\n" +
"where A.MaMH = B.MaMH and \n" +
"B.SoDH = C.SoDH and \n" +
"C.SoDH= D.SoDonHang and\n" +
"D.TrangThai = ? \n" +
"group by  A.MaMH, B.soluong, D.TrangThai, A.TenMH, A.giaNhap, A.giaBan";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, trangThai);
            rs = ps.executeQuery();

            while (rs.next()) {
                MatHang mathang = new MatHang();

                mathang.setMaMH(rs.getString("MaMH"));
                mathang.setTenMH(rs.getString("TenMH"));
                mathang.setSoLuong(rs.getInt("SoLuong"));
//                lấy loại để khỏi khai báo 1 biến mới
//                  loiaj giờ sẽ có nhiệm vụ là trạng thái
                mathang.setLoai(rs.getInt("TrangThai"));
                if(rs.getInt("TrangThai") ==1){
                    mathang.setNhaSX("Đã giao");
                }
                if(rs.getInt("TrangThai")==3){
                    mathang.setNhaSX("Đã hủy");
                }
//                mathang.setNhaSX(rs.getString("NhaSX"));
//                mathang.setNgayTao(rs.getDate("NgayTao"));
                mathang.setGiaNhap(rs.getInt("GiaNhap"));
                mathang.setGiaBan(rs.getInt("giaBan"));
//                mathang.setHinhAnh(rs.getBytes("hinhAnh"));
               

                mathangs.add(mathang);
            }
            close(conn, ps, rs);
            return mathangs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return mathangs;
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
