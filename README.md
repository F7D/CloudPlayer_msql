# CloudPlayer_msql
# CloudPlayer
Player of your audio files from clouds
# simple MusicPlayer with Mysql - php - json or api
---------------

Before Anything You need (data) with josn code.
 mysql + php connection 

first Steps :
MySQL creates, configures, and communicates with databases:
CREATE SQL then we need a table ex:
 
--
 Table structure for table `music`
--

CREATE TABLE IF NOT EXISTS `music` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `artist` text CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `picture` text CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `link_128` text CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
 Dumping data for table `music`
--

#NOW INSERT YOUR DATA NAME ,ARTIST,PICTURE LINK AND MP3 LINK . EX:

INSERT INTO `music` (`id`, `name`, `artist`, `picture`, `link_128`) VALUES

(1, 'Bailando', 'enrique ', 'http://exmple.com/pic/1.jpg', 'http://exmple.com/enrique_1.mp3'),

(2, 'Duele el Corazón', 'enrique ', 'http://exmple.com/pic/2.jpg', 'http://exmple.com/enrique_2.mp3'),

(3,...

--------------------------
now we need a php connection.. so Upload php file and set Your mysql informaiton.
ex data output : 

https://vp7.us/apps_/ZdchY/test/2.php

--
-- Good Luck 
--

7201118 at gmail.com .

Farzad.Yousefi at hotmail.com .










#
