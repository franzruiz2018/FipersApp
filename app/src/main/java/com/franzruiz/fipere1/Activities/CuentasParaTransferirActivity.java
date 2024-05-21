package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_gvCuentas;
import com.franzruiz.fipere1.Adapters.adapter_gv_cuentas_transferir;
import com.franzruiz.fipere1.Adapters.adapter_lvCuentas;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentasParaTransferirActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    Cuenta cuentaOrigen;
    CategoriaMovimiento categoriaMovimiento = null;
    GridView gridView;
    //Cambiar
    GenericRequest genericRequest;
    //Fin Cambiar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas_para_transferir);

        cuentaOrigen =(Cuenta) getIntent().getSerializableExtra("Cuenta");
        categoriaMovimiento = (CategoriaMovimiento) getIntent().getSerializableExtra("CategoriaMovimiento");

        preferences=getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String UsuarioId = preferences.getString("UsuarioId", "");

        //Cambiar
        genericRequest = new GenericRequest();
        genericRequest.setUsuarioId(Integer.valueOf(UsuarioId) );
        genericRequest.setCuentaId( cuentaOrigen.getCuentaId() );
        //Fin Cambiar
        MostrarCuentas();
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

    private void MostrarCuentas() {

        ////Crear mensaje de espera
            AlertDialog.Builder builder1 = new AlertDialog.Builder(CuentasParaTransferirActivity.this);
            builder1.setMessage("Loading...");
            builder1.setCancelable(false);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();
        ////Fin Crear mensaje de espera

        //Crear Instancia para el servicio
        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        //Llamado al metodo que lista las cuentas para transferir
        Call<List<Cuenta>> CuentasCall = cuentaService.getListarCuentasParaTransferir(genericRequest);

        //LLamado al Servicio
        CuentasCall.enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                //Cierra el mensaje de espera
                alertDialog1.dismiss();
                //Recibe la lista de cuentas
                List<Cuenta> cuentas = response.body();
                //instancia al grid view (activity_cuentas_para_transferir) que mostrará las cuentas
                gridView = (GridView) findViewById(R.id.gvCuentasTransferir);
                //instancia al adaptador que  recoge la información,  construye los item (item_gridview_cuentas_transferir) y lo muestra en el gridview (activity_cuentas_para_transferir)
                adapter_gv_cuentas_transferir _adapter_lvCuentas = new adapter_gv_cuentas_transferir(CuentasParaTransferirActivity.this, R.layout.item_gridview_cuentas_transferir, cuentas);
                gridView.setAdapter(_adapter_lvCuentas);

                //Construyendo un evento para el click de cada item
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        //Capturar la información del elemento seleccionado
                        Cuenta cuenta = (Cuenta) adapterView.getItemAtPosition(position);
                        //Abrir otro Activity
                        Intent intent = new Intent(CuentasParaTransferirActivity.this, RegistrarTransferencia.class);
                        //Enviar datos a otro activity
                        intent.putExtra("Cuenta_destino_tranferencia", cuenta);
                        intent.putExtra("Cuenta_origen_tranferencia", cuentaOrigen);
                        intent.putExtra("CategoriaMovimiento", categoriaMovimiento);
                        //Abrir Activity
                        startActivity(intent);
                        //Inhabilitar GridView
                        gridView.setEnabled(false);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {
                //Cerrar primer dialogo
                alertDialog1.dismiss();
                //Iniciar otro dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(CuentasParaTransferirActivity.this);
                builder.setMessage("Tenemos algunos inconvenientes técnicos, intente mas tarde");
                builder.setCancelable(false);
                //Boton Cerrar la Aplicación
                builder.setPositiveButton("Cerrar la aplicación", new DialogInterface.OnClickListener() {
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


    private void goToCuentas() {
        Intent intent = new Intent(CuentasParaTransferirActivity.this, CuentasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed (){
        goToCuentas();
    }

}