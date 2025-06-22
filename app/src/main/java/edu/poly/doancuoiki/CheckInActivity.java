package edu.poly.doancuoiki;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView; // Dùng cho Spinner
import android.widget.ArrayAdapter; // Dùng cho Spinner
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast; // Để hiển thị thông báo ngắn

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Dùng cho btn_back_circle

import java.util.Calendar; // Dùng để chọn ngày
import java.util.Objects; // Dùng để kiểm tra null cho Object (trong trường hợp Spinner.getSelectedItem())

public class CheckInActivity extends AppCompatActivity {

    // 1. Khai báo các biến để tham chiếu đến các thành phần UI
    private CardView btnBackCircle; // Nút quay lại dạng hình tròn
    private EditText edtReservationCode; // Trường nhập mã đặt phòng
    private Button btnSearchReservation; // Nút tìm kiếm đặt phòng

    private Spinner spinnerRoomType; // Spinner chọn loại phòng
    private Spinner spinnerRoomNumber; // Spinner chọn số phòng cụ thể

    private EditText etFullName; // Trường họ và tên khách hàng
    private EditText etIdCard; // Trường thẻ căn cước/CMND
    private EditText etRoomName; // Trường tên phòng (ví dụ: "Phòng 105") - đọc readonly
    private EditText etRoomTypeName; // Trường tên loại phòng (ví dụ: "Phòng Suite") - đọc readonly

    private LinearLayout layoutCheckInDate; // Layout click để chọn ngày nhận phòng
    private TextView tvCheckInDate; // TextView hiển thị ngày nhận phòng
    private LinearLayout layoutCheckOutDate; // Layout click để chọn ngày trả phòng
    private TextView tvCheckOutDate; // TextView hiển thị ngày trả phòng
    private EditText etMaxGuests; // Trường số lượng người tối đa - readonly
    private EditText etPrice; // Trường giá - readonly

    private Button btnCheckIn; // Nút " Nhận phòng"
    private Button btnCheckOut; // Nút "Trả phòng"
    private Button btnCancel; // Nút "Hủy"
    private Button btnClose; // Nút "Đóng"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanphong); // Đảm bảo tên layout khớp với file XML của bạn

        // 2. Ánh xạ các thành phần UI bằng ID của chúng
        btnBackCircle = findViewById(R.id.btn_back_circle);
        edtReservationCode = findViewById(R.id.edt_reservation_code);
        btnSearchReservation = findViewById(R.id.btn_search_reservation);

        spinnerRoomType = findViewById(R.id.spinner_room_type);
        spinnerRoomNumber = findViewById(R.id.spinner_room_number);

        etFullName = findViewById(R.id.et_full_name);
        etIdCard = findViewById(R.id.et_id_card);
        etRoomName = findViewById(R.id.et_room_name);
        etRoomTypeName = findViewById(R.id.et_room_type_name);

        layoutCheckInDate = findViewById(R.id.layout_check_in_date);
        tvCheckInDate = findViewById(R.id.tv_check_in_date);
        layoutCheckOutDate = findViewById(R.id.layout_check_out_date);
        tvCheckOutDate = findViewById(R.id.tv_check_out_date);
        etMaxGuests = findViewById(R.id.et_max_guests);
        etPrice = findViewById(R.id.et_price);

        btnCheckIn = findViewById(R.id.btn_check_in_guest);
        btnCheckOut= findViewById(R.id.btn_check_out_guest);
        btnCancel = findViewById(R.id.btn_cancel);
        btnClose = findViewById(R.id.btn_close);

        // 3. Thiết lập lắng nghe sự kiện (OnClickListener cho Buttons và click-able layouts)

        // Nút Quay lại
        btnBackCircle.setOnClickListener(v -> onBackPressed());

        // Nút Tìm kiếm đặt phòng
        btnSearchReservation.setOnClickListener(v -> handleSearchReservation());

        // Chọn ngày nhận phòng (mở DatePickerDialog)
        layoutCheckInDate.setOnClickListener(v -> showDatePickerDialog(tvCheckInDate));

        // Chọn ngày trả phòng (mở DatePickerDialog)
        layoutCheckOutDate.setOnClickListener(v -> showDatePickerDialog(tvCheckOutDate));

        // Nút Thêm Khách Hàng
        btnCheckIn.setOnClickListener(v -> handleAddCustomer());

        // Nút Nhận Phòng
        btnCheckOut.setOnClickListener(v -> handleCheckIn());

        // Nút Hủy
        btnCancel.setOnClickListener(v -> handleCancelReservation());

        // Nút Đóng
        btnClose.setOnClickListener(v -> finish()); // Đóng Activity hiện tại

        // 4. Thiết lập lắng nghe sự kiện cho Spinners (nếu cần xử lý khi item được chọn)

