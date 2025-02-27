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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Role_Permission",
            joinColumns = @JoinColumn(name = "Role_ID"),
            inverseJoinColumns = @JoinColumn(name = "Permission_ID")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role() {}

    public Role(String roleName, List<Account> accounts, Set<Permission> permissions) {
        this.roleName = roleName;
        this.accounts = accounts;
        this.permissions = permissions;
    }

    public Role(String Role_Name) {
      this.roleName = Role_Name;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
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
