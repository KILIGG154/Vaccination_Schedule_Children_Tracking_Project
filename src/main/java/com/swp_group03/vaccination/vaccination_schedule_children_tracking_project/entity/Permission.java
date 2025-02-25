package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @Column(name = "Permission_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Permission_ID;
    @Column(name = "Permission_Name")
    private String Permission_Name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> role= new ArrayList<>();

    public Permission() {

    }

    public Permission(int permission_ID, String permission_Name, List<Role> role) {
        Permission_Name = permission_Name;
        this.role = role;
    }

    public Permission(String permission_Name) {

    }

    public int getPermission_ID() {
        return Permission_ID;
    }

    public String getPermission_Name() {
        return Permission_Name;
    }

    public void setPermission_Name(String permission_Name) {
        Permission_Name = permission_Name;
    }
}