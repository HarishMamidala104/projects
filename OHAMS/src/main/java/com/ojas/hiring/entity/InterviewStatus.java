package com.ojas.hiring.entity;

public enum InterviewStatus {
	
	STAGE1_SELECT("STAGE1_SELECT"),
	STAGE2_SELECT("STAGE2_SELECT"),
	STAGE3_SELECT("STAGE3_SELECT"),
	MANAGER_ROUND_SELECT("MANAGER_ROUND_SELECT"),
	HR_SELECT("HR_SELECT"),
	
	STAGE1_REJECT("STAGE1_REJECT"),
	STAGE2_REJECT("STAGE2_REJECT"),
	STAGE3_REJECT("STAGE3_REJECT"),
	MANAGER_ROUND_REJECT("MANAGER_ROUND_REJECT"),
	HR_REJECT("HR_REJECT");
	
	
	private String text;

	InterviewStatus(String text) {   
    	this.text = text;
    }
    public String getText() {
        return this.text;
    }

    public static InterviewStatus fromString(String text) {
        for (InterviewStatus b : InterviewStatus.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
