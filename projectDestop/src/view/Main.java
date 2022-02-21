/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import BEAN.DonHang;
import BEAN.HoaDon;
import BEAN.KhachHang;
import BEAN.MatHang;
import DAO.FormatElement;
import DAO.ImageHelper;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import service.DonHangService;
import service.HoaDonService;
import service.KhachHangService;
import service.MathangService;



import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author dell
 */
public class Main extends javax.swing.JFrame {

    DefaultTableModel defaultTableModel;
     DefaultTableModel defaultTableModel_1;

    MathangService mathangService;
    DonHangService donhangService;
    HoaDonService hoadonService;
    KhachHangService khachhangService;

    MatHang mathang;
    DonHang donhang;
    HoaDon hoadon;
    KhachHang khachHang;

    int phiVC = 0;
    int tongTienHD = 0;
    int moveTab = 0;
    LocalDate ngayGio;
    FormatElement formatElement;
    byte[] productImage;
    CardLayout cardLayout;

    /**
     * Creates new form Main
     */
    public Main() {
//        rộng toàn màn hình
         setExtendedState(this.MAXIMIZED_BOTH);        
        initComponents();
        this.setTitle("Quản lý bán hàng thời trang");
        Dimension d = getMaximumSize();
        this.setSize(d.width, d.height);
        ngayGio = java.time.LocalDate.now();

        mathangService = new MathangService();
        donhangService = new DonHangService();
        hoadonService = new HoaDonService();
        khachhangService = new KhachHangService();

        mathang = new MatHang();
        donhang = new DonHang();
        hoadon = new HoaDon();
        khachHang = new KhachHang();
        
        formatElement = new FormatElement();

        defaultTableModel = new DefaultTableModel() {
            
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        defaultTableModel_1 = new DefaultTableModel() {
            
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
//        initTableSP();
        
//        showPieChart();
//        showLineChart();
//        showHistogram();
//        showBarChart();

//      hiện thị trang chính lên đầu
         CardLayout cardLayout = (CardLayout) card.getLayout();
      cardLayout.show(card, "c10");

    }

    private void initTableSP() {
        int i =1;
        tablMathang.setModel(defaultTableModel);
        
        
        defaultTableModel.addColumn("STT");
        defaultTableModel.addColumn("Mã sản phẩm");
        defaultTableModel.addColumn("Tên sản phẩm");
        defaultTableModel.addColumn("Loại");
        defaultTableModel.addColumn("Giá nhập");
        defaultTableModel.addColumn("Giá bán");
        defaultTableModel.addColumn("Nhà sản xuất");
        defaultTableModel.addColumn("Tồn kho");
        defaultTableModel.addColumn("Ngày tạo");
        defaultTableModel.addColumn("Chú thích");
        JTableHeader header = tablMathang.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));

        
//        chỉnh sửa table
        tablMathang.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablMathang.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablMathang.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablMathang.getColumnModel().getColumn(3).setPreferredWidth(60);
        tablMathang.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablMathang.getColumnModel().getColumn(5).setPreferredWidth(90);
        tablMathang.getColumnModel().getColumn(6).setPreferredWidth(90);
        tablMathang.getColumnModel().getColumn(7).setPreferredWidth(60);
        tablMathang.getColumnModel().getColumn(8).setPreferredWidth(100);
        tablMathang.getColumnModel().getColumn(9).setMinWidth(170);
        tablMathang.getColumnModel().getColumn(9).setMaxWidth(350);
         
        formatElement.formatTable(0,JLabel.CENTER,tablMathang);
        formatElement.formatTable(3,JLabel.CENTER,tablMathang);
        formatElement.formatTable(4,JLabel.RIGHT,tablMathang);
        formatElement.formatTable(5,JLabel.RIGHT,tablMathang);
        formatElement.formatTable(6,JLabel.RIGHT,tablMathang);
        formatElement.formatTable(7,JLabel.CENTER,tablMathang);
        formatElement.formatTable(8,JLabel.RIGHT,tablMathang);
         
//        show dữ liệu ra bẳng
        List<MatHang> matHangs = mathangService.getAllSanPham();
        for (MatHang mathang : matHangs) {
            defaultTableModel.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
                mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney(mathang.getGiaBan()) , mathang.getNhaSX(), 
                mathang.getSoLuong(), mathang.getNgayTao()});
            i++;
        }
    }


    public  void innitTableHD() {
        int i=1;
        DefaultTableModel defaultTableModel_1 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
//        set suex liệu cho table
        tableHD.setModel(defaultTableModel_1);

        defaultTableModel_1.addColumn("STT");
        defaultTableModel_1.addColumn("Khách hàng");
        defaultTableModel_1.addColumn("Nhân viên");
       
        defaultTableModel_1.addColumn("Số lượng");
        defaultTableModel_1.addColumn("Tổng tiền");
         defaultTableModel_1.addColumn("Thanh toán");
        defaultTableModel_1.addColumn("Ngày đặt");
        defaultTableModel_1.addColumn("Ghi chú");
        JTableHeader header = tableHD.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));
        
        
        
         tableHD.getColumnModel().getColumn(0).setPreferredWidth(30);
        formatElement.formatTable(0,JLabel.CENTER,tableHD);
        tableHD.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableHD.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableHD.getColumnModel().getColumn(3).setPreferredWidth(60);
        formatElement.formatTable(3,JLabel.CENTER,tableHD);
        tableHD.getColumnModel().getColumn(4).setPreferredWidth(90);
        formatElement.formatTable(4,JLabel.RIGHT,tableHD);
        tableHD.getColumnModel().getColumn(5).setPreferredWidth(150);
//        formatElement.formatTable(5,JLabel.CENTER,tableHD);
        tableHD.getColumnModel().getColumn(6).setPreferredWidth(100);
//        formatElement.formatTable(6,JLabel.CENTER,tableHD);
         tableHD.getColumnModel().getColumn(7).setMinWidth(170);
         tableHD.getColumnModel().getColumn(7).setMaxWidth(350);
         
          //        xóa hết dữ liệu hiện tại
        defaultTableModel.setRowCount(0);
