function checkValidNames(srcElement,msg) {
	let text = srcElement.value;
	let result = /^[a-zA-Z ]+$/.test(text);
	console.log(result);
	if (result) {
	} else {
		$(srcElement).attr("placeholder", msg);
		srcElement.value = '';
	}
}

function checkCurrency(srcElement) {
	let text = srcElement.value;
	text = text.replace(/,/g, '');
	var res = checkCurrencyFormat(text);
	console.log(res);
	if (res.length == 0) {
		$(srcElement).val("");
		$(srcElement).attr("placeholder", "Not Valid Currency Format e.g 1,23,456");
	} else {
		$(srcElement).val(text);

	}

}

function convertToCurrencyValue(srcElement) {
	let text = srcElement.value;
	if (!!text.trim()) {
		srcElement.value = parseFloat(text).toLocaleString('en-IN', {
			style: 'decimal',
			maximumFractionDigits: 2,
			minimumFractionDigits: 2
		});
	} else {
		srcElement.value = 0;
	}


}

function checkCurrencyFormat(value) {
	//var value= $("#field1").val();
	var regex = /^[1-9]\d*(((,\d{3}){1})?(\.\d{0,2})?)$/;
	if (regex.test(value)) {
		//Input is valid, check the number of decimal places
		var twoDecimalPlaces = /\.\d{2}$/g;
		var oneDecimalPlace = /\.\d{1}$/g;
		var noDecimalPlacesWithDecimal = /\.\d{0}$/g;

		if (value.match(twoDecimalPlaces)) {
			//all good, return as is
			return value;
		}
		if (value.match(noDecimalPlacesWithDecimal)) {
			//add two decimal places
			return value + '00';
		}
		if (value.match(oneDecimalPlace)) {
			//ad one decimal place
			return value + '0';
		}
		//else there is no decimal places and no decimal
		return value + ".00";
	}
	return '';
};