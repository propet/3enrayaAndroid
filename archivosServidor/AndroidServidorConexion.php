<?php

//--------------------------------------------------------------------------------------------------------
// Seleccion automatica de rivales
//--------------------------------------------------------------------------------------------------------

include("datosconexionAndroid.php");
try{
	$gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}
$ELO;
$id_usuario_propia;
$id_usuario_rival;
$nombre_usuario_rival;

$nombre = $_POST['nombre'];
    
    // Busqueda de un rival aleatorio que este conectado
    $conectado = 0;
    $consulta2 = "SELECT id_usuario,nombre,conectado from usuario WHERE nombre != :nombre AND conectado = 1";
    $resultadoConsulta = $gd->prepare($consulta2);
    $resultadoConsulta->execute(array(":nombre"=>$nombre));
    //Obtencion del rival
    foreach($resultadoConsulta as $valor){
        $id_usuario_rival = $valor[0]; 
        $nombre_usuario_rival = $valor[1];
        $conectado = $valor[2];
    }
    if($conectado != 0){
        echo "Conectado con: \n"; 
        echo "$nombre_usuario_rival \n";

    // HOTFIX BUG: NO SE PUEDE JUGAR COMO USUARIO ANONIMO!! LES HAGO NOTAR A LOS JUGADORES QUE ESTO 
    // ES ASI POR UN BUG, Y SE LO DIGO POR EL CHAT DEL JUEGO.!!!!!!!!!!!!!!!!!!!!!!!!!!
    // INSERCION DEL COMENTARIO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    $mensaje_bug = 'OPCION JUGAR DESHABILITADA POR UN BUG DE ULTIMA HORA.Perdonadnos la vida XD';
    $consulta = "INSERT INTO mensaje (id_mensaje,nombre_autor,nombre_escuchante,mensaje) VALUES
                    (NULL,:nombre,:nombre_usuario_rival,:mensaje_bug) WHERE
                    ((nombre_autor = :nombre AND nombre_escuchante = :nombre_usuario_rival) OR
                    (nombre_autor = :nombre_usuario_rival AND nombre_escuchante = :nombre))";
    $resultadoConsulta = $gd->prepare($consulta);
    $resultadoConsulta->execute(array(":nombre"=>$nombre,"nombre_usuario_rival"=>$nombre_usuario_rival,
                                        ":mensaje_bug"=>$mensaje_bug));

        break;
    }
    else{
        echo "Ups";
    }



    //Eleccion del rival por su nombre
    // $consulta3 = "SELECT id_usuario,nombre,conectado from usuario WHERE nombre = :nombre_usuario_rival";
    // $resultadoConsulta = $gd->prepare($consulta3);
    // $resultadoConsulta->execute(array(":nombre_usuario_rival"=>$nombre_usuario_rival));
    // //obtencion del rival
    // foreach($resultadoConsulta as $valor){
    //     $id_usuario_rival = $valor[0];
    //     $nombre_usuario_rival = $valor[1];
    //     $conectado = $valor[2];
    // }
    // if($conectado != 0){
    //     echo "Conectado con: \n";
    //     echo "$id_usuario_propia \n";
    //     echo "$id_usuario_rival \n";
    //     echo "$nombre_usuario_rival \n";
    // }else{
    //     echo "El usuario $nombre_usuario_rival no esta disponible"; 
    // }

    //}

?>
