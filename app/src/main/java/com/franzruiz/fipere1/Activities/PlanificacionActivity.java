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
import com.franzruiz.fipere1.Menu.OpcionesMenu;
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

public class PlanificacionActivity extends AppCompatActivity {

    RecyclerView recycler_planificacion;
    adapter_rcPlanificacionMensual adapter;
    TextView txt_mes, txt_año;
    PlanificacionMensualRequest request;
    Integer i_mes,i_año;
    Button btn_buscar,btn_registrar;
    private SharedPreferences preferences;
    Usuario usuario = null;
    String mes,año;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificacion);

        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));

        recycler_planificacion=findViewById(R.id.recycler_planificacion);

        //setRecyclerView();

        mes =(String) getIntent().getSerializableExtra("mes");
        año =(String) getIntent().getSerializableExtra("anio");

        if(mes==null || año==null ){
            //Obtener el mes
            TimeZone myTimeZone = TimeZone.getTimeZone("America/Lima");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
            simpleDateFormat.setTimeZone(myTimeZone);
            mes = simpleDateFormat.format(new Date());

            //Obtener el año
            TimeZone myTimeZone1 = TimeZone.getTimeZone("America/Lima");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
            simpleDateFormat1.setTimeZone(myTimeZone1);
            año = simpleDateFormat1.format(new Date());
        }




        // array de meses
       // String[] meses = {"","enero", "febrero","marzo", "abril", "mayo", "junio",
         //       "julio", "agosto","setiembre", "octubre", "noviembre", "diciembre"};

        // array de meses
        String[] meses = {"","ENE", "FEB","MAR", "ABR", "MAY", "JUN",
                "JUL", "AGO","SET", "OCT", "NOV", "DIC"};


        String  mes_texto =  meses[Integer.parseInt (mes)];


        this.setTitle("Planificación mensual: " + mes_texto + "-"+ año);

        i_mes=Integer.parseInt (mes);
        i_año=Integer.parseInt (año);

        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        btn_registrar = (Button) findViewById(R.id.btnRegistrar);

        txt_mes =(TextView) findViewById(R.id.txt_mes) ;
        txt_año =(TextView) findViewById(R.id.txt_año) ;

        txt_mes.setText(mes);
        txt_año.setText(año);

        MostrarCuentas(usuario.getUsuarioId(),i_mes,i_año);



        //Evento del Boton
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos(txt_mes.getText().toString(), txt_año.getText().toString())) {

                    i_mes=  Integer.parseInt (txt_mes.getText().toString());
                    i_año=  Integer.parseInt (txt_año.getText().toString());

                    setTitle("Planificación mensual: " + mes_texto + "-"+ año);
                    MostrarCuentas(usuario.getUsuarioId(),i_mes,i_año);
                    btn_buscar.setEnabled(false);
                }
                else{
                    btn_buscar.setEnabled(true);
                }


            }
        });

        //Evento del Boton
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PlanificacionActivity.this, PlanificacionRegistrarActivity.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("mes", txt_mes.getText().toString());
                intent.putExtra("anio", txt_año.getText().toString());
                startActivity(intent);

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

    private boolean ValidarCampos(String mes,  String año) {

        boolean rpta = true;
        txt_mes.setError(null);
        txt_año.setError(null);

        if (mes.isEmpty()) {
            txt_mes.setError("Ingrese un mes");
            txt_mes.requestFocus();
            rpta = false;
        }else if (año.length()<4) {
            txt_año.setError("Ingrese el año correctamente");
            txt_año.requestFocus();
            rpta = false;
        }
        else if (Integer.parseInt(mes)>12) {
            txt_mes.setError("Ingrese el mes correctamente");
            txt_mes.requestFocus();
            rpta = false;
        }
        else if (Integer.parseInt(mes)<1) {
            txt_mes.setError("Ingrese el mes correctamente");
            txt_mes.requestFocus();
            rpta = false;
        }
        else if (año.isEmpty()) {
            txt_año.setError("Ingrese un año");
            txt_año.requestFocus();
            rpta = false;
        }
        return rpta;
    }


    private void setRecyclerView(List<PlanificacionMensualRespons> rpta) {
        recycler_planificacion.setHasFixedSize(false);
        recycler_planificacion.setLayoutManager(new LinearLayoutManager(this));
        adapter=new adapter_rcPlanificacionMensual(this,rpta,i_mes,i_año);
        recycler_planificacion.setAdapter(adapter);


    }

//    private List<PlanificacionMensualRespons> getList(){
//        List<PlanificacionMensualRespons> list =new ArrayList<>();
//        list.add(new PlanificacionMensualRespons(1,"Alimentos",200,100));
//        list.add(new PlanificacionMensualRespons(2,"Frutas",200,100));
//        list.add(new PlanificacionMensualRespons(3,"Limpieza",200,100));
//        list.add(new PlanificacionMensualRespons(4,"Transporte",200,100));
//        return list;
//    }

    @Override
    public void onBackPressed (){
        Intent intent=new Intent(PlanificacionActivity.this, CuentasActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void MostrarCuentas(int usuario, int mes,int año) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(PlanificacionActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        request=new PlanificacionMensualRequest();
        request.setUsuarioId(usuario);
        request.setMes(mes);
        request.setAnio(año);

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<List<PlanificacionMensualRespons>> CuentasCall = cuentaService.getlistar_egresos_planificados_vs_real(request);
        CuentasCall.enqueue(new Callback<List<PlanificacionMensualRespons>>() {
            @Override
            public void onResponse(Call<List<PlanificacionMensualRespons>> call, Response<List<PlanificacionMensualRespons>> response) {
                alertDialog1.dismiss();
                List<PlanificacionMensualRespons> rpta = response.body();
                if(rpta!=null){
                    setRecyclerView(rpta);
                    btn_buscar.setEnabled(true);
                }
                else{
//                    setRecyclerView(rpta);
                    Toast.makeText(PlanificacionActivity.this, "No se encontró ningún registro" , Toast.LENGTH_SHORT).show();
                    btn_buscar.setEnabled(true);
                }



            }

            @Override
            public void onFailure(Call<List<PlanificacionMensualRespons>> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanificacionActivity.this);
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