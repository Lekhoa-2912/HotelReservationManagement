package edu.poly.doancuoiki.AdapterDoanhThu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import edu.poly.doancuoiki.R;
import edu.poly.doancuoiki.AdapterDoanhThu.RevenueSummaryAdapter; // Import adapter mới
import edu.poly.doancuoiki.AdapterDoanhThu.RevenueEntry; // Import model mới

public class RevenueStatisticsActivity extends AppCompatActivity {

    // UI elements
    private TextView tvRevenueTitle;
    private LinearLayout layoutStartDate;
    private TextView tvStartDate;
    private LinearLayout layoutEndDate;
    private TextView tvEndDate;
    private Button btnViewReport;
    private TextView tvTotalRevenueAmount; // TextView để hiển thị tổng doanh thu
    private RecyclerView rvRevenueList; // RecyclerView cho chi tiết doanh thu

    // Data and Helper classes
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    private RevenueSummaryAdapter revenueAdapter;
    private List<RevenueEntry> currentRevenueDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_statistics); // Sử dụng layout chính

        // Ánh xạ UI elements
        tvRevenueTitle = findViewById(R.id.tv_revenue_title);
        layoutStartDate = findViewById(R.id.layout_start_date);
        tvStartDate = findViewById(R.id.tv_start_date);
        layoutEndDate = findViewById(R.id.layout_end_date);
        tvEndDate = findViewById(R.id.tv_end_date);
        btnViewReport = findViewById(R.id.btn_view_report);
        tvTotalRevenueAmount = findViewById(R.id.tv_total_revenue_amount); // Ánh xạ TextView tổng doanh thu
        rvRevenueList = findViewById(R.id.rv_revenue_list); // Ánh xạ RecyclerView

        // Thiết lập RecyclerView
        rvRevenueList.setLayoutManager(new LinearLayoutManager(this));
        currentRevenueDetails = new ArrayList<>();
        revenueAdapter = new RevenueSummaryAdapter(currentRevenueDetails);
        rvRevenueList.setAdapter(revenueAdapter);

        revenueAdapter.setOnItemClickListener(revenueEntry -> {
            Toast.makeText(RevenueStatisticsActivity.this, "Clicked: Phòng " + revenueEntry.getRoomNumber() + " - " + currencyFormatter.format(revenueEntry.getTotalAmount()), Toast.LENGTH_SHORT).show();
            // TODO: Xử lý khi click vào một mục doanh thu chi tiết
        });

        // Thiết lập sự kiện chọn ngày
        layoutStartDate.setOnClickListener(v -> showDatePickerDialog(tvStartDate));
        layoutEndDate.setOnClickListener(v -> showDatePickerDialog(tvEndDate));

        // Thiết lập sự kiện cho nút "Xem Báo Cáo"
        btnViewReport.setOnClickListener(v -> calculateAndDisplayRevenue());

        // Khởi tạo ngày mặc định
        initializeDatePickers();
    }

    private void showDatePickerDialog(final TextView targetTextView) {
        final Calendar c = Calendar.getInstance();
        String currentText = targetTextView.getText().toString();
        try {
            Date currentDate = dateFormatter.parse(currentText);
            if (currentDate != null) {
                c.setTime(currentDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    targetTextView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void initializeDatePickers() {
        Calendar calendar = Calendar.getInstance();
        tvEndDate.setText(dateFormatter.format(calendar.getTime()));

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        tvStartDate.setText(dateFormatter.format(calendar.getTime()));
    }

    /**
     * Tính toán và hiển thị tổng doanh thu bằng cách tổng hợp từ các item.
     * Đồng thời tải chi tiết doanh thu.
     */
    private void calculateAndDisplayRevenue() {
        String startDateStr = tvStartDate.getText().toString();
        String endDateStr = tvEndDate.getText().toString();

        if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Date startDate = dateFormatter.parse(startDateStr);
            Date endDate = dateFormatter.parse(endDateStr);

            if (startDate == null || endDate == null || endDate.before(startDate)) {
                Toast.makeText(this, "Ngày kết thúc phải sau ngày bắt đầu.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Đang tạo báo cáo từ " + startDateStr + " đến " + endDateStr, Toast.LENGTH_LONG).show();

            // Gọi API để lấy danh sách các RevenueEntry chi tiết
            callApiToGetRevenueDetails(startDateStr, endDateStr, new ApiCallback<List<RevenueEntry>>() {
                @Override
                public void onSuccess(List<RevenueEntry> detailsList) {
                    currentRevenueDetails.clear();
                    currentRevenueDetails.addAll(detailsList); // Cập nhật dữ liệu cho adapter
                    revenueAdapter.notifyDataSetChanged(); // Yêu cầu RecyclerView vẽ lại

                    double totalRevenue = 0;
                    for (RevenueEntry entry : detailsList) {
                        totalRevenue += entry.getTotalAmount(); // Cộng dồn tổng tiền từ mỗi item
                    }
                    tvTotalRevenueAmount.setText(currencyFormatter.format(totalRevenue)); // Hiển thị tổng doanh thu

                    Toast.makeText(RevenueStatisticsActivity.this, "Tải báo cáo thành công!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String error) {
                    tvTotalRevenueAmount.setText(currencyFormatter.format(0)); // Đặt về 0 nếu lỗi
                    currentRevenueDetails.clear();
                    revenueAdapter.notifyDataSetChanged();
                    Toast.makeText(RevenueStatisticsActivity.this, "Lỗi khi tải báo cáo: " + error, Toast.LENGTH_LONG).show();
                }
            });

        } catch (ParseException e) {
            Toast.makeText(this, "Định dạng ngày không hợp lệ. Vui lòng chọn lại.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // --- Các lớp Model giả định và API Callback ---

    /**
     * Giao diện callback giả định cho các cuộc gọi API.
     */
    private interface ApiCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }

    /**
     * Hàm giả định gọi API lấy danh sách chi tiết doanh thu (các item).
     * @param startDate Ngày bắt đầu.
     * @param endDate Ngày kết thúc.
     * @param callback Callback để xử lý kết quả (là List<RevenueEntry>).
     */
    private void callApiToGetRevenueDetails(String startDate, String endDate, ApiCallback<List<RevenueEntry>> callback) {
        // Đây là nơi bạn sẽ thực hiện cuộc gọi Retrofit/Volley thực tế
        new android.os.Handler().postDelayed(() -> {
            List<RevenueEntry> dummyDetails = new ArrayList<>();

            // Tạo dữ liệu giả lập dựa trên cấu trúc item mới
            // Giả lập dữ liệu cho khoảng thời gian 01/01/2025 - 31/01/2025
            if (startDate.equals("01/01/2025") && endDate.equals("31/01/2025")) {
                dummyDetails.add(new RevenueEntry("101", 500_000, "15/06/2025", "17/06/2025", 1_000_000));
                dummyDetails.add(new RevenueEntry("102", 750_000, "16/06/2025", "18/06/2025", 1_500_000));
                dummyDetails.add(new RevenueEntry("201", 1_000_000, "17/06/2025", "20/06/2025", 3_000_000));
                dummyDetails.add(new RevenueEntry("301", 600_000, "18/06/2025", "19/06/2025", 600_000));
                dummyDetails.add(new RevenueEntry("404", 800_000, "19/06/2025", "22/06/2025", 2_400_000));
            } else if (startDate.equals("01/02/2025")) { // Giả lập trường hợp không có dữ liệu
                // Trả về danh sách rỗng
            } else {
                callback.onFailure("Không thể lấy dữ liệu doanh thu cho khoảng thời gian này.");
                return; // Thoát khỏi hàm nếu lỗi
            }
            callback.onSuccess(dummyDetails);
        }, 1500); // Giả lập độ trễ mạng 1.5 giây
    }
}
