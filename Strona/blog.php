<?php
define('KLUCZ',15121995);
include 'head.php';
echo '<div class="container">';
include 'menu.php';


echo '<div class="wrapper">';
if (!empty($_GET['nazwa'])) {
	
	$blogName = $_GET ['nazwa'];
	$blogNameDir =  $blogName;
	
	$blogsDirectories = glob ( '*', GLOB_ONLYDIR );
	
	$isBlogFind = false;
	$sem=sem_get(KLUCZ);
	sem_acquire($sem);
	foreach ( $blogsDirectories as $blog ) {
		if ($blog == $blogNameDir) {
			$isBlogFind = true;
			printBlog ( $blog );
		}
	}
	if (!$isBlogFind)
		echo "Blog o podanej nazwie nie istnieje";
	sem_release($sem);
}
else {
	printListOfBlogs();
}


echo '</div><div style="clear: both"></div></div></body></html>';

function printBlog($blogName) {
	printBlogName($blogName);
	printBlogInfo($blogName);
	echo '<hr />';
	printBlogPosts($blogName);
}

function printBlogPosts ($blogName) {
	$files = scandir($blogName);
	foreach ($files as $post) {
		if (strlen($post) == 16) {
			echo '<div class="post">';
			$postToShow = fopen($blogName."/".$post, 'r');
			while(!feof($postToShow)) {
				echo fgets($postToShow)."<br>";
			}
			echo '</div>';
			printPostFiles($blogName, $post);
			echo '<br><br>';
			echo '<div class="dodajKomentarz"><a href="commentForm.php?blogName='.$blogName."&amp;".'postName='.$post.'">Dodaj komentarz</a></div><br>';
			printPostComments($post, $blogName, $post);		
		}
	}
}

function printPostFiles ($blogName, $post) {
	for ($i = 1; $i <= 3; $i = $i + 1) {
			$postFiles =glob("$blogName/$post$i*");
			if (!empty($postFiles[0])) {
				echo '<a href ="'.$postFiles[0].'"target="_blank">'.'Załącznik '.$i.'</a><br>';
			}
		}
}

function printPostComments($postName, $blogName, $post) {
	if (file_exists($blogName."/".$postName.'.k')) {
		$files = scandir($blogName."/".$postName.'.k');
		echo '<div class="komentarze"><b>Komentarze:</b><br><br>';
		foreach ($files as $comment) {
			if (($comment == '.') || ($comment == '..'))
				continue;
			$commentToShow = fopen($blogName."/".$postName.'.k/'.$comment, 'r');
			$commentType = fgets($commentToShow);
			$commentDate = fgets($commentToShow);
			$commentAuthor = fgets($commentToShow);
			
			echo $commentDate.' <b>'.$commentAuthor.'</b> napisał/a '.$commentType.' komentarz:';
			while(!feof($commentToShow)) {
				echo '<div class="komentarz">'.fgets($commentToShow).'<br></div>';
			}
			echo '<br>';
		}
		echo '</div>';
	}
}

function printBlogName($blogName) {
	echo '<div class="blogName">'.$blogName.'<br></div>';
}

function printBlogInfo($blogName) {
	
	$blogInfo = fopen($blogName."/info.txt", 'r'); 
 	fgets($blogInfo);
 	fgets($blogInfo);
 	echo '<div class="blogInfo">';
 	while(!feof($blogInfo)) {
 		echo fgets($blogInfo)."<br>";
 	}
 	echo '</div>';
 	fclose($blogInfo);
 	
}

function printListOfBlogs() {
	$blogsDirectories = glob ( '*', GLOB_ONLYDIR );
	echo '<b style="font-size:125%;">Lista blogów:</b><br><br>';
	foreach ( $blogsDirectories as $blog ) {
		echo ('<a href="blog.php?nazwa='.$blog.'" > '.$blog.'</a>'); 	
		echo "<br>";
	}
}

?>