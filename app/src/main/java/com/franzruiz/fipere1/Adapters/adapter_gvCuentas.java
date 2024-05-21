package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.franzruiz.fipere1.Activities.RegistrarMovimiento;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_gvCuentas extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CategoriaMovimiento> categoriaMovimientos;
    private Cuenta cuenta;


    public adapter_gvCuentas(Context context, int layout, List<CategoriaMovimiento> categoriaMovimientos, Cuenta cuenta) {
        this.context = context;
        this.layout = layout;
        this.categoriaMovimientos = categoriaMovimientos;
        this.cuenta = cuenta;

    }


    public int getCount() {
        return categoriaMovimientos.size();
    }


    public Object getItem(int position) {
        return this.categoriaMovimientos.get(position);
    }


    public long getItemId(int id) {
        return id;
    }


    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.item_gridview_operaciones, null);
        final CategoriaMovimiento Item = (CategoriaMovimiento) getItem(position);
        TextView _txtOperacion = (TextView) v.findViewById(R.id.txtOperacion);
        _txtOperacion.setText(Item.getCategoriaMovimientoNombre());

        ImageView _imgOperacion = (ImageView) v.findViewById(R.id.imgOperacion);
        int operacion = Item.getCategoriaMovimientoId();

        switch (operacion) {
            case 1:
                _imgOperacion.setImageResource(R.drawable.ic_icono_ingreso_color);
                break;
            case 2:
                _imgOperacion.setImageResource(R.drawable.ic_icono_gasto_color);
                break;
            case 3:
                _imgOperacion.setImageResource(R.drawable.ic_icono_transferencia_color);
                break;
            case 4:
                _imgOperacion.setImageResource(R.drawable.ic_icono_pago_copia);
                break;
            case 5:
                _imgOperacion.setImageResource(R.drawable.ic_icono_inversion_color);
                break;

        }

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int _tipoMovimiento = Item.getCategoriaMovimientoId();
//                String _tipoMovimientoDescripcion = Item.getCategoriaMovimientoNombre();
//                Intent intent2 = new Intent(context, RegistrarMovimiento.class);
//                intent2.putExtra("TipoMovimientoDescipcion", _tipoMovimientoDescripcion);
//                intent2.putExtra("Operacion", Item);
//                intent2.putExtra("Cuenta", cuenta);
//                context.startActivity(intent2);
//            }
//        });

        return v;
    }
}
