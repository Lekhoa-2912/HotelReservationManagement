package edu.poly.doancuoiki.AdapterDanhSachNhanPhong;


public class CheckInItem {
    private String id; // Mã nhận phòng
    private String customerName;
    private String cmnd;
    private String roomInfo; // Tên phòng hoặc loại phòng (Phòng 206)
    private String checkInDate;
    private String checkOutDate;

    public CheckInItem(String id, String customerName, String cmnd, String roomInfo, String checkInDate, String checkOutDate) {
        this.id = id;
        this.customerName = customerName;
        this.cmnd = cmnd;
        this.roomInfo = roomInfo;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters for all fields
    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCmnd() { return cmnd; }
    public String getRoomInfo() { return roomInfo; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
}
