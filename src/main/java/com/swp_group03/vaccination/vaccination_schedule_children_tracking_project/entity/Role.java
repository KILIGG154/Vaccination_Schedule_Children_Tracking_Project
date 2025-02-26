package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @Column(name = "Role_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name = "Role_Name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Role_Permission",
            joinColumns = @JoinColumn(name = "Role_ID"),
            inverseJoinColumns = @JoinColumn(name = "Permission_ID")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role() {

    }

    public Role(String role_Name, List<Account> accounts, Set<Permission> permissions) {
        role_Name = role_Name;
        this.accounts = accounts;
        this.permissions = permissions;
    }

    public Role(String Role_Name) {

    }

    public int getRole_ID() {
        return roleId;
    }

    public String getRole_Name() {
        return roleName;
    }

    public void setRole_Name(String role_Name) {
        role_Name = role_Name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
