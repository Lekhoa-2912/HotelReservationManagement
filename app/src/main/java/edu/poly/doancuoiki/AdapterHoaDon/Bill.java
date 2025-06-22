package edu.poly.doancuoiki.AdapterHoaDon;

public class Bill {
    private String roomNumber;    // Số phòng
    private double unitPrice;     // Đơn giá
    private String checkInDate;   // Ngày nhận phòng
    private String checkOutDate;  // Ngày trả phòng
    private double totalAmount;   // Tổng tiền / Thành tiền

    // Constructor chính cho hóa đơn tiền phòng
    public Bill(String roomNumber, double unitPrice, String checkInDate, String checkOutDate, double totalAmount) {
        this.roomNumber = roomNumber;
        this.unitPrice = unitPrice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

    // --- Getters ---
    public String getRoomNumber() {
        return roomNumber;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // --- Setters (Nếu cần thiết, có thể thêm vào) ---
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}