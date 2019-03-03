//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class Lista_adaptador extends ArrayAdapter {
    //----------------------------------------------------------------------------------------------
    // Adaptador para mostrar cada item de la listview con el formato de layout_mensaje_tipo.xml
    // y la informacion del contenedor del mensaje
    //----------------------------------------------------------------------------------------------

    private Context context;

    public Lista_adaptador(Context context, ArrayList<Mensaje_entrada> mensajes) {
        super(context, 0, mensajes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Mensaje_entrada contenedor_mensaje = (Mensaje_entrada) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_mensaje_tipo, parent, false);
        }
        // Lookup view for data population
        TextView texto_nombre_fila = (TextView) convertView.findViewById(R.id.textViewNombre);
        TextView texto_mensaje_fila = (TextView) convertView.findViewById(R.id.textViewMensaje);
        // Populate the data into the template view using the data object
        texto_nombre_fila.setText(contenedor_mensaje.autor);
        if(contenedor_mensaje.autor.equals(ConexionHttp.nombre)){
            // Mis mensajes se muestran de un color
            texto_nombre_fila.setTextColor(ContextCompat.getColor(context, R.color.verdeClarito));
            // Colocar tu texto a la derecha del layout
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) texto_nombre_fila.getLayoutParams();
            params.gravity = Gravity.END;
            texto_nombre_fila.setLayoutParams(params);
            texto_mensaje_fila.setLayoutParams(params);
        }
        else{
            // Mensajes de mi oponente se muestran de otro color
            texto_nombre_fila.setTextColor(ContextCompat.getColor(context, R.color.rojoRival));
            // Colocar el texto de tu oponente a la izquierda del layout
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) texto_nombre_fila.getLayoutParams();
            params.gravity = Gravity.START;
            texto_nombre_fila.setLayoutParams(params);
            texto_mensaje_fila.setLayoutParams(params);
        }
        //
        texto_mensaje_fila.setText(contenedor_mensaje.mensaje);
        // Return the completed view to render on screen
        return convertView;
    }
}
