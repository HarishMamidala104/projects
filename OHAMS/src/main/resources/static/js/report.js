$(document).ready(function() {
	console.log("ready!");
	populate('notifications0');
});


function printReport() {
	var els = document.getElementsByTagName("table");

	for (var i = 0; i < els.length; i++) {
		var tmp = els[i].getAttribute("class");
		tmp = tmp.replace('table', 'tbl');
		//els[i].setAttribute("class",tmp);
	}

	var currentdate = new Date();
	var fn = 'InterviewID_' + $('#interviewId').val() + '_' + currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + currentdate.getHours() + currentdate.getMinutes() + currentdate.getSeconds() + ".pdf";
	var pdf_content = document.getElementById("pdf_body").innerHTML;

	var pdf = new jsPDF('p', 'pt', 'a4');
	pdf.setFont("Times-Roman");
	pdf.setFontType("normal");
	//pdf.setFontSize(7);

	var options = {
		margin: 1,
		filename: fn,
		image: { type: 'jpeg', quality: 0.98 },
		html2canvas: { scale: 2 },
		jsPDF: pdf

	};
	//html2pdf().from(pdf_content).set(options).toPdf().get('pdf').save()
	html2pdf(pdf_content, options);
	//window.close();
	/*
		var pdf = new jsPDF('p', 'pt', 'letter');
		pdf.setFont("Times-Roman");
		pdf.setFontType("normal");
		pdf.setFontSize(7);
		//pdf.text(20, 20, 'Consulta');
	
		specialElementHandlers = {
			'#bypassme': function(element, renderer) {
				return true
			}
		};
		margins = {
			top: 15,
			bottom: 15,
			left: 15,
			width: 900
		};
		// all coords and widths are in jsPDF instance's declared units
		// 'inches' in this case
	
	
		pdf.fromHTML(
			pdf_content, // HTML string or DOM elem ref.
			margins.left, // x coord
			margins.top, {// y coord
			'width': margins.width, // max width of content on PDF
			'elementHandlers': specialElementHandlers
		},
			function(dispose) {
				// dispose: object with X, Y of the last line add to the PDF 
				//          this allow the insertion of new lines after html
				pdf.save(fn);
			}, margins);
	
	*/
}

function genHeader(source) {
	var srcText = source.value;
	var targetId = source.id + "_head";
	$('#' + targetId).text('Technical interview by ' + srcText + ':');
}

function newProfile(note) {
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		$('#p1win').show();
		$('#p2win').show();
		$('#p3win').show();
		$('#p4win').show();
		$('#p5win').show();
		$('#p6win').show();
	});
	document.getElementById("interviewform").reset();
}
function populate(note) {
	$("#cnp").hide();

	$('#searchForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		$('#p1win').show();
		$('#p2win').show();
		$('#p3win').show();
		$('#p4win').show();
		$('#p5win').show();
		$('#p6win').show();
	});

	int_id = $('#interviewId').val();
	console.log('Processed Interview Id : ' + int_id);
	$.ajax({
		url: "/hiringold/api/interviewdetails/" + int_id,
		type: "GET",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			var res = null;
			for (let x in data) {
				console.log(x + ": " + data[x])
				res = JSON.parse(JSON.stringify(data[x]));
				break;
			}
			//console.log(data["hireId"]);
			const dd = new Date(res.publishedOn);
			$('#p1win_title').append(' on ' + formatDate(dd));
			$('#iHireId').text(res.hireId);
			$('#iHireName').text(res.hireName);
			//console.log(data["hireId"]);
			$('#iHireId').text(res.hireId);
			$('#iHireName').text(res.hireName);
			const d = new Date(res.interviewScheduledDate);
			var pdate = formatDate(d)
			$('#int_sch_date').text(pdate.trim());
			$("#positions").text(res.position.trim());
			$("#hm").text(res.hiringManager.trim());
			$("#approvedBy").text(res.approvedBy.trim());
			$("#tech").text(res.technology.trim());
			$("#ps").text(res.profileSourceAcquisition.trim());
			$("#ie").text(res.hiringType.trim());
			$("#snd").text(res.salaryNegotiation);
			$("#hop").text(res.hiringObjective);
			$('#budget').text(res.budget);
			$('#neg_ctc').text(res.negotiated);
			$('#off_ctc').text(res.offeredCTC);
			getInterviewDetails(int_id, note);

		},
		error: function(data, textStatus, xhr) {
			res = JSON.parse(JSON.stringify(data));
			document.getElementById(note).innerHTML += res.responseText;
		}
	});
	return false;
}

function getSalaryDetails(id, note) {
	$.ajax({
		url: "/hiringold/api/salNegDetailsIntId/" + id,
		type: "GET",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			var res = JSON.parse(JSON.stringify(data));
			/*
			for (let x in data) {
				console.log(x + ": " + data[x]);				
				
			}*/
			console.log(res);
			$('#budget').text(res.budget);
			$('#neg_ctc').text(res.negotiated);
			$('#off_ctc').text(res.offeredCTC);
			getInterviewDetails(id, note);

		},
		error: function(data, textStatus, xhr) {
			res = JSON.parse(JSON.stringify(data));
			console.log("Error:"+res.responseText);
			//document.getElementById(note).innerHTML += res.responseText;
		}
	});
}

