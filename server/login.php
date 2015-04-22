<?php 
/*
Description - API to login into TrojaNow
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';
require 'connection.php';

$data = file_get_contents('php://input');

$json_array = json_decode($data,true);
$user = $json_array['Username'];
$pass = $json_array['Password'];

$query = "Select * from users where Username = '$user' and Password = '$pass'";
$result = mysqli_query($conn,$query);
$record = mysqli_fetch_row($result);
if(empty($record))
{
	$response["Status"] = 400;
	$response["Message"] = "Login Failed";	
}
else
{
	$query = "Update users set Online = 1 where User_id = '$record[0]'";
	$result = mysqli_query($conn,$query);
	$response["Status"] = 200;
	$response["Message"] = "Successful";	
	$response["User_id"] = $record[0];
	$response["Firstname"] = $record[1];	
	$response["Lastname"] = $record[2];	
	$response["Username"] = $record[3];	
	$response["Contact"] = $record[6];
	$response["Email"] = $record[5];
	$response["Online"] = "1";
}
echo json_encode($response);

mysqli_close($conn);
?>