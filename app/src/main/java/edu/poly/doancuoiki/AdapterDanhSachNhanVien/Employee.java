package edu.poly.doancuoiki.AdapterDanhSachNhanVien;


public class Employee {
    private String username;
    private String fullName;
    private String type; // Loại nhân viên (ví dụ: Quản lý, Lễ tân)
    private String idCard; // CMND
    private String phoneNumber; // SĐT
    private String address;

    public Employee(String username, String fullName, String type, String idCard, String phoneNumber, String address) {
        this.username = username;
        this.fullName = fullName;
        this.type = type;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters for all fields
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getType() { return type; }
    public String getIdCard() { return idCard; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
}
