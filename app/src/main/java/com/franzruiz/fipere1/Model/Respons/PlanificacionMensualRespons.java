package com.franzruiz.fipere1.Model.Respons;

public class PlanificacionMensualRespons {
    private  int MovimientoId;
    private String MovimientoNombre;
    private double MontoPlanificado;
    private double  MontoEjecutado;

    public PlanificacionMensualRespons() {
    }

    public PlanificacionMensualRespons(int movimientoId, String movimientoNombre, double montoPlanificado, double montoEjecutado) {
        MovimientoId = movimientoId;
        MovimientoNombre = movimientoNombre;
        MontoPlanificado = montoPlanificado;
        MontoEjecutado = montoEjecutado;
    }

    public int getMovimientoId() {
        return MovimientoId;
    }

    public void setMovimientoId(int movimientoId) {
        MovimientoId = movimientoId;
    }

    public String getMovimientoNombre() {
        return MovimientoNombre;
    }

    public void setMovimientoNombre(String movimientoNombre) {
        MovimientoNombre = movimientoNombre;
    }

    public double getMontoPlanificado() {
        return MontoPlanificado;
    }

    public void setMontoPlanificado(double montoPlanificado) {
        MontoPlanificado = montoPlanificado;
    }

    public double getMontoEjecutado() {
        return MontoEjecutado;
    }

    public void setMontoEjecutado(double montoEjecutado) {
        MontoEjecutado = montoEjecutado;
    }
}
