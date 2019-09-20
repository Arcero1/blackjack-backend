package com.qa.blackjack.account;

public class UserAccountRequestPasswordChange {
    private String email;
    private String oldPassword;
    private String newPassword;

    UserAccountRequestPasswordChange() {}
    UserAccountRequestPasswordChange(String email, String newPassword, String oldPassword) {
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
