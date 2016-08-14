/* 
* Copyright 2016 René Majewski
*  
* Lizenziert unter der EUPL, Version 1.1 oder - sobald diese von der
* Europäischen Kommission genehmigt wurden - Folgeversionen der EUPL
* ("Lizenz"); Sie dürfen dieses Werk ausschließlich gemäß dieser Lizenz
* nutzen. 
* 
* Eine Kopie der Lizenz finden Sie hier: 
* https://joinup.ec.europa.eu/software/page/eupl
*  
* Sofern nicht durch anwendbare Rechtsvorschriften gefordert oder in 
* schriftlicher Form vereinbart, wird die unter der Lizenz verbreitete 
* Software "so wie sie ist", OHNE JEGLICHE GEWÄHRLEISTUNG ODER BEDINGUNGEN -
* ausdrücklich oder stillschweigend - verbreitet.
* Die sprachspezifischen Genehmigungen und Beschränkungen unter der Lizenz
* sind dem Lizenztext zu entnehmen.
*/ 

package tests.org.db.tables.models;

import java.sql.SQLException;

import org.db.datas.Data;
import org.db.tables.models.TableModelData;

/**
 * Implementiert für die Tests die Abstrakte Klasse
 * {@link org.db.tables.models.TableModelData}. Es werden keine Funktionen
 * implementiert.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class TableModelImpl extends TableModelData {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Gibt 0 zurück.
	 * 
	 * @return 0
	 */
	@Override
	public int getColumnCount() {
		return 0;
	}

	/**
	 * Fügt zu Testzwecken ein Datensatz in die Liste ein.
	 */
	@Override
	protected void readDb() throws SQLException {
		Data data = new Data(100);
		_data.add(data);
	}

	/**
	 * Es wird null zurückgegeben.
	 * 
	 * @return null
	 */
	@Override
	public String getTableName() {
		return null;
	}

}