function getInterviewDetails(id, note) {
	$('#p1win').hide();
	$('#p2win').hide();
	$('#p3win').hide();
	$('#p4win').hide();
	$('#p5win').hide();
	$('#p6win').hide();

	$.ajax({
		url: "/hiringold/api/getHiresInfoByToken/" + id,
		type: "GET",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			var res = null;
			for (let x in data) {
				console.log(x + ": " + data[x])
				res = JSON.parse(JSON.stringify(data[x]));
				console.log(res);
				if (res.roleDescription === 'Tech-Interview-1') {
					$('#hid-2').text(res.hid);
					$('#tech1_int').text(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#t1feed').text(feed);
					var rating = res.rating;
					$('#t1').text(rating);
					$('#t1-status').text(res.status);

					const d = new Date(res.publishedDate);
					$('#p2win_title').append(' on ' + formatDate(d));

					$('#p1win').show();
					$('#p2win').show();
				}
				if (res.roleDescription === 'Tech-Interview-2') {
					$('#hid-3').text(res.hid);
					$('#tech2_int').text(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#t2feed').text(feed);
					var rating = res.rating;
					$('#t2').text(rating);
					$('#t2-status').text(res.status);

					const d = new Date(res.publishedDate);
					$('#p3win_title').append(' on ' + formatDate(d));

					$('#p3win').show();
				}
				if (res.roleDescription === 'Man-Interview') {
					$('#hid-4').text(res.hid);
					$('#man_int').text(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#manfeed').text(feed);
					var rating = res.rating;
					$('#manRating').text(rating);
					$('#man-status').text(res.status);

					const d = new Date(res.publishedDate);
					$('#p4win_title').append(' on ' + formatDate(d));

					$('#p4win').show();
				}
				if (res.roleDescription === 'hr-Interview') {
					$('#hid-5').text(res.hid);
					$('#hr_int').text(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#hrfeed').text(feed);
					var rating = res.rating;
					$('#hrRating').text(rating);
					$('#hr-status').text(res.status);

					const d = new Date(res.publishedDate);
					$('#p5win_title').append(' on ' + formatDate(d));

					$('#p5win').show();
				}
				if (res.roleDescription === 'Interview-Result') {
					$('#hid-6').text(res.hid);
					$('#finalJudge_int').text(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#finalJudgefeed').text(feed);
					var rating = res.rating;
					$('#finalJudge-status').text(res.status);

					const d = new Date(res.publishedDate);
					$('#p6win_title').append(' on ' + formatDate(d));

					$('#p6win').show();
				}
			}

			// call print function
			//printReport();

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

function phaseOne(note, source, operationType) {
	$('#interviewform').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		//generate comment
		var hiringType = $('#ie :selected').text();
		var hm = $('#hm').val();
		var approvedBy = $('#approvedBy').val();
		var position = $('#positions :selected').text();
		var tech = $('#tech').val();
		var profileSource = $('#ps :selected').text();
		var int_sch_date = $('#int_sch_date').val();

		var snd = $('#snd').val();
		snd = snd.replaceAll(',', '&comma;');
		var hop = $('#hop').val();
		hop = hop.replaceAll(',', '&comma;');

		var comment = "";
		comment += "HiringType : " + hiringType + ",";
		comment += "Interview Scheduled Date(mm/dd/yyyy) : " + int_sch_date + ",";
		comment += "Interview Position : " + position + ",";
		comment += "Technology : " + tech + ",";
		comment += "Hiring Objective Purpose : " + hop + ",";
		comment += "Salary Negotiation Details : " + snd + ",";
		comment += "Hiring Manager : " + hm + ",";
		comment += "Approved By : " + approvedBy + ",";
		comment += "Profile Source Acquired : " + profileSource;

		document.getElementById('comment').value = comment;

		var iHireId = $('#iHireId').val();
		var budget = $('#budget').val();
		var neg_ctc = $('#neg_ctc').val();
		var off_ctc = $('#off_ctc').val();

		if (iHireId !== "") {
		} else {
			document.getElementById(note).innerHTML += 'Get Hiring ID...<br/>';
			return;
		}
		var apiUrl = '';
		var postage = '';
		if (operationType == 1) {
			apiUrl = '/api/interviewDetails';
			postage = 'post';
		}
		if (operationType == 2) {
			apiUrl = '';
			postage = 'put';
		}
		if (operationType == 3) {
			apiUrl = '';
			postage = 'delete';
		}
		if (apiUrl === "") {
			document.getElementById(note).innerHTML += 'No API Found For This Operation...<br/>';
			return;
		}

		var jsdata = null;

		document.getElementById(note).innerHTML = "";
		var formdata = $('#interviewform').serializeArray();
		console.log('formdata---' + JSON.stringify(formdata));
		console.log('formdata---' + apiUrl);
		var jsonData = { "data": formdata };
		$.ajax({
			url: apiUrl,
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(jsonData),
			dataType: 'json',
			cache: false,
			timeout: 600000,
			success: function(data) {
				var len = data.length;
				console.log("server response data is---" + JSON.stringify(data));
				//console.log("data length is---" + len);
				var res = JSON.parse(JSON.stringify(data));
				//console.log("data city is---" + res.cityCode);
				var tmp = "" + res.hireId;
				//console.log(tmp);
				if (tmp.length > 0) {
					//document.getElementById("interviewform").reset();
					document.getElementById(note).innerHTML += 'Successfully Published Data<br/>';
					jsdata = JSON.stringify(
						{
							"hireId": iHireId,
							"budget": budget,
							"negotiated": neg_ctc,
							"offeredCTC": off_ctc,
							"token": res.id,
						}
					);

					publishSalaryNegotiationDetails(jsdata, note);

				}
			}, error: function(e) {
				document.getElementById(note).innerHTML += 'Error Publishing Data<br/>';
			}
		});

		return false;
	});


}

function publishSalaryNegotiationDetails(jsdata, note) {
	// step 2
	console.log(jsdata);

	$.ajax({
		url: "/api/pubSalNegDetails/",
		type: "POST",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		dataType: "json",
		data: jsdata,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			//console.log("data length is---" + len);
			var res = JSON.parse(JSON.stringify(data));
			var tmp = "" + res.hireId;

			if (tmp.length > 0) {
				document.getElementById(note).innerHTML += 'Successfully Published Salary Negotiation Details<br/>';
			}
		},
		error: function(data, textStatus, xhr) {
			document.getElementById(note).innerHTML += "Error:" + JSON.stringify(data);
		}
	});
}

function phaseTwo(formId, intv, feed, rating, status, int_role, note, source, operationType) {
	thisHid = source.id.split('-')[1];
	thisHid = "hid-" + thisHid.charAt(0);
	console.log(thisHid);
	$('#' + formId).submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		//generate content
		var id = $('#' + thisHid).val();
		if (id === "" && operationType != 1) {
			document.getElementById(note).innerHTML += 'No Interview Id<br/>';
			return;
		}
		var int_id = $('#interviewId').val();
		var hireId = $('#iHireId').val();
		if (hireId !== "") {
		} else {
			document.getElementById(note).innerHTML += 'Get Hiring ID...<br/>';
			return;
		}

		var feedback = $('#' + feed).val();
		feedback = feedback.replaceAll(',', '&comma;');
		var ratingScore = $('input[name=' + rating + ']:checked', '#' + formId).val();
		var interviewer = $('#' + intv).val();
		var statusTemp = $('#' + status).val();

		var apiUrl = '';
		var postage = '';
		if (operationType == 1) {
			apiUrl = '/api/publishHireeDetails';
			postage = 'post';
		}
		if (operationType == 2) {
			apiUrl = '/api/updateHireeDetails/' + id;
			postage = 'put';
		}
		if (operationType == 3) {
			apiUrl = '/api/deleteHireeDetails/' + id;
			postage = 'delete';
		}
		if (apiUrl === "") {
			document.getElementById(note).innerHTML += 'No API Found For This Operation...<br/>';
			return;
		}

		jsdata = JSON.stringify(
			{
				"comment": feedback,
				"hireId": hireId,
				"interviewer": interviewer,
				"publishedDate": '',
				"rating": ratingScore,
				"roleDescription": int_role,
				"status": statusTemp,
				"token": int_id,
			}
		);

		console.log(jsdata);

		document.getElementById(note).innerHTML = "";
		$.ajax({
			url: apiUrl,
			type: postage,
			contentType: "application/json",
			data: jsdata,
			dataType: 'json',
			cache: false,
			timeout: 600000,
			success: function(data) {
				var len = data.length;
				console.log("server response data is---" + JSON.stringify(data));
				//console.log("data length is---" + len);
				var res = JSON.parse(JSON.stringify(data));
				//console.log("data city is---" + res.cityCode);
				var tmp = "" + res.hireId;
				//console.log(tmp);
				if (tmp.length > 0) {
					//document.getElementById("interviewform").reset();
					var msg = '';
					if (operationType == 1) {
						msg = 'Successfully Published Data';
					}
					if (operationType == 2) {
						msg = 'Successfully Updated Data';
					}
					if (operationType == 3) {
						msg = 'Successfully Deleted Data';
						$('#' + feed).val('');
						$('input[name=' + rating + ']', '#' + formId).prop("checked", false);
						$('#' + intv).val('');
						$('#' + status).val('');
					}
					document.getElementById(note).innerHTML += msg + '<br/>';
				}
			}, error: function(e) {
				document.getElementById(note).innerHTML += 'Error Publishing Data<br/>';
			}
		});
		return false;
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
