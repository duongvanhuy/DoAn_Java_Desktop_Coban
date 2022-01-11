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

/**
 *
 * @author dell
 */
public class Main extends javax.swing.JFrame {

    DefaultTableModel defaultTableModel;

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
    byte[] productImage;
    CardLayout cardLayout;
    HoaDonMain hoaDonMain;

    /**
     * Creates new form Main
     */
    public Main() {

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

        defaultTableModel = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        initTableSP();

    }

    private void initTableSP() {

//        set suex liệu cho table
        tablMathang.setModel(defaultTableModel);

        defaultTableModel.addColumn("Mã sản phẩm");
        defaultTableModel.addColumn("Tên sản phẩm");
        defaultTableModel.addColumn("Loại sản phẩm");
        defaultTableModel.addColumn("Giá nhập");
        defaultTableModel.addColumn("Giá bán");
        defaultTableModel.addColumn("Nhà sản xuất");
        defaultTableModel.addColumn("Lượng tồn kho");
        defaultTableModel.addColumn("Ngày tạo");
        JTableHeader header = tablMathang.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));

//        show dữ liệu ra bẳng
        List<MatHang> matHangs = mathangService.getAllSanPham();
        for (MatHang mathang : matHangs) {
            defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
        }
    }

    private void innitTableHD() {

        DefaultTableModel defaultTableModel_1 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
//        set suex liệu cho table
        tableHD.setModel(defaultTableModel_1);

        defaultTableModel_1.addColumn("Mã đơn hàng");
        defaultTableModel_1.addColumn("Khách hàng");
        defaultTableModel_1.addColumn("Nhân viên");
        defaultTableModel_1.addColumn("Thanh toán");
        defaultTableModel_1.addColumn("Tổng số lượng");
        defaultTableModel_1.addColumn("Tổng tiền");
        defaultTableModel_1.addColumn("Ngày đặt");
        JTableHeader header = tableHD.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));

//        show dữ liệu ra bẳng
        List<DonHang> donhangs = donhangService.getALLDonHang();
        for (DonHang donhang : donhangs) {
            defaultTableModel_1.addRow(new Object[]{donhang.getSoHD(), donhang.getKhachHang(), donhang.getNhanVien(), donhang.getLoaiThanhToan(), donhang.getSoLuong(), donhang.getTongTien(), donhang.getNgayDat()});
        }
    }

    private void innitTableCTHoaDon(int maHD) {
//    defaultTableModel.setRowCount(0);
//    defaultTableModel.setColumnCount(0);
        DefaultTableModel defaultTableModel_2 = new DefaultTableModel() {
//            không cho phép người dùng sửa dữ liệu trực tiếp trên bẳng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };

//        set suex liệu cho table
        tableCTHoaDon.setModel(defaultTableModel_2);

        defaultTableModel_2.addColumn("Mã đơn hàng");
        defaultTableModel_2.addColumn("Mặt hàng");
        defaultTableModel_2.addColumn("Phí Vận chuyển");
        defaultTableModel_2.addColumn("Trạng thái");
        defaultTableModel_2.addColumn("Tổng tiền");
        JTableHeader header = tableCTHoaDon.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 16));

//        show dữ liệu ra bẳng
        hoadon = hoadonService.getTTHoaDon(maHD);
        defaultTableModel_2.addRow(new Object[]{hoadon.getSoDH(), hoadon.getMatHang(), hoadon.getPhiVC(), hoadon.getTrangThai(), hoadon.getTongTien()});
        phiVC = hoadon.getPhiVC();
        tongTienHD = hoadon.getTongTien();
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
            defaultTableModel_3.addRow(new Object[]{i, mathang.getTenMH(), "VNĐ", mathang.getSoLuong(), mathang.getGiaBan(), mathang.getSoLuong() * mathang.getGiaBan()});
            tongTien += mathang.getSoLuong() * mathang.getGiaBan();
            i++;
        }

