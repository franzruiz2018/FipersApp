package com.franzruiz.fipere1.Model.Request;


public class OperacionRequest {

    private String OperacionFechaOperacion;
    private double OperacionMonto;
    private int CuentaId;
    private int MovimientoId;
    private int UsuarioId;
    private String OperacionDescripcion;
    private int OperacionId;
    private int OperacionCuentaDestino;
    private int OperacionCuentaSalida;
    private int DeudaMes;
    private int DeudaAño;
    private String CreditoDeudaFechaPago;


    public OperacionRequest() {
    }

    public OperacionRequest(String operacionFechaOperacion, double operacionMonto, int cuentaId, int movimientoId, int usuarioId, String operacionDescripcion, int operacionId, int operacionCuentaDestino, int operacionCuentaSalida, int deudaMes, int deudaAño, String creditoDeudaFechaPago) {
        OperacionFechaOperacion = operacionFechaOperacion;
        OperacionMonto = operacionMonto;
        CuentaId = cuentaId;
        MovimientoId = movimientoId;
        UsuarioId = usuarioId;
        OperacionDescripcion = operacionDescripcion;
        OperacionId = operacionId;
        OperacionCuentaDestino = operacionCuentaDestino;
        OperacionCuentaSalida = operacionCuentaSalida;
        DeudaMes = deudaMes;
        DeudaAño = deudaAño;
        CreditoDeudaFechaPago = creditoDeudaFechaPago;
    }

    public String getOperacionFechaOperacion() {
        return OperacionFechaOperacion;
    }

    public void setOperacionFechaOperacion(String operacionFechaOperacion) {
        OperacionFechaOperacion = operacionFechaOperacion;
    }

    public double getOperacionMonto() {
        return OperacionMonto;
    }

    public void setOperacionMonto(double operacionMonto) {
        OperacionMonto = operacionMonto;
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

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getOperacionDescripcion() {
        return OperacionDescripcion;
    }

    public void setOperacionDescripcion(String operacionDescripcion) {
        OperacionDescripcion = operacionDescripcion;
    }

    public int getOperacionId() {
        return OperacionId;
    }

    public void setOperacionId(int operacionId) {
        OperacionId = operacionId;
    }

    public int getOperacionCuentaDestino() {
        return OperacionCuentaDestino;
    }

    public void setOperacionCuentaDestino(int operacionCuentaDestino) {
        OperacionCuentaDestino = operacionCuentaDestino;
    }

    public int getOperacionCuentaSalida() {
        return OperacionCuentaSalida;
    }

    public void setOperacionCuentaSalida(int operacionCuentaSalida) {
        OperacionCuentaSalida = operacionCuentaSalida;
    }

    public int getDeudaMes() {
        return DeudaMes;
    }

    public void setDeudaMes(int deudaMes) {
        DeudaMes = deudaMes;
    }

    public int getDeudaAño() {
        return DeudaAño;
    }

    public void setDeudaAño(int deudaAño) {
        DeudaAño = deudaAño;
    }

    public String getCreditoDeudaFechaPago() {
        return CreditoDeudaFechaPago;
    }

    public void setCreditoDeudaFechaPago(String creditoDeudaFechaPago) {
        CreditoDeudaFechaPago = creditoDeudaFechaPago;
    }
}
