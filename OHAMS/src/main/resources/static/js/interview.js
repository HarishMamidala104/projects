var url = '';
var autoSuggestFlag = 1;

$(document).ready(function() {
	console.log("ready!");

	var base_url = window.location.origin;
	var pathArray = window.location.pathname.split('/');
	console.log("Params : " + pathArray.length);
	if (pathArray.length == 3) {
		url = base_url + "/" + pathArray[1] + "/" + pathArray[2];
	}
	if ($('#interviewId').val() === '-1') {
		$('#p1win').hide();
		$('#p2win').hide();
		$('#p3win').hide();
		$('#p4win').hide();
		$('#p5win').hide();
		$('#p6win').hide();
	} else {
		url = base_url + "/" + pathArray[1] + "/" + pathArray[2] + '?intId=' + $('#interviewId').val().trim();
		populate('notifications0', 1);
	}

});

function checkAndGenEmpId(hireType, target) {
	var selId = hireType.id;
	var selected = $('#' + selId + ' :selected').text();
	console.log(selected);
	if (selected === 'Internal') {
		$('#' + target).val('');
		$('#' + target).prop("readonly", false);
		$('#company').val('');
		$('#company').prop("readonly", false);
	}
	if (selected === 'External') {
		$('#' + target).val('');
		//var currentdate = new Date();
		//var fn = 'E' + currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + currentdate.getHours() + currentdate.getMinutes() + currentdate.getSeconds();
		//$('#' + target).val(fn);
		$('#' + target).prop("readonly", true);
		$('#company').val('Ojas-It');
		$('#company').prop("readonly", true);
	}
}

function genHeader(source) {
	var srcText = source.value;
	var targetId = source.id + "_head";
	$('#' + targetId).text('Interview by ' + srcText + ':');
}


function showDataGrid(note) {
	console.log('Data Grid...');
	document.getElementById("searchForm").reset();
	window.location.href = "/hiringold/grid";
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();


	});
}

function newProfile(note) {
	console.log('New Profile...');
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		$('#p1win').show();
		document.getElementById("searchForm").reset();
		$('#pub-1U').hide();
		$('#pub-1D').hide();
		//$('#p2win').show();
		//$('#p3win').show();
		//$('#p4win').show();
		//$('#p5win').show();
		//$('#p6win').show();
	});
	//document.getElementById("interviewform").reset();
}
function populate(note, opt) {
	var int_id = -1;
	if (opt == 0) {
		$('#' + note).text('');
		$('#searchForm').submit(function(e) {
			e.preventDefault();
			e.stopImmediatePropagation();
		});
		var search_box = $('#search_box').val();
		var str_array = search_box.split('-');
		for (var i = 0; i < str_array.length; i++) {
			// Trim the excess whitespace.
			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
			// Add additional code here, such as:		
			if (str_array[i].startsWith("Interview Id :")) {
				var sub_str_array = str_array[i].split(':')[1];
				int_id = parseInt(sub_str_array);
				$('#interviewId').val(sub_str_array.trim());
				break;
			}
		}
	}
	if (opt == 1) {
		int_id = $('#interviewId').val();
		//refresh iframe
	}


	if (int_id == -1) {
		$('#' + note).text('Search Field is Empty');
		return;
	}

	$("#cnp").hide();

	$('#p1win').show();
	$('#p2win').show();
	$('#p3win').show();
	$('#p4win').show();
	$('#p5win').show();
	$('#p6win').show();

	console.log('Processed Interview Id : ' + int_id);
	console.log('Interview Details API :' + "/hiringold/api/interviewdetails/" + int_id);
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
			$('#iHireId').val(res.hireId);
			$('#iHireName').val(res.hireName);

			var int_sch_date = (res.interviewScheduledDate);
			const d = new Date(int_sch_date);
			var today = new Date(d);
			$('#int_sch_date').val(today.toISOString().slice(0, 16));

			$("#positions").val(res.position.trim()).attr("selected", "selected");
			$("#hm").val(res.hiringManager.trim());
			$("#approvedBy").val(res.approvedBy.trim());
			$("#tech").val(res.technology.trim());
			$("#ps").val(res.profileSourceAcquisition.trim()).attr("selected", "selected");
			$("#ie").val(res.hiringType.trim()).attr("selected", "selected");
			$("textarea#snd").val(res.salaryNegotiation);
			$("textarea#hop").val(res.hiringObjective);
			$('#budget').val(res.budget);
			$('#neg_ctc').val(res.negotiated);
			$('#off_ctc').val(res.offeredCTC);
			$('#company').val(res.company);

			$('#pub-1U').show();
			$('#pub-1D').show();
			//generate link
			var base_url = window.location.origin;
			var pathArray = window.location.pathname.split('/');
			url = base_url + "/" + pathArray[1] + "/" + pathArray[2] + '?intId=' + $('#interviewId').val().trim();

			var a = document.createElement('a');
			var linkText = document.createTextNode("Shareable Interview Link");
			a.appendChild(linkText);
			a.setAttribute('style', 'color:red');
			a.setAttribute('title', url);
			a.setAttribute('target', '_blank');
			a.setAttribute('onclick', 'openOutLook(this);');
			a.setAttribute('id', $('#interviewId').val().trim());
			//a.href = url;
			document.getElementById('genLink').innerHTML = '';
			document.getElementById('genLink').append(a);
			////////////
			autoSuggestFlag = 0;
			getInterviewDetails(res.id, note);

		},
		error: function(data, textStatus, xhr) {
			res = JSON.parse(JSON.stringify(data));
			document.getElementById(note).innerHTML += res.responseText;
		}
	});
	document.getElementById('search_box').value = '';
	return false;
}

