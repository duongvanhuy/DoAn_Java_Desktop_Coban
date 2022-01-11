/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import BEAN.DonHang;
import DAO.DonhangDAO;
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
}