//        show dữ liệu ra bẳng
        List<DonHang> donhangs = donhangService.getALLDonHang();
        String loaiTT = "";
        for (DonHang donhang : donhangs) {
            if(donhang.getLoaiThanhToan() == 1){
                loaiTT = "Thanh toán online";
            }
            if(donhang.getLoaiThanhToan() == 2){
                loaiTT = "Thanh toán khi nhận hàng";
            }
            if(donhang.getLoaiThanhToan() == 3){
                loaiTT = "Thanh toán tại quầy";
            }
            defaultTableModel_1.addRow(new Object[]{i, donhang.getKhachHang(),
                donhang.getNhanVien(),  donhang.getSoLuong(), 
               formatElement.numberMoney(donhang.getTongTien()) ,loaiTT, donhang.getNgayDat()});
            i++;
        }
    }


    private void innitTableHoaDonXuat(int move) {
        int i = 1;
        int tongTien = 0;
        DefaultTableModel defaultTableModel_3 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };

//        set suex liệu cho table
        tableHoaDonXuat.setModel(defaultTableModel_3);

        defaultTableModel_3.addColumn("Số TT");
        defaultTableModel_3.addColumn("Tên sản phẩm");
        defaultTableModel_3.addColumn("Đơn vị tính");
        defaultTableModel_3.addColumn("Số lượng");
        defaultTableModel_3.addColumn("Đơn Giá");
        defaultTableModel_3.addColumn("Thành Tiền");
//        format header table
        JTableHeader header = tableHoaDonXuat.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));

//        show dữ liệu ra bẳng
        List<MatHang> mathangs = mathangService.getAllmatHang_on_Donhang(move);
        for (MatHang mathang : mathangs) {
            defaultTableModel_3.addRow(new Object[]{i, mathang.getTenMH(), "VNĐ", 
                mathang.getSoLuong(), mathang.getGiaBan(), mathang.getSoLuong() * mathang.getGiaBan()});
            tongTien += mathang.getSoLuong() * mathang.getGiaBan();
            i++;
        }

//        set thông tin khách hàng
        khachHang = khachhangService.getKhachHang(move);
        labelHOTenKH.setText("Họ và tên khách hàng: ");
        labelHOTenKH.setText(labelHOTenKH.getText() + " " + khachHang.getHoTen() + ".................................................................");
        labelSDTKH.setText("Số điện thoại: ");
        labelSDTKH.setText(labelSDTKH.getText() + " " + khachHang.getSoDienThoai() + ".................................................................");
        labelDCKH.setText("Địa chỉ giao hàng: ");
        labelDCKH.setText(labelDCKH.getText() + " " + khachHang.getDiaChi() + "........................................................................");
//     Set tổng tiền và phiVC
        
      
        labelPhiVC.setText("Phí VC: ");
        labelPhiVC.setText(labelPhiVC.getText() + formatElement.numberMoney(phiVC));
        lableTongTien.setText("Tổng tiền: ");
        lableTongTien.setText(lableTongTien.getText() + formatElement.numberMoney(tongTienHD));

    }

    private void initTableSale(int a) {
        int i = 1;
        int sale = 0;
        DefaultTableModel defaultTableModel_4 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };

        JTableHeader header = tableSale.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));
//        header.setBackground(Color.red);

//        DefaultTableCellRenderer header_reder = new DefaultTableCellRenderer();
////        header_reder.setFont(new Font("Tahoma", Font.BOLD, 40));
//        header_reder.setBackground(Color.red);
//        
//        tableSale.getTableHeader().setDefaultRenderer(header_reder);
        tableSale.setModel(defaultTableModel_4);
//        tableSale.setOpaque(true);
//        tableSale.setMinimumSize(300);
        defaultTableModel_4.addColumn("STT");
        defaultTableModel_4.addColumn("Mã sản phẩm");
        defaultTableModel_4.addColumn("Tên sản phẩm");
        defaultTableModel_4.addColumn("Số lượng");
        defaultTableModel_4.addColumn("Trạng thái");
        defaultTableModel_4.addColumn("Giá nhập");
        defaultTableModel_4.addColumn("Giá bán");
        defaultTableModel_4.addColumn("Lợi nhuận");
        defaultTableModel_4.addColumn("Chú thích");
          
          
//          định dạng căn chỉnh 
            
            tableSale.getColumnModel().getColumn(0).setPreferredWidth(50);
            formatElement.formatTable(0,JLabel.CENTER,tableSale);
            tableSale.getColumnModel().getColumn(1).setPreferredWidth(120);
            tableSale.getColumnModel().getColumn(2).setPreferredWidth(350);
            tableSale.getColumnModel().getColumn(3).setPreferredWidth(90);
           formatElement.formatTable(3,JLabel.CENTER,tableSale);
            tableSale.getColumnModel().getColumn(4).setPreferredWidth(110);
            tableSale.getColumnModel().getColumn(5).setPreferredWidth(120);
            formatElement.formatTable(5,JLabel.RIGHT,tableSale);
            tableSale.getColumnModel().getColumn(6).setPreferredWidth(100);
            formatElement.formatTable(6,JLabel.RIGHT,tableSale);
            tableSale.getColumnModel().getColumn(7).setPreferredWidth(100);
            formatElement.formatTable(7,JLabel.RIGHT,tableSale);
            
             tableSale.getColumnModel().getColumn(8).setMinWidth(170);
             tableSale.getColumnModel().getColumn(8).setMaxWidth(350);

//        tableSale.getTableHeader().setOpaque(false);
//        tableSale.getTableHeader().setBackground(Color.YELLOW);
//         tableSale.getTableHeader().setForeground(Color.YELLOW);
//        JTableHeader tableHeader = tableSale.getTableHeader();
////      
////         tableHeader.setBorder();
//        tableHeader.setBackground(Color.RED);
//         tableHeader.setOpaque(false);
List<MatHang> mathangs = null;
switch(a){
    case 1: 
        //      show: data with tình trạng đơn hàng thành công
         mathangs = mathangService.getAllMatHangSale(1);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
                mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney( mathang.getGiaBan()), 
                formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
    case 2:
        //        show: data with tình trạng đơn hàng đang giao
        mathangs = mathangService.getAllMatHangSale(2);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
        }
        break;
    case 3:
        //        show: data with tình trạng đơn hàng đã hủy
        mathangs = mathangService.getAllMatHangSale(3);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
        }
        break;
         //        show: doanh thu lớn nhất => sắp xếp lớn => bé
        case 4:
       
        mathangs = mathangService.getAllMatHangSale2(1, 1, 0);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
        case 5:
      //        show: doanh thu nhỏ nhất => sắp xếp bé => lớn
        mathangs = mathangService.getAllMatHangSale2(1, 0, 0);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
        case 6:
      //        show: số lượng lớn nhất
       mathangs = mathangService.getAllMatHangSale2(1, 1,1);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
        case 7:
         //        show: số lượng nhỏ nhất
              mathangs = mathangService.getAllMatHangSale2(1, 0,1);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
        case 8:
        //        show: tồn kho lớn nhất
        mathangs = mathangService.getAllMatHangSale(3);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
        }
        break;
        case 9:
        //        show: tồn kho nhỏ nhất
        mathangs = mathangService.getAllMatHangSale(3);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
            mathang.getSoLuong(), mathang.getNhaSX(), formatElement.numberMoney(mathang.getGiaNhap()),
            formatElement.numberMoney( mathang.getGiaBan()),
            formatElement.numberMoney((mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()) });
            i++;
        }
        break;
        
        
}


        

