package com.franzruiz.fipere1.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.franzruiz.fipere1.API.API;
import com.franzruiz.fipere1.API.APIServices.CuentaService;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.Request.LoginRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Usuario;
import com.franzruiz.fipere1.R;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SpotsDialog alertDialogLoading;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VerificarInternet();

        preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);

    }

    private void VerificarInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValidarCredenciales();
                }
            },2000);
        } else {

            // Si no hay conexión a Internet en este momento
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setMessage("Ocurrio un problema al conectarse con Fipers, por favor intenta nuevamente.");
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
    }


    public void onBackPressed (){
        finish();
        System.exit(0);
    }

    private void ValidarCredenciales() {

        String email = preferences.getString("UsuarioCorreo", "");
        String password = preferences.getString("UsuarioPassword", "");

        View view = null;

        if (!email.isEmpty() && !password.isEmpty()) {
            Logueo(email, password);
        }
        else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void Logueo(String email, String pass) {

            alertDialogLoading = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Iniciando...")
                .setCancelable(false)
                .build();
            alertDialogLoading.show();
            CuentaService cuentaService = API.getAppi().create(CuentaService.class);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsuarioCorreo(email);
            loginRequest.setUsuarioPassword(pass);
            Call<Usuario> LoginCall = cuentaService.getLogin(loginRequest);

            LoginCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Usuario usuario = response.body();
                    if (usuario.isSuccess()) {
                        alertDialogLoading.dismiss();
                        goToCuentas();

                    } else {
                        alertDialogLoading.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setMessage("Intente loguearse nuevamente");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                preferences.edit().clear().apply();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    alertDialogLoading.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
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

    private void goToCuentas() {
        Intent intent = new Intent(SplashActivity.this, CuentasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}