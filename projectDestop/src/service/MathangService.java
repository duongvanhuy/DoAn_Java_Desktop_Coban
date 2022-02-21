/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import BEAN.MatHang;
import DAO.SanPhamDAO;
import java.util.List;

/**
 *
 * @author dell
 */
public class MathangService {

    public List<MatHang> getAllSanPham() {
        return SanPhamDAO.getAllSanPham();
    }

    public void addSanPham(MatHang mathang) {
        SanPhamDAO.addSanPham(mathang);
    }

    public MatHang getMaMH(String maMH) {
        return SanPhamDAO.getMaMH(maMH);
    }

    public void deleteMatHang(String maMH) {
        SanPhamDAO.deleteMatHang(maMH);
    }

    public void updateMatHang(MatHang mathang) {
        SanPhamDAO.updateMatHang(mathang);
    }

    public MatHang searchProduct(String tenMH) {
        return SanPhamDAO.searchProduct(tenMH);
    }

    public  List<MatHang> getAllmatHang_on_Donhang(int maDH) {
        return SanPhamDAO.getAllmatHang_on_Donhang(maDH);
    }

    public  List<MatHang> getAllMatHangSale(int trangThai) {
        return SanPhamDAO.getAllMatHangSale(trangThai);
    }
     public  List<MatHang> getAllMatHangSale2(int trangThai, int sort, int dvSort){
         return SanPhamDAO.getAllMatHangSale2(trangThai,sort,dvSort);
     }
     public   List<MatHang>  getMatHang_TonKho(int sort){
          return SanPhamDAO.getMatHang_TonKho(sort);
     }
     public static String[] getAllMaMH(){
         return SanPhamDAO.getAllMaMH();
     }
}
