//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class CrearUsuarioAnonimo extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Si no deseo tener que registrarme para empezar a jugar al tres en raya, aqui se registra
    // un jugador anonimo en la base de datos que nos sirve de identidad temporal. El nombre del
    // jugador creado sera del tipo GUEST_#. Siendo # un numero autoincrementado que indica tu
    // id de usuario.
    //----------------------------------------------------------------------------------------------


    BufferedReader in;
    HttpURLConnection con;
    String result;
    Context context;

    CrearUsuarioAnonimo(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... datos) {


        result = "Failure";

        Log.d("CrearUsuarioAnonimo", "Try to send messages to server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/CrearUsuarioAnonimo.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);


            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));


            try {
                result = in.readLine(); // Lectura de mi nombre de usuario generado automaticamente
                Login.nombre = result;
                Log.d("Nuevo Usuario Anonimo", "tu nombre es: " + result);

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
        // Para mostrar mi estatus de conectado en la base de datos y pueda
        // ser seleccionable como rival
        String estatus_conectado = "1";
        // Desde la clase ActualizaEstado lanzo la clase ConexionHttp si estatus_conectado es
        // igual a "1". Desde ConexionHttp se busca un rival para poder jugar, una vez encontrado
        // uno se pasa a la pantalla de juego
        new ActualizaEstado(context).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, estatus_conectado);
    }
}