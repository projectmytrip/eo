package com.eni.ioc.assetintegrity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TURNI")
@Entity
public class Shift {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "start")
	private String start;

	@Column(name = "end")
	private String end;

	public Shift() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Shift{" + "id=" + id + ", code='" + code + '\'' + ", name='" + name + '\'' + ", start='" + start +
				'\'' + ", end='" + end + '\'' + '}';
	}
}
