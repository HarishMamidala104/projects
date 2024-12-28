package com.ojas.hiring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@Entity
@Table(name = "data_rrf")
public class RRF implements Serializable {

	private static final long serialVersionUID = -1044599953508102897L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@JsonPropertyDescription("A brief description of the job or role associated with this entry.")
	private String jobDescription;

	@JsonPropertyDescription("The name of the customer associated with this record.")
	private String customerName;

	@JsonPropertyDescription("The number of open positions available for hiring.")
	private int openPositions;

	@JsonPropertyDescription("The number of positions that have been successfully filled or closed.")
	private int closedPositions;

	@JsonPropertyDescription("The type of hiring process, such as full-time, part-time, or contract.")
	private String hiringType;

	@JsonPropertyDescription("The level of the job position, such as entry-level, mid-level, or senior-level.")
	private String jobLevel;

	@JsonPropertyDescription("The minimum years of experience required for the job position.")
	public double minExp;

	@JsonPropertyDescription("The maximum years of experience considered for the job position.")
	public double maxExp;

	@JsonPropertyDescription("The budget allocated for the hiring process or position.")
	private double budget;

	@JsonPropertyDescription("The primary skills required for the job position, typically the most critical ones.")
	private String primarySkills;

	@JsonPropertyDescription("The secondary skills preferred for the job position, often complementary to the primary skills.")
	private String secondarySkills;

	@JsonPropertyDescription("The number of candidates associated with the job requirement.")
	private int candidateCount;

	@JsonPropertyDescription("The name or title of the job requirement or position.")
	@Column(name = "requirement_Name")
	private String requirementName;

	@JsonPropertyDescription("The date when the job requirement was published, formatted as MM-dd-yyyy.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private Date publishedOn;

	@JsonPropertyDescription("The visibility level of the job requirement, determining who can see it.")
	private int visibility;

	@JsonPropertyDescription("The email address associated with this job requirement.")
	@Column(name = "email")
	private String emailAddress;

	@JsonPropertyDescription("The name or identifier of the person who created this job requirement.")
	private String createdBy;

	@JsonIgnore
	@JsonPropertyDescription("The IP address from which this job requirement was created.")
	private String ipAddress;

	@JsonPropertyDescription("The unique identifier of the employee associated with this job requirement.")
	private long employeeId;

	@JsonPropertyDescription("A list of candidates associated with this job requirement.")
	@OneToMany(mappedBy = "rrf", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Candidate> candidate;

	@JsonPropertyDescription("The title of the job position being offered.")
	private String jobTitle;

	@JsonPropertyDescription("The type of job, such as full-time, part-time, or contract.")
	private String jobType;

	@JsonPropertyDescription("The priority level of the job requirement, indicating its urgency.")
	private String priority;

	@JsonPropertyDescription("The mode of work for the job, such as on-site, remote, or hybrid.")
	private String modeOfWork;

	@JsonPropertyDescription("The title or designation of the job requirement.")
	private String title;

	@JsonPropertyDescription("The name of the person who owns or is responsible for this job requirement.")
	private String ownerOfRequirement;

	@JsonPropertyDescription("The city where the job position is located.")
	private String city;

	@JsonPropertyDescription("The state where the job position is located.")
	private String state;

	@JsonPropertyDescription("The total number of positions available for this job requirement.")
	private int totalPositions;

	@JsonPropertyDescription("The current status of the job requirement, such as open, closed, or pending.")
	private String requirementStatus;

	@JsonPropertyDescription("The specific location of the job position, typically more detailed than just the city and state.")
	private String location;

	@JsonPropertyDescription("The experience required for the job position, typically expressed in years.")
	private String experience;

	// Getters and Setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getOpenPositions() {
		return openPositions;
	}

	public void setOpenPositions(int openPositions) {
		this.openPositions = openPositions;
	}

	public int getClosedPositions() {
		return closedPositions;
	}

	public void setClosedPositions(int closedPositions) {
		this.closedPositions = closedPositions;
	}

	public String getHiringType() {
		return hiringType;
	}

	public void setHiringType(String hiringType) {
		this.hiringType = hiringType;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public double getMinExp() {
		return minExp;
	}

	public void setMinExp(double minExp) {
		this.minExp = minExp;
	}

	public double getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(double maxExp) {
		this.maxExp = maxExp;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public String getPrimarySkills() {
		return primarySkills;
	}

	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	public String getSecondarySkills() {
		return secondarySkills;
	}

	public void setSecondarySkills(String secondarySkills) {
		this.secondarySkills = secondarySkills;
	}

	public int getCandidateCount() {
		return candidateCount;
	}

	public void setCandidateCount(int candidateCount) {
		this.candidateCount = candidateCount;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public List<Candidate> getCandidate() {
		return candidate;
	}

	public void setCandidate(List<Candidate> candidate) {
		this.candidate = candidate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getModeOfWork() {
		return modeOfWork;
	}

	public void setModeOfWork(String modeOfWork) {
		this.modeOfWork = modeOfWork;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwnerOfRequirement() {
		return ownerOfRequirement;
	}

	public void setOwnerOfRequirement(String ownerOfRequirement) {
		this.ownerOfRequirement = ownerOfRequirement;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTotalPositions() {
		return totalPositions;
	}

	public void setTotalPositions(int totalPositions) {
		this.totalPositions = totalPositions;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	// Constructors

	public RRF() {
		super();
	}

	public RRF(long id, String jobDescription, String customerName, int openPositions, int closedPositions,
			String hiringType, String jobLevel, double minExp, double maxExp, double budget, String primarySkills,
			String secondarySkills, Date publishedOn, int visibility, String emailAddress, String createdBy,
			String ipAddress, long employeeId, List<Candidate> candidate, String jobTitle, String jobType,
			String priority, String modeOfWork, String title, String ownerOfRequirement, String city, String state,
			int totalPositions, String requirementStatus, String location, String experience) {
		super();
		this.id = id;
		this.jobDescription = jobDescription;
		this.customerName = customerName;
		this.openPositions = openPositions;
		this.closedPositions = closedPositions;
		this.hiringType = hiringType;
		this.jobLevel = jobLevel;
		this.minExp = minExp;
		this.maxExp = maxExp;
		this.budget = budget;
		this.primarySkills = primarySkills;
		this.secondarySkills = secondarySkills;
		this.publishedOn = publishedOn;
		this.visibility = visibility;
		this.emailAddress = emailAddress;
		this.createdBy = createdBy;
		this.ipAddress = ipAddress;
		this.employeeId = employeeId;
		this.candidate = candidate;
		this.jobTitle = jobTitle;
		this.jobType = jobType;
		this.priority = priority;
		this.modeOfWork = modeOfWork;
		this.title = title;
		this.ownerOfRequirement = ownerOfRequirement;
		this.city = city;
		this.state = state;
		this.totalPositions = totalPositions;
		this.requirementStatus = requirementStatus;
		this.location = location;
		this.experience = experience;
	}

}
