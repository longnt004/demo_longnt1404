package com.fpoly.demo_longnt1404.repository;

import com.fpoly.demo_longnt1404.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
