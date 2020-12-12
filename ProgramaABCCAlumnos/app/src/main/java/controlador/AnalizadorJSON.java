package controlador;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AnalizadorJSON {

    InputStream is = null;
    OutputStream os = null;
    JSONObject jsonObject = null;
    String json = null;
    HttpURLConnection conexion = null;
    URL mUrl = null;


    //-------------------------------------------metodo para Altas, Bajas y Cambios---------------------------
    public JSONObject peticionHTTP(String url, String metodo, Map datos){

        // {"nc":"1", "n":"Luke", "pa":"Skywalker", "sa":"-", "e":50, "s":5, "c":"ISC"}

        //--------------------------------ENVIO DE INFORMACION-------------------
        try {

            String cadenaJSON = "{\"nc\":\"" + URLEncoder.encode(String.valueOf(datos.get("nc")), "UTF-8") +
                    "\", \"n\":\"" + URLEncoder.encode(String.valueOf(datos.get("n")), "UTF-8") +
                    "\", \"pa\":\"" + URLEncoder.encode(String.valueOf(datos.get("pa")), "UTF-8") +
                    "\", \"sa\":\"" + URLEncoder.encode(String.valueOf(datos.get("sa")), "UTF-8") +
                    "\", \"e\":\"" + URLEncoder.encode(String.valueOf(datos.get("e")), "UTF-8") +
                    "\", \"s\":\"" + URLEncoder.encode(String.valueOf(datos.get("s")), "UTF-8") +
                    "\", \"c\":\"" + URLEncoder.encode(String.valueOf(datos.get("c")), "UTF-8") + "\"}";

            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();

            //activar el envio de datos a traves de HTTP
            conexion.setDoOutput(true);
            //indicar el metodo de envio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena enviada
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de envio de informacion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes()); //datos del alumno para el alta

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //--------------------------------RECIBIR RESPUESTA-----------------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena = new StringBuilder();

            String fila = null;
            while( (fila = br.readLine()  ) != null ){
                cadena.append(fila+"\n");
            }

            Log.i("MSJ->", cadena.toString());


            is.close();
            json = cadena.toString().substring(4);

            jsonObject = new JSONObject(json);


            jsonObject = new JSONObject(json);
            //jsonObject = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }//metodo peticionHTTP


    // ---------------- Peticion HTTP CONSULTAS -------------------------------------------------------------------

    public JSONObject consultaHTTP(String url){

        //peticion
        try {
            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();

            //activar el envio de datos a traves de HTTP
            conexion.setDoOutput(true);
            //indicar el metodo de envio
            conexion.setRequestMethod("POST");

            //Establecer el formato de envio de informacion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());


            //os.write(Integer.parseInt(""));


            os.flush();
            os.close();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Resultado o respuesta
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena = new StringBuilder();

            String fila = null;
            while( (fila = br.readLine()  ) != null ){
                cadena.append(fila+"\n");
            }

            Log.i("MSJ->", cadena.toString().substring(4));

            is.close();
            json = cadena.toString().substring(4);

            jsonObject = new JSONObject(json);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;


    }//metodo consultaHTTP


    // ---------------- Peticion HTTP CONSULTAS -------------------------------------------------------------------

    public JSONObject consultaHTTPParametro(String url, String cadenaJSON){

        //peticion
        try {
            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();

            //activar el envio de datos a traves de HTTP
            conexion.setDoOutput(true);
            //indicar el metodo de envio
            conexion.setRequestMethod("POST");

            //tamaño preestablecido o fijo para la cadena enviada
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de envio de informacion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes()); //datos del alumno para el alta

            os.flush();
            os.close();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Resultado o respuesta
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena = new StringBuilder();

            String fila = null;
            while( (fila = br.readLine()  ) != null ){
                cadena.append(fila+"\n");
            }

            Log.i("MSJ->", cadena.toString().substring(4));

            is.close();
            json = cadena.toString().substring(4);

            jsonObject = new JSONObject(json);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;


    }//metodo consultaHTTP


    //-------------------------------------------metodo para de Usuarios---------------------------
    public JSONObject peticionHTTPUsuarios(String url, String metodo, Map datos){


        //--------------------------------ENVIO DE INFORMACION-------------------
        try {

            String cadenaJSON = "{\"u\":\"" + URLEncoder.encode(String.valueOf(datos.get("u")), "UTF-8") +
                    "\", \"p\":\"" + URLEncoder.encode(String.valueOf(datos.get("p")), "UTF-8") + "\"}";

            mUrl = new URL(url);
            conexion = (HttpURLConnection) mUrl.openConnection();

            //activar el envio de datos a traves de HTTP
            conexion.setDoOutput(true);
            //indicar el metodo de envio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena enviada
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de envio de informacion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes()); //datos del alumno para el alta

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //--------------------------------RECIBIR RESPUESTA-----------------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena = new StringBuilder();

            String fila = null;
            while( (fila = br.readLine()  ) != null ){
                cadena.append(fila+"\n");
            }

            Log.i("MSJ->", cadena.toString());


            is.close();
            json = cadena.toString();


            jsonObject = new JSONObject(json);
            //jsonObject = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }//metodo peticionHTTP



}//clase
