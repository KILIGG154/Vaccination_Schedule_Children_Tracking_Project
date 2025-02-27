package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @Column(name = "Permission_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;
    @Column(name = "Permission_Name")
    private String permissionName;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> role= new HashSet<>();

    public Permission(){}

    public Permission(String permissionName, Set<Role> role) {
        this.permissionName = permissionName;
        this.role = role;
    }

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}