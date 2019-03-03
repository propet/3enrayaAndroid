//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class juego extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla de juego donde se muestran el chat y el tres en raya.
    // Los mensajes del chat se muestran dentro de una listView, los botones representan las
    // casillas del tres en raya.
    // Los botones son pulsables o no segun sea mi turno en la partida
    // o no.
    // Al presionar un boton envio la informacion de cual boton he pulsado como
    // un mensaje de chat mas, pero con el prefijo "~~~" para que mi rival lo interprete como un
    // movimiento (aqui lo hemos llamado comando).
    //----------------------------------------------------------------------------------------------

    // Objeto escucha
    public static EscuchaMensajesHttp escucha;

    // Declaracion de views
    public EditText edit;
    public static TextView texto_mensaje_nombre;
    public static TextView texto_mensaje;
    // Listview de mensajes
    public static ArrayList<Mensaje_entrada> mensajeArr; // informacion de items en el listView
    public static ListView lista;
    public static Lista_adaptador adapter;
    // Variables tres en raya
    public static int ELO;
    public static boolean turno= false;
    public static String marca; // Mis fichas del tres en raya
    public static String marca_rival; // Las fichas de mi rival en el tres en raya
    int[] botones = {0,0,0,0,0,0,0,0,0}; // Guarda la posicion de mis fichas
    int[] botones_rival = {0,0,0,0,0,0,0,0,0}; // Guarda la posicion de las fichas de mi rival

    public int[] buttonViewIds = new int[] { // Array con las id's de los botones
              R.id.boton0, R.id.boton1, R.id.boton2 ,
              R.id.boton3, R.id.boton4, R.id.boton5 ,
              R.id.boton6, R.id.boton7, R.id.boton8 };

    // Declaracion de Array de botones
    public Button[] buttonArray = new Button[buttonViewIds.length];



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_juego);

        // Inicio la captura de mensajes
        juego.escucha = new EscuchaMensajesHttp(juego.this);
        juego.escucha.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // Asignacion de los elementos de la array de los botones
        for (int i = 0; i < buttonViewIds.length; i++) {
            buttonArray[i] = (Button)findViewById(buttonViewIds[i]);
        }

        // Asignacion Views
        edit = (EditText) findViewById(R.id.mensaje);
        texto_mensaje_nombre = (TextView) findViewById(R.id.textViewNombre);
        texto_mensaje = (TextView) findViewById(R.id.textViewMensaje);
        lista = (ListView) findViewById(R.id.listView);
        // Array de objetos con la informacion de los items de la listView
        mensajeArr = new ArrayList<>();
        // Introduzco el adaptador en la listview
        adapter = new Lista_adaptador(getApplicationContext(), mensajeArr);
        lista.setAdapter(adapter);

        // Quien empieza la partida? Me asigna un valor de true o false a mi variable turno.
        // true -> empiezo; false -> empieza mi rival.
        // Tambien asigna marcas a mi rival y a mi.
        new QuienEmpieza(this, juego.this).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, Login.nombre, Login.nombre_rival);
    }

    public void onEnviar(View v){

        Log.d("onEnviar", "A punto de crear objeto mensaje_obj");
        EditText men = (EditText) findViewById(R.id.mensaje);
        String mensaje = men.getText().toString();

        // AsyncTask en paralelo, para poder evaluar la base de datos a la vez que enviar mensajes
        new MensajesHttp(edit).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,mensaje);
        Log.d("onEnviar", "ejecutado mensaje_obj");
    }


    public void marcaBoton(String comando, String nombre_autor){
        // Si yo no he mandado el comando, dibujo la marca de mi rival y tomo el turno
        if(!(nombre_autor.equals(Login.nombre))){
            String[] tokens = comando.split("~~~");
            comando = tokens[1]; // eliminada la parte ~~~ del comando, recupero el numero del boton
            Log.d("COMANDO", comando);
            int n_boton = Integer.parseInt(comando);
            buttonArray[n_boton].setText(marca_rival);
            buttonArray[n_boton].setTextColor(ContextCompat.getColor(this, R.color.rojoRival));
            botones_rival[n_boton] = 1;

            // He perdido la partida con este movimiento de mi rival?, si lo he hecho voy
            // a la activity derrota
            if(ComprobarVictoria(botones_rival)){ // Comprueba la victoria de mi rival
                // Dejo de escuchar los mensajes de la partida que he abandonado
                juego.escucha.cancel(true);
                // Borro la lista de mensajes que tenia de la conversacion anterior
                juego.mensajeArr.clear();
                String mensaje;
                if (turno) mensaje = "Han ganado las X";
                else mensaje ="Han ganado las Os";

                Intent intento = new Intent(this, ActivityDerrota.class);
                intento.putExtra("mensaje_extra", mensaje);
                startActivity(intento);
            }
            else if(comprobarEmpate(botones, botones_rival)){
                // Dejo de escuchar los mensajes de la partida que he abandonado
                juego.escucha.cancel(true);
                // Borro la lista de mensajes que tenia de la conversacion anterior
                juego.mensajeArr.clear();
                Intent intento = new Intent(this, ActivityEmpate.class);
                startActivity(intento);
            }

            turno = true; // Es mi turno
            actualizaBotones(); // Desbloqueo los botones para poder mover ficha
        }
    }

    // Bloquea o desbloquea los botones de la tableLayout segun sea mi turno o no
    public void actualizaBotones() {

        String numeroDeBotones = Integer.toString(buttonViewIds.length);
        Log.d("actualizar botones", numeroDeBotones);

        Button boton_0 = (Button) findViewById(R.id.boton0);
        Button boton_1 = (Button) findViewById(R.id.boton1);
        Button boton_2 = (Button) findViewById(R.id.boton2);
        Button boton_3 = (Button) findViewById(R.id.boton3);
        Button boton_4 = (Button) findViewById(R.id.boton4);
        Button boton_5 = (Button) findViewById(R.id.boton5);
        Button boton_6 = (Button) findViewById(R.id.boton6);
        Button boton_7 = (Button) findViewById(R.id.boton7);
        Button boton_8 = (Button) findViewById(R.id.boton8);
        Button[] array_de_botones = new Button[] {boton_0,boton_1,boton_2,boton_3,boton_4,boton_5,boton_6
                ,boton_7,boton_8};
        if(turno) {
            for (Button array_de_botone : array_de_botones) {
                array_de_botone.setEnabled(true);
                array_de_botone.setClickable(true);
            }
        }else{
            for (Button array_de_botone : array_de_botones) {
                array_de_botone.setEnabled(false);
                array_de_botone.setClickable(false);
            }
        }
    }

    public int RegistrarBoton( Button v ){
        switch (v.getId()){
            case R.id.boton0: return 0;
            case R.id.boton1: return 1;
            case R.id.boton2: return 2;
            case R.id.boton3: return 3;
            case R.id.boton4: return 4;
            case R.id.boton5: return 5;
            case R.id.boton6: return 6;
            case R.id.boton7: return 7;
            case R.id.boton8: return 8;
        }
        return 0;}

    public boolean ComprobarVictoria(int b[]){
        //horizontal
        if((b[0]==b[1])&&(b[0]==b[2])){if (b[0]!=0)return true;}
        if((b[3]==b[4])&&(b[3]==b[5])){if (b[3]!=0)return true;}
        if((b[6]==b[7])&&(b[6]==b[8])){if (b[6]!=0)return true;}
        //vertical
        if((b[0]==b[3])&&(b[0]==b[6])) {if (b[0]!=0)return true;}
        if((b[1]==b[4])&&(b[1]==b[7])) {if (b[1]!=0)return true;}
        if((b[2]==b[5])&&(b[2]==b[8])) {if (b[2]!=0)return true;}
        //diagonal
        if((b[0]==b[4])&&(b[0]==b[8])) {if (b[0]!=0)return true;}
        if((b[2]==b[4])&&(b[2]==b[6])) {if (b[2]!=0)return true;}
        return false;
    }

    public boolean comprobarEmpate(int b1[], int b2[]){
        int[] b3 = new int[botones.length];
        for(int i =0; i < botones.length; i++){
            b3[i] = b1[i] + b2[i];
            if(b3[i] == 0) return false;
        }
        return true;
    }

    public void Rellenar(View v){
        int n;
        n= RegistrarBoton((Button)v);

        if (botones[n]==0 && botones_rival[n]==0){

            if (turno) {
                ((Button) v).setText(marca);
                ((Button) v).setTextColor(ContextCompat.getColor(this, R.color.verdeClarito));
                botones[n]=1;
                turno = false;
                actualizaBotones();

                // Manda comando para identificar el boton que se ha seleccionado
                String comando = Integer.toString(n);
                comando = "~~~" + comando; // prefijo para distinguir comandos de mensajes normales
                new MensajesHttp(edit).executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, comando);
                Log.d("COMANDOSS", "enviado comando");

            }
        }
        if (ComprobarVictoria(botones)){
            // Dejo de escuchar los mensajes de la partida que he abandonado
            juego.escucha.cancel(true);
            // Borro la lista de mensajes que tenia de la conversacion anterior
            juego.mensajeArr.clear();
            ((Button) v).setText("V");
            Intent intento = new Intent(this, ActivityVictoria.class);
            startActivity(intento);
        }
        else if(comprobarEmpate(botones, botones_rival)){
            // Dejo de escuchar los mensajes de la partida que he abandonado
            juego.escucha.cancel(true);
            // Borro la lista de mensajes que tenia de la conversacion anterior
            juego.mensajeArr.clear();
            Intent intento = new Intent(this, ActivityEmpate.class);
            startActivity(intento);
        }
    }

    /*Pregunta si queremos salir del jeugo en cualquier momento al darle al boton back*/
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Seguro que quieres abandonar la partida?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        juego.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    protected void onPause(){
        super.onPause();
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
