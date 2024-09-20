package com.fpoly.demo_longnt1404.service;


import com.fpoly.demo_longnt1404.model.User;

public interface OtpService {
    String generatorOTP(User user);
    Boolean validateOTP(User user, String otp);
    String FindOtpByUser(User user);
}
