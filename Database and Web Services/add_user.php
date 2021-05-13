<?php 

/*
 * Following code will create a new user row
 * All product details are read from HTTP Post Request
 */
 
	// array for JSON response
	$response = array();
	if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password'])){

		$username = $_POST['username'];
		$email =$_POST['email'];
		$password= $_POST['password'];
		$role=$_POST['role'];
		// include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
		$sql = "INSERT INTO user(username,email,password,role) VALUES(?,?,?,?);";
		$reponse = $bdd->prepare($sql); 
		$result = $reponse->execute(array($username,$email,$password,$role));
		if($result){
			echo 'Account Created !';
			 $response["success"] = 1;
        	 $response["message"] = "User Added!.";
		}else{
			echo 'An Error Occured';
			 $response["success"] = 0;
        	 $response["message"] = "An Error Occured.";
		}
	}else{
			 echo 'Required field missing!';
			 $response["success"] = 0;
        	 $response["message"] = "Required fields is/are missing!.";
	}
	//echo json_encode($response);

?>