package com.franzruiz.fipere1.Model.Request;

public class PlanificacionMensualRequest {

    private  int UsuarioId;
    private  int Mes;
    private  int Anio;

    public PlanificacionMensualRequest() {
    }

    public PlanificacionMensualRequest(int usuarioId, int mes, int anio) {
        UsuarioId = usuarioId;
        Mes = mes;
        Anio = anio;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }
}
