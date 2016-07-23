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

/**
 * In diesem Interface werden die Methoden deklariert, die in einer
 * Abfrage-Klasse mindestens enthalten sein sollen. So etwa, createTable,
 * insert, delete und update.
 * 
 * @author Ren� Majewski
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
	 * Erzeugt die SQL-Abfrage, um einen Datensatz in die Tabelle einzuf�gen.
	 * F�r die Daten werden "?" als Platzhalter benutzt.
	 * 
	 * @return SQL-Abfrage, um einen Datensatz in die Tabelle einzuf�gen.
	 */
	public String insert();
	
	/**
	 * Erzeugt die SQL-Abfrage zum l�schen des angegebenen Datensatzes.
	 * 
	 * @param id ID des Datensatzes, der gel�scht werden soll.
	 * 
	 * @return SQL-Abfrage zum l�schen des angegebenen Datensatzes.
	 */
	public String delete(int id);
	
	/**
	 * Erstellt die SQL-Abfrage zum �ndern des angegebenen Datensatzes. F�r die
	 * Daten werden "?" als Platzhalter benutzt.
	 * 
	 * @param id ID des Datensatzes, der ge�ndert werden soll.
	 * 
	 * @return SQL-Abfrage zum �ndern des angegebenen Datensatzes.
	 */
	public String update(int id);
	
	/**
	 * Erzeugt die SQL-Abfrage zum zur�ckgeben aller Datens�tze.
	 * 
	 * @return SQL-Abfrage zur Ausgabe alle Datens�tze.
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
	 * Erstellt die SQL-Abfrage zum sortieren der Datens�tze.
	 * 
	 * @param col Name der Spalte, nach der sortiert werden soll
	 * 
	 * @return Datenbank-Abfrage, um die Datens�tze zu sortieren
	 */
	public String sort(String col);
	
	/**
	 * Erzeugt die Meldung, dass ein Datensatz richtig eingef�gt wurde.
	 * 
	 * @return Meldung: Datensatz wurde hinzugef�gt.
	 */
	public String statusInsertOk();
	
	/**
	 * Erzeugt die Meldung, dass ein Datensatz nicht eingef�gt wurde.
	 * 
	 * @return Meldung: Datensatz konnte nicht eingef�gt werden
	 */
	public String statusInsertError();
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz ge�ndert wurde.
	 * 
	 * @param id ID des Datensatzes, der ge�ndert wurde.
	 * 
	 * @return Meldung: Datensatz wurde ge�ndert
	 */
	public String statusUpdateOk(int id);
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz nicht ge�ndert werden
	 * konnte.
	 * 
	 * @param id ID des Datensatzes, der nicht ge�ndert werden konnte.
	 * 
	 * @return Meldung: Datensatz konnte nicht ge�ndert werden
	 */
	public String statusUpdateError(int id);
	
	/**
	 * Erzeugt die Meldung, dass der angegebene Datensatz gel�scht wurde.
	 * 
	 * @param id ID des Datensatzes, der gel�scht wurde.
	 * 
	 * @return Meldung: Datensatz wurde gel�scht
	 */
	public String statusDeleteOk(int id);
	
	/**
	 * Erzeugt die Meldung, dass der Datensatz nicht gel�scht werden konnte.
	 * 
	 * @param id ID des Datensatzes, der nicht gel�scht werden konnte.
	 * 
	 * @return Meldung: Datensatz konnte nicht gel�scht werden.
	 */
	public String statusDeleteError(int id);
}
