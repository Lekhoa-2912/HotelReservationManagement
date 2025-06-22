package edu.poly.doancuoiki.AdapterDanhSachNhanVien;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import edu.poly.doancuoiki.R;  // Đảm bảo import đúng

public class EmployeeListActivity extends AppCompatActivity {

    private CardView btnBackCircle; // Nút quay lại
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo tên layout chính xác với file XML của bạn
        // Giả sử layout bạn cung cấp ở trên được lưu là activity_employee_list.xml
        setContentView(R.layout.activity_danhsach_nhanvien);

        // Ánh xạ View:
        btnBackCircle = findViewById(R.id.btn_back_circle);
        recyclerView = findViewById(R.id.rv_employee_list);

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách nhân viên
        employeeList = new ArrayList<>();
        // TODO: Gọi API để tải dữ liệu nhân viên thực tế
        // Hiện tại, thêm dữ liệu giả lập để hiển thị UI:
        employeeList.add(new Employee("admin123", "Nguyễn Văn A", "Quản lý", "123456789", "0987654321", "123 Đường ABC, Hà Nội"));
        employeeList.add(new Employee("nhanvien01", "Trần Thị B", "Lễ tân", "987654321", "0912345678", "456 Đường XYZ, Đà Nẵng"));
        employeeList.add(new Employee("kt_vien01", "Lê Văn C", "Kế toán", "456789123", "0901234567", "789 Phố KLQ, TP.HCM"));
        employeeList.add(new Employee("giamdoc", "Phạm Thị D", "Giám đốc", "321654987", "0976543210", "101 Đường MNO, Cần Thơ"));
        employeeList.add(new Employee("nhanvien02", "Hoàng Anh E", "Buồng phòng", "654987321", "0965432109", "202 Hẻm DEF, Hải Phòng"));
        employeeList.add(new Employee("baove", "Vũ Văn F", "Bảo vệ", "789123456", "0932165498", "303 Ngõ GHI, Huế"));
        employeeList.add(new Employee("tiepthi", "Nguyễn Thị G", "Tiếp thị", "159753842", "0945678912", "404 Hẻm JKL, Nha Trang"));


        // Khởi tạo Adapter và gán cho RecyclerView
        adapter = new EmployeeAdapter(employeeList);
        // (Tùy chọn) Thiết lập lắng nghe sự kiện click cho adapter
        adapter.setOnItemClickListener(employee -> {
            Toast.makeText(EmployeeListActivity.this, "Clicked on: " + employee.getFullName() + " (" + employee.getUsername() + ")", Toast.LENGTH_SHORT).show();
            // TODO: Xử lý khi click vào một item nhân viên, ví dụ: mở màn hình chi tiết/chỉnh sửa nhân viên
            // Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailActivity.class);
            // intent.putExtra("employee_username", employee.getUsername());
            // startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện cho nút quay lại
        btnBackCircle.setOnClickListener(v -> onBackPressed()); // Hoặc finish() nếu đây là màn hình gốc
    }
}