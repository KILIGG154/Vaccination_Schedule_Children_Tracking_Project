package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Child")
public class Child{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int child_id;

    @Column(name = "name",length = 100)
//    @NotBlank(message = "Vui lòng nhập tên cảu trẻ")
    private String name;
    @Temporal(TemporalType.DATE)
    @Column(name = "Date_Of_Birth")
    private Date dob;
    @Column(name = "Height")
//    @NotBlank(message = "Vui lòng nhập chiều cao của trẻ")
    private String height;
    @Column(name = "Weight")
//    @NotBlank(message = "Vui lòng nhập cân nặng của trẻ")
    private String weight;
    @Enumerated(EnumType.STRING)
    @Column(name = "Gender")
//    @NotBlank(message = "Vui lòng chọn giới tính của trẻ")
    private Gender gender;
    @Column(name = "Url_Image")
    private String urlImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Account_ID")
    private Account account;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();


    public Child() {
    }

    public Child(String name, Date dob, String height, String weight, Gender gender, String urlImage, Account account, List<Booking> bookings) {
        this.name = name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.urlImage = urlImage;
        this.account = account;
        this.bookings = bookings;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
