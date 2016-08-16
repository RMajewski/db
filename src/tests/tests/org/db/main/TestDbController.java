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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;

import org.db.datas.Data;
import org.db.main.DbController;
import org.db.main.DbStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.log.LogData;
import org.log.StatusBar;

/**
 * Testet {@link org.db.main.DbController}.
 * 
 * @author René Majewski
 * 
 * @version 0.2
 * @since 0.1
 */
public class TestDbController {
	/**
	 * Diese Initialisierungen bleiben für alle Tests bestehen.
	 */
	@BeforeClass
	public static void setUpClass() {
		System.setProperty("testing", "true");
	}
	
	/**
	 * Initialisiert die einzelnen Tests.
	 */
	@Before
	public void setUp() {
		DbController.getInstance().initConnection(":memory:");
	}
	
	/**
	 * Räumt nach jeden Test auf
	 */
	@After
	public void tearDown() {
		// Verbindung zur Datenbank beenden
		DbController.getInstance().close();
		
		StatusBar.getInstance().clear();
	}
	
	/**
	 * Status-Nachricht für Datenbank-Fehler darf nicht <b>null</b> oder eine
	 * leere Zeichenkette sein.
	 * 
	 * @deprecated Wurde durch {@link DbStatus#errorDatabase(Exception) ersetzt.}
	 */
	@Test
	public void testStatusDbErrorNotNullAndNotEmpty() {
		Exception exception = new Exception();
		LogData log = 
				LogData.messageDatabaseError("Fehler beim Zugriff auf die Datenbank",
						exception);
		LogData res = DbController.statusDbError(exception);
		assertThat(res.getMessage(), is(equalTo(log.getMessage())));
	}
	
	/**
	 * Überprüft, ob die zurück gegebene Instanz auch eine Instanz von
	 * DbController-Klasse ist.
	 * 
	 * @see org.db.main.DbController#getInstance()
	 */
	@Test
	public void testGetInstanceRightClass()  {
		assertThat(DbController.getInstance(), 
				is(instanceOf(DbController.class)));
	}
	
	/**
	 * Überprüft, ob die zurück gegebene Instanz nicht null ist.
	 * 
	 * @see org.db.main.DbController#getInstance()
	 */
	@Test
	public void testGetInstanceReturnNotNull() {
		assertThat(DbController.getInstance(), is(notNullValue()));
	}
	
	/**
	 * Überprüft, ob der Datenbank-Name nicht <b>null</b> oder eine leere
	 * Zeichenkette ist.
	 * 
	 * @see org.db.main.DbController#getInstance()
	 */
	@Test
	public void testGetDatabaseNameReturnIsNotNullAndNotEmpty() {
		assertThat(DbController.getInstance().getDatabaseName(),
				is(notNullValue()));
		assertThat(DbController.getInstance().getDatabaseName(), not(""));
	}
	
	/**
	 * Überprüft, ob zum testen die Datenbank im Speichert angelegt wird.
	 * 
	 * @see org.db.main.DbController#getInstance()
	 */
	@Test
	public void testGetDatabaseNameReturnIsMemoryWithTesting() {
		assertThat(DbController.getInstance().getDatabaseName(),
				is(":memory:"));
	}
	
	/**
	 * Überprüft, ob die Rückgabe von createStatement() eine Instanz der Klasse
	 * org.sqlite.jdbc4.JDBC4Statement ist.
	 * 
	 * @throws SQLException Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt. 
	 * 
	 * @see org.db.main.DbController#createStatement()
	 */
	@Test
	public void testCreateStatementRightClass() throws SQLException {
		assertThat(DbController.getInstance().createStatement().getClass()
				.getName(), is(equalTo("org.sqlite.jdbc4.JDBC4Statement")));
		
	}

	/**
	 * Überprüft, ob die Rückgabe von getConnection() eine Instanz der Klasse
	 * org.sqlite.SQLiteConnection ist.
	 * 
	 * @see org.db.main.DbController#createStatement()
	 */
	@Test
	public void testGetConnectionRightClass() {
		assertThat(DbController.getInstance().getConnection(),
				is(instanceOf(org.sqlite.SQLiteConnection.class)));
	}
	
	/**
	 * Überprüft, ob die Rückgabe von isConnection() korrekt ist.
	 * 
	 * @throws SQLException Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt. 
	 * 
	 * @see org.db.main.DbController#isConnection()
	 */
	@Test
	public void testIsConnectionReturnTrue() throws SQLException {
		assertThat(DbController.getInstance().isConnection(),
				is(equalTo(true)));
		assertThat(DbController.getInstance().getConnection().isClosed(),
				is(equalTo(false)));
	}
	
