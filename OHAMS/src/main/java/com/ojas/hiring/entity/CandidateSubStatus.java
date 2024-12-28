package com.ojas.hiring.entity;

public enum CandidateSubStatus {
	YET_TO_SCHEDULE("yet_to_schedule"),
	INTERVIEW_SCHEDULED("interview_scheduled"),
	//new modification
	STAGE_1_SCHEDULED("_scheduled"),

	FEEDBACK_PENDING("feedback_pending"),
	L1_SELECT("L1_Select"),
	L2_SELECT("L2_Select"),
	L3_SELECT("L3_Select"),
	MANAGER_SELECT("manager_select"),
	CLIENT_SELECT("client_select"),
	
	SCREEN_REJECT("screen_reject"),
	DUPLICATE("duplicate"),
	PROXY("proxy"),
	L1_REJECT("L1_Reject"),
	L2_REJECT("L2_Reject"),
	L3_REJECT("L3_Reject"),
	MANAGER_REJECT("manager_reject"),
	CLIENT_REJECT("client_reject"),
	NO_SHOW("no_show"),
	HR_REJECT("hr_reject"),
	
	LEAD_HOLD("lead_hold"),
	BDM_HOLD("bdm_hold"),
	CLIENT_HOLD("client_hold"),
	SELECTION_HOLD("selection_hold"),
	
	NLFC("nlfc"),
	RETAINED("retained"),
	PERSONAL_REASONS("personal_reasons"),
	OTHER_OFFER("other_offer"),
	
	ONBOARDED("onboarded"),
	OFFERED("offered");
	
    private String text;

    CandidateSubStatus(String text) {   
    	this.text = text;
    }
    public String getText() {
        return this.text;
    }

    public static CandidateSubStatus fromString(String text) {
        for (CandidateSubStatus b : CandidateSubStatus.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
	
	
}
