<?php
 
require_once 'DB_function.php';
$db = new DB_function();
 
$response = array("error" => FALSE);
 
if (isset($_POST['nama']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    $nama = $_POST['nama'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    if ($db->userTerdaftar($email)) {
        $response["error"] = TRUE;
        $response["message"] = "User telah ada dengan email " . $email;
        echo json_encode($response);
    } else {
        $user = $db->daftar($nama, $email, $password);
        if ($user) {
            $response["error"] = FALSE;
            $response["message"] = "daftar user berhasil";
            echo json_encode($response);
        } else {
            $response["error"] = TRUE;
            $response["message"] = "Terjadi kesalahan saat melakukan daftar";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["message"] = "Harap lengkapi data";
    echo json_encode($response);
}
?>