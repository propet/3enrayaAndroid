<?php

//--------------------------------------------------------------------------------------------------------
// Creacion de un usuario anonimo para jugar sin necesidad de registrarse
//--------------------------------------------------------------------------------------------------------

include("datosconexionAndroid.php");
try{
	$gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}


$nombre = "GUEST";
$ultima_id;

    
// Consulta la id del ultimo usuario
$consulta2 = "SELECT id_usuario from usuario ORDER BY id_usuario ASC";
$resultadoConsulta = $gd->prepare($consulta2);
$resultadoConsulta->execute();
// Obtencion de la ultima id
foreach($resultadoConsulta as $valor){
    $ultima_id = $valor[0]; 
}

// El nombre del nuevo usuario a insertar es:
settype($ultima_id,"integer");
$ultima_id = $ultima_id + 1;
$nombre = $nombre."_".$ultima_id;


echo "$nombre\n";

// Insercion del nuevo usuario con este nombre en la base de datos
$consulta = "INSERT INTO usuario (nombre,ELO,conectado) VALUES (:nombre,0,0)";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));

?>
