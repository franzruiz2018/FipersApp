package com.franzruiz.fipere1.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.franzruiz.fipere1.Activities.CuentasActivity;
import com.franzruiz.fipere1.Activities.EstadoCuentaActivity;
import com.franzruiz.fipere1.Activities.LoginActivity;
import com.franzruiz.fipere1.Activities.PlanificacionActivity;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.R;

public class OpcionesMenu extends AppCompatActivity {
    private SharedPreferences preferences;
    private Context context;
    private MenuItem menuItem;
    private Activity activity;


    public OpcionesMenu(Context context, MenuItem menuItem, Activity activity) {
        this.context = context;
        this.menuItem = menuItem;
        this.activity = activity;
        onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cuentas:
                Cuentas();
                return true;
            case R.id.menu_logout:
                Logout();
                return true;
            case R.id.menu_planificacion:
                Planificacion();
                return true;
            case R.id.estado_cuenta:
                EstadoCuenta();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void Cuentas() {
        Intent intent = new Intent(context, CuentasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void EstadoCuenta(){
        Intent intent = new Intent(context, EstadoCuentaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void Planificacion(){
        Intent intent = new Intent(context, PlanificacionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void Logout(){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        preferences.edit().clear().apply();
    }

}
