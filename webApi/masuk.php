<?php
require_once 'DB_function.php';
$db = new DB_function();

$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $user = $db->masuk($email, $password);

    if ($user) {
        $response["error"] = FALSE;
        $response["id"] = $user["id"];
        $response["user"]["nama"] = $user["nama"];
        $response["user"]["email"] = $user["email"];
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["message"] = "Login gagal. email/password salah";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["message"] = "Parameter (email atau password) ada yang kurang";
    echo json_encode($response);
}
?>