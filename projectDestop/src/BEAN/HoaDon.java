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
public class HoaDon {

    private int soHD;
    private Date ngayTao;
    private int phiVC;
    private int soDH;
    
//    1. Đã hoàn thành
//    2. Đang giao hàng
//    3. Đã hủy
            
    private int trangThai;
    private int tongTien;
    private String matHang;

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getMatHang() {
        return matHang;
    }

    public void setMatHang(String matHang) {
        this.matHang = matHang;
    }
    
    public int getSoHD() {
        return soHD;
    }

    public void setSoHD(int soHD) {
        this.soHD = soHD;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getPhiVC() {
        return phiVC;
    }

    public void setPhiVC(int phiVC) {
        this.phiVC = phiVC;
    }

    public int getSoDH() {
        return soDH;
    }

    public void setSoDH(int soDH) {
        this.soDH = soDH;
    }

    public HoaDon() {
    }
   
}
