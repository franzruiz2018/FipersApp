package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franzruiz.fipere1.Model.EstadoCuenta;

import com.franzruiz.fipere1.R;

import java.util.List;


public class adapter_rc_EstadoCuenta extends RecyclerView.Adapter<adapter_rc_EstadoCuenta.ViewHolder> {

    Context context;
    List<EstadoCuenta> estadoCuentas_list;

    public adapter_rc_EstadoCuenta(Context context, List<EstadoCuenta> estadoCuentas_list) {
        this.context = context;
        this.estadoCuentas_list = estadoCuentas_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_estado_cuenta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (estadoCuentas_list != null && estadoCuentas_list.size() > 0) {
            EstadoCuenta model = estadoCuentas_list.get(position);

            // array de meses
            String[] meses = {"","Ene", "Feb","Mar", "Abr", "May", "Jun",
                    "Jul", "Ago","Set", "Oct", "Nov", "Dic"};

            String  mes_texto =  meses[model.getMes()];

            holder.txt_periodo.setText(mes_texto + "-" + model.getAnio());
            holder.txt_ingresos.setText(String.format("%.2f", model.getIngresos()));
            holder.txt_gasto.setText(String.format("%.2f", model.getGasto()));
            holder.txt_credito_gasto.setText(String.format("%.2f", model.getCreditoGasto()));
            holder.txt_inversion.setText(String.format("%.2f", model.getInversion()));
            holder.txt_credito_inversion.setText(String.format("%.2f", model.getCreditoInversion()));
            holder.txt_deuda_pendiente.setText(String.format("%.2f", model.getDeudaPendiente()));
            holder.txt_deuda_pagada.setText(String.format("%.2f", model.getDeudaPagada()));
        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return estadoCuentas_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_periodo, txt_ingresos, txt_gasto, txt_inversion,txt_credito_gasto,txt_credito_inversion,txt_deuda_pendiente,txt_deuda_pagada;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_periodo = itemView.findViewById(R.id.txt_periodo);
            txt_ingresos = itemView.findViewById(R.id.txt_ingresos);
            txt_gasto = itemView.findViewById(R.id.txt_gasto);
            txt_credito_gasto = itemView.findViewById(R.id.txt_credito_gasto);
            txt_inversion = itemView.findViewById(R.id.txt_inversion);
            txt_credito_inversion = itemView.findViewById(R.id.txt_credito_inversion);
            txt_deuda_pendiente = itemView.findViewById(R.id.txt_deuda_pendiente);
            txt_deuda_pagada = itemView.findViewById(R.id.txt_deuda_pagada);
        }
    }
}

