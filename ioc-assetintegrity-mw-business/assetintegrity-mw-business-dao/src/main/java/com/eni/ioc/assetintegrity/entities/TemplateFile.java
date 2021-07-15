package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the result database table.
 * 
 */
@Entity
@Table(name="TEMPLATE_FILE")
public class TemplateFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="CODE")
	private String code;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="ATTACHMENT")
	private byte[] attachment;

	@Column(name="FILENAME")
	private String filename;

	@Column(name="CONTENT_TYPE")
	private String contentType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INSERTION_DATE")
	private Date insertionDate;

	public TemplateFile() {
		
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

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}
	
}