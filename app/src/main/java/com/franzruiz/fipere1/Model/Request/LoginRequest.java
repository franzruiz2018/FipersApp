package com.franzruiz.fipere1.Model.Request;

public class LoginRequest {
    private String UsuarioCorreo;
    private String UsuarioPassword;


    public LoginRequest() {
    }

    public LoginRequest(String usuarioCorreo, String usuarioPassword) {
        UsuarioCorreo = usuarioCorreo;
        UsuarioPassword = usuarioPassword;
    }

    public String getUsuarioCorreo() {
        return UsuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        UsuarioCorreo = usuarioCorreo;
    }

    public String getUsuarioPassword() {
        return UsuarioPassword;
    }

    public void setUsuarioPassword(String usuarioPassword) {
        UsuarioPassword = usuarioPassword;
    }
}
