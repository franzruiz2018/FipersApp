package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_lvMovimientos;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaDetalleActivity extends AppCompatActivity {
    ListView listView;

    private Cuenta cuenta;
    TextView _nombreCuenta, _saldoCuenta,_descripcionSaldo;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_detalle);

        cuenta =(Cuenta) getIntent().getSerializableExtra("Cuenta");

        Inicializar();

        MostrarSaldo();
        MostrarMovimiento();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CuentaDetalleActivity.this, OperacionesCuentaActivity.class );
                intent.putExtra("Cuenta", cuenta);
                startActivity(intent);
                fab.setEnabled(false);
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

    private void Inicializar() {
        _nombreCuenta=(TextView) findViewById(R.id.txtNombreCuenta) ;
        _saldoCuenta = (TextView)findViewById(R.id.txtSaldo);
        _descripcionSaldo = (TextView)findViewById(R.id.txtDescripcionSaldo);
        fab = findViewById(R.id.fabRegistrarMovimiento);
    }

    @Override
    public void onBackPressed (){
        Intent intent=new Intent(CuentaDetalleActivity.this, CuentasActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void MostrarSaldo() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CuentaDetalleActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService= API.getAppi().create(CuentaService.class);
        Call<Cuenta> cuentaCall=cuentaService.getCuenta(cuenta);

        cuentaCall.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    Cuenta c=response.body();
                    if(c.getTipoCuentaId()==3){
                        _descripcionSaldo.setText("Deuda pendiente de pago");
                        _saldoCuenta.setText(String.format("%.2f",c.getCuentaDeuda()));
                    }
                    else{
                        _descripcionSaldo.setText("Saldo disponible");
                        _saldoCuenta.setText(String.format("%.2f",c.getCuentaSaldo()));
                    }

                    _nombreCuenta.setText(c.getCuentaNombre());
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CuentaDetalleActivity.this);
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
            }

            @Override
            public void onFailure(Call<Cuenta> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CuentaDetalleActivity.this);
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
        });


    }

    private void MostrarMovimiento() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CuentaDetalleActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService= API.getAppi().create(CuentaService.class);

        Call<List<OperacionesRespons>> operacionesCall=cuentaService.getOperaciones(cuenta);

        operacionesCall.enqueue(new Callback<List<OperacionesRespons>>() {
            @Override
            public void onResponse(Call<List<OperacionesRespons>> call, Response<List<OperacionesRespons>> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    List<OperacionesRespons> operacionesRespons=response.body();
                    listView = (ListView) findViewById(R.id.lvMovimientos);
                    adapter_lvMovimientos myAdapter = new adapter_lvMovimientos(CuentaDetalleActivity.this,R.layout.item_listviews_movimiento,operacionesRespons);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            OperacionesRespons operacionesResponsClick = (OperacionesRespons) adapterView.getItemAtPosition(position);
                            Intent intent=new Intent(CuentaDetalleActivity.this, OperacionDetalleActivity.class);
                            intent.putExtra("Operaciones", operacionesResponsClick);
                            intent.putExtra("Cuenta", cuenta);
                            startActivity(intent);
                            listView.setEnabled(false);
                        }
                    });

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CuentaDetalleActivity.this);
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

            }

            @Override
            public void onFailure(Call<List<OperacionesRespons>> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CuentaDetalleActivity.this);
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
        });




    }


}