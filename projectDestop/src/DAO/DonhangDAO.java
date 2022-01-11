/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.DonHang;
import BEAN.MatHang;
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
public class DonhangDAO {
    public static List<DonHang> getALLDonHang() {

        List<DonHang> donHangs = new ArrayList<DonHang>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select A.SoDH, A.SoLuong, A.LoaiThanhToan, A.NgayDat , B.HoTen as 'KhachHang1', D.HoTen, sum(F.soluong*E.giaBan) as 'Tongtien'\n" +
"from DonHang A, KhachHang B , TinhTrangDonHang C, NhanVien D, MatHang E, ChiTietDonHang F\n" +
"where \n" +
"A.KhachHang = B.MaKH and \n" +
"A.SoDH = C.SoDonHang and \n" +
"E.MaMH = F.MaMH and \n" +
"F.SoDH= A.SoDH and \n" +
"C.NVPhuTrach = D.MaNV\n" +
"group by A.SoDH,  A.LoaiThanhToan, A.NgayDat , B.HoTen , D.HoTen, A.SoLuong";

        try {
            conn = Connection_to_SQLServer.innit();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                DonHang donhang = new DonHang();

                donhang.setSoHD(rs.getInt("soDH"));
                donhang.setLoaiThanhToan(rs.getInt("loaiThanhToan"));
                donhang.setSoLuong(rs.getInt("soLuong"));
                donhang.setKhachHang(rs.getString("KhachHang1"));
                donhang.setNhanVien(rs.getString("HoTen"));
//                donhang.setHinhThucVC(rs.getInt("hinhThucVanChuyen"));
                donhang.setTongTien(rs.getInt("Tongtien"));
                donhang.setNgayDat(rs.getDate("ngayDat"));

               donHangs.add(donhang);
            }
            close(conn, ps, rs);
            return donHangs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static void addSanPham(MatHang mathang){
     
        java.util.Date ngayGio = mathang.getNgayTao();
        java.sql.Date sqlStartDate = new java.sql.Date(ngayGio.getTime());
        System.out.println(sqlStartDate);
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
 public static DonHang getMaDH(int maDH){
        Connection conn =Connection_to_SQLServer.innit();
        String sql = "select A.*, B.HoTen as 'KhachHang1', D.HoTen, F.soluong*E.giaBan as 'Tongtien'\n" +
"from DonHang A, KhachHang B , TinhTrangDonHang C, NhanVien D, MatHang E, ChiTietDonHang F \n" +
"where \n" +
"A.KhachHang = B.MaKH and \n" +
"A.SoDH = C.SoDonHang and \n" +
"E.MaMH = F.MaMH and \n" +
"F.SoDH= A.SoDH and \n" +
"C.NVPhuTrach = D.MaNV" +
" and  A.SoDH = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maDH);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
//                 private int soHD;
//    private int loaiThanhToan;
//    private int soLuong;
//    private String diaChiGH;
//    private String khachHang;
//    private String nhanVien;
//    private int hinhThucVanChuyen;
//    private int tongTien;
//    private Date ngayDat;
                DonHang donhang = new DonHang();
                
                donhang.setSoHD(rs.getInt("soDH"));
                donhang.setKhachHang(rs.getString("KhachHang1"));
                donhang.setNhanVien(rs.getString("HoTen"));
                donhang.setSoLuong(rs.getInt("soLuong"));
                donhang.setLoaiThanhToan(rs.getInt("loaiThanhToan"));
                donhang.setTongTien(rs.getInt("Tongtien"));
                donhang.setNgayDat(rs.getDate("NgayDat"));
                
                return donhang;
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
