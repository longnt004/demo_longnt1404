package com.fpoly.demo_longnt1404.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    private static final long EXPIRATION_TIME = 300000; // 5 minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", columnDefinition = "VARCHAR(4)", nullable = false, unique = true)
    private String otp;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "username")
    private User user;

    @Column(name = "expiry_date", columnDefinition = "DATETIME", nullable = false)
    private Date expiryDate;

    public Otp(String otp, User user) {
        this.otp = otp;
        this.user = user;
        // Set the expiration time for the OTP to be 5 minutes after the current time
        this.expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    // Check if the OTP has expired
    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
