(function(){
	window.onload = function() {
		initStyle();
		distanceGraph();
		//speedGraph();
	}

	function initStyle () {
		$('#loader').hide();
		$('#left_arrow').hover(function(){
			$('#ride_date').css('margin-left', '7.9vw');
		}, function() {
			$('#ride_date').css('margin-left', '7.7vw');
		});
		$('.navbar_logo_style').hover(function(){
			$('#navbar_logo_cloud').css('text-shadow', '0px 3px 6px black');
			$('#navbar_logo_bike').css('text-shadow', '0px 3px 6px black');
		}, function() {
			$('#navbar_logo_cloud').css('text-shadow', '');
			$('#navbar_logo_bike').css('text-shadow', '');
		});
	}

	function distanceGraph () {
		$(function () {
		    $('#container2').highcharts({
		        title: {
		            text: 'RPM',
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
		                text: 'RPM'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#9b59b6'
		            }]
		        },
		        tooltip: {
		            valueSuffix: 'rpm'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'User',
		            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
		        }]
		    });
		});
	}

	function speedGraph (speedArr) {
		$(function () {
		    $('#container').highcharts({
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
		                text: 'Speed (meters/second)'
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
		            name: 'User',
		            data: speedArr
		        }]
		    });
		});
	}
	
	// (function ($) {
    
	//     $.fn.CountUpCircle = function(options){

	//     	var self = this;
		
	// 	    /**
	// 	    * DEFAULT OPTIONS
	// 	    *
	// 	    * Description
	// 	    *
	// 	    * @param 
	// 	    **/

	// 		var settings = $.extend({
	// 			duration: 5000, //ms
	// 			opacity_anim: false,
	// 			step_divider: 1
	// 		}, options);

	// 		var toCount = parseInt(this.html());
	// 		console.log("Count up to: " + toCount);

	// 		var i 	 		 = 0;
	// 		var step 		 = settings.duration / (toCount / settings.step_divider);
	// 		var procent_step = 1/(toCount / settings.step_divider);
	// 		console.log("Step duration: " + step+"ms");

	// 		var displayNumber = function() {
	// 			i=i+settings.step_divider;
	// 			self.html(i);
	// 			if (settings.opacity_anim){
	// 				console.log("animate opacity");
	// 				self.css({'opacity':procent_step*i});
	// 			}
	// 			if (i < toCount - settings.step_divider) {
	// 				setTimeout(displayNumber, step);
	// 			}
	// 			else{
	// 				setTimeout(set_endpoint, step);
	// 			}
	// 		};
			
	// 		var set_endpoint = function (){
	// 			self.html(toCount);
	// 		}

	// 		displayNumber();
	// 	}

	// }

})();
