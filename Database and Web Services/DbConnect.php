<?php

try {
    $bdd = new PDO('mysql:host=localhost;dbname=stage_ensam;charset=utf8','root','',array(PDO::ATTR_EMULATE_PREPARES=>false,
    PDO::MYSQL_ATTR_DIRECT_QUERY=>false,
    PDO::ATTR_ERRMODE=>PDO::ERRMODE_EXCEPTION));
} catch (Exception $th) {
    die('Erreur : '.$th->getMessage());
}

?>