let chart = null;

$(document).ready(function() {
	console.log("ready!");
	var key = $('#key').val();
	var option = $('#option').val();
	if (option === '1') {
		getInterviewDetails(key, 'notifications0');
	}
	if (option === '2') {
		getCandidateDetails(key, 'notifications0');
	}

});

function random_rgba() {
	var o = Math.round, r = Math.random, s = 255;
	return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + r().toFixed(1) + ')';
}

function getCandidateDetails(id, note) {
	var responseCount = 0;
	candidateName = [];
	head = [];
	intCount = [];
	intId = [];
	pubDates = [];
	bgColor = [];
	bgColor2 = [];

	$.ajax({
		url: "/hiringold/api/getCandidateAnalytics/" + id,
		type: "GET",
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
							intId.push(data[x][i][j]);
						}
						if (j == 1) {
							intCount.push(data[x][i][j]);
							bgColor.push(random_rgba());
							bgColor2.push(random_rgba());
						}
						if (j == 2) {
							candidateName.push(data[x][i][j] + ' Overall Interview Profiles');
						}
						if (j == 3) {
							pubDates.push(data[x][i][j]);
							head.push(data[x][i][j]);
						}
					}
					console.log('------------------------------');
				}
			}

			//renderring chart
			chart = new Chart('myChart', {
				type: 'bar',
				data: {
					labels: pubDates,
					datasets: [{
						backgroundColor: bgColor,
						barPercentage: 0.2,
						borderColor: bgColor2,
						data: intCount
					}]
				},
				options: {
					legend: false,
					tooltip: false,
					scales: {
						xAxes: [{
							
						}],
						yAxes: [{
							ticks: {
								beginAtZero: true,
								max: 6,
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
								return value < 2 ? '#ff2020'
									: value < 4 ? '#223388'
										: '#22cc22'
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


function getInterviewDetails(id, note) {
	var responseCount = 0;
	head = [];
	vals = [];
	title = [];
	company = [];
	sch_date = [];
	bgColor = [];
	bgColor2 = [];
	console.log("/hiringold/api/getHiresInfoByTokenOpt/" + id);
	$.ajax({
		url: "/hiringold/api/getHiresInfoByTokenOpt/" + id,
		type: "GET",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		contentType: "application/json; charset=utf-8",
		success: function(json, textStatus, xhr) {
			var response = JSON.parse(json);
			console.log(json);
			console.log(response.length);
			if (response.length > 0) {
				for (var count = 0; count < response.length; count++) {
					head.push(response[count].role_description);
					vals.push(response[count].rating);
					title.push(response[count].candidate);
					company.push(response[count].company);
					const d = new Date(response[count].published_on);
					sch_date.push((response[count].published_on));
					bgColor.push(random_rgba());
					bgColor2.push(random_rgba());
				}
			}

			//$("#title").text('Interview Rating Results Of ' + title[0] + ' From ' + company[0] + ' scheduled on ' + sch_date[0]);
			//renderring chart
			//renderring chart
			chart = new Chart('myChart', {
				type: 'bar',
				data: {
					labels: head,
					datasets: [{
						label: 'Rating:',
						barPercentage: 0.2,
						backgroundColor: bgColor,
						borderColor: bgColor2,
						data: vals
					}]
				},
				options: {
					legend: false,
					tooltip: false,
					scales: {
						xAxes: [{
							
						}],
						yAxes: [{
							ticks: {
								beginAtZero: true,
								max: 5,
								min: 0,
								userCallback: function(label, index, labels) {
									// when the floored value is the same as the value we have a whole number
									if (Math.floor(label) === label) {
										return label;
									}

								},
							}
						}]
					}, responsive: true,

					title: {

						display: true,

						text: 'Interview Rating Results Of ' + title[0] + ' From ' + company[0] + ' scheduled on ' + sch_date[0]

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
								return value < 2 ? '#ff2020'
									: value < 4 ? '#223388'
										: '#22cc22'
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