//        show: data with tình trạng đơn hàng đang giao
//       in ra tổng doanh thu
        labelSale.setText("Tổng tiền: ");
        labelSale.setText(labelSale.getText() + formatElement.numberMoney(sale) + " VNĐ");
    }
    
    
    private void initTonKho(int a){
        int i =1;
        DefaultTableModel defaultTableModel_5 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        
         JTableHeader header = tableSale.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));
        tableSale.setModel(defaultTableModel_5);
        
        
        defaultTableModel_5.addColumn("STT");
        defaultTableModel_5.addColumn("Mã sản phẩm");
        defaultTableModel_5.addColumn("Tên sản phẩm");
        defaultTableModel_5.addColumn("tồn kho");
        defaultTableModel_5.addColumn("Giá nhập");
        defaultTableModel_5.addColumn("Giá bán");
        defaultTableModel_5.addColumn("Ngày tạo");
        defaultTableModel_5.addColumn("Chú thích");
          
          
//          định dạng căn chỉnh 
            
            tableSale.getColumnModel().getColumn(0).setPreferredWidth(50);
            formatElement.formatTable(0,JLabel.CENTER,tableSale);
            tableSale.getColumnModel().getColumn(1).setPreferredWidth(120);
            tableSale.getColumnModel().getColumn(2).setPreferredWidth(350);
            tableSale.getColumnModel().getColumn(3).setPreferredWidth(90);
           formatElement.formatTable(3,JLabel.CENTER,tableSale);
            tableSale.getColumnModel().getColumn(4).setPreferredWidth(110);
            formatElement.formatTable(4,JLabel.RIGHT,tableSale);
            tableSale.getColumnModel().getColumn(5).setPreferredWidth(120);
            formatElement.formatTable(5,JLabel.RIGHT,tableSale);
            tableSale.getColumnModel().getColumn(6).setPreferredWidth(100);
            formatElement.formatTable(6,JLabel.RIGHT,tableSale);
             tableSale.getColumnModel().getColumn(7).setMinWidth(170);
             tableSale.getColumnModel().getColumn(7).setMaxWidth(350);
            
  
             
             
            List<MatHang> mathangs = null;