//        set thông tin khách hàng
        khachHang = khachhangService.getKhachHang(move);
        labelHOTenKH.setText("Họ và tên khách hàng: ");
        labelHOTenKH.setText(labelHOTenKH.getText() + " " + khachHang.getHoTen() + ".................................................................");
        labelSDTKH.setText("Số điện thoại: ");
        labelSDTKH.setText(labelSDTKH.getText() + " " + khachHang.getSoDienThoai() + ".................................................................");
        labelDCKH.setText("Địa chỉ giao hàng");
        labelDCKH.setText(labelDCKH.getText() + " " + khachHang.getDiaChi() + "........................................................................");
//     Set tổng tiền và phiVC
        labelPhiVC.setText("Phí VC: ");
        labelPhiVC.setText(labelPhiVC.getText() + dinhDangNumbeer(phiVC));
        lableTongTien.setText("Tổng tiền: ");
        lableTongTien.setText(lableTongTien.getText() + dinhDangNumbeer(tongTienHD) + " VNĐ");

    }

    private void initTableSale() {
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

        defaultTableModel_4.addColumn("Số TT");
        defaultTableModel_4.addColumn("Mã sản phẩm");
        defaultTableModel_4.addColumn("Tên sản phẩm");
//        defaultTableModel_4.addColumn("Đơn vị tính");
        defaultTableModel_4.addColumn("Số lượng bán");
        defaultTableModel_4.addColumn("Trạng thái");
        defaultTableModel_4.addColumn("Giá nhập");
        defaultTableModel_4.addColumn("Giá bán");
        defaultTableModel_4.addColumn("Lợi nhuận");

//        tableSale.getTableHeader().setOpaque(false);
//        tableSale.getTableHeader().setBackground(Color.YELLOW);
//         tableSale.getTableHeader().setForeground(Color.YELLOW);
//        JTableHeader tableHeader = tableSale.getTableHeader();
////      
////         tableHeader.setBorder();
//        tableHeader.setBackground(Color.RED);
//         tableHeader.setOpaque(false);
//        show dữ liệu ra bẳng
        List<MatHang> mathangs = mathangService.getAllMatHangSale(1);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(), mathang.getSoLuong(), mathang.getNhaSX(), mathang.getGiaNhap(), mathang.getGiaBan(), (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong()});
            i++;
            sale += (mathang.getGiaBan() - mathang.getGiaNhap()) * mathang.getSoLuong();
        }
        mathangs = mathangService.getAllMatHangSale(3);
        for (MatHang mathang : mathangs) {
            defaultTableModel_4.addRow(new Object[]{i, mathang.getMaMH(), mathang.getTenMH(), mathang.getSoLuong(), mathang.getNhaSX(), mathang.getGiaNhap(), mathang.getGiaBan(), 0});
            i++;
        }
