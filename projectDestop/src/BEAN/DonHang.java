/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import java.util.Date;

/**
 *
 * @author dell
 */
public class DonHang {

    private int soHD;
    private int loaiThanhToan;
    private int soLuong;
    private String diaChiGH;
    private String khachHang;
    private String nhanVien;
    private int hinhThucVC;
    private int tongTien;
    private Date ngayDat;
    private int phiVC;

    public int getPhiVC() {
        return phiVC;
    }

    public void setPhiVC(int phiVC) {
        this.phiVC = phiVC;
    }

    public DonHang() {
    }

    public int getSoHD() {
        return soHD;
    }

    public void setSoHD(int soHD) {
        this.soHD = soHD;
    }

    public int getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(int loaiThanhToan) {
        this.loaiThanhToan = loaiThanhToan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDiaChiGH() {
        return diaChiGH;
    }

    public void setDiaChiGH(String diaChiGH) {
        this.diaChiGH = diaChiGH;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
    

    public int getHinhThucVC() {
        return hinhThucVC;
    }

    public void setHinhThucVC(int hinhThucVC) {
        this.hinhThucVC = hinhThucVC;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }
    
}
