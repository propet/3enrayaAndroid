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



// Cual es MI nivel de ELO?   
$consulta = "SELECT ELO,id_usuario FROM usuario WHERE nombre = :nombre";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre"=>$nombre));
    foreach($resultadoConsulta as $valor){
        $ELO_propio = $valor[0]; 
        $id_usuario_propia = $valor[1];
    }


// Cual es el nivel de ELO de mi oponente?
$consulta = "SELECT ELO,id_usuario FROM usuario WHERE nombre = :nombre_rival";
$resultadoConsulta = $gd->prepare($consulta);
$resultadoConsulta->execute(array(":nombre_rival"=>$nombre_rival));
    foreach($resultadoConsulta as $valor){
        $ELO_rival = $valor[0]; 
        $id_usuario_rival = $valor[1];
    }


// Comparacion de niveles de ELO, el de menor nivel tiene el primer turno en la partida.
// En caso de que tengamos el mismo nivel, empieza quien tenga la id de usuario menor
if($ELO_propio != $ELO_rival){
	if($ELO_propio > $ELO_rival){
		echo "false";
	}else{
		echo "true";
	}
}else{
	if($id_usuario_propia < $id_usuario_rival){
		echo "true";

	}else{
		echo "false";
	}
}

?>
