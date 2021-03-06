<?php 

/*
Description - API to get all the chats from users
Created By - Kunal Parakh
Application - TrojaNow
*/

require 'header.php';

require 'time_elapsed.php';

require 'connection.php';

$data = file_get_contents('php://input');

$json_array = json_decode($data,true);



if(isset($json_array['User_id_from']))

{

	$userid_from = $json_array['User_id_from'];

}

if(isset($json_array['User_id_to']))

{

	$userid_to = $json_array['User_id_to'];

}

if(isset($json_array['Operation']))

{

	$operation = $json_array['Operation'];

}

if(($userid_from != "" || $userid_to != "") && $operation == "Fetch")

{

	$query = "Select * from chats where (User_id_one = '$userid_from' and User_id_two = '$userid_to') OR (User_id_two = '$userid_from' and User_id_one = '$userid_to') ORDER BY Created_Date";

	$result = mysqli_query($conn,$query);

	while ($row = $result->fetch_assoc()) 

	{

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

			$user_id_one = $record['User_id_one'];

			$user_id_two = $record['User_id_two'];

			$query = "Select * from users where User_id = '$user_id_one'";

			$result = mysqli_query($conn,$query);

			$user_one_info = mysqli_fetch_row($result);

			$response["Chat_id"] = $record["Chat_id"];

			$response["User_id_from"] = $record["User_id_one"];

			$response["User_from_Firstname"] = $user_one_info[1];

			$response["User_from_Lastname"] = $user_one_info[2];

			$query = "Select * from users where User_id = '$user_id_two'";

			$result = mysqli_query($conn,$query);

			$user_two_info = mysqli_fetch_row($result);

			$response["User_id_to"] = $record["User_id_two"];

			$response["User_to_Firstname"] = $user_two_info[1];

			$response["User_to_Lastname"] = $user_two_info[2];

			$response["Content"] = $record["Content"];

			$response["Created_Date"] = $record["Created_Date"];

			$response["Time_Elapsed"] = time_elapsed(strtotime($record["Created_Date"]));

		    if($userid_from == $user_id_one)

			{

				$response["Sent_by_me"] = "True";

			}

			else

			{

				$response["Sent_by_me"] = "False";

			}

			$final_response["chats"][] =  $response;	

		}

		echo json_encode($final_response);

	}

}

elseif(($userid_from != "" || $userid_to != "") && $operation == "Delete")

{

	$query = "Delete from chats where (User_id_one = '$userid_from' and User_id_two = '$userid_to') OR (User_id_two = '$userid_from' and User_id_one = '$userid_to')";

	$result = mysqli_query($conn,$query);

	

	if (mysqli_affected_rows($conn) > 0)

	{

		$response["Status"] = 200;

		$response["Message"] = "Chat Successfully Deleted.";

	}

	else

	{

		$response["Status"] = 400;

		$response["Message"] = "Failed to Delete Chat.";

	}

	echo json_encode($response);

}

else

{

	$response["Status"] = 400;

	$response["Message"] = "Error in Processing your Request!!!";

	echo json_encode($response);

}



mysqli_close($conn);

?>