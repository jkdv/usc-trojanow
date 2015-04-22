<?php 
/*
Description - API to handle user accounts
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';
require 'connection.php';

$data = file_get_contents('php://input');
$json_array = json_decode($data,true);

if(isset($json_array['User_id']))
{
	$userid = $json_array['User_id'];
}
if(isset($json_array['Firstname']))
{
	$firstname = $json_array['Firstname'];
}
if(isset($json_array['Lastname']))
{
	$lastname = $json_array['Lastname'];
}
if(isset($json_array['Username']))
{
	$username = $json_array['Username'];
}
if(isset($json_array['Password']))
{
	$password = $json_array['Password'];
}
if(isset($json_array['Email']))
{
	$email = $json_array['Email'];
}
if(isset($json_array['Contact']))
{
	$contact = $json_array['Contact'];
}
if(isset($json_array['Operation']))
{
	$operation = $json_array['Operation'];
}

if($userid != "" && $operation == "Update")
{
	$query = "Update users set First_Name = '$firstname', Last_name = '$lastname', Username = '$username', Password = '$password', Email = '$email', Contact = '$contact' where User_id = '$userid'";
	$result = mysqli_query($conn, $query);
	if (mysqli_affected_rows($conn) > 0)
	{
		$response["Status"] = 200;
		$response["Message"] = "Profile Successfully Updated.";
	}
	else
	{
		$response["Status"] = 400;
		$response["Message"] = "Profile is already up to date.";
	}	
}
else if($userid != "" && $operation == "Delete")
{
	$query1 = "Select * from posts where User_id = '$userid'";
	$result = mysqli_query($conn, $query1);
	if (mysqli_affected_rows($conn) > 0)
	{
		$query = "Delete from posts where User_id = '$userid';";
		$query .= "Delete from users where User_id = '$userid'";
		$result = mysqli_multi_query($conn, $query);
	}
	else
	{
		$query = "Delete from users where User_id = '$userid'";
		$result = mysqli_query($conn, $query);
	}
	
	if (mysqli_affected_rows($conn) > 0)
	{
		$response["Status"] = 200;
		$response["Message"] = "Account Successfully Deactivated.";
	}
	else
	{
		$response["Status"] = 400;
		$response["Message"] = "Failed to Deactivate Account.";
	}
}
else
{
	$response["Status"] = 400;
	$response["Message"] = "Error in Processing your Request!!!";
}
echo json_encode($response);

mysqli_close($conn);

?>