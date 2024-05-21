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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Adapters.adapter_lvMovimientos;
import com.franzruiz.fipere1.Adapters.adapter_lvMovimientosPorMes;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimientosListaPorMesActivity extends AppCompatActivity {
    Usuario usuario = null;
    private SharedPreferences preferences;
    int MovimientoId,año,mes;
    double MontoPlanificado,MontoEjecutado;
    ListView listView;
    GenericRequest genericRequest;
    TextView txtMontoEjecutado, txtMontoPlanificado;
    Button btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_movimientos_lista_por_mes);

        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));
        MovimientoId = (int) getIntent().getSerializableExtra("MovimientoId");
        mes = (int) getIntent().getSerializableExtra("mes");
        año = (int) getIntent().getSerializableExtra("año");
        MontoPlanificado = (double) getIntent().getSerializableExtra("MontoPlanificado");
        MontoEjecutado = (double) getIntent().getSerializableExtra("MontoEjecutado");

        MostrarMovimiento();

        String[] meses = {"","ENE", "FEB","MAR", "ABR", "MAY", "JUN",
                "JUL", "AGO","SET", "OCT", "NOV", "DIC"};
        String  mes_texto =  meses[mes];
        this.setTitle("Movimientos Realizadas: " + mes_texto + "-"+ año);

        txtMontoEjecutado=  (TextView) findViewById(R.id.txtMontoEjecutado) ;
        txtMontoPlanificado=  (TextView) findViewById(R.id.txtMontoPlanificado) ;



        txtMontoEjecutado.setText("Ejecutado: " + String.format("%.2f",MontoEjecutado) );
        txtMontoPlanificado.setText("Planificado: " +  String.format("%.2f",MontoPlanificado) );


        btnRegresar=(Button)findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regresar();

            }
        });



    }

    private void Regresar() {
        Intent intent = new Intent(MovimientosListaPorMesActivity.this, PlanificacionActivity.class);
        intent.putExtra("mes", String.valueOf(mes) );
        intent.putExtra("anio",String.valueOf(año) );
        startActivity(intent);
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
        Regresar();
    }

    private void MostrarMovimiento() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MovimientosListaPorMesActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        genericRequest =new GenericRequest();
        genericRequest.setMovimientoId(MovimientoId);
        genericRequest.setAño(año);
        genericRequest.setMes(mes);
        genericRequest.setUsuarioId(usuario.getUsuarioId());

        CuentaService cuentaService= API.getAppi().create(CuentaService.class);

        Call<List<OperacionesRespons>> operacionesCall=cuentaService.getOperaciones_por_tipo_movimiento_y_fecha(genericRequest);

        operacionesCall.enqueue(new Callback<List<OperacionesRespons>>() {
            @Override
            public void onResponse(Call<List<OperacionesRespons>> call, Response<List<OperacionesRespons>> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    List<OperacionesRespons> operacionesRespons=response.body();

                    if(operacionesRespons.size()>0){
                        listView = (ListView) findViewById(R.id.lvMovimientos);
                        adapter_lvMovimientosPorMes myAdapter = new adapter_lvMovimientosPorMes(MovimientosListaPorMesActivity.this,R.layout.item_listviews_movimiento,operacionesRespons);
                        listView.setAdapter(myAdapter);
                    }
                    else{
                      Toast toast=  Toast.makeText(MovimientosListaPorMesActivity.this, "No se encontró ningún movimiento realizado" , Toast.LENGTH_SHORT);
                      toast.setGravity(Gravity.CENTER, 0, 0);
                      toast.show();

                    }



                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MovimientosListaPorMesActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MovimientosListaPorMesActivity.this);
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