switch(a){
    case 1: 
        //      show: tăng
         mathangs = mathangService.getMatHang_TonKho(0);
        for (MatHang mathang : mathangs) {
            defaultTableModel_5.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
                mathang.getSoLuong(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney( mathang.getGiaBan()), mathang.getNgayTao() });
            i++;
//            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        break;
    case 2:
        //        show: giảm
        mathangs = mathangService.getMatHang_TonKho(1);
        for (MatHang mathang : mathangs) {
           defaultTableModel_5.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(),
                mathang.getSoLuong(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney(mathang.getGiaBan()), mathang.getNgayTao() });
            i++;
        }
        break;
}
labelSale.setText("");
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        card = new javax.swing.JPanel();
        jpanelSP = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablMathang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtGiaBan = new javax.swing.JTextField();
        txtMaSp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        comBoLoaiSP = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txtTonKho = new javax.swing.JFormattedTextField();
        txtNhaSX = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        imgSanPham = new javax.swing.JLabel();
        btnChonAnh = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnXoaSp = new javax.swing.JButton();
        btnChinhSuaSP = new javax.swing.JButton();
        btnTimKiemSP = new javax.swing.JButton();
        txtTimKiemSP = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jpanelHD = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableHD = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel32 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel42 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        labelHOTenKH = new javax.swing.JLabel();
        labelDCKH = new javax.swing.JLabel();
        labelSDTKH = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel45 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableHoaDonXuat = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        lableTongTien = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        labelPhiVC = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jpanelKhachHang = new javax.swing.JPanel();
        jpanelNhanVien = new javax.swing.JPanel();
        jpanelDoiTac = new javax.swing.JPanel();
        jpanelDoanhThu = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jcombTTDonHang = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        jCombMatHang = new javax.swing.JComboBox<>();
        timeSystemBD = new javax.swing.JLabel();
        txtTonKho_sale = new javax.swing.JLabel();
        jcombTonKho = new javax.swing.JComboBox<>();
        calendarTK = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableSale = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        labelSale = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jPanel29 = new javax.swing.JPanel();
        jpanelTaiKhoan = new javax.swing.JPanel();
        jpanelAbout = new javax.swing.JPanel();
        jpanelTrangChu = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));

        jPanel16.setBackground(new java.awt.Color(51, 204, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit_property_24px.png"))); // NOI18N
        jLabel12.setText("   Sản phẩm");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home_24px.png"))); // NOI18N
        jLabel13.setText("  Trang chủ");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/google_forms_24px.png"))); // NOI18N
        jLabel14.setText("   Hóa đơn");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/account_24px.png"))); // NOI18N
        jLabel15.setText("   Doanh thu");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_user_group_woman_man_24px.png"))); // NOI18N
        jLabel19.setText("   Nhân viên");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_user_group_woman_man_24px.png"))); // NOI18N
        jLabel20.setText("   Khách hàng");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sort_window_24px.png"))); // NOI18N
        jLabel21.setText("   Đối tác");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/google_maps_old_24px.png"))); // NOI18N
        jLabel22.setText("   Giới thiệu");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/categorize_24px.png"))); // NOI18N
        jLabel80.setText("   Tài khoản");
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.LINE_START);

        card.setBackground(new java.awt.Color(136, 189, 188));
        card.setLayout(new java.awt.CardLayout());

        jpanelSP.setBackground(new java.awt.Color(255, 255, 255));
        jpanelSP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(300, 69));

        jPanel1.setBackground(new java.awt.Color(136, 189, 188));

        jPanel4.setBackground(new java.awt.Color(136, 189, 188));

        jScrollPane1.setBackground(new java.awt.Color(136, 189, 188));
        jScrollPane1.setOpaque(false);

        tablMathang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablMathang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tablMathang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablMathang.setGridColor(new java.awt.Color(255, 255, 255));
        tablMathang.setOpaque(false);
        tablMathang.setSelectionBackground(new java.awt.Color(37, 78, 88));
        tablMathang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablMathangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablMathang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        txtGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtMaSp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Loại sản phẩm");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        txtGiaNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Giá bán");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Giá nhập");

        txtTenSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        comBoLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Mã sản phẩm");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaSp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comBoLoaiSP, javax.swing.GroupLayout.Alignment.TRAILING, 0, 409, Short.MAX_VALUE)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(txtMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comBoLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("     Nhà sản xuất");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("     Tồn kho");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("      Chú thích");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        txtTonKho.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTonKho.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtNhaSX.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNhaSX)
                    .addComponent(txtTonKho)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNhaSX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122))))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(0, 0, 0)));

        imgSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nike.jpg"))); // NOI18N

        btnChonAnh.setBackground(new java.awt.Color(51, 51, 51));
        btnChonAnh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChonAnh.setForeground(new java.awt.Color(255, 255, 255));
        btnChonAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/group_message_26px.png"))); // NOI18N
        btnChonAnh.setText("   Chọn ảnh");
        btnChonAnh.setBorder(null);
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        btnThemSP.setBackground(new java.awt.Color(51, 51, 51));
        btnThemSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shopping_cart_24px.png"))); // NOI18N
        btnThemSP.setText("   Thêm sản phẩm");
        btnThemSP.setBorder(null);
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnXoaSp.setBackground(new java.awt.Color(51, 51, 51));
        btnXoaSp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaSp.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bell_26px.png"))); // NOI18N
        btnXoaSp.setText("   Xóa Sản Phẩm");
        btnXoaSp.setBorder(null);
        btnXoaSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSpActionPerformed(evt);
            }
        });

        btnChinhSuaSP.setBackground(new java.awt.Color(51, 51, 51));
        btnChinhSuaSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChinhSuaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnChinhSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit_property_24px.png"))); // NOI18N
        btnChinhSuaSP.setText("    Chỉnh sửa");
        btnChinhSuaSP.setBorder(null);
        btnChinhSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChinhSuaSPActionPerformed(evt);
            }
        });

        btnTimKiemSP.setBackground(new java.awt.Color(51, 51, 51));
        btnTimKiemSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search_26px.png"))); // NOI18N
        btnTimKiemSP.setText("   Tìm kiếm");
        btnTimKiemSP.setBorder(null);
        btnTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSPActionPerformed(evt);
            }
        });

        txtTimKiemSP.setBackground(new java.awt.Color(51, 51, 51));
        txtTimKiemSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTimKiemSP.setForeground(new java.awt.Color(255, 255, 255));
        txtTimKiemSP.setBorder(null);

        btnReset.setBackground(new java.awt.Color(51, 51, 51));
        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sort_window_24px.png"))); // NOI18N
        btnReset.setText("   Làm mới");
        btnReset.setBorder(null);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChinhSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemSP)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChinhSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel37.setBackground(new java.awt.Color(17, 45, 50));
        jPanel37.setForeground(new java.awt.Color(255, 255, 255));

        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster_1.png"))); // NOI18N

        jLabel106.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Công ti thời trang Hine");

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(255, 255, 255));
        jLabel107.setText("Home");

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 255, 255));
        jLabel108.setText("About");
        jLabel108.setMaximumSize(new java.awt.Dimension(42, 17));
        jLabel108.setMinimumSize(new java.awt.Dimension(42, 17));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setText("Events");

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setText("foundryX");

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setText("News");

        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facebook.png"))); // NOI18N

        jLabel113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instagram.png"))); // NOI18N

        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter-icon.png"))); // NOI18N

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setText("Services");

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText("Static");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel112)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel113)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(61, 61, 61))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator12)
                        .addContainerGap())))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel105)
                    .addComponent(jLabel112)
                    .addComponent(jLabel113)
                    .addComponent(jLabel114))
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel106)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1338, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Loại Sản Phẩm", jPanel2);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1338, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Phiếu Nhập Hàng", jPanel15);

        javax.swing.GroupLayout jpanelSPLayout = new javax.swing.GroupLayout(jpanelSP);
        jpanelSP.setLayout(jpanelSPLayout);
        jpanelSPLayout.setHorizontalGroup(
            jpanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSPLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelSPLayout.setVerticalGroup(
            jpanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSPLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        card.add(jpanelSP, "c1");

        jpanelHD.setBackground(new java.awt.Color(255, 255, 255));
        jpanelHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpanelHD.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane2.setBackground(new java.awt.Color(136, 189, 188));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(136, 189, 188));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Đơn hàng  ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(110, 102, 88))); // NOI18N

        tableHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableHD);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel31.setBackground(new java.awt.Color(17, 45, 50));
        jPanel31.setForeground(new java.awt.Color(255, 255, 255));

        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster_1.png"))); // NOI18N

        jLabel94.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("Công ti thời trang Hine");

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Home");

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("About");
        jLabel96.setMaximumSize(new java.awt.Dimension(42, 17));
        jLabel96.setMinimumSize(new java.awt.Dimension(42, 17));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Events");

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("foundryX");

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("News");

        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facebook.png"))); // NOI18N

        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instagram.png"))); // NOI18N

        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter-icon.png"))); // NOI18N

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText("Services");

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Static");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel100)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel101)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator11)))
                .addGap(61, 61, 61))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel101)
                    .addComponent(jLabel102)
                    .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel100)
                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel93))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel32.setBackground(new java.awt.Color(94, 57, 41));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shopping-cart-accept-icon.png"))); // NOI18N
        jButton1.setText("Đặt hàng");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/newHine_1.png"))); // NOI18N

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Pen-4-icon (1).png"))); // NOI18N
        jButton3.setText("Chỉnh sửa");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Xu hướng thời trang HINE");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Quá khứ - hiện tại -tương lai");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Phiếu mua hàng", jPanel6);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1339, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Chi tiết đơn mua", jPanel18);

        jPanel5.setBackground(new java.awt.Color(136, 189, 188));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Mẫu số: C1020012VN");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Kí hiệu: AA/3P");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Số: 000100001");

        jLabel41.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("HÓA ĐƠN BÁN HÀNG");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addGap(47, 47, 47))
        );

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster.png"))); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("FASHION HINE");

        jLabel38.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel38.setText("ĐT: 0920100299-0921099201");

        jLabel39.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel39.setText("Email: Fashion.Hine@gmail.com");

        jLabel40.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel40.setText("Website: www.fashionHine.vn");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel33))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel34)))
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        labelHOTenKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelHOTenKH.setText("Họ tên khách hàng: ");

        labelDCKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelDCKH.setText("Địa chỉ: ");

        labelSDTKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelSDTKH.setText("Điện thoại");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator5))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDCKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(labelHOTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHOTenKH)
                    .addComponent(labelSDTKH))
                .addGap(18, 18, 18)
                .addComponent(labelDCKH)
                .addContainerGap())
        );

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        tableHoaDonXuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tableHoaDonXuat);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        lableTongTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lableTongTien.setForeground(new java.awt.Color(255, 0, 51));
        lableTongTien.setText("Tổng tiền(bằng số): ");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Người mua hàng");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Kỹ rõ họ tên");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Ngày...tháng...năm");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Người bán hàng");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Kỹ rõ họ tên");

        labelPhiVC.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelPhiVC.setForeground(new java.awt.Color(255, 0, 0));
        labelPhiVC.setText("Phí VC:");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(labelPhiVC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lableTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lableTongTien)
                    .addComponent(jLabel30)
                    .addComponent(labelPhiVC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)))
        );

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(17, 45, 50));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster_1.png"))); // NOI18N

        jLabel55.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Công ti thời trang Hine");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Home");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("About");
        jLabel57.setMaximumSize(new java.awt.Dimension(42, 17));
        jLabel57.setMinimumSize(new java.awt.Dimension(42, 17));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Events");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("foundryX");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("News");

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facebook.png"))); // NOI18N

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instagram.png"))); // NOI18N

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter-icon.png"))); // NOI18N

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Services");

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Static");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel61)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel62)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator8)))
                .addGap(61, 61, 61))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel61)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel54))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel14.setBackground(new java.awt.Color(136, 189, 188));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/formHoaDoncl.png"))); // NOI18N

        jButton2.setBackground(new java.awt.Color(94, 57, 41));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/google_forms_24px.png"))); // NOI18N
        jButton2.setText("Xuất hóa đơn");
        jButton2.setBorder(null);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel5);

        javax.swing.GroupLayout jpanelHDLayout = new javax.swing.GroupLayout(jpanelHD);
        jpanelHD.setLayout(jpanelHDLayout);
        jpanelHDLayout.setHorizontalGroup(
            jpanelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jpanelHDLayout.setVerticalGroup(
            jpanelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        card.add(jpanelHD, "c2");

        javax.swing.GroupLayout jpanelKhachHangLayout = new javax.swing.GroupLayout(jpanelKhachHang);
        jpanelKhachHang.setLayout(jpanelKhachHangLayout);
        jpanelKhachHangLayout.setHorizontalGroup(
            jpanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1359, Short.MAX_VALUE)
        );
        jpanelKhachHangLayout.setVerticalGroup(
            jpanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        card.add(jpanelKhachHang, "card4");

        javax.swing.GroupLayout jpanelNhanVienLayout = new javax.swing.GroupLayout(jpanelNhanVien);
        jpanelNhanVien.setLayout(jpanelNhanVienLayout);
        jpanelNhanVienLayout.setHorizontalGroup(
            jpanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1359, Short.MAX_VALUE)
        );
        jpanelNhanVienLayout.setVerticalGroup(
            jpanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        card.add(jpanelNhanVien, "card5");

        javax.swing.GroupLayout jpanelDoiTacLayout = new javax.swing.GroupLayout(jpanelDoiTac);
        jpanelDoiTac.setLayout(jpanelDoiTacLayout);
        jpanelDoiTacLayout.setHorizontalGroup(
            jpanelDoiTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1359, Short.MAX_VALUE)
        );
        jpanelDoiTacLayout.setVerticalGroup(
            jpanelDoiTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        card.add(jpanelDoiTac, "card6");

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(136, 189, 188));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setText("Tình trạng đơn hàng");

        jcombTTDonHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jcombTTDonHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Đã giao", "2. Đang vận chuyển", "3. Đã hủy" }));
        jcombTTDonHang.setBorder(null);
        jcombTTDonHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombTTDonHangActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setText("Mặt hàng");

        jCombMatHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCombMatHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Doan thu lớn nhất", "2. Doanh thu thấp nhất", "3. Số lượng lớn nhất", "4. Số lượng thấp nhất" }));
        jCombMatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombMatHangActionPerformed(evt);
            }
        });

        timeSystemBD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        timeSystemBD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeSystemBD.setText("Thời gian");

        txtTonKho_sale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTonKho_sale.setText("Tồn kho");

        jcombTonKho.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jcombTonKho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Lớn nhất", "2. Thấp nhất" }));
        jcombTonKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombTonKhoActionPerformed(evt);
            }
        });

        calendarTK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        calendarTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Thời gian");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcombTTDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCombMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTonKho_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcombTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeSystemBD, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(calendarTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jLabel79)
                    .addComponent(timeSystemBD)
                    .addComponent(txtTonKho_sale))
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jcombTonKho, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jcombTTDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCombMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(calendarTK, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("CÔNG TI FASHION HINE");

        jLabel35.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Số 32, Ngõ 10, Phố Ngọc Khách, Phường Giảng Võ,Quận Ba Đình, Hà Nội      ");

        jLabel36.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("ngày... tháng... năm      ");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Báo cáo doanh thu theo mặt hàng");

        tableSale.setBackground(new java.awt.Color(204, 255, 153));
        tableSale.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableSale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableSale.setGridColor(new java.awt.Color(255, 102, 51));
        tableSale.setOpaque(false);
        tableSale.setSelectionBackground(new java.awt.Color(255, 0, 153));
        tableSale.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableSale.setShowGrid(true);
        jScrollPane7.setViewportView(tableSale);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1209, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        labelSale.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelSale.setForeground(new java.awt.Color(255, 51, 51));
        labelSale.setText("Tổng doanh thu: ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 1273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelSale, javax.swing.GroupLayout.PREFERRED_SIZE, 1268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel35))
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel27))
                .addGap(43, 43, 43)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSale)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(17, 45, 50));
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster_1.png"))); // NOI18N

        jLabel43.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Công ti thời trang Hine");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Home");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("About");
        jLabel45.setMaximumSize(new java.awt.Dimension(42, 17));
        jLabel45.setMinimumSize(new java.awt.Dimension(42, 17));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Events");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("foundryX");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("News");

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facebook.png"))); // NOI18N

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instagram.png"))); // NOI18N

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter-icon.png"))); // NOI18N

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Services");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Static");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel49)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator7)))
                .addGap(61, 61, 61))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 1336, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Doanh thu", jPanel7);

        jPanel24.setBackground(new java.awt.Color(17, 45, 50));
        jPanel24.setForeground(new java.awt.Color(255, 255, 255));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/horizontal_on_white_by_logaster_1.png"))); // NOI18N

        jLabel67.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Công ti thời trang Hine");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Home");

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("About");
        jLabel69.setMaximumSize(new java.awt.Dimension(42, 17));
        jLabel69.setMinimumSize(new java.awt.Dimension(42, 17));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Events");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("foundryX");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("News");

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facebook.png"))); // NOI18N

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instagram.png"))); // NOI18N

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter-icon.png"))); // NOI18N

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Services");

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Static");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel73)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel74)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator9)))
                .addGap(61, 61, 61))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel74)
                    .addComponent(jLabel75)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel73)
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel66))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel29.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 1324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Biểu đồ", jPanel8);

        javax.swing.GroupLayout jpanelDoanhThuLayout = new javax.swing.GroupLayout(jpanelDoanhThu);
        jpanelDoanhThu.setLayout(jpanelDoanhThuLayout);
        jpanelDoanhThuLayout.setHorizontalGroup(
            jpanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        jpanelDoanhThuLayout.setVerticalGroup(
            jpanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        card.add(jpanelDoanhThu, "card7");

        javax.swing.GroupLayout jpanelTaiKhoanLayout = new javax.swing.GroupLayout(jpanelTaiKhoan);
        jpanelTaiKhoan.setLayout(jpanelTaiKhoanLayout);
        jpanelTaiKhoanLayout.setHorizontalGroup(
            jpanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1359, Short.MAX_VALUE)
        );
        jpanelTaiKhoanLayout.setVerticalGroup(
            jpanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        card.add(jpanelTaiKhoan, "card8");

        javax.swing.GroupLayout jpanelAboutLayout = new javax.swing.GroupLayout(jpanelAbout);
        jpanelAbout.setLayout(jpanelAboutLayout);
        jpanelAboutLayout.setHorizontalGroup(
            jpanelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1359, Short.MAX_VALUE)
        );
        jpanelAboutLayout.setVerticalGroup(
            jpanelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        card.add(jpanelAbout, "card9");

        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Black Simple Dark Fashion Retail Website (2).png"))); // NOI18N
        jLabel81.setText("jLabel81");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1.png"))); // NOI18N
        jLabel11.setText("jLabel11");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanelTrangChuLayout = new javax.swing.GroupLayout(jpanelTrangChu);
        jpanelTrangChu.setLayout(jpanelTrangChuLayout);
        jpanelTrangChuLayout.setHorizontalGroup(
            jpanelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1351, Short.MAX_VALUE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanelTrangChuLayout.setVerticalGroup(
            jpanelTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel81)
                .addGap(52, 52, 52))
        );

        card.add(jpanelTrangChu, "c10");

        jPanel16.add(card, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1532, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtGiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNhapActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChoose = new JFileChooser();
        try {

//            chỉ cho phép sd  file img
            FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("hình ảnh", "jpg", "png", "jfif");
            fileChoose.setFileFilter(imgFilter);
            fileChoose.setMultiSelectionEnabled(false);
            //            hiện thị hoopf thoại mở file
            int result = fileChoose.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
//                chọn 1 file 1 thời điểm
                File f = fileChoose.getSelectedFile();

                ImageIcon icon = new ImageIcon(f.getPath());
                Image img = ImageHelper.resize(icon.getImage(), 200, 200);
                ImageIcon resizeIcon = new ImageIcon(img);
                imgSanPham.setIcon(resizeIcon);

                productImage = ImageHelper.toByteArray(img, "jpg");

            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnChonAnhActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        int i =1;
        mathang.setMaMH(txtMaSp.getText());
        mathang.setTenMH(txtTenSP.getText());
        mathang.setSoLuong(Integer.valueOf(txtTonKho.getText()));
        mathang.setLoai(Integer.valueOf(comBoLoaiSP.getSelectedItem().toString()));
        mathang.setGiaNhap(Integer.valueOf(txtGiaNhap.getText()));
        mathang.setGiaBan(Integer.valueOf(txtGiaBan.getText()));
        mathang.setNhaSX(txtNhaSX.getText());
        mathang.setNgayTao(Date.from(ngayGio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        mathang.setHinhAnh(productImage);

        mathangService.addSanPham(mathang);

        //        xóa hết dữ liệu hiện tại
        defaultTableModel.setRowCount(0);
        List<MatHang> mathangs = mathangService.getAllSanPham();

        for (MatHang mathang : mathangs) {
            defaultTableModel.addRow(new Object[]{ i, mathang.getMaMH(), 
                mathang.getTenMH(), mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney(mathang.getGiaBan()), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
            i++;
        }
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void tablMathangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablMathangMouseClicked
        // TODO add your handling code here:
        int row = tablMathang.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {

//                row và colum(0=id);
            String maMH = String.valueOf(tablMathang.getValueAt(row, 1));
            mathang = mathangService.getMaMH(maMH);
            txtMaSp.setText(mathang.getMaMH());
            txtTenSP.setText(mathang.getTenMH());
            txtTonKho.setText(String.valueOf(mathang.getSoLuong()));
            comBoLoaiSP.setSelectedIndex(mathang.getLoai() - 1);
            txtGiaNhap.setText(String.valueOf(mathang.getGiaNhap()));
            txtGiaBan.setText(String.valueOf(mathang.getGiaBan()));
            txtNhaSX.setText(mathang.getNhaSX());
            productImage = mathang.getHinhAnh();
            System.out.println("if " + productImage);
            if (productImage != null) {
                try {
                    Image img = ImageHelper.createImageByteArray(mathang.getHinhAnh(), "jpg");
                    imgSanPham.setIcon(new ImageIcon(img));
//                    lưu lại byte[] hình ảnh
                    productImage = mathang.getHinhAnh();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
//                nếu chưa có hình ảnh để hiện thị thì mình hiện thị 1 hình ảnh bất kì
//                    lưu lại byte[] hình ảnh
                productImage = mathang.getHinhAnh();
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/nike.jpg"));
                imgSanPham.setIcon(icon);

            }
        }
    }//GEN-LAST:event_tablMathangMouseClicked

    private void btnXoaSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSpActionPerformed
        // TODO add your handling code here:
        int i =1;
        int row = tablMathang.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            int confim = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa không");
            if (confim == JOptionPane.YES_OPTION) {
                String maMH = String.valueOf(tablMathang.getValueAt(row, 1));
                mathangService.deleteMatHang(maMH);
                //        xóa hết dữ liệu hiện tại
                defaultTableModel.setRowCount(0);
                List<MatHang> mathangs = mathangService.getAllSanPham();

                for (MatHang mathang : mathangs) {
                    defaultTableModel.addRow(new Object[]{i, mathang.getMaMH(),
                        mathang.getTenMH(), mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()), 
                        formatElement.numberMoney(mathang.getGiaBan()), mathang.getNhaSX(),
                        mathang.getSoLuong(), mathang.getNgayTao()});
                    i++;
                }
            }
        }
    }//GEN-LAST:event_btnXoaSpActionPerformed

    private void btnChinhSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChinhSuaSPActionPerformed
        // TODO add your handling code here:
        int i =1;
        int row = tablMathang.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            mathang.setMaMH(txtMaSp.getText());
            mathang.setTenMH(txtTenSP.getText());
            mathang.setSoLuong(Integer.valueOf(txtTonKho.getText()));
            mathang.setLoai(Integer.valueOf(comBoLoaiSP.getSelectedItem().toString()));
            mathang.setGiaNhap(Integer.valueOf(txtGiaNhap.getText()));
            mathang.setGiaBan(Integer.valueOf(txtGiaBan.getText()));
            mathang.setNhaSX(txtNhaSX.getText());
            mathang.setNgayTao(Date.from(ngayGio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            mathang.setHinhAnh(productImage);
            
            
            int confim = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thay đổi không?");
            if (confim == JOptionPane.YES_OPTION) {
                 mathangService.updateMatHang(mathang);
            //        xóa hết dữ liệu hiện tại
            defaultTableModel.setRowCount(0);
            defaultTableModel.setRowCount(0);
            List<MatHang> mathangs = mathangService.getAllSanPham();

            for (MatHang mathang : mathangs) {
                defaultTableModel.addRow(new Object[]{i, mathang.getMaMH(), 
                    mathang.getTenMH(), mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()),
                    formatElement.numberMoney(mathang.getGiaBan()), mathang.getNhaSX(), mathang.getSoLuong(),
                    mathang.getNgayTao()});
                i++;
            }
            }
           

        }
    }//GEN-LAST:event_btnChinhSuaSPActionPerformed

    private void btnTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSPActionPerformed
        // TODO add your handling code here:
        int i =1;
        //        xóa hết dữ liệu hiện tại
        defaultTableModel.setRowCount(0);

//        show dữ liệu lại
        MatHang mathang = mathangService.searchProduct(txtTimKiemSP.getText());

//        for (Book book : books) {
        defaultTableModel.addRow(new Object[]{i, mathang.getMaMH(), 
                    mathang.getTenMH(), mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()),
                    formatElement.numberMoney(mathang.getGiaBan()), mathang.getNhaSX(), mathang.getSoLuong(),
                    mathang.getNgayTao()});


    }//GEN-LAST:event_btnTimKiemSPActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        //        xóa hết dữ liệu hiện tại
        int i =1;
        defaultTableModel.setRowCount(0);
        defaultTableModel.setRowCount(0);
        List<MatHang> mathangs = mathangService.getAllSanPham();

        for (MatHang mathang : mathangs) {
            defaultTableModel.addRow(new Object[]{i, mathang.getMaMH(),
                mathang.getTenMH(), mathang.getLoai(), formatElement.numberMoney(mathang.getGiaNhap()),
                formatElement.numberMoney(mathang.getGiaBan()), mathang.getNhaSX(), mathang.getSoLuong(),
                mathang.getNgayTao()});
            i++;
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void tableHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHDMouseClicked
       
        int row = tableHD.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
//                row và colum(0=id);
             String soHD = String.valueOf(tableHD.getValueAt(row, 0));
             List<DonHang> donhangs = donhangService.getALLDonHang();
             for(DonHang donhang: donhangs){
                 if(donhang.getSoHD() == Integer.valueOf(soHD)){
                     phiVC = donhang.getPhiVC();
                     tongTienHD = donhang.getTongTien() + phiVC;
                 }
             }
            innitTableHoaDonXuat(Integer.valueOf(soHD));
        }

    }//GEN-LAST:event_tableHDMouseClicked

    private void jcombTTDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombTTDonHangActionPerformed
        // TODO add your handling code here:
        if (jcombTTDonHang.getSelectedIndex() == 0) {
            initTableSale(1);
        }
        if (jcombTTDonHang.getSelectedIndex() == 1) {
            initTableSale(2);
        }
        if (jcombTTDonHang.getSelectedIndex() == 2) {
            initTableSale(3);
        }
    }//GEN-LAST:event_jcombTTDonHangActionPerformed

    private void jCombMatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombMatHangActionPerformed
        // TODO add your handling code here:
        if (jCombMatHang.getSelectedIndex() == 0) {
            initTableSale(4);
        }
        if (jCombMatHang.getSelectedIndex() == 1) {
            initTableSale(5);
        }
        if (jCombMatHang.getSelectedIndex() == 2) {
            initTableSale(6);
        }
        if (jCombMatHang.getSelectedIndex() == 3) {
            initTableSale(7);
        }
    }//GEN-LAST:event_jCombMatHangActionPerformed

    private void jcombTonKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombTonKhoActionPerformed
        // TODO add your handling code here:
         if (jcombTonKho.getSelectedIndex() == 0) {
             initTonKho(2);
        }
          if (jcombTonKho.getSelectedIndex() == 1) {
            initTonKho(1);
        }
    }//GEN-LAST:event_jcombTonKhoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         
        innitTableHD();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new PhieuMuaHang().setVisible(true);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
         defaultTableModel.setRowCount(0);
        defaultTableModel.setColumnCount(0);
        initTableSP();
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.first(card);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
      cardLayout.show(card, "c10");
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
         CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.show(card, "c2");
        innitTableHD();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.show(card, "card7");
        formatElement.clock(timeSystemBD,calendarTK);
        initTableSale(1);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel80MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
          CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.show(card, "c2");
        innitTableHD();
    }//GEN-LAST:event_jLabel11MouseClicked
