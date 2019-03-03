//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ActivityVictoria extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla de victoria
    //----------------------------------------------------------------------------------------------

    public TextView texto_puntuacion;
    MediaPlayer cancion;

    public void Reiniciar2(View v){

        Intent intento2 = new Intent(this, BuscandoRival.class);
        startActivity(intento2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_victoria);
        texto_puntuacion = (TextView) findViewById(R.id.puntuacionVictoria);

        if(!Main2Activity.jugador_anonimo) {
            // Consulta a la base de datos para subir mi puntuacion
            String resultado = "VICTORIA";
            new ActualizaMiELO(texto_puntuacion).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, resultado);
        }else{
            // No muestra los cuadros de dialogo referidos a la puntuacion
            TextView tu_puntuacion = (TextView) findViewById(R.id.textView6);
            tu_puntuacion.setText("");
            TextView puntos = (TextView) findViewById(R.id.textView7);
            puntos.setText("");
        }

        // Musiquita de victoria
        cancion =  MediaPlayer.create(this, R.raw.victoria);
        cancion.start();

    }

    protected void onPause() {
        super.onPause();
        cancion.stop();
        finish();
    }

}
