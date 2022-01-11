/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import BEAN.KhachHang;
import DAO.KhachHangDAO;

/**
 *
 * @author dell
 */
public class KhachHangService {
     public static KhachHang getKhachHang(int maDH) {
         return KhachHangDAO.getKhachHang(maDH);
     }
}