        // Spinner Loại phòng: Khi người dùng chọn một loại phòng,
        // bạn có thể tải danh sách các phòng cụ thể thuộc loại đó.
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomType = parent.getItemAtPosition(position).toString();
                Toast.makeText(CheckInActivity.this, "Bạn đã chọn loại phòng: " + selectedRoomType, Toast.LENGTH_SHORT).show();
                // TODO: Gọi API để tải danh sách các phòng cụ thể (ví dụ: "Phòng 101", "Phòng 102")
                // có sẵn cho loại phòng này và cập nhật spinnerRoomNumber.
                // Ví dụ: loadRoomsByType(selectedRoomType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });

        // Spinner Số phòng: Khi người dùng chọn một phòng cụ thể,
        // bạn có thể cập nhật các thông tin liên quan đến phòng đó.
        spinnerRoomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomNumber = parent.getItemAtPosition(position).toString();
                Toast.makeText(CheckInActivity.this, "Bạn đã chọn phòng: " + selectedRoomNumber, Toast.LENGTH_SHORT).show();
                // TODO: Dựa vào selectedRoomNumber, lấy thông tin chi tiết của phòng
                // từ dữ liệu đã tải hoặc gọi API, sau đó điền vào etRoomName, etRoomTypeName,
                // etMaxGuests, etPrice.
                // Ví dụ: updateRoomDetailsDisplay(selectedRoomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });

