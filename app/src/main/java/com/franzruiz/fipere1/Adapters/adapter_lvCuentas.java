package com.franzruiz.fipere1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

public class adapter_lvCuentas extends BaseAdapter {


    private Context context;
    private int layout;
    private List<Cuenta> cuentas;

    public adapter_lvCuentas(Context context, int layout, List<Cuenta> cuentas) {
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
        View v =view;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        v= layoutInflater.inflate(R.layout.item_listviews_cuentas,null);

        final Cuenta Item = (Cuenta) getItem(position);
        TextView _txtNombreCuenta =(TextView) v.findViewById(R.id.txtNombreCuenta);
        _txtNombreCuenta.setText(Item.getCuentaNombre());

      //  RelativeLayout bgElement = (RelativeLayout) v.findViewById(R.id.lySaldo);
      //  bgElement.setBackgroundColor(Color.rgb(76,175,80));
        TextView _descripcionSaldo =(TextView) v.findViewById(R.id.txtDescripcionSaldo);
        TextView _txtSaldo =(TextView) v.findViewById(R.id.txtSaldo);

        if(Item.getTipoCuentaId()==3){
            _descripcionSaldo.setText("Deuda");
            _txtSaldo.setText(String.format("%.2f", Item.getCuentaDeuda() ) );
        }
        else{
            _descripcionSaldo.setText("Saldo disponible");
            _txtSaldo.setText(String.format("%.2f", Item.getCuentaSaldo() ) );
        }



        ImageView _imgCuentas = (ImageView) v.findViewById(R.id.imgCuentas);
        int tipoCuentaId = Item.getTipoCuentaId();

        switch (tipoCuentaId) {
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

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(context, MainActivity.class);
//                intent.putExtra("Cuenta", Item);
//                intent.putExtra("Usuario", usuario);
//               context.startActivity(intent);
//
//            }
//        });

        return v;
    }


}
