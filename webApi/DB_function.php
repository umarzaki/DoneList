<?php 
class DB_function{
    private $con;
    
    function __construct(){
        require_once 'connection.php';
        $db = new Db_Connect();
        $this->con = $db->connect();
    }
    
    public function daftar($nama, $email, $password){
        $stmt = $this->con->prepare("INSERT INTO user (nama, email, password) VALUES (?,?,?)");
        $stmt->bind_param("sss", $nama, $email, $password);
        $result = $stmt->execute();
        $stmt->close();
        
        if($result){
            return true;
        } else {
            return false;
        }
    }
    
    public function masuk($email, $password){
        $stmt = $this->con->prepare("SELECT * FROM user WHERE email = ?");
        $stmt->bind_param("s", $email);
        $result = $stmt->execute();
        
        if($result){
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            if ($password == $user['password']){
                return $user;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public function tambahActivity($id, $activity){
        $stmt = $this->con->prepare("INSERT INTO userActivity (userID, activityName) VALUES (?,?)");
        $stmt->bind_param("ss", $id, $activity);
        $result = $stmt->execute();
        $stmt->close();
        
        if($result){
            return true;
        } else {
            return false;
        }
    }
    
    public function ActivityList($nama){
        $stmt = $this->con->prepare("SELECT activityName FROM userActivity WHERE userID = (SELECT id FROM user WHERE nama = ?)");
        $stmt->bind_param("s", $nama);
        $result = $stmt->execute();
        
        if ($result){
            $activity = $stmt->get_result();
            $stmt->close();
            return $activity;
        } else {
            return NULL;
        }
    }
    
    public function userTerdaftar($email) {
        $stmt = $this->con->prepare("SELECT email from user WHERE email = ?");
        $stmt->bind_param("s", $email);
        $result = $stmt->execute();
        
        if($result){
          $stmt->store_result();  
          if ($stmt->num_rows > 0) {
            // user telah ada 
            $stmt->close();
            return true;
            } else {
            // user belum ada 
            $stmt->close();
            return false;
            }
        }
        
    }
}



?>