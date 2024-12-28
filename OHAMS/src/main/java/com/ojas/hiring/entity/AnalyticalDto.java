package com.ojas.hiring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class AnalyticalDto {

    private String technology;

    private String customer;// Set to 'Company' for company-wide statistics
    private long openPositions;
    private long closedPositions;
    public long getOpenPositions() {
		return openPositions;
	}

	public void setOpenPositions(long openPositions) {
		this.openPositions = openPositions;
	}

	public long getClosedPositions() {
		return closedPositions;
	}

	public void setClosedPositions(long closedPositions) {
		this.closedPositions = closedPositions;
	}

	private long interviews;
    private long inprogress;
    private long hold;
    private long reject;
    private long selected;
    private String onboarded;
    private String offerred;

    public String getOnboarded() {
		return onboarded;
	}

	public void setOnboarded(String onboarded) {
		this.onboarded = onboarded;
	}

	public String getOfferred() {
		return offerred;
	}

	public void setOfferred(String offerred) {
		this.offerred = offerred;
	}

	public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getInterviews() {
        return interviews;
    }

    public void setInterviews(long interviews) {
        this.interviews = interviews;
    }

    public long getInprogress() {
        return inprogress;
    }

    public void setInprogress(long inprogress) {
        this.inprogress = inprogress;
    }

    public long getHold() {
        return hold;
    }

    public void setHold(long hold) {
        this.hold = hold;
    }

    public long getReject() {
        return reject;
    }

    public void setReject(long reject) {
        this.reject = reject;
    }

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }
}
