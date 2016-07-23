/* 
* Copyright 2016 Ren� Majewski
*  
* Lizenziert unter der EUPL, Version 1.1 oder - sobald diese von der
* Europ�ischen Kommission genehmigt wurden - Folgeversionen der EUPL
* ("Lizenz"); Sie d�rfen dieses Werk ausschlie�lich gem�� dieser Lizenz
* nutzen. 
* 
* Eine Kopie der Lizenz finden Sie hier: 
* https://joinup.ec.europa.eu/software/page/eupl
*  
* Sofern nicht durch anwendbare Rechtsvorschriften gefordert oder in 
* schriftlicher Form vereinbart, wird die unter der Lizenz verbreitete 
* Software "so wie sie ist", OHNE JEGLICHE GEW�HRLEISTUNG ODER BEDINGUNGEN -
* ausdr�cklich oder stillschweigend - verbreitet.
* Die sprachspezifischen Genehmigungen und Beschr�nkungen unter der Lizenz
* sind dem Lizenztext zu entnehmen.
*/ 

package org.db.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Von dieser Klasse werden die einzelnen Klassen abgeleitet, die
 * Datenbank-Abfragen bereit stellen.
 * 
 * Der Name der Datenbank-Tabelle und die Namen der einzelnen Spalten werden
 * dann gespeichert und k�nnen �ber Getter-Methoden abgerufen werden. 

 * @author Ren� Majewski
 * 
 * @version 0.1
 * @since 0.1
 */
public abstract class AbstractQuery implements QueryInterface {
	/**
	 * Speichert den Namen der der Datenbank-Tabelle
	 */
	protected String _tableName;
	
	/**
	 * Speichert die Liste mit den Spalten-Namen
	 */
	protected List<String> _columnNames;

	/**
	 * Initialisiert diese Klasse
	 * 
	 * @param name Name der Datenbank-Tabelle
	 */
	public AbstractQuery(String name) {
		_tableName = name;
		_columnNames = new ArrayList<String>();
	}
	
	/**
	 * �berpr�ft die angegebene ID, ob sie gr��er als <b>-1</b> ist. Ist dies
	 * der Fall, so wird die angegebene Stelle mit der ID �berschrieben. 
	 * 
	 * @param id ID, die in der Zeichenkette ersetzt werden soll.
	 * 
	 * @param builder Zeichenkette, in die die ID geschrieben werde soll.
	 * 
	 * @param last Wird <b>true</b> �bergeben, so wird das letzte Fragezeichen
	 * mit der ID �berschrieben. <b>false</b>, so wird das erste Fragezeichen
	 * mit der ID �berschrieben.
	 */
	protected void replaceId(int id, StringBuilder builder, boolean last) {
		if (id > -1)
			if (last)
				builder.replace(builder.lastIndexOf("?"),
						builder.lastIndexOf("?") + 1, String.valueOf(id));
			else
				builder.replace(builder.indexOf("?"),
						builder.indexOf("?") + 1, String.valueOf(id));
	}
	
	/**
	 * Liefert den Namen der zugeh�rigenen Datenbank-Tabelle
	 * 
	 * @return Name der Datenbank-Tabelle
	 */
	public String getTableName() {
		return _tableName;
	}
	
	/**
	 * Liefert eine Liste mit allen Spalten-Namen
	 * 
	 * @return Liste mit allen Spalten-Namen
	 */
	public List<String> getColumnNames() {
		return _columnNames;
	}
	
