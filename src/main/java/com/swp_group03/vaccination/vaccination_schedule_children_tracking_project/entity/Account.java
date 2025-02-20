package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
/*import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Account_ID;

    @Column(name = "username", length = 30,unique=true)
    private String Username;

    @Column(name = "password", length = 50, nullable = false)
    @NotBlank(message = "Vui lòng không để trống Password !!")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W]).{8,20}$", message = "Mật Khẩu nên có ít nhất 1 ký tự đặc biệt và 1 chữ" +
            " in hoa")
    private String Password;

    @Column(name = "first_Name", length = 100, nullable = false)
    @Nationalized
    @NotBlank(message = "Vui lòng không để trống Tên !!")
    private String First_Name;

    @Column(name = "last_Name", length = 100, nullable = false)
    @NotBlank(message = "Vui lòng không để trống Tên !!")
    private String Last_Name;

    @Column(name = "email", length = 50, nullable = false)
    @NotBlank(message = "Vui lòng không để trống Password !!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Please enter right email")
    private String Email;

    @Column(name = "phoneNumber", length = 10,nullable = false)
    @NotBlank(message = "Vui lòng không để trống số điện thoại !!")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
    private String Phone_number;

    @Column(name = "address", length = 100,nullable = false)
    @Nationalized
    @NotBlank(message = "Vui lòng không để trống Địa chỉ !!")
    private String Address;

    @Column(name = "gender", length = 6, nullable = false)
    @NotBlank(message = "Vui lòng không để trống Giới tính !!")
    private String Gender;

    @Column(name = "status")
    private boolean Status;

    @Column(name = "URL_image")
    private String url_image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_ID")
    private Account_Role role;

    public Account() {

    }

    public Account(String username, String password, String first_Name, String last_Name, String email,
                   String phone_number, String address, String gender, boolean status, String url_image,
                   Account_Role role) {
        Username = username;
        Password = password;
        First_Name = first_Name;
        Last_Name = last_Name;
        Email = email;
        Phone_number = phone_number;
        Address = address;
        Gender = gender;
        Status = status;
        this.url_image = url_image;
        this.role = role;
    }

    public String getAccount_ID() {
        return Account_ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public Account_Role getRole() {
        return role;
    }

    public void setRole(Account_Role role) {
        this.role = role;
    }
}
