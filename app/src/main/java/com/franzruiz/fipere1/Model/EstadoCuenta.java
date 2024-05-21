package com.franzruiz.fipere1.Model;

public class EstadoCuenta {
    private int Mes;
    private int Anio;
    private double Ingresos;
    private double Gasto;
    private double CreditoGasto;
    private double Inversion;
    private double CreditoInversion;
    private double DeudaPendiente;
    private double DeudaPagada;


    public EstadoCuenta() {
    }

    public EstadoCuenta(int mes, int anio, double ingresos, double gasto, double creditoGasto, double inversion, double creditoInversion, double deudaPendiente, double deudaPagada) {
        Mes = mes;
        Anio = anio;
        Ingresos = ingresos;
        Gasto = gasto;
        CreditoGasto = creditoGasto;
        Inversion = inversion;
        CreditoInversion = creditoInversion;
        DeudaPendiente = deudaPendiente;
        DeudaPagada = deudaPagada;
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

    public double getIngresos() {
        return Ingresos;
    }

    public void setIngresos(double ingresos) {
        Ingresos = ingresos;
    }

    public double getGasto() {
        return Gasto;
    }

    public void setGasto(double gasto) {
        Gasto = gasto;
    }

    public double getCreditoGasto() {
        return CreditoGasto;
    }

    public void setCreditoGasto(double creditoGasto) {
        CreditoGasto = creditoGasto;
    }

    public double getInversion() {
        return Inversion;
    }

    public void setInversion(double inversion) {
        Inversion = inversion;
    }

    public double getCreditoInversion() {
        return CreditoInversion;
    }

    public void setCreditoInversion(double creditoInversion) {
        CreditoInversion = creditoInversion;
    }

    public double getDeudaPendiente() {
        return DeudaPendiente;
    }

    public void setDeudaPendiente(double deudaPendiente) {
        DeudaPendiente = deudaPendiente;
    }

    public double getDeudaPagada() {
        return DeudaPagada;
    }

    public void setDeudaPagada(double deudaPagada) {
        DeudaPagada = deudaPagada;
    }
}
