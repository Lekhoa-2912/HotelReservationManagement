package edu.poly.doancuoiki.AdapterDanhSachNhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.poly.doancuoiki.R; // Đảm bảo import đúng R class

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;
    private OnItemClickListener listener; // (Tùy chọn) Để xử lý click vào item

    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item nhân viên
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsach, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee currentEmployee = employeeList.get(position);

        // Đặt dữ liệu vào các TextView
        holder.tvUsername.setText(currentEmployee.getUsername());
        holder.tvFullName.setText(currentEmployee.getFullName());
        holder.tvType.setText(currentEmployee.getType());
        holder.tvIdCard.setText(currentEmployee.getIdCard());
        holder.tvPhoneNumber.setText(currentEmployee.getPhoneNumber());
        holder.tvAddress.setText(currentEmployee.getAddress());

        // Đặt màu nền xen kẽ
        if (position % 2 == 0) { // Hàng chẵn (0, 2, 4...)
            holder.employeeItemContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
        } else { // Hàng lẻ (1, 3, 5...)
            holder.employeeItemContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray_background_light));
        }

        // Thiết lập lắng nghe sự kiện click cho toàn bộ item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentEmployee);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    // ViewHolder class - Giữ các references đến các View con trong mỗi item
    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout employeeItemContainer; // Để thay đổi màu nền của cả item
        TextView tvUsername;
        TextView tvFullName;
        TextView tvType;
        TextView tvIdCard;
        TextView tvPhoneNumber;
        TextView tvAddress;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeItemContainer = (LinearLayout) itemView; // Lấy reference đến LinearLayout gốc của item
            tvUsername = itemView.findViewById(R.id.tv_id_column);
            tvFullName = itemView.findViewById(R.id.tv_customer_name_column);
            tvType = itemView.findViewById(R.id.tv_cmnd_column);
            tvIdCard = itemView.findViewById(R.id.tv_room_info_column);
            tvPhoneNumber = itemView.findViewById(R.id.tv_check_in_date_column);
            tvAddress = itemView.findViewById(R.id.tv_check_out_date_column);
        }
    }
}