//    public void showPieChart(){
//        
//        //create dataset
//    DefaultPieDataset barDataset = new DefaultPieDataset( );  
//    barDataset.setValue( "IPhone 5s" , 30);  
//      barDataset.setValue( "SamSung Grand" , 20 );   
//      barDataset.setValue( "MotoG" ,  40);    
//      barDataset.setValue( "Nokia Lumia" , 10 );
//      
//      //create chart
//       JFreeChart piechart = ChartFactory.createPieChart("mobile sales",barDataset, false,true,false);//explain
//      
//        PiePlot piePlot =(PiePlot) piechart.getPlot();
//      
//       //changing pie chart blocks colors
//       piePlot.setSectionPaint(0, new Color(255,255,102));
//       piePlot.setSectionPaint(1, new Color(102,255,102));
//       piePlot.setSectionPaint(2, new Color(255,102,153));
//       piePlot.setSectionPaint(3, new Color(0,204,204));
//      
//       
//        piePlot.setBackgroundPaint(Color.white);
//        
//        //create chartPanel to display chart(graph)
//        ChartPanel barChartPanel = new ChartPanel(piechart);
//        jPanel27.removeAll();
//        jPanel27.add(barChartPanel, BorderLayout.CENTER);
//        jPanel27.validate();
//    }
//
//    /*=============================================================================*/
//    
//    public void showLineChart(){
//        //create dataset for the graph
//         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(200, "Amount", "january");
//        dataset.setValue(150, "Amount", "february");
//        dataset.setValue(18, "Amount", "march");
//        dataset.setValue(100, "Amount", "april");
//        dataset.setValue(80, "Amount", "may");
//        dataset.setValue(250, "Amount", "june");
//        
//        //create chart
//        JFreeChart linechart = ChartFactory.createLineChart("contribution","monthly","amount", 
//                dataset, PlotOrientation.VERTICAL, false,true,false);
//        
//        //create plot object
//         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
//       // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
//        lineCategoryPlot.setBackgroundPaint(Color.white);
//        
//        //create render object to change the moficy the line properties like color
//        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
//        Color lineChartColor = new Color(204,0,51);
//        lineRenderer.setSeriesPaint(0, lineChartColor);
//        
//         //create chartPanel to display chart(graph)
//        ChartPanel lineChartPanel = new ChartPanel(linechart);
//        jPanel26.removeAll();
//        jPanel26.add(lineChartPanel, BorderLayout.CENTER);
//        jPanel26.validate();
//    }
//
//    /*========================================================================================*/
//    
//    public void showHistogram(){
//        
//         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
//                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
//                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
//                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
//                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
//                          };
// 
// 
//        HistogramDataset dataset = new HistogramDataset();
//        dataset.addSeries("key", values, 20);
//        
//         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
//            XYPlot plot= chart.getXYPlot();
//        plot.setBackgroundPaint(Color.WHITE);
//
//        
//        
//        ChartPanel barpChartPanel2 = new ChartPanel(chart);
//        jPanel28.removeAll();
//        jPanel28.add(barpChartPanel2, BorderLayout.CENTER);
//        jPanel28.validate();
//    }

    /*========================================================================================*/
    
    public void showBarChart(){
//        int i =0;
//        int[] list = new int[12]; 
//        List<MatHang>  mathangs = mathangService.getAllMatHangSale(1);
//        for (MatHang mathang : mathangs) {
//           list[i] =  mathang.getSoLuong();
//          i++;
//        }
        
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(10, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
         dataset.setValue(200, "Amount", "July ");
        dataset.setValue(150, "Amount", "August ");
        dataset.setValue(18, "Amount", "September ");
        dataset.setValue(100, "Amount", "October ");
        dataset.setValue(80, "Amount", "November ");
        dataset.setValue(250, "Amount", "December ");
        
        JFreeChart chart = ChartFactory.createBarChart("Doanh thu mặt hàng theo các tháng ","Tháng","Số lượng", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
//        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
//        categoryPlot.setBackgroundPaint(Color.WHITE);
//        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
//        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        jPanel29.removeAll();
        jPanel29.add(barpChartPanel, BorderLayout.CENTER);
        jPanel29.validate();
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChinhSuaSP;
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiemSP;
    private javax.swing.JButton btnXoaSp;
    private javax.swing.JLabel calendarTK;
    private javax.swing.JPanel card;
    private javax.swing.JComboBox<String> comBoLoaiSP;
    private javax.swing.JLabel imgSanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jCombMatHang;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> jcombTTDonHang;
    private javax.swing.JComboBox<String> jcombTonKho;
    private javax.swing.JPanel jpanelAbout;
    private javax.swing.JPanel jpanelDoanhThu;
    private javax.swing.JPanel jpanelDoiTac;
    private javax.swing.JPanel jpanelHD;
    private javax.swing.JPanel jpanelKhachHang;
    private javax.swing.JPanel jpanelNhanVien;
    private javax.swing.JPanel jpanelSP;
    private javax.swing.JPanel jpanelTaiKhoan;
    private javax.swing.JPanel jpanelTrangChu;
    private javax.swing.JLabel labelDCKH;
    private javax.swing.JLabel labelHOTenKH;
    private javax.swing.JLabel labelPhiVC;
    private javax.swing.JLabel labelSDTKH;
    private javax.swing.JLabel labelSale;
    private javax.swing.JLabel lableTongTien;
    private javax.swing.JTable tablMathang;
    private javax.swing.JTable tableHD;
    private javax.swing.JTable tableHoaDonXuat;
    private javax.swing.JTable tableSale;
    private javax.swing.JLabel timeSystemBD;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaSp;
    private javax.swing.JTextField txtNhaSX;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiemSP;
    private javax.swing.JFormattedTextField txtTonKho;
    private javax.swing.JLabel txtTonKho_sale;
    // End of variables declaration//GEN-END:variables
}
