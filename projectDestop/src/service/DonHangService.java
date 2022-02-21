/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import BEAN.DonHang;
import DAO.DonhangDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dell
 */
public class DonHangService {
    public  List<DonHang> getALLDonHang(){
        return DonhangDAO.getALLDonHang();
    }
     public  DonHang getMaDH(int maDH){
         return DonhangDAO.getMaDH(maDH);
     }
     public void order_Product(DonHang donHang, String sdt, String maMH) {
        DonhangDAO.order_Product(donHang, sdt, maMH);
     }
       public  int getLastDonHang(){
           return DonhangDAO.getLastDonHang();
       }
   public  void insert_Data_In_ChiTietDonHang(String maMH, int soDH, int soLuong){
        DonhangDAO.insert_Data_In_ChiTietDonHang(maMH, soDH, soLuong);
   }

}
