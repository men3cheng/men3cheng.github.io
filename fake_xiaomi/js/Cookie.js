function setCookie(name, value, day) {
	var date = new Date();
	date.setDate(date.getDate() + day);
	document.cookie = name + '=' + value + ';expires=' + date;
};

function getCookie(name) {
	var arr = document.cookie.split('; ');
	for (var i = 0; i < arr.length; i++) {
		var arr1 = arr[i].split('=');
		if (arr1[0] == name) {
			return arr1[1];
		};
	};
	return '';
};

function deleteCookie(name) {
	setCookie(name, '0', -1);
}