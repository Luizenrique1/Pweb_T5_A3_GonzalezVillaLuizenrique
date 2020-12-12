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
            $filtro = json_decode($cadena_JSON, true);

            $s = $filtro['pa'];

            $sql = "SELECT * FROM Alumnos WHERE primerAp='$s'";

            $res = mysqli_query($conexion, $sql);
            
            $datos['alumnos'] = array();
            if($res) {
                //Todo bien
                while($fila = mysqli_fetch_assoc($res)) {
                    $alumno = array();
                    $alumno['nc']=$fila['numControl']; 
                    $alumno['n']=$fila['nombre'];
                    $alumno['pa']=$fila['primerAp'];
                    $alumno['sa']=$fila['segundoAp'];
                    $alumno['e']=$fila['edad'];
                    $alumno['s']=$fila['semestre'];
                    $alumno['c']=$fila['carrera']; 
                    array_push($datos['alumnos'], $alumno);
                }

                $cad = json_encode($datos);
                echo $cad;

            } else {
                //Todo mal
                $respuesta['exito'] = false;
                $respuesta['mensaje'] = "Error en la insercion";
                $cad = json_encode($respuesta);
                //var_dump($cad);
                echo $cad;
            }
        }        

    } else {
        echo "No hay peticion HTTP";
    }

?>