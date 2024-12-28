package com.ojas.hiring.entity;

import java.time.LocalDate;

public class CustomDate {
	private LocalDate date;

	public CustomDate(int year, int month, int day) {
		this.date = LocalDate.of(year, month, day);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(int year, int month, int day) {
		this.date = LocalDate.of(year, month, day);
	}
}
