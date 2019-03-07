<?php
include("datosconexionAndroid.php");
$mensaje;
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
    $mensaje = $_POST['mensaje'];
    $nombre_autor = $_POST['nombre_autor'];
    $nombre_escuchante = $_POST['nombre_escuchante'];
    
    
    // id_usuario1
    $consulta = "SELECT id_usuario FROM usuario WHERE nombre = :nombre_autor";
    $resultadoConsulta = $gd->prepare($consulta);
    $resultadoConsulta->execute(array(":nombre_autor"=>$nombre_autor));
    foreach($resultadoConsulta as $valor){
      $id_usuario1 = $valor[0];
    }

    // id_usuario2
    $consulta = "SELECT id_usuario FROM usuario WHERE nombre = :nombre_escuchante";
    $resultadoConsulta = $gd->prepare($consulta);
    $resultadoConsulta->execute(array(":nombre_escuchante"=>$nombre_escuchante));
    foreach($resultadoConsulta as $valor){
      $id_usuario2 = $valor[0];
    }


    //Insercion de mensaje en tabla
    if(isset($mensaje,$id_usuario1,$id_usuario2)){
        $consulta = "INSERT INTO mensaje (id_mensaje,id_usuario1,id_usuario2,mensaje) VALUES (NULL,:id_usuario1,:id_usuario2,:mensaje)";
        $resultadoConsulta = $gd->prepare($consulta);
        $resultadoConsulta->execute(array(":id_usuario1"=>$id_usuario1,":id_usuario2"=>$id_usuario2,":mensaje"=>$mensaje));
    }
/*    //Consulta del mensaje recibido
    $consulta = "SELECT mensaje.mensaje FROM mensaje WHERE ((id_usuario1 = :id_usuario1 OR id_usuario1 = :id_usuario2) OR (id_usuario2 = :id_usuario1 OR id_usuario2 = :id_usuario2))"; 
    $resultadoConsulta = $gd->prepare($consulta);
    $resultadoConsulta->execute(array(":id_usuario1"=>$id_usuario1,":id_usuario2"=>$id_usuario2));
    //obtencion del ultimo mensaje en la conversacion
    foreach($resultadoConsulta as $valor){
        $mensaje = $valor[0];
    }
 */
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}

/* echo "{$id_usuario2}"; */
echo "mensaje insertado";
$gd = null;
?>
