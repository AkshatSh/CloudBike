var BACKEND_HOST = "http://173.250.163.33:8080/api";
var MENU_ENDPOINT = "/routes";
var ROUTE_ENDPOINT = "/route";

function getMenuItems(callback) {
	var ajax = new XMLHttpRequest();
	ajax.onload = callback;
	ajax.open("GET", BACKEND_HOST + MENU_ENDPOINT, true);
	ajax.send();
}

function getRoute(routeId, callBack) {
	var ajax = new XMLHttpRequest();
	ajax.onload = callback;
	ajax.open("GET", BACKEND_HOST + MENU_ENDPOINT + ROUTE_ENDPOINT + "/" + routeId, true);
	ajax.send();
}