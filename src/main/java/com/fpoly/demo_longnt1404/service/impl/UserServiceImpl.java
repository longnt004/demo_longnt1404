package com.fpoly.demo_longnt1404.service.impl;


import com.fpoly.demo_longnt1404.config.EncoderConfig;
import com.fpoly.demo_longnt1404.model.User;
import com.fpoly.demo_longnt1404.repository.UserRepository;
import com.fpoly.demo_longnt1404.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncoderConfig passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, EncoderConfig passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.passwordEncoder().encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public User delete(String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            userRepository.deleteById(username);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
