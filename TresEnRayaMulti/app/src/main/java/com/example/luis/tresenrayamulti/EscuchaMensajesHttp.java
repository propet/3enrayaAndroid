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

public class EscuchaMensajesHttp extends AsyncTask<String, String, String> {
    //----------------------------------------------------------------------------------------------
    // Escucha en bucle de la ultima fila de la tabla mensaje de la base de datos.
    // Si no tengo el ultimo mensaje que me devuelve la base de datos, lo añado a la lista
    // de mensajes si es un mensaje de texto, o se lo paso al metodo marcarBoton() de la clase
    // juego si es un comando del tres en raya.
    // Se distinguen los mensajes de chat de los comandos dentro del metodo onPublishProgress(). Si
    // el memsaje recibido tiene por prefijo "~~~" se sabe entonces que se trata de un comando.
    //----------------------------------------------------------------------------------------------

    String result;
    String nombre_autor;
    String id_mensaje1;
    String id_mensaje2;
    juego actividad_juego;

    EscuchaMensajesHttp(juego actividad_juego) {
        this.actividad_juego = actividad_juego;
    }

    @Override
    protected String doInBackground(String... datos) {

        result = "Failure";

        Log.d("ObtenerTexto", "Try to get messages from server");

    // Escucha de mensajes en bucle, no para hasta que ejecuto juego.escucha.cancel(true);
    // en el MainActivity.
    while(!isCancelled()) {
        // Datos de la conexion
        try {
            // Actualizacion de mi estatus como usuario conectado o a punto de desconectarse
            URL url = new URL("http://www.luisdaranda.dynu.com/archivosServidor/AndroidServidorEscucha.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); //utilizacion del metodo POST
            con.setDoInput(true);
            con.setDoOutput(true);

            // Salida de datos

            String paresKeyValue = "nombre_autor=" + URLEncoder.encode(Login.nombre, "UTF-8")
                      + "&nombre_escuchante=" + URLEncoder.encode(Login.nombre_rival, "UTF-8");

            Log.d("ObtenerTexto", "POST request:" + paresKeyValue);
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(paresKeyValue.getBytes());
            out.flush();
            out.close();
            Log.d("ObtenerTexto", "Connection completed, waiting response");

            // Entrada y lectura de datos
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            result = in.readLine(); // obtiene el mensaje
            nombre_autor = in.readLine(); // obtiene el nombre del autor del mensaje
            id_mensaje1 = in.readLine(); // id del comentario para comprobar que no es el mismo

            Log.d("EscuchaMensaje", result + nombre_autor + id_mensaje1);

            // No envio el resultado de mi consulta si el mensaje es nulo o esta repetido:
            // Compruebo si el mensaje que he recibido es nulo
            if (result != null && !(result.isEmpty())) {
                // Compruebo si el mensaje que he recibido ya lo tengo mostrado en la listview
                if(!id_mensaje1.equals(id_mensaje2)){
                    publishProgress(result,nombre_autor);//strings a onProgressUpdate para mostrar en UI
                    id_mensaje2 = id_mensaje1;
                }
            }
            Log.d("ObtenerTexto", "Received: " + result);
            con.disconnect();

        } catch (UnknownHostException ex) {
            Log.e("ObtenerTexto",
                    "Don't know about host: " + ":" + ex.toString());
            ex.printStackTrace();
            cancel(true);
        } catch (IOException ex) {
            Log.e("ObtenerTexto", "Could not obtain streams: " + ":"
                    + ex.toString());
            ex.printStackTrace();
            cancel(true);
        }

        // Sleep del thread, en milisegundos.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
            cancel(true);
        }

    }// end while
        return result;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
            String mensaje = progress[0]; // la variable result es el mensaje recibido
            nombre_autor = progress[1];

            // Me han mandado un mensaje o un comando?
            if(mensaje.startsWith("~~~")) {
            // Mando la informacion al metodo actualizaBotones de la clase juego
                actividad_juego.actualizaBotones();
                actividad_juego.marcaBoton(mensaje,nombre_autor);
            }else{ // Se trata de un mensaje para la conversacion

                // Introduzco la informacion en el contenedor del item para la listview.
                juego.mensajeArr.add(new Mensaje_entrada(nombre_autor, mensaje));
                // Aviso a la listview para que actualice su contenido
                juego.adapter.notifyDataSetChanged();
                // Scroll listView to last position
                juego.lista.setSelection(juego.adapter.getCount() - 1);

                Log.d("ObtenerTexto", "mandado el mensaje a la lista");
            }


    }

    @Override
    protected void onPostExecute(String result) {
    }

}