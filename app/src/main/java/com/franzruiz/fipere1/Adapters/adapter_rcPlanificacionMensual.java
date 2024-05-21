package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franzruiz.fipere1.Activities.CuentaDetalleActivity;
import com.franzruiz.fipere1.Activities.MovimientosListaPorMesActivity;
import com.franzruiz.fipere1.Activities.OperacionDetalleActivity;
import com.franzruiz.fipere1.Activities.RegistrarMovimiento;
import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.Model.Respons.PlanificacionMensualRespons;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_rcPlanificacionMensual extends RecyclerView.Adapter<adapter_rcPlanificacionMensual.ViewHolder> {

    Context context;
    List<PlanificacionMensualRespons> planificacionMensualRespons_list;
    private final int mes;
    private final int año;


    public adapter_rcPlanificacionMensual(Context context, List<PlanificacionMensualRespons> planificacionMensualRespons_list, int mes, int año) {
        this.context = context;
        this.planificacionMensualRespons_list = planificacionMensualRespons_list;
        this.mes = mes;
        this.año = año;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_recycler_planificacion_mensual,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(planificacionMensualRespons_list!=null && planificacionMensualRespons_list.size()>0) {
            PlanificacionMensualRespons model = planificacionMensualRespons_list.get(position);
            holder.txt_movimiento.setText(model.getMovimientoNombre());
            holder.txt_planificado.setText( String.format("%.2f",model.getMontoPlanificado()));
            holder.txt_ejecutado.setText(String.format("%.2f",model.getMontoEjecutado()));
            holder.txt_pendiente.setText(String.format("%.2f",model.getMontoPlanificado()- model.getMontoEjecutado()));

            if((model.getMontoPlanificado()- model.getMontoEjecutado())<0){
                holder.txt_pendiente.setTextColor(context.getResources().getColor(R.color.colorRojo) );            }

            if(model.getMovimientoId()==999){
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(model.getMovimientoId()!=999) {
                       // Toast.makeText(view.getContext(), "Item is clicked " + model.getMovimientoId() + " " + mes + " " + año, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, MovimientosListaPorMesActivity.class);
                        intent.putExtra("MovimientoId",  model.getMovimientoId());
                        intent.putExtra("mes", mes);
                        intent.putExtra("año", año);
                        intent.putExtra("MontoPlanificado", model.getMontoPlanificado());
                        intent.putExtra("MontoEjecutado", model.getMontoEjecutado());
                        context.startActivity(intent);


                    }
                }
            });


        }
        else
        {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return planificacionMensualRespons_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_movimiento,txt_planificado,txt_ejecutado,txt_pendiente;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_movimiento=itemView.findViewById(R.id.txt_movimiento);
            txt_planificado=itemView.findViewById(R.id.txt_planificado);
            txt_ejecutado=itemView.findViewById(R.id.txt_ejecutado);
            txt_pendiente=itemView.findViewById(R.id.txt_pendiente);





        }
    }
}
