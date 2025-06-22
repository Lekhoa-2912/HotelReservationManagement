package edu.poly.doancuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import edu.poly.doancuoiki.AdapterDanhSachNhanPhong.DailyCheckInsActivity;
import edu.poly.doancuoiki.AdapterDanhSachNhanVien.EmployeeListActivity;
import edu.poly.doancuoiki.AdapterDoanhThu.RevenueStatisticsActivity;


public class TrangChuActivity extends AppCompatActivity {
    private CardView cardQuanLyNhanVien;
    // Thêm các CardView khác
    private CardView cardDatPhong, cardQuanLyPhong, cardThongKeDoanhThu, cardQuanLyDanhGia;
    private CardView cardQuanLyHoaDon, cardQuanLyDichVu, cardThanhToan, cardQuanLyKhachHang, cardQuyDinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        // Ánh xạ các CardView theo id (cần thêm id vào layout)
        cardDatPhong = findViewById(R.id.card_dat_phong);
        cardQuanLyPhong = findViewById(R.id.card_quan_ly_phong);
        cardThongKeDoanhThu = findViewById(R.id.card_thong_ke_doanh_thu);
        cardQuanLyDanhGia = findViewById(R.id.card_quan_ly_danh_gia);
        cardQuanLyNhanVien = findViewById(R.id.card_quan_ly_nhan_vien);
        cardQuanLyHoaDon = findViewById(R.id.card_quan_ly_hoa_don);
        cardQuanLyDichVu = findViewById(R.id.card_quan_ly_dich_vu);
        cardThanhToan = findViewById(R.id.card_thanh_toan);
        cardQuanLyKhachHang = findViewById(R.id.card_quan_ly_khach_hang);
        cardQuyDinh = findViewById(R.id.card_quy_dinh);

        // Đặt sự kiện click cho từng CardView
        if (cardDatPhong != null) {
            cardDatPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quản lý đặt phòng
                    startActivity(new Intent(TrangChuActivity.this, DatPhongActivity.class));
                }
            });
        }
        if (cardQuanLyPhong != null) {
            cardQuanLyPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quản lý phòng
                    startActivity(new Intent(TrangChuActivity.this, CheckInActivity.class));
                }
            });
        }
        if (cardThongKeDoanhThu != null) {
            cardThongKeDoanhThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity thống kê doanh thu
                     startActivity(new Intent(TrangChuActivity.this, RevenueStatisticsActivity.class));
                }
            });
        }
        if (cardQuanLyDanhGia != null) {
            cardQuanLyDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity danh sách nhận phòng
                    startActivity(new Intent(TrangChuActivity.this, DailyCheckInsActivity.class));
                }
            });
        }
        if (cardQuanLyNhanVien != null) {
            cardQuanLyNhanVien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sửa lại tên Activity cho đúng
                    Intent intent = new Intent(TrangChuActivity.this, EmployeeManagementActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (cardQuanLyHoaDon != null) {
            cardQuanLyHoaDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quản lý hóa đơn
                     //startActivity(new Intent(TrangChuActivity.this, InvoiceActivity.class));
                }
            });
        }
        if (cardQuanLyDichVu != null) {
            cardQuanLyDichVu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quản lý dịch vụ
                    // startActivity(new Intent(TrangChuActivity.this, EmployeeListActivity.class));
                }
            });
        }
        if (cardThanhToan != null) {
            cardThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity sử dụng dịch vụ và thanh toán
                    // startActivity(new Intent(TrangChuActivity.this, ThanhToanActivity.class));
                }
            });
        }
        if (cardQuanLyKhachHang != null) {
            cardQuanLyKhachHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quản lý khách hàng
                    // startActivity(new Intent(TrangChuActivity.this, QuanLyKhachHangActivity.class));
                }
            });
        }
        if (cardQuyDinh != null) {
            cardQuyDinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Chuyển sang Activity quy định
                    // startActivity(new Intent(TrangChuActivity.this, QuyDinhActivity.class));
                }
            });
        }
    }
}