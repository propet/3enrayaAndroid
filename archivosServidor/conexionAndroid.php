<?php
include("datosconexion.php");   //mas seguro que declarar directamente aqui las variable usuario y contrasena

try{
	$gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}

$nombre = $_POST["nombre"];
$consulta = "SELECT * FROM usuario WHERE nombre = :nombre";
$resultadoConsulta=$gd->prepare($consulta);
$resultadoConsulta->execute(":nombre" => $nombre);
echo "id_cliente<
?>
