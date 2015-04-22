<?php
/*
Description - Calculate time elapsed after the post is posted
Created By - Kunal Parakh
Application - TrojaNow
*/


date_default_timezone_set('America/Los_Angeles');

function time_elapsed ($time)

{

	require 'connection.php';

	$query = "SELECT NOW() - INTERVAL 12 HOUR";

	$result = mysqli_query($conn,$query);

	$record = mysqli_fetch_row($result);


    $time = strtotime($record[0]) - $time;



    $tokens = array (

        31536000 => 'year',

        2592000 => 'month',

        604800 => 'week',

        86400 => 'day',

        3600 => 'hour',

        60 => 'minute',

        1 => 'second'

    );



    foreach ($tokens as $unit => $text) {

        if ($time < $unit) continue;

        $numberOfUnits = floor($time / $unit);

        return $numberOfUnits.' '.$text.(($numberOfUnits>1)?'s '.'ago':' ago');

    }

}

?>