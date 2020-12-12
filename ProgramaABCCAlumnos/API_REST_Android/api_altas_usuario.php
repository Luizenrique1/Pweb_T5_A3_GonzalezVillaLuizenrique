<?php

    include('../sitio/scripts_php/conexion_bd_usuarios.php');

    $con = new ConexionBD();
    $conexion = $con->getConexion();

    //var_dump($conexion);

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
        $cadena_JSON = file_get_contents('php://input'); //Recibe información a través de HTTP

        if($cadena_JSON == false) {
            echo "No hay cadena JSON";
        } else {
            $datos = json_decode($cadena_JSON, true);

            $u = $datos['u'];
            $p = $datos['p'];
           
            $u_cifrado = sha1($u);
            $p_cifrado = sha1($p);

        //echo $u_cifrado;

            $sql = "INSERT INTO Usuarios VALUES(sha1('$u'), sha1('$p'))";


            $res = mysqli_query($conexion, $sql);

            $respuesta = array();

            if($res) {
                //Todo bien
                $respuesta['exito'] = true;
                $respuesta['mensaje'] = "Insercion correcta";
                $cad = json_encode($respuesta);
                echo ($cad);
            } else {
                //Todo mal
                $respuesta['exito'] = false;
                $respuesta['mensaje'] = "Error en la insercion";
                $cad = json_encode($respuesta);
                echo ($cad);
            }
        }        

    } else {
        echo "No hay peticion HTTP";
    }

?>