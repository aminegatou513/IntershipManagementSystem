<?php 

/*
 *
 */
 // include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
	// array for JSON response
	$response = array();
	if(isset($_POST['id_demande']) && isset($_POST['statut']) ){

		$statut =" ";
		$id=$_POST['id_demande'];
		$sql = "UPDATE demande SET statut = ? where id_demande = ?";
		$reponse = $bdd->prepare($sql);
		if($_POST['statut'] == "accepted"){
			$statut="accepted";
		}elseif($_POST['statut'] == "refused"){
			$statut="refused";
		}elseif($_POST['statut'] == "pending"){
			$statut="pending";
		}
		 
		$result = $reponse->execute(array($statut,$id));
   	 	if($result){
   	 		echo 'Request '.$statut;
   	 	}else{
   	 		  echo "Oops! An error occurred.";
   	 	}

	}else{
		     echo "Required fields is/are missing!.";
	}

	

?>