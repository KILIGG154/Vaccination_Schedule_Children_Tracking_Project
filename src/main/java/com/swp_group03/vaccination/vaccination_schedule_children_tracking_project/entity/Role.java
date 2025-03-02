package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @Column(name = "RoleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column(name = "RoleName")
    private String roleName;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Account> accounts = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    public Role() {}

    public Role(String roleName, Set<Account> accounts, Set<Permission> permissions) {
        this.roleName = roleName;
        this.accounts = accounts;
        this.permissions = permissions;
    }

    public Role(String roleName) {
      this.roleName = roleName;
      this.accounts = new HashSet<>();
    }

    public int getRoleID() {
        return this.roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.getRoles().add(this); // Cập nhật mối quan hệ hai chiều
    }

    // Phương thức để xóa một Account
    public void removeAccount(Account account) {
        this.accounts.remove(account);
        account.getRoles().remove(this); // Cập nhật mối quan hệ hai chiều
    }

}
