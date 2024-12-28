package com.ojas.hiring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

//@Author Rajendar.Baswaraju
@Entity
@Table(name = "config_profile_source_information")
public class ProfileSource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private int profileid;
	@Column(name = "profile_name")
	private String profilename;

	public int getProfileid() {
		return profileid;
	}

	public void setProfileid(int profileid) {
		this.profileid = profileid;
	}

	public String getProfilename() {
		return profilename;
	}

	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	public ProfileSource(int profileid, String profilename) {
		super();
		this.profileid = profileid;
		this.profilename = profilename;
	}

	public ProfileSource() {
		super();
	}

	@Override
	public String toString() {
		return "ProfileSource [profileid=" + profileid + ", profilename=" + profilename + "]";
	}

}