package edu.poly.doancuoiki.AdapterDoanhThu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.poly.doancuoiki.R; // Đảm bảo import đúng R
import edu.poly.doancuoiki.AdapterDoanhThu.RevenueEntry; // Đảm bảo import đúng lớp RevenueEntry

public class RevenueSummaryAdapter extends RecyclerView.Adapter<RevenueSummaryAdapter.RevenueViewHolder> {

    private List<RevenueEntry> revenueList;
    private OnItemClickListener listener;
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Định dạng VNĐ

    public interface OnItemClickListener {
        void onItemClick(RevenueEntry revenueEntry);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RevenueSummaryAdapter(List<RevenueEntry> revenueList) {
        this.revenueList = revenueList;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item: item_revenue_summary
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doanhthu, parent, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        RevenueEntry currentItem = revenueList.get(position);

        // Đặt dữ liệu vào các TextView mới
        holder.tvRoomNumber.setText(currentItem.getRoomNumber());
        holder.tvUnitPrice.setText(currencyFormatter.format(currentItem.getUnitPrice()));
        holder.tvCheckInDate.setText(currentItem.getCheckInDate());
        holder.tvCheckOutDate.setText(currentItem.getCheckOutDate());
        holder.tvTotalAmount.setText(currencyFormatter.format(currentItem.getTotalAmount())); // Hiển thị tổng tiền của item

        // Đặt màu nền xen kẽ (trắng và xám nhạt)
        if (position % 2 == 0) { // Hàng chẵn (0, 2, 4...)
            holder.revenueItemContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
        } else { // Hàng lẻ (1, 3, 5...)
            holder.revenueItemContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray_background_light));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return revenueList.size();
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        LinearLayout revenueItemContainer;
        TextView tvRoomNumber;
        TextView tvUnitPrice;
        TextView tvCheckInDate;
        TextView tvCheckOutDate;
        TextView tvTotalAmount;

        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            revenueItemContainer = (LinearLayout) itemView;
            tvRoomNumber = itemView.findViewById(R.id.tv_room_number);
            tvUnitPrice = itemView.findViewById(R.id.tv_unit_price);
            tvCheckInDate = itemView.findViewById(R.id.tv_check_in_date);
            tvCheckOutDate = itemView.findViewById(R.id.tv_check_out_date);
            tvTotalAmount = itemView.findViewById(R.id.tv_total_amount); // Ánh xạ tv_total_amount
        }
    }
}
