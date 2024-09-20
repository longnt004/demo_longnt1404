package com.fpoly.demo_longnt1404.service;


import com.fpoly.demo_longnt1404.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    User update(User user);
    User updatePassword(User user, String newPassword);
    User delete(String username);
    List<User> findAll();
}
