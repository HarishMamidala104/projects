package com.ojas.hiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notice_period")
public class NoticePeriod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	@NotNull(message = "noticeperiod is mandatory")
	private int noticeperiod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoticeperiod() {
		return noticeperiod;
	}

	public void setNoticeperiod(int noticeperiod) {
		this.noticeperiod = noticeperiod;
	}


}
