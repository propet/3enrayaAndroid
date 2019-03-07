//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class ActualizaEstado extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Coge el nombre de mi usuario y actualiza el estado de conectado a 1 o 0 segun este
    // dispuesto para encontrar partida o no.
    // Si he declarado que quiero estar buscando partida, con el valor de conectado = 1, despues
    // de realizar la consulta para actualizar la base de datos, inicio la tarea asincrona
    // ConexionHttp para pasar a buscar un rival.
    //
    // Devuelve ERROR si el nombre de usuario o contraseña no coinciden con ningun registro de
    // usuario en la base de datos
    //----------------------------------------------------------------------------------------------

    BufferedReader in = null;
    HttpURLConnection con;
    OutputStream out = null;

    String result;
    String estatus_conectado; // 0 o 1, para determinar si voy a conectar o desconectar de la base de datos
    public static String nombre;

    Context context;
    public static Toast toast;


    ActualizaEstado(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... datos) {

        nombre = datos[0];
        estatus_conectado = datos[1];

        result = "Failure";

        Log.d("Conectar", "Try to get messages from server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidServidorActualizaEstado.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            // Salida de datos
            String paresKeyValue = "nombre=" + URLEncoder.encode(nombre,"UTF-8")
                    +"&conectado=" + URLEncoder.encode(estatus_conectado,"UTF-8")
                    +"&pass=" + URLEncoder.encode(Login.password,"UTF-8");
            Log.d("Actualiza Estado", "POST request:" + paresKeyValue);
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();

            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            result = in.readLine(); // "ERROR", o "Ya no eres selecionable para una partida", o
                                    // "Pasas a estar en la lista de seleccionables para partida"

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

        Log.d("Actualiza Estado", "Connection completed, waiting response");
        Log.d("Actualiza Estado", "Received: " + result);

        con.disconnect();
        return result;
    }
    @Override
    protected void onProgressUpdate(String... progress){

    }

    @Override
    protected void onPostExecute(String result) {
        // Si la contraseña o el nombre del usuario no son correctos, no paso a buscar rival.
        // Muestro por pantalla un toast notificando el error.
        if(result.equals("ERROR")){
            // Toast: No existe un usuario con ese nombre y/o contraseña
            CharSequence text = "No existe un usuario con ese nombre y/o contraseña";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            // Si quiero conectar, ahora paso a buscar un oponente.
            if (estatus_conectado.equals("1")) {
                // Ejecuta la conexion como una tarea asincrona que puede funcionar en paralelo
                // con otras tareas asincronas
                BuscandoRival.conexion =  new ConexionHttp(context);
                BuscandoRival.conexion.executeOnExecutor(
                                AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre);

                // Toast: Buscando rival...
                CharSequence text = "Buscando rival...";
                int duration = Toast.LENGTH_LONG;
                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

}

