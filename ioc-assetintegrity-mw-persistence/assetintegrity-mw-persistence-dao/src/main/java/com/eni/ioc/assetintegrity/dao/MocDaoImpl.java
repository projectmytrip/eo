package com.eni.ioc.assetintegrity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.utils.AssetIntegrityException;

@Repository
@Transactional
public class MocDaoImpl implements MocDao{

	private static final Logger logger = LoggerFactory.getLogger(MocDaoImpl.class);
    private static final String DATABASE_TIMEZONE = "UTC";
    private static final String APPLICATION_TIMEZONE = "CET";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateSignalsMOC_DE(String asset, List<String> elements, String uid){
    	logger.debug("updateSignalMoc");

		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();

			sb2.append("(");
			for(int i=0;i<elements.size() ;i++){
				sb2.append("'").append(elements.get(i)).append("'");
				if(i!=(elements.size()-1)){
					sb2.append(", ");
				}
			}
			sb2.append(")");

			sb.append("UPDATE `ASSETINT`.`SEGNALI_WF` SET `MoC` = 1 , `LAST_UPDATE_MOC` = NOW() , `UID_UPDATE_MOC` = '"+uid+"' ");
			sb.append(" WHERE `NAME` in "+sb2.toString()+" and CHIUSO=0");

			logger.info("query updateSignalMoc: " + sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();

		} catch (Exception e) {
			logger.error("", e);
		}
    }


