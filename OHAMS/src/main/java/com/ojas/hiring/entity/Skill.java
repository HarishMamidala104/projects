package com.ojas.hiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_skills_information")
public class Skill {
	@Id
	@GeneratedValue
	private int skillId;
	private String skillName;
	private String skillAspects;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillAspects() {
		return skillAspects;
	}

	public void setSkillAspects(String skillAspects) {
		this.skillAspects = skillAspects;
	}

	public Skill(int skillId, String skillName, String skillAspects) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
		this.skillAspects = skillAspects;
	}

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

}
