let chart = null;

$(document).ready(function() {
	console.log("ready!");


});

function random_rgba() {
	var o = Math.round, r = Math.random, s = 255;
	return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + r().toFixed(1) + ')';
}

function generateChart(note) {
	$('#chartForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
	}
	);

	var chartType = $('#chartType :selected').text();
	var start_date = $('#start_date').val();
	var end_date = $('#end_date').val();

	apiUrl = '';
	if (chartType === 'Company Wise Opportunities') {

		apiUrl = '/hiringold/api/getOptimizedAnalytics';

		jsdata = JSON.stringify(
			{
				"chartType": chartType,
				"start_date": start_date,
				"end_date": end_date,
			}
		);

		var msg = chartType + ' ';
		if (start_date.trim().length > 0) {
			msg += ' ' + start_date;
		}
		if (end_date.trim().length > 0) {
			msg += ' to  ' + end_date;
		}
		resetCanvas();

		var responseCount = 0;
		company = [];
		intCount = [];
		pubDates = [];
		bgColor = [];
		bgColor2 = [];

		$.ajax({
			url: apiUrl,
			type: "POST",
			data: jsdata,
			dataType: 'json',
			async: true, // set to false if you don't mind the page pausing while waiting for response
			cache: false,
			contentType: "application/json; charset=utf-8",
			success: function(data, textStatus, xhr) {
				console.log("server response data is---" + JSON.stringify(data));
				var res = null;
				console.log(JSON.parse(JSON.stringify(data)).length);
				for (let x in data) {
					responseCount = data[x].length;
					console.log(x + ":: " + data[x].length)
					for (let i = 0; i < data[x].length; i++) {
						for (let j = 0; j < data[x][i].length; j++) {
							console.log(data[x][i][j]);
							if (j == 0) {
								company.push(data[x][i][j]);
							}
							if (j == 1) {
								intCount.push(data[x][i][j]);
								bgColor.push(random_rgba());
								bgColor2.push(random_rgba());
								if(data[x][i][j]>responseCount){
									responseCount = data[x][i][j];
								}
							}
							if (j == 2) {
								pubDates.push(data[x][i][j]);
							}

						}
						console.log('------------------------------');
					}
				}
				
				responseCount = responseCount+10;

				//renderring chart
				chart = new Chart('myChart', {
					type: 'bar',
					data: {
						labels: company,
						datasets: [{
							barPercentage: 0.2,
							backgroundColor: bgColor,
							borderColor: bgColor2,
							data: intCount
						}]
					},
					options: {
						responsive: true,
						legend: false,
						tooltip: false,
						title: {
							display: true,
							text: msg
						},
						scales: {
							xAxes: [{

							}],
							yAxes: [{
								ticks: {
									beginAtZero: true,
									max: responseCount,
									min: 0,
									userCallback: function(label, index, labels) {
										// when the floored value is the same as the value we have a whole number
										if (Math.floor(label) === label) {
											return label;
										}

									},
								}
							}]
						},
						plugins: {
							datalabels: {
								align: 'end',
								anchor: 'end',
								backgroundColor: null,
								borderColor: null,
								borderRadius: 4,
								borderWidth: 1,
								color: function(context) {
									var value = context.dataset.data[context.dataIndex];
									return value;
								},
								font: {
									size: 11,
									weight: 200
								},
								offset: 4,
								padding: 0,
								formatter: function(value) {
									//return value < 2 ? "NS"	: value < 4 ? "NS": "S";
									return value;
								}
							}
						}
					}
				});

			},
			error: function(data, textStatus, xhr) {
				res = JSON.parse(JSON.stringify(data));
				document.getElementById(note).innerHTML += res.responseText;
				/*
				for (let x in res) {
					console.log(x);				
				}*/
			}
		});
	}

	if (chartType === 'Company Wise Opportunities/Selections') {
		apiUrl = '/hiringold/api/getOptimizedAnalytics';

		jsdata = JSON.stringify(
			{
				"chartType": chartType,
				"start_date": start_date,
				"end_date": end_date,
			}
		);

		var msg = chartType + ' ';
		if (start_date.trim().length > 0) {
			msg += ' ' + start_date;
		}
		if (end_date.trim().length > 0) {
			msg += ' to  ' + end_date;
		}
		resetCanvas();


		var responseCount = 0;
		company = [];
		intCount = [];
		selCount = [];
		bgColor = [];
		bgColor2 = [];
		bgColor3 = [];
		bgColor4 = [];

		$.ajax({
			url: apiUrl,
			type: "POST",
			data: jsdata,
			dataType: 'json',
			async: true, // set to false if you don't mind the page pausing while waiting for response
			cache: false,
			contentType: "application/json; charset=utf-8",
			success: function(data, textStatus, xhr) {
				console.log("server response data is---" + JSON.stringify(data));
				var res = null;
				console.log(JSON.parse(JSON.stringify(data)).length);
				for (let x in data) {
					//responseCount = data[x].length;
					console.log(x + ":: " + data[x].length)
					for (let i = 0; i < data[x].length; i++) {
						for (let j = 0; j < data[x][i].length; j++) {
							console.log(data[x][i][j]);
							if (j == 0) {
								company.push(data[x][i][j]);
							}
							if (j == 1) {
								intCount.push(data[x][i][j]);
								bgColor.push('rgba(0,0,255,1)');
								bgColor2.push(random_rgba());
							}
							if (j == 2) {
								selCount.push(data[x][i][j]);
								bgColor3.push('rgba(0,255,0,1)');
								bgColor4.push(random_rgba());
								
								if(data[x][i][j]+data[x][i][j-1]>responseCount){
									responseCount = data[x][i][j]+data[x][i][j-1];
								}
							}

						}
						console.log('------------------------------');
					}
				}
				
				responseCount = responseCount+10;

				//renderring chart
				chart = new Chart('myChart', {
					type: 'bar',
					data: {
						labels: company,
						datasets: [{
							label: "Opportunities",
							barPercentage: 0.2,
							backgroundColor: "rgba(0,0,255,1)",
							borderColor: "rgba(179,181,198,1)",
							pointBackgroundColor: "rgba(179,181,198,1)",
							pointBorderColor: "#fff",
							pointHoverBackgroundColor: "#fff",
							pointHoverBorderColor: "rgba(179,181,198,1)",
							data: intCount
						}, {
							label: "Selected",
							barPercentage: 0.2,
							backgroundColor: "rgba(0,255,0,1)",
							borderColor: "rgba(255,99,132,1)",
							pointBackgroundColor: "rgba(255,99,132,1)",
							pointBorderColor: "#fff",
							pointHoverBackgroundColor: "#fff",
							pointHoverBorderColor: "rgba(255,99,132,1)",
							data: selCount
						}
						]
					},
					options: {
						responsive: true,
						legend: false,
						tooltip: false,
						title: {
							display: true,
							text: msg
						},
						scales: {
							xAxes: [{
								stacked: true
							}],
							yAxes: [{
								stacked: true,
								ticks: {
									beginAtZero: true,
									max: 100,
									min: 0,
									userCallback: function(label, index, labels) {
										// when the floored value is the same as the value we have a whole number
										if (Math.floor(label) === label) {
											return label;
										}

									},
								}
							}]
						},
						plugins: {
							datalabels: {
								align: 'end',
								anchor: 'end',
								backgroundColor: null,
								borderColor: null,
								borderRadius: 4,
								borderWidth: 1,
								color: function(context) {
									var value = context.dataset.data[context.dataIndex];
									return value;
								},
								font: {
									size: 11,
									weight: 200
								},
								offset: 15,
								padding: 0,
								formatter: function(value) {
									//return value < 2 ? "NS"	: value < 4 ? "NS": "S";
									return value;
								}
							}
						}
					}
				});



			},
			error: function(data, textStatus, xhr) {
				res = JSON.parse(JSON.stringify(data));
				document.getElementById(note).innerHTML += res.responseText;
				/*
				for (let x in res) {
					console.log(x);				
				}*/
			}
		});
	}

	if (chartType === 'Week Wise Opportunities') {
		apiUrl = '/hiringold/api/getOptimizedAnalytics';

		jsdata = JSON.stringify(
			{
				"chartType": chartType,
				"start_date": start_date,
				"end_date": end_date,
			}
		);

		var msg = chartType + ' ';
		if (start_date.trim().length > 0) {
			msg += ' ' + start_date;
		}
		if (end_date.trim().length > 0) {
			msg += ' to  ' + end_date;
		}
		resetCanvas();


		var responseCount = 0;
		weekNum = [];
		intCount = [];
		selCount = [];

		bgColor = [];
		bgColor2 = [];
		bgColor3 = [];
		bgColor4 = [];

		$.ajax({
			url: apiUrl,
			type: "POST",
			data: jsdata,
			dataType: 'json',
			async: true, // set to false if you don't mind the page pausing while waiting for response
			cache: false,
			contentType: "application/json; charset=utf-8",
			success: function(data, textStatus, xhr) {
				console.log("server response data is---" + JSON.stringify(data));
				var res = null;
				console.log(JSON.parse(JSON.stringify(data)).length);
				for (let x in data) {
					console.log(x + ":: " + data[x].length)
					for (let i = 0; i < data[x].length; i++) {
						for (let j = 0; j < data[x][i].length; j++) {
							console.log(data[x][i][j]);
							if (j == 0) {
								weekNum.push("Week:" + data[x][i][j]);
							}
							if (j == 1) {
								temp = data[x][i][j];
								if (temp > responseCount) {
									responseCount = temp;
								}
								intCount.push(temp);
								bgColor.push('rgba(0,0,255,1)');
								bgColor2.push(random_rgba());
							}
							if (j == 2) {
								selCount.push(data[x][i][j - 1] - data[x][i][j]);
								bgColor3.push('rgba(0,255,0,1)');
								bgColor4.push(random_rgba());
							}

						}
						console.log('------------------------------');
					}
				}

				//renderring chart
				chart = new Chart('myChart', {
					type: 'bar',
					data: {
						labels: weekNum,
						datasets: [{
							label: "Interviewed",
							barPercentage: 0.2,
							backgroundColor: "rgba(0,0,255,1)",
							borderColor: "rgba(179,181,198,1)",
							pointBackgroundColor: "rgba(179,181,198,1)",
							pointBorderColor: "#fff",
							pointHoverBackgroundColor: "#fff",
							pointHoverBorderColor: "rgba(179,181,198,1)",
							data: intCount
						}, {
							label: "Selected",
							barPercentage: 0.2,
							backgroundColor: "rgba(0,255,0,1)",
							borderColor: "rgba(255,99,132,1)",
							pointBackgroundColor: "rgba(255,99,132,1)",
							pointBorderColor: "#fff",
							pointHoverBackgroundColor: "#fff",
							pointHoverBorderColor: "rgba(255,99,132,1)",
							data: selCount
						}
						]
					},
					options: {
						responsive: true,
						legend: false,
						tooltip: false,
						title: {
							display: true,
							text: msg
						},
						scales: {
							xAxes: [{
								stacked: true
							}],
							yAxes: [{
								stacked: true,
								ticks: {
									beginAtZero: true,
									max: responseCount + 30,
									min: 0,
									userCallback: function(label, index, labels) {
										// when the floored value is the same as the value we have a whole number
										if (Math.floor(label) === label) {
											return label;
										}

									},
								}
							}]
						},
						plugins: {
							datalabels: {
								align: 'end',
								anchor: 'end',
								backgroundColor: null,
								borderColor: null,
								borderRadius: 4,
								borderWidth: 1,
								color: function(context) {
									var value = context.dataset.data[context.dataIndex];
									return value;
								},
								font: {
									size: 11,
									weight: 200
								},
								offset: 15,
								padding: 0,
								formatter: function(value) {
									//return value < 2 ? "NS"	: value < 4 ? "NS": "S";
									return value;
								}
							}
						}
					}
				});



			},
			error: function(data, textStatus, xhr) {
				res = JSON.parse(JSON.stringify(data));
				document.getElementById(note).innerHTML += res.responseText;
				/*
				for (let x in res) {
					console.log(x);				
				}*/
			}
		});
	}

}



