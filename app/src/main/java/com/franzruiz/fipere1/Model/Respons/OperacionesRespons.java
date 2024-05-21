package com.franzruiz.fipere1.Model.Respons;

import java.io.Serializable;
import java.util.Date;

public class OperacionesRespons implements Serializable {

    private  int CuentaId;
    private  int MovimientoId;
    private String CategoriaMovimientoNombre;
    private String MovimientoNombre;
    private String  MovimientoIcono;
    private Date OperacionFechaOperacion;
    private double OperacionDetalleMonto;
    private String OperacionDetalleDescripcion;
    private  int OperacionId;
    private String OperacionFechaOperacionString;
    private String OperacionDescripcion;
    private String UsuarioNombre;



    public OperacionesRespons() {
    }

    public OperacionesRespons(int cuentaId, int movimientoId, String categoriaMovimientoNombre, String movimientoNombre, String movimientoIcono, Date operacionFechaOperacion, double operacionDetalleMonto, String operacionDetalleDescripcion, int operacionId, String operacionFechaOperacionString, String operacionDescripcion, String usuarioNombre) {
        CuentaId = cuentaId;
        MovimientoId = movimientoId;
        CategoriaMovimientoNombre = categoriaMovimientoNombre;
        MovimientoNombre = movimientoNombre;
        MovimientoIcono = movimientoIcono;
        OperacionFechaOperacion = operacionFechaOperacion;
        OperacionDetalleMonto = operacionDetalleMonto;
        OperacionDetalleDescripcion = operacionDetalleDescripcion;
        OperacionId = operacionId;
        OperacionFechaOperacionString = operacionFechaOperacionString;
        OperacionDescripcion = operacionDescripcion;
        UsuarioNombre = usuarioNombre;
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

    public String getCategoriaMovimientoNombre() {
        return CategoriaMovimientoNombre;
    }

    public void setCategoriaMovimientoNombre(String categoriaMovimientoNombre) {
        CategoriaMovimientoNombre = categoriaMovimientoNombre;
    }

    public String getMovimientoNombre() {
        return MovimientoNombre;
    }

    public void setMovimientoNombre(String movimientoNombre) {
        MovimientoNombre = movimientoNombre;
    }

    public String getMovimientoIcono() {
        return MovimientoIcono;
    }

    public void setMovimientoIcono(String movimientoIcono) {
        MovimientoIcono = movimientoIcono;
    }

    public Date getOperacionFechaOperacion() {
        return OperacionFechaOperacion;
    }

    public void setOperacionFechaOperacion(Date operacionFechaOperacion) {
        OperacionFechaOperacion = operacionFechaOperacion;
    }

    public double getOperacionDetalleMonto() {
        return OperacionDetalleMonto;
    }

    public void setOperacionDetalleMonto(double operacionDetalleMonto) {
        OperacionDetalleMonto = operacionDetalleMonto;
    }

    public String getOperacionDetalleDescripcion() {
        return OperacionDetalleDescripcion;
    }

    public void setOperacionDetalleDescripcion(String operacionDetalleDescripcion) {
        OperacionDetalleDescripcion = operacionDetalleDescripcion;
    }

    public int getOperacionId() {
        return OperacionId;
    }

    public void setOperacionId(int operacionId) {
        OperacionId = operacionId;
    }

    public String getOperacionFechaOperacionString() {
        return OperacionFechaOperacionString;
    }

    public void setOperacionFechaOperacionString(String operacionFechaOperacionString) {
        OperacionFechaOperacionString = operacionFechaOperacionString;
    }

    public String getOperacionDescripcion() {
        return OperacionDescripcion;
    }

    public void setOperacionDescripcion(String operacionDescripcion) {
        OperacionDescripcion = operacionDescripcion;
    }

    public String getUsuarioNombre() {
        return UsuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        UsuarioNombre = usuarioNombre;
    }
}
