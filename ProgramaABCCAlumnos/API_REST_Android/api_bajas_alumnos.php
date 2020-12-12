<?php

    include('../sitio/scripts_php/conexion_bd.php');

    $con = new ConexionBD();
    $conexion = $con->getConexion();

    //var_dump($conexion);

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        $cadena_JSON = file_get_contents('php://input'); //Recibe información a través de HTTP

        if($cadena_JSON == false) {
            echo "No hay cadena JSON";
        } else {
            $datos = json_decode($cadena_JSON, true);

            $nc = $datos['nc'];
            
            $sql = "DELETE FROM ALUMNOS WHERE numControl='$nc'";

            $res = mysqli_query($conexion, $sql);

            $respuesta = array();

            if($res) {
                //Todo bien
                $respuesta['exito'] = true;
                $respuesta['mensaje'] = "Se elimino correctamente";
                $cad = json_encode($respuesta);
                echo($cad);
            } else {
                //Todo mal
                $respuesta['exito'] = false;
                $respuesta['mensaje'] = "Error en eliminacion de registro";
                $cad = json_encode($respuesta);
                echo($cad);
            }
        }        

    } else {
        echo "No hay peticion HTTP";
    }

?>