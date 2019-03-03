//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Compite extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla donde se nos muestran las opciones para empezar a jugar como jugadores
    // registrados: puedes logearte si ya te has registrado como algun jugador, o crear un
    // nuevo usuario.
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compite);
    }

    public void onLogin(View v){
        // Ahora que tengo rival, inicio la pantalla de juego
        Intent intento = new Intent(this, Login.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intento);
    }

    public void onRegistro(View v){
        // Ahora que tengo rival, inicio la pantalla de juego
        Intent intento = new Intent(this, Registro.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intento);
    }

    protected void onPause(){
        super.onPause();
        finish();
    }
}
