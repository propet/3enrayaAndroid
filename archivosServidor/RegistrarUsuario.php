<?php

//--------------------------------------------------------------------------------------------------------
// Registro de un usuario, con su nombre y su password
//--------------------------------------------------------------------------------------------------------

include("datosconexionAndroid.php");
try{
	$gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}


$nombre = $_POST['nombre'];
$pass = $_POST['pass'];
    

// Insercion del nuevo usuario con este nombre en la base de datos
$consulta = "INSERT INTO usuario (id_usuario,nombre,pass,ELO,conectado) VALUES (NULL,:nombre,:pass,0,0)";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre,":pass"=>$pass));

echo "El registro del usuario $nombre ha sido exitoso";

?>