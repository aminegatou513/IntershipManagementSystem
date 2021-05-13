<?php 

// include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
// array for JSON response
		$response = array();
		//Count users
		$sql = "SELECT * from demande";
		$reponse = $bdd->prepare($sql); 
		$reponse->execute();
		$result = $reponse->fetchAll();
		echo count($result);


?>