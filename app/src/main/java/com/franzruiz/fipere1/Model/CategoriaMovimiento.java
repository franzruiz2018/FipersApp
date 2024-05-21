package com.franzruiz.fipere1.Model;

import java.io.Serializable;

public class CategoriaMovimiento implements Serializable {

    private int CategoriaMovimientoId;
    private String CategoriaMovimientoNombre;
    private int TipoCuentaId;

    public CategoriaMovimiento() {
    }

    public CategoriaMovimiento(int categoriaMovimientoId, String categoriaMovimientoNombre, int tipoCuentaId) {
        CategoriaMovimientoId = categoriaMovimientoId;
        CategoriaMovimientoNombre = categoriaMovimientoNombre;
        TipoCuentaId = tipoCuentaId;
    }

    public int getCategoriaMovimientoId() {
        return CategoriaMovimientoId;
    }

    public void setCategoriaMovimientoId(int categoriaMovimientoId) {
        CategoriaMovimientoId = categoriaMovimientoId;
    }

    public String getCategoriaMovimientoNombre() {
        return CategoriaMovimientoNombre;
    }

    public void setCategoriaMovimientoNombre(String categoriaMovimientoNombre) {
        CategoriaMovimientoNombre = categoriaMovimientoNombre;
    }

    public int getTipoCuentaId() {
        return TipoCuentaId;
    }

    public void setTipoCuentaId(int tipoCuentaId) {
        TipoCuentaId = tipoCuentaId;
    }
}