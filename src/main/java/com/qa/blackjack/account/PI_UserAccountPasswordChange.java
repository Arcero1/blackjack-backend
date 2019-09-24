package com.qa.blackjack.account;

public class PI_UserAccountPasswordChange {
    private String email;
    private String oldPassword;
    private String newPassword;

    PI_UserAccountPasswordChange() {}
    PI_UserAccountPasswordChange(String email, String newPassword, String oldPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