function getInterviewDetails(id, note) {
	console.log('Get Interview Details API :' + "/hiringold/api/getHiresInfoByToken/" + id);
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
					$('#hid-2').val(res.hid);
					$('#tech1_int').val(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#t1feed').text(feed);
					var rating = res.rating;
					$('#t1' + rating).attr('checked', true);
					$('#t1-status').val(res.status);

					$('#pub-1').hide();
					$('#pub-1U').hide();
					$('#pub-1D').hide();
					$('#pub-2').hide();
				}
				if (res.roleDescription === 'Tech-Interview-2') {
					$('#hid-3').val(res.hid);
					$('#tech2_int').val(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#t2feed').text(feed);
					var rating = res.rating;
					$('#t2' + rating).attr('checked', true);
					$('#t2-status').val(res.status);

					$('#pub-3').hide();
				}
				if (res.roleDescription === 'Man-Interview') {
					$('#hid-4').val(res.hid);
					$('#man_int').val(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#manfeed').text(feed);
					var rating = res.rating;
					$('#man' + rating).attr('checked', true);
					$('#man-status').val(res.status);

					$('#pub-4').hide();
				}
				if (res.roleDescription === 'hr-Interview') {
					$('#hid-5').val(res.hid);
					$('#hr_int').val(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#hrfeed').text(feed);
					var rating = res.rating;
					$('#hr' + rating).attr('checked', true);
					$('#hr-status').val(res.status);

					$('#pub-5').hide();
				}
				if (res.roleDescription === 'Interview-Result') {
					$('#hid-6').val(res.hid);
					$('#finalJudge_int').val(res.interviewer);
					var feed = res.comment;
					feed = feed.replaceAll('&comma;', ',');
					$('#finalJudgefeed').text(feed);
					var rating = res.rating;
					$('#finalJudge' + rating).attr('checked', true);
					$('#finalJudge-status').val(res.status);

					$('#pub-6').hide();
				}
			}

		},
		error: function(data, textStatus, xhr) {
			res = JSON.parse(JSON.stringify(data));
			document.getElementById(note).innerHTML += res.responseText;
			if (res.responseText.trim() === 'Only Scheduled.No Interviews Taken') {
				$('#pub-1').hide();
			}			/*			for (let x in res) {				console.log(x);							}*/
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


		var intId = $('#interviewId').val();
		var iHireId = $('#iHireId').val();
		var iHireName = $('#iHireName').val();

		var regex = new RegExp(',', 'g');

		var budget = $('#budget').val();
		budget = budget.replace(regex, "");

		var neg_ctc = $('#neg_ctc').val();
		neg_ctc = neg_ctc.replace(regex, "");

		var off_ctc = $('#off_ctc').val();
		off_ctc = off_ctc.replace(regex, '');

		if (isNaN(budget)) {
			budget = 0;
		}
		if (isNaN(neg_ctc)) {
			neg_ctc = 0;
		}
		if (isNaN(off_ctc)) {
			off_ctc = 0;
		}

		var company = $('#company').val();

		if (iHireId !== "") {
		} else {
			document.getElementById(note).innerHTML += 'Get Hiring ID...<br/>';
			return;
		}
		var apiUrl = '';
		var postage = '';
		if (operationType == 1) {
			apiUrl = '/hiringold/api/postInterviewDetails';
			postage = 'post';
			intId = 0;
		}
		if (operationType == 2) {
			apiUrl = '/hiringold/api/interviewupdate/' + intId;
			postage = 'put';
		}
		if (operationType == 3) {
			apiUrl = '/hiringold/api/interviewdelete/' + intId;
			postage = 'delete';
		}
		if (apiUrl === "") {
			document.getElementById(note).innerHTML += 'No API Found For This Operation...<br/>';
			return;
		}
		console.log('Interview Details API :' + apiUrl + '  method=' + postage);
		var jsdata = null;

		document.getElementById(note).innerHTML = "";
		jsdata = JSON.stringify(
			{
				"id": intId,
				"hireId": iHireId,
				"hireName": iHireName,
				"hiringType": hiringType,
				"interviewScheduledDate": int_sch_date,
				"position": position,
				"technology": tech.trim(),
				"hiringObjective": hop.trim(),
				"salaryNegotiation": snd.trim(),
				"hiringManager": hm.trim(),
				"approvedBy": approvedBy.trim(),
				"profileSourceAcquisition": profileSource.trim(),
				"author": "HR",
				"authorRole": "HR",
				"rating": 0,
				"budget": budget,
				"negotiated": neg_ctc,
				"offeredCTC": off_ctc,
				"company": company,

			}
		);

		console.log('formdata---' + jsdata);
		console.log('formdata---' + apiUrl);

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
				var res = JSON.parse(JSON.stringify(data));
				var tmp = res.hireId;
				console.log(tmp);
				if (typeof tmp === 'undefined' || tmp === null) {
					document.getElementById(note).innerHTML += res.error;
				}
				else {
					var res = JSON.parse(JSON.stringify(data));
					if (operationType == 1 && tmp.length > 0) {
						document.getElementById(note).innerHTML += 'Successfully Published Data<br/>';
					}
					if (operationType == 2 && tmp.length > 0) {
						document.getElementById(note).innerHTML += 'Successfully Updated Data<br/>';
					}
					if (operationType == 3 && tmp.length > 0) {
						document.getElementById(note).innerHTML += 'Successfully Deleted Data<br/>';
					}
				}
			}, error: function(e) {
				document.getElementById(note).innerHTML += 'Error Publishing Data<br/>' + e.status;
			}
		});

		return false;
	});


}

