<?php 
/*
Description - API to sign up into TrojaNow
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';
require 'connection.php';

$data = file_get_contents('php://input');
$json_array = json_decode($data,true);

$firstname = $json_array['Firstname'];
$lastname = $json_array['Lastname'];
$user = $json_array['Username'];
$pass = $json_array['Password'];
$contact = $json_array['Contact'];
$email = $json_array['Email'];

$query = "Select User_id from users where Email = '$email' OR Username = '$user'";
$result = mysqli_query($conn,$query);
$record = mysqli_fetch_row($result);

if(empty($record))
{
	$query = "Insert into users values (NULL,'$firstname','$lastname','$user','$pass','$email','$contact',1)";
	$result = mysqli_query($conn,$query);
	$userid = mysqli_insert_id($conn);
	$query = "Select * from users where User_id = '$userid'";
	$result = mysqli_query($conn,$query);
	$record = mysqli_fetch_row($result);
	$response["Status"] = 200;
	$response["Message"] = "Successful";
	$response["User_id"] = $userid;
	$response["Firstname"] = $record[1];	
	$response["Lastname"] = $record[2];	
	$response["Username"] = $record[3];	
	$response["Contact"] = $record[5];
	$response["Email"] = $record[6];
	$response["Online"] = $record[7];		
}
else
{
	$response["Status"] = 400;
	$response["Message"] = "User already exists";
}

echo json_encode($response);

mysqli_close($conn);
?>