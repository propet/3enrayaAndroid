<?php

//--------------------------------------------------------------------------------------------------------
// Para jugar con un rival conocido en caso de que estes 
// conectado registrado en la base de datos
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
$nombre_usuario_rival = $_POST['nombre_usuario_rival'];
$conectado = $_POST['conectado'];
$consulta = "UPDATE usuario SET conectado = :conectado WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":conectado"=>$conectado,":nombre"=>$nombre));

if($conectado == 0){
    echo "Ya no estas conectado";
}

if($conectado == 1){
    //Consulta para comprobar cual es tu ELO en el juego
    $consultaELO = "SELECT ELO,id_usuario from usuario WHERE nombre = :nombre";
    $resultadoConsulta = $gd->prepare($consultaELO); 
    $resultadoConsulta->execute(array(":nombre" => $nombre));
    //obtencion de tu ELO
    foreach($resultadoConsulta as $valor){
        $ELO = $valor[0];
        $id_usuario_propia = $valor[1];
    }

    


    Eleccion del rival por su nombre
    $consulta3 = "SELECT id_usuario,nombre,conectado from usuario WHERE nombre = :nombre_usuario_rival";
    $resultadoConsulta = $gd->prepare($consulta3);
    $resultadoConsulta->execute(array(":nombre_usuario_rival"=>$nombre_usuario_rival));
    //obtencion del rival
    foreach($resultadoConsulta as $valor){
        $id_usuario_rival = $valor[0];
        $nombre_usuario_rival = $valor[1];
        $conectado = $valor[2];
    }
    if($conectado != 0){
        echo "Conectado con: \n";
        echo "$id_usuario_propia \n";
        echo "$id_usuario_rival \n";
        echo "$nombre_usuario_rival \n";
    }else{
        echo "El usuario $nombre_usuario_rival no esta disponible"; 
    }

    }

?>