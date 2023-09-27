<?php

// Validate the credentials in the database, or in other data store.
// Codigo para validar credenciales
$hostname = "localhost";
$username = "root";
$password = "";
$database = "loginapp";

$conexion = new mysqli($hostname, $username, $password, $database);

if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}

?>