// app/src/main/java/edu/poly/doancuoiki/adapters/DailyCheckInsAdapter.java
package edu.poly.doancuoiki.AdapterDanhSachNhanPhong; // Đã sửa package cho Adapter

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.poly.doancuoiki.R; // Đã sửa đường dẫn R


public class DailyCheckInsAdapter extends RecyclerView.Adapter<DailyCheckInsAdapter.CheckInViewHolder> {

    private List<CheckInItem> checkInList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CheckInItem checkInItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DailyCheckInsAdapter(List<CheckInItem> checkInList) {
        this.checkInList = checkInList;
    }

    @NonNull
    @Override
    public CheckInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Đã sửa tên layout item cho khớp với file XML bạn cung cấp
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsach, parent, false);
        return new CheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInViewHolder holder, int position) {
        CheckInItem currentItem = checkInList.get(position);

        holder.tvIdColumn.setText(currentItem.getId());
        holder.tvCustomerNameColumn.setText(currentItem.getCustomerName());
        holder.tvCmndColumn.setText(currentItem.getCmnd());
        holder.tvRoomInfoColumn.setText(currentItem.getRoomInfo());
        holder.tvCheckInDateColumn.setText(currentItem.getCheckInDate());
        holder.tvCheckOutDateColumn.setText(currentItem.getCheckOutDate());

        // Đặt màu nền xen kẽ
        if (position == 0) { // Hàng đầu tiên
            holder.itemBackgroundContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green_primary_button));
            int whiteColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white);
            holder.tvIdColumn.setTextColor(whiteColor);
            holder.tvCustomerNameColumn.setTextColor(whiteColor);
            holder.tvCmndColumn.setTextColor(whiteColor);
            holder.tvRoomInfoColumn.setTextColor(whiteColor);
            holder.tvCheckInDateColumn.setTextColor(whiteColor);
            holder.tvCheckOutDateColumn.setTextColor(whiteColor);
        } else if (position % 2 != 0) { // Hàng lẻ
            holder.itemBackgroundContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray_background_light));
            int greenDarkText = ContextCompat.getColor(holder.itemView.getContext(), R.color.green_text_dark);
            holder.tvIdColumn.setTextColor(greenDarkText);
            holder.tvCustomerNameColumn.setTextColor(greenDarkText);
            holder.tvCmndColumn.setTextColor(greenDarkText);
            holder.tvRoomInfoColumn.setTextColor(greenDarkText);
            holder.tvCheckInDateColumn.setTextColor(greenDarkText);
            holder.tvCheckOutDateColumn.setTextColor(greenDarkText);
        } else { // Hàng chẵn
            holder.itemBackgroundContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
            int greenDarkText = ContextCompat.getColor(holder.itemView.getContext(), R.color.green_text_dark);
            holder.tvIdColumn.setTextColor(greenDarkText);
            holder.tvCustomerNameColumn.setTextColor(greenDarkText);
            holder.tvCmndColumn.setTextColor(greenDarkText);
            holder.tvRoomInfoColumn.setTextColor(greenDarkText);
            holder.tvCheckInDateColumn.setTextColor(greenDarkText);
            holder.tvCheckOutDateColumn.setTextColor(greenDarkText);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkInList.size();
    }

    public static class CheckInViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemBackgroundContainer;
        TextView tvIdColumn;
        TextView tvCustomerNameColumn;
        TextView tvCmndColumn;
        TextView tvRoomInfoColumn;
        TextView tvCheckInDateColumn;
        TextView tvCheckOutDateColumn;

        public CheckInViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBackgroundContainer = (LinearLayout) itemView;
            tvIdColumn = itemView.findViewById(R.id.tv_id_column);
            tvCustomerNameColumn = itemView.findViewById(R.id.tv_customer_name_column);
            tvCmndColumn = itemView.findViewById(R.id.tv_cmnd_column);
            tvRoomInfoColumn = itemView.findViewById(R.id.tv_room_info_column);
            tvCheckInDateColumn = itemView.findViewById(R.id.tv_check_in_date_column);
            tvCheckOutDateColumn = itemView.findViewById(R.id.tv_check_out_date_column);
        }
    }
}
