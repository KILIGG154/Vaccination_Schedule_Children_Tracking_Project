package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

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
    private Account account_Id;

    public Child() {
    }

    public Child(String name, Date dob, String height, String weight, Gender gender, String urlImage, Account account_Id) {
        this.name = name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.urlImage = urlImage;
        this.account_Id = account_Id;
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

    public Account getAccount_Id() {
        return account_Id;
    }

    public void setAccount_Id(Account account_Id) {
        this.account_Id = account_Id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
