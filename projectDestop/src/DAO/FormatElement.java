/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author dell
 */
public class FormatElement {
//    định dạng tiền tệ
    public static String numberMoney(int number){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(number);

        return str1;
    }
    
//    định dạng phần tử trong table nằm giữa
    public static void formatTable(int columns, int alignment, JTable tableFormat){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        JLabel.CENTER: căn giữa-------JLabel.RIGHT: căn phải
        centerRenderer.setHorizontalAlignment(alignment);
        
         tableFormat.getColumnModel().getColumn(columns).setCellRenderer(centerRenderer);
    }
    public static void clock(JLabel timeSystemBD, JLabel calendarTK) {
    Thread clock = new Thread() {
        public void run() {
            try {
                while (true) {
                    Calendar cal = new GregorianCalendar();
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    String thu;
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek == 1) {
                        thu = "Chủ nhật";
                    } else {
                        thu = "Thứ " + Integer.toString(dayOfWeek);
                    }
                    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);

//                    timeSystem.setText(hour + ":" + minute + ":" + second);
                    timeSystemBD.setText(hour + ":" + minute + ":" + second);
//                    timeSystemTK.setText(hour + ":" + minute + ":" + second);
//                    calendarBD.setText(thu + " ngày " + dayOfMonth + " tháng " + (month + 1) + " năm " + year);
                    calendarTK.setText(thu  + " ngày " + dayOfMonth + " tháng " + (month + 1) + " năm " + year);
                    sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    clock.start();
}
  
 
}