        // (Tùy chọn) Nếu bạn muốn tải dữ liệu ban đầu cho Spinners từ API
        // thay vì dùng android:entries trong XML, bạn sẽ khởi tạo chúng ở đây.
        // setupSpinnersFromApi();
    }

    /**
     * Hàm hiển thị DatePickerDialog (hộp thoại chọn ngày)
     * và cập nhật TextView đích với ngày đã chọn.
     * @param targetTextView TextView sẽ hiển thị ngày được chọn.
     */
    private void showDatePickerDialog(final TextView targetTextView) {
        // Lấy ngày hiện tại để làm giá trị mặc định cho DatePickerDialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Định dạng ngày tháng thành DD/MM/YYYY
                    String date = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    targetTextView.setText(date); // Cập nhật TextView đích
                }, year, month, day);
        datePickerDialog.show(); // Hiển thị hộp thoại chọn ngày
    }

    /**
     * Xử lý logic khi nút "Tìm Kiếm" đặt phòng được nhấn.
     * Sẽ lấy mã đặt phòng và gọi API để lấy thông tin.
     */
    private void handleSearchReservation() {
        String reservationCode = edtReservationCode.getText().toString().trim(); // Lấy mã đặt phòng
        if (reservationCode.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã đặt phòng để tìm kiếm.", Toast.LENGTH_SHORT).show();
            return; // Dừng lại nếu trường rỗng
        }
        Toast.makeText(this, "Đang tìm kiếm đặt phòng với mã: " + reservationCode, Toast.LENGTH_SHORT).show();

        // TODO: Gửi yêu cầu API (GET) đến backend để tìm kiếm thông tin đặt phòng
        // Ví dụ API: GET /api/manager/bookings/{reservationCode}
        // Giả sử API trả về một đối tượng `ReservationDetails`
        // Sau khi nhận được phản hồi, gọi hàm `updateUIWithReservationDetails`.
        //
        // Ví dụ giả định:
        // callApiToGetReservationDetails(reservationCode, new ApiCallback<ReservationDetails>() {
        //     @Override
        //     public void onSuccess(ReservationDetails details) {
        //         updateUIWithReservationDetails(details);
        //         Toast.makeText(CheckInActivity.this, "Tìm thấy đặt phòng!", Toast.LENGTH_SHORT).show();
        //     }
        //     @Override
        //     public void onFailure(String error) {
        //         Toast.makeText(CheckInActivity.this, "Không tìm thấy đặt phòng hoặc lỗi: " + error, Toast.LENGTH_LONG).show();
        //         clearReservationDetails(); // Xóa thông tin cũ nếu không tìm thấy
        //     }
        // });
    }

    /**
     * Hàm này được gọi sau khi thông tin đặt phòng được tìm thấy từ API.
     * Nó sẽ điền dữ liệu vào các trường UI tương ứng.
     * @param reservationDetails Đối tượng chứa thông tin đặt phòng.
     */
    private void updateUIWithReservationDetails(Object reservationDetails) { // Thay Object bằng class model thực tế của bạn
        // Đây là ví dụ giả định cách bạn sẽ điền dữ liệu
        // Các trường EditText không chỉnh sửa được (enabled="false") sẽ được cập nhật ở đây
        // Ví dụ:
        // etFullName.setText(reservationDetails.getFullName());
        // etIdCard.setText(reservationDetails.getIdCard());
        // tvCheckInDate.setText(reservationDetails.getCheckInDate());
        // tvCheckOutDate.setText(reservationDetails.getCheckOutDate());
        // etRoomName.setText(reservationDetails.getAssignedRoomName()); // Nếu đã có phòng cụ thể được gán
        // etRoomTypeName.setText(reservationDetails.getRoomTypeName());
        // etMaxGuests.setText(String.valueOf(reservationDetails.getMaxGuests()));
        // etPrice.setText(String.format("%,.0f VND", reservationDetails.getTotalPrice())); // Định dạng tiền tệ

        // TODO: Cập nhật dữ liệu cho Spinners (Loại phòng và Số phòng) nếu cần
        // Ví dụ: setupRoomSpinners(reservationDetails.getAvailableRoomTypes(), reservationDetails.getAvailableRoomNumbers());
    }

    /**
     * Hàm để xóa thông tin đặt phòng trên UI (khi tìm kiếm không thành công hoặc hủy).
     */
    private void clearReservationDetails() {
        etFullName.setText("");
        etIdCard.setText("");
        tvCheckInDate.setText("");
        tvCheckOutDate.setText("");
        etRoomName.setText("");
        etRoomTypeName.setText("");
        etMaxGuests.setText("");
        etPrice.setText("");
        // TODO: Đặt lại Spinners về trạng thái mặc định
    }

    /**
     * Xử lý logic khi nút "Thêm Khách Hàng" được nhấn.
     * Có thể là để tạo đặt phòng mới tại quầy (walk-in) hoặc thêm thông tin khách hàng vào phòng.
     */
    private void handleAddCustomer() {
        Toast.makeText(this, "Mở giao diện thêm thông tin khách hàng hoặc tạo đặt phòng Walk-in", Toast.LENGTH_SHORT).show();
        // TODO: Chuyển đến Activity hoặc hiển thị Dialog để nhập thông tin khách hàng mới
        // hoặc khởi tạo một quy trình đặt phòng mới tại quầy.
        // Ví dụ: Intent intent = new Intent(CheckInActivity.this, AddCustomerOrWalkInBookingActivity.class);
        // startActivity(intent);
    }

    /**
     * Xử lý logic khi nút "Nhận Phòng" được nhấn.
     * Đây là hành động chính để đánh dấu khách hàng đã nhận phòng.
     */
    private void handleCheckIn() {
        String reservationCode = edtReservationCode.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String idCard = etIdCard.getText().toString().trim();
        // Lấy giá trị đã chọn từ Spinner, kiểm tra null trước
        String selectedRoomType = spinnerRoomType.getSelectedItem() != null ? spinnerRoomType.getSelectedItem().toString() : "";
        String selectedRoomNumber = spinnerRoomNumber.getSelectedItem() != null ? spinnerRoomNumber.getSelectedItem().toString() : "";

        // TODO: Thực hiện validation các trường thông tin cần thiết trước khi nhận phòng
        if (reservationCode.isEmpty() || fullName.isEmpty() || idCard.isEmpty() ||
                selectedRoomType.isEmpty() || selectedRoomNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin và chọn phòng để nhận phòng.", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Đang thực hiện nhận phòng cho " + fullName + " vào phòng " + selectedRoomNumber, Toast.LENGTH_LONG).show();
        // TODO: Gửi yêu cầu API (PUT/POST) để cập nhật trạng thái đặt phòng thành "Checked-In"
        // và gán phòng cụ thể cho đặt phòng đó.
        // Ví dụ API: PUT /api/manager/bookings/{reservationCode}/checkin
        // Body có thể bao gồm: {"room_number": selectedRoomNumber, "actual_check_in_time": current_time, ...}
        //
        // Sau khi API thành công:
        // Toast.makeText(this, "Nhận phòng thành công!", Toast.LENGTH_SHORT).show();
        // finish(); // Đóng màn hình nhận phòng hoặc chuyển về dashboard
    }

    /**
     * Xử lý logic khi nút "Hủy" đặt phòng được nhấn.
     */
    private void handleCancelReservation() {
        String reservationCode = edtReservationCode.getText().toString().trim();
        if (reservationCode.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã đặt phòng để hủy.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Đang thực hiện hủy đặt phòng: " + reservationCode, Toast.LENGTH_SHORT).show();

        // TODO: Gửi yêu cầu API (PUT/DELETE) để cập nhật trạng thái đặt phòng thành "Cancelled"
        // Ví dụ API: PUT /api/manager/bookings/{reservationCode}/cancel
        //
        // Sau khi API thành công:
        // Toast.makeText(this, "Đặt phòng đã được hủy.", Toast.LENGTH_SHORT).show();
        // clearReservationDetails(); // Xóa thông tin trên UI
        // finish(); // Hoặc chuyển về dashboard
    }
}
