package com.franzruiz.fipere1.Model.Request;

import java.util.Date;

public class PlanificacionMensualRegistroRequest {

    private int MovimientoId;
    private String EgresosPlanificadosFecha;
    private double EgresosPlanificadosMonto;
    private int         UsuarioId;

    public PlanificacionMensualRegistroRequest() {
    }

    public PlanificacionMensualRegistroRequest(int movimientoId, String egresosPlanificadosFecha, double egresosPlanificadosMonto, int usuarioId) {
        MovimientoId = movimientoId;
        EgresosPlanificadosFecha = egresosPlanificadosFecha;
        EgresosPlanificadosMonto = egresosPlanificadosMonto;
        UsuarioId = usuarioId;
    }

    public int getMovimientoId() {
        return MovimientoId;
    }

    public void setMovimientoId(int movimientoId) {
        MovimientoId = movimientoId;
    }

    public String getEgresosPlanificadosFecha() {
        return EgresosPlanificadosFecha;
    }

    public void setEgresosPlanificadosFecha(String egresosPlanificadosFecha) {
        EgresosPlanificadosFecha = egresosPlanificadosFecha;
    }

    public double getEgresosPlanificadosMonto() {
        return EgresosPlanificadosMonto;
    }

    public void setEgresosPlanificadosMonto(double egresosPlanificadosMonto) {
        EgresosPlanificadosMonto = egresosPlanificadosMonto;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }
}
