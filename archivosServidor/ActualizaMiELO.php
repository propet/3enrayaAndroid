<?php
include("datosconexionAndroid.php");
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
    echo "Fallo la conexion: ". $e->getMessage();
}

$ELO;
$nombre = $_POST['nombre'];
$resultado = $_POST['resultado'];

// Consulta mi ELO actual
$consulta = "SELECT ELO FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));
// Obtiene mi ELO de la consulta
foreach($resultadoConsulta as $valor){
	$ELO = $valor[0];   
}

// Si el resultado de la partida ha sido DERROTA
if($resultado == "DERROTA"){
	if($ELO <= 10){ // Si no tengo mas de 10 puntos de ELO me quedo con 0
		$ELO = 0;
	}else{
		$ELO = $ELO - 10;
	}
}
// Si el resultado de la partida ha sido VICTORIA
if($resultado == "VICTORIA"){
	$ELO = $ELO + 10;
}

// Acutaliza mi ELO
$consulta = "UPDATE usuario SET ELO = :ELO WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre,":ELO"=>$ELO));

// Devuelve mi puntuacion actualizada
echo "$ELO";

?>
