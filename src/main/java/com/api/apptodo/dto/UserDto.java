package com.api.apptodo.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String userName;
    @NotBlank
    private String userEmail;
    @NotBlank
    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