//        labelSale
        labelSale.setText("Tổng tiền: ");
        labelSale.setText(labelSale.getText() + dinhDangNumbeer(sale) + " VNĐ");
    }

    private String dinhDangNumbeer(int number) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(number);

        return str1;
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
        btnHoaDon = new javax.swing.JButton();
        btnSanPham = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnNhanvien = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnDoanhThu = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        btnDoiTac = new javax.swing.JButton();
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
        jSeparator1 = new javax.swing.JSeparator();
        jPanel22 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnXoaSp = new javax.swing.JButton();
        btnChinhSuaSP = new javax.swing.JButton();
        btnTimKiemSP = new javax.swing.JButton();
        txtTimKiemSP = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jpanelHD = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableHD = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCTHoaDon = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMaDH = new javax.swing.JFormattedTextField();
        txtTongSL = new javax.swing.JTextField();
        txtKhachHang = new javax.swing.JTextField();
        txtNgayLap = new javax.swing.JFormattedTextField();
        txtTongTien = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNhanVien = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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
        jpanelKhachHang = new javax.swing.JPanel();
        jpanelNhanVien = new javax.swing.JPanel();
        jpanelDoiTac = new javax.swing.JPanel();
        jpanelDoanhThu = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
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
        jpanelTaiKhoan = new javax.swing.JPanel();
        jpanelAbout = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));

        jPanel16.setBackground(new java.awt.Color(51, 204, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(79, 74, 65));

        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/product-sales-report-icon.png"))); // NOI18N
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BAG-icon.png"))); // NOI18N
        btnSanPham.setText("Sản Phẩm");
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });

        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/User-Group-icon.png"))); // NOI18N
        btnKhachHang.setText("Khách Hàng");

        btnNhanvien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Customer-service-icon.png"))); // NOI18N
        btnNhanvien.setText("Nhân Viên");

        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user-info-icon.png"))); // NOI18N
        btnTaiKhoan.setText("Tài Khoản");

        btnDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sale-icon.png"))); // NOI18N
        btnDoanhThu.setText("Doanh Thu");
        btnDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoanhThuActionPerformed(evt);
            }
        });

        btnGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actions-help-about-icon.png"))); // NOI18N
        btnGioiThieu.setText("Giới Thiệu");

        btnDoiTac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Ecommerce-Product-icon.png"))); // NOI18N
        btnDoiTac.setText("Đối Tác");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDoiTac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGioiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnHoaDon)
                .addGap(18, 18, 18)
                .addComponent(btnSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnKhachHang)
                .addGap(26, 26, 26)
                .addComponent(btnNhanvien)
                .addGap(26, 26, 26)
                .addComponent(btnDoiTac)
                .addGap(27, 27, 27)
                .addComponent(btnDoanhThu)
                .addGap(18, 18, 18)
                .addComponent(btnTaiKhoan)
                .addGap(29, 29, 29)
                .addComponent(btnGioiThieu)
                .addContainerGap(344, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.LINE_START);

        card.setBackground(new java.awt.Color(136, 189, 188));
        card.setLayout(new java.awt.CardLayout());

        jpanelSP.setBackground(new java.awt.Color(136, 189, 188));
        jpanelSP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTabbedPane1.setBackground(new java.awt.Color(51, 204, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(300, 69));

        jPanel1.setBackground(new java.awt.Color(136, 189, 188));

        jPanel4.setBackground(new java.awt.Color(136, 189, 188));

        jScrollPane1.setBackground(new java.awt.Color(136, 189, 188));
        jScrollPane1.setOpaque(false);

        tablMathang.setBackground(new java.awt.Color(204, 204, 204));
        tablMathang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Loại sản phẩm");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Giá bán");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Giá nhập");

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
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGiaBan)
                    .addComponent(txtGiaNhap)
                    .addComponent(comBoLoaiSP, 0, 460, Short.MAX_VALUE)
                    .addComponent(txtTenSP)
                    .addComponent(txtMaSp))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comBoLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nhà sản xuất");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tồn kho");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Chú thích");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        txtTonKho.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(txtTonKho, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNhaSX))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNhaSX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imgSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nike.jpg"))); // NOI18N

        btnChonAnh.setText("Chọn ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(imgSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(imgSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 0, 0));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Ecommerce-Product-icon.png"))); // NOI18N
        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnXoaSp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaSp.setForeground(new java.awt.Color(255, 0, 0));
        btnXoaSp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete-file-icon (1).png"))); // NOI18N
        btnXoaSp.setText("Xóa Sản Phẩm");
        btnXoaSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSpActionPerformed(evt);
            }
        });

        btnChinhSuaSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChinhSuaSP.setForeground(new java.awt.Color(255, 51, 51));
        btnChinhSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actions-document-edit-icon (1).png"))); // NOI18N
        btnChinhSuaSP.setText("Chỉnh sửa");
        btnChinhSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChinhSuaSPActionPerformed(evt);
            }
        });

        btnTimKiemSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiemSP.setForeground(new java.awt.Color(255, 0, 0));
        btnTimKiemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search-icon (1).png"))); // NOI18N
        btnTimKiemSP.setText("Tìm kiếm");
        btnTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSPActionPerformed(evt);
            }
        });

        txtTimKiemSP.setBackground(new java.awt.Color(204, 204, 255));
        txtTimKiemSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BOTTOM));

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 51, 51));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Button-Load-icon.png"))); // NOI18N
        btnReset.setText("Làm mới");
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
                .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChinhSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemSP)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP)
                    .addComponent(btnXoaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChinhSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Loại Sản Phẩm", jPanel2);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        card.add(jpanelSP, "c1");

        jpanelHD.setBackground(new java.awt.Color(136, 189, 188));
        jpanelHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpanelHD.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTabbedPane2.setBackground(new java.awt.Color(136, 189, 188));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(136, 189, 188));

        jPanel23.setBackground(new java.awt.Color(136, 189, 188));

        jPanel25.setBackground(new java.awt.Color(136, 189, 188));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Đơn hàng  ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(110, 102, 88))); // NOI18N

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
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(136, 189, 188));
        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng chi tiết hóa đơn   ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(110, 102, 88))); // NOI18N

        tableCTHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCTHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCTHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableCTHoaDon);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel24.setBackground(new java.awt.Color(136, 189, 188));

        jPanel27.setBackground(new java.awt.Color(136, 189, 188));

        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Mã đơn hàng");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Tổng số lượng");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Khách hàng");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Ngày lập");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Tổng tiền");

        txtMaDH.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        txtKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKhachHangActionPerformed(evt);
            }
        });

        txtNgayLap.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MM/dd/yyyy"))));
        txtNgayLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLapActionPerformed(evt);
            }
        });

        txtTongTien.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Nhân viên");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Thanh toán");

        txtThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Ví điện tử", "2. Nhận hàng" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Ghi chú");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane5.setViewportView(jTextArea2);

        jButton2.setText("jButton2");

        jButton3.setText("jButton3");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKhachHang, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongSL)
                            .addComponent(txtMaDH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNhanVien, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNgayLap)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
                    .addComponent(jSeparator2))
                .addContainerGap())
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongSL, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Phiếu mua hàng", jPanel6);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1364, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 718, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Chi tiết đơn mua", jPanel18);

        jPanel5.setBackground(new java.awt.Color(136, 189, 188));

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Mẫu số: C1020012VN");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Kí hiệu: AA/3P");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Số: 000100001");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Hóa đơn bán hàng");

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
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
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
                .addContainerGap(34, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHOTenKH)
                    .addComponent(labelSDTKH))
                .addGap(18, 18, 18)
                .addComponent(labelDCKH)
                .addContainerGap())
        );

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
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addContainerGap()))
        );

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
                .addContainerGap()
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
                    .addComponent(jLabel32))
                .addContainerGap(41, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel5);

        javax.swing.GroupLayout jpanelHDLayout = new javax.swing.GroupLayout(jpanelHD);
        jpanelHD.setLayout(jpanelHDLayout);
        jpanelHDLayout.setHorizontalGroup(
            jpanelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jpanelHDLayout.setVerticalGroup(
            jpanelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        card.add(jpanelHD, "c2");

        javax.swing.GroupLayout jpanelKhachHangLayout = new javax.swing.GroupLayout(jpanelKhachHang);
        jpanelKhachHang.setLayout(jpanelKhachHangLayout);
        jpanelKhachHangLayout.setHorizontalGroup(
            jpanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1381, Short.MAX_VALUE)
        );
        jpanelKhachHangLayout.setVerticalGroup(
            jpanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );

        card.add(jpanelKhachHang, "card4");

        javax.swing.GroupLayout jpanelNhanVienLayout = new javax.swing.GroupLayout(jpanelNhanVien);
        jpanelNhanVien.setLayout(jpanelNhanVienLayout);
        jpanelNhanVienLayout.setHorizontalGroup(
            jpanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1381, Short.MAX_VALUE)
        );
        jpanelNhanVienLayout.setVerticalGroup(
            jpanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );

        card.add(jpanelNhanVien, "card5");

        javax.swing.GroupLayout jpanelDoiTacLayout = new javax.swing.GroupLayout(jpanelDoiTac);
        jpanelDoiTac.setLayout(jpanelDoiTacLayout);
        jpanelDoiTacLayout.setHorizontalGroup(
            jpanelDoiTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1381, Short.MAX_VALUE)
        );
        jpanelDoiTacLayout.setVerticalGroup(
            jpanelDoiTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );

        card.add(jpanelDoiTac, "card6");

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(136, 189, 188));

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setText("Tình trạng đơn hàng");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Đã giao", "2. Đang vận chuyển", "3. Đã hủy" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setText("Mặt hàng");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Doan thu lớn nhất", "2. Doanh thu thấp nhất", "3. Số lượng lớn nhất", "4. Số lượng thấp nhất" }));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("Thời gian");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1.setText("Ngày..tháng..năm");
        jFormattedTextField1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 294, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jLabel79)
                    .addComponent(jLabel80))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Thực hiện");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Quay lại");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addContainerGap(103, Short.MAX_VALUE))
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
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Doanh thu", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1356, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 828, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Tồn kho", jPanel8);

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
            .addGap(0, 1381, Short.MAX_VALUE)
        );
        jpanelTaiKhoanLayout.setVerticalGroup(
            jpanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );

        card.add(jpanelTaiKhoan, "card8");

        javax.swing.GroupLayout jpanelAboutLayout = new javax.swing.GroupLayout(jpanelAbout);
        jpanelAbout.setLayout(jpanelAboutLayout);
        jpanelAboutLayout.setHorizontalGroup(
            jpanelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1381, Short.MAX_VALUE)
        );
        jpanelAboutLayout.setVerticalGroup(
            jpanelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
        );

        card.add(jpanelAbout, "card9");

        jPanel16.add(card, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1524, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:

//        cardLayout.last(card);
//       cardLayout.show(card, "c2");
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.show(card, "c2");
        innitTableHD();

    }//GEN-LAST:event_btnHoaDonActionPerformed

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
            defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
        }
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void tablMathangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablMathangMouseClicked
        // TODO add your handling code here:
        int row = tablMathang.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {

//                row và colum(0=id);
            String maMH = String.valueOf(tablMathang.getValueAt(row, 0));
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
        int row = tablMathang.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            int confim = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa không");
            if (confim == JOptionPane.YES_OPTION) {
                String maMH = String.valueOf(tablMathang.getValueAt(row, 0));
                mathangService.deleteMatHang(maMH);
                //        xóa hết dữ liệu hiện tại
                defaultTableModel.setRowCount(0);
                List<MatHang> mathangs = mathangService.getAllSanPham();

                for (MatHang mathang : mathangs) {
                    defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
                }
            }
        }
    }//GEN-LAST:event_btnXoaSpActionPerformed

    private void btnChinhSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChinhSuaSPActionPerformed
        // TODO add your handling code here:
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

            mathangService.updateMatHang(mathang);
            //        xóa hết dữ liệu hiện tại
            defaultTableModel.setRowCount(0);
            defaultTableModel.setRowCount(0);
            List<MatHang> mathangs = mathangService.getAllSanPham();

            for (MatHang mathang : mathangs) {
                defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
            }

        }
    }//GEN-LAST:event_btnChinhSuaSPActionPerformed

    private void btnTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSPActionPerformed
        // TODO add your handling code here:
        //        xóa hết dữ liệu hiện tại
        defaultTableModel.setRowCount(0);

