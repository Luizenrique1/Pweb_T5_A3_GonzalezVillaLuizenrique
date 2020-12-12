package com.example.bd_android_http;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends Activity {

    RecyclerView recycler;
    Spinner filtro;
    EditText Txt_Busqueda;

    ArrayList<String> datos = new ArrayList<>();
    AdaptadorAlumno adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        recycler=findViewById(R.id.recycler_view_consultas);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Txt_Busqueda =findViewById(R.id.caja_parametro);

        Txt_Busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    consultarAlumno(Txt_Busqueda);
            }
        });
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
                    for (int i=0; i<jsonArray.length();i++){

                        cadena = jsonArray.getJSONObject(i).getString("nc") + " | " +
                                jsonArray.getJSONObject(i).getString("n") + "|" +
                                jsonArray.getJSONObject(i).getString("pa") + "|" +
                                jsonArray.getJSONObject(i).getString("sa") + "|" +
                                jsonArray.getJSONObject(i).getString("e") + "|" +
                                jsonArray.getJSONObject(i).getString("s") + "|" +
                                jsonArray.getJSONObject(i).getString("c");

                        datos.add(cadena);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter=new AdaptadorAlumno(datos);
                        recycler.setAdapter(adapter);
                    }
                });
            }//run
        }).start();
    }


    public void consultarAlumno(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cadenaJSON ="";
                String url="";


                recycler.removeAllViewsInLayout();
                datos.clear();

                    try {
                        cadenaJSON = "{\"pa\":\"" + URLEncoder.encode( Txt_Busqueda.getText().toString(), "UTF-8") +"\"}";
                        url= "http://192.168.0.6:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_consultas_alumnos_primerAp.php";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }




                String metodo = "POST";

                AnalizadorJSON analizadorJSON = new AnalizadorJSON();
                JSONObject jsonObject = analizadorJSON.consultaHTTPParametro(url,cadenaJSON);


                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("alumnos");


                    String cadena = "";
                    for (int i=0; i<jsonArray.length();i++){

                        cadena = jsonArray.getJSONObject(i).getString("nc") + " | " +
                                jsonArray.getJSONObject(i).getString("n") + "|" +
                                jsonArray.getJSONObject(i).getString("pa") + "|" +
                                jsonArray.getJSONObject(i).getString("sa") + "|" +
                                jsonArray.getJSONObject(i).getString("e") + "|" +
                                jsonArray.getJSONObject(i).getString("s") + "|" +
                                jsonArray.getJSONObject(i).getString("c");

                        datos.add(cadena);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AdaptadorAlumno adapter2=new AdaptadorAlumno(datos);
                        adapter.notifyDataSetChanged();
                        recycler.setAdapter(adapter2);
                    }
                });
            }//run
        }).start();
    }
}//class Consultas

