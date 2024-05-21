package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.franzruiz.fipere1.Model.Respons.DeudaRespons;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_lvDeudas extends BaseAdapter  {

    private Context context;
    private int layout;
    private List<DeudaRespons> deudas;

    public adapter_lvDeudas(Context context, int layout, List<DeudaRespons> deudas) {
        this.context = context;
        this.layout = layout;
        this.deudas = deudas;
    }

    public int getCount() {
        return deudas.size();
    }

    public Object getItem(int position) {
        return this.deudas.get(position);
    }

    public long getItemId(int id) {
        return id;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        View v =view;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        v= layoutInflater.inflate(R.layout.item_listview_deudas,null);

        final DeudaRespons Item = (DeudaRespons) getItem(position);
        TextView _txtMontoDeuda =(TextView) v.findViewById(R.id.txtMontoDeuda);
        _txtMontoDeuda.setText(String.format("%.2f", Item.getCreditoDeudaMonto() ) );

        TextView _txtFechaPago =(TextView) v.findViewById(R.id.txtFechaPago);
        _txtFechaPago.setText(Item.getCuentaPagoDia() + "/" +Item.getCreditoDeudaMesPago() + "/" + Item.getCreditoDeudaAnioPago());

        return v;
    }

}
