<?php 
/*
Description - API to get details of all users
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'connection.php';

$data = file_get_contents('php://input');

$json_array = json_decode($data,true);
$userid = $json_array['User_id'];

$query = "Select * from users where User_id != '$userid' Order By Online DESC";
$result = mysqli_query($conn,$query);

while ($row = $result->fetch_assoc()) {
    $record[] = $row;
}
if(empty($record))
{
	$response["Status"] = 400;
	$response["Message"] = "Failed";	
}
else
{
	foreach($record as $record)
	{
		$response["User_id"] = $record["User_id"];
		$response["Firstname"] = $record["First_name"];	
		$response["Lastname"] = $record["Last_name"];	
		$response["Username"] = $record["Username"];	
		$response["Contact"] = $record["Contact"];
		$response["Email"] = $record["Email"];
		$response["Online"] = $record["Online"];
		$final_response["users"][] =  $response;
	}
}
echo json_encode($final_response);

mysqli_free_result($result);
mysqli_close($conn);
?>