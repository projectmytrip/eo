package com.eni.ioc.dao;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.common.CovaFlaringAD;
import com.eni.ioc.common.CovaFlaringEvent;
import com.eni.ioc.common.CovaRootCause;
import com.eni.ioc.common.CovaSo2Average;
import com.eni.ioc.common.CovaSo2Impact;
import com.eni.ioc.common.CovaSo2Prediction;
import com.eni.ioc.common.EmissionClouderaException;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.service.Sender;

public class DaoUtils {

	private static final Logger logger = LoggerFactory.getLogger(DaoUtils.class);

	private static String JDBCDriver = ApplicationProperties.getInstance().getClouderaJdbcDriver();
	private static String user = ApplicationProperties.getInstance().getClouderaJdbcUser();
    private static String pwd = ApplicationProperties.getInstance().getClouderaJdbcPwd();
	private static String jdbcUrl = ApplicationProperties.getInstance().getClouderaJdbcUrl() + "UID="
			+ ApplicationProperties.getInstance().getClouderaJdbcUser() + ";PWD="
			+ ApplicationProperties.getInstance().getClouderaJdbcPwd();

	private static String jdbcUrl_krb = CustomConfigurations.getProperty("cloudera.kerberos.jdbc.url");
			
	private DaoUtils() {
	}
	
	private static Connection getConnection() throws IOException, InterruptedException {
		
		logger.debug("jdbcUrl_krb: " + jdbcUrl_krb);
		
		UserGroupInformation ugi = KerberosAuthentication.getInstance();
		Connection con = (Connection) ugi.doAs(new PrivilegedExceptionAction<Object>() {
			    public Object run() {
			        Connection tcon = null;
			        try {
			          tcon = DriverManager.getConnection(jdbcUrl_krb);
			        } catch (SQLException e) {
			        	logger.error("SQLException when establishing the connection", e);
			        }
			        return tcon;
			      }
			});
		
		return con;
	}
	
