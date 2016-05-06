function setDate() { 
	alert("vfbfgb");
	var date = document.getElementsByName("date");
	var time = document.getElementsByName("time");

	var day = new getDay();
	var month = new getMonth();
	var year = new getFullYear(); 

	if (day < 10)
		day = '0' + day;

	if (month < 10)
		month = '0' + month;

	date.value = year + '-' + month + '-' + day;

	var hour = new getHours();
	var minutes = new getMinutes();

	if (hour < 10)
		hour = '0' + hour;

	if (minutes < 10)
		minutes = '0' + minutes;

	time.value = hour + ':' minutes;
}

window.onload = setDate;

