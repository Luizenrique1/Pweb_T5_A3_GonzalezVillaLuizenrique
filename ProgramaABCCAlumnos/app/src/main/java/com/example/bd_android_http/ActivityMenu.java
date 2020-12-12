package com.example.bd_android_http;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void abrirActivitiesTablas(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btn_altas:  //VALIDAR DATOS
                i = new Intent(this, ActivityAltas.class);
                startActivity(i);
                break;
            case R.id.btn_modificar:
                i = new Intent(this, ActivityCambios.class);
                startActivity(i);
                break;
            case R.id.btn_eliminar:
                i = new Intent(this, ActivityBajas.class);
                startActivity(i);
                break;
            case R.id.btn_consultas:
                i = new Intent(this, ActivityConsultas.class);
                startActivity(i);
                break;
            case R.id.btn_cerrar:
                i=new Intent(this,MainActivity.class);
                finish();
                startActivity(i);
        }
    }
}