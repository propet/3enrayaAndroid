//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Registro extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    // Pantalla tipo formulario donde rellenamos campos nombre y password para registrarnos
    // como un usuario en la base de datos.
    //----------------------------------------------------------------------------------------------


    EditText nomb;
    EditText pass;
    EditText rep_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_layout);

        nomb = (EditText) findViewById(R.id.nombre);
        pass = (EditText) findViewById(R.id.pass);
        rep_pass = (EditText) findViewById(R.id.rep_pass);

    }

    public void onRegistrar(View v){
        String nombre = nomb.getText().toString();
        String password = pass.getText().toString();
        String rep_password = rep_pass.getText().toString();
        // Coinciden las passwords?
        if(password.equals(rep_password) && !(nombre.isEmpty())) {
            Log.d("onRegistrar", "password: " + password + "   password_repetida: " + rep_password);
            Login.nombre = nombre;
            Login.password = password;
            new RegistraUsuario(this, nombre, password).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            if(password.equals(rep_password)){
                // Toast: El nombre proporcionado no es valido
                Context context = getApplicationContext();
                CharSequence text = "El nombre proporcionado no es valido.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                // Toast: Las contraseñas no coinciden.
                Context context = getApplicationContext();
                CharSequence text = "Las contraseñas no coinciden.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                pass.setText("");
                rep_pass.setText("");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
