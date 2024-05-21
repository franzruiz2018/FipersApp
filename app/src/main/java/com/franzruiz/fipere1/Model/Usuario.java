package com.franzruiz.fipere1.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int UsuarioId;
    private String UsuarioNombre;
    private String UsuarioPassword;
    private String UsuarioImagen;
    private String UsuarioCorreo;
    private int UsuarioPadreCuenta;
    private boolean UsuarioAdminitradorCuenta;
    private boolean UsuarioAdminitradorSistema;
    private String Messages;
    private boolean Success;


    public Usuario() {
    }

    public Usuario(int usuarioId, String usuarioNombre, String usuarioPassword, String usuarioImagen, String usuarioCorreo, int usuarioPadreCuenta, boolean usuarioAdminitradorCuenta, boolean usuarioAdminitradorSistema, String messages, boolean success) {
        UsuarioId = usuarioId;
        UsuarioNombre = usuarioNombre;
        UsuarioPassword = usuarioPassword;
        UsuarioImagen = usuarioImagen;
        UsuarioCorreo = usuarioCorreo;
        UsuarioPadreCuenta = usuarioPadreCuenta;
        UsuarioAdminitradorCuenta = usuarioAdminitradorCuenta;
        UsuarioAdminitradorSistema = usuarioAdminitradorSistema;
        Messages = messages;
        Success = success;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return UsuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        UsuarioNombre = usuarioNombre;
    }

    public String getUsuarioPassword() {
        return UsuarioPassword;
    }

    public void setUsuarioPassword(String usuarioPassword) {
        UsuarioPassword = usuarioPassword;
    }

    public String getUsuarioImagen() {
        return UsuarioImagen;
    }

    public void setUsuarioImagen(String usuarioImagen) {
        UsuarioImagen = usuarioImagen;
    }

    public String getUsuarioCorreo() {
        return UsuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        UsuarioCorreo = usuarioCorreo;
    }

    public int getUsuarioPadreCuenta() {
        return UsuarioPadreCuenta;
    }

    public void setUsuarioPadreCuenta(int usuarioPadreCuenta) {
        UsuarioPadreCuenta = usuarioPadreCuenta;
    }

    public boolean isUsuarioAdminitradorCuenta() {
        return UsuarioAdminitradorCuenta;
    }

    public void setUsuarioAdminitradorCuenta(boolean usuarioAdminitradorCuenta) {
        UsuarioAdminitradorCuenta = usuarioAdminitradorCuenta;
    }

    public boolean isUsuarioAdminitradorSistema() {
        return UsuarioAdminitradorSistema;
    }

    public void setUsuarioAdminitradorSistema(boolean usuarioAdminitradorSistema) {
        UsuarioAdminitradorSistema = usuarioAdminitradorSistema;
    }

    public String getMessages() {
        return Messages;
    }

    public void setMessages(String messages) {
        Messages = messages;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
