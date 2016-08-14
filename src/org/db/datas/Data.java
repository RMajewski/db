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

package org.db.datas;

/**
 * Von dieser Klasse werden alle Klassen für die Datensätze abgeleitet.
 * 
 * In dieser Klasse wird die ID gespeichert.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * @since 0.2
 */
public class Data {
	/**
	 * Speichert die ID des Datensatzes.
	 */
	private int _id;
	
	/**
	 * Initialisiert die ID mit -1.
	 */
	public Data() {
		_id = -1;
	}
	

	/**
	 * Initialisiert die Klasse. Die ID wird gespeichert, die übergeben wurde.
	 * 
	 * @param id ID für diesen Datensatz.
	 */
	public Data(int id) {
		_id = id;
	}
	
	/**
	 * Setzt die ID auf die übergebene ID.
	 * 
	 * @param id Neue ID für diesen Datensatz.
	 */
	public void setId(int id) {
		_id = id;
	}
	
	/**
	 * Gibt die gespeicherte ID zurück.
	 * 
	 * @return ID, die gespeichert ist.
	 */
	public int getId() {
		return _id;
	}
}
