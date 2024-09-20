package com.fpoly.demo_longnt1404.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String fullname;

    @Column(name = "email", columnDefinition = "NVARCHAR(255)", nullable = false, unique = true)
    private String email;

    @Column(name = "enabled", columnDefinition = "BIT", nullable = false)
    private Boolean enabled;

    @Column(name = "account_non_expired", columnDefinition = "BIT", nullable = false)
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked", columnDefinition = "BIT", nullable = false)
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", columnDefinition = "BIT", nullable = false)
    private Boolean credentialsNonExpired = true;

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    private String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private String updatedAt;

    // @ManyToMany: many-to-many relationship between User and Role
    @ManyToMany(fetch = FetchType.EAGER) // eager: load ngay lập tức
    // @JoinTable: create a join table to store the relationship between User and Role
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", roles=" + roles +
                '}';
    }
}
