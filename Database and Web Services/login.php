<?php 
// include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
// array for JSON response
	$response = array();
	if(isset($_POST['username']) && isset($_POST['password'])){

		$user=$_POST['username'];
		$pass=$_POST['password'];
		$role=$_POST['role'];
		//Creating a DbConnect object to connect to the database
		$sql = "SELECT * from user where username = ? AND password = ? AND role = ?";
		$reponse = $bdd->prepare($sql); 
		$reponse->execute(array($user,$pass,$role));
		$result = $reponse->fetchAll();
		if(count($result) > 0){
			echo $role;
			$response["success"] = 1;
        	$response["message"] = "Login Succesfull.";
		}else{
			echo "Username or password incorrect";
			$response["success"] = 0;
        	$response["message"] = "Username or password incorrect.";
		}

	}else{
		echo "Cannot leave fields blank!";
	}
	//echoing JSON response
    //echo json_encode($response);


?>