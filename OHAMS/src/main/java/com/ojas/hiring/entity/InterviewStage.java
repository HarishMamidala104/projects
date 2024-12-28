package com.ojas.hiring.entity;

public enum InterviewStage {
	
	STAGE_1("STAGE_1"),
	STAGE_2("STAGE_2"),
	STAGE_3("STAGE_3"),
	MANAGER("MANAGER"),
	CLIENT("CLIENT"),
	HR("HR");
	
	private String text;

	InterviewStage(String text) {   
    	this.text = text;
    }
    public String getText() {
        return this.text;
    }

    public static InterviewStage fromString(String text) {
        for (InterviewStage b : InterviewStage.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
