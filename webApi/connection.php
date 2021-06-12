<?php
    class DB_Connect {

        public function connect()
            {
                define ('HOST', 'localhost');
                define('USER', 'id17029886_umarzaki');
                define('PASS', 'Donelistapp17!');
                define('DB', 'id17029886_donelist');

                $this -> con = new mysqli(HOST, USER, PASS, DB) or die ('unable to connect');
                return $this -> con;
            }
    }
?>