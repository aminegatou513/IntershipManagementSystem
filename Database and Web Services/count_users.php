<?php 

// include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
// array for JSON response
		$response = array();
		$role=$_POST['role'];
		//Count users
		$sql = "SELECT * from user where role = ?";
		$reponse = $bdd->prepare($sql); 
		$reponse->execute(array($role));
		$result = $reponse->fetchAll();
		echo count($result);


?>