//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BuscandoRival extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla de busqueda de rivales, una vez que ya te has registrado o logeado en el juego.
    //----------------------------------------------------------------------------------------------

    public static ConexionHttp conexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscando_rival);
        // Borra los mensajes de la partida anterior que se han guardado en la base de datos
            new BorraMensajesBaseDeDatos().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);

            Login.nombre_rival = null;

        // Para mostrar mi estatus de conectado en la base de datos y pueda
        // ser seleccionable como rival
        String estatus_conectado = "1";
        // Desde la clase ActualizaEstado lanzo la clase ConexionHttp si estatus_conectado es
        // igual a "1". Desde ConexionHttp se busca un rival para poder jugar, una vez encontrado
        // uno se pasa a la pantalla de juego
        new ActualizaEstado(this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, estatus_conectado);

    }
    protected void onPause() {
        super.onPause();

        // Dejo de mostrar el mensaje que me dice que estoy buscando partida
        ActualizaEstado.toast.cancel();

        // Dejo de buscar rival si decido abandonar esta activity.
        if(conexion != null){
            conexion.cancel(true);
        }

        String estatus_conectado = "0";
        new ActualizaEstado(this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, estatus_conectado);
        // Dejo de escuchar los mensajes de la partida que he abandonado si he llegado a tener oponente
        if(Login.nombre_rival != null) {
            juego.escucha.cancel(true);
        }
        // Borro la lista de mensajes que tenia de la conversacion anterior
        juego.mensajeArr.clear();
        finish();
    }
}
