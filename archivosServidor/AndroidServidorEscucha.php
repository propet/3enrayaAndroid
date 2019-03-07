<?php
include("datosconexionAndroid.php");

$gd;
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
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
    if(isset($id_usuario1,$id_usuario2)){
        $consulta = "SELECT mensaje.mensaje, usuario.nombre, mensaje.id_mensaje FROM mensaje INNER JOIN usuario ON mensaje.id_usuario1 = usuario.id_usuario WHERE ((id_usuario1 = :id_usuario1 AND id_usuario2 = :id_usuario2) OR (id_usuario1 = :id_usuario2 AND id_usuario2 = :id_usuario1)) ORDER BY id_mensaje DESC"; 
        $resultadoConsulta = $gd->prepare($consulta);
        $resultadoConsulta->execute(array(":id_usuario1"=>$id_usuario1,":id_usuario2"=>$id_usuario2));
    //obtencion del ultimo comentario de la conversacion
        foreach($resultadoConsulta as $fila){
            echo "$fila[0]\n";   // mensaje 
            echo "$fila[1]\n";   // nombre el autor del mensaje 
            echo "$fila[2]\n";   // id_mensaje  id del mensaje 
        }
    }
}
catch(PDOException $e){
	echo "Fallo la conexion: ". $e->getMessage();
}
$gd = null;
?>
