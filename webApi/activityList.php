<?php
require_once'DB_function.php';
$db = new DB_function();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $nama = $_GET['nama'];
    $res = $db->activityList($nama);
    if($res){
      $result = array();
    while ($row = $res->fetch_array()) {
        array_push($result, array('activity' => $row[0]));
    }
    echo json_encode(array("value" => 1, "result" => $result));  
    } else {
        echo json_encode(array("value" => 0, "message" => "pengambilan data gagal"));
    }
}
?>
