<?php
require_once 'DB_function.php';
$db = new DB_function();
 
$response = array("error" => FALSE);
 
if (isset($_POST['id']) && isset($_POST['activity'])) {
     $id = $_POST['id'];
    $activity = $_POST['activity'];
    
    $res = $db->tambahActivity($id, $activity);
    
    if($res){
        $response["error"] = FALSE;
        $response["message"] = "aktivitas berhasil ditambahkan";
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["message"] = "aktivitas gagal ditambahkan";
        echo json_encode($response);
    }

} else {
    $response["error"] = TRUE;
    $response["message"] = "aktivitas gagal ditambahkan, kesalahan input";
    echo json_encode($response);
}
?>