<?php
include("datosconexionAndroid.php");
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
    echo "Fallo la conexion: ". $e->getMessage();
}


$nombre = $_POST['nombre'];
$nombre_rival = $_POST['nombre_rival'];

// id_usuario1
$consulta = "SELECT id_usuario FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));
foreach($resultadoConsulta as $valor){
  $id_usuario1 = $valor[0];
}

// id_usuario2
$consulta = "SELECT id_usuario FROM usuario WHERE nombre = :nombre_rival";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre_rival"=>$nombre_rival));
foreach($resultadoConsulta as $valor){
  $id_usuario2 = $valor[0];
}


// Eliminar los mensajes de la partida
$consulta = "DELETE FROM mensaje WHERE ((id_usuario1 = :id_usuario1 AND id_usuario2 = :id_usuario2) OR (id_usuario1 = :id_usuario2 AND id_usuario2 = :id_usuario1))"; 
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":id_usuario1"=>$id_usuario1,":id_usuario2"=>$id_usuario2));

echo "Eliminacion de mensajes completada";


?>
