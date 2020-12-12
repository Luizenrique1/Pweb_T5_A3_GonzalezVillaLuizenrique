package com.example.bd_android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import controlador.AnalizadorJSON;

public class ActivityAltas extends AppCompatActivity {

    EditText cajaNumControl, cajaNombre, cajaPrimerAp,cajaSegundoAp, cajaEdad;
    Spinner spinnerSemestre, spinnerCarrera;
    static boolean mensajeResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.caja_num_control_altas);
        cajaNombre = findViewById(R.id.caja_nombre_altas);
        cajaPrimerAp=findViewById(R.id.caja_primer_ap_altas);
        cajaSegundoAp=findViewById(R.id.caja_segundo_ap_altas);
        cajaEdad=findViewById(R.id.caja_edad_altas);
        spinnerSemestre=findViewById(R.id.spinner_semestre_altas);
        spinnerCarrera=findViewById(R.id.spinner_carrera_altas);

    }

    public void agregarRegistro(View v) {
        String nc = cajaNumControl.getText().toString();
        String n = cajaNombre.getText().toString();
        String pa = cajaPrimerAp.getText().toString();
        String sa = cajaSegundoAp.getText().toString();
        String e = cajaEdad.getText().toString();
        String s = spinnerSemestre.getSelectedItem().toString();
        String c = spinnerCarrera.getSelectedItem().toString();

        //Verificar que no vengan datos vacios
        if (nc.isEmpty() || n.isEmpty() || pa.isEmpty() || e.isEmpty() || s.isEmpty() || c.isEmpty()) {
            Toast.makeText(this, "CAMPOS VACIOS!!", Toast.LENGTH_LONG).show();
        } else {


            //Verificar que la comunicación con WIFI funcione
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://192.168.1.117:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_altas_alumnos.php";
                        String metodo = "POST";

                        Map<String, String> mapDatos = new HashMap<String, String>();
                        mapDatos.put("nc", nc);
                        mapDatos.put("n", n);
                        mapDatos.put("pa", pa);
                        mapDatos.put("sa", sa);
                        mapDatos.put("e", e);
                        mapDatos.put("s", s);
                        mapDatos.put("c", c);

                        AnalizadorJSON aj = new AnalizadorJSON();
                        JSONObject resultado = aj.peticionHTTP(url, metodo, mapDatos);

                        boolean exito = false;
                        try {
                            exito = resultado.getBoolean("exito");
                            ActivityAltas.mensajeResultados = exito;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), mensajeResultados ? "EXITO" : "No se pudo dar de alta", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }).start();

            } else {
                Log.e("MSJ-->", "Error en el WIFI");
            }


        }
    }
}

