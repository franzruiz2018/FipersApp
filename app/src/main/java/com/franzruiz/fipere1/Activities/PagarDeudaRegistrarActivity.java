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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Respons.DeudaRespons;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagarDeudaRegistrarActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    Cuenta cuenta_que_paga,cuenta_origen;
    DeudaRespons deuda;
    String usuarioId;
    TextView txt_Cuenta,txtPeriodoPago,txtUltimoDiaPago,txtMonto,txtCuentaPagaDeuda;
    Button btnRegresar,btnPagar;
    double saldo_cuenta_que_paga=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_deuda_registrar);

        this.setTitle("Pago de deuda de tarjeta de dredito");

        cuenta_que_paga =(Cuenta) getIntent().getSerializableExtra("Cuenta_que_paga_deuda");
        deuda = (DeudaRespons) getIntent().getSerializableExtra("Deuda");
        cuenta_origen = (Cuenta) getIntent().getSerializableExtra("Cuenta_Origen");




        preferences=getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuarioId = preferences.getString("UsuarioId", "");

        Inicializar();

        BuscarSaldo();

        txt_Cuenta.setText(cuenta_origen.getCuentaNombre());
        txtMonto.setText(String.valueOf(deuda.getCreditoDeudaMonto()));
        txtPeriodoPago.setText(String.valueOf(deuda.getCreditoDeudaMesPago())+" - " + String.valueOf(deuda.getCreditoDeudaAnioPago()) );
        txtUltimoDiaPago.setText(String.valueOf(cuenta_origen.getCuentaPagoDia())+" / " +String.valueOf(deuda.getCreditoDeudaMesPago())+" / " + String.valueOf(deuda.getCreditoDeudaAnioPago()));
        txtCuentaPagaDeuda.setText(cuenta_que_paga.getCuentaNombre() + " - " + saldo_cuenta_que_paga);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegresarACuenta();
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos()) {
                    RegistrarPagoDeuda();
                    btnPagar.setEnabled(false);
                }
                else{
                    btnPagar.setEnabled(true);
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

    private boolean ValidarCampos() {

        boolean rpta = true;


        return rpta;
    }

    private void Inicializar() {
        txt_Cuenta=(TextView) findViewById(R.id.txt_Cuenta) ;
        txtPeriodoPago=(TextView) findViewById(R.id.txtPeriodoPago) ;
        txtUltimoDiaPago=(TextView) findViewById(R.id.txtUltimoDiaPago) ;
        txtMonto=(TextView) findViewById(R.id.txtMonto) ;
        txtCuentaPagaDeuda=(TextView) findViewById(R.id.txtCuentaPagaDeuda) ;

        btnRegresar=(Button) findViewById(R.id.btnRegresar) ;
        btnPagar=(Button) findViewById(R.id.btnPagar) ;

    }

    @Override
    public void onBackPressed (){
        RegresarACuenta();
    }
    private void RegresarACuenta() {
        Intent intent=new Intent(PagarDeudaRegistrarActivity.this, CuentasParaPagarDeudasActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Deuda", deuda);
        intent.putExtra("Cuenta_Origen", cuenta_origen);
        startActivity(intent);
    }

    private void RegistrarPagoDeuda() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PagarDeudaRegistrarActivity.this);
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

        OperacionRequest operacion = new OperacionRequest();

        operacion.setCuentaId(cuenta_origen.getCuentaId());
        operacion.setMovimientoId(10);
        operacion.setOperacionFechaOperacion(fecha);
        operacion.setOperacionMonto(deuda.getCreditoDeudaMonto());
        operacion.setOperacionDescripcion("Pago de deuda");
        operacion.setUsuarioId(Integer.parseInt(usuarioId) );
        operacion.setCreditoDeudaFechaPago(fecha);
        operacion.setOperacionCuentaSalida(cuenta_que_paga.getCuentaId());
        operacion.setDeudaAño(deuda.getCreditoDeudaAnioPago());
        operacion.setDeudaMes(deuda.getCreditoDeudaMesPago());




        Call<GenericRespons> operacionCall = cuentaService.getRegistrarOperaciones(operacion);

        operacionCall.enqueue((new Callback<GenericRespons>() {
            @Override
            public void onResponse(Call<GenericRespons> call, Response<GenericRespons> response) {
                alertDialog1.dismiss();
                GenericRespons genericRespons = response.body();
                if (genericRespons.isSuccess()) {

                    Toast.makeText(PagarDeudaRegistrarActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(PagarDeudaRegistrarActivity.this, CuentaDetalleActivity.class);
                            intent.putExtra("Cuenta", cuenta_origen);
                            startActivity(intent);
                        }
                    },1000);


                } else {
                    Toast.makeText(PagarDeudaRegistrarActivity.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    btnPagar.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<GenericRespons> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(PagarDeudaRegistrarActivity.this);
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

    private void BuscarSaldo() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PagarDeudaRegistrarActivity.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<Cuenta> cuentaCall = cuentaService.getCuenta(cuenta_que_paga);

        cuentaCall.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    Cuenta c = response.body();
                    saldo_cuenta_que_paga =c.getCuentaSaldo();
                    txtCuentaPagaDeuda.setText(cuenta_que_paga.getCuentaNombre() + " (" + saldo_cuenta_que_paga+")");

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PagarDeudaRegistrarActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(PagarDeudaRegistrarActivity.this);
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