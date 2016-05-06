<?php
	include 'head.php';
	echo '<div class="container">';
	include 'menu.php';
?>
	<div class="wrapper">
		<form action="koment.php" method="post">
		<?php
		if (!empty($_GET['blogName']) && !empty($_GET['postName'])) {
			echo '<input type="hidden" name="blogName" value="'.$_GET['blogName'].'"/>';
			echo '<input type="hidden" name="postName" value="'.$_GET['postName'].'"/>';
		}
		?>
				
			<select name="commentType">
				<option>pozytywny</option>
				<option>neutralny</option>
				<option>negatywny</option>
			</select>
			<br>
			Komentarz: <br>
			<textarea name="commentText" style="width: 250px; height: 150px;"></textarea>
		    <br>
		    <br>
		    Imię/nazwisko/pseudonim: <input type="text" name="userName"><br>
		    <br>
		    <input type="reset">
		   	<input type="submit">
		</form>
	</div>
	<div style="clear: both">
	</div>
	</div>
	</body>
</html>