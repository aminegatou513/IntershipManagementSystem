<?php 

/*
 * Following code will create a new demande row
 * All product details are read from HTTP Post Request
 */
 // include db connect class
   	 	require_once __DIR__ . '/DbConnect.php';
   	 	ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
	// array for JSON response
	$response = array();
		if(isset($_POST['titre']) && isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['lieu_naiss']) && isset($_POST['email']) && isset($_POST['CIN']) && isset($_POST['Code_Apoge']) && isset($_POST['adresse']) && isset($_POST['langue']) && isset($_POST['logiciel']) && isset($_POST['user_id']) && isset($_POST['statut'])){

		$titre=$_POST['titre'];
		$nom=$_POST['nom'];
		$prenom=$_POST['prenom'];
		$lieu_naiss=$_POST['lieu_naiss'];
		$email=$_POST['email'];
		$cin=$_POST['CIN'];
		$code_ap=$_POST['Code_Apoge'];
		$adr=$_POST['adresse'];
		$langue=$_POST['langue'];
		$logiciel=$_POST['logiciel'];
		$statut=$_POST['statut'];
		$user_id =$_POST['user_id'];
		// For now change later
		///
		
		$sql = "INSERT INTO demande(titre,nom,prenom,lieu_naiss,email,CIN,Code_Apoge,adresse,langue,logiciel,statut,user_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		$reponse = $bdd->prepare($sql); 
		$result = $reponse->execute(array($titre,$nom,$prenom,$lieu_naiss,$email,$cin,$code_ap,$adr,$langue,$logiciel,$statut,$user_id));
   	 	if($result){
   	 		// successfully inserted into database
        	echo "accepted";
   	 	}else{
   	 		 echo "error";
   	 	}

	}else{

		     echo "internal error";
	}



?>