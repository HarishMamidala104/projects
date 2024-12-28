$(document).ready(function() {
	console.log("ready!");
	//$('#p1win').hide();
	
	$('.summernote').summernote({		
		height: 400
	});
	$(".summernote").summernote("foreColor", "blue"); 
	$('.summernote').summernote('justifyLeft');
	
	document.getElementById('rrf').focus();

	
	genFormData();

	$('#rrf').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		// Get form
		var form = $('#rrf')[0];
		// FormData object 
		var data = new FormData(form);
		// If you want to add an extra field for the FormData
		//data.append("CustomField", "This is some extra data, testing");
		// disabled the submit button
		$("#pub-1").prop("disabled", true);

		$.ajax({

			type: "POST",
			enctype: 'multipart/form-data',
			url: '/hiringold/api/postRRF',
			data: data,
			dataType: 'json',
			processData: false,
			contentType: false,
			cache: false,
			timeout: 800000,
			success: function(data) {
				console.log("SUCCESS : ", data.length);
				console.log("SUCCESS : ", data);
				$("#pub-1").prop("disabled", false);
				var res = JSON.parse(JSON.stringify(data));
				console.log("-------" + res.id);
				if (res.id > 0) {
					$("#notifications").text("Created RRF");
				}
			},

			error: function(e) {
				console.log("ERROR : ", e.responseText);
				$("#pub-1").prop("disabled", false);
			}
		});


		$.ajax({
			url: '/hiringold/api/postRRF',
			type: 'post',
			cache: false,
			processData: false,
			contentType: false,
			dataType: 'json',
			data: $('#rrf').serialize(),
			success: function(data) {
				console.log(data);
			}
		});
	});

});

function newRRF(note) {
	console.log('New RRF...');
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		$('#p1win').show();
		document.getElementById("searchForm").reset();

		var currentdate = new Date();
		var fn = '' + currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + currentdate.getHours() + currentdate.getMinutes() + currentdate.getSeconds();
		$('#rrfId').val(fn);



	});
}


function randomInteger(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function genFormData() {
	var currentdate = new Date();
	var fn = currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + currentdate.getHours() + currentdate.getMinutes() + currentdate.getSeconds();
	$('#rrfId').val(0);
	$('#location').val('Hyderabad');
	$('#minE').val(randomInteger(0, 3));
	$('#maxE').val(randomInteger(4, 10));
	$('#budget').val(randomInteger(360000, 1000000));
	$('#imode').val('Some random Mode' + fn);
	const ps = ["Java", "DotNet", "PHP", "Python", "DevOps", "Fullstack", "MySql"];
	const random1 = Math.floor(Math.random() * ps.length);
	const sec = ["Angular", "ReactJS", "Postgres", "Node.js", "Docker", "AWS", "Jenkins"];
	const random2 = Math.floor(Math.random() * sec.length);
	$('#ps').text(ps[random1]);
	$('#ss').text(sec[random2]);
	const random3 = randomInteger(360000, 1000000);
	$('#jd').summernote('editor.pasteHTML', '<b><font color="green">Job Description for ' + ps[random1] + ' and ' + sec[random2] + '</font></b>');
  	//$('#jd').summernote("insertImage", 'http://192.168.3.240:8084/QuestyTasks/2.png', random3+'.png');
	//$('#rrqDoc').val("d:/sam.pdf");

}