//        show dữ liệu lại
        MatHang mathang = mathangService.searchProduct(txtTimKiemSP.getText());

//        for (Book book : books) {
        defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
//        }mathang

    }//GEN-LAST:event_btnTimKiemSPActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        //        xóa hết dữ liệu hiện tại
        defaultTableModel.setRowCount(0);
        defaultTableModel.setRowCount(0);
        List<MatHang> mathangs = mathangService.getAllSanPham();

        for (MatHang mathang : mathangs) {
            defaultTableModel.addRow(new Object[]{mathang.getMaMH(), mathang.getTenMH(), mathang.getLoai(), mathang.getGiaNhap(), mathang.getGiaBan(), mathang.getNhaSX(), mathang.getSoLuong(), mathang.getNgayTao()});
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        // TODO add your handling code here:
        defaultTableModel.setRowCount(0);
        defaultTableModel.setColumnCount(0);
        initTableSP();
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.first(card);
    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void txtNgayLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapActionPerformed

    private void txtKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKhachHangActionPerformed

    private void tableCTHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCTHoaDonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tableCTHoaDonMouseClicked

    private void tableHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHDMouseClicked
        // TODO add your handling code here:
        int row = tableHD.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {

//                row và colum(0=id);
            int maDH = (int) tableHD.getValueAt(row, 0);
            moveTab = maDH;
            donhang = donhangService.getMaDH(maDH);
            txtMaDH.setText(String.valueOf(donhang.getSoHD()));
            txtTongSL.setText(String.valueOf(donhang.getSoLuong()));
            txtKhachHang.setText(donhang.getKhachHang());
            txtNhanVien.setText(donhang.getNhanVien());
            txtTongTien.setText(String.valueOf(donhang.getTongTien()));
            txtNgayLap.setText(String.valueOf(donhang.getNgayDat()));
            int a = donhang.getLoaiThanhToan();
            if (a == 0) {
                txtThanhToan.setSelectedIndex(0);
            } else if (a == 1) {
                txtThanhToan.setSelectedIndex(1);
            }

            innitTableCTHoaDon(maDH);
            innitTableHoaDonXuat(moveTab);

        }

    }//GEN-LAST:event_tableHDMouseClicked

    private void btnDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoanhThuActionPerformed
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) card.getLayout();
//        next: lấy các contentTyoe nằm dưới cái 1
        cardLayout.show(card, "card7");
        initTableSale();
    }//GEN-LAST:event_btnDoanhThuActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
    private javax.swing.JButton btnDoanhThu;
    private javax.swing.JButton btnDoiTac;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnNhanvien;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiemSP;
    private javax.swing.JButton btnXoaSp;
    private javax.swing.JPanel card;
    private javax.swing.JComboBox<String> comBoLoaiSP;
    private javax.swing.JLabel imgSanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel jpanelAbout;
    private javax.swing.JPanel jpanelDoanhThu;
    private javax.swing.JPanel jpanelDoiTac;
    private javax.swing.JPanel jpanelHD;
    private javax.swing.JPanel jpanelKhachHang;
    private javax.swing.JPanel jpanelNhanVien;
    private javax.swing.JPanel jpanelSP;
    private javax.swing.JPanel jpanelTaiKhoan;
    private javax.swing.JLabel labelDCKH;
    private javax.swing.JLabel labelHOTenKH;
    private javax.swing.JLabel labelPhiVC;
    private javax.swing.JLabel labelSDTKH;
    private javax.swing.JLabel labelSale;
    private javax.swing.JLabel lableTongTien;
    private javax.swing.JTable tablMathang;
    private javax.swing.JTable tableCTHoaDon;
    private javax.swing.JTable tableHD;
    private javax.swing.JTable tableHoaDonXuat;
    private javax.swing.JTable tableSale;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtKhachHang;
    private javax.swing.JFormattedTextField txtMaDH;
    private javax.swing.JTextField txtMaSp;
    private javax.swing.JFormattedTextField txtNgayLap;
    private javax.swing.JTextField txtNhaSX;
    private javax.swing.JTextField txtNhanVien;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JComboBox<String> txtThanhToan;
    private javax.swing.JTextField txtTimKiemSP;
    private javax.swing.JFormattedTextField txtTonKho;
    private javax.swing.JTextField txtTongSL;
    private javax.swing.JFormattedTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
