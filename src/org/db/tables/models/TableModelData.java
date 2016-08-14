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

package org.db.tables.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.db.datas.Data;
import org.log.LogData;
import org.log.StatusBar;

/**
 * Von diesem Model werden alle Models abgeleitet, die etwas in einer Tabelle
 * anzeigen.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * @since 0.2
 */
public abstract class TableModelData extends AbstractTableModel {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Speichert die Liste für die Datensätze
	 */
	protected List<Data> _data;
	
	/**
	 * Initialisiert die Klasse.
	 */
	public TableModelData() {
		_data = new ArrayList<Data>();
		dataRefresh(false);
	}

	/**
	 * Ermittelt die Anzahl der Zeilen.
	 * 
	 * @return Anzahl der Zeilen, die die Tabelle hat.
	 */
	@Override
	public int getRowCount() {
		return _data.size();
	}

	/**
	 * Ermittelt den Inhalt der angegebenen Zelle.
	 * 
	 * @param row Zeile, in der die Zelle sich befindet.
	 * 
	 * @param col Spalte, in der die Zelle sich befindet.
	 * 
	 * @return Inhalt der mit row und col angegebenen Zelle.
	 */
	@Override
	public Object getValueAt(int row, int col) {
		// Soll die ID zurück gegeben werden?
		if (col == 0)
			return _data.get(row).getId();
		
		// Als Standard null zurück geben
		return null;
	}

	/**
	 * Holt die Daten aus der Datenbank und speichert diese in der Liste ab.
	 * 
	 * @param repaint Soll die Tabelle neu gezeichnet werden? Wird true
	 * übergeben, so wird die zu Grunde liegende Tabelle angewiesen, dass neu
	 * gezeichnet werden muss. Bei false wird dies nicht gemacht.
	 */
	public void dataRefresh(boolean repaint) {
		// Daten aus Liste löschen
		_data.clear();
		
		// Daten aus der Datenbank holen
		try {
			readDb();
		} catch (SQLException e) {
			StatusBar.getInstance().setMessage(LogData.messageError(
				"Fehler beim Zugriff auf die Tabelle '" + getTableName() + "'.",
				e));
		}
		
		// Soll die Tabelle neu gezeichnet werden?
		if (repaint)
			fireTableDataChanged();
	}
	
	/**
	 * Wird aufgerufen, um die Daten aus der Datenbank abzurufen.
	 * 
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim Zugriff auf die
	 * Datenbank erfolgt ist.
	 */
	protected abstract void readDb() throws SQLException;

	/**
	 * Ermittelt den Namen der Datenbank-Tabelle.
	 * 
	 * @return Name der Datenbank-Tabelle.
	 */
	public abstract String getTableName();

	/**
	 * Ermittelt den Datensatz, dessen Zeile angegeben wurde und gibt diesen
	 * zurück.
	 * 
	 * @param row Zeile, in der sich der Datensatz befindet.
	 * 
	 * @return Datensatz, dessen Zeile angegeben wurde.
	 */
	public Data getDataRecord(int row) {
		return _data.get(row);
	}
	
	/**
	 * Ermittelt die Klasse der Daten, die in der angegebenen Spalte vorliegen.
	 * 
	 * @param column Spalte, von der die Daten-Klasse ermittelt werden soll.
	 * 
	 * @return Klasse der Daten, die in der angegebenen Spalte vorliegen.
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		if (getValueAt(0, column) != null)
			return getValueAt(0, column).getClass();
		return Object.class;
	}
}
