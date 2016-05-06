<?php
	include 'head.php';
	echo '<div class="container">';
	include 'menu.php';
?>

		<div class="wrapper">
			<form action="wpis.php" enctype="multipart/form-data" method="post">
				Nazwa użytkownika: <input type="text" name="login"><br>
				Hasło: <input type="password" name="password"><br>
				Wpis: <br>
				<textarea name="entry" style="width: 250px; height: 150px;"></textarea>
			    <br>
			    Data (RRRR-MM-DD) <input type="text" name="date"><br>
			    Data (GG:MM) <input type="text" name="time"><br>
			    <br>
			    Wybierz pliki do dodania: <br>
			    
			    <input type="hidden" name="MAX_FILE_SIZE" value="10000000" />
			    <input type="file" name="file1" > <br>
			    <input type="file" name="file2" > <br>
			    <input type="file" name="file3" > <br>
	
			    <input type="reset">
			   	<input type="submit">
			</form>
		</div>
		<div style="clear: both">
		</div>
		</div>
		</body>
	</html>
