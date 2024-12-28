package com.ojas.hiring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Owner_Of_requirement")
public class OwnerOfRequirement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	
	@NotBlank(message="ownerOfRequirement is mandatory")
	private String ownerOfRequirement;
	
	@Column(name = "created_on")
	private String createdOn;
	
	private int visibility;
	
	

	public OwnerOfRequirement() {
		super();
	}

	public OwnerOfRequirement(int id, @NotBlank(message = "ownerOfRequirement is mandatory") String ownerOfRequirement,
			String createdOn, int visibility) {
		super();
		this.id = id;
		this.ownerOfRequirement = ownerOfRequirement;
		this.createdOn = createdOn;
		this.visibility = visibility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwnerOfRequirement() {
		return ownerOfRequirement;
	}

	public void setOwnerOfRequirement(String ownerOfRequirement) {
		this.ownerOfRequirement = ownerOfRequirement;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	
}
