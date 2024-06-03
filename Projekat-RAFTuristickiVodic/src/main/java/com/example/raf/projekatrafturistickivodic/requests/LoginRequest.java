package com.example.raf.projekatrafturistickivodic.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Email je obavezno polje")
    @NotEmpty(message = "Email je obavezno polje")
    private String email;

    @NotNull(message = "Password je obavezno polje")
    @NotEmpty(message = "Password je obavezno polje")
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