	/**
	 * Überprüft, ob nach dem Beenden der Verbindung <b>false</b> zurück
	 * gegeben wird.
	 * @throws SQLException  Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt. 
	 * 
	 * @see org.db.main.DbController#isConnection()
	 */
	@Test
	public void testIsConnectionReturnFalseAfterClose() throws SQLException {
		DbController.getInstance().getConnection().close();
		assertThat(DbController.getInstance().isConnection(),
				is(equalTo(false)));
		assertThat(DbController.getInstance().getConnection().isClosed(),
				is(equalTo(true)));
	}
	
	/**
	 * Überprüft, ob prepareStatement(String) eine Instanz der Klasse
	 * org.sqlite.jdbc4.JDBC4PreparedStatement zurück gibt.
	 * 
	 * @throws SQLException Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt. 
	 * 
	 * @see org.db.main.DbController#prepareStatement(String)
	 */
	@Test
	public void testPrepareStatementReturnIsRightClass() throws SQLException {
		assertThat(DbController.getInstance().prepareStatement(
				"SELECT * FROM sqlite_master").getClass().getName(),
				is(equalTo("org.sqlite.jdbc4.JDBC4PreparedStatement")));
	}
	
	/**
	 * Überprüft, ob die Methode setAutoCommit(boolean) richtig arbeitet. Das
	 * heißt, wenn <b>false</b> übergeben wurde, sollte bei
	 * getConnectio().getAutoCommit() auch <b>true</b> zurück gegeben werden.
	 * 
	 * @throws SQLException Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt. 
	 * 
	 * @see org.db.main.DbController#setAutoCommit(boolean)
	 */
	@Test
	public void testSetAutoCommitWithTrueAsParameter() throws SQLException {
		DbController.getInstance().setAutoCommit(true);
		assertThat(DbController.getInstance().getConnection().getAutoCommit(),
				is(equalTo(true)));
	}
	
	/**
	 * Überprüft, ob die Methode setAutoCommit(boolean) richtig arbeitet. Das
	 * heißt, wenn <b>false</b> übergeben wurde, sollte bei
	 * getConnectio().getAutoCommit() auch <b>true</b> zurück gegeben werden.
	 * @throws SQLException Wird ausgelöst, wenn ein Fehler beim Zugriff auf
	 * die Datenbank auftritt.
	 * 
	 * @see org.db.main.DbController#setAutoCommit(boolean)
	 */
	@Test
	public void testSetAutoCommitWithFalseAsParameter() throws SQLException {
		DbController.getInstance().setAutoCommit(false);
		assertThat(DbController.getInstance().getConnection().getAutoCommit(),
				is(equalTo(false)));
	}
	
