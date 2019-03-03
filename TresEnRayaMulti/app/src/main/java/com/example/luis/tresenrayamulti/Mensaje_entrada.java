//----------------------------------------------------------------------------------------------
// Javier Henche Guijarro 12185   &   Luis Domingo Aranda E14004
//----------------------------------------------------------------------------------------------
package com.example.luis.tresenrayamulti;

public class Mensaje_entrada {
    //----------------------------------------------------------------------------------------------
    // Contenedor de la informacion que contendra cada item de la listview, se instancia
    // en clase Lista_adaptador
    //----------------------------------------------------------------------------------------------

    public String autor;
    public String mensaje;

    public Mensaje_entrada (String autor, String mensaje) {
        this.autor = autor;
        this.mensaje = mensaje;
    }
}

