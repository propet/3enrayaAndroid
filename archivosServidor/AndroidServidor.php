<?php
include("datosconexion.php");
try{
	$gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}
$nombre = $_POST['nombre'];
$consulta = "SELECT * FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre" => $nombre));

if($resultadoConsulta){
    foreach($resultadoConsulta as $valor){
        echo "id:$valor[0] nombre:$valor[1] pass:$valor[2] ELO:$valor[3]";
    }
}
?>
