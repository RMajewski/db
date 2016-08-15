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

import org.log.LogData;
import org.log.StatusBar;

/**
 * Stellt Standard Status-Nachrichten für den Datenbankzugriff bereit.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * 
 * @since 0.2
 */
public class DbStatus {
	/**
	 * Die angegebene Tabelle wurde erzeugt.
	 * 
	 * @param table Name der Tabelle, die erzeugt wurde.
	 */
	public static void createTable(String table) {
		StatusBar.getInstance().setMessage(LogData.message(
				"Datenbank: Die Tabelle '" + table + "' wurde erstellt.", ""));
	}
	
	/**
	 * Die angegebene Tabelle wurde vorbereitet.
	 * 
	 * @param table Name der Tabelle, die fertig vorbereitet wurde.
	 */
	public static void preparedTable(String table) {
		StatusBar.getInstance().setMessage(LogData.messageOk(
				"Datenbank: Die Tabelle '" + table + "' wurde vorbereitet.", 
				""));
	}
	
	/**
	 * Es ist beim Zugriff auf die Tabelle ein Fehler aufgetreten.
	 * 
	 * @param table Name der Tabelle, bei dessen Zugriff der Fehler aufgetreten
	 * ist.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void tableError(String table, Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: Fehler beim Zugriff auf die Tabelle '" + table +
				"'.", error));
	}
	
	/**
	 * Ein Datensatz wurde in die angegebene Tabelle eingefügt.
	 * 
	 * @param table Name der Tabelle, in die der Datensatz eingefügt wurde.
	 */
	public static void insertInTable(String table) {
		StatusBar.getInstance().setMessage(LogData.message(
				"Datenbank: In die Tabelle '" + table + 
				"' wurde ein Datensatz eingefügt.", ""));
	}
	
	/**
	 * Ein Datensatz konnte nicht in die angegebene Tabelle eingefügt werden.
	 * 
	 * @param table Name der Tabelle, in die der Datensatz eingefügt werden
	 * sollte.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void notInsertInTable(String table, Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: In die Tabelle '" + table + 
				"' konnte kein Datensatz eingefügt werden.", error));
	}
	
	/**
	 * Der angegebene Datensatz wurde geändert.
	 * 
	 * @param table Name der Tabelle, in der der Datensatz geändert wurde.
	 * 
	 * @param id ID des Datensatzes, der geändert wurde.
	 */
	public static void updateInTable(String table, int id) {
		StatusBar.getInstance().setMessage(LogData.message(
				"Datenbank: Der Datensatz mit der ID " + id +
				" aus der Tabelle '" + table + "' wurde geändert.", ""));
	}
	
	/**
	 * Der angegebene Datensatz konnte nicht geändert werden.
	 * 
	 * @param table Name der Tabelle, in der der Datensatz geändert werden
	 * sollte.
	 * 
	 * @param id ID des Datensatzes, der geändert werden sollte.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void notUpdateInTable(String table, int id, Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: Der Datensatz mit der ID " + id + 
				" aus der Tabelle '" + table + 
				"' konnte nicht geändert werden.", error));
	}
	
	/**
	 * Der angegebene Datensatz wurde gelöscht.
	 * 
	 * @param table Name der Tabelle, in der der Datensatz gelöscht wurde.
	 * 
	 * @param id ID des Datensatzes, der gelöscht werden sollte.
	 */
	public static void deleteFromTable(String table, int id) {
		StatusBar.getInstance().setMessage(LogData.message(
				"Datenbank: Der Datensatz mit der ID " + id + 
				" wurde aus der Tabelle '" + table + "' gelöscht.", ""));
	}
	
	/**
	 * Der angegebene Datensatz konnte nicht gelöscht werden.
	 * 
	 * @param table Name der Tabelle, in der der Datensatz gelöscht werden
	 * sollte.
	 * 
	 * @param id ID des Datensatzes, der gelöscht werden sollte.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void notDeleteFromTable(String table, int id,
			Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: Der Datensatz mit der ID " + id +
				" konnte nicht aus der Tabelle '" + table +
				"' gelöscht werden.", error));
	}
	
	/**
	 * Fehler beim Zugriff auf einen Datensatz.
	 * 
	 * @param table Name der Tabelle, aus der der Datensatz gelesen werden
	 * sollte.
	 * 
	 * @param id ID des Datensatzes, der gelesen werden sollte.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void getFromTable(String table, int id, Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: Der Datensatz mit der ID " +
				String.valueOf(id) + " konnte nicht in der Tabelle '" + table +
				"' gefunden werden.", error));
	}
	
	/**
	 * Es ist ein Fehler beim ausführen der SQL-Abfrage aufgetreten.
	 * 
	 * @param sql SQL-Abfrage, bei der der Fehler aufgetreten ist.
	 * 
	 * @param error Fehler, der aufgetreten ist.
	 */
	public static void notSql(String sql, Exception error) {
		StatusBar.getInstance().setMessage(LogData.messageError(
				"Datenbank: Es ist ein Fehler beim ausführen einer " +
				"SQL-Abfrage aufgetreten.",
				"SQL-Abfrage, bei der der Fehler aufgetreten " +
				"ist:" + System.lineSeparator() + "\t" + sql +
				System.lineSeparator() + System.lineSeparator() +
				"Fehlerbericht:" + System.lineSeparator() +
				LogData.createError(error)));
	}
	
	/**
	 * Die angegebene SQL-Abfrage wurde erfolgreich ausgeführt.
	 * 
	 * @param sql SQL-Abfrage, die erfolgreich ausgeführt wurde.
	 */
	public static void sql(String sql) {
		StatusBar.getInstance().setMessage(LogData.message(
				"Datenbank: SQL-Abfrage wurde erfolgreich ausgeführt.",
				"SQL-Abfrage, die ausgeführt wurde:" + System.lineSeparator() +
				"\t" + sql));
	}
}
