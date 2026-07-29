package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone_number")
        }
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private Set<ApartmentEntity> apartments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(final Long id, final String fullName, final String email,
                      final String phoneNumber, final String password, final LocalDateTime createdAt,
                      final Set<ApartmentEntity> apartments, final Set<RoleEntity> roles) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdAt = createdAt;
        this.apartments = apartments;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRoles(final Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<ApartmentEntity> getApartments() {
        return apartments;
    }

    public void setApartments(final Set<ApartmentEntity> apartments) {
        this.apartments = apartments;
    }
}