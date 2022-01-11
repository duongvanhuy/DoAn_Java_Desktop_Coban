/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import BEAN.HoaDon;
import DAO.HoaDonDao;

/**
 *
 * @author dell
 */
public class HoaDonService {
    public  int getTongTien(int maDH){
        return HoaDonDao.getTongTien(maDH);
    }
    public  String getHoaDon(int maDH) {
         return HoaDonDao.getHoaDon(maDH);
    }
    public  HoaDon getTTHoaDon(int maHD){
        return HoaDonDao.getTTHoaDon(maHD);
    }

  
}