	/**
	 * Liefert die Anzahl der Spalten.
	 * 
	 * @return Anzahl der Spalten
	 */
	public int getCloumnCount() {
		return _columnNames.size();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, um alle Datens�tze anzuzeigen.
	 * Die Datens�tze werden nach den IDs geordnet.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * @return Datenbank-Abfrage, um alle Datens�tze aufzulisten
	 */
	@Override
	public String select() {
		return sort(null);
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, um einen neuen Datensatz in die Tabelle
	 * einzuf�gen.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * Es werden Anf�hrungszeichen um die Fragezeichen gesetzt.
	 * 
	 * @return Datenbank-Abfrage um neuen Datensatz einzuf�gen
	 */
	@Override
	public String insert() {
		return insert(true);
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, um einen neuen Datensatz in die Tabelle
	 * einzuf�gen.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * @return Datenbank-Abfrage um neuen Datensatz einzuf�gen
	 * 
	 * @param quotes Bei <b>true</b> werden die Anf�hrungszeichen gesetzt.
	 * Bei <b>false</b> werden keine Anf�hrungszeichen gesetzt.
	 */
	public String insert(boolean quotes) {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("INSERT INTO ");
		
		// Anf�hrungszeichen
		String quote = new String("");
		if (quotes)
			quote = new String("\"");
		
		// Tabellen-Namen einf�gen
		ret.append(_tableName);
		ret.append(" (");
		
		// Spalten-Namen einf�gen (id auslassen)
		boolean first = true;
		int queries = 0;
		for (int i = 0; i < _columnNames.size(); i++) {
			if (!_columnNames.get(i).equals("id")) {
				if (first)
					first = false;
				else
					ret.append(", ");
				ret.append(_columnNames.get(i));
				queries++;
			}
		}
		
		// Anzahl Fragezeichen einf�gen
		ret.append(") VALUES (");
		for (int i = 0; i < queries; i++) {
			if (i > 0)
				ret.append(", ");
			
			ret.append(quote);
			ret.append("?");
			ret.append(quote);
		}
		ret.append(");");
		
		// Abfrage zur�ck geben
		return ret.toString();
	}

	/**
	 * Erzeugt die Datenbank-Abfrage, um einen neuen Datensatz in die Tabelle
	 * einzuf�gen. Es wird auch die ID mit in die Abfrage eingebaut.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * @return Datenbank-Abfrage um neuen Datensatz einzuf�gen
	 */
	public String insertWithId() {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("INSERT INTO ");
		
		// Anf�hrungszeichen
		String quote = new String("\"");
		
		// Tabellen-Namen einf�gen
		ret.append(_tableName);
		ret.append(" (");
		
		// Spalten-Namen einf�gen (id auslassen)
		boolean first = true;
		int queries = 0;
		for (int i = 0; i < _columnNames.size(); i++) {
			if (first)
				first = false;
			else
				ret.append(", ");
			ret.append(_columnNames.get(i));
			queries++;
		}
		
		// Anzahl Fragezeichen einf�gen
		ret.append(") VALUES (");
		for (int i = 0; i < queries; i++) {
			if (i > 0)
				ret.append(", ");
			
			ret.append(quote);
			ret.append("?");
			ret.append(quote);
		}
		ret.append(");");
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, um einen neuen Datensatz zu �ndern.
	 * Wird als ID <b>-1</b> �bergeben, so wird als Platzhalter ein
	 * Fragenzeichen eingesetzt. Wenn nicht, so wird die ID mit in der
	 * Abfrage erstellt.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * @param id ID des Datensatzes, der ge�ndert werden soll.
	 * 
	 * @return Datenbank-Abfrage um einen Datensatz zu �ndern
	 */
	@Override
	public String update(int id) {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("UPDATE ");
		
		// Tabellen-Namen einf�gen
		ret.append(_tableName);
		ret.append(" SET ");
		
		// Spalten mit Fragenzeichen einf�gen ( id auslassen)
		boolean first = true;
		for (int i = 0; i < _columnNames.size(); i++) {
			if (!_columnNames.get(i).equals("id")) {
				if (first)
					first = false;
				else
					ret.append(", ");
				
				ret.append(_columnNames.get(i));
				ret.append(" = '?'");
			}
		}
		
		// Abfrage abschlie�en
		ret.append(" WHERE id = ?");
		
		// ID einf�gen
		replaceId(id, ret, true);
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, um einen neuen Datensatz l�schen.
	 * Wird als ID <b>-1</b> �bergeben, so wird als Platzhalter ein
	 * Fragenzeichen eingesetzt. Wenn nicht, so wird die ID mit in der
	 * Abfrage erstellt.
	 * 
	 * Diese Abfrage wird mit Hilfe der gespeicherten Spalten-Namen
	 * und des gespeicherten Tabellen-Namens erstellt.
	 * 
	 * @param id ID des Datensatzes, der gel�scht werden soll.
	 * 
	 * @return Datenbank-Abfrage um einen Datensatz zu l�schen
	 */
	@Override
	public String delete(int id) {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("DELETE FROM ");
		
		// Tabellen-Namen einf�gen und Abfrage abschlie�en
		ret.append(_tableName);
		ret.append(" WHERE id = ?");
		
		// ID ersetzen
		replaceId(id, ret, true);
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, in der die Anzahl der Datens�tze
	 * ermittelt wird.
	 * 
	 * @return Datenbank-Abfrage, in der Anzah der Datens�tze ermittelt wird.
	 */
	@Override
	public String count() {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("SELECT count(*) FROM '");
		ret.append(_tableName);
		ret.append("'");
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, in der ein bestimmter Datensatz
	 * ausgew�hlt werden soll.
	 * 
	 * @param col Spalte, in der gesucht werden soll.
	 * 
	 * @param str Zeichenkette nach der gesucht werden soll.
	 * 
	 * @return Datenbank-Abfrage, in der ein bestimmter Datensatz ausgew�hlt
	 * werden soll.
	 */
	@Override
	public String search(String col, String str) {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("SELECT ");
		
		// Spalten-Namen einf�gen
		for (int i = 0; i < _columnNames.size(); i++) {
			if (i == 0)
				ret.append(_columnNames.get(i));
			else {
				ret.append(", ");
				ret.append(_columnNames.get(i));
			}
		}
		
		// Tabellen-Name einf�gen
		ret.append(" FROM ");
		ret.append(_tableName);
		
		// Suche einf�gen
		ret.append(" WHERE ");
		ret.append(col);
		ret.append(" = \"");
		ret.append(str);
		ret.append("\"");
		
		// Nach was wird geordnet?
		ret.append(" ORDER BY id ASC");
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, in der ein bestimmter Datensatz
	 * ausgew�hlt werden soll.
	 * 
	 * @param col Spalte, in der gesucht werden soll.
	 * 
	 * @param str Inter-Wert, nach dem gesucht werden soll.
	 * 
	 * @return Datenbank-Abfrage, in der ein bestimmter Datensatz ausgew�hlt
	 * werden soll.
	 */
	@Override
	public String search(String col, int str) {
		// Abfrage vorbereiten
		StringBuilder ret = new StringBuilder("SELECT ");
		
		// Spalten-Namen einf�gen
		for (int i = 0; i < _columnNames.size(); i++) {
			if (i == 0)
				ret.append(_columnNames.get(i));
			else {
				ret.append(", ");
				ret.append(_columnNames.get(i));
			}
		}
		
		// Tabellen-Name einf�gen
		ret.append(" FROM ");
		ret.append(_tableName);
		
		// Suche einf�gen
		ret.append(" WHERE ");
		ret.append(col);
		ret.append(" = ");
		ret.append(str);
		
		// Nach was wird geordnet?
		ret.append(" ORDER BY id ASC");
		
		// Abfrage zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Datenbank-Abfrage, in der nach einer bestimmten Spalte
	 * sortiert werden soll.
	 * 
	 * @param col Spalte, nach der sortiert werden soll.
	 * 
	 * @return Datenbank-Abfrage, wo nach einer bestimmten Spalte sortiert
	 * wird.
	 */
	@Override
	public String sort(String col) {
		// R�ckgabe vorbereiten
		StringBuilder ret = new StringBuilder("SELECT ");
		
		// Spalten-Namen einf�gen
		for (int i = 0; i < _columnNames.size(); i++) {
			if (i == 0)
				ret.append(_columnNames.get(i));
			else {
				ret.append(", ");
				ret.append(_columnNames.get(i));
			}
		}
		
		// Tabellen-Name einf�gen
		ret.append(" FROM ");
		ret.append(_tableName);
		
		// Nach was wird geordnet?
		ret.append(" ORDER BY ");
		if (col == null || col.isEmpty())
			ret.append("id");
		else
			ret.append(col);
		ret.append(" ASC");
		
		// R�ckgabe der Datenbank-Abfrage
		return ret.toString();
	}
	
	/**
	 * Gibt die Status-Nachricht f�rs Einf�gen eines Datensatzes zur�ck.
	 * 
	 * @return Status-Nachricht f�rs Einf�gen eines Datensatzes
	 */
	@Override
	public String statusInsertOk() {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder("Datenbank: In die Tabelle '");
		ret.append(_tableName);
		ret.append("' wurde ein Datensatz eingef�gt.");
		
		// Nachricht zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Status-Nachricht, wenn der Datensatz nicht eingef�gt werden
	 * kann.
	 * 
	 * @return Status-Nachricht, wenn Datensatz nicht eingef�gt werden konnte.
	 */
	@Override
	public String statusInsertError() {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder("Datenbank: In die Tabelle '");
		ret.append(_tableName);
		ret.append("' konnte kein Datensatz eingef�gt werden.");
		
		// Nachricht zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Status-Nachricht, f�rs �ndern einen Datensatzes.
	 * 
	 * @return Status-Nachricht, wenn Datensatz ge�ndert wurde.
	 */
	@Override
	public String statusUpdateOk(int id) {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder();
		ret.append("Datenbank: Der Datensatz mit der ID ");
		ret.append(id);
		ret.append(" aus der Tabelle '");
		ret.append(_tableName);
		ret.append("' wurde ge�ndert.");
		
		// nachrihct zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Status-Nachricht, wenn der Datensatz nicht ge�ndert werden
	 * kann.
	 * 
	 * @return Status-Nachricht, wenn Datensatz nicht ge�ndert werden konnte
	 */
	@Override
	public String statusUpdateError(int id) {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder();
		ret.append("Datenbank: Der Datensatz mit der ID ");
		ret.append(id);
		ret.append(" aus der Tabelle '");
		ret.append(_tableName);
		ret.append("' konnte nicht ge�ndert werden.");
		
		// Nachricht zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Status-Nachrciht, wenn ein Datensatz gel�scht wurde.
	 * 
	 * @return Status-Nachricht, wenn Datensatz gel�scht wurde.
	 */
	@Override
	public String statusDeleteOk(int id) {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder();
		ret.append("Datenbank: Der Datensatz mit der ID ");
		ret.append(id);
		ret.append(" wurde aus der Tabelle '");
		ret.append(_tableName);
		ret.append("' gel�scht.");
		
		// Nachricht zur�ck geben
		return ret.toString();
	}
	
	/**
	 * Erzeugt die Status-Nachricht, wenn ein Datensatz nicht gel�scht werden
	 * konnte.
	 * 
	 * @return Status-Nachricht, wenn Datensatz nicht gel�scht werden konnte.
	 */
	@Override
	public String statusDeleteError(int id) {
		// Nachricht vorbereiten
		StringBuilder ret = new StringBuilder();
		ret.append("Datenbank: Der Datensatz mit der ID ");
		ret.append(id);
		ret.append(" konnte nicht aus der Tabelle '");
		ret.append(_tableName);
		ret.append("' gel�scht werden.");
		
		// Nachricht zur�ck geben
		return ret.toString();
	}
}
