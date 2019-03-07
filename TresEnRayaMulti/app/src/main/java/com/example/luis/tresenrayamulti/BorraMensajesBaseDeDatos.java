//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;


import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class BorraMensajesBaseDeDatos extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Elimina los mensajes de la partida de la base de datos
    //----------------------------------------------------------------------------------------------

    BufferedReader in = null;
    HttpURLConnection con;
    OutputStream out = null;

    String result;
    String nombre;
    String nombre_rival;

    BorraMensajesBaseDeDatos() {

    }

    @Override
    protected String doInBackground(String... datos) {

        nombre = datos[0];
        nombre_rival = datos[1];

        result = "Failure";

        Log.d("Conectar", "Try to get messages from server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidBorraMensajesBaseDeDatos.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            // Salida de datos
            String paresKeyValue = "nombre=" + URLEncoder.encode(nombre,"UTF-8")
                    +"&nombre_rival=" + URLEncoder.encode(nombre_rival,"UTF-8");
            Log.d("Borra los registros", "POST request:" + paresKeyValue);
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();

            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            result = in.readLine();

        } catch (UnknownHostException ex) {
            Log.e("Conectar",
                    "Don't know about host: " + ":" + ex.toString());
            ex.printStackTrace();
            return result;
        } catch (IOException ex) {
            Log.e("Conectar", "Could not obtain streams: " + ":"
                    + ex.toString());
            ex.printStackTrace();
            return result;
        }

        Log.d("Borra mensajes", "Connection completed, waiting response");
        Log.d("Borra mensajes", "Received: " + result);

        con.disconnect();
        return result;
    }
    @Override
    protected void onProgressUpdate(String... progress){

    }

    @Override
    protected void onPostExecute(String result) {
    }

}
