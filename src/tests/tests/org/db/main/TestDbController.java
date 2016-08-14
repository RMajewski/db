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

import org.db.main.DbController;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.log.LogData;

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
	 * Räumt nach jeden Test auf
	 */
	@After
	public void tearDown() {
		// Verbindung zur Datenbank beenden
		DbController.getInstance().close();
	}
	
	/**
	 * Status-Nachricht für Datenbank-Fehler darf nicht <b>null</b> oder eine
	 * leere Zeichenkette sein.
	 */
	@Test
	public void testStatusDbErrorNotNullAndNotEmpty() {
		Exception exception = new Exception();
		LogData log = 
				LogData.messageError("Fehler beim Zugriff auf die Datenbank",
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
}
