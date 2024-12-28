$(document).ready(function() {

	/* Datatable access by using table ID */
	$('#example').DataTable({
		scrollY: "300px",
		scrollX: true,
		scrollCollapse: true,
		//autoWidth: true,
		columnDefs: [
			{ width: 20, targets: 0 }
		],
		"info": true,

		/* Set pagination as false or 
		true as per need */
		"paging": true,

		/* Name of the file source 
		for data retrieval */
		"ajax": {
			"url": "/hiringold/api/getAllInterviewDetails/",
			"type": "get",
			"dataType": 'json',
			"dataSrc": function(json) {
				console.log(json);
				for (var i = 0; i < json.data.length; i++) {
					//console.log(json.data[i]);
					try {
						if (json.data[i].id === '') {
							json.data[i].id = 'NA';
						}
						if (json.data[i].author === undefined) {
							json.data[i].author = 'NA';
						}
						if (json.data[i].authorRole === undefined) {
							json.data[i].authorRole = 'NA';
						}
						json.data[i].authorRole = '<a title="Report" target="_blank" href="/hiringold/report/prep?submit=Report&search_box=' + json.data[i].id + '"><img alt="Report" src="img/report.png" /></a>';
						json.data[i].authorRole += '&nbsp;<a title="InterviewAnalytics" target="_blank" href="/hiringold/report/prep?option=1&submit=Analytics&search_box=' + json.data[i].id + '"><img alt="InterviewAnalytics" src="img/analytics1.png"  /></a>';
						json.data[i].authorRole += '&nbsp;<a title="CandidateAnalytics" target="_blank" href="/hiringold/report/prep?option=2&submit=Analytics&search_box=' + json.data[i].hireId + '"><img alt="CandidateAnalytics" src="img/analytics2.png"  /></a>';
												
						json.data[i].hireId = '<a title="Report" target="_blank" href="/hiringold/search?intId=' + json.data[i].id + '">'+json.data[i].hireId+'</a>';
						json.data[i].hireName = '<a title="Report" target="_blank" href="/hiringold/search?intId=' + json.data[i].id + '">'+json.data[i].hireName+'</a>';
						json.data[i].id = '<a title="Report" target="_blank" href="/hiringold/search?intId=' + json.data[i].id + '">'+json.data[i].id+'</a>';
					}
					catch (err) {
						console.log(json.data[i]);
						console.log(err.message);
					}


				}
				return json.data;
			},

		},
		"columns": [

			/* Name of the keys from 
			data file source */
			{ "data": "id" },
			{ "data": "hireId" },
			{ "data": "hireName" },
			//{ "data": "comment" },
			//{ "data": "author" },
			//{ "data": "authorRole" },
			//{ "data": "rating" },
			//{ "data": "publishedOn" },
			{ "data": "interviewScheduledDate" },
			{ "data": "authorRole" }

		]
	});
});