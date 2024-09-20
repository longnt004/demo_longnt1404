package com.fpoly.demo_longnt1404.repository;

import com.fpoly.demo_longnt1404.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Query to find user by email
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
