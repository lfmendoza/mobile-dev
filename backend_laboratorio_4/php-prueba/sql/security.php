<?php

function findUserByUserNameAndPassword($username, $password) {
    return "SELECT id, username FROM user WHERE username = '$username' AND password = MD5('$password')";
}

function updateUserTokenExpiracy($token, $expire_at_formatted, $user_id) {
    return "UPDATE user SET token = '$token', token_expiracy = '$expire_at_formatted' WHERE id = $user_id";
}

function createUser($username, $password, $name) {
    return "INSERT INTO user (username, password, nombre) VALUES ('$username', MD5('$password'), '$name')";
}