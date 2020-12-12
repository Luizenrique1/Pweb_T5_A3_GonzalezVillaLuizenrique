package com.example.bd_android_http;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityBajas extends Activity {

    private static boolean mensajeResultados;
    RecyclerView recycler;

    ArrayList<String> datos = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);
        recycler = findViewById(R.id.recycler_view_bajas);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.1.117:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_consultas_alumnos.php";
                String metodo = "POST";

                AnalizadorJSON analizadorJSON = new AnalizadorJSON();
                JSONObject jsonObject = analizadorJSON.consultaHTTP(url);


                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("alumnos");


                    String cadena = "";
                    for (int i = 0; i < jsonArray.length(); i++) {

                        cadena = jsonArray.getJSONObject(i).getString("nc") + "," +
                                jsonArray.getJSONObject(i).getString("n") + "," +
                                jsonArray.getJSONObject(i).getString("pa") + "," +
                                jsonArray.getJSONObject(i).getString("sa") + "," +
                                jsonArray.getJSONObject(i).getString("e") + "," +
                                jsonArray.getJSONObject(i).getString("s") + "," +
                                jsonArray.getJSONObject(i).getString("c");

                        datos.add(cadena);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdaptadorAlumno adapter = new AdaptadorAlumno(datos);
                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                int posicion = recycler.getChildAdapterPosition(view);

                                String cad = datos.get(posicion);
                                String alumno[] = cad.split(",");

                                    new EliminarAlumno().execute(alumno);

                            }
                        });
                        recycler.setAdapter(adapter);
                    }
                });
            }//run
        }).start();
    }

    class EliminarAlumno extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... datos) {

            String url = "http://192.168.1.117:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_bajas_alumnos.php";
            String metodo = "POST";

            Map<String, String> mapDatos = new HashMap<String, String>();
            mapDatos.put("nc", datos[0]);


            AnalizadorJSON aj = new AnalizadorJSON();
            JSONObject resultado = aj.peticionHTTP(url, metodo, mapDatos);

            boolean exito = false;
            try {
                exito = resultado.getBoolean("exito");
                ActivityBajas.mensajeResultados = exito;
                Intent i=new Intent(getBaseContext(),ActivityBajas.class);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return String.valueOf(exito);
        }
    }


}//class ActivityBajas
