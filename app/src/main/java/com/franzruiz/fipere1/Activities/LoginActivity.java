package com.franzruiz.fipere1.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Model.Request.LoginRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    TextView password, user;
    Button btn_Login;
    String _usuario;
    String _password;
    Switch _switchRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Inicializar();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _usuario = user.getText().toString();
                _password = password.getText().toString();
                VerificarInternet(view);
            }
        });
        //Automaticamente loguearse - Desarrollo
        //btn_Login.performClick();
    }

    private void VerificarInternet(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
                    Logueo(_usuario, _password, view);
        } else {

            // Si no hay conexión a Internet en este momento
            Toast.makeText(LoginActivity.this, "Ocurrio un problema al conectarse con Fipers, no cuenta con conexión a internet." , Toast.LENGTH_SHORT).show();
        }
    }



    private void Inicializar() {
        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        user = (TextView) findViewById(R.id.txtUser);
        //Automaticamente loguearse - Desarrollo
       // user.setText("franz.ruizsolis@gmail.com");
        password = (TextView) findViewById(R.id.txtPassword);
        //Automaticamente loguearse - Desarrollo
       // password.setText("theclash1982");
        btn_Login = (Button) findViewById(R.id.btnLogin);
        _switchRecordar = (Switch) findViewById(R.id.swtRecordarme);
    }


    private void goToCuentas() {
        Intent intent = new Intent(LoginActivity.this, CuentasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean ValidarCampos(String email, String pass) {

        boolean rpta = true;
        user.setError(null);
        password.setError(null);

        if (email.isEmpty()) {
            user.setError("Ingrese un correo válido");
            user.requestFocus();
            rpta = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user.setError("Ingrese un correo válidosss");
            user.requestFocus();
            rpta = false;
        }
        if (pass.isEmpty()) {
            password.setError("Ingrese una constraseña válida");
            password.requestFocus();
            rpta = false;
        }
        return rpta;
    }



    private void savePreference(Usuario usuario) {
        if (_switchRecordar.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UsuarioId", String.valueOf(usuario.getUsuarioId()) );
            editor.putString("UsuarioNombre", usuario.getUsuarioNombre());
            editor.putString("UsuarioPassword", usuario.getUsuarioPassword());
            editor.putString("UsuarioImagen", usuario.getUsuarioImagen());
            editor.putString("UsuarioCorreo", usuario.getUsuarioCorreo());
            editor.putString("UsuarioPadreCuenta", String.valueOf(usuario.getUsuarioPadreCuenta() ));
            editor.putString("UsuarioAdminitradorCuenta", String.valueOf(usuario.isUsuarioAdminitradorCuenta() ));
            editor.putString("UsuarioAdminitradorSistema", String.valueOf(usuario.isUsuarioAdminitradorSistema() ));
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UsuarioId", String.valueOf(usuario.getUsuarioId()) );
            editor.putString("UsuarioNombre", usuario.getUsuarioNombre());
            editor.putString("UsuarioImagen", usuario.getUsuarioImagen());
            editor.putString("UsuarioCorreo", usuario.getUsuarioCorreo());
            editor.putString("UsuarioPadreCuenta", String.valueOf(usuario.getUsuarioPadreCuenta() ));
            editor.putString("UsuarioAdminitradorCuenta", String.valueOf(usuario.isUsuarioAdminitradorCuenta() ));
            editor.putString("UsuarioAdminitradorSistema", String.valueOf(usuario.isUsuarioAdminitradorSistema() ));
            editor.apply();

        }
    }

    private void Logueo(String email, String pass, View view) {

        if (ValidarCampos(email, pass)) {

            btn_Login.setEnabled(false);

            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
            builder1.setMessage("Loading...");
            builder1.setCancelable(false);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();

            CuentaService cuentaService = API.getAppi().create(CuentaService.class);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsuarioCorreo(email);
            loginRequest.setUsuarioPassword(pass);
            Call<Usuario> LoginCall = cuentaService.getLogin(loginRequest);

            LoginCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Usuario usuario = response.body();
                    usuario.setUsuarioPassword(pass);
                    alertDialog1.dismiss();
                    if (usuario.isSuccess()) {
                        goToCuentas();
                        savePreference(usuario);
                    } else {
                        Toast.makeText(view.getContext(), "El correo eléctronico o la contraseña que has ingresado es incorrecta, vuelve a intentarlo", Toast.LENGTH_LONG).show();
                        btn_Login.setEnabled(true);
                    }
                }

                @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                    alertDialog1.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Tenemos algunos inconvenientes técnicos, intente mas tarde");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Cerrar Aplicación", new DialogInterface.OnClickListener() {
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
        else{
            btn_Login.setEnabled(true);
        }
    }
}