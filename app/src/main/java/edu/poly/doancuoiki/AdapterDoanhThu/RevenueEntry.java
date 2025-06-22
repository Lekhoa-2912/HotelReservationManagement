package edu.poly.doancuoiki.AdapterDoanhThu;

public class RevenueEntry {
    private String roomNumber;
    private double unitPrice;
    private String checkInDate;
    private String checkOutDate;
    private double totalAmount; // Đây là trường chúng ta sẽ tổng hợp

    public RevenueEntry(String roomNumber, double unitPrice, String checkInDate, String checkOutDate, double totalAmount) {
        this.roomNumber = roomNumber;
        this.unitPrice = unitPrice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

    // Getters for all fields
    public String getRoomNumber() { return roomNumber; }
    public double getUnitPrice() { return unitPrice; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public double getTotalAmount() { return totalAmount; } // Getter cho tổng tiền
}
