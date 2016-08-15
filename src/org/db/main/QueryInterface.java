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

package org.db.main;

/**
 * In diesem Interface werden die Methoden deklariert, die in einer
 * Abfrage-Klasse mindestens enthalten sein sollen. So etwa, createTable,
 * insert, delete und update.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * @since 0.1
 */
public interface QueryInterface {
	/**
	 * Erstellt die SQL-Abfrage zum erstellen der Tabelle.
	 * 
	 * @return SQL-Abfrage zum Erstellen der Tabelle.
	 */
	public String createTable();
	
	/**
	 * Erzeugt die SQL-Abfrage, um einen Datensatz in die Tabelle einzufügen.
	 * Für die Daten werden "?" als Platzhalter benutzt.
	 * 
	 * @return SQL-Abfrage, um einen Datensatz in die Tabelle einzufügen.
	 */
	public String insert();
	
	/**
	 * Erzeugt die SQL-Abfrage zum löschen des angegebenen Datensatzes.
	 * 
	 * @param id ID des Datensatzes, der gelöscht werden soll.
	 * 
	 * @return SQL-Abfrage zum löschen des angegebenen Datensatzes.
	 */
	public String delete(int id);
	
	/**
	 * Erstellt die SQL-Abfrage zum ändern des angegebenen Datensatzes. Für die
	 * Daten werden "?" als Platzhalter benutzt.
	 * 
	 * @param id ID des Datensatzes, der geändert werden soll.
	 * 
	 * @return SQL-Abfrage zum ändern des angegebenen Datensatzes.
	 */
	public String update(int id);
	
	/**
	 * Erzeugt die SQL-Abfrage zum zurückgeben aller Datensätze.
	 * 
	 * @return SQL-Abfrage zur Ausgabe alle Datensätze.
	 */
	public String select();
	
	/**
	 * Erzeugt die SQL-Abfrage zum abfragen der Datensatz-Anzahl.
	 * 
	 * @return SQL-Abfrage zum ermitteln der Datensatz-Anzahl.
	 */
	public String count();
	
	/**
	 * Erstellt die SQL-Abfrage zum suchen eines Datensatzes.
	 * 
	 * @param col Name der Spalte, in der gesucht werden soll
	 * 
	 * @param str Zeichenfolge, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	public String search(String col, String str);
	
	/**
	 * Erstellt die SQL-Abfrage zum suchen eines Datensatzes.
	 * 
	 * @param col Name der Spalte, in der gesucht werden soll
	 * 
	 * @param number Zahl, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	public String search(String col, int number);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze.
	 * 
	 * @param col Name der Spalte, nach der sortiert werden soll
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren
	 */
	public String sort(String col);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze.
	 * 
	 * @param cols Namen der Spalten, nach denen sortiert werden soll.
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren.
	 */
	public String sort(String[] cols);
	
	/**
	 * Erzeugt die Meldung, dass ein Datensatz richtig eingefügt wurde.
	 * 
	 * @return Meldung: Datensatz wurde hinzugefügt.
	 */
	public String statusInsertOk();
	
	/**
	 * Erzeugt die Meldung, dass ein Datensatz nicht eingefügt wurde.
	 * 
	 * @return Meldung: Datensatz konnte nicht eingefügt werden
	 */
	public String statusInsertError();
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz geändert wurde.
	 * 
	 * @param id ID des Datensatzes, der geändert wurde.
	 * 
	 * @return Meldung: Datensatz wurde geändert
	 */
	public String statusUpdateOk(int id);
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz nicht geändert werden
	 * konnte.
	 * 
	 * @param id ID des Datensatzes, der nicht geändert werden konnte.
	 * 
	 * @return Meldung: Datensatz konnte nicht geändert werden
	 */
	public String statusUpdateError(int id);
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz gelöscht wurde.
	 * 
	 * @param id ID des Datensatzes, der gelöscht wurde.
	 * 
	 * @return Meldung: Datensatz wurde gelöscht
	 */
	public String statusDeleteOk(int id);
	
	/**
	 * Erzeugt die Meldung, dass der Datensatz nicht gelöscht werden konnte.
	 * 
	 * @param id ID des Datensatzes, der nicht gelöscht werden konnte.
	 * 
	 * @return Meldung: Datensatz konnte nicht gelöscht werden.
	 */
	public String statusDeleteError(int id);
}
