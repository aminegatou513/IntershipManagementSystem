<?php 

/*
 * 
 */
 // include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
	// array for JSON response
	$response = array();
	if(isset($_POST['id'])){

		$id=$_POST['id'];
		
		///
		
		$sql = "DELETE FROM user where id = ?";
		$reponse = $bdd->prepare($sql); 
		$result = $reponse->execute(array($id));
   	 	if($result){
   	 		echo "User Deleted";
   	 	}else{
   	 		  echo "Oops! An error occurred.";
   	 	}

	}else{
		     echo "Required fields is/are missing!.";
	}

	

?>