//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Context;
import android.content.Intent;
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

public class RegistraUsuario extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Insercion en la base de datos del usuario que hemos especificado con un nombre y una
    // contraseña dentro de la activity Registro.
    //----------------------------------------------------------------------------------------------


    public static boolean registro_finalizado = false;
    BufferedReader in;
    HttpURLConnection con;
    OutputStream out = null;
    String result;
    Context context;
    String nombre;
    String password;



    RegistraUsuario(Context context, String nombre, String password) {
        this.context = context.getApplicationContext();
        this.nombre = nombre;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... datos) {

        result = "Failure";

        Log.d("Registar Usuario", "Try to send messages to server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.eventosforthepeople.dynu.com/RegistrarUsuario.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            // Salida de datos
            String paresKeyValue = "nombre=" + URLEncoder.encode(nombre,"UTF-8")
                    +"&pass=" + URLEncoder.encode(password,"UTF-8");
            Log.d("Registar Usuario", "POST request:" + paresKeyValue);
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();


            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));


            try {
                result = in.readLine(); // La insercion ha sido exitosa!
                Log.d("Registrar Usuario", "tu nombre es: " + result);

            } catch (IOException ex) {
                Log.e("Conectar", ex.toString());
                ex.printStackTrace();
                return result;
            }

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

        con.disconnect();
        return result;
    }
    @Override
    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        registro_finalizado = true;

        // Ahora que estoy registrado como usario, paso a la pantalla del login
        Intent intento = new Intent(context, Login.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intento);

    }
}
