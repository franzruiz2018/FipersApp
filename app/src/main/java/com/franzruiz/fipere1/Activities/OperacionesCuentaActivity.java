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
import android.widget.GridView;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_gvCuentas;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperacionesCuentaActivity extends AppCompatActivity {

    GridView gridView;
    private Cuenta cuenta;
    int _tipocuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_cuenta);

        cuenta =(Cuenta) getIntent().getSerializableExtra("Cuenta");
        _tipocuenta= cuenta.getTipoCuentaId();
        MostrarOperaciones();

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
        Intent intent = new Intent(OperacionesCuentaActivity.this, CuentaDetalleActivity.class);
        intent.putExtra("Cuenta", cuenta);
        startActivity(intent);
    }

    private void MostrarOperaciones() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OperacionesCuentaActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService= API.getAppi().create(CuentaService.class);

        Call<List<CategoriaMovimiento>> catMovCall=cuentaService.getListarTipoMov_por_cuenta(cuenta);

        catMovCall.enqueue(new Callback<List<CategoriaMovimiento>>() {
            @Override
            public void onResponse(Call<List<CategoriaMovimiento>> call, Response<List<CategoriaMovimiento>> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    List<CategoriaMovimiento> categoriaMovimientos = response.body();
                    gridView = (GridView) findViewById(R.id.gvOperaciones);
                    adapter_gvCuentas _adapter_lvCuentas = new adapter_gvCuentas(OperacionesCuentaActivity.this, R.layout.item_gridview_operaciones, categoriaMovimientos, OperacionesCuentaActivity.this.cuenta);
                    gridView.setAdapter(_adapter_lvCuentas);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            CategoriaMovimiento categoriaMovimiento = (CategoriaMovimiento) adapterView.getItemAtPosition(position);
                            int _tipoMovimiento = categoriaMovimiento.getCategoriaMovimientoId();
                            //Si es tranferencia
                            if(_tipoMovimiento==3){
                            Intent intent2 = new Intent(view.getContext(), CuentasParaTransferirActivity.class);
                            intent2.putExtra("CategoriaMovimiento", categoriaMovimiento);
                            intent2.putExtra("Cuenta", cuenta);
                            startActivity(intent2);
                            }else if(_tipoMovimiento==4){
                                    Intent intent2 = new Intent(view.getContext(), DeudasActivity.class);
                                    intent2.putExtra("CategoriaMovimiento", categoriaMovimiento);
                                    intent2.putExtra("Cuenta", cuenta);
                                    startActivity(intent2);
                            }
                            else{
                                Intent intent2 = new Intent(view.getContext(), RegistrarMovimiento.class);
                                intent2.putExtra("CategoriaMovimiento", categoriaMovimiento);
                                intent2.putExtra("Cuenta", cuenta);
                                startActivity(intent2);
                            }
                            gridView.setEnabled(false);



                        }
                    });

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(OperacionesCuentaActivity.this);
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
            public void onFailure(Call<List<CategoriaMovimiento>> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(OperacionesCuentaActivity.this);
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