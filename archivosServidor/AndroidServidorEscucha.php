<?php
include("datosconexionAndroid.php");

$gd;
try{
    $gd = new PDO($dsn,$usuario,$contrasena);
    $nombre_autor = $_POST['nombre_autor'];
    $nombre_escuchante = $_POST['nombre_escuchante'];
    // cambio de ids de tipo string a tipe int 
    settype($id_usuario1,"integer");
    settype($id_usuario2,"integer");
    //Insercion de mensaje en tabla
    if(isset($id_usuario1,$id_usuario2)){
        $consulta = "SELECT mensaje.mensaje, mensaje.nombre_autor, id_mensaje FROM mensaje WHERE ((nombre_autor = :nombre_autor AND nombre_escuchante = :nombre_escuchante) OR (nombre_autor = :nombre_escuchante AND nombre_escuchante = :nombre_autor)) ORDER BY id_mensaje DESC"; 
        $resultadoConsulta = $gd->prepare($consulta);
        $resultadoConsulta->execute(array(":nombre_autor"=>$nombre_autor,":nombre_escuchante"=>$nombre_escuchante));
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
