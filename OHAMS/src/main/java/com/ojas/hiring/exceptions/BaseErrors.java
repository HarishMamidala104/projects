package com.ojas.hiring.exceptions;

public enum BaseErrors {
	    NO_TASK(1001, "No TASK Found"),
	    ERR_CODE_NODATA(1006, "Data not found for {0}"),
	    NO_DATA_FOUND(1006, "No Data present for given id"),
	    APPLICATION_NOT_FOUND(9011, "Application not found for this user"),
	    LOSUNIQUEIDENTIFIER_INCORRECT(1033, "Incorrect Los Unique Identifier"),
	    FIELD_EMPTY(8026, "Field {0} cannot be empty"),
	    FIELD_NOT_FOUND(9012, "Field {0} is not Found"),
	    INVALID_AUTHORIZATION(401, "Invalid authorization header."),
	    LOSUNIQUEIDENTIFIER_EMPTY(1005, "Los Unique Identifier Empty"),
	    ERR_COMPLETED_PROCESS(1009, "Trying to edit a process which is completed"),
	    MSG_INSUFFICIENT_PERMISSIONS(1105, "Insufficient Permissions for the User."),
	    NO_FORMKEY_LINKED(1024, "No form linked with the task"),
	    API_KEY_CLIENT_DETAILS_NOT_AVAILABLE(12503, "Client details not available"),
	    NO_DOCUMENT_FOUND(8020, "Document not found in database"),
	    ERR_INVALID_REQUEST_DATA(9006, "Invalid Request Data"),
	    ERR_REQUIREDFIELDS_EMPTY(1004, "Required Fields cannot be empty"),
	    Err_PASSWORD_MATCH_OLD_PASSWORD(1003, "New password matches an old password"),
	    ERR_SAME_MAKER_CHECKER(9007, "Maker id is same"),
	    ERR_WHILE_PROCESSING_MONGODB(7700, "An error occurred while processing MongoDB data"),
	    ERR_WHILE_REPLACING_STRING(7700, "Issue while replacing String"),
	    ERR_LINK_GOT_EXPIRED(6788, "Link got expired"),
	    ERR_WHILE_PASSWORD_FORGOT(7896, "An error occurred during forgot password"),
	    ERR_INCORRECT_OTP(1034, "Incorrect OTP or Record not found for the provided email or mobile"),
	    ERR_AUTHENTICATE_FAILED(1212, "Ldap Server Authentication Failed"),
	    ERR_PRODUCT_NOT_FOUND(1242, "Product not found by given productid"),
	    ERR_DOCUMENT_EMPTY(1002, "Document Details is empty"),
	    FIELDS_NOT_ALLOWED(8004,"RequestBody contains Fields which are not allowed"),
	    ERR_INVALID_API_KEY(1032,"Invalid api key"),
	    ERR_NULL_API_KEY(1033,"Api key cannot be null."),
	    ERROR_PARSE_EXCEPTION(12505,"Invalid date format");

	    private final int code;
	    private final String message;

	    BaseErrors(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }

	    public int getCode() {
	        return code;
	    }

	    public String getMessage() {
	        return message;
	    }
}
