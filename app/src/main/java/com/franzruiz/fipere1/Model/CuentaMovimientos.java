package com.franzruiz.fipere1.Model;

import java.util.Date;

public class CuentaMovimientos {
    private int Id;
    private String Descripcion;
    private double monto;
    private Date FechaMovimiento;
    private String UsuarioNombre;

    public CuentaMovimientos() {
    }

    public CuentaMovimientos(int id, String descripcion, double monto, Date fechaMovimiento, String usuarioNombre) {
        Id = id;
        Descripcion = descripcion;
        this.monto = monto;
        FechaMovimiento = fechaMovimiento;
        UsuarioNombre = usuarioNombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaMovimiento() {
        return FechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        FechaMovimiento = fechaMovimiento;
    }

    public String getUsuarioNombre() {
        return UsuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        UsuarioNombre = usuarioNombre;
    }
}
