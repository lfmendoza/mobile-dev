<?php

declare(strict_types=1);
require_once('../vendor/autoload.php');
require_once('../config/connection.php');
require_once('../sql/security.php');

$json = file_get_contents("php://input");
$data = json_decode($json, true);

$usr = $data['username'];
$pwd = $data['password'];
$name = $data['name'];

if($conexion->query(createUser($usr, $pwd, $name))) {
    $response = array(
        "success" => true,
        "message" => "User created succesfully"
    );
    $json_response = json_encode($response);
    echo $json_response;
} else {
    $error_message = "An error has occurred while creating the user: " . $conn->error;
    $response = array(
        "success" => false,
        "message" => $error_message
    );
    $json_response = json_encode($response);
    echo $json_response;
}