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

package tests.org.db.main;

import org.db.main.AbstractQuery;

/**
 * Implementiert für die Tests die abstrakte Klasse
 * {@link org.db.main.AbstractQuery}.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class AbstractQueryImpl extends AbstractQuery {

	/**
	 * Initialisiert die Klasse. Es wird nur der Konstruktor der Super-Klasse
	 * aufgerufen.
	 * 
	 * @param name Name der Tabelle
	 * 
	 * @see org.db.main.AbstractQuery#AbstractQuery(String)
	 */
	public AbstractQueryImpl(String name) {
		super(name);
	}

	/**
	 * Gibt {@code null} zurück.
	 */
	@Override
	public String createTable() {
		return null;
	}

}
