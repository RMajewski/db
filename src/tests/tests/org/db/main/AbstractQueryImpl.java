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

import org.db.datas.Data;
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
	 * Speichert, ob ein Fehler bei den SQL-Abfragen auftreten soll oder nicht.
	 */
	private boolean _error;

	/**
	 * Initialisiert die Klasse. Es wird nur der Konstruktor der Super-Klasse
	 * aufgerufen.
	 * 
	 * @param name Name der Tabelle
	 * 
	 * @see org.db.main.AbstractQuery#AbstractQuery(String)
	 */
	public AbstractQueryImpl(String name) {
		this (name, false);
	}

	/**
	 * Initialisiert die Klasse. Es wird gespeichert, ob bei den SQL-Abfragen
	 * ein Fehler auftreten soll oder nicht.
	 * 
	 * @param table Name der Tabelle.
	 * 
	 * @param error Soll ein Fehler bei den SQL-Abfragen auftreten? Wird true
	 * übergeben, so wird in den SQL-Abfragen ein Fehler erzeugt. Bei false,
	 * werden sie ohne Fehler erzeugt.
	 */
	public AbstractQueryImpl(String table, boolean error) {
		super(table);
		_error = error;
		_columnNames.add("id");
		_columnNames.add("test");
	}

	/**
	 * Gibt die Create-SQL-Abfrage zurück. Die Tabelle hat nur einen Spalte für
	 * die ID und eine für Tests.
	 * 
	 * @return Create SQL-Abfrage
	 */
	@Override
	public String createTable() {
		if (_error)
			return "INSERT TABLE IF NOT EXISTS" + getTableName() + 
					"('id' INTEGER, 'test' STRING)";
		else
			return "CREATE TABLE IF NOT EXISTS " + getTableName() + 
					"('id' INTEGER, 'test' STRING)";
	}

	/**
	 * Fügt einen Datensatz in die Tabelle ein.
	 * 
	 * @param data Datensatz, der in die Tabelle eingefügt werden soll.
	 */
	@Override
	public String insert(Data data) {
		StringBuilder result = new StringBuilder(insert(true, true));
		
		result.replace(result.indexOf("?"), result.indexOf("?") + 1,
				String.valueOf(data.getId()));
		
		result.replace(result.indexOf("?"), result.indexOf("?") + 1,
				"Dies ist ein Test!");
		
		if (_error)
			result.replace(result.indexOf("test"), result.indexOf("test") + 4,
					"test3");
		
		return result.toString();
	}

	/**
	 * Ändert den angegebenen Datensatz.
	 * 
	 * @param data Datensatz, der geändert werden soll.
	 */
	@Override
	public String update(Data data) {
		StringBuilder result = new StringBuilder(update(data.getId()));
		
		result.replace(result.indexOf("?"), result.indexOf("?") + 1,
				"Dies ist ein Test!");
		
		if (_error)
			result.replace(result.indexOf("id"), result.indexOf("id") + 2,
					"test3");
		
		return result.toString();
	}

	/**
	 * Löscht den angegebenen Datensatz. Es wird eine Variante hinzugefügt, die
	 * die SQLException auslöst.
	 * 
	 * @param id ID des Datensatzes, der gelöscht werden soll.
	 */
	@Override
	public String delete(int id) {
		if (_error)
			return super.delete(id).replace("id", "testen");
		
		return super.delete(id);
	}
}
