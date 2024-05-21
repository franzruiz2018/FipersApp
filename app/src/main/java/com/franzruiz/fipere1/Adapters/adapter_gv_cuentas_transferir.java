package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_gv_cuentas_transferir extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Cuenta> cuentas;

    public adapter_gv_cuentas_transferir(Context context, int layout, List<Cuenta> cuentas) {
        this.context = context;
        this.layout = layout;
        this.cuentas = cuentas;
    }

    public int getCount() {
        return cuentas.size();
    }

    public Object getItem(int position) {
        return this.cuentas.get(position);
    }

    public long getItemId(int id) {
        return id;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.item_gridview_cuentas_transferir, null);
        final Cuenta Item = (Cuenta) getItem(position);
        TextView _txtTitulo = (TextView) v.findViewById(R.id.txtTitulo);
        _txtTitulo.setText(Item.getCuentaNombre());

        TextView _txtSaldo = (TextView) v.findViewById(R.id.txtSaldo);
        _txtSaldo.setText(String.format("%.2f",Item.getCuentaSaldo()));


        ImageView _imgCuentas = (ImageView) v.findViewById(R.id.imgCuentas);
        int tipoCuenta = Item.getTipoCuentaId();

        switch (tipoCuenta) {
            case 1:
                _imgCuentas.setImageResource(R.drawable.ic_icono_tarjeta_debito_blanco);
                break;
            case 2:
                _imgCuentas.setImageResource(R.drawable.ic_icono_cuenta_ahorro_color_blanco);
                break;
            case 3:
                _imgCuentas.setImageResource(R.drawable.ic_icono_tarjeta_credito_blanco);
                break;
        }
        return v;
    }

}
