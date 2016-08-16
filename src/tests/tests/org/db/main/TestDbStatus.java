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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.db.main.DbStatus;
import org.junit.Before;
import org.junit.Test;
import org.log.LogData;
import org.log.StatusBar;

/**
 * Testet die Klasse {@link org.db.main.DbStatus}.
 * 
 * @author René Majewski
 *
 * @since 0.2
 */
public class TestDbStatus {
	/**
	 * Speichert den Namen der Tabelle.
	 */
	private String _tableName;
	
	/**
	 * Speichert den Fehler.
	 */
	private Exception _error;
	
	/**
	 * Speichert die Fehlermeldung.
	 */
	private String _errorMessage;
	
	/**
	 * Speichert die ID.
	 */
	private int _id;
	
	/**
	 * Initialisiert die Tests.
	 */
	@Before
	public void setUp() {
		_tableName = "test";
		_error = new Exception();
		_errorMessage = LogData.createError(_error);
		_id = 100;
	}
	
	/**
	 * Ermittelt die letzte Nachricht, die in das Logbuch eingefügt wurde.
	 * 
	 * @return Letzt Nachricht aus dem Logbuch.
	 */
	private LogData getLastLog() {
		List<LogData> list = StatusBar.getInstance().getLog();
		return list.get(list.size() - 1);
	}
	
	/**
	 * Testet, ob der letzte Eintrag im Logbuch eine Nachricht ist, die keine
	 * Fehlermeldung hat und den angegebenen Text beinhaltet.
	 * 
	 * @param message Nachricht, die der letzte Logbuch-Eintrag enthalten soll.
	 */
	private void testMessage(String message) {
		LogData data = getLastLog();
		
		assertThat(data.getOut(), is(LogData.DATABASE_INSERT));
		assertThat(data.getError(), isEmptyString());
		assertThat(data.getMessage(), is(message));
	}
	
	/**
	 * Testet, ob der übergebene LogData-Eintrag den Vorgaben entspricht.
	 * 
	 * @param message Nachricht, die der LogData-Eintrag enthalten soll.
	 * 
	 * @param data LogData-Eintrag, der auf Richtigkeit überprüft werden soll.
	 */
	private void testMessageData(String message, LogData data) {
		assertThat(data.getOut(), is(LogData.DATABASE_INSERT));
		assertThat(data.getError(), isEmptyString());
		assertThat(data.getMessage(), is(message));
	}
	
	/**
	 * Testet, ob der letzte Eintrag im Logbuch eine Fehler-Nachricht ist, die
	 * den angegebenen Text und den angegebenen Fehler beinhaltet.
	 * 
	 * @param message Nachricht, die der letzte Logbuch-Eintrag enthalten soll.
	 * 
	 * @param error Fehler, den der letzte Logbuch-Eintrag enthalten soll.
	 */
	private void testError(String message, String error) {
		LogData data = getLastLog();
		
		assertThat(data.getOut(), is (LogData.DATABASE_ERROR));
		assertThat(data.getError(), is(error));
		assertThat(data.getMessage(), is(message));
	}
	
