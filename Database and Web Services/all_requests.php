<?php 

// array for JSON response
		$response = array();
		$bdd = new mysqli("localhost","root","","stage_ensam");

		// get all products from products table
$result = mysqli_query($bdd,"SELECT demande.*, user.username FROM demande,user where demande.user_id=user.id ; ") or die(mysqli_error());
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["answer"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $answer = array();
        $answer["id_demande"] = $row["id_demande"];
        $answer["titre"] = $row["titre"];
        $answer["nom"] = $row["nom"];
        $answer["prenom"] = $row["prenom"];
        $answer["lieu_naiss"] = $row["lieu_naiss"];
        $answer["email"] = $row["email"];
        $answer["CIN"] = $row["CIN"];
        $answer["Code_Apoge"] = $row["Code_Apoge"];
        $answer["adresse"] = $row["adresse"];
        $answer["langue"] = $row["langue"];
        $answer["logiciel"] = $row["logiciel"];
        $answer["statut"] = $row["statut"];
        $answer["username"]= $row["username"];

 
        // push single product into final response array
        array_push($response["answer"], $answer);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Users found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>