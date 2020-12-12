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

            $sql = "SELECT * FROM usuarios WHERE usuario='$u_cifrado' AND password='$p_cifrado'";


            $res = mysqli_query($conexion, $sql);

            $respuesta = array();

            if(mysqli_num_rows($res)==1) {
                //Todo bien
                $respuesta['exito'] = true;
                $respuesta['mensaje'] = "Existe";
                $cad = json_encode($respuesta);
                echo ($cad);
            } else {
                //Todo mal
                $respuesta['exito'] = false;
                $respuesta['mensaje'] = "No existe";
                $cad = json_encode($respuesta);
                echo ($cad);
            }
        }        

    } else {
        echo "No hay peticion HTTP";
    }

?>