	/**
	 * Testet, ob der übergebene LogData-Eintrag den Vorgaben entspricht.
	 * 
	 * @param message Nachricht, die der letzte Logbuch-Eintrag enthalten soll.
	 * 
	 * @param error Fehler, den der letzte Logbuch-Eintrag enthalten soll.
	 * 
	 * @param data LogData-Eintrag, der auf Richtigkeit überprüft werden soll.
	 */
	private void testErrorData(String message, String error, LogData data) {
		assertThat(data.getOut(), is (LogData.DATABASE_ERROR));
		assertThat(data.getError(), is(error));
		assertThat(data.getMessage(), is(message));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#createTable(java.lang.String)
	 */
	@Test
	public void testCreateTable() {
		DbStatus.createTable(_tableName);
		
		testMessage("Datenbank: Die Tabelle '" + _tableName + 
				"' wurde erstellt.");
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#createTable(java.lang.String)
	 */
	@Test
	public void testCreateTableMessage() {
		testMessageData("Datenbank: Die Tabelle '" + _tableName + 
				"' wurde erstellt.", DbStatus.createTableMessage(_tableName));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#preparedTable(java.lang.String)
	 */
	@Test
	public void testPreparedTable() {
		DbStatus.preparedTable(_tableName);
		LogData data = getLastLog();
		
		assertThat(data.getOut(), is(LogData.OK));
		assertThat(data.getError(), isEmptyString());
		assertThat(data.getMessage(), is("Datenbank: Die Tabelle '" +
				_tableName + "' wurde vorbereitet."));
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#preparedTable(java.lang.String)
	 */
	@Test
	public void testPreparedTableMessage() {
		LogData data = DbStatus.preparedTableMessage(_tableName);
		
		assertThat(data.getOut(), is(LogData.OK));
		assertThat(data.getError(), isEmptyString());
		assertThat(data.getMessage(), is("Datenbank: Die Tabelle '" +
				_tableName + "' wurde vorbereitet."));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#tableError(java.lang.String,
	 * java.lang.Exception)
	 */
	@Test
	public void testTableError() {
		DbStatus.tableError(_tableName, _error);
		
		testError("Datenbank: Fehler beim Zugriff auf die Tabelle '" +
				_tableName + "'.", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#createTable(java.lang.String)
	 */
	@Test
	public void testTableErrorMessage() {
		testErrorData("Datenbank: Fehler beim Zugriff auf die Tabelle '" +
				_tableName + "'.", _errorMessage,
				DbStatus.tableErrorMessage(_tableName, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#insertInTable(java.lang.String)
	 */
	@Test
	public void testInsertInTable() {
		DbStatus.insertInTable(_tableName);
		
		testMessage("Datenbank: In die Tabelle '" + _tableName + 
				"' wurde ein Datensatz eingefügt.");
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#createTable(java.lang.String)
	 */
	@Test
	public void testInsertInTableMessage() {
		testMessageData("Datenbank: In die Tabelle '" + _tableName + 
				"' wurde ein Datensatz eingefügt.",
				DbStatus.insertInTableMessage(_tableName));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#notInsertInTable(java.lang.String,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotInsertInTable() {
		DbStatus.notInsertInTable(_tableName, _error);
		
		testError("Datenbank: Der Datensatz konnte nicht in die Tabelle '" +
				_tableName +"' eingefügt werden", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#notInsertInTable(java.lang.String,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotInsertInTableMessage() {
		testErrorData("Datenbank: Der Datensatz konnte nicht in die Tabelle '" +
				_tableName +"' eingefügt werden", _errorMessage,
				DbStatus.notInsertInTableMessage(_tableName, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#updateInTable(java.lang.String, int)
	 */
	@Test
	public void testUpdateInTable() {
		DbStatus.updateInTable(_tableName, _id);
		
		testMessage("Datenbank: Der Datensatz mit der ID " +
				String.valueOf(_id) + " aus der Tabelle '" + _tableName +
				"' wurde geändert.");
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#updateInTable(java.lang.String, int)
	 */
	@Test
	public void testUpdateInTableMessage() {
		testMessageData("Datenbank: Der Datensatz mit der ID " +
				String.valueOf(_id) + " aus der Tabelle '" + _tableName +
				"' wurde geändert.",
				DbStatus.updateInTableMessage(_tableName, _id));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#notUpdateInTable(java.lang.String, int,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotUpdateInTable() {
		DbStatus.notUpdateInTable(_tableName, _id, _error);
		
		testError("Datenbank: Der Datensatz mit der ID " + _id + 
				" aus der Tabelle '" + _tableName + 
				"' konnte nicht geändert werden.", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#notInsertInTable(java.lang.String,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotUpdateInTableMessage() {
		testErrorData("Datenbank: Der Datensatz mit der ID " + _id + 
				" aus der Tabelle '" + _tableName + 
				"' konnte nicht geändert werden.", _errorMessage,
				DbStatus.notUpdateInTableMessage(_tableName, _id, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#deleteFromTable(java.lang.String, int)
	 */
	@Test
	public void testDeleteFromTable() {
		DbStatus.deleteFromTable(_tableName, _id);
		
		testMessage("Datenbank: Der Datensatz mit der ID " + _id +
				" wurde aus der Tabelle '" + _tableName + "' gelöscht.");
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#deleteFromTable(java.lang.String, int)
	 */
	@Test
	public void testDeleteFromTableMessage() {
		testMessageData("Datenbank: Der Datensatz mit der ID " + _id +
				" wurde aus der Tabelle '" + _tableName + "' gelöscht.",
				DbStatus.deleteFromTableMessage(_tableName, _id));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#notDeleteFromTable(java.lang.String, int,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotDeleteFromTable() {
		DbStatus.notDeleteFromTable(_tableName, _id, _error);
		
		testError("Datenbank: Der Datensatz mit der ID " + _id +
				" konnte nicht aus der Tabelle '" + _tableName + 
				"' gelöscht werden.", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#notDeleteFromTable(java.lang.String, int,
	 * java.lang.Exception)
	 */
	@Test
	public void testNotDeleteFromTableMessage() {
		testErrorData("Datenbank: Der Datensatz mit der ID " + _id +
				" konnte nicht aus der Tabelle '" + _tableName + 
				"' gelöscht werden.", _errorMessage,
				DbStatus.notDeleteFromTableMessage(_tableName, _id, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#getFromTable(java.lang.String, int,
	 * java.lang.Exception)
	 */
	@Test
	public void testGetFromTable() {
		DbStatus.getFromTable(_tableName, _id, _error);
		
		testError("Datenbank: Der Datensatz mit der ID " + _id +
				" konnte nicht in der Tabelle '" + _tableName +
				"' gefunden werden.", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#getFromTableMessage(java.lang.String, int,
	 * java.lang.Exception)
	 */
	@Test
	public void testGetFromTableMessage() {
		testErrorData("Datenbank: Der Datensatz mit der ID " + _id +
				" konnte nicht in der Tabelle '" + _tableName +
				"' gefunden werden.", _errorMessage,
				DbStatus.getFromTableMessage(_tableName, _id, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#notSql(java.lang.String, java.lang.Exception)
	 */
	@Test
	public void testNotSql() {
		String sql = "SELECT * FROM test";
		DbStatus.notSql(sql, _error);
		
		String error = "SQL-Abfrage, bei der der Fehler aufgetreten ist:" +
				System.lineSeparator() + "\t" + sql + System.lineSeparator() +
				System.lineSeparator() + "Fehlerbericht:" +
				System.lineSeparator() + _errorMessage;

		testError("Datenbank: Es ist ein Fehler beim " +
				"ausführen einer SQL-Abfrage aufgetreten.", error);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#notSql(java.lang.String, java.lang.Exception)
	 */
	@Test
	public void testNotSqlMessage() {
		String sql = "SELECT * FROM test";
		
		String error = "SQL-Abfrage, bei der der Fehler aufgetreten ist:" +
				System.lineSeparator() + "\t" + sql + System.lineSeparator() +
				System.lineSeparator() + "Fehlerbericht:" +
				System.lineSeparator() + _errorMessage;
		
		testErrorData("Datenbank: Es ist ein Fehler beim " +
				"ausführen einer SQL-Abfrage aufgetreten.", error,
				DbStatus.notSqlMessage(sql, _error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#sql(java.lang.String)
	 */
	@Test
	public void testSql() {
		String sql = "SELECT * FROM test";
		DbStatus.sql(sql);
		LogData data = getLastLog();
		
		assertThat(data.getOut(), is(LogData.DATABASE_INSERT));
		assertThat(data.getError(), is("SQL-Abfrage, die ausgeführt wurde:" +
				System.lineSeparator() + "\t" + sql));
		assertThat(data.getMessage(), is("Datenbank: SQL-Abfrage wurde " +
				"erfolgreich ausgeführt."));
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#sql(java.lang.String)
	 */
	@Test
	public void testSqlMessage() {
		String sql = "SELECT * FROM test";
		LogData data = DbStatus.sqlMessage(sql);
		
		assertThat(data.getOut(), is(LogData.DATABASE_INSERT));
		assertThat(data.getError(), is("SQL-Abfrage, die ausgeführt wurde:" +
				System.lineSeparator() + "\t" + sql));
		assertThat(data.getMessage(), is("Datenbank: SQL-Abfrage wurde " +
				"erfolgreich ausgeführt."));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#errorDatabase(Exception)
	 */
	@Test
	public void testErrorDataBase() {
		DbStatus.errorDatabase(_error);
		
		testError("Datenbank: Fehler beim Zugriff auf die Datenbank.",
				_errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#errorDatabase(Exception)
	 */
	@Test
	public void testErrorDatabaseMessage() {
		testErrorData("Datenbank: Fehler beim Zugriff auf die Datenbank.",
				_errorMessage, DbStatus.errorDatabaseMessage(_error));
	}

	/**
	 * Testet, ob die Nachricht richtig ins Logbuch eingefügt wurde.
	 * 
	 * @see org.db.main.DbStatus#notCreateTable(String, Exception)
	 */
	@Test
	public void testnotCreateTable() {
		DbStatus.notCreateTable(_tableName, _error);
		
		testError("Datenbank: Die Tabelle '" + _tableName +
				"' konnte nicht erstellt werden.", _errorMessage);
	}

	/**
	 * Testet, ob die Nachricht richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.DbStatus#notCreateTable(String, Exception)
	 */
	@Test
	public void testnotCreateTableMessage() {
		testErrorData("Datenbank: Die Tabelle '" + _tableName +
				"' konnte nicht erstellt werden.", _errorMessage,
				DbStatus.notCreateTableMessage(_tableName, _error));
	}
}
