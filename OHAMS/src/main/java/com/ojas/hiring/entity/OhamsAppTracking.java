package com.ojas.hiring.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ohams_app_tracking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OhamsAppTracking {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String userId;
	private String activity;
	private String action;
	private String module;
	private int link;
	private Date date;

}
