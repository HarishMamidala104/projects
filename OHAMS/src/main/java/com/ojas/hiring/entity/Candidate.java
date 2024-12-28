package com.ojas.hiring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "data_candidate")
public class Candidate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long cid;
    @Column(name = "full_name")
    private String fullName;
    private String mobileNo;
    //	@NotBlank
//	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\\\.[A-Za-z]{2,})$", message = "Invalid email format")
    private String emailId;
    private double totalExperience;
    private String currentlyWorkingAs;
    private String currentlyWorkingAt;
    private String currentLocation;
    private String preferredLocation;
    private double ctcPA;
    private double expectedCtcPa;
    private double noticePeriod;
    private String currentlyServingNoticePeriod;
    private String comments;
    private String availability;
    private long employeeId;
    private String resourceType;
	private String requirementName;

    @ElementCollection
    @CollectionTable(name = "data_candidate_skill_set")
    private Map<String, Double> skillSet;


    private long rrfLink;


    private String status;

    private double offered_CTC;

    private int visibility;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String recordAuthor;

    @JsonIgnore
    @Column(columnDefinition = "MEDIUMTEXT")
    private String ipAddress;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rrf_id")
    @JsonManagedReference
//	@JsonBackReference
    private RRF rrf;
   
    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
	@JsonBackReference
//	@JsonManagedReference
	private List<Interviews> interviews;

    @Column(columnDefinition = "VENDOR")
    private String vendor;

    @Column(columnDefinition = "CREATION_DATE")
    private Date creationDate;
    
	@Column(columnDefinition = "SOURCE")
    private String source;

	@Column(columnDefinition = "additional_skills")
    private String additionalSkills;

    @Column(columnDefinition = "sub_status")
    private String subStatus;

    
//    @Transient
//    private Interviews interviews;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    
    
    public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public double getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(double totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getCurrentlyWorkingAs() {
        return currentlyWorkingAs;
    }

    public void setCurrentlyWorkingAs(String currentlyWorkingAs) {
        this.currentlyWorkingAs = currentlyWorkingAs;
    }

    public String getCurrentlyWorkingAt() {
        return currentlyWorkingAt;
    }

    public void setCurrentlyWorkingAt(String currentlyWorkingAt) {
        this.currentlyWorkingAt = currentlyWorkingAt;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public double getCtcPA() {
        return ctcPA;
    }

    public void setCtcPA(double ctcPA) {
        this.ctcPA = ctcPA;
    }

    public double getExpectedCtcPa() {
        return expectedCtcPa;
    }

    public void setExpectedCtcPa(double expectedCtcPa) {
        this.expectedCtcPa = expectedCtcPa;
    }

    public double getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(double noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getCurrentlyServingNoticePeriod() {
        return currentlyServingNoticePeriod;
    }

    public void setCurrentlyServingNoticePeriod(String currentlyServingNoticePeriod) {
        this.currentlyServingNoticePeriod = currentlyServingNoticePeriod;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Map<String, Double> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Map<String, Double> skillSet) {
        this.skillSet = skillSet;
    }

    public long getRrfLink() {
        return rrfLink;
    }

    public void setRrfLink(long rrfLink) {
        this.rrfLink = rrfLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getOffered_CTC() {
        return offered_CTC;
    }

    public void setOffered_CTC(double offered_CTC) {
        this.offered_CTC = offered_CTC;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getRecordAuthor() {
        return recordAuthor;
    }

    public void setRecordAuthor(String recordAuthor) {
        this.recordAuthor = recordAuthor;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public RRF getRrf() {
        return rrf;
    }

    public void setRrf(RRF rrf) {
        this.rrf = rrf;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAdditionalSkills() {
        return additionalSkills;
    }

    public void setAdditionalSkills(String additionalSkills) {
        this.additionalSkills = additionalSkills;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
    
//    public Interviews getInterviews() {
//		return interviews;
//	}
//
//	public void setInterviews(Interviews interviews) {
//		this.interviews = interviews;
//	}


	public List<Interviews> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interviews> interviews) {
		this.interviews = interviews;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	
    
}
