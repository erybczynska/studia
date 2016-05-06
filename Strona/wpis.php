<?php
define('KLUCZ',15121995);
include 'head.php';
echo '<div class="container">';
include 'menu.php';
echo '<div class="wrapper">';
run();
echo '</div><div style="clear: both"></div></div></body></html>';


function run() {
	$blogsDirectories = glob ( '*', GLOB_ONLYDIR );
	
	if ($_SERVER ['REQUEST_METHOD'] == 'POST') {
		$loginForm = $_POST ['login'];
		$passwordForm = $_POST ['password'];
		$entryForm = $_POST ['entry'];
		$dateForm = $_POST ['date'];
		$timeForm = $_POST ['time'];

		$sem=sem_get(KLUCZ);
		sem_acquire($sem);
		foreach ( $blogsDirectories as $blog ) {
			$blogInfo = fopen ( $blog . '/info.txt', "r" );
	
			$login = getLogin ( fgets ( $blogInfo ) );
			$password = getPassword ( fgets ( $blogInfo ) );
			fclose ( $blogInfo );
	
			if (isLoginCorrect ( $login, $loginForm ) && isPasswordCorrect ( $password, $passwordForm )) {
				$entryName = makeDate ( $dateForm, $timeForm );
				createPost ( $blog, $entryName, $entryForm );
				saveFiles($entryName, $blog);
				echo "Post został dodany!";
			}
		}
		sem_release($sem);
		
	}
}
function getLogin($line) {
	return substr ( $line, 0, strlen ( $line ) - 1 );
}
function getPassword($line) {
	return substr ( $line, 0, strlen ( $line ) - 1 );
}
function isLoginCorrect($login, $loginForm) {
	if ($login == $loginForm) {
		return true;
	} else {
		return false;
	}
}
function isPasswordCorrect($password, $passwordForm) {
	if ($password == md5 ( $passwordForm )) {
		return true;
	} else {
		echo 'Niepoprawne hasło';
		return false;
	}
}
function makeDate($dateForm, $timeForm) {
	$date = str_replace ( "-", "", $dateForm );
	$time = str_replace ( ":", "", $timeForm );
	
	date_default_timezone_set ( "Europe/Warsaw" );
	$currentTime = getdate ();
	$seconds = $currentTime ['seconds'];
	if ($seconds < 10)
		$seconds = '0'.$seconds;
	if ($seconds == 0)
		$seconds = '00';
	
	return $date . $time . $seconds . rand ( 10, 99 );
}
function createPost($blogDir, $nameFile, $text) {
	$newEntry = fopen ( $blogDir . "/" . $nameFile, "w" );
	fwrite ( $newEntry, $text );
	fclose ( $newEntry );
	chmod ( $blogDir . "/" . $nameFile, 0755 );
}

function saveFiles($entryName, $blogName) {
	for ($i = 1; $i < 4; $i++) {
		saveSingleFile($i, $entryName, $blogName);
	}
}
function saveSingleFile($fileNumber, $entryName, $blogName) {
	$fileName = 'file'.$fileNumber;
	
	if(!file_exists($_FILES[$fileName]['tmp_name'])
			|| !is_uploaded_file($_FILES[$fileName]['tmp_name'])) {
		return;
	}
	$ext = pathinfo($_FILES[$fileName]['name'], PATHINFO_EXTENSION);
	$path = $blogName.'/'.$entryName.$fileNumber.'.'.$ext;
	
	move_uploaded_file($_FILES[$fileName]['tmp_name'], $path);
}

?>