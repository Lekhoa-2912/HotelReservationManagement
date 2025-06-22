// app/src/main/java/edu/poly/doancuoiki/DailyCheckInsActivity.java
package edu.poly.doancuoiki.AdapterDanhSachNhanPhong; // Đã sửa package cho Activity

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.poly.doancuoiki.R;

public class DailyCheckInsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DailyCheckInsAdapter adapter;
    private List<CheckInItem> checkInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đã sửa tên layout cho khớp với file XML bạn cung cấp
        setContentView(R.layout.danhsachnhanphong_activity);

        recyclerView = findViewById(R.id.rv_daily_check_ins); // ID của RecyclerView trong activity_daily_check_ins.xml
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkInList = new ArrayList<>();
        // Dữ liệu giả lập
        checkInList.add(new CheckInItem("35", "Nguyễn Duy Cương", "16520147", "Phòng 206", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("34", "Nguyễn Duy Cương", "16520147", "Phòng 102", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("33", "Nguyen Duy Cuong", "123456", "Phòng 201", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("32", "Lê Thị D", "998877665", "Phòng 305", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("31", "Phạm Văn E", "112233445", "Phòng 401", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("30", "Mai Thanh F", "667788990", "Phòng 105", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("29", "Hoàng Kim G", "121212121", "Phòng 202", "5/23/2018", "5/24/2018"));
        checkInList.add(new CheckInItem("28", "Đào Văn H", "343434343", "Phòng 301", "5/23/2018", "5/24/2018"));

        adapter = new DailyCheckInsAdapter(checkInList);
        adapter.setOnItemClickListener(checkInItem -> {
            Toast.makeText(DailyCheckInsActivity.this, "Bạn đã click vào ID: " + checkInItem.getId() + " - " + checkInItem.getCustomerName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
    }
}
