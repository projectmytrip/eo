package com.eni.ioc.assetintegrity.dto;

import java.util.Date;

import com.eni.ioc.assetintegrity.entities.RegistroMoc;

public class RegistroMocDto {

	    private String numero;
	    private String wfId; 
	    private String wfIdRipristino;
		private String titolo;
		private Date dataRegistrazione;
		private Date dataApprovazione;
		private Date dataAutorizzazione;
		private Date dataFineProgettazione;
		private Date dataFineRealizzazione;
		private Date dataRipristino;
		private Date dataChiusura;
		private String inFaseDiRealizzazione;
		
		public RegistroMocDto() {
			// stub
		}
		
		public RegistroMocDto(RegistroMoc moc) {
			if(moc != null){				
				setNumero(moc.getNumero());
				setWfId(moc.getWfId());
				setWfIdRipristino(moc.getWfIdRipristino());
				setTitolo(moc.getTitolo());
				setDataRegistrazione(moc.getDataRegistrazione());
				setDataApprovazione(moc.getDataApprovazione());
				setDataAutorizzazione(moc.getDataAutorizzazione());
				setDataFineProgettazione(moc.getDataFineProgettazione());
				setDataFineRealizzazione(moc.getDataFineRealizzazione());
				setDataRipristino(moc.getDataRipristino());
				setDataChiusura(moc.getDataChiusura());
				setInFaseDiRealizzazione(moc.getInFaseDiRealizzazione());
			}
		}
		
		public RegistroMocDto(String numero, String wfId, String wfIdRipristino, String titolo, Date dataApprovazione, Date dataAutorizzazione, Date dataFineProgettazione,
								Date dataFineRealizzazione, Date dataRipristino, Date dataChiusura, Date dataRegistrazione, String inFaseDiRealizzazione) {
			setNumero(numero);
			setWfId(wfId);
			setWfIdRipristino(wfIdRipristino);
			setTitolo(titolo);
			setDataRegistrazione(dataRegistrazione);
			setDataApprovazione(dataApprovazione);
			setDataAutorizzazione(dataAutorizzazione);
			setDataFineProgettazione(dataFineProgettazione);
			setDataFineRealizzazione(dataFineRealizzazione);
			setDataRipristino(dataRipristino);
			setDataChiusura(dataChiusura);
			setInFaseDiRealizzazione(inFaseDiRealizzazione);
		}

		public Date getDataRegistrazione() {
			return dataRegistrazione;
		}
		
		public void setDataRegistrazione(Date dataRegistrazione) {
			this.dataRegistrazione = dataRegistrazione;
		}
		
		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getWfId() {
			return wfId;
		}

		public void setWfId(String wfId) {
			this.wfId = wfId;
		}
		
		public String getWfIdRipristino() {
			return wfIdRipristino;
		}

		public void setWfIdRipristino(String wfIdRipristino) {
			this.wfIdRipristino = wfIdRipristino;
		}

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public Date getDataApprovazione() {
			return dataApprovazione;
		}

		public void setDataApprovazione(Date dataApprovazione) {
			this.dataApprovazione = dataApprovazione;
		}

		public Date getDataAutorizzazione() {
			return dataAutorizzazione;
		}

		public void setDataAutorizzazione(Date dataAutorizzazione) {
			this.dataAutorizzazione = dataAutorizzazione;
		}

		public Date getDataFineProgettazione() {
			return dataFineProgettazione;
		}

		public void setDataFineProgettazione(Date dataFineProgettazione) {
			this.dataFineProgettazione = dataFineProgettazione;
		}

		public Date getDataFineRealizzazione() {
			return dataFineRealizzazione;
		}

		public void setDataFineRealizzazione(Date dataFineRealizzazione) {
			this.dataFineRealizzazione = dataFineRealizzazione;
		}

		public Date getDataRipristino() {
			return dataRipristino;
		}

		public void setDataRipristino(Date dataRipristino) {
			this.dataRipristino = dataRipristino;
		}

		public Date getDataChiusura() {
			return dataChiusura;
		}

		public void setDataChiusura(Date dataChiusura) {
			this.dataChiusura = dataChiusura;
		}
		
		public String getInFaseDiRealizzazione() {
			return inFaseDiRealizzazione;
		}

		public void setInFaseDiRealizzazione(String inFaseDiRealizzazione) {
			this.inFaseDiRealizzazione = inFaseDiRealizzazione;
		}

		@Override
		public String toString() {
			return "RegistroMocDto [numero=" + numero + ", wfId=" + wfId + ", wfIdRipristino=" + wfIdRipristino
					+ ", titolo=" + titolo + ", dataRegistrazione=" + dataRegistrazione + ", dataApprovazione="
					+ dataApprovazione + ", dataAutorizzazione=" + dataAutorizzazione + ", dataFineProgettazione="
					+ dataFineProgettazione + ", dataFineRealizzazione=" + dataFineRealizzazione + ", dataRipristino="
					+ dataRipristino + ", dataChiusura=" + dataChiusura + ", inFaseDiRealizzazione="
					+ inFaseDiRealizzazione + "]";
		}

}
