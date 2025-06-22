package edu.poly.doancuoiki;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
// import android.widget.EditText; // Không còn sử dụng EditText cho các trường hiển thị
import android.widget.Spinner;
import android.widget.TextView; // Bây giờ sử dụng TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class InvoiceActivity extends AppCompatActivity {

    // 1. Khai báo các biến để tham chiếu đến các thành phần UI
    // Header
    private TextView tvInvoiceTitle;

    // Card 1: Thông tin chi tiết hóa đơn (Tất cả đã chuyển thành TextView)
    private TextView tvInvoiceId;
    private TextView tvInvoiceDate;
    private TextView tvEmployeeName;
    private TextView tvCustomerName;
    private TextView tvRoomNumber;
    private TextView tvCheckInDate;
    private TextView tvCheckOutDate;
    private TextView tvTotalNights;

    // Card 2: Chi tiết các khoản phí (Tất cả đã chuyển thành TextView)
    private TextView tvRoomCharge;
    private TextView tvServiceCharge;
    private TextView tvDiscountAmount;
    private TextView tvTaxAmount;

    // Card 3: Tổng tiền & Thanh toán (Tổng tiền đã chuyển thành TextView)
    private TextView tvGrandTotal;
    private Spinner spinnerPaymentMethod;
    private Spinner spinnerPaymentStatus;

    // Card 4: Các nút chức năng
    private Button btnPrintInvoice;
    private Button btnProcessPayment;
    private Button btnCloseInvoice;

    // Định dạng tiền tệ
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Định dạng VNĐ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon); // Sử dụng layout mới đã sửa đổi

        // 2. Ánh xạ các thành phần UI bằng ID của chúng (Đã cập nhật từ edt_ sang tv_)
        tvInvoiceTitle = findViewById(R.id.tv_invoice_title);

        tvInvoiceId = findViewById(R.id.tv_invoice_id);
        tvInvoiceDate = findViewById(R.id.tv_invoice_date);
        tvEmployeeName = findViewById(R.id.tv_employee_name);
        tvCustomerName = findViewById(R.id.tv_customer_name);
        tvRoomNumber = findViewById(R.id.tv_room_number);
        tvCheckInDate = findViewById(R.id.tv_check_in_date);
        tvCheckOutDate = findViewById(R.id.tv_check_out_date);
        tvTotalNights = findViewById(R.id.tv_total_nights);

        tvRoomCharge = findViewById(R.id.tv_room_charge);
        tvServiceCharge = findViewById(R.id.tv_service_charge);
        tvDiscountAmount = findViewById(R.id.tv_discount_amount);
        tvTaxAmount = findViewById(R.id.tv_tax_amount);

        tvGrandTotal = findViewById(R.id.tv_grand_total);
        spinnerPaymentMethod = findViewById(R.id.spinner_payment_method);
        spinnerPaymentStatus = findViewById(R.id.spinner_payment_status);

        btnPrintInvoice = findViewById(R.id.btn_print_invoice);
        btnProcessPayment = findViewById(R.id.btn_process_payment);
        btnCloseInvoice = findViewById(R.id.btn_close_invoice);

        // 3. Thiết lập lắng nghe sự kiện (OnClickListener cho Buttons)
        btnPrintInvoice.setOnClickListener(v -> handlePrintInvoice());
        btnProcessPayment.setOnClickListener(v -> handleProcessPayment());
        btnCloseInvoice.setOnClickListener(v -> finish()); // Đóng Activity hiện tại

        // 4. Thiết lập lắng nghe sự kiện cho Spinners
        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMethod = parent.getItemAtPosition(position).toString();
                Toast.makeText(InvoiceActivity.this, "Phương thức thanh toán: " + selectedMethod, Toast.LENGTH_SHORT).show();
                // TODO: Xử lý logic khi phương thức thanh toán thay đổi
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinnerPaymentStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                Toast.makeText(InvoiceActivity.this, "Trạng thái thanh toán: " + selectedStatus, Toast.LENGTH_SHORT).show();
                // TODO: Xử lý logic khi trạng thái thanh toán thay đổi
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // 5. Điền dữ liệu vào hóa đơn khi Activity được tạo
        String invoiceId = getIntent().getStringExtra("invoice_id");
        if (invoiceId != null && !invoiceId.isEmpty()) {
            loadInvoiceDetails(invoiceId);
        } else {
            Toast.makeText(this, "Không tìm thấy ID hóa đơn, hiển thị dữ liệu mẫu.", Toast.LENGTH_LONG).show();
            fillDummyInvoiceDetails();
        }
    }

    /**
     * Tải thông tin hóa đơn từ API dựa trên invoiceId.
     * @param invoiceId Mã hóa đơn cần tải.
     */
    private void loadInvoiceDetails(String invoiceId) {
        Toast.makeText(this, "Đang tải hóa đơn: " + invoiceId, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu API (GET) đến backend để lấy thông tin hóa đơn chi tiết
        // Giả sử API trả về một đối tượng `InvoiceDetails`
        callApiToGetInvoiceDetails(invoiceId, new ApiCallback<InvoiceDetails>() {
            @Override
            public void onSuccess(InvoiceDetails details) {
                displayInvoiceDetails(details);
                Toast.makeText(InvoiceActivity.this, "Tải hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(String error) {
                Toast.makeText(InvoiceActivity.this, "Không tải được hóa đơn: " + error, Toast.LENGTH_LONG).show();
                fillDummyInvoiceDetails(); // Fallback to dummy data on error
            }
        });
    }

    /**
     * Điền dữ liệu hóa đơn vào các trường UI (đã cập nhật cho TextViews).
     * @param invoiceDetails Đối tượng chứa thông tin hóa đơn.
     */
    private void displayInvoiceDetails(InvoiceDetails invoiceDetails) {
        tvInvoiceId.setText(invoiceDetails.getInvoiceId());
        tvInvoiceDate.setText(invoiceDetails.getInvoiceDate());
        tvEmployeeName.setText(invoiceDetails.getEmployeeName());
        tvCustomerName.setText(invoiceDetails.getCustomerName());
        tvRoomNumber.setText(invoiceDetails.getRoomNumber());
        tvCheckInDate.setText(invoiceDetails.getCheckInDate());
        tvCheckOutDate.setText(invoiceDetails.getCheckOutDate());
        tvTotalNights.setText(invoiceDetails.getTotalNights() + " đêm");

        tvRoomCharge.setText(currencyFormatter.format(invoiceDetails.getRoomCharge()));
        tvServiceCharge.setText(currencyFormatter.format(invoiceDetails.getServiceCharge()));
        tvDiscountAmount.setText(currencyFormatter.format(invoiceDetails.getDiscountAmount()));
        tvTaxAmount.setText(currencyFormatter.format(invoiceDetails.getTaxAmount()));

        tvGrandTotal.setText(currencyFormatter.format(invoiceDetails.getGrandTotal()));

        // Đặt giá trị cho Spinners
        spinnerPaymentMethod.setSelection(getSpinnerIndex(spinnerPaymentMethod, invoiceDetails.getPaymentMethod()));
        spinnerPaymentStatus.setSelection(getSpinnerIndex(spinnerPaymentStatus, invoiceDetails.getPaymentStatus()));
    }

    /**
     * Hàm giả định để điền dữ liệu hóa đơn mẫu.
     * Được dùng khi không có dữ liệu thật hoặc để test layout.
     */
    private void fillDummyInvoiceDetails() {
        tvInvoiceId.setText("HD" + System.currentTimeMillis());
        tvInvoiceDate.setText("21/06/2025");
        tvEmployeeName.setText("Nguyễn Thị B");
        tvCustomerName.setText("Trần Văn C");
        tvRoomNumber.setText("Phòng 206 (Deluxe)");
        tvCheckInDate.setText("18/06/2025");
        tvCheckOutDate.setText("21/06/2025");
        tvTotalNights.setText("3 đêm");

        tvRoomCharge.setText(currencyFormatter.format(3_000_000));
        tvServiceCharge.setText(currencyFormatter.format(500_000));
        tvDiscountAmount.setText(currencyFormatter.format(-100_000)); // Giảm giá là số âm
        tvTaxAmount.setText(currencyFormatter.format(350_000));

        tvGrandTotal.setText(currencyFormatter.format(3_750_000));

        spinnerPaymentMethod.setSelection(getSpinnerIndex(spinnerPaymentMethod, "Tiền mặt"));
        spinnerPaymentStatus.setSelection(getSpinnerIndex(spinnerPaymentStatus, "Chưa thanh toán"));
    }

    /**
     * Xử lý logic khi nút "In Hóa Đơn" được nhấn.
     */
    private void handlePrintInvoice() {
        String invoiceId = tvInvoiceId.getText().toString(); // Lấy từ TextView
        Toast.makeText(this, "Đang xử lý in hóa đơn: " + invoiceId, Toast.LENGTH_SHORT).show();
        // TODO: Gửi yêu cầu in hóa đơn đến một máy in Bluetooth/Wi-Fi hoặc tạo PDF
    }

    /**
     * Xử lý logic khi nút "Thanh Toán" được nhấn.
     */
    private void handleProcessPayment() {
        String invoiceId = tvInvoiceId.getText().toString(); // Lấy từ TextView
        String paymentMethod = spinnerPaymentMethod.getSelectedItem().toString();
        String currentStatus = spinnerPaymentStatus.getSelectedItem().toString();
        // Lấy tổng tiền và chuyển về dạng số Double để gửi API
        String grandTotalClean = tvGrandTotal.getText().toString().trim().replaceAll("[^\\d]", ""); // Lấy từ TextView
        double grandTotalAmount = 0;
        try {
            grandTotalAmount = Double.parseDouble(grandTotalClean);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Tổng tiền không hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đang xử lý thanh toán cho hóa đơn " + invoiceId + " bằng " + paymentMethod, Toast.LENGTH_LONG).show();
        // TODO: Gửi yêu cầu API (POST/PUT) để xử lý thanh toán
    }

    /**
     * Helper method to get the index of an item in a Spinner.
     * @param spinner The Spinner to search.
     * @param item The string value of the item to find.
     * @return The index of the item, or 0 if not found.
     */
    private int getSpinnerIndex(Spinner spinner, String item) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equalsIgnoreCase(item)) {
                    return i;
                }
            }
        }
        return 0; // Default to first item if not found
    }

    // --- Các lớp Model giả định cho mục đích minh họa API ---

    /**
     * Lớp model giả định cho thông tin hóa đơn.
     * Đảm bảo tên package phù hợp với cấu trúc dự án của bạn.
     */
    private static class InvoiceDetails {
        private String invoiceId;
        private String invoiceDate;
        private String employeeName;
        private String customerName;
        private String roomNumber;
        private String checkInDate;
        private String checkOutDate;
        private int totalNights;
        private double roomCharge;
        private double serviceCharge;
        private double discountAmount;
        private double taxAmount;
        private double grandTotal;
        private String paymentMethod;
        private String paymentStatus;

        public InvoiceDetails(String invoiceId, String invoiceDate, String employeeName, String customerName, String roomNumber, String checkInDate, String checkOutDate, int totalNights, double roomCharge, double serviceCharge, double discountAmount, double taxAmount, double grandTotal, String paymentMethod, String paymentStatus) {
            this.invoiceId = invoiceId;
            this.invoiceDate = invoiceDate;
            this.employeeName = employeeName;
            this.customerName = customerName;
            this.roomNumber = roomNumber;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.totalNights = totalNights;
            this.roomCharge = roomCharge;
            this.serviceCharge = serviceCharge;
            this.discountAmount = discountAmount;
            this.taxAmount = taxAmount;
            this.grandTotal = grandTotal;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
        }

        // Getters for all fields
        public String getInvoiceId() { return invoiceId; }
        public String getInvoiceDate() { return invoiceDate; }
        public String getEmployeeName() { return employeeName; }
        public String getCustomerName() { return customerName; }
        public String getRoomNumber() { return roomNumber; }
        public String getCheckInDate() { return checkInDate; }
        public String getCheckOutDate() { return checkOutDate; }
        public int getTotalNights() { return totalNights; }
        public double getRoomCharge() { return roomCharge; }
        public double getServiceCharge() { return serviceCharge; }
        public double getDiscountAmount() { return discountAmount; }
        public double getTaxAmount() { return taxAmount; }
        public double getGrandTotal() { return grandTotal; }
        public String getPaymentMethod() { return paymentMethod; }
        public String getPaymentStatus() { return paymentStatus; }
    }

    /**
     * Giao diện callback giả định cho các cuộc gọi API.
     * Đặt interface này trong một file riêng (ví dụ: ApiCallback.java)
     * hoặc ở đây nếu chỉ dùng cho Activity này.
     */
    private interface ApiCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

    /**
     * Hàm giả định gọi API lấy chi tiết hóa đơn.
     * @param invoiceId Mã hóa đơn.
     * @param callback Callback để xử lý kết quả.
     */
    private void callApiToGetInvoiceDetails(String invoiceId, ApiCallback<InvoiceDetails> callback) {
        // Đây là nơi bạn sẽ thực hiện cuộc gọi Retrofit/Volley thực tế
        new android.os.Handler().postDelayed(() -> {
            if ("HD123456789".equals(invoiceId)) {
                // Mô phỏng dữ liệu hóa đơn thật
                callback.onSuccess(new InvoiceDetails(
                        "HD123456789", "21/06/2025", "Nguyễn Thị B", "Trần Văn C",
                        "Phòng 206 (Deluxe)", "18/06/2025", "21/06/2025", 3,
                        3_000_000, 500_000, -100_000, 350_000, 3_750_000,
                        "Tiền mặt", "Chưa thanh toán"
                ));
            } else {
                callback.onFailure("Không tìm thấy hóa đơn với ID: " + invoiceId);
            }
        }, 1000); // Giả lập độ trễ mạng
    }

    // TODO: Thêm các hàm callApiToProcessPayment, callApiToGeneratePrintableInvoice, v.v. tương tự
}

