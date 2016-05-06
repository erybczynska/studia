<?php
define('KLUCZ',15121995);
include 'head.php';
echo '<div class="container">';
include 'menu.php';
echo '<div class="wrapper">';
run();
echo '</div><div style="clear: both"></div></div></body></html>';

	function run() {
		if ($_SERVER ['REQUEST_METHOD'] == 'POST') {
			$commentType = $_POST ['commentType'];
			$commentText = $_POST ['commentText'];
			$userName = $_POST ['userName'];
		
			if (isset($_POST ['blogName']))
				$blogName = $_POST ['blogName'];
				else {
					echo "Nie można dodać komentarza";
					return;
				}
		
				if (isset($_POST ['postName']))
					$postName = $_POST ['postName'];
					else {
						echo "Nie można dodać komentarza";
						return;
					}
		
					$commentName = $postName . ".k";
	
					$path = $blogName . "/" . $commentName;
					$sem=sem_get(KLUCZ);
					sem_acquire($sem);
					if(isCommentTypeCorrect($commentType) && isCommentTextCorrect($commentText)
							&& isUserNameCorrect($userName)) {
								if (! file_exists( $path ))
									makeCommentsDir ( $path );
		
									makeNewComment($commentType, $commentText, $userName, $path, $blogName);
							}
							else {
								echo "Błędnie wypełniony formularz komentarza.";
							}
					sem_release($sem);					
		}
	}
	
	function makeCommentsDir($path) {
		mkdir($path, 0777);
	}
	
	function makeNewComment($commentType, $commentText, $userName, $path, $blogName) {
		$files = scandir($path);
		$countFiles = count($files);
		
		if ($countFiles != 2) {
			$max = -1; 
			foreach ( $files as $comment ) {
				if ($comment >= $max)
					$max = $comment + 1;
			}
		}
		else {
			$max = 0;
		}
		
		$commentFile = fopen($path."/".$max, 'w');
		fwrite($commentFile, $commentType."\n");
		fwrite($commentFile, makeDate().", ".makeTime()."\n");
		fwrite($commentFile, $userName."\n");
		fwrite($commentFile, $commentText);
		
		fclose($commentFile);
		
		echo 'Komentarz został dodany! <br>';
		echo ('<a href="blog.php?nazwa='.$blogName.'" >Wróć do bloga</a>'); 	
		
	}
	
	function isCommentTypeCorrect($commentType) { 
		if ($commentType == "pozytywny" || $commentType == "negatywny" || $commentType == "neutralny") 
			return true;
		return false; 
	}
	
	function isCommentTextCorrect($commentText) {
		if (strlen($commentText) == 0 )
			return false; 
		return true;
	}
	
	function isUserNameCorrect($userName) {
		if (strlen($userName) == 0 )
			return false;
			return true;
	}

	function makeTime() {
		date_default_timezone_set ( "Europe/Warsaw" );
		$currentTime = getdate ();
		$hours = $currentTime ['hours'];
		if ($hours < 10)
			$hours = "0".$hours;
		$minutes = $currentTime ['minutes'];
		if ($minutes < 10)
			$minutes = "0".$minutes;
		$seconds = $currentTime ['seconds'];
		if ($seconds < 10)
			$seconds = "0".$seconds;
		if ($seconds == 0)
			$seconds = "00";
		
		return $hours.":".$minutes.":".$seconds;
	}
	
	function makeDate() {
		date_default_timezone_set ( "Europe/Warsaw" );
		$currentTime = getdate ();
		$day = $currentTime ['mday'];
		if ($day < 10) 
			$day = "0".$day;
		$month = $currentTime ['mon'];
		if ($month <10)
			$month = "0".$month;
		$year = $currentTime ['year'];
	
		return $year.".".$month.".".$day;
	}
		

?>