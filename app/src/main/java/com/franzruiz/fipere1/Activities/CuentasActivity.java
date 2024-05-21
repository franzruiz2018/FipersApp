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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_lvCuentas;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentasActivity extends AppCompatActivity {

    ListView listView;
    Usuario usuario;
    private SharedPreferences preferences;
    TextView txtusuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        preferences=getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String UsuarioId = preferences.getString("UsuarioId", "");
        String UsuarioNombre = preferences.getString("UsuarioNombre", "");

        txtusuarioNombre=(TextView) findViewById(R.id.txtusuarioNombre) ;
        txtusuarioNombre.setText("Bienvenido: "+UsuarioNombre);

        // Toast.makeText(this, "Bienvenido " + preferences.getString("UsuarioNombre", ""),Toast.LENGTH_LONG).show();

        usuario = new Usuario();
        usuario.setUsuarioId( Integer.valueOf(UsuarioId) );
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

    public void onBackPressed() {
       // moveTaskToBack(true);
        finish();
        System.exit(1);
    }

    private void MostrarCuentas() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CuentasActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<List<Cuenta>> CuentasCall = cuentaService.getListarCuentas(usuario);
        CuentasCall.enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                alertDialog1.dismiss();
                List<Cuenta> cuentas = response.body();
                listView = (ListView) findViewById(R.id.lvCuentas);
                adapter_lvCuentas _adapter_lvCuentas = new adapter_lvCuentas(CuentasActivity.this, R.layout.item_listviews_cuentas, cuentas);
                listView.setAdapter(_adapter_lvCuentas);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Cuenta cuenta = (Cuenta) adapterView.getItemAtPosition(position);
                        Intent intent = new Intent(CuentasActivity.this, CuentaDetalleActivity.class);
                        intent.putExtra("Cuenta", cuenta);
                        startActivity(intent);
                        listView.setEnabled(false);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(CuentasActivity.this);
                builder.setMessage("Tenemos algunos inconvenientes técnicos, intente mas tarde");
                builder.setCancelable(false);
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



}