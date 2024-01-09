package com.parkking.parkingservice.utils;


import com.parkking.parkingservice.dto.auth.TwoFactorResponse;

public interface SmsStrategy {

    public TwoFactorResponse makeSmsCall(String phoneNo, String otp);

}
