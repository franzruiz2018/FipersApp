package com.franzruiz.fipere1.Model;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private int CuentaId;
    private String CuentaNombre;
    private Double CuentaSaldo;
    private Double CuentaDeuda;
    private int UsuarioId;
    private int TipoCuentaId;
    private int CuentaCierreDia;
    private boolean  CuentaActivo;
    private int CuentaPagoDia;

    public Cuenta() {
    }

    public Cuenta(int cuentaId, String cuentaNombre, Double cuentaSaldo, Double cuentaDeuda, int usuarioId, int tipoCuentaId, int cuentaCierreDia, boolean cuentaActivo, int cuentaPagoDia) {
        CuentaId = cuentaId;
        CuentaNombre = cuentaNombre;
        CuentaSaldo = cuentaSaldo;
        CuentaDeuda = cuentaDeuda;
        UsuarioId = usuarioId;
        TipoCuentaId = tipoCuentaId;
        CuentaCierreDia = cuentaCierreDia;
        CuentaActivo = cuentaActivo;
        CuentaPagoDia = cuentaPagoDia;
    }

    public int getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(int cuentaId) {
        CuentaId = cuentaId;
    }

    public String getCuentaNombre() {
        return CuentaNombre;
    }

    public void setCuentaNombre(String cuentaNombre) {
        CuentaNombre = cuentaNombre;
    }

    public Double getCuentaSaldo() {
        return CuentaSaldo;
    }

    public void setCuentaSaldo(Double cuentaSaldo) {
        CuentaSaldo = cuentaSaldo;
    }

    public Double getCuentaDeuda() {
        return CuentaDeuda;
    }

    public void setCuentaDeuda(Double cuentaDeuda) {
        CuentaDeuda = cuentaDeuda;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getTipoCuentaId() {
        return TipoCuentaId;
    }

    public void setTipoCuentaId(int tipoCuentaId) {
        TipoCuentaId = tipoCuentaId;
    }

    public int getCuentaCierreDia() {
        return CuentaCierreDia;
    }

    public void setCuentaCierreDia(int cuentaCierreDia) {
        CuentaCierreDia = cuentaCierreDia;
    }

    public boolean isCuentaActivo() {
        return CuentaActivo;
    }

    public void setCuentaActivo(boolean cuentaActivo) {
        CuentaActivo = cuentaActivo;
    }

    public int getCuentaPagoDia() {
        return CuentaPagoDia;
    }

    public void setCuentaPagoDia(int cuentaPagoDia) {
        CuentaPagoDia = cuentaPagoDia;
    }
}
