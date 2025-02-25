package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @Column(name = "Role_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Role_ID;
    @Column(name = "Role_Name")
    private String Role_Name;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accountList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Role_Permission",
            joinColumns = @JoinColumn(name = "Role_ID"),
            inverseJoinColumns = @JoinColumn(name = "Permission_ID")
    )
    private List<Permission> permissions = new ArrayList<>();

    public Role() {

    }

    public Role(String role_ID, String role_Name, List<Account> accountList, List<Permission> permissions) {
        Role_ID = role_ID;
        Role_Name = role_Name;
        this.accountList = accountList;
        this.permissions = permissions;
    }

    public Role(String Role_Name) {

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
