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
		$blogName = $_POST ['blogName'];
		$loginForm = $_POST ['userName'];
		$passwordForm = $_POST ['userPassword'];
		$sem=sem_get(KLUCZ);
		sem_acquire($sem);
		if (isLoginLongEnough ( $loginForm ) && isLoginNotTaken ( $loginForm )
				&& isPasswordLongEnough ( $passwordForm )) {
					$path = $blogName;
					if (! is_dir ( $path )) {
						makeBlog ( $path );
					} else {
						echo "Blog o takiej nazwie już niestety istnieje";
					}
			sem_release($sem);		
		}
	}
}

function isLoginLongEnough($login) {
	if (strlen ( $login ) > 3)
		return true;
	else {
		echo "Login ma za mało znaków!";
		return;
	}
}
function isPasswordLongEnough($password) {
	if (strlen ( $password ) > 5)
		return true;
	else {
		echo "Hasło ma za mało znaków!";
		return false;
	}
}
function getLogin($line) {
	return substr ( $line, 0, strlen ( $line ) - 1 );
}
function isLoginNotTaken($loginToCheck) {
	$blogsDirectories = glob ( '*', GLOB_ONLYDIR );
	foreach ( $blogsDirectories as $blog ) {
		$blogInfo = fopen ( $blog . '/info.txt', "r" );
		$login = getLogin ( fgets ( $blogInfo ) );
		fclose ( $blogInfo );
		if ($login == $loginToCheck) {
			echo "Login jest już niestety zajęty!";
			return false;
		}
	}
	return true;
}

function makeBlog($path) {
	mkdir ( $path, 0755, true );
	$info = fopen ( $path . "/info.txt", "w" );
	
	$bloggerName = $_POST ["userName"] . "\n";
	fwrite ( $info, $bloggerName );
	
	$bloggerPassword = $_POST ["userPassword"];
	$bloggerPasswordEncode = md5 ( $bloggerPassword );
	fwrite ( $info, $bloggerPasswordEncode . "\n" );
	
	fwrite ( $info, $_POST ["blogDescription"] );
	
	fclose ( $info );
	chmod ( $path . "/info.txt", 0755 );
	chmod ( $path, 0755 );
	
	echo "Stworzyłeś nowego bloga!";
}
?>