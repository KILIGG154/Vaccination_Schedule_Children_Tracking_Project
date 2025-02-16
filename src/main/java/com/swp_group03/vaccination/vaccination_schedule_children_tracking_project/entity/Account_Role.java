package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

public class Account_Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Role_ID;
    @Column
    private String Role_Name;


    public Account_Role() {

    }

    public Account_Role(String Role_Name) {

    }

    public String getRole_ID() {
        return Role_ID;
    }

    public String getRole_Name() {
        return Role_Name;
    }

    public void setRole_Name(String role_Name) {
        Role_Name = role_Name;
    }

}
