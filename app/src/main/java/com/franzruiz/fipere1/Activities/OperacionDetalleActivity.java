package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperacionDetalleActivity extends AppCompatActivity {

    TextView _txtOperacionId,_txtMovimiento,_txtMonto,_txtDescripción,_txtFechaMovimiento,_txtusuarioNombre;
    Button _btnRegresar, _btnAnular;
    Cuenta cuenta;
    OperacionesRespons operacionesRespons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion_detalle);
        Inicializar();

        cuenta =(Cuenta) getIntent().getSerializableExtra("Cuenta");
        operacionesRespons =(OperacionesRespons) getIntent().getSerializableExtra("Operaciones");
        _txtOperacionId.setText("OP-" + String.format("%05d",operacionesRespons.getOperacionId()));
        _txtMovimiento.setText(operacionesRespons.getOperacionDetalleDescripcion());
        _txtMonto.setText(String.format("%.2f",operacionesRespons.getOperacionDetalleMonto()).replace("-",""));
        _txtDescripción.setText(operacionesRespons.getOperacionDescripcion());
        _txtFechaMovimiento.setText(operacionesRespons.getOperacionFechaOperacionString());
        _txtusuarioNombre.setText(operacionesRespons.getUsuarioNombre());

        _btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegresarACuenta();
            }
        });

        _btnAnular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OperacionDetalleActivity.this);
                builder.setMessage("Estas seguro de anular la operación");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AnularOperacion();
                        _btnAnular.setEnabled(false);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Activity activity;
        activity=this;
        OpcionesMenu opcionesMenu=new OpcionesMenu(this,item, activity);
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        RegresarACuenta();
    }

    private void RegresarACuenta() {

        Intent intent = new Intent(OperacionDetalleActivity.this, CuentaDetalleActivity.class);
        intent.putExtra("Cuenta", cuenta);
        startActivity(intent);

    }

    private void AnularOperacion() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OperacionDetalleActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);

        OperacionRequest operacion = new OperacionRequest();
        operacion.setOperacionId(operacionesRespons.getOperacionId());

        Call<GenericRespons> operacionCall = cuentaService.getAnularOperaciones(operacion);

        operacionCall.enqueue((new Callback<GenericRespons>() {
            @Override
            public void onResponse(Call<GenericRespons> call, Response<GenericRespons> response) {
                alertDialog1.dismiss();
                GenericRespons genericRespons = response.body();
                if (genericRespons.isSuccess()) {

                    Toast.makeText(OperacionDetalleActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RegresarACuenta();
                        }
                    },2000);


                } else {
                    Toast.makeText(OperacionDetalleActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    _btnAnular.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<GenericRespons> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(OperacionDetalleActivity.this);
                builder.setMessage("Tenemos algunos inconvenientes técnicos, intente mas tarde");
                builder.setCancelable(false);
                builder.setPositiveButton("Cerrar Aplicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        System.exit(1);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }));
    }

    private void Inicializar() {
        _txtOperacionId=(TextView) findViewById(R.id.txtOperacionId);
        _txtMovimiento=(TextView) findViewById(R.id.txtMovimiento);
        _txtMonto=(TextView) findViewById(R.id.txtMonto);
        _txtDescripción=(TextView) findViewById(R.id.txtDescripción);
        _txtFechaMovimiento=(TextView) findViewById(R.id.txtFechaMovimiento);
        _txtusuarioNombre=(TextView) findViewById(R.id.txtusuarioNombre);
        _btnRegresar=(Button) findViewById(R.id.btnRegresar);
        _btnAnular=(Button) findViewById(R.id.btnAnular);
    }
}