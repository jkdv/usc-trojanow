<?php 
$username="root";
$password="";
$database="trojanow";
$host = "localhost";
$conn = mysqli_connect($host,$username,$password,$database);

if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
?>