	/**
	 * Überprüft, ob die SQL-Anweisung ohne Fehler ausgeführt wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test
	public void testSql() {
		String sql = "SELECT tbl_name FROM sqlite_master WHERE type='table'";
		
		assertThat(DbController.getInstance().sql(sql), is(true));
		assertThat(StatusBar.getInstance().getLog().size(), is(0));
	}
	
	/**
	 * Überprüft, ob der Fehler abgefangen und eine entsprechende
	 * Status-Nachricht gesetzt wurde.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test
	public void testSqlWithError() {
		String sql = "SELECT test FROM sqlite_master WHERE type='table'";
		
		assertThat(DbController.getInstance().sql(sql), is(false));
		
		LogData log = LogData.messageDatabaseError("Datenbank: Es ist ein Fehler " +
				"beim ausführen einer SQL-Abfrage aufgetreten.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob ein Fehler erzeugt wird, wenn null als SQL-Anweisung
	 * übergeben wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSqlWithNullAsParameter() {
		DbController.getInstance().sql(null);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException ausgelöst wird, wenn
	 * eine leere Zeichenkette übergeben wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSqlWithEmptyStringAsParameter() {
		DbController.getInstance().sql(new String());
	}
	
	/**
	 * Überprüft, ob count ausgeführt wird und das richtige Ergebnis
	 * zurückgegeben wird.
	 * 
	 * @see org.db.main.DbController#count(org.db.main.Queryable)
	 */
	@Test
	public void testCount() {
		AbstractQueryImpl query = new AbstractQueryImpl("test");
		DbController.getInstance().sql(query.createTable());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(0));
		assertThat(DbController.getInstance().count(query), is(0));
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException ausgelöst wird, wenn
	 * null übergeben wird.
	 * 
	 * @see org.db.main.DbController#count(org.db.main.Queryable)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCountWithNullAsParameter() {
		DbController.getInstance().count(null);
	}
	
	/**
	 * Überprüft, ob ein SQL-Fehler auftritt und abgefangen, wenn ein Fehler
	 * in der count-Abfrage auftritt.
	 * 
	 * @see org.db.main.DbController#count(org.db.main.Queryable)
	 */
	@Test
	public void testCountWithSqlError() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table, true);
		DbController.getInstance().count(query);
		LogData log = LogData.messageDatabaseError("Datenbank: Die Anzahl der " +
				"Datensätze für die Tabelle '" + table +
				"' konnte nicht ermittelt werden.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob die Tabelle angelegt werden konnte.
	 * 
	 * @see org.db.main.DbController#createTable(org.db.main.Queryable)
	 */
	@Test
	public void testCreateTable() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().createTable(query);
		LogData log = LogData.messageDatabaseInsert("Datenbank: Die Tabelle '" + table +
				"' wurde erstellt.", "");
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException ausgelöst wird, wenn
	 * null als Parameter übergeben wird.
	 * 
	 * @see org.db.main.DbController#createTable(org.db.main.Queryable)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTableWithNullAsParameter() {
		DbController.getInstance().createTable(null);
	}
	
	/**
	 * Überprüft, ob SQLException abgefangen wird, wenn er auftritt und ob eine
	 * Fehlermeldung ausgegeben wird.
	 * 
	 * @see org.db.main.DbController#createTable(org.db.main.Queryable)
	 */
	@Test
	public void testCreateTableWithSqlEception() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table, true);
		DbController.getInstance().createTable(query);
		
		LogData log = LogData.messageDatabaseError("Datenbank: Die Tabelle '" + table +
				"' konnte nicht erstellt werden.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob der angegebene Datensatz gelöscht werden konnte.
	 * 
	 * @see org.db.main.DbController#delete(org.db.main.Queryable, int)
	 */
	@Test
	public void testDelete() {
		String table = "test";
		Data data = new Data(100);
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().createTable(query);
		DbController.getInstance().insert(query, data);
		
		assertThat(DbController.getInstance().count(query), is(1));
		
		DbController.getInstance().delete(query, data.getId());
		
		assertThat(DbController.getInstance().count(query), is(0));
		
		LogData log = LogData.messageDatabaseInsert("Datenbank: Der Datensatz mit der ID " +
				data.getId() + " wurde aus der Tabelle '" + table +
				"' gelöscht.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(3));
		assertThat(StatusBar.getInstance().getLog().get(2), is(log));
	}
	
	/**
	 * Überprüft ob der Fehler IllegalArgumentException auftritt, wenn null
	 * als Parameter übergeben wird.
	 * 
	 * @see org.db.main.DbController#delete(org.db.main.Queryable, int)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteWithNullAsParameter() {
		DbController.getInstance().delete(null, 1);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn 0 als
	 * ID übergeben wird.
	 * 
	 * @see org.db.main.DbController#delete(org.db.main.Queryable, int)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteWithZeroAsParameter() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().delete(query, 0);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn -1 als
	 * ID übergeben wird.
	 * 
	 * @see org.db.main.DbController#delete(org.db.main.Queryable, int)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteWithMinusOneAsParameter() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().delete(query, -1);
	}
	
	/**
	 * Überprüft, ob SQL-Fehler abgefangen werden und eine entsprechende
	 * Nachricht ausgegeben wird.
	 * 
	 * @see org.db.main.DbController#delete(org.db.main.Queryable, int)
	 */
	@Test
	public void testDeleteWithSqlException() {
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().delete(query, 1);
		LogData log = LogData.messageDatabaseError("Datenbank: Der Datensatz mit der " +
				"ID 1 konnte nicht aus der Tabelle '" + table +
				"' gelöscht werden.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob der angegebene Datensatz in die Datenbank geschrieben
	 * werden konnte.
	 * 
	 * @see org.db.main.DbController#insert(org.db.main.Queryable,
	 * org.db.datas.Data)
	 */
	@Test
	public void testInsert() {
		String table = "test";
		Data data = new Data(100);
		AbstractQueryImpl query = new AbstractQueryImpl(table);		
		DbController.getInstance().createTable(query);
		
		DbController.getInstance().insert(query, data);
		
		assertThat(DbController.getInstance().count(query), is (1));
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn null
	 * als Query übergeben wird.
	 * 
	 * @see org.db.main.DbController#insert(org.db.main.Queryable,
	 * org.db.datas.Data)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInsertWithNullAsQuery() {
		Data data = new Data(100);
		DbController.getInstance().insert(null, data);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn null
	 * als Daten übergeben wird.
	 * 
	 * @see org.db.main.DbController#insert(org.db.main.Queryable, Data)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInsertWithNullAsData() {
		AbstractQueryImpl query = new AbstractQueryImpl("test");
		DbController.getInstance().insert(query, null);
	}
	
	/**
	 * Überprüft, ob SQL-Fehler abgefangen werden, wenn sie auftreten und ob
	 * sie als Nachricht ausgegeben werden.
	 * 
	 * @see org.db.main.DbController#insert(org.db.main.Queryable, Data)
	 */
	@Test
	public void testInsertWithSqlError() {
		Data data = new Data(100);
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().insert(query, data);
		
		LogData log = LogData.messageDatabaseError("Datenbank: Der Datensatz konnte " +
				"nicht in die Tabelle '" + table +"' eingefügt werden",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob der angegebene Datensatz geändert werden konnte.
	 * 
	 * @see org.db.main.DbController#update(org.db.main.Queryable,
	 * org.db.datas.Data)
	 */
	@Test
	public void testUpdate() {
		Data data = new Data(100);
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().createTable(query);
		DbController.getInstance().insert(query, data);
		DbController.getInstance().update(query, data);
		LogData log = LogData.messageDatabaseInsert("Datenbank: Der Datensatz mit der ID " +
		data.getId() + " aus der Tabelle '" + table + "' wurde geändert.", "");
		
		assertThat(DbController.getInstance().count(query), is (1));
		assertThat(StatusBar.getInstance().getLog().size(), is(3));
		assertThat(StatusBar.getInstance().getLog().get(2), is(log));
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn null als
	 * Query übergeben wird.
	 * 
	 * @see org.db.main.DbController#update(org.db.main.Queryable,
	 * org.db.datas.Data)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateWithNullAsQuery() {
		Data data = new Data(100);
		DbController.getInstance().update(null, data);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException auftritt, wenn null als
	 * Daten übergeben wird.
	 * 
	 * @see org.db.main.DbController#update(org.db.main.Queryable,
	 * org.db.datas.Data)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateWithNullAsData() {
		AbstractQueryImpl query = new AbstractQueryImpl("test");
		DbController.getInstance().update(query, null);
	}
	
	/**
	 * Überprüft, ob SQL-Fehler abgefangen werden und als Nachricht ausgegeben
	 * werden.
	 */
	@Test
	public void testUpdateWithSqlException() {
		Data data = new Data(100);
		String table = "test";
		AbstractQueryImpl query = new AbstractQueryImpl(table);
		DbController.getInstance().update(query, data);
		LogData log = LogData.messageDatabaseError("Datenbank: Der Datensatz mit der " +
				"ID " + data.getId() + " aus der Tabelle '" + table +
				"' konnte nicht geändert werden.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob die SQL-Anweisung ohne Fehler ausgeführt wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test
	public void testSqlStringLogData() {
		String sql = "SELECT tbl_name FROM sqlite_master WHERE type='table'";
		
		assertThat(DbController.getInstance().sql(sql, null), is(true));
		assertThat(StatusBar.getInstance().getLog().size(), is(0));
	}
	
	/**
	 * Überprüft, ob der Fehler abgefangen und eine entsprechende
	 * Status-Nachricht gesetzt wurde.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test
	public void testSqlStringLogDataWithErrorAndNullAsData() {
		String sql = "SELECT test FROM sqlite_master WHERE type='table'";
		
		assertThat(DbController.getInstance().sql(sql, null), is(false));
		
		LogData log = LogData.messageDatabaseError("Datenbank: Es ist ein Fehler " +
				"beim ausführen einer SQL-Abfrage aufgetreten.",
				StatusBar.getInstance().getLog().get(0).getError());
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob der Fehler abgefangen und eine entsprechende
	 * Status-Nachricht gesetzt wurde.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test
	public void testSqlStringLogDataWithErrorAndDataAsData() {
		Exception exception = new Exception();
		String sql = "SELECT test FROM sqlite_master WHERE type='table'";

		LogData log = DbStatus.errorDatabaseMessage(exception);
		
		assertThat(DbController.getInstance().sql(sql, log), is(false));
		
		assertThat(StatusBar.getInstance().getLog().size(), is(1));
		assertThat(StatusBar.getInstance().getLog().get(0), is(log));
	}
	
	/**
	 * Überprüft, ob ein Fehler erzeugt wird, wenn null als SQL-Anweisung
	 * übergeben wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSqlStringLogDataWithNullAsParameter() {
		DbController.getInstance().sql(null, null);
	}
	
	/**
	 * Überprüft, ob der Fehler IllegalArgumentException ausgelöst wird, wenn
	 * eine leere Zeichenkette übergeben wird.
	 * 
	 * @see org.db.main.DbController#sql(String)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSqStringLogDatalWithEmptyStringAsParameter() {
		DbController.getInstance().sql(new String(), null);
	}
}
