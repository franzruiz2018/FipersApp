package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarTransferencia extends AppCompatActivity {


    Cuenta cuentaOrigen;
    Cuenta cuentaDestino;
    CategoriaMovimiento categoriaMovimiento = null;
    TextView txtCuentaDestino;
    TextView _nombreCuenta, _saldoCuenta, _descripcionSaldo;
    TextView _txtMonto;
    TextView _txtDescripcion;
    TextView _txtFecha;
    Button _btnRegistrarOperacion;

    private SharedPreferences preferences;
    Usuario usuario = null;

    //Variables Fecha
        //Variables para obtener la fecha
        private static final String CERO = "0";
        private static final String BARRA = "-";
        private static final String DOS_PUNTOS = ":";
        //Calendario para obtener fecha & hora
        public final Calendar c = Calendar.getInstance();
        //Variables para obtener la hora hora
        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int minuto = c.get(Calendar.MINUTE);
    //Fin Variables Fecha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_transferencia);


        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));

        //Recibiendo datos de otro activity
        cuentaOrigen =(Cuenta) getIntent().getSerializableExtra("Cuenta_origen_tranferencia");
        cuentaDestino =(Cuenta) getIntent().getSerializableExtra("Cuenta_destino_tranferencia");
        categoriaMovimiento = (CategoriaMovimiento) getIntent().getSerializableExtra("CategoriaMovimiento");


        //Personalizando Titulo
        //setTitle("Transferir a  " + cuentaDestino.getCuentaNombre());


        //Declarar los controles
        txtCuentaDestino = (TextView) this.findViewById(R.id.txtCuentaDestino);
        ///////Campos de la cuenta origen
        _saldoCuenta = (TextView) findViewById(R.id.txtSaldo);
        _descripcionSaldo = (TextView) findViewById(R.id.txtDescripcionSaldo);
        _nombreCuenta = (TextView) findViewById(R.id.txtNombreCuenta);
        //////Campos del formulario
        _txtMonto = (TextView) findViewById(R.id.txtMonto);
        _txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        _txtFecha = (TextView) findViewById(R.id.txtFecha);
        _btnRegistrarOperacion = (Button) findViewById(R.id.btnRegistrarMovimiento);







        //Iniciar metodos
        MostrarCuentaDestino();
        MostrarSaldo();


        //Mostrar el datePickerDialog
        _txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);
                //Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                //String s = formatter.format(c.getTime());
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarTransferencia.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                        final int mesActual = monthOfYear + 1;
                        //Formateo el día obtenido: antepone el 0 si son menores de 10
                        String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                        //Formateo el mes obtenido: antepone el 0 si son menores de 10
                        String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                        //Muestro la fecha con el formato deseado
                        _txtFecha.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
                        //  Toast.makeText(RegistrarMovimiento.this, dayOfMonth +"-"+(monthOfYear+1)+"-"+year,Toast.LENGTH_LONG ).show();
                    }
                }, anio, mes, dia);
                datePickerDialog.show();

            }
        });

        //Evento del Boton
        _btnRegistrarOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos(_txtFecha.getText().toString(), _txtMonto.getText().toString(), _txtDescripcion.getText().toString())) {
                    RegistrarOperacion();
                    _btnRegistrarOperacion.setEnabled(false);
                }
                else{
                    _btnRegistrarOperacion.setEnabled(true);
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

    //Metodo para mostrar la cuenta destino
    private void MostrarCuentaDestino() {
        txtCuentaDestino.setText("Cuenta Destino : " + cuentaDestino.getCuentaNombre());
    }



    //Metodo para crear una activity para el boton hacia atras
    private void goToCuentas() {
        Intent intent = new Intent(RegistrarTransferencia.this, CuentasParaTransferirActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Enviar datos a otro activity
        intent.putExtra("Cuenta", cuentaOrigen);

        startActivity(intent);
    }

    //Configurando el boton hacia atras
    @Override
    public void onBackPressed (){
        goToCuentas();
    }

    //Mostrar Saldo
    private void MostrarSaldo() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrarTransferencia.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<Cuenta> cuentaCall = cuentaService.getCuenta(cuentaOrigen);

        cuentaCall.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    Cuenta c = response.body();
                    if (c.getTipoCuentaId() == 3) {
                        //En teoria nunca deberia entrar acá
                        _descripcionSaldo.setText("Deuda pendiente de pago");
                    } else {
                        _descripcionSaldo.setText("Saldo disponible");
                    }
                    _saldoCuenta.setText(String.format("%.2f", c.getCuentaSaldo()));
                    _nombreCuenta.setText(c.getCuentaNombre());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarTransferencia.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarTransferencia.this);
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

    //Validar los campor del formulario
    private boolean ValidarCampos(String fecha, String monto, String descripcion) {

        boolean rpta = true;
        _txtFecha.setError(null);
        _txtMonto.setError(null);
        _txtDescripcion.setError(null);

        if (fecha.isEmpty()) {
            _txtFecha.setError("Ingrese una fecha");
            _txtFecha.requestFocus();
            rpta = false;
        }
        if (monto.isEmpty()) {
            _txtMonto.setError("Ingrese un monto");
            _txtMonto.requestFocus();
            rpta = false;
        }else if (Double.parseDouble(monto) <= 0) {
            _txtMonto.setError("Ingrese un monto");
            _txtMonto.requestFocus();
            rpta = false;
        }
        if (descripcion.isEmpty()) {
            _txtDescripcion.setError("Ingrese una descripción");
            _txtDescripcion.requestFocus();
            rpta = false;
        }
        return rpta;
    }


    private void RegistrarOperacion() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrarTransferencia.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);

        OperacionRequest operacion = new OperacionRequest();
        operacion.setCuentaId(cuentaOrigen.getCuentaId());
        operacion.setOperacionCuentaDestino(cuentaDestino.getCuentaId());
        operacion.setMovimientoId(9);


        //Obtener la Hora
        TimeZone myTimeZone = TimeZone.getTimeZone("America/Lima");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(myTimeZone);
        String hora = simpleDateFormat.format(new Date());

        String fecha = _txtFecha.getText().toString() + " " + hora ;

        operacion.setOperacionFechaOperacion(fecha);
        operacion.setOperacionMonto(Double.parseDouble(_txtMonto.getText().toString()));
        operacion.setOperacionDescripcion(_txtDescripcion.getText().toString());
        operacion.setUsuarioId(usuario.getUsuarioId());


        Call<GenericRespons> operacionCall = cuentaService.getRegistrarOperaciones(operacion);

        operacionCall.enqueue((new Callback<GenericRespons>() {
            @Override
            public void onResponse(Call<GenericRespons> call, Response<GenericRespons> response) {
                alertDialog1.dismiss();
                GenericRespons genericRespons = response.body();
                if (genericRespons.isSuccess()) {

                    Toast.makeText(RegistrarTransferencia.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegistrarTransferencia.this, CuentaDetalleActivity.class);
                            intent.putExtra("Cuenta", cuentaOrigen);
                            startActivity(intent);
                        }
                    },1000);


                } else {
                    Toast.makeText(RegistrarTransferencia.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    _btnRegistrarOperacion.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<GenericRespons> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarTransferencia.this);
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





}