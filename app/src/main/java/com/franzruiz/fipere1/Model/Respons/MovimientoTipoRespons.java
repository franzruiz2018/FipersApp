package com.franzruiz.fipere1.Model.Respons;

public class MovimientoTipoRespons {

    private int MovimientoId;
    private String MovimientoNombre;
    private  String    MovimientoIcono;

    public MovimientoTipoRespons() {
    }

    public MovimientoTipoRespons(int movimientoId, String movimientoNombre, String movimientoIcono) {
        MovimientoId = movimientoId;
        MovimientoNombre = movimientoNombre;
        MovimientoIcono = movimientoIcono;
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

    public String getMovimientoIcono() {
        return MovimientoIcono;
    }

    public void setMovimientoIcono(String movimientoIcono) {
        MovimientoIcono = movimientoIcono;
    }
}
