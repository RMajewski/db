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

import org.db.datas.Data;

/**
 * In diesem Interface werden die Methoden deklariert, die in einer
 * Abfrage-Klasse mindestens enthalten sein sollen. So etwa, createTable,
 * insert, delete und update.
 * 
 * @author René Majewski
 *
 * @version 0.4
 * Neue Methoden hinzugefügt: {@link #insert(Data)} und {@link #update(Data)}.
 * 
 * @version 0.3
 * Es kann jetzt auch in absteigender Reihenfolge sortiert werden. Neue
 * Methoden: {@link #sortDesc(String)} und {@link #sortDesc(String[])}.
 * 
 * @version 0.2
 * Neue Such-Methoden zum suchen nach boolean-Werten
 * ({@link #search(String, boolean)}), double-Werten
 * ({@link #search(String, double)}) und long-Werten
 * ({@link #search(String, long)}) hinzugefügt.
 * 
 * @version 0.1
 * Standard Methoden eingefügt.
 * 
 * @since 0.2
 */
public interface Queryable {
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
	 * Erzeugt die SQL-Abfrage, um einen Datensatz in die Tabelle einzufügen.
	 * Die Daten aus dem Datensatz werden übernommen.
	 * 
	 * @param data Datensatz, der in die Tabelle eingefügt werden sollen.
	 * 
	 * @return SQL-Abfrage, um den angegebenen Datensatz in die Tabelle
	 * einzufügen.
	 */
	public String insert(Data data);
	
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
	 * Erstellt die SQL-Abfrage zum ändern des angegebenen Datensatzes. Die
	 * Daten werden aus den übergebenen Datensatz übernommen.
	 * 
	 * @param data Datensatz, der geändert werden soll.
	 * 
	 * @return SQL-Abfrage zum ändern des angegebenen Datensatzes.
	 */
	public String update(Data data);
	
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
	 * @param search Zahl, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	public String search(String col, int search);

	/**
	 * Erstellt die SQL-Abfrage zum suchen eines Datensatzes.
	 * 
	 * @param col Name der Spalte, in der gesucht werden soll
	 * 
	 * @param search Boolscher-Wert, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	String search(String col, boolean search);

	/**
	 * Erstellt die SQL-Abfrage zum suchen eines Datensatzes.
	 * 
	 * @param col Name der Spalte, in der gesucht werden soll
	 * 
	 * @param search Long-Wert, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	String search(String col, long search);

	/**
	 * Erstellt die SQL-Abfrage zum suchen eines Datensatzes.
	 * 
	 * @param col Name der Spalte, in der gesucht werden soll
	 * 
	 * @param search Double-Wert, nach der gesucht werden soll
	 * 
	 * @return SQL-Abfrage zum suchen eines Datensatzes.
	 */
	String search(String col, double search);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze aufsteigender
	 * Anordnung.
	 * 
	 * @param col Name der Spalte, nach der sortiert werden soll
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren
	 */
	public String sort(String col);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze in aufsteigender
	 * Anordnung.
	 * 
	 * @param cols Namen der Spalten, nach denen sortiert werden soll.
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren.
	 */
	public String sort(String[] cols);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze in absteigender
	 * Anordnung.
	 * 
	 * @param col Name der Spalte, nach der sortiert werden soll
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren
	 */
	public String sortDesc(String col);
	
	/**
	 * Erstellt die SQL-Abfrage zum sortieren der Datensätze in absteigender
	 * Anordnung.
	 * 
	 * @param cols Namen der Spalten, nach denen sortiert werden soll.
	 * 
	 * @return Datenbank-Abfrage, um die Datensätze zu sortieren.
	 */
	public String sortDesc(String[] cols);

	/**
	 * Gibt den Namen der Tabelle zurück.
	 * 
	 * @return Namen der Datenbank-Tabelle.
	 */
	public String getTableName();
}