    @Override
    public void insertUpdateSignalsMOC(String asset, List<String> elements, String uid, int moc, String wf){
    	logger.debug("updateSignalMoc");

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("REPLACE INTO `ASSETINT`.`SEGNALI_WF_MOC` ( `NAME`, `WF_ID`, `MOC`, `UID_UPDATE_MOC`, `ASSET`)");
			sb.append(" VALUES ");
			for(int i=0;i<elements.size() ;i++){
				sb.append("( '").append(elements.get(i)).append("', '").append(wf).append("' ,").append(moc)
				.append(" , '").append(uid).append("'").append(", '").append(asset).append("'),");
			}


			logger.info("query insertUpdateSignalsMOC: " + sb.toString().substring(0, sb.length() - 1)+";");
			Query sql = entityManager.createNativeQuery(sb.toString().substring(0, sb.length() - 1)+";");
			sql.executeUpdate();

		} catch (Exception e) {
			logger.error("", e);
		}
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMoc(RegistroMoc moc) {

    	try {
            logger.debug("inserting");
            StringBuilder sb = new StringBuilder();
            sb.append(" REPLACE INTO ASSETINT.REGISTRO_MOC ");
            sb.append(" ( NUMERO, ANNO, TITOLO, RICHIEDENTE, SEGUITADA, DATAREGISTRAZIONE, TIPOMODIFICAA, TEMPORANEA, PRIORITAMODIFICA, MOTIVO, DATARIUNIONE, ");
            sb.append(" ( MODA, MODB2, MODB3, MODB4, ESITORIUNIONE, STATUSAPPROFONDIMENTI, CLASSIFICAZIONEMODIFICAPERITERAUTORIZZATIVO, TIPOMODIFICAB4, NOTE, STATOMODIFICHE, ");
            sb.append(" ( DATAFIRMAMODULOB4, INFASEDIREALIZZAZIONE, DATAAPPROVAZIONE, APPROVAZIONE, DATAAUTORIZZAZIONE, AUTORIZZAZIONE, DATAFINEPROGETTAZIONE, DATAFINEREALIZZAZIONE, ");
            sb.append(" ( REALIZZAZIONE, OTTENUTEAUTORIZZAZIONIPREVISTE, RICEVENTE, CONSEGNA, DATAASSEGNAZIONE, DATARIPRISTINO, DATACHIUSURA, CHIUSURA, PROCEDUREMANUTENZIONE, ELEMENTICRITICI, LISTASTRUMENTICRITICI, ");
            sb.append(" ( ATTIVITAPED, ATTIVITAATEX, PRATICHE, VERIFICAPSV, AGGIORNAMENTOPIANOISPEZIONI, INFOMANUTENZIONE, ALTROMAI, AUTORIZZAZIONIAMBIENTALI, ISIDONEAATTREZZATURAANTINCENDIO, ");
            sb.append(" ( DSSC, ANALISIOPERATIVARISCHIO, ANALISIASPETTIAMBIENTALI, REVISIONERISCHIPROCESSO, PROCEDURESGI, REVISIONEPEI, ALTROSSA, AGGIORNAMENTOMANUALIOPERATIVI, FORMAZIONE, ");
            sb.append(" ( ADDESTRAMENTOOPERATORI, AGGIORNAMENTOINFORMATICOSCHEDESICUREZZA, ALTROADD, CLASSIFICAZIONEAREE, PROCESSFLOWDIAGRAM, PID, SCHEMIELETTROSTRUMENTALI, SCHEMIELETTRICI, ");
            sb.append(" REGISTROALLARMI, REGISTROBLOCCHI, SISTEMICONTROLLO, AGGIORNAMENTOPLC, AGGIORNAMENTODCS, ALTROADR, PRATICHEFISCALI, UNMIG, ALTROVARIE, WFID, SEGNALI, WF_ID_RIPRISTINO ) ");
            sb.append(" VALUES ");
            sb.append(" (");
			sb.append("'").append(moc.getNumero() != null ? moc.getNumero() : "" ).append("',");
			sb.append("'").append(moc.getAnno() != null ? moc.getAnno() : "" ).append("',");
			sb.append("'").append(moc.getTitolo() != null ? moc.getTitolo() : "" ).append("',");
			sb.append("'").append(moc.getRichiedente() != null ? moc.getRichiedente() : "" ).append("',");
			sb.append("'").append(moc.getSeguitaDa() != null ? moc.getSeguitaDa() : "" ).append("',");
			sb.append("'").append(moc.getDataRegistrazione() != null ? moc.getDataRegistrazione() : "" ).append("',");
			sb.append("'").append(moc.getTipoModificaA() != null ? moc.getTipoModificaA() : "" ).append("',");
			sb.append("'").append(moc.getTemporanea() != null ? moc.getTemporanea() : "" ).append("',");
			sb.append("'").append(moc.getPrioritaModifica() != null ? moc.getPrioritaModifica() : "" ).append("',");
			sb.append("'").append(moc.getMotivo() != null ? moc.getMotivo() : "" ).append("',");
			sb.append("'").append(moc.getDataRiunione() != null ? moc.getDataRiunione() : "" ).append("',");
			sb.append("'").append(moc.getModA() != null ? moc.getModA() : "" ).append("',");
			sb.append("'").append(moc.getModB2() != null ? moc.getModB2() : "" ).append("',");
			sb.append("'").append(moc.getModB3() != null ? moc.getModB3() : "" ).append("',");
			sb.append("'").append(moc.getModB4() != null ? moc.getModB4() : "" ).append("',");
			sb.append("'").append(moc.getEsitoRiunione() != null ? moc.getEsitoRiunione() : "" ).append("',");
			sb.append("'").append(moc.getStatusApprofondimenti() != null ? moc.getStatusApprofondimenti() : "" ).append("',");
			sb.append("'").append(moc.getClassificazioneModificaPerIterAutorizzativo() != null ? moc.getClassificazioneModificaPerIterAutorizzativo() : "" ).append("',");
			sb.append("'").append(moc.getTipoModificaB4() != null ? moc.getTipoModificaB4() : "" ).append("',");
			sb.append("'").append(moc.getNote() != null ? moc.getNote() : "" ).append("',");
			sb.append("'").append(moc.getStatoModifiche() != null ? moc.getStatoModifiche() : "" ).append("',");
			sb.append("'").append(moc.getDataFirmaModuloB4() != null ? moc.getDataFirmaModuloB4() : "" ).append("',");
			sb.append("'").append(moc.getInFaseDiRealizzazione() != null ? moc.getInFaseDiRealizzazione() : "" ).append("',");
			sb.append("'").append(moc.getDataApprovazione() != null ? moc.getDataApprovazione() : "" ).append("',");
			sb.append("'").append(moc.getApprovazione() != null ? moc.getApprovazione() : "" ).append("',");
			sb.append("'").append(moc.getDataAutorizzazione() != null ? moc.getDataAutorizzazione() : "" ).append("',");
			sb.append("'").append(moc.getAutorizzazione() != null ? moc.getAutorizzazione() : "" ).append("',");
			sb.append("'").append(moc.getDataFineProgettazione() != null ? moc.getDataFineProgettazione() : "" ).append("',");
			sb.append("'").append(moc.getDataFineRealizzazione() != null ? moc.getDataFineRealizzazione() : "" ).append("',");
			sb.append("'").append(moc.getRealizzazione() != null ? moc.getRealizzazione() : "" ).append("',");
			sb.append("'").append(moc.getOttenuteAutorizzazioniPreviste() != null ? moc.getOttenuteAutorizzazioniPreviste() : "" ).append("',");
			sb.append("'").append(moc.getRicevente() != null ? moc.getRicevente() : "" ).append("',");
			sb.append("'").append(moc.getConsegna() != null ? moc.getConsegna() : "" ).append("',");
			sb.append("'").append(moc.getDataAssegnazione() != null ? moc.getDataAssegnazione() : "" ).append("',");
			sb.append("'").append(moc.getDataRipristino() != null ? moc.getDataRipristino() : "" ).append("',");
			sb.append("'").append(moc.getDataChiusura() != null ? moc.getDataChiusura() : "" ).append("',");
			sb.append("'").append(moc.getChiusura() != null ? moc.getChiusura() : "" ).append("',");
			sb.append("'").append(moc.getProcedureManutenzione() != null ? moc.getProcedureManutenzione() : "" ).append("',");
			sb.append("'").append(moc.getElementiCritici() != null ? moc.getElementiCritici() : "" ).append("',");
			sb.append("'").append(moc.getListaStrumentiCritici() != null ? moc.getListaStrumentiCritici() : "" ).append("',");
			sb.append("'").append(moc.getAttivitaPED() != null ? moc.getAttivitaPED() : "" ).append("',");
			sb.append("'").append(moc.getAttivitaATEX() != null ? moc.getAttivitaATEX() : "" ).append("',");
			sb.append("'").append(moc.getPratiche() != null ? moc.getPratiche() : "" ).append("',");
			sb.append("'").append(moc.getVerificaPSV() != null ? moc.getVerificaPSV() : "" ).append("',");
			sb.append("'").append(moc.getAggiornamentoPianoIspezioni() != null ? moc.getAggiornamentoPianoIspezioni() : "" ).append("',");
			sb.append("'").append(moc.getInfoManutenzione() != null ? moc.getInfoManutenzione() : "" ).append("',");
			sb.append("'").append(moc.getAltroMai() != null ? moc.getAltroMai() : "" ).append("',");
			sb.append("'").append(moc.getAutorizzazioniAmbientali() != null ? moc.getAutorizzazioniAmbientali() : "" ).append("',");
			sb.append("'").append(moc.getIsIdoneaAttrezzaturaAntincendio() != null ? moc.getIsIdoneaAttrezzaturaAntincendio() : "" ).append("',");
			sb.append("'").append(moc.getDssc() != null ? moc.getDssc() : "" ).append("',");
			sb.append("'").append(moc.getAnalisiOperativaRischio() != null ? moc.getAnalisiOperativaRischio() : "" ).append("',");
			sb.append("'").append(moc.getAnalisiAspettiAmbientali() != null ? moc.getAnalisiAspettiAmbientali() : "" ).append("',");
			sb.append("'").append(moc.getRevisioneRischiProcesso() != null ? moc.getRevisioneRischiProcesso() : "" ).append("',");
			sb.append("'").append(moc.getProcedureSGI() != null ? moc.getProcedureSGI() : "" ).append("',");
			sb.append("'").append(moc.getRevisionePEI() != null ? moc.getRevisionePEI() : "" ).append("',");
			sb.append("'").append(moc.getAltroSsa() != null ? moc.getAltroSsa() : "" ).append("',");
			sb.append("'").append(moc.getAggiornamentoManualiOperativi() != null ? moc.getAggiornamentoManualiOperativi() : "" ).append("',");
			sb.append("'").append(moc.getFormazione() != null ? moc.getFormazione() : "" ).append("',");
			sb.append("'").append(moc.getAddestramentoOperatori() != null ? moc.getAddestramentoOperatori() : "" ).append("',");
			sb.append("'").append(moc.getAggiornamentoInformaticoSchedeSicurezza() != null ? moc.getAggiornamentoInformaticoSchedeSicurezza() : "" ).append("',");
			sb.append("'").append(moc.getAltroAdd() != null ? moc.getAltroAdd() : "" ).append("',");
			sb.append("'").append(moc.getClassificazioneAree() != null ? moc.getClassificazioneAree() : "" ).append("',");
			sb.append("'").append(moc.getProcessFlowDiagram() != null ? moc.getProcessFlowDiagram() : "" ).append("',");
			sb.append("'").append(moc.getPID() != null ? moc.getPID() : "" ).append("',");
			sb.append("'").append(moc.getSchemiElettrostrumentali() != null ? moc.getSchemiElettrostrumentali() : "" ).append("',");
			sb.append("'").append(moc.getSchemiElettrici() != null ? moc.getSchemiElettrici() : "" ).append("',");
			sb.append("'").append(moc.getRegistroAllarmi() != null ? moc.getRegistroAllarmi() : "" ).append("',");
			sb.append("'").append(moc.getRegistroBlocchi() != null ? moc.getRegistroBlocchi() : "" ).append("',");
			sb.append("'").append(moc.getSistemiControllo() != null ? moc.getSistemiControllo() : "" ).append("',");
			sb.append("'").append(moc.getAggiornamentoPLC() != null ? moc.getAggiornamentoPLC() : "" ).append("',");
			sb.append("'").append(moc.getAggiornamentoDCS() != null ? moc.getAggiornamentoDCS() : "" ).append("',");
			sb.append("'").append(moc.getAltroAdr() != null ? moc.getAltroAdr() : "" ).append("',");
			sb.append("'").append(moc.getPraticheFiscali() != null ? moc.getPraticheFiscali() : "" ).append("',");
			sb.append("'").append(moc.getUnmig() != null ? moc.getUnmig() : "" ).append("',");
			sb.append("'").append(moc.getAltroVarie() != null ? moc.getAltroVarie() : "" ).append("',");
			sb.append("'").append(moc.getWfId() != null ? moc.getWfId() : "" ).append("', ");
			sb.append("'").append(moc.getSegnali() != null ? moc.getSegnali() : "" ).append("'").append("',");
			sb.append("'").append(moc.getWfIdRipristino() != null ? moc.getWfIdRipristino() : "" ).append("'");
            sb.append(");");

            logger.info(String.format("query insertWellAlarm: %s", sb.toString()));
            Query sql = entityManager.createNativeQuery(sb.toString());
            sql.executeUpdate();
        } catch (Exception e) {
            logger.error("Error during insertRegistoMoc ", e);
            throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
        }

    }

}
