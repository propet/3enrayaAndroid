//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla para introducir tu nombre de usuario y contraseña.
    // Si existe el usuario de nombre y constraseña especificados, se pasa a buscar un
    // rival para competir nada mas presionar el boton conectar.(de esta comprobacion se
    // encarga la tarea asincrona ActualizaEstado)
    //----------------------------------------------------------------------------------------------


    public static String nombre =null;
    public static String nombre_rival = null;
    public static String password;
    public static String estatus_conectado = "0"; // Hasta que no presione uno de los botones de buscar
                                          // partida no soy seleccionable como rival


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Borra los mensajes de la partida anterior que se han guardado en la base de datos, si
        // es que ha habido una partida anteriormente.
        if(nombre_rival != null){
            new BorraMensajesBaseDeDatos().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);
        }

        // Si he sido direccionado a esta activity desde la pantalla de registro de jugadores, es
        // que he creado un nuevo usuario correctamente
        if(RegistraUsuario.registro_finalizado){
            // Toast: Registro completado
            Context context = getApplicationContext();
            CharSequence text = "Registro completado";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            EditText nomb = (EditText) findViewById(R.id.nombre);
            nomb.setText(Login.nombre);

            EditText contra = (EditText) findViewById(R.id.pass);
            contra.setText(Login.password);

            RegistraUsuario.registro_finalizado = false;
        }

    }

    public void onConectar(View v){
        // Estoy jugando como un jugador registrado. Por tanto podre ver mi puntuacion despues
        // de las partidas
        Main2Activity.jugador_anonimo = false;

        nombre_rival = null;
        // Si ya tenia una conexion realizada, antes de iniciar una nueva,
        // cancelo la escucha de mensajes de la anterior conexion
        if (ConexionHttp.contador_conexiones != 0){
            juego.escucha.cancel(true);
            // Borro la lista de mensajes que tenia de la conversacion anterior
            juego.mensajeArr.clear();
        }

        // Dejo de buscar rival si lo estaba haciendo antes.
        if(BuscandoRival.conexion != null){
            BuscandoRival.conexion.cancel(true);
        }

        // Coger la informacion de mi usuario
        EditText nomb = (EditText) findViewById(R.id.nombre);
        nombre = nomb.getText().toString();

        EditText pass = (EditText) findViewById(R.id.pass);
        Login.password = pass.getText().toString();

        // Para mostrar mi estatus de conectado en la base de datos y pueda
        // ser seleccionable como rival
        estatus_conectado = "1";
        // Desde la clase ActualizaEstado lanzo la clase ConexionHttp si estatus_conectado es
        // igual a "1". Desde ConexionHttp se busca un rival para poder jugar, una vez encontrado
        // uno se pasa a la pantalla de juego
        new ActualizaEstado(this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, nombre, estatus_conectado);
    }

    protected void onPause(){
        super.onPause();

        // Dejo de buscar rival si decido abandonar esta activity.
        if(BuscandoRival.conexion != null){
            BuscandoRival.conexion.cancel(true);
        }

        // Borra los mensajes de la partida que se han guardado en la base de datos
        if(nombre != null && nombre_rival != null) {
            new BorraMensajesBaseDeDatos().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);
        }
        if(Login.nombre != null) {
            // Dejo de ser seleccionable para una partida
            String estatus_conectado = "0";
            new ActualizaEstado(this).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, nombre, estatus_conectado);
        }
        finish();
    }
}
