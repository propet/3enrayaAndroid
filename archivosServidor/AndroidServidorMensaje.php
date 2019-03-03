<?php
include("datosconexionAndroid.php");
$mensaje;
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
    $mensaje = $_POST['mensaje'];
    $nombre_autor = $_POST['nombre_autor'];
    $nombre_escuchante = $_POST['nombre_escuchante'];
    //Insercion de mensaje en tabla
    if(isset($mensaje,$nombre_autor,$nombre_escuchante)){
        $consulta = "INSERT INTO mensaje (id_mensaje,nombre_autor,nombre_escuchante,mensaje) VALUES (NULL,:nombre_autor,:nombre_escuchante,:mensaje)";
        $resultadoConsulta = $gd->prepare($consulta);
        $resultadoConsulta->execute(array(":nombre_autor"=>$nombre_autor,":nombre_escuchante"=>$nombre_escuchante,":mensaje"=>$mensaje));
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

echo "mensaje insertado";
$gd = null;
?>
