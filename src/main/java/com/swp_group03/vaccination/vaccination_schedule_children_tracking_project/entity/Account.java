package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import jakarta.persistence.CascadeType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "AccountId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;

    @Column(name = "Username", length = 30, unique = true)
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
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<WorkingSchedule> workingSchedules = new HashSet<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Child> child;

    public Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String username, String password, String firstName, String lastName, String email, String phoneNumber, String address, Gender gender, boolean status, Set<Role> roles, Set<WorkingSchedule> workingSchedules, List<Child> child) {
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
        this.workingSchedules = workingSchedules;
        this.child = child;
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
}

