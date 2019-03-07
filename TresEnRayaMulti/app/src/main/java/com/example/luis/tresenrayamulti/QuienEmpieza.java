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

public class QuienEmpieza extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Consulta al sevidor para ver si empiezo yo moviendo o no.
    // Si empiezo yo, me asigna como marca los circulos y como marca de mi rival las X's.
    // Si no empiezo, las marcas se asignan al reves.
    // En el onPostExecute() actualizo el estado de los botones que forman el tres en raya para
    // hacerlos pulsables si es mi turno, y se muestra un mensaje por pantalla que dice que es mi
    // turno. De otra forma forma, los botones continuan siendo no pulsables y se muestra un
    // mensaje diciendo que el turno lo tiene mi rival.
    //----------------------------------------------------------------------------------------------

    BufferedReader in = null;
    HttpURLConnection con;
    OutputStream out = null;

    String result;
    String nombre;
    String nombre_rival;
    Context context;
    juego actividad_juego;

    QuienEmpieza(Context context, juego actividad_juego) {
        this.context = context;
        this.actividad_juego = actividad_juego;
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
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidQuienEmpieza.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            // Salida de datos
            String paresKeyValue = "nombre=" + URLEncoder.encode(nombre,"UTF-8")
                    +"&nombre_rival=" + URLEncoder.encode(nombre_rival,"UTF-8");
            Log.d("Quien Empieza?", "POST request:" + paresKeyValue);
            out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();

            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            result = in.readLine(); // devuelve true o false
            juego.turno = Boolean.parseBoolean(result); // tu turno, que sera true o false

            // Empiezan los circulos
            if(juego.turno){
                juego.marca = "O";
            }else{
                juego.marca = "X";
            }

            // Que marca le corresponde a mi rival?
            if(juego.marca.equals("O")){
                juego.marca_rival = "X";
            }else{
                juego.marca_rival = "O";
            }

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
        // Toast: Tu rival/Tu empiezas moviendo.
        CharSequence text;
        if(juego.turno) {
            text = "Empiezas poniendo";
            actividad_juego.actualizaBotones(); // Si empiezo jugando desbloqueo los botones
        }else{
            text = "Tu rival empieza poniendo";
        }
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
