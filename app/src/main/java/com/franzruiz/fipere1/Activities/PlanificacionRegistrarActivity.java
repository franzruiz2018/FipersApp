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
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Request.PlanificacionMensualRegistroRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Respons.MovimientoTipoRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanificacionRegistrarActivity extends AppCompatActivity {

    Spinner spinnerTipoMovimiento;
    MovimientoTipoRespons movimientoTipo = null;
    Usuario usuario = null;
    private SharedPreferences preferences;
    String mes,año;
    TextView txtTitulo,txtMonto;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificacion_registrar);
        CargarSpinnerTipoMovimiento();
        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spTipoMovimiento);

        mes =(String) getIntent().getSerializableExtra("mes");
        año =(String) getIntent().getSerializableExtra("anio");

        txtTitulo = (TextView)findViewById(R.id.txtTitulo) ;
        txtMonto = (TextView)findViewById(R.id.txtMonto) ;
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar) ;

        txtTitulo.setText("Planifica tu gasto e inversión del periodo" + " " + mes + "-" + año);

        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos(txtMonto.getText().toString())) {
                    RegistrarPlanificacion();
                    btnRegistrar.setEnabled(false);
                }
                else{
                    btnRegistrar.setEnabled(true);
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
        Intent intent=new Intent(PlanificacionRegistrarActivity.this, PlanificacionActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void CargarSpinnerTipoMovimiento() {


        CuentaService tipoMovimientoService = API.getAppi().create(CuentaService.class);

        Call<List<MovimientoTipoRespons>> tipoMovimientoResponse = tipoMovimientoService.getlistar_movimientos_para_planificacion();

        tipoMovimientoResponse.enqueue(new Callback<List<MovimientoTipoRespons>>() {
            @Override
            public void onResponse(Call<List<MovimientoTipoRespons>> call, Response<List<MovimientoTipoRespons>> response) {
                if (response.isSuccessful()) {
                    List<MovimientoTipoRespons> l = response.body();
                    //Converit a un Array
                    MovimientoTipoRespons[] __movimientoTipoRespons = new MovimientoTipoRespons[l.size()];
                    __movimientoTipoRespons = l.toArray(__movimientoTipoRespons);
                    //Cargar Datos en el Spinner
                    MySpinnerAdapter adapter = new MySpinnerAdapter(PlanificacionRegistrarActivity.this, R.layout.spinner_item_tipo_gasto, __movimientoTipoRespons);

                    spinnerTipoMovimiento.setAdapter(adapter);

                    spinnerTipoMovimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            MovimientoTipoRespons movimientoTipoRespons = (MovimientoTipoRespons) parent.getSelectedItem();
                            movimientoTipo = new MovimientoTipoRespons();
                            movimientoTipo.setMovimientoId(movimientoTipoRespons.getMovimientoId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlanificacionRegistrarActivity.this);
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
            public void onFailure(Call<List<MovimientoTipoRespons>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanificacionRegistrarActivity.this);
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

    public static class MySpinnerAdapter extends ArrayAdapter<MovimientoTipoRespons> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private MovimientoTipoRespons[] values;

        public MySpinnerAdapter(Context context, int textViewResourceId,
                                MovimientoTipoRespons[] values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public MovimientoTipoRespons getItem(int position) {
            return values[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = (TextView) super.getView(position, convertView, parent);
            //    label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            label.setText(values[position].getMovimientoNombre());

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            //  label.setTextColor(Color.BLACK);
            label.setText(values[position].getMovimientoNombre());

            return label;
        }
    }


    private void RegistrarPlanificacion() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PlanificacionRegistrarActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);

        //Obtener la fecha
        TimeZone myTimeZone = TimeZone.getTimeZone("America/Lima");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(myTimeZone);
        String fecha = simpleDateFormat.format(new Date());

        PlanificacionMensualRegistroRequest request = new PlanificacionMensualRegistroRequest();
        request.setEgresosPlanificadosFecha( año + "-"+ mes+ "-" +"01") ;
        request.setEgresosPlanificadosMonto( Double.parseDouble(txtMonto.getText().toString()) );
        request.setMovimientoId(movimientoTipo.getMovimientoId());
        request.setUsuarioId(usuario.getUsuarioId());

        Call<GenericRespons> operacionCall = cuentaService.getEgresos_planificados_registrar(request);

        operacionCall.enqueue((new Callback<GenericRespons>() {
            @Override
            public void onResponse(Call<GenericRespons> call, Response<GenericRespons> response) {
                alertDialog1.dismiss();
                GenericRespons genericRespons = response.body();
                if (genericRespons.isSuccess()) {

                    Toast.makeText(PlanificacionRegistrarActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(PlanificacionRegistrarActivity.this, PlanificacionActivity.class);
                            intent.putExtra("mes", mes);
                            intent.putExtra("anio", año);
                            startActivity(intent);
                        }
                    },1000);


                } else {
                    Toast.makeText(PlanificacionRegistrarActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    btnRegistrar.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<GenericRespons> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanificacionRegistrarActivity.this);
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

    private boolean ValidarCampos( String monto) {

        boolean rpta = true;
        txtMonto.setError(null);

        if (monto.isEmpty()) {
            txtMonto.setError("Ingrese un monto");
            txtMonto.requestFocus();
            rpta = false;
        }
        return rpta;
    }
}