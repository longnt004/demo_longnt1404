package com.fpoly.demo_longnt1404.repository;

import com.fpoly.demo_longnt1404.model.Otp;
import com.fpoly.demo_longnt1404.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    // Query to find OTP by user and OTP
    @Query("SELECT o FROM Otp o WHERE o.user = ?1 AND o.otp = ?2")
    Otp findByUserAndOtp(User user, String otp);

    // Query to find User by OTP
    @Query("SELECT o.user FROM Otp o WHERE o.otp = ?1")
    User findUserByOtp(String otp);

    // Repository method to delete the current OTP for the user
    @Transactional
    @Modifying
    @Query("DELETE FROM Otp o WHERE o.user = :user")
    void deleteUserOtp(@Param("user") User user);

    @Query("SELECT o FROM Otp o WHERE o.user = ?1")
    Otp findByUser(User user);
}
