package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_rcPlanificacionMensual;
import com.franzruiz.fipere1.Adapters.adapter_rc_EstadoCuenta;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.EstadoCuenta;
import com.franzruiz.fipere1.Model.Request.PlanificacionMensualRequest;
import com.franzruiz.fipere1.Model.Respons.PlanificacionMensualRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstadoCuentaActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    Usuario usuario = null;
    TextView _titulo, txt_año;
    Integer i_año;
    Button btn_buscar;
    RecyclerView recycler_estado_cuenta;
    adapter_rc_EstadoCuenta adapter;
    PlanificacionMensualRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta);


        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));

        recycler_estado_cuenta=findViewById(R.id.recycler_estado_cuenta);

        //Obtener el año
        TimeZone myTimeZone1 = TimeZone.getTimeZone("America/Lima");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
        simpleDateFormat1.setTimeZone(myTimeZone1);
        String año = simpleDateFormat1.format(new Date());

        _titulo = (TextView) findViewById(R.id.txtTituloPlanificacion);
        _titulo.setText("Estado de cuenta del año " + año);
        this.setTitle("Estado de cuenta");

        i_año=Integer.parseInt (año);

        btn_buscar = (Button) findViewById(R.id.btn_buscar);

        txt_año =(TextView) findViewById(R.id.txt_año) ;
        txt_año.setText(año);

        MostrarEstadoCuenta(usuario.getUsuarioId(),i_año);

        //Evento del Boton
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos(txt_año.getText().toString())) {


                    i_año=  Integer.parseInt (txt_año.getText().toString());

                    _titulo.setText("Estado de cuenta del año " + i_año);
                    MostrarEstadoCuenta(usuario.getUsuarioId(),i_año);
                    btn_buscar.setEnabled(false);
                }
                else{
                    btn_buscar.setEnabled(true);
                }


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
    public void onBackPressed (){
        Intent intent=new Intent(EstadoCuentaActivity.this, CuentasActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean ValidarCampos(String año) {

        boolean rpta = true;
        txt_año.setError(null);

        if (año.length()<4) {
            txt_año.setError("Ingrese el año correctamente");
            txt_año.requestFocus();
            rpta = false;
        }
        else if (año.isEmpty()) {
            txt_año.setError("Ingrese un año");
            txt_año.requestFocus();
            rpta = false;
        }
        return rpta;
    }

    private void setRecyclerView(List<EstadoCuenta> rpta) {
        recycler_estado_cuenta.setHasFixedSize(false);
        recycler_estado_cuenta.setLayoutManager(new LinearLayoutManager(this));
        adapter=new adapter_rc_EstadoCuenta(this,rpta);
        recycler_estado_cuenta.setAdapter(adapter);
    }

    private void MostrarEstadoCuenta(int usuario,int año) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(EstadoCuentaActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        request=new PlanificacionMensualRequest();
        request.setUsuarioId(usuario);
        request.setAnio(año);

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<List<EstadoCuenta>> CuentasCall = cuentaService.getListar_estado_de_cuenta(request);
        CuentasCall.enqueue(new Callback<List<EstadoCuenta>>() {
            @Override
            public void onResponse(Call<List<EstadoCuenta>> call, Response<List<EstadoCuenta>> response) {
                alertDialog1.dismiss();
                List<EstadoCuenta> rpta = response.body();
                if(rpta!=null){
                    setRecyclerView(rpta);
                    btn_buscar.setEnabled(true);
                }
                else{
//                    setRecyclerView(rpta);
                    Toast.makeText(EstadoCuentaActivity.this, "No se encontró ningún registro" , Toast.LENGTH_SHORT).show();
                    btn_buscar.setEnabled(true);
                }



            }

            @Override
            public void onFailure(Call<List<EstadoCuenta>> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(EstadoCuentaActivity.this);
                builder.setMessage("Tenemos algunos inconvenientes técnicos, intente mas tarde");
                builder.setCancelable(false);
                builder.setPositiveButton("Cerrar la aplicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //moveTaskToBack(true);
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