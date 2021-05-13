<?php 

/*
 * Following code will create a new demande row
 * All product details are read from HTTP Post Request
 */
 // include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
	// array for JSON response
	$response = array();
	if(isset($_POST['id']) && isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password'])){

		$id=$_POST['id'];
		$username=$_POST['username'];
		$email=$_POST['email'];
		$password=$_POST['password'];
		$role = $_POST['role'];
		///
		
		$sql = "UPDATE user SET username = ? , email = ? , password = ? , role = ? where id = ?";
		$reponse = $bdd->prepare($sql); 
		$result = $reponse->execute(array($username,$email,$password,$role,$id));
   	 	if($result){
   	 		echo "User Updated";
   	 	}else{
   	 		  echo "Oops! An error occurred.";
   	 	}

	}else{
		     echo "Required fields is/are missing!.";
	}

	

?>