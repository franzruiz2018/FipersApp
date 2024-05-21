package com.franzruiz.fipere1.Model.Request;

public class GenericRequest {

    private  boolean Success;
    private int CategoriaMovimientoId;
    private String Messages;
    private int UsuarioId;
    private int CuentaId;
    private int MovimientoId;
    private int Mes;
    private int Año;

    public GenericRequest() {
    }


    public GenericRequest(boolean success, int categoriaMovimientoId, String messages, int usuarioId, int cuentaId, int movimientoId, int mes, int año) {
        Success = success;
        CategoriaMovimientoId = categoriaMovimientoId;
        Messages = messages;
        UsuarioId = usuarioId;
        CuentaId = cuentaId;
        MovimientoId = movimientoId;
        Mes = mes;
        Año = año;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getCategoriaMovimientoId() {
        return CategoriaMovimientoId;
    }

    public void setCategoriaMovimientoId(int categoriaMovimientoId) {
        CategoriaMovimientoId = categoriaMovimientoId;
    }

    public String getMessages() {
        return Messages;
    }

    public void setMessages(String messages) {
        Messages = messages;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(int cuentaId) {
        CuentaId = cuentaId;
    }

    public int getMovimientoId() {
        return MovimientoId;
    }

    public void setMovimientoId(int movimientoId) {
        MovimientoId = movimientoId;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAño() {
        return Año;
    }

    public void setAño(int año) {
        Año = año;
    }
}
