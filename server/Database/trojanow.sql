-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2015 at 02:54 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `trojanow`
--

-- --------------------------------------------------------

--
-- Table structure for table `chats`
--

CREATE TABLE IF NOT EXISTS `chats` (
`Chat_id` int(10) NOT NULL,
  `User_id_one` int(10) NOT NULL,
  `User_id_two` int(10) NOT NULL,
  `Content` text,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=71 ;

--
-- Dumping data for table `chats`
--

INSERT INTO `chats` (`Chat_id`, `User_id_one`, `User_id_two`, `Content`, `Created_Date`) VALUES
(47, 59, 60, 'Brand new', '2015-04-15 15:11:02'),
(48, 59, 60, 'Delete works great', '2015-04-15 15:11:25'),
(49, 60, 59, 'I think it does work', '2015-04-15 15:11:41'),
(50, 59, 60, 'I think that too', '2015-04-15 15:11:59'),
(63, 59, 60, 'Yes it works great', '2015-04-15 16:28:26');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
`Post_id` int(10) NOT NULL,
  `User_id` int(10) NOT NULL,
  `Content` varchar(255) DEFAULT NULL,
  `Anonymity` varchar(10) NOT NULL DEFAULT 'False',
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=58 ;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`Post_id`, `User_id`, `Content`, `Anonymity`, `Created_Date`) VALUES
(52, 60, 'Brand new post.', 'False', '2015-04-13 22:40:21'),
(53, 60, 'Second Brand new post.', 'False', '2015-04-13 22:41:15'),
(54, 60, 'Check for time elapsed Heetae :D.', 'False', '2015-04-13 22:46:46'),
(55, 61, 'Thanks bro! This is great!', 'False', '2015-04-14 20:33:08'),
(56, 59, 'Wassp?', 'False', '2015-04-15 17:58:37'),
(57, 59, 'New', 'False', '2015-04-17 01:42:50');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`User_id` int(10) NOT NULL,
  `First_name` varchar(255) DEFAULT NULL,
  `Last_name` varchar(255) DEFAULT NULL,
  `Username` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Contact` varchar(255) DEFAULT NULL,
  `Online` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=64 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`User_id`, `First_name`, `Last_name`, `Username`, `Password`, `Email`, `Contact`, `Online`) VALUES
(58, 'Karan121', 'Kapoor23', 'kp1', '123', 'kp@jp.com', '213224351', 1),
(59, 'Machael', 'Kors', 'testuser1', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', 'a@a.com', '1231231231', 0),
(60, 'Kunal', 'Parakh', 'kp', '123456', 'kparakh@usc.edu', '1234567890', 1),
(61, 'Karan', 'Kapoor', 'jn', '123456', 'j1n@gmail.com', '2132252454', 1),
(62, 'Jason', 'Statum', 'js', '123456', 'jason@gmail.com', '1234567890', 1),
(63, 'Jason', 'Statum', 'sj', '123456', 'jn@gmail.com', '1234567890', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chats`
--
ALTER TABLE `chats`
 ADD PRIMARY KEY (`Chat_id`), ADD KEY `User_id_one` (`User_id_one`), ADD KEY `User_id_two` (`User_id_two`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
 ADD PRIMARY KEY (`Post_id`), ADD KEY `User_id` (`User_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`User_id`), ADD UNIQUE KEY `Username` (`Username`), ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chats`
--
ALTER TABLE `chats`
MODIFY `Chat_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=71;
--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
MODIFY `Post_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=58;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `User_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=64;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `chats`
--
ALTER TABLE `chats`
ADD CONSTRAINT `chats_ibfk_1` FOREIGN KEY (`User_id_one`) REFERENCES `users` (`User_id`),
ADD CONSTRAINT `chats_ibfk_2` FOREIGN KEY (`User_id_two`) REFERENCES `users` (`User_id`);

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `users` (`User_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
