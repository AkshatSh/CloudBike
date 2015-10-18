(function(){
	window.onload = function() {
		distanceGraph();
		speedGraph();
		googleMap();
	}

	function distanceGraph () {
		$(function () {
		    $('#container').highcharts({
		        title: {
		            text: 'Distance',
		            x: -20 //center
		        },
		        subtitle: {
		            text: '',
		            x: -20
		        },
		        xAxis: {
		            title: {
		                text: 'Time (minutes)'
		            },
		            categories: ['5', '10', '15', '20', '25', '30',
		                '35', '40', '45', '50', '55', '60']
		        },
		        yAxis: {
		            title: {
		                text: 'Distance Traveled (feet)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#9b59b6'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '°C'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'London',
		            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
		        }]
		    });
		});
	}

	function speedGraph () {
		$(function () {
		    $('#container2').highcharts({
		        title: {
		            text: 'Speed',
		            x: -20 //center
		        },
		        subtitle: {
		            text: '',
		            x: -20
		        },
		        xAxis: {
		            title: {
		                text: 'Time (minutes)'
		            },
		            categories: ['5', '10', '15', '20', '25', '30',
		                '35', '40', '45', '50', '55', '60']
		        },
		        yAxis: {
		            title: {
		                text: 'Speed (feet/second)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '°C'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'Tokyo',
		            data: [17.0, 26.9, 29.5, 24.5, 18.2, 21.5, 25.2, 16.5, 13.3, 18.3, 13.9, 9.6]
		        }]
		    });
		});
	}

	function googleMap() {
		// This example creates a 2-pixel-wide red polyline showing the path of William
		// Kingsford Smith's first trans-Pacific flight between Oakland, CA, and
		// Brisbane, Australia.

		function initMap() {
		  var map = new google.maps.Map(document.getElementById('map'), {
		    zoom: 3,
		    center: {lat: 0, lng: -180},
		    mapTypeId: google.maps.MapTypeId.TERRAIN
		  });

		  var flightPlanCoordinates = [
		    {lat: 37.772, lng: -122.214},
		    {lat: 21.291, lng: -157.821},
		    {lat: -18.142, lng: 178.431},
		    {lat: -27.467, lng: 153.027}
		  ];
		  var flightPath = new google.maps.Polyline({
		    path: flightPlanCoordinates,
		    geodesic: true,
		    strokeColor: '#FF0000',
		    strokeOpacity: 1.0,
		    strokeWeight: 2
		  });

		  flightPath.setMap(map);
		}
	}
	
})();