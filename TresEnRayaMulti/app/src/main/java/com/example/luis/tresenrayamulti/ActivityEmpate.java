//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ActivityEmpate extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla de empate
    //----------------------------------------------------------------------------------------------


    public TextView texto_puntuacion;

    public void Reiniciar2(View v){

        Intent intento2 = new Intent(this, BuscandoRival.class);
        startActivity(intento2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_empate);
        texto_puntuacion = (TextView) findViewById(R.id.puntuacionEmpate);

        if(!Main2Activity.jugador_anonimo) {
            // Consulta a la base de datos para mostrar mi puntuacion. No se modifica por el empate.
            String resultado = "EMPATE";
            new ActualizaMiELO(texto_puntuacion).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, resultado);
        }else{
            // No muestra los cuadros de dialogo referidos a la puntuacion
            TextView tu_puntuacion = (TextView) findViewById(R.id.textView6);
            tu_puntuacion.setText("");
        }
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
