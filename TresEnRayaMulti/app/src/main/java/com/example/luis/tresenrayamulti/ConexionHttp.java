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

public class ConexionHttp extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Busca un oponente para jugar.
    // Se realiza una consulta a la base de datos a la que le paso mi nombre de usuario para
    // que me encuentre un jugador de nombre distinto al mio y que este buscando partida en
    // este momento.
    // La consulta me devuelve por tanto un valor para la variable nombre_rival.
    // Despues de la consulta actualizo mi estado de buscando partida a 0 a traves de la tarea
    // asincrona ActualizaEstado para dejar de ser seleccionable como rival.
    // Finalmente inicio la actividad juego para iniciar la escucha de mensajes en el onCreate.
    //----------------------------------------------------------------------------------------------

    public static int contador_conexiones = 0; // Para determinar si es la primera vez que conecto

    BufferedReader in = null;
    HttpURLConnection con;
    OutputStream out = null;

    String result = null;
    public static String nombre;
    Context context;

    ConexionHttp(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... datos) {

        nombre = datos[0];

        Log.d("Conectar", "Try to get messages from server");

        // Consultas en bucle para buscar un rival. Hasta que la base de datos me de un nombre
        // valido, que no sea ni null ni "Ups".
        while (result == null || result.equals("Ups")){

            // Si cancelo la tarea de buscar un rival, salgo del bucle while
            if(isCancelled()){
                break;
            }
            try {
                URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidServidorConexion.php");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST"); //utilizacion del metodo POST
                con.setDoInput(true);
                con.setDoOutput(true);

                // Pares key-value de la consulta a recibir por el php a traves del metodo POST
                String paresKeyValue = "nombre=" + URLEncoder.encode(nombre, "UTF-8");
                Log.d("Conectar", "POST request:" + paresKeyValue);
                out = new BufferedOutputStream(con.getOutputStream());
                out.write(paresKeyValue.getBytes());
                out.flush();

                // Entrada y lectura de datos
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

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

            Log.d("Conectar", "Connection completed, waiting response");
            try {
                result = in.readLine(); //mensaje "Conectado con: " o "Ups" si no hay rival
                Login.nombre_rival = in.readLine(); //nombre del usuario con el que conecto
                Log.d("Conectar", "Conectado con: " + Login.nombre_rival);

            } catch (IOException ex) {
                Log.e("Conectar", ex.toString());
                ex.printStackTrace();
                return result;
            }
            con.disconnect();

        } // end while

        // Le doy un tiempo al otro usuario para que me reconozca como dispuesto a jugar
        // antes de que entremos a la partida y actualice mi estatus de conectado como 0
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
            cancel(true);
        }

        return result;
    }
    @Override
    protected void onProgressUpdate(String... progress){

    }

    @Override
    protected void onPostExecute(String result) {
        // Si ya tengo rival, dejo de ser seleccionable para una partida
        String estatus_conectado = "0";
        new ActualizaEstado(context).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,nombre,estatus_conectado);

        // Suma al contador de conexiones
        contador_conexiones++;


        // Ahora que tengo rival, inicio la pantalla de juego
        Intent intento = new Intent(context, juego.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intento);
        }
    }





