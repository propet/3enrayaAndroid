//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Primera pantalla de la aplicacion.
    //
    // Se nos muestran dos opciones:
    //
    // -> empezar a jugar directamente como un usuario anonimo
    //    sin derecho a puntuacion.
    //
    //  ó
    //
    // -> loguearnos o registrarnos para poder jugar como jugadores con derecho a puntuacion.
    //----------------------------------------------------------------------------------------------

    public static boolean jugador_anonimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        // Borra los mensajes de la partida anterior que se han guardado en la base de datos, si
        // es que ha habido partida anterior
        if(Login.nombre != null && Login.nombre_rival != null) {
            new BorraMensajesBaseDeDatos().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);
        }

    }

    public void onJugar(View v){
        jugador_anonimo = true;
        Login.password = "";
        new CrearUsuarioAnonimo(this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR);

        // BLoquea el boton de jugar, porque ya estoy buscando partida
        Button boton_jugar = (Button) findViewById(R.id.button3);
        boton_jugar.setEnabled(false);
        boton_jugar.setClickable(false);
    }


    public void onCompetir(View v){
        // Caso de que le haya dado al boton de jugar, y luego al boton de competir. Dejo de buscar
        // partida y actualiza el estado de conectado del usuario creado a 0
        if(Login.nombre != null) {
            // Dejo de ser seleccionable para una partida
            String estatus_conectado = "0";
            new ActualizaEstado(this).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, estatus_conectado);
        }
        // Dejo de buscar rival si decido abandonar esta activity.
        if(BuscandoRival.conexion != null){
            BuscandoRival.conexion.cancel(true);
        }

        // Ahora que tengo rival, inicio la pantalla de juego
        Intent intento = new Intent(this, Compite.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intento);
    }

    @Override
    protected void onPause(){
        super.onPause();
        // Reestablece el boton de jugar como pulsable
        Button boton_jugar = (Button) findViewById(R.id.button3);
        boton_jugar.setEnabled(true);
        boton_jugar.setClickable(true);

        // Borra los mensajes de la partida que se han guardado en la base de datos
        if(Login.nombre != null && Login.nombre_rival != null) {
            new BorraMensajesBaseDeDatos().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);
        }
        if(Login.nombre != null) {
            // Dejo de ser seleccionable para una partida
            String estatus_conectado = "0";
            new ActualizaEstado(this).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, estatus_conectado);
        }
        // Dejo de buscar rival si decido abandonar esta activity.
        if(BuscandoRival.conexion != null){
            BuscandoRival.conexion.cancel(true);
        }
    }
}
