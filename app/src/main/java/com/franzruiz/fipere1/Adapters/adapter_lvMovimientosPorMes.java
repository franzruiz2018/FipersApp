package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_lvMovimientosPorMes extends BaseAdapter {

    private Context context;
    private int layout;
    private List<OperacionesRespons> operaciones;

    public adapter_lvMovimientosPorMes(Context context, int layout, List<OperacionesRespons> operaciones) {
        this.context = context;
        this.layout = layout;
        this.operaciones = operaciones;
    }

    @Override
    public int getCount() {
        return operaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return this.operaciones.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v =view;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        v= layoutInflater.inflate(R.layout.item_listviews_movimiento,null);
        final OperacionesRespons currentMov = operaciones.get(position);
        TextView _txtMovimiento =(TextView) v.findViewById(R.id.txtMovimiento);
        _txtMovimiento.setText(currentMov.getOperacionDescripcion());


        TextView _txtFechaMov =(TextView) v.findViewById(R.id.txtFecha);
        _txtFechaMov.setText(currentMov.getOperacionFechaOperacionString());

        TextView _txtMonto =(TextView) v.findViewById(R.id.txtMonto);

        _txtMonto.setText(String.format("%.2f",currentMov.getOperacionDetalleMonto()));
        if(currentMov.getOperacionDetalleMonto()<0){
            _txtMonto.setTextColor(v.getResources().getColor(R.color.colorRojo) );
        }








        return v;
    }


}
