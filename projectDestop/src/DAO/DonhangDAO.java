/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BEAN.DonHang;
import BEAN.MatHang;
import Connection.Connection_to_SQLServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        String sql = "select A.SoDH, A.SoLuong ,A.phiVC, A.LoaiThanhToan, A.NgayDat , B.HoTen as 'KhachHang1', D.HoTen, sum(F.soluong*E.giaBan) as 'Tongtien'\n"
                + "from DonHang A, KhachHang B , TinhTrangDonHang C, NhanVien D, MatHang E, ChiTietDonHang F\n"
                + "where \n"
                + "A.KhachHang = B.MaKH and \n"
                + "A.SoDH = C.SoDonHang and \n"
                + "E.MaMH = F.MaMH and \n"
                + "F.SoDH= A.SoDH and \n"
                + "C.NVPhuTrach = D.MaNV\n"
                + "group by A.SoDH,  A.LoaiThanhToan, A.NgayDat , B.HoTen , D.HoTen, A.SoLuong, A.phiVC";
        
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
                donhang.setPhiVC(rs.getInt("phiVC"));
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
    
    public static void addSanPham(MatHang mathang) {
        
        java.util.Date ngayGio = mathang.getNgayTao();
        java.sql.Date sqlStartDate = new java.sql.Date(ngayGio.getTime());
        System.out.println(sqlStartDate);
        Connection conn = Connection_to_SQLServer.innit();
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
    
    public static DonHang getMaDH(int maDH) {
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "select A.*, B.HoTen as 'KhachHang1', D.HoTen, F.soluong*E.giaBan as 'Tongtien'\n"
                + "from DonHang A, KhachHang B , TinhTrangDonHang C, NhanVien D, MatHang E, ChiTietDonHang F \n"
                + "where \n"
                + "A.KhachHang = B.MaKH and \n"
                + "A.SoDH = C.SoDonHang and \n"
                + "E.MaMH = F.MaMH and \n"
                + "F.SoDH= A.SoDH and \n"
                + "C.NVPhuTrach = D.MaNV"
                + " and  A.SoDH = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maDH);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
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
    
    public static MatHang searchProduct(String tenMH) {
        
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "Select * from matHang where tenMH = ?";
        
        try {
            
            MatHang product = new MatHang();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenMH);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
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
    
    public static void updateMatHang(MatHang mathang) {
        Connection conn = Connection_to_SQLServer.innit();
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
    
    public static void deleteMatHang(String maMH) {
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "delete from matHang where maMH =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maMH);
            
            int result = ps.executeUpdate();
            System.out.println(result);
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

//    kh??ch h??ng ?????t m???i 1 ????n h??ng
    public static void order_Product(DonHang donHang, String sdt, String maMH) {
        
        java.util.Date ngayGio = donHang.getNgayDat();
        java.sql.Date sqlStartDate = new java.sql.Date(ngayGio.getTime());
        try {
            Connection conn = Connection_to_SQLServer.innit();
//         kh??ch h??ng ph???i t???n t???i.
//          1. N???u kh??ch h??ng c?? t???n t???i trong h??? th???ng th?? cb m??nh k l??m g?? c???
//          2.N???u kh??ng t???n t???i th?? ti???n h??nh t???o m???i kh??ch h??ng
//

//          1. th??m v??o b???ng ????n h??ng
            if (!check_exist_KhachHang(sdt)) {
                KhachHangDAO.insert_new_KhachHang(donHang.getKhachHang(), sdt, ngayGio);
                System.out.println("Th??m th??nh c??ng kh??ch h??ng");
            } else {
                System.out.println("Khach hang da ton tai");
            }
            
            String sql = "insert into donhang(LoaiThanhToan, DCgiaoHang,"
                    + "khachHang, hinhThucVanChuyen, phiVC, ngayDat, soLuong) values(?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, donHang.getLoaiThanhToan());
            ps.setString(2, donHang.getDiaChiGH());
            ps.setInt(3, check_exist_KhachHang_1(sdt));
            ps.setInt(4, donHang.getHinhThucVC());
            ps.setInt(5, donHang.getPhiVC());
            ps.setDate(6, sqlStartDate);
            ps.setInt(7, 0);
            
            int result = ps.executeUpdate();
            System.out.println("Th??m th??nh c??ng ????n h??ng " + result);

//            System.out.println("Th??m th??nh c??ng v??o b???ng h??a ????n v???i: "+ ps.executeUpdate());
            close(conn, ps, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //     m??nh l???y ra ????n h??ng v???a t???o ra
    public static int getLastDonHang() {
        Connection conn = Connection_to_SQLServer.innit();
//        String sql = "SELECT TOP 1 soDH FROM donhang ORDER BY SoDH DESC ";
        String sql = "select * from donhang";
        int a = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("S??? h??a ????n " + a);
            while (rs.next()) {
                a = rs.getInt("soDH");
                System.out.println("S??? h??a ????n " + a);
            }
            
            close(conn, ps, rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return a;
        
    }

//    ki???m tra xem c?? kh??ch h??ng t???n t???i trong CDSL kh??ng v???i tham s??? truy???n v??o l?? sdt
    public static boolean check_exist_KhachHang(String sdt) throws SQLException {
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "select sodienThoai from khachHang";

//         l??u l???i s??? ??i???n tho???i c???a h??? th???ng
//         List<String> sdt_System = new ArrayList<String>();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
//             String sdt_System = "";
            if (sdt.equals(rs.getString("sodienThoai"))) {
                return true;
            }
            
        }
        
        close(conn, ps, rs);
        return false;
    }
    
    public static int check_exist_KhachHang_1(String sdt) throws SQLException {
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "select maKH, sodienThoai from khachHang";
        int check = 0;
//         l??u l???i s??? ??i???n tho???i c???a h??? th???ng
//         List<String> sdt_System = new ArrayList<String>();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
//             String sdt_System = "";
            if (sdt.equals(rs.getString("sodienThoai"))) {
                check = rs.getInt("maKH");
                return check;
            }
            
        }
        
        close(conn, ps, rs);
        return check;
    }

    public static void insert_Data_In_ChiTietDonHang(String maMH, int soDH, int soLuong) {
        Connection conn = Connection_to_SQLServer.innit();
        String sql = "insert into ChiTietDonHang values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maMH);
            ps.setInt(2, soDH);
            ps.setInt(3, soLuong);

//            if(ps.executeUpdate()>0){
//                System.out.println("b???ng t??nh tr???ng ??on h??ng th??m th??nh c??ng");
//            }else{
//                System.out.println("b???ng t??nh tr???ng ??on th??m th???t b???i");
//            }
            int result = ps.executeUpdate();
            System.out.println("chi ti???t ????n h??ng " + result);
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
