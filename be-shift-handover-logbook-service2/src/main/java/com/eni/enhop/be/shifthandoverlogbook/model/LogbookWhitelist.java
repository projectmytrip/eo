package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logbook_whitelist")
public class LogbookWhitelist extends BasicEntity {
	private static final long serialVersionUID = 8482131414978731303L;

	@Id
	@Column(name = "id_logbook_whitelist", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "id_user", nullable = false)
	private Long idUser;

	@Column(name = "primary_recipient", nullable = true)
	private boolean primaryRecipient;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public boolean isPrimaryRecipient() {
		return primaryRecipient;
	}

	public void setPrimaryRecipient(boolean primaryRecipient) {
		this.primaryRecipient = primaryRecipient;
	}

}
