package com.ojas.hiring.entity;

public enum CandidateStatus {
	
	InProgress("InProgress"),
	Reject("Reject"),
	Hold("Hold"),
	Backout("Backout"),
	Select("Select");
	
	private String text;

	CandidateStatus(String text) {   
    	this.text = text;
    }
	
    public String getText() {
        return this.text;
    }

    public static CandidateStatus fromString(String text) {
        for (CandidateStatus b : CandidateStatus.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
