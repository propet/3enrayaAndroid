<?php
include("datosconexionAndroid.php");
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
}
catch(PDOException $e){
    echo "Fallo la conexion: ". $e->getMessage();
}


$nombre = $_POST['nombre'];
$conectado = $_POST['conectado'];
$pass = $_POST['pass'];
$nombre_consulta;
$pass_real;
$nombre_coincide = false;
$pass_coincide = false;



// Realiza consulta para saber si existe el usuario de nombre dado
$consulta = "SELECT nombre FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));

$numeroDeFilas = $resultadoConsulta->rowCount();

// Si la consulta ha devuelto algun nombre de usuario es que este existe.
if($numeroDeFilas != 0){
	$nombre_coincide = true;
}
else{
	echo "ERROR\n";
}




// Realiza consulta para saber si la password proporcionada corresponde con la
// que tenga el usuario de nombre dado
$consulta = "SELECT pass FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));
// Obtiene la contraseña del usuario de nombre dado
foreach($resultadoConsulta as $valor){
	$pass_real = $valor[0];   
}

if($pass_real == $pass){
	$pass_coincide = true;
}


// Si existe el usuario, y la password dada corresponde con la real, entonces se permite 
// la actualizacion del estado en la base de datos
if($nombre_coincide && $pass_coincide){
		// Realiza consulta para actualizar estado de conectado
	$consulta = "UPDATE usuario SET conectado = :conectado WHERE nombre = :nombre";
	$resultadoConsulta = $gd->prepare($consulta);
	$resultadoConsulta->execute(array(":conectado"=>$conectado,":nombre"=>$nombre));

	if($conectado == 0){
	    echo "Ya no eres selecionable para una partida";
	}

	if($conectado == 1){
	    echo "Pasas a estar en la lista de seleccionables para partida";
	}
}
else{
	echo "ERROR";
}





?>