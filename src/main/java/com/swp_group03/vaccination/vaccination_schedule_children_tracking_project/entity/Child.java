package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "Child")
public class Child{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String child_id;

    @Column(name = "Name",length = 100)
    @NotBlank(message = "Vui lòng nhập tên cảu trẻ")
    private String child_name;
    @Temporal(TemporalType.DATE)
    @Column(name = "Date_Of_Birth")
    private Date dob;
    @Column(name = "Height")
    @NotBlank(message = "Vui lòng nhập chiều cao của trẻ")
    private String height;
    @Column(name = "Weight")
    @NotBlank(message = "Vui lòng nhập cân nặng của trẻ")
    private String weight;
    @Column(name = "Gender")
    @NotBlank(message = "Vui lòng chọn giới tính của trẻ")
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Account_ID")
    private Account account_Id;

    public Child() {
    }

    public Child(String child_name, Date dob, String height, String weight, String gender) {
        this.child_name = child_name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public String getChild_id() {
        return child_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public Account getAccount_Id() {
        return account_Id;
    }

    public void setAccount_Id(Account account_Id) {
        this.account_Id = account_Id;
    }
}
