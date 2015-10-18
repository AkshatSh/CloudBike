var BACKEND_HOST = "http://cloudbike-backend.azurewebsites.net/api"; //http://173.250.163.33:8080/api
var MENU_ENDPOINT = "/routes";
var ROUTE_ENDPOINT = "/route";

function getMenuItems(callback) {
	var ajax = new XMLHttpRequest();
	ajax.onload = callback;
    
	ajax.open("GET", BACKEND_HOST + MENU_ENDPOINT, true);
	ajax.send();
}

function getMenuData() {
    getMenuItems(function () {
	var data = JSON.parse(this.responseText);
	console.log(data);
	});
}

function getRouteData(routeid) {
    getRoute(routeid, function() {
	    var data = JSON.parse(this.responseText);
	    console.log(data);
	});
}

function getRoute(routeId, callback) {
	var ajax = new XMLHttpRequest();
	ajax.onload = callback;
	ajax.open("GET", BACKEND_HOST + ROUTE_ENDPOINT + "/" + routeId, true);
	ajax.send();
}