	public static List<CovaSo2Prediction> retrieveSo2PredictiveProbabilities(Date startDate, Date endDate) {
		List<CovaSo2Prediction> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveSo2PredictiveProbabilities -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);

            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);
		
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT mo.`dt`, mo.proba");
			querySql.append(" FROM pmc_stage.pmc_l1_cova_so2_predictive_pred mo");
			querySql.append(" WHERE mo.`dt` BETWEEN '"+sDate+"' AND '"+eDate+"'");
			querySql.append(" ORDER BY mo.`dt` DESC");

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {
				
				logger.debug("Retrieving result from pmc_l1_cova_so2_predictive_pred");

				while (rs.next()) {
					CovaSo2Prediction c = new CovaSo2Prediction();
					Timestamp timestamp = rs.getTimestamp("dt");

					if (timestamp != null) {
						c.setDatetime(new Date(timestamp.getTime()));
					}
                	
                    c.setProba(rs.getFloat("proba"));
                    results.add(c);
				}
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error(e.getMessage());
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}
	
	public static List<CovaRootCause> retrieveRootCause(Date startDate, Date endDate) {
		List<CovaRootCause> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveTdaStatus -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);


			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT mo.`dt`, mo.pred_label__claus__200, mo.pred_label__claus__300, mo.pred_label__claus__400, mo.pred_label__td__a, mo.pred_label__td__b, runid");
			querySql.append(" FROM pmc_stage.pmc_l1_cova_e15_root_cause_pred mo");
			querySql.append(" WHERE mo.`dt` BETWEEN '"+sDate+"' AND '"+eDate+"'");
			querySql.append(" ORDER BY mo.`dt` DESC");

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {
				
				logger.debug("Retrieving result from pmc_l1_cova_e15_root_cause_pred");

				while (rs.next()) {
					CovaRootCause c = new CovaRootCause();
					
					Timestamp timestamp = rs.getTimestamp("dt");
					if (timestamp != null) {
						c.setDatetime(new Date(timestamp.getTime()));
					}
                	c.setCLAUS200(rs.getString("pred_label__claus__200"));
                	c.setCLAUS300(rs.getString("pred_label__claus__300"));
                	c.setCLAUS400(rs.getString("pred_label__claus__400"));
                	c.setRunid(rs.getString("runid"));
                	c.setTDA(rs.getString("pred_label__td__a"));
                	c.setTDB(rs.getString("pred_label__td__b"));
                    results.add(c);
				}
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error(e.getMessage());
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}
	
	public static List<CovaFlaringAD> retrieveFlaringAnomaly(Date startDate, Date endDate, String tableName, String keyName) {
		List<CovaFlaringAD> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveFlaringAnomaly -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
            
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);


			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT tr.event_start as INIZIO_ALLARME, tr.event_end as FINE_ALLARME, tr.tag as TAG, ");
			querySql.append(" reg.descriptor as DESCRIZIONE, SUBSTR(tr.tag,10,4) as UNITA, tr.`from`, tr.`to`, tr.`importance`, reg.`engunits` as UNITA_MISURA");
			querySql.append(" FROM pmc_stage." +  tableName + " tr");
			querySql.append(" LEFT JOIN pmc_stage.pmc_cova_pipoint_registry reg");
			querySql.append(" ON split_part(tr.tag, \"__\", 1) = LOWER(reg.tag)");
			querySql.append(" WHERE tr.event_start >='"+sDate+"' OR (tr.event_end >='"+ sDate+ "' OR tr.event_end IS NULL)");
			querySql.append(" ORDER BY tr.event_start DESC, tr.`importance` ASC");			

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {
				
				logger.debug("Retrieving result from " + tableName);

				while (rs.next()) {
					CovaFlaringAD c = new CovaFlaringAD();
					
					Timestamp timestampS = rs.getTimestamp("INIZIO_ALLARME");
					Timestamp timestampF = rs.getTimestamp("FINE_ALLARME");

					if (timestampS != null) {
						c.setsAlarm(new Date(timestampS.getTime()));
					}
					if (timestampF != null) {
						c.seteAlarm(new Date(timestampF.getTime()));
					}
					
					c.setDescription(rs.getString("DESCRIZIONE"));
					c.setFrom(rs.getFloat("from"));
					c.setImportance(rs.getString("importance"));
					c.setTag(rs.getString("TAG"));
					c.setTo(rs.getFloat("to"));
					c.setUnit(rs.getString("UNITA"));
					c.setMeasureUnit(rs.getString("UNITA_MISURA"));
					c.setKeyName(keyName);
                    results.add(c);
				}
				
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error("retrieveFlaringAnomaly", e);
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}

	public static List<CovaSo2Impact> retrieveSo2PredictiveImpacts(Date startDate, Date endDate) {
		List<CovaSo2Impact> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveSo2PredictiveImpacts -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
			
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);
            
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT me.`dt`, me.variable, me.impact, me.median_values, me.point_value, ");
			querySql.append("         CASE upper(split_part(variable, \"__\", 2)) ");
			querySql.append("             WHEN '' THEN tr.descriptor ");
			querySql.append("             ELSE concat_ws( ' - ',  ");
			querySql.append("                  tr.descriptor, ");
			querySql.append("                  concat_ws(  '',");
			querySql.append("                     regexp_replace(upper(split_part(variable, '__', 2)), '_', ' '),");
			querySql.append("                     'h'");
			querySql.append("                   )");
			querySql.append("           )");
			querySql.append("         END descriptor,");
			querySql.append("         tr.engunits");
			querySql.append(" FROM");
			querySql.append("         pmc_stage.pmc_l1_cova_so2_predictive_impacts me");
			querySql.append(" LEFT JOIN");
			querySql.append("         pmc_stage.pmc_cova_pipoint_registry tr");
			querySql.append(" ON");
			querySql.append("         split_part(variable, \"__\", 1) = LOWER(tr.tag)");
			querySql.append(" WHERE    me.`dt` BETWEEN '"+sDate+"' AND '"+eDate+"'");
			querySql.append(" ORDER BY");
			querySql.append("          me.`dt` DESC,");
			querySql.append("          me.impact DESC");
			querySql.append(";");

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {

				logger.debug("Retrieving result from pmc_l1_cova_so2_predictive_impacts and pmc_cova_pipoint_registry");

				while (rs.next()) {
					CovaSo2Impact c = new CovaSo2Impact();
					
					Timestamp timestamp = rs.getTimestamp("dt");
					if (timestamp != null) {
						c.setDatetime(new Date(timestamp.getTime()));
					}
					
                    c.setFeature(rs.getString("variable"));
                    c.setImpact(rs.getFloat("impact"));
                    c.setMedian(rs.getFloat("median_values"));
                    c.setValue(rs.getFloat("point_value"));
                    c.setDescriptor(rs.getString("descriptor"));
                    c.setEngunits(rs.getString("engunits"));
                    results.add(c);
				}
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
            
            return results;
		}catch(Exception e){
			logger.error("retrieveSo2PredictiveImpacts", e);
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}
	
	public static List<CovaSo2Average> retrieveSo2Average(Date startDate, Date endDate) {
		List<CovaSo2Average> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveSo2PredictiveProbabilities -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
            
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);
            

			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT mo.`datetime`, mo.mape, mo.p, mo.prediction");
			querySql.append(" FROM pmc_stage.pmc_l1_cova_emissions_nofs_reg mo");
			querySql.append(" WHERE mo.`datetime` BETWEEN '"+sDate+"' AND '"+eDate+"'");
			querySql.append(" ORDER BY mo.`datetime` DESC");

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {

				logger.debug("Retrieving result from pmc_l1_cova_emissions_nofs_reg");

				while (rs.next()) {
					CovaSo2Average c = new CovaSo2Average();
					
					Timestamp timestamp = rs.getTimestamp("datetime");
					if (timestamp != null) {
						c.setDatetime(new Date(timestamp.getTime()));
					}
                	
                    c.setMape(rs.getFloat("mape"));
                    c.setP(rs.getFloat("p"));
                    c.setPrediction(rs.getFloat("prediction"));

                    results.add(c);
				}
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error("retrieveSo2Average", e);
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}
	
	public static List<CovaSo2Prediction> retrieveFlaringPredictive(Date startDate, Date endDate, String tableName, String keyName) {
		List<CovaSo2Prediction> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveFlaringPredictive -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
            
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);


            StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT mo.`dt`, mo.proba");
			querySql.append(" FROM pmc_stage." + tableName + " mo");
			querySql.append(" WHERE mo.`dt` BETWEEN '"+sDate+"' AND '"+eDate+"'");
			querySql.append(" ORDER BY mo.`dt` DESC");	

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {
				
				logger.debug("Retrieving result from " + tableName);

				while (rs.next()) {
					CovaSo2Prediction c = new CovaSo2Prediction();
					
					Timestamp timestamp = rs.getTimestamp("dt");
					if (timestamp != null) {
						c.setDatetime(new Date(timestamp.getTime()));
					}
                	
                    c.setProba(rs.getFloat("proba"));
                    c.setKeyName(keyName);
                    results.add(c);
				}
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error("retrieveFlaringPredictive", e);
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}

	public static List<CovaFlaringEvent> retrieveFlaringEvent(Date startDate, Date endDate, String tableName, String keyName) {
		List<CovaFlaringEvent> results = new ArrayList<>();
		try {
            
			logger.info("Calling Cloudera for retrieveFlaringEvent -> [startDate: "+startDate+", endDate: "+endDate+"]");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = df.format(startDate);
            String eDate = df.format(endDate);
            
            logger.debug("sDate: " + sDate);
            logger.debug("eDate: " + eDate);


			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT average_emission, event_end, event_length, event_start, max_emission, total_emission, runid ");
			querySql.append(" FROM pmc_stage." +  tableName);
			querySql.append(" WHERE event_start >='"+sDate+"' OR (event_end >='"+ sDate+ "' OR event_end IS NULL)");
			querySql.append(" ORDER BY event_start DESC");			

			Class.forName(JDBCDriver);
			try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(querySql.toString()) ) {
				
				logger.debug("Retrieving result from " + tableName);

				while (rs.next()) {
					CovaFlaringEvent c = new CovaFlaringEvent();
					
					Timestamp timestampS = rs.getTimestamp("event_start");
					Timestamp timestampF = rs.getTimestamp("event_end");

					if (timestampS != null) {
						c.setStartEvent(new Date(timestampS.getTime()));
					}
					if (timestampF != null) {
						c.setEndEvent(new Date(timestampF.getTime()));
					}
					
					c.setAvgEmission(rs.getFloat("average_emission"));
					c.setEventLength(rs.getInt("event_length"));
					c.setMaxEmission(rs.getFloat("max_emission"));
					c.setTotalEmission(rs.getFloat("total_emission"));
					c.setId(rs.getString("runid"));
					c.setKeyName(keyName);
                    results.add(c);
				}
				
				Sender.sendSystemUp();
			} catch (SQLException se) {
				logger.error("SQLException", se);
				Sender.sendSystemDown();
			} catch (Exception e) {
				logger.error("Exception", e);
				Sender.sendSystemDown();
			}
			
            return results;
		}catch(Exception e){
			logger.error("retrieveFlaringEvent", e);
			trySendingSysDown();
			throw new EmissionClouderaException(EmissionClouderaException.CodeError.dbError, e.getMessage());
		}
	}

	private static void trySendingSysDown(){
		try {
			Sender.sendSystemDown();
		} catch (Exception e1) {
			logger.error("could not sendSystemDown", e1);
		}
	}
}