function publishSalaryNegotiationDetails(jsdata, note, operationType) {
	// step 2	
	var apiUrl = '';
	var postage = '';
	if (operationType == 1) {
		apiUrl = "/hiringold/api/pubSalNegDetails/";
		postage = 'post';
		intId = 0;
	}
	if (operationType == 2) {
		apiUrl = '/hiringold/api/salnegdetails/updatebytoken/' + JSON.parse(jsdata).token;
		postage = 'put';
	}
	if (operationType == 3) {
		apiUrl = '/hiringold/api/salnegdetails/deletebytokenid/' + JSON.parse(jsdata).token;
		postage = 'delete';
	}
	if (apiUrl === "") {
		document.getElementById(note).innerHTML += 'No API Found For This Operation...<br/>';
		return;
	}

	console.log('Salary Details API :' + apiUrl + '  method=' + postage);
	$.ajax({
		url: apiUrl,
		type: postage,
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		dataType: "json",
		data: jsdata,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			//console.log("data length is---" + len);
			var res = JSON.parse(JSON.stringify(data));
			if (operationType == 1) {
				var tmp = "" + res.hireId;
				if (tmp.length > 0) {
					document.getElementById(note).innerHTML += 'Successfully Published Salary Negotiation Details<br/>';
				}
			}
			if (operationType == 2) {
				var tmp = "" + res.responseText;
				if (tmp.length > 0) {
					document.getElementById(note).innerHTML += tmp + '<br/>';
				}
			}
			if (operationType == 3) {
				var tmp = "" + res;
				console.log(tmp);
				if (tmp.length > 0) {
					document.getElementById(note).innerHTML += 'Successfully Deleted Salary Negotiation Details<br/>';
				}
			}
		},
		error: function(data, textStatus, xhr) {
			document.getElementById(note).innerHTML += (data).responseText;
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
			apiUrl = '/hiringold/api/publishHireeDetails';
			postage = 'post';
		}
		if (operationType == 2) {
			apiUrl = '/hiringold/api/updateHireeDetails/' + id;
			postage = 'put';
		}
		if (operationType == 3) {
			apiUrl = '/hiringold/api/deleteHireeDetails/' + id;
			postage = 'delete';
		}
		if (apiUrl === "") {
			document.getElementById(note).innerHTML += 'No API Found For This Operation...<br/>';
			return;
		}
		console.log('Hiree Details API :' + apiUrl + '  method=' + postage);
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
				var tmp = res.hireId;
				if (typeof tmp === 'undefined' || tmp === null) {
					document.getElementById(note).innerHTML += res.error;
				}
				else {
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

function formatDateAndTime(date) {
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var ampm = hours >= 12 ? 'pm' : 'am';
	hours = hours % 12;
	hours = hours ? hours : 12; // the hour '0' should be '12'
	minutes = minutes < 10 ? '0' + minutes : minutes;
	var strTime = ", " + hours + ':' + minutes + ' ' + ampm;
	return [
		padTo2Digits(date.getMonth() + 1),
		padTo2Digits(date.getDate()),
		date.getFullYear(),
	].join('/') + strTime;
}

function openOutLook(src) {
	var formattedBody = "Interview Feedback Link\n\n" + src.title + "\n\n";
	var domain = 'ojas-it.com';
	
	var int_sch_date = $('#int_sch_date').val();
	console.log(int_sch_date);
	var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DDTHH:mm");
	const d = new Date(withoutOffset);
	
	var sub = "Interview Scheduled For " + $('#iHireName').val() + " On " + formatDateAndTime(d);
	var mailToLink = "mailto:?subject=" + sub + "&body=" +
		encodeURIComponent(formattedBody);
	window.location.href = mailToLink;
}

function convertLetterToNumber(str) {
	var out = 0, len = str.length;
	for (pos = 0; pos < len; pos++) {
		out += (str.charCodeAt(pos) - 64) * Math.pow(26, len - pos - 1);
	}
	return out;
}

function genEmpId(hireNameBox, hireType, target) {
	var selected = $('#' + hireType + ' :selected').text();
	console.log(selected);
	if (selected === 'Internal') {
		$('#' + target).val('');
		$('#' + target).prop("readonly", false);
		$('#company').val('');
		$('#company').prop("readonly", false);
	}
	if (selected === 'External') {
		$('#' + target).val('');
		//var currentdate = new Date();
		var ename = hireNameBox.value;
		ename = ename.replace(/\s/g, "");
		console.log(ename);
		var fn = BigInt(convertLetterToNumber(ename));
		$('#' + target).val("E" + fn);
		$('#' + target).prop("readonly", true);
		$('#company').val('Ojas-It');
		$('#company').prop("readonly", true);
	}

	getSuggestions(hireNameBox);
}

function getSuggestions(src) {
	if (autoSuggestFlag == 0) {
		return;
	}
	var key = src.id;
	var keyTarget = key + "_target";
	var val = src.value;

	var option = 0;
	if (key === 'iHireName') {
		option = 1;
	}
	if (key === 'tech') {
		option = 2;
	}
	if (key === 'company') {
		option = 3;
	}


	var dynUrl = "/hiringold/ajax/results/" + option + "/" + val
	//console.log(dynUrl);

	$.ajax({
		url: dynUrl,
		type: "GET",
		async: true, // set to false if you don't mind the page pausing while waiting for response
		cache: false,
		contentType: "application/json; charset=utf-8",
		success: function(data, textStatus, xhr) {
			console.log("server response data is---" + JSON.stringify(data));
			var res = null;
			var html = '<div class="list-group">';
			for (let x in data) {
				responseCount = data[x].length;
				console.log(x + ":: " + responseCount)
				if (responseCount > 0) {
					for (let i = 0; i < data[x].length; i++) {
						var en = data[x][i];
						//console.log(key + "------------------" + keyTarget);
						html += '<a href="#" class="list-group-item list-group-item-action" onclick="get_Opt_Suggestions(this,\'' + key.trim() + '\',\'' + keyTarget.trim() + '\')" >' + en + '</a>';
					}
				} else {
					//html += '<a href="#" class="list-group-item list-group-item-action disabled" onclick="get_Opt_Suggestions(this,\'' + key.trim() + '\',\'' + keyTarget.trim() + '\')" >No Data Found</a>';
					document.getElementById(keyTarget).value = '';
				}
			}

			html += '</div>';

			document.getElementById(keyTarget).innerHTML = html;


		},
		error: function(data, textStatus, xhr) {
			console.log('Auto Sugg v2 Error : ' + data.responseText);
		}
	});

}

function get_Opt_Suggestions(event, target, sbid) {
	var string = event.textContent;
	if (string === 'No Data Found') {
		document.getElementById(target).value = '';
		document.getElementById(sbid).innerHTML = '';
	} else {
		document.getElementById(target).value = string;
		document.getElementById(sbid).innerHTML = '';
	}
	document.getElementById(target).focus();
}