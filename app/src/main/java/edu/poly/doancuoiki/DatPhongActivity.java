package edu.poly.doancuoiki;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView; // Import for Spinner listener
import android.widget.ArrayAdapter; // Import for Spinner adapter
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast; // Import Toast for messages
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatPhongActivity extends AppCompatActivity {
    // Khai báo các view
    private Spinner spinnerRoomType, spinnerCustomerType, spinnerCustomerGender, spinnerCustomerNationality;
    private EditText edtNumNights, edtMaxGuests, edtPrice, edtCustomerIdSearch, edtCustomerFullName, edtCustomerIdCard, edtCustomerPhone, edtCustomerAddress;
    private TextView tvCheckInDate, tvCheckOutDate, tvCustomerDob;
    private LinearLayout layoutCheckInDate, layoutCheckOutDate, layoutCustomerDob;
    private Button btnSearchCustomer, btnBookRoom, btnCancelBooking, btnClose;
    private CheckBox checkboxMoveToCheckin;

    // Biến để lưu trữ ngày đã chọn
    private Calendar checkInCalendar, checkOutCalendar, customerDobCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datphong);

        // Khởi tạo Calendar instances
        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();
        customerDobCalendar = Calendar.getInstance();

        // Ánh xạ view
        initViews();

        // Cấu hình Spinners (giả định có các arrays.xml tương ứng)
        setupSpinners();

        // Bắt sự kiện các nút và layouts
        setupListeners();

        // Cập nhật ngày hiện tại vào TextViews lúc ban đầu
        updateDateInView(tvCheckInDate, checkInCalendar);
        updateDateInView(tvCheckOutDate, checkOutCalendar);
        updateDateInView(tvCustomerDob, customerDobCalendar);
    }

    private void initViews() {
        spinnerRoomType = findViewById(R.id.spinner_room_type_booking);
        spinnerCustomerType = findViewById(R.id.spinner_customer_type);
        spinnerCustomerGender = findViewById(R.id.spinner_customer_gender);
        spinnerCustomerNationality = findViewById(R.id.spinner_customer_nationality);

        edtNumNights = findViewById(R.id.edt_num_nights);
        edtMaxGuests = findViewById(R.id.edt_max_guests);
        edtPrice = findViewById(R.id.edt_price);
        edtCustomerIdSearch = findViewById(R.id.edt_customer_id_search);
        edtCustomerFullName = findViewById(R.id.edt_customer_full_name);
        edtCustomerIdCard = findViewById(R.id.edt_customer_id_card);
        edtCustomerPhone = findViewById(R.id.edt_customer_phone);
        edtCustomerAddress = findViewById(R.id.edt_customer_address);

        tvCheckInDate = findViewById(R.id.tv_check_in_date_booking);
        tvCheckOutDate = findViewById(R.id.tv_check_out_date_booking);
        tvCustomerDob = findViewById(R.id.tv_customer_dob);

        layoutCheckInDate = findViewById(R.id.layout_check_in_date_booking);
        layoutCheckOutDate = findViewById(R.id.layout_check_out_date_booking);
        layoutCustomerDob = findViewById(R.id.layout_customer_dob);

        btnSearchCustomer = findViewById(R.id.btn_search_customer);
        btnBookRoom = findViewById(R.id.btn_book_room);
        btnCancelBooking = findViewById(R.id.btn_cancel_booking);
        btnClose = findViewById(R.id.btn_close);
        checkboxMoveToCheckin = findViewById(R.id.checkbox_move_to_checkin);
    }

    private void setupSpinners() {
        // Cấu hình Spinner Loại phòng
        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.room_types, // Đảm bảo bạn đã định nghĩa room_types trong res/values/arrays.xml
                android.R.layout.simple_spinner_item
        );
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomTypeAdapter);

        // Bắt sự kiện khi chọn loại phòng
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomType = parent.getItemAtPosition(position).toString();
                Toast.makeText(DatPhongActivity.this, "Bạn đã chọn loại phòng: " + selectedRoomType, Toast.LENGTH_SHORT).show();
                // TODO: Dựa vào selectedRoomType, bạn có thể cập nhật edtMaxGuests và edtPrice
                // Ví dụ:
                if (selectedRoomType.equals("Phòng Đơn")) {
                    edtMaxGuests.setText("1");
                    edtPrice.setText("500000"); // Ví dụ giá
                } else if (selectedRoomType.equals("Phòng Đôi")) {
                    edtMaxGuests.setText("2");
                    edtPrice.setText("800000");
                } else if (selectedRoomType.equals("Phòng Gia Đình")) {
                    edtMaxGuests.setText("4");
                    edtPrice.setText("1500000");
                } else {
                    edtMaxGuests.setText("");
                    edtPrice.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });

        // Cấu hình Spinner Loại khách hàng
        ArrayAdapter<CharSequence> customerTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.customer_types, // Cần định nghĩa array này
                android.R.layout.simple_spinner_item
        );
        customerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomerType.setAdapter(customerTypeAdapter);
        spinnerCustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCustomerType = parent.getItemAtPosition(position).toString();
                // TODO: Xử lý khi chọn loại khách hàng nếu cần
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Cấu hình Spinner Giới tính
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.genders, // Cần định nghĩa array này
                android.R.layout.simple_spinner_item
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomerGender.setAdapter(genderAdapter);
        spinnerCustomerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();
                // TODO: Xử lý khi chọn giới tính nếu cần
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Cấu hình Spinner Quốc tịch
        ArrayAdapter<CharSequence> nationalityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.nationalities, // Cần định nghĩa array này
                android.R.layout.simple_spinner_item
        );
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomerNationality.setAdapter(nationalityAdapter);
        spinnerCustomerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNationality = parent.getItemAtPosition(position).toString();
                // TODO: Xử lý khi chọn quốc tịch nếu cần
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupListeners() {
        // Sự kiện chọn ngày nhận phòng
        layoutCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvCheckInDate, checkInCalendar);
            }
        });

        // Sự kiện chọn ngày trả phòng
        layoutCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvCheckOutDate, checkOutCalendar);
            }
        });

        // Sự kiện chọn ngày sinh khách hàng
        layoutCustomerDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvCustomerDob, customerDobCalendar);
            }
        });

        // Sự kiện nút Tìm Kiếm Khách hàng
        btnSearchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerId = edtCustomerIdSearch.getText().toString().trim();
                if (!customerId.isEmpty()) {
                    Toast.makeText(DatPhongActivity.this, "Đang tìm kiếm khách hàng ID: " + customerId, Toast.LENGTH_SHORT).show();
                    // TODO: Gọi API/logic để tìm kiếm khách hàng bằng ID và điền thông tin vào các EditText/Spinner
                    // Giả sử tìm thấy khách hàng:
                    edtCustomerFullName.setText("Nguyễn Thị B");
                    edtCustomerIdCard.setText(customerId);
                    edtCustomerPhone.setText("0901234567");
                    edtCustomerAddress.setText("123 Đường C, Quận D");
                    // Đặt các spinner về giá trị phù hợp (cần tìm index của giá trị)
                    // spinnerCustomerType.setSelection(adapterCustomerType.getPosition("Khách Vãng Lai"));
                    // spinnerCustomerGender.setSelection(adapterGender.getPosition("Nữ"));
                    // spinnerCustomerNationality.setSelection(adapterNationality.getPosition("Việt Nam"));
                    // tvCustomerDob.setText("01/01/1990"); // Cần cập nhật Calendar instance nếu muốn dùng DatePicker
                    Toast.makeText(DatPhongActivity.this, "Đã tìm thấy thông tin khách hàng.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DatPhongActivity.this, "Vui lòng nhập ID khách hàng để tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện Checkbox "Chuyển đến nhận phòng"
        checkboxMoveToCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(DatPhongActivity.this, "Sẽ chuyển đến màn hình nhận phòng sau khi đặt.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DatPhongActivity.this, "Chỉ đặt phòng, không chuyển đến nhận phòng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện nút Đặt Phòng
        btnBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý logic đặt phòng
                // 1. Lấy tất cả dữ liệu từ các trường nhập liệu và spinner
                String roomType = spinnerRoomType.getSelectedItem().toString();
                String numNightsStr = edtNumNights.getText().toString().trim();
                String checkInDate = tvCheckInDate.getText().toString();
                String checkOutDate = tvCheckOutDate.getText().toString();
                String maxGuestsDisplay = edtMaxGuests.getText().toString(); // Thông tin hiển thị, không nhập
                String priceDisplay = edtPrice.getText().toString();       // Thông tin hiển thị, không nhập

                String customerFullName = edtCustomerFullName.getText().toString().trim();
                String customerIdCard = edtCustomerIdCard.getText().toString().trim();
                String customerType = spinnerCustomerType.getSelectedItem().toString();
                String customerPhone = edtCustomerPhone.getText().toString().trim();
                String customerDob = tvCustomerDob.getText().toString();
                String customerAddress = edtCustomerAddress.getText().toString().trim();
                String customerGender = spinnerCustomerGender.getSelectedItem().toString();
                String customerNationality = spinnerCustomerNationality.getSelectedItem().toString();

                boolean moveToCheckin = checkboxMoveToCheckin.isChecked();

                // 2. Xác thực dữ liệu (ví dụ đơn giản)
                if (numNightsStr.isEmpty() || customerFullName.isEmpty() || customerIdCard.isEmpty() || customerPhone.isEmpty() || customerAddress.isEmpty()) {
                    Toast.makeText(DatPhongActivity.this, "Vui lòng điền đầy đủ thông tin bắt buộc!", Toast.LENGTH_LONG).show();
                    return; // Dừng nếu dữ liệu không hợp lệ
                }

                int numNights = Integer.parseInt(numNightsStr);

                // 3. Thực hiện logic đặt phòng (gọi API, lưu vào DB, ...)
                Toast.makeText(DatPhongActivity.this, "Đang xử lý đặt phòng...", Toast.LENGTH_SHORT).show();
                // TODO: Gọi API hoặc thực hiện lưu trữ dữ liệu đặt phòng

                // 4. Sau khi đặt phòng thành công, chuyển hướng hoặc hiển thị thông báo
                Toast.makeText(DatPhongActivity.this, "Đặt phòng thành công!", Toast.LENGTH_LONG).show();

                if (moveToCheckin) {
                    // Chuyển sang màn hình nhận phòng (Check-in)
                    // Giả sử có một Activity tên là CheckinActivity
                    Intent intent = new Intent(DatPhongActivity.this, CheckInActivity.class);
                    // Bạn có thể truyền dữ liệu đặt phòng qua Intent nếu cần
                    intent.putExtra("booking_info", "Thông tin đặt phòng chi tiết...");
                    startActivity(intent);
                    finish(); // Đóng Activity hiện tại nếu không muốn quay lại
                } else {
                    // Có thể reset form hoặc chuyển về màn hình chính
                    // Ví dụ: resetForm();
                }
            }
        });

        // Sự kiện nút Hủy
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý hủy đặt phòng hoặc reset form
                Toast.makeText(DatPhongActivity.this, "Hủy thao tác đặt phòng", Toast.LENGTH_SHORT).show();
                resetForm(); // Gọi hàm reset form
            }
        });

        // Sự kiện nút Đóng
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng Activity hiện tại
                finish();
            }
        });
    }

    // Hàm hiển thị DatePickerDialog
    private void showDatePickerDialog(final TextView targetTextView, final Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    updateDateInView(targetTextView, calendar);
                },
                year, month, day);
        datePickerDialog.show();
    }

    // Hàm cập nhật TextView với ngày đã chọn
    private void updateDateInView(TextView targetTextView, Calendar calendar) {
        String myFormat = "dd/MM/yyyy"; // Định dạng ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        targetTextView.setText(sdf.format(calendar.getTime()));
    }

    // Hàm reset form
    private void resetForm() {
        // Reset các EditText
        edtNumNights.setText("");
        edtCustomerIdSearch.setText("");
        edtCustomerFullName.setText("");
        edtCustomerIdCard.setText("");
        edtCustomerPhone.setText("");
        edtCustomerAddress.setText("");

        // Reset các TextView ngày về ngày hiện tại
        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();
        customerDobCalendar = Calendar.getInstance();
        updateDateInView(tvCheckInDate, checkInCalendar);
        updateDateInView(tvCheckOutDate, checkOutCalendar);
        updateDateInView(tvCustomerDob, customerDobCalendar);

        // Reset các Spinner về vị trí đầu tiên
        spinnerRoomType.setSelection(0);
        spinnerCustomerType.setSelection(0);
        spinnerCustomerGender.setSelection(0);
        spinnerCustomerNationality.setSelection(0);

        // Bỏ chọn checkbox
        checkboxMoveToCheckin.setChecked(false);

        Toast.makeText(this, "Form đã được reset!", Toast.LENGTH_SHORT).show();
    }
}