function padTo2Digits(num) {
	return num.toString().padStart(2, '0');
}

function formatDate(date) {
	return (
		[
			padTo2Digits(date.getMonth() + 1),
			padTo2Digits(date.getDate()),
			date.getFullYear(),
		].join('/') +
		' ' +
		[
			padTo2Digits(date.getHours()),
			padTo2Digits(date.getMinutes()),
			padTo2Digits(date.getSeconds()),
		].join(':')
	);
}

function downloadChart() {
	var a = document.createElement('a');
	a.href = chart.toBase64Image();
	var currentdate = new Date();
	var fn = 'Chart_' + $('#title').text() + '_' + currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + currentdate.getHours() + currentdate.getMinutes() + currentdate.getSeconds() + ".png";
	a.download = fn;

	// Trigger the download
	a.click();
}

function getRGB(str) {
	var match = str.match(/rgba?\((\d{1,3}), ?(\d{1,3}), ?(\d{1,3})\)?(?:, ?(\d(?:\.\d?))\))?/);
	return match ? {
		red: match[1],
		green: match[2],
		blue: match[3]
	} : {};
}

var resetCanvas = function() {
	$('#myChart').remove(); // this is my <canvas> element
	$('#results-container').append('<canvas id="myChart"><canvas>');
};

function setTitleToCanvas(title) {
	canvas = document.getElementById('myChart');
	ctx = canvas.getContext('2d');
	ctx.canvas.width = $('#graph').width(); // resize to parent width
	ctx.canvas.height = $('#graph').height(); // resize to parent height
	var x = canvas.width / 2;
	var y = canvas.height / 2;
	ctx.font = '10pt Verdana';
	ctx.textAlign = 'center';
	ctx.fillText(title, x, y);
}