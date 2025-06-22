package edu.poly.doancuoiki;

import android.app.DatePickerDialog;
import android.content.Intent; // Thêm dòng này
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView; // Cho Spinner
import android.widget.ArrayAdapter; // Cho Spinner
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast; // Dùng để hiển thị thông báo ngắn
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import edu.poly.doancuoiki.AdapterDanhSachNhanVien.EmployeeListActivity; // Đảm bảo import đúng Activity danh sách nhân viên

import java.util.Calendar; // Dùng để chọn ngày

public class EmployeeManagementActivity extends AppCompatActivity {

    // 1. Khai báo các biến để tham chiếu đến các thành phần UI
    private CardView btnBackCircle;
    private EditText etSearchQuery;
    private Button btnSearch;

    private EditText etUsername;
    private Spinner spinnerEmployeeType;
    private LinearLayout layoutHireDate;
    private TextView tvHireDate; // TextView để hiển thị ngày vào làm

    private EditText etEmployeeName;
    private EditText etCmnd;
    private Spinner spinnerGender;
    private LinearLayout layoutDob;
    private TextView tvDob; // TextView để hiển thị ngày sinh
    private EditText etPhone;
    private EditText etAddress;

    private Button btnUpdateEmployee;
    private Button btnAddEmployee;
    private Button btnResetPassword;
    private Button btnGrantAccess;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanli_nhanvien); // Đảm bảo tên layout trùng khớp

        // 2. Ánh xạ các thành phần UI bằng ID của chúng
        btnBackCircle = findViewById(R.id.btn_back_circle);
        etSearchQuery = findViewById(R.id.et_search_query);
        btnSearch = findViewById(R.id.btn_search);

        etUsername = findViewById(R.id.et_username);
        spinnerEmployeeType = findViewById(R.id.spinner_employee_type);
        layoutHireDate = findViewById(R.id.layout_hire_date);
        tvHireDate = findViewById(R.id.tv_hire_date);

        etEmployeeName = findViewById(R.id.et_employee_name);
        etCmnd = findViewById(R.id.et_cmnd);
        spinnerGender = findViewById(R.id.spinner_gender);
        layoutDob = findViewById(R.id.layout_dob);
        tvDob = findViewById(R.id.tv_dob);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);

        btnUpdateEmployee = findViewById(R.id.btn_update_employee);
        btnAddEmployee = findViewById(R.id.btn_add_employee);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        btnGrantAccess = findViewById(R.id.btn_grant_access);
        btnClose = findViewById(R.id.btn_close);

        // 3. Thiết lập lắng nghe sự kiện (OnClickListener cho Buttons và click-able layouts)

        // Nút Quay lại
        btnBackCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại màn hình trước đó
            }
        });

        // Nút Tìm kiếm
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchEmployee();
            }
        });

        // Lựa chọn Ngày vào làm (mở DatePickerDialog)
        layoutHireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvHireDate); // Gọi hàm hiển thị DatePicker
            }
        });

        // Lựa chọn Ngày sinh (mở DatePickerDialog)
        layoutDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvDob); // Gọi hàm hiển thị DatePicker
            }
        });

        // Nút Cập Nhật Nhân Viên
        btnUpdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateEmployee();
            }
        });

        // Nút Thêm Nhân Viên
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddEmployee();
            }
        });

        // Nút Đặt Lại Mật Khẩu
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResetPassword();
            }
        });

        // Danh sách Nhân Viên (cấp quyền truy cập)
        btnGrantAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nếu bạn muốn mở giao diện cấp quyền thì gọi handleGrantAccess();
                // Nếu muốn mở danh sách nhân viên thì giữ nguyên dòng dưới (và đảm bảo EmployeeListActivity tồn tại)
                // handleGrantAccess();
                startActivity(new Intent(EmployeeManagementActivity.this, EmployeeListActivity.class));
            }
        });

        // Nút Đóng
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại
            }
        });

        // 4. Thiết lập lắng nghe sự kiện cho Spinners (nếu bạn muốn lấy giá trị khi người dùng chọn)
        spinnerEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
                Toast.makeText(EmployeeManagementActivity.this, "Loại nhân viên: " + selectedType, Toast.LENGTH_SHORT).show();
                // TODO: Xử lý khi loại nhân viên được chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();
                Toast.makeText(EmployeeManagementActivity.this, "Giới tính: " + selectedGender, Toast.LENGTH_SHORT).show();
                // TODO: Xử lý khi giới tính được chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // (Tùy chọn) Khởi tạo dữ liệu cho Spinner nếu chưa có trong XML (entries)
        // Nếu bạn đã có android:entries="@array/employee_types" trong XML, không cần phần này
        // setupSpinners();
    }

    /**
     * Hàm hiển thị DatePickerDialog và cập nhật TextView đích.
     * @param targetTextView TextView sẽ hiển thị ngày được chọn.
     */
    private void showDatePickerDialog(final TextView targetTextView) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Định dạng ngày tháng thành MM/DD/YYYY hoặc DD/MM/YYYY tùy ý
                    String date = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    targetTextView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Xử lý logic khi nút Tìm Kiếm được nhấn.
     */
    private void handleSearchEmployee() {
        String searchQuery = etSearchQuery.getText().toString().trim();
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin tìm kiếm.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Tìm kiếm nhân viên với: " + searchQuery, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu API tìm kiếm nhân viên đến backend và hiển thị kết quả
        // Ví dụ: callApiToSearchEmployee(searchQuery);
    }

    /**
     * Xử lý logic khi nút Cập Nhật Nhân Viên được nhấn.
     */
    private void handleUpdateEmployee() {
        // Lấy dữ liệu từ các trường nhập liệu
        String username = etUsername.getText().toString().trim();
        String employeeName = etEmployeeName.getText().toString().trim();
        String cmnd = etCmnd.getText().toString().trim();
        String hireDate = tvHireDate.getText().toString().trim();
        String dob = tvDob.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String employeeType = spinnerEmployeeType.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        // TODO: Thực hiện validation các trường dữ liệu ở đây
        if (username.isEmpty() || employeeName.isEmpty() || cmnd.isEmpty() || hireDate.isEmpty() ||
                dob.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin để cập nhật.", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Cập nhật nhân viên: " + employeeName, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu API để cập nhật thông tin nhân viên
        // Ví dụ: callApiToUpdateEmployee(username, employeeName, ...);
    }

    /**
     * Xử lý logic khi nút Thêm Nhân Viên được nhấn.
     */
    private void handleAddEmployee() {
        // Lấy dữ liệu từ các trường nhập liệu (tương tự update)
        String username = etUsername.getText().toString().trim();
        String employeeName = etEmployeeName.getText().toString().trim();
        String cmnd = etCmnd.getText().toString().trim();
        String hireDate = tvHireDate.getText().toString().trim();
        String dob = tvDob.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String employeeType = spinnerEmployeeType.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        // TODO: Thực hiện validation các trường dữ liệu ở đây, đặc biệt là tài khoản mới
        if (username.isEmpty() || employeeName.isEmpty() || cmnd.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin bắt buộc để thêm nhân viên.", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Thêm nhân viên mới: " + employeeName, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu API để thêm nhân viên mới (có thể cần thêm mật khẩu ban đầu)
        // Ví dụ: callApiToAddEmployee(username, employeeName, password, ...);
    }

    /**
     * Xử lý logic khi nút Đặt Lại Mật Khẩu được nhấn.
     */
    private void handleResetPassword() {
        String username = etUsername.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập của nhân viên cần đặt lại mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Đặt lại mật khẩu cho: " + username, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu API để đặt lại mật khẩu cho nhân viên (ví dụ: gửi email đặt lại)
        // Ví dụ: callApiToResetPassword(username);
    }

    /**
     * Xử lý logic khi nút Quyền Truy Cập được nhấn.
     */
    private void handleGrantAccess() {
        String username = etUsername.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn nhân viên để cấp quyền.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Mở giao diện cấp quyền cho: " + username, Toast.LENGTH_SHORT).show();
        // TODO: Mở một Activity/Dialog mới để quản lý chi tiết quyền truy cập cho nhân viên này
        // Ví dụ: Intent intent = new Intent(this, AccessControlActivity.class);
        // intent.putExtra("employee_username", username);
        // startActivity(intent);
    }

    /**
     * (Tùy chọn) Khởi tạo và thiết lập ArrayAdapter cho các Spinner
     * Chỉ cần nếu bạn không dùng android:entries trong XML
     */
    /*
    private void setupSpinners() {
        // Spinner Loại nhân viên
        ArrayAdapter<CharSequence> employeeTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.employee_types, // Cần định nghĩa mảng này trong res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        employeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployeeType.setAdapter(employeeTypeAdapter);

        // Spinner Giới tính
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.genders, // Cần định nghĩa mảng này trong res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
    }
    */
}