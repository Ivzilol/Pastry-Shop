package com.example.pastry.shop.model.dto;

public class ForgottenPasswordNewPasswordDto {

    private String verificationCode;

    private String password;

    private String confirmPassword;

    public ForgottenPasswordNewPasswordDto() {
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 3 || password.length() > 20) {
            return;
        }
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
