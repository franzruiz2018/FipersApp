package com.franzruiz.fipere1.Model.Respons;

import java.io.Serializable;

public class DeudaRespons implements Serializable {


    private int CuentaId;
    private int CreditoDeudaMesPago;
    private int CreditoDeudaAnioPago;
    private double CreditoDeudaMonto;
    private int CuentaPagoDia;


    public DeudaRespons() {
    }

    public DeudaRespons(int cuentaId, int creditoDeudaMesPago, int creditoDeudaAnioPago, double creditoDeudaMonto, int cuentaPagoDia) {
        CuentaId = cuentaId;
        CreditoDeudaMesPago = creditoDeudaMesPago;
        CreditoDeudaAnioPago = creditoDeudaAnioPago;
        CreditoDeudaMonto = creditoDeudaMonto;
        CuentaPagoDia = cuentaPagoDia;
    }

    public int getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(int cuentaId) {
        CuentaId = cuentaId;
    }

    public int getCreditoDeudaMesPago() {
        return CreditoDeudaMesPago;
    }

    public void setCreditoDeudaMesPago(int creditoDeudaMesPago) {
        CreditoDeudaMesPago = creditoDeudaMesPago;
    }

    public int getCreditoDeudaAnioPago() {
        return CreditoDeudaAnioPago;
    }

    public void setCreditoDeudaAnioPago(int creditoDeudaAnioPago) {
        CreditoDeudaAnioPago = creditoDeudaAnioPago;
    }

    public double getCreditoDeudaMonto() {
        return CreditoDeudaMonto;
    }

    public void setCreditoDeudaMonto(double creditoDeudaMonto) {
        CreditoDeudaMonto = creditoDeudaMonto;
    }

    public int getCuentaPagoDia() {
        return CuentaPagoDia;
    }

    public void setCuentaPagoDia(int cuentaPagoDia) {
        CuentaPagoDia = cuentaPagoDia;
    }
}
