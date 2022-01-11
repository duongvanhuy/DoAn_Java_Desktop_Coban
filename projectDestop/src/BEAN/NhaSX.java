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
public class NhaSX {
//    MaNSX varchar(10) primary key,
//	TenNSX nvarchar(225) not null,
//	Email nvarchar(225),
//	DiaChi nvarchar(MAX) not null,
//	SoDT varchar(15) not null,
//	NgayTao date
    
    private String maNhaSX;
    private String tenNSX;
    private String email;
    private String diaChi;
    private String soDienThoai;
    private Date ngayTao;

    public String getMaNhaSX() {
        return maNhaSX;
    }

    public void setMaNhaSX(String maNhaSX) {
        this.maNhaSX = maNhaSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    
}
