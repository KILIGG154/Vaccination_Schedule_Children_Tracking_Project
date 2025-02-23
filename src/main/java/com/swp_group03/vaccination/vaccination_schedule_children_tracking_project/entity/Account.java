package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
/*import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Account_ID;

    @Column(name = "username", length = 30,unique=true)
    @Size(min = 6, max = 30, message = "Tên đăng nhập phải có ít nhất 6 ký tự và nhiều nhất 30 ký tự !!")
    private String Username;

    @Column(name = "password", length = 50)
    @Size(min = 8, max = 20, message = "Mật khẩu phải có ít nhất 8 ký tự và nhiều nhất 20 ký tự !!")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W]).{8,20}$", message = "Mật Khẩu nên có ít nhất 1 ký tự đặc biệt và 1 chữ" +
            " in hoa !!")
    private String Password;

    @Column(name = "first_Name", length = 100)
    @Nationalized
    @Size(max = 100, message = "first_Name không được vượt quá 100 ký tự !!")
    private String First_Name;

    @Column(name = "last_Name", length = 100)
    @Nationalized
    @Size(max = 100, message = "last_Name không được vượt quá 100 ký tự !!")
    private String Last_Name;

    @Column(name = "email", length = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Please enter right email")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự !!")
    private String Email;

    @Column(name = "phoneNumber", length = 10)
    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
    private String Phone_number;

    @Column(name = "address", length = 100)
    @Nationalized
    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự !!")
    private String Address;

    @Column(name = "gender", length = 6)
    @Size(max = 6, message = "Giới tính không được vượt quá 6 ký tự !!")
    private String Gender;

    @Column(name = "status")
    private boolean Status;

    @Column(name = "URL_image")
    private String urlIimage;

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
        this.urlIimage = url_image;
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

    public void setFirstName(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLastName(String last_Name) {
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
        return urlIimage;
    }

    public void setUrlimage(String urlImage) {
        this.urlIimage = urlImage;
    }

    public Account_Role getRole() {
        return role;
    }

    public void setRole(Account_Role role) {
        this.role = role;
    }
}
