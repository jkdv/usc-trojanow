<?php 
/*
Description - API to deal with user posts
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';

require 'connection.php';

require 'time_elapsed.php';

$date = date('Y-m-d h:i:s', time());

$data = file_get_contents('php://input');

$json_array = json_decode($data,true);



if(isset($json_array['User_id']))

{

	$userid = $json_array['User_id'];

}

if(isset($json_array['Post_id']))

{

	$postid = $json_array['Post_id'];

}

if(isset($json_array['Content']))

{

	$content = mysqli_real_escape_string($conn,$json_array['Content']);

}

if(isset($json_array['Operation']))

{

	$operation = $json_array['Operation'];

}

if(isset($json_array['Anonymous']))

{

	$anonymous = $json_array['Anonymous'];

}



if($operation == "New")

{

	$query = "Insert into posts values (NULL,$userid,'$content','$anonymous','$date')";

	$result = mysqli_query($conn,$query);

	if (mysqli_affected_rows($conn) > 0)

	{

		$response["Status"] = 200;

		$response["Message"] = "Post Successfully Posted.";

	}

	else

	{

		$response["Status"] = 400;

		$response["Message"] = "Failed to Post.";

	}

}

else if($postid != "" && $operation == "Update")

{

	$query = "Update posts set Content = '$content', Anonymity = '$anonymous' where Post_id = '$postid'";

	$result = mysqli_query($conn,$query);

	if (mysqli_affected_rows($conn) > 0)

	{

		$response["Status"] = 200;

		$response["Message"] = "Post Successfully Updated.";

	}

	else

	{

		$response["Status"] = 200;

		$response["Message"] = "Post is already up to date.";

	}

}

else if($postid != "" && $operation == "Delete")

{

	$query = "Delete from posts where Post_id = '$postid' and User_id = '$userid'";

	$result = mysqli_query($conn,$query);

	if (mysqli_affected_rows($conn) > 0)

	{

		$response["Status"] = 200;

		$response["Message"] = "Post Successfully Deleted.";

	}

	else

	{

		$response["Status"] = 400;

		$response["Message"] = "Failed to Delete Post.";

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