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
public class KhachHang {    
    private int maKH;
    private String hoTen;
    private Date ngaySinh;
    private String email;
    private String soDienThoai;
    private int loaiKH;
    private String diaChi;
    private Date ngayTao;
    
    
    public KhachHang() {
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setLoaiKH(int loaiKH) {
        this.loaiKH = loaiKH;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getMaKH() {
        return maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public int getLoaiKH() {
        return loaiKH;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    
}
