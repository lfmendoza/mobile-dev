<?php
declare(strict_types=1);
use Firebase\JWT\JWT;
require_once('../vendor/autoload.php');
require_once('../config/connection.php');
require_once('../sql/security.php');

$json = file_get_contents("php://input");
$data = json_decode($json, true);

$usr = $data['username'];
$pwd = $data['password'];

$result = $conexion->query(findUserByUserNameAndPassword($usr, $pwd));

// Validate the credentials in the database, or in other data store.
// For the purposes of this application, we'll consider that the credentials are valid.
$hasValidCredentials = $result->num_rows == 1;

// extract credentials from the request
if ($hasValidCredentials) {
    $secret_Key = '68V0zWFrS72GbpPreidkQFLfj4v9m3Ti+DXc8OB0gcM=';
    $date       = new DateTimeImmutable();
    $expire_at  = $date->modify('+6 minutes')->getTimestamp();      // Add 60 seconds
    $domainName = "edu.uvg.loginapp";
    $username   = "username";                                    // Retrieved from filtered POST data

    // Create the token as an array
    $request_data = [
        'iat'  => $date->getTimestamp(),        // Issued at: time when the token was generated
        'iss'  => $domainName,                  // Issuer
        'nbf'  => $date->getTimestamp(),        // Not before
        'exp'  => $expire_at,                      // Expire
        'userName' => $username,                // User name
    ];

    // Encode the array to a JWT string.
	$token = JWT::encode(
        $request_data,      //Data to be encoded in the JWT
        $secret_Key, // The signing key
        'HS512'     // Algorithm used to sign the token, see https://tools.ietf.org/html/draft-ietf-jose-json-web-algorithms-40#section-3
    );

    $row = $result->fetch_assoc();
	$expire_at_formatted = date('Y-m-d', $expire_at);
    $user_id = $row["id"];
    if ($conexion->query(updateUserTokenExpiracy($token, $expire_at_formatted, $user_id)) === TRUE) {
        $response = array(
            "success" => true,
            "jwt" => $token
        );
        $json_response = json_encode($response);
        echo $json_response;
    } else {
        $error_message = "An error has occurred while trying to update the user information: " . $conn->error;
        $response = array(
            "success" => false,
            "error" => $error_message,
            "jwt" => $token
        );
        $json_response = json_encode($response);
        echo $json_response;
    }
} else {
	echo "error_bad_credentials";
}
