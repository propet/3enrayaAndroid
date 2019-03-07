//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class ActualizaMiELO extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Actualiza el valor de mi puntuacion en la base de datos.
    // En funcion de si he ganado o perdido, se me suman o restan 10 puntos, si no tengo mas de 10
    // puntos y he perdido, me quedo con 0 puntos.
    // Tambien devuelve mi puntuacion actualizada para poder mostrarla por pantalla.
    // Esta ultima accion de mostrar por pantalla la puntuacion se realiza en el
    // metodo onPostExecute().
    //----------------------------------------------------------------------------------------------

    BufferedReader in;
    HttpURLConnection con;
    String result;
    TextView texto_puntuacion;

    ActualizaMiELO(TextView texto_puntuacion) {
        this.texto_puntuacion = texto_puntuacion;
    }

    @Override
    protected String doInBackground(String... datos) {

        String nombre = datos[0];
        String resultado = datos[1];

        result = "Failure";

        Log.d("Actualiza Mi ELO", "Try to send messages to server");

        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/ActualizaMiELO.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            String paresKeyValue = "nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&resultado=" + URLEncoder.encode(resultado, "UTF-8");

            Log.d("ActualizaMiELO", "POST request:" + paresKeyValue);
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();
            out.close();

            // Entrada y lectura de datos
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));



            try {
                result = in.readLine(); // Lectura del valor de mi puntuacion como una string
                juego.ELO = Integer.parseInt(result);
                Log.d("Actualiza Mi ELO", "tu puntuacion ahora es: " + result);

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
        Log.d("Actualiza Mi ELO", "Completada actualizacion de ELO");
        texto_puntuacion.setText(result);
    }
}