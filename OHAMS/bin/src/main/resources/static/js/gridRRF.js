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
			"url": "/hiringold/api/getAllRRFDetails/",
			"type": "get",
			"dataType": 'json',
			"dataSrc": function(json) {
				console.log(json);
				for (var i = 0; i < json.data.length; i++) {
					//console.log(json.data[i]);
					try {
						var id = json.data[i].id;						
						//json.data[i].id = '<a title="Report" target="_blank" href="/hiringold/search?intId=' + json.data[i].id + '">'+json.data[i].id+'</a>';
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
			{ "data": "jobDescription" },
			{ "data": "location" },		
			{ "data": "publishedOn" },
			{ "data": "rrfId" }

		]
	});
});