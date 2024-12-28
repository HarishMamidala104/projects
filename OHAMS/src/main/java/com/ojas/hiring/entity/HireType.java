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
@Table(name = "config_hiring_types_information")
public class HireType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hire_id")
	private int id;

	@NotBlank(message = "Not a blank hire type")
	@Column(name = "hire_type")
	private String hireType;

	public String getHireType() {
		return hireType;
	}

	public void setHireType(String hireType) {
		this.hireType = hireType;
	}

	public int getId() {
		return id;
	}

	public HireType() {
		super();
	}

	public HireType(int id, @NotBlank(message = "Not a blank hire type") String hireType) {
		super();
		this.id = id;
		this.hireType = hireType;
	}

	@Override
	public String toString() {
		return "HireType [id=" + id + ", hireType=" + hireType + "]";
	}

}