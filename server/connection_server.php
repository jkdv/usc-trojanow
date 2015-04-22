<?php 
$username="a4154624_trojan";
$password="allclear123456";
$database="a4154624_trojan";
$host = "mysql16.000webhost.com";
$conn = mysqli_connect($host,$username,$password,$database);

if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
?>