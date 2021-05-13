<?php 

// array for JSON response
		$response = array();
		$bdd = new mysqli("localhost","root","","stage_ensam");

		// get all products from products table
$result = mysqli_query($bdd,"SELECT * FROM user where role = 'user';") or die(mysqli_error());
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["users"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $user = array();
        $user["id"] = $row["id"];
        $user["username"] = $row["username"];
        $user["password"] = $row["password"];
        $user["email"] = $row["email"];
 
        // push single product into final response array
        array_push($response["users"], $user);
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