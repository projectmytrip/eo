package com.eni.ioc.assetintegrity.dao;

import java.util.List;

import com.eni.ioc.assetintegrity.entities.RegistroMoc;

/**
 * An Example DAO for JPA access
 *
 * @author generated automatically by eni-msa-mw-archetype
 *
 */
public interface MocDao {

	void insertMoc(RegistroMoc moc);

	void updateSignalsMOC_DE(String asset, List<String> elements, String uid);
	void insertUpdateSignalsMOC(String asset, List<String> elements, String uid, int moc, String wf);

}
