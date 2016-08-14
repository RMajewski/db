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
 * Speichert zusätzlich zu der ID die Beschreibung.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * @since 0.1
 */
public class DataDescription extends Data {
	/**
	 * Speichert die Beschreibung.
	 */
	private String _description;
	
	/**
	 * Initialisiert die Daten mit standard Werten.
	 */
	public DataDescription() {
		super();
		_description = new String();
	}
	
	/**
	 * Initialisiert die Daten mit den übergebenen Werten.
	 * 
	 * @param id ID des Datensatzes.
	 * 
	 * @param description Beschreibung des Datensatzes.
	 */
	public DataDescription(int id, String description) {
		super(id);
		_description = description;
	}
	
	/**
	 * Setzt die Beschreibung auf den übergebenen Wert.
	 * 
	 * @param description Neue Beschreibung des Datensatzes.
	 */
	public void setDescription(String description) {
		_description = description;
	}
	
	/**
	 * Gibt die gespeicherte Beschreibung zurück.
	 * 
	 * @return Beschreibung, die für den Datensatz gespeichert wurde.
	 */
	public String getDescription() {
		return _description;
	}
}
