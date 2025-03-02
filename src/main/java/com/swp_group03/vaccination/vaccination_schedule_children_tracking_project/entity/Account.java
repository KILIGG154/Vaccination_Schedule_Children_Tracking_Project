package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;
import jakarta.persistence.CascadeType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "AccountId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;

    @Column(name = "Username", length = 30,unique=true)
    @Size(min = 2, max = 30, message = "Tên đăng nhập phải có ít nhất 6 ký tự và nhiều nhất 30 ký tự !!")
    private String username;

    @Column(name = "Password")
//    @Size(min = 8, max = 20, message = "Mật khẩu phải có ít nhất 8 ký tự và nhiều nhất 20 ký tự !!")
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\W]).{8,20}$", message = "Mật Khẩu nên có ít nhất 1 ký tự đặc biệt và 1 chữ" +
//            " in hoa !!")
    private String password;

    @Column(name = "FirstName", length = 100)
    @Nationalized
    @Size(max = 100, message = "first_Name không được vượt quá 100 ký tự !!")
    private String firstName;

    @Column(name = "LastName", length = 100)
    @Nationalized
    @Size(max = 100, message = "last_Name không được vượt quá 100 ký tự !!")
    private String lastName;

    @Column(name = "Email", length = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Please enter right email")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự !!")
    private String email;

    @Column(name = "PhoneNumber", length = 10)
    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
    private String phoneNumber;

    @Column(name = "Address", length = 100)
    @Nationalized
    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự !!")
    private String address;

    @Enumerated(EnumType.STRING) // Use STRING to store the enum as a string in the database
    @Column(name = "Gender", length = 6)
    private Gender gender; // Change from String to Gender enum

    @Column(name = "Status")
    private boolean status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "Account_Role",
            joinColumns = @JoinColumn(name = "AccountId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Role> roles = new HashSet<>();


    public Account() {}

    public Account(String username) {
        this.username = username;
    }

    public Account(String accountId, String username, String password, String firstName, String lastName, String email, String phoneNumber, String address, Gender gender, boolean status, String urlImage, Set<Role> roles) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.status = status;
        this.roles = roles;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Phương thức để thêm một Role
    public void addRole(Role role) {
        this.roles.add(role);
        role.getAccounts().add(this); // Cập nhật mối quan hệ hai chiều
    }

    // Phương thức để xóa một Role
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getAccounts().remove(this); // Cập nhật mối quan hệ hai chiều
    }

    //
//    public Account(String username, String password, String first_Name, String last_Name, String email,
//                   String phone_number, String address, String gender, boolean status, String url_image,
//                   Account_Role role) {
//        this.username = username;
//        this.password = password;
//        First_Name = first_Name;
//        Last_Name = last_Name;
//        Email = email;
//        Phone_number = phone_number;
//        Address = address;
//        Gender = gender;
//        Status = status;
//        this.urlIimage = url_image;
//        this.role = role;
//    }
//
//    public String getAccount_ID() {
//        return Account_ID;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirst_Name() {
//        return First_Name;
//    }
//
//    public void setFirstName(String first_Name) {
//        First_Name = first_Name;
//    }
//
//    public String getLast_Name() {
//        return Last_Name;
//    }
//
//    public void setLastName(String last_Name) {
//        Last_Name = last_Name;
//    }
//
//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String email) {
//        Email = email;
//    }
//
//    public String getPhone_number() {
//        return Phone_number;
//    }
//
//    public void setPhone_number(String phone_number) {
//        Phone_number = phone_number;
//    }
//
//    public String getAddress() {
//        return Address;
//    }
//
//    public void setAddress(String address) {
//        Address = address;
//    }
//
//    public String getGender() {
//        return Gender;
//    }
//
//    public void setGender(String gender) {
//        Gender = gender;
//    }
//
//    public boolean isStatus() {
//        return Status;
//    }
//
//    public void setStatus(boolean status) {
//        Status = status;
//    }
//
//    public String getUrl_image() {
//        return urlIimage;
//    }
//
//    public void setUrlimage(String urlImage) {
//        this.urlIimage = urlImage;
//    }
//
//    public Account_Role getRole() {
//        return role;
//    }
//
//    public void setRole(Account_Role role) {
//        this.role = role;
//    }
}
