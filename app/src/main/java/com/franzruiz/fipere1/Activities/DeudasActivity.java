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

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_lvDeudas;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Respons.DeudaRespons;
import com.franzruiz.fipere1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeudasActivity extends AppCompatActivity {

    Cuenta cuentaOrigen;
    ListView listView;
    GenericRequest genericRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);

        cuentaOrigen =(Cuenta) getIntent().getSerializableExtra("Cuenta");

        this.setTitle("Cuenta "+ cuentaOrigen.getCuentaNombre());

        genericRequest = new GenericRequest();
        genericRequest.setCuentaId( cuentaOrigen.getCuentaId() );

        MostrarDeudas();
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

    private void MostrarDeudas() {

        ////Crear mensaje de espera
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DeudasActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();
        ////Fin Crear mensaje de espera

        //Crear Instancia para el servicio
        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        //Llamado al metodo que lista las cuentas para transferir
        Call<List<DeudaRespons>> DeudasCall = cuentaService.getlistar_deudas_por_cuenta(genericRequest);

        //LLamado al Servicio
        DeudasCall.enqueue(new Callback<List<DeudaRespons>>() {
            @Override
            public void onResponse(Call<List<DeudaRespons>> call, Response<List<DeudaRespons>> response) {
                //Cierra el mensaje de espera
                alertDialog1.dismiss();
                //Recibe la lista de deudas
                List<DeudaRespons> deudas = response.body();
                //instancia al grid view (activity_cuentas_para_transferir) que mostrará las cuentas
                listView = (ListView) findViewById(R.id.lvDeuda);
                //instancia al adaptador que  recoge la información,  construye los item (item_gridview_cuentas_transferir) y lo muestra en el gridview (activity_cuentas_para_transferir)
                adapter_lvDeudas _adapter_lvCuentas = new adapter_lvDeudas( DeudasActivity.this, R.layout.item_listview_deudas, deudas);
                listView.setAdapter(_adapter_lvCuentas);

                //Construyendo un evento para el click de cada item
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        //Capturar la información del elemento seleccionado
                        DeudaRespons deuda = (DeudaRespons) adapterView.getItemAtPosition(position);
//                        //Abrir otro Activity
                        Intent intent = new Intent(DeudasActivity.this, CuentasParaPagarDeudasActivity.class);
//                        //Enviar datos a otro activity
                        intent.putExtra("Deuda", deuda);
                        intent.putExtra("Cuenta_Origen", cuentaOrigen);
//                        //Abrir Activity
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<DeudaRespons>> call, Throwable t) {
                //Cerrar primer dialogo
                alertDialog1.dismiss();
                //Iniciar otro dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(DeudasActivity.this);
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeudasActivity.this, CuentaDetalleActivity.class);
        intent.putExtra("Cuenta", cuentaOrigen);
        startActivity(intent);
    }

}