<?php 

// array for JSON response
		$response = array();
		$bdd = new mysqli("localhost","root","","stage_ensam");
        $id= "";

if(isset($_POST['username'])){
    $user = $_POST['username'];
    $result = mysqli_query($bdd,"SELECT id FROM user where username ='$user' AND role = 'user' LIMIT 1 ;") or die(mysqli_error($bdd));

// check for empty result
if (mysqli_num_rows($result) > 0) {
        while ($row = mysqli_fetch_array($result)){
            $id =$row["id"];
        }
        echo $id;
    }else{
        echo "error!";
    }


}else{
    echo "error!";
}
		

 

?>