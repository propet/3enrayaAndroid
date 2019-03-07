//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class MensajesHttp extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Envio de mis mensajes de chat, y comandos para comunicar las acciones en el juego.
    // Inserto los mensajes/comandos en la tabla mensaje de mi base de datos con la informacion de
    // los dos usuarios que toman parte en la comunicacion.
    //----------------------------------------------------------------------------------------------

    EditText edit;
    BufferedReader in;
    HttpURLConnection con;
    String result;

    MensajesHttp(EditText edit) {
        this.edit = edit;
    }

    @Override
    protected String doInBackground(String... datos) {

        String mensaje = datos[0];

        result = "Failure";

        Log.d("Enviar Mensajes", "Try to send messages from server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidServidorMensaje.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            String paresKeyValue = "mensaje=" + URLEncoder.encode(mensaje, "UTF-8")
                    + "&nombre_autor=" + URLEncoder.encode(Login.nombre, "UTF-8")
                    + "&nombre_escuchante=" + URLEncoder.encode(Login.nombre_rival, "UTF-8");

            Log.d("Enviar Mensajes", "POST request:" + paresKeyValue);
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();
            out.close();

            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        } catch (UnknownHostException ex) {
            Log.e("ObtenerTexto",
                    "Don't know about host: " + ":" + ex.toString());
            ex.printStackTrace();
            cancel(true);
            return result;
        } catch (IOException ex) {
            Log.e("ObtenerTexto", "Could not obtain streams: " + ":"
                    + ex.toString());
            ex.printStackTrace();
            cancel(true);
            return result;
        }

        Log.d("ObtenerTexto", "Connection completed, waiting response");
        try {
            result = in.readLine(); //ultimo mensaje de la conversacion
            Log.d("ObtenerTexto", "Received: " + result);
        } catch (IOException ex) {
            Log.e("ObtenerTexto", ex.toString());
            ex.printStackTrace();
            return result;
        }

        con.disconnect();
        return result;
    }
    @Override
    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        // Vacia el contenido del editText donde he introducido el mensaje
        edit.setText("");
    }
}

