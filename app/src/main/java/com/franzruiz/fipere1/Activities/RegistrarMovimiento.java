package com.franzruiz.fipere1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Menu.OpcionesMenu;
import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Respons.MovimientoTipoRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarMovimiento extends AppCompatActivity {
    //Variables para obtener la fecha
    private static final String CERO = "0";
    private static final String BARRA = "-";
    private static final String DOS_PUNTOS = ":";
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    CategoriaMovimiento categoriaMovimiento = null;
    Cuenta cuenta = null;
    Usuario usuario = null;
    MovimientoTipoRespons movimientoTipo = null;

    TextView _nombreCuenta, _saldoCuenta, _descripcionSaldo;

    Spinner spinnerTipoMovimiento;
    Button _btnRegistrarOperacion;

    TextView _txtMonto;
    TextView _txtDescripcion;
    TextView _txtFecha;
//    TextView _txtHora;


    private SharedPreferences preferences;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrarMovimiento.this, OperacionesCuentaActivity.class);
        intent.putExtra("Cuenta", cuenta);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_movimiento);

        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setUsuarioId(Integer.parseInt(preferences.getString("UsuarioId", "")));

        categoriaMovimiento = (CategoriaMovimiento) getIntent().getSerializableExtra("CategoriaMovimiento");
        cuenta = (Cuenta) getIntent().getSerializableExtra("Cuenta");

        setTitle("Registrar " + categoriaMovimiento.getCategoriaMovimientoNombre());
        Inicializar();
        MostrarSaldo();
        CargarSpinnerTipogasto();


        _txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);
//                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
//                String s = formatter.format(c.getTime());
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarMovimiento.this, new DatePickerDialog.OnDateSetListener() {
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

//        _txtHora.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View view) {
//
//
//                int hora = c.get(Calendar.HOUR_OF_DAY);
//                int minuto = c.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(RegistrarMovimiento.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//
//                        //Formateo el hora obtenido: antepone el 0 si son menores de 10
//                        String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
//                        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
//                        String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
//                        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
//                        String AM_PM;
//                        if (hourOfDay < 12) {
//                            AM_PM = "a.m.";
//                        } else {
//                            AM_PM = "p.m.";
//                        }
//                        //Muestro la hora con el formato deseado
//                        _txtHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
//                    }
//                }, hora, minuto, false);
//                timePickerDialog.show();
//
//
//            }
//        });


        _btnRegistrarOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidarCampos(_txtFecha.getText().toString(),  _txtMonto.getText().toString(), _txtDescripcion.getText().toString())) {
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

    private void Inicializar() {
        _txtMonto = (TextView) findViewById(R.id.txtMonto);
        _txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        _txtFecha = (TextView) findViewById(R.id.txtFecha);


//        _txtHora = (TextView) findViewById(R.id.txtHora);
        _nombreCuenta = (TextView) findViewById(R.id.txtNombreCuenta);
        _btnRegistrarOperacion = (Button) findViewById(R.id.btnRegistrarMovimiento);
        _saldoCuenta = (TextView) findViewById(R.id.txtSaldo);
        _descripcionSaldo = (TextView) findViewById(R.id.txtDescripcionSaldo);
        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spTipoGasto);
    }

    private void CargarSpinnerTipogasto() {


        CuentaService tipoMovimientoService = API.getAppi().create(CuentaService.class);
        GenericRequest genericRequest = new GenericRequest();
        genericRequest.setCategoriaMovimientoId(categoriaMovimiento.getCategoriaMovimientoId());

        Call<List<MovimientoTipoRespons>> tipoMovimientoResponse = tipoMovimientoService.getlistar_movimientos_por_categoria_gasto(genericRequest);

        tipoMovimientoResponse.enqueue(new Callback<List<MovimientoTipoRespons>>() {
            @Override
            public void onResponse(Call<List<MovimientoTipoRespons>> call, Response<List<MovimientoTipoRespons>> response) {
                if (response.isSuccessful()) {
                    List<MovimientoTipoRespons> l = response.body();
                    //Converit a un Array
                    MovimientoTipoRespons[] __movimientoTipoRespons = new MovimientoTipoRespons[l.size()];
                    __movimientoTipoRespons = l.toArray(__movimientoTipoRespons);
                    //Cargar Datos en el Spinner
                    MySpinnerAdapter adapter = new MySpinnerAdapter(RegistrarMovimiento.this, R.layout.spinner_item_tipo_gasto, __movimientoTipoRespons);
                   // spinnerTipoMovimiento.setPrompt("Selecciona una categoría");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarMovimiento.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarMovimiento.this);
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


    private void MostrarSaldo() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrarMovimiento.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);
        Call<Cuenta> cuentaCall = cuentaService.getCuenta(cuenta);

        cuentaCall.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                alertDialog1.dismiss();
                if (response.isSuccessful()) {
                    Cuenta c = response.body();
                    if (c.getTipoCuentaId() == 3) {
                        _descripcionSaldo.setText("Deuda pendiente de pago");
                        _saldoCuenta.setText(String.format("%.2f", c.getCuentaDeuda()));

                    } else {
                        _descripcionSaldo.setText("Saldo disponible");
                        _saldoCuenta.setText(String.format("%.2f", c.getCuentaSaldo()));
                    }

                    _nombreCuenta.setText(c.getCuentaNombre());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarMovimiento.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarMovimiento.this);
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


    private void RegistrarOperacion() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrarMovimiento.this);
        builder1.setMessage("Loading...");
        builder1.setCancelable(false);
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

        CuentaService cuentaService = API.getAppi().create(CuentaService.class);

        OperacionRequest operacion = new OperacionRequest();
        operacion.setCuentaId(cuenta.getCuentaId());
        operacion.setMovimientoId(movimientoTipo.getMovimientoId());

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

                    Toast.makeText(RegistrarMovimiento.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegistrarMovimiento.this, CuentaDetalleActivity.class);
                            intent.putExtra("Cuenta", cuenta);
                            startActivity(intent);
                        }
                    },1000);


                } else {
                    Toast.makeText(RegistrarMovimiento.this, genericRespons.getMessages(), Toast.LENGTH_SHORT).show();
                    _btnRegistrarOperacion.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<GenericRespons> call, Throwable t) {
                alertDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarMovimiento.this);
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

    private boolean ValidarCampos(String fecha,  String monto, String descripcion) {

        boolean rpta = true;
        _txtFecha.setError(null);
//        _txtHora.setError(null);
        _txtMonto.setError(null);
        _txtDescripcion.setError(null);


        if (fecha.isEmpty()) {
            _txtFecha.setError("Ingrese una fecha");
            _txtFecha.requestFocus();
            rpta = false;
        }
//        if (hora.isEmpty()) {
//            _txtHora.setError("Ingrese la hora");
//            _txtHora.requestFocus();
//            rpta = false;
//        }
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

}

