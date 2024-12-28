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
@Table(name = "config_positions_information")
public class Positions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id")
	private int positionid;
	@Column(name = "position_name", unique = true)
	private String positionname;

	public int getPositionid() {
		return positionid;
	}

	public void setPositionid(int positionid) {
		this.positionid = positionid;
	}

	public String getPositionname() {
		return positionname;
	}

	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}

	public Positions(int positionid, String positionname) {
		super();
		this.positionid = positionid;
		this.positionname = positionname;
	}

	public Positions() {
		super();
	}

	@Override
	public String toString() {
		return "Positions [positionid=" + positionid + ", positionname=" + positionname + "]";
	}

}