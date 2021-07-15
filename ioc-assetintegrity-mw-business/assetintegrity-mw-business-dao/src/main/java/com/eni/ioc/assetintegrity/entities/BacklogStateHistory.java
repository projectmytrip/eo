package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BACKLOG_STATE_HISTORY")
public class BacklogStateHistory {

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "N_ODM")
	private String nOdm;

	@Column(name = "STATO_UTENTE_ODM")
	private String statoUtenteOdm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INIZIO_STATO")
	private Date dataInizioStato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_FINE_STATO")
	private Date dataFineStato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getnOdm() {
		return nOdm;
	}

	public void setnOdm(String nOdm) {
		this.nOdm = nOdm;
	}

	public String getStatoUtenteOdm() {
		return statoUtenteOdm;
	}

	public void setStatoUtenteOdm(String statoUtenteOdm) {
		this.statoUtenteOdm = statoUtenteOdm;
	}

	public Date getDataInizioStato() {
		return dataInizioStato;
	}

	public void setDataInizioStato(Date dataInizioStato) {
		this.dataInizioStato = dataInizioStato;
	}

	public Date getDataFineStato() {
		return dataFineStato;
	}

	public void setDataFineStato(Date dataFineStato) {
		this.dataFineStato = dataFineStato;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}
}
