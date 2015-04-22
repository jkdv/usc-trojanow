<?php 
/*
Description - API to logout from TrojaNow
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';
require 'connection.php';

$data = file_get_contents('php://input');

$json_array = json_decode($data,true);
$userid = $json_array['User_id'];

$query = "Update users set Online = 0 where User_id = '$userid'";
$result = mysqli_query($conn,$query);

if (mysqli_affected_rows($conn) > 0)
{
	$response["Status"] = 200;
	$response["Message"] = "User Successfully logged out.";
}
else
{
	$response["Status"] = 400;
	$response["Message"] = "Failed to log out.";
}
echo json_encode($response);

mysqli_close($conn);
?>