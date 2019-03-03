<?php
include("datosconexionAndroid.php");
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
    echo "Fallo la conexion: ". $e->getMessage();
}


$nombre_autor = $_POST['nombre'];
$nombre_escuchante = $_POST['nombre_rival'];

// Eliminar los mensajes de la partida
$consulta = "DELETE FROM mensaje WHERE ((nombre_autor = :nombre_autor AND nombre_escuchante = :nombre_escuchante) OR (nombre_autor = :nombre_escuchante AND nombre_escuchante = :nombre_autor))"; 
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre_autor"=>$nombre_autor,":nombre_escuchante"=>$nombre_escuchante));

echo "Eliminacion de mensajes completada";


?>