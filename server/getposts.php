<?php 
/*
Description - API to fetch all the user posts
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'time_elapsed.php';

require 'connection.php';



$query = "Select * from posts Order By Created_Date DESC";

$result = mysqli_query($conn,$query);

while ($row = $result->fetch_assoc()) {

    $record[] = $row;

}



if(empty($record))

{

	$response["Status"] = 400;

	$response["Message"] = "No Records Found.";

	echo json_encode($response);

}

else

{

	foreach($record as $record)

	{

		$user_id = $record["User_id"];

		$query = "Select * from users where User_id = '$user_id'";

		$result = mysqli_query($conn,$query);

		$user_info = array();

		while ($row = $result->fetch_assoc()) 

		{

			$user_info[] = $row;

		}

		$response["Post_id"] = $record["Post_id"];

		$response["User_id"] = $record["User_id"];

		$response["Firstname"] = $user_info[0]["First_name"];

		$response["Lastname"] = $user_info[0]["Last_name"];

		$response["Content"] = $record["Content"];

		$response["Anonymous"] = $record["Anonymity"];

		$response["Created_Date"] = $record["Created_Date"];

		$response["Time_Elapsed"] = time_elapsed(strtotime($record["Created_Date"]));

		$final_response["posts"][] =  $response;	

	}

	echo json_encode($final_response);

}



mysqli_free_result($result);

mysqli_close($conn);



?>