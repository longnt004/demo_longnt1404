package com.fpoly.demo_longnt1404.service.impl;

import com.fpoly.demo_longnt1404.model.Otp;
import com.fpoly.demo_longnt1404.model.User;
import com.fpoly.demo_longnt1404.repository.OtpRepository;
import com.fpoly.demo_longnt1404.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public String generatorOTP(User user) {
        // Delete the current OTP for the user
        otpRepository.deleteUserOtp(user);

        // Create OTP code with 4 digits
        String otp = String.valueOf(new Random().nextInt(9000) + 1000);

        // Save the new OTP code to the database with user
        Otp otpEntity = new Otp(otp, user);
        otpRepository.save(otpEntity);

        return otp;
    }

    @Override
    public Boolean validateOTP(User user, String otp) {
        // Validate OTP code with user in database
        Otp otpEntity = otpRepository.findByUserAndOtp(user, otp);
        System.out.println(otpEntity);
        // Return true if OTP code is valid and not expired
        return otpEntity != null && !otpEntity.isExpired();
    }

    @Override
    public String FindOtpByUser(User user) {
        // Find OTP code by user
        Otp otpEntity = otpRepository.findByUser(user);
        return otpEntity.getOtp();
    }


}
