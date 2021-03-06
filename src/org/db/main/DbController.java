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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.db.datas.Data;
import org.log.LogData;
import org.log.StatusBar;

/**
 * Stellt die Verbindung zur Datenbank her und stellt die einzelnen Abfragen.
 * 
 * @author René Majewski
 *
 *@version 0.1
 *@since 0.1
 */
public class DbController {
	/**
	 * Speichert die Instanz dieser Klasse.
	 */
	private static DbController _controller;
	
	/**
	 * Speichert die Verbindung zur Datenbank.
	 */
	private static Connection _connection;
	
	/**
	 * Speichert den Namen der Datenbank.
	 */
	private String _dbName;
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			StatusBar.getInstance().setMessage(LogData.messageError(
					"Fehler beim laden des JDBC-Treibers", e));
		}
	}
	
	/**
	 * Initialisiert diese Klasse.
	 */
	private DbController() {
		_dbName = new String();
		initConnection(_dbName);
	}
	
	/**
	 * Beendet eine offene Verbindung zur Datenbank.
	 */
	public void close() {
		try {
			if ((_connection != null) && !_connection.isClosed()) {
				_connection.close();
				_connection = null;
			}
		} catch (SQLException e) {
			StatusBar.getInstance().setMessage(statusDbError(e));
		}
	}
	
	/**
	 * Gibt die Instanz des Controllers zurück. Ist noch keine Instanz
	 * initialisiert wurden, so wird eine initialisiert.
	 * 
	 * @return Instanz des Controllers.
	 */
	public static DbController getInstance() {
		if (_controller == null)
			_controller = new DbController();
		return _controller;
	}

	/**
	 * Initialisiert die Verbindung zur Datenbank.
	 * 
	 * Ist die System-Einstellung "testing" gesetzt, so wird die Datenbank
	 * im Speicher angelegt.
	 * 
	 * Ist die System-Einstellung "debugging" gesetzt, so wird an den
	 * Datenbank-Namen noch "_debug" angehängt.
	 * 
	 * @param name Name der Datenbank
	 */
	public void initConnection(String name) {
		// Überprüfen ob getestet wird
		if ((System.getProperty("testing") != null) ||
				name.equals(":memory:")) {
			// Beim testen nur die Datenbank im Speicher anlegen
			_dbName = new String(":memory:");
		} else {
			// Überprüfen ob ein Datenbank-Name übergeben wurde
			if ((name != null) && !name.isEmpty()) {
				// Überprüfen ob das Verzeichnis existiert
				String home = new String();
				
				if ((System.getenv("APPDATA") != null) && 
						!System.getenv("APPDATA").isEmpty()) {
					home = System.getenv("APPDATA") + File.separator;
				} else {
					home = System.getProperty("user.home") + File.separator +
							".";
				}
				
				File file = new File(home + name);
				
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				
				// Pfad und Name der Datenbank speichern
				_dbName = file.getAbsolutePath();
				
				// Überprüfen ob debugging läuft.
				if (System.getProperty("debugging") != null)
					_dbName += "_debug";
			}
		}
		
		// Wurde der Datenbank-Name gesetzt?
		if ((_dbName != null) && !_dbName.isEmpty()) {
			try {
				// Besteht schon eine Verbindung zur Datenbank, dann diese
				// schließen
				if (_connection != null) {
					_connection.close();
					_connection = null;
				}
				
				// Verbindung zur Datenbank herstellen
				_connection = DriverManager.getConnection("jdbc:sqlite:" + 
						_dbName);
			} catch (SQLException e) {
				StatusBar.getInstance().setMessage(statusDbError(e));
			}
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					close();
				}
			});
		}
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück.
	 * 
	 * @return Verbindung zur Datenbank.
	 */
	public Connection getConnection() {
		return _connection;
	}
	
	/**
	 * Überprüft ob eine Verbindung zur Datenbank besteht.
	 * 
	 * @return Besteht eine Verbindung zur Datenbank?
	 */
	public boolean isConnection() {
		try {
			if ((_connection == null) || _connection.isClosed())
				return false;
		} catch (SQLException e) {
			StatusBar.getInstance().setMessage(statusDbError(e));
		}
		
		return true;
	}
	
	/**
	 * Erzeugt ein {@link java.sql.Statement}.
	 * 
	 * @return Erzeugte SQL-Statement.
	 * 
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim Zugriff auf die
	 * Datenbank stattgefunden hat.
	 */
	public Statement createStatement() throws SQLException {
		return _connection.createStatement();
	}
	
	/**
	 * Erzeugt ein {@link java.sql.PreparedStatement}.
	 * 
	 * @param sql SQL-Befehl, der ausgeführt werden soll.
	 * 
	 * @return Erzeugtes SQL-PreparedStatement.
	 * 
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim Zugriff auf die
	 * Datenbank stattgefunden hat.
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return _connection.prepareStatement(sql);
	}
	
	/**
	 * Legt fest, ob der AutoCommit ausgeführt werden soll oder nicht.
	 * 
	 * @param autoCommit Soll der AutoCommit ausgeführt werden? Wird true
	 * übergeben, soll der AutoCommit durchgeführt werden. Bei false soll er
	 * nicht ausgeführt werden.
	 *  
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim Zugriff auf die
	 * Datenbank stattgefunden hat.
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		_connection.setAutoCommit(autoCommit);
	}

	/**
	 * Erzeugt die Status-Nachricht, wenn ein Datenbank-Fehler aufgetreten ist.
	 * 
	 * @param e Fehler, der aufgetreten ist.
	 * 
	 * @return Status-Nachricht bei Datenbank-Fehler.
	 * 
	 * @deprecated Wurde durch {@link DbStatus#errorDatabase(Exception) ersetzt.}
	 */
	public static LogData statusDbError(Exception e) {
		return LogData.messageError("Fehler beim Zugriff auf die Datenbank", e);
	}
	
	/**
	 * Gibt den Namen der Datenbank zurück.
	 * 
	 * @return Name der Datenbank.
	 */
	public String getDatabaseName() {
		return _dbName;
	}
	
	/**
	 * Führt den Batch-Vorgang des PreparedStatements aus.
	 * 
	 * @param ps PreparedStatement, dessen Batch-Vorgang ausgeführt werden soll.
	 * 
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim ausführen des
	 * Batch-Vorgangs auftritt.
	 */
	public void executeBatch(PreparedStatement ps) throws SQLException {
		setAutoCommit(false);
		ps.executeBatch();
		setAutoCommit(true);
	}

	/**
	 * Führt die angegebene SQL-Anweisung aus. Sollte ein SQLException-Fehler
	 * auftreten so wird dieser abgefangen und eine Nachricht in der
	 * Status-Leiste angezeigt.
	 * 
	 * @param sql SQL-Abfrage, die ausgeführt werden soll.
	 * 
	 * @return Wurde die SQL-Abfrage richtig ausgeführt? Wenn ja, wird true
	 * zurückgegeben. Wenn nicht, dann false.
	 */
	public boolean sql(String sql) {
		return sql(sql, null);
	}
	
	/**
	 * Führt die angegebene SQL-Anweisung aus. Sollte ein SQLException-Fehler
	 * auftrete, so wird dieser abgefangen und eine Nachricht in der
	 * Status-Leiste angezeigt.
	 * 
	 * @param sql SQL-Abfrage, die ausgeführt werden soll.
	 * 
	 * @param message Nachricht, die beim auftreten des Fehlers SQLException
	 * ausgegeben werden soll. Wird null übergeben, so wird eine standard
	 * Nachricht ausgegeben.
	 * 
	 * @return Wurde die SQL-Abfrage richtig ausgeführt? Wenn ja, wird true
	 * zurückgegeben. Wenn nicht, dann false.
	 */
	public boolean sql(String sql, LogData message) {
		if ((sql == null) || sql.isEmpty())
			throw new IllegalArgumentException(
					"Die SQL-Abfrage muss angegeben sein!");
		try {
			Statement stm = createStatement();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			if (message == null)
				DbStatus.notSql(sql, e);
			else {
				message.setError(LogData.createError(e));
				StatusBar.getInstance().setMessage(message);
			}
			return false;
		}
		
		return true;
	}

	/**
	 * Erstellt die angegebene Tabelle in der Datenbank.
	 * 
	 * @param query Abfrage, die von Queryable abgeleitet wurde.
	 */
	public void createTable(Queryable query) {
		if (query == null)
			throw new IllegalArgumentException(
					"Die Abfrage muss übergeben werden.");
		
		if (sql(query.createTable(), DbStatus.notCreateTableMessage(
				query.getTableName(), null)))
			DbStatus.createTable(query.getTableName());
	}

	/**
	 * Ermittelt von der angegebenen Tabelle die Anzahl an Datensätzen.
	 * 
	 * @param query Abfrage, die von Queryable abgeleitet wurde.
	 * 
	 * @return Anzahl an Datensätzen. ist ein Fehler aufgetreten, so wird -1
	 * zurückgegeben.
	 */
	public int count(Queryable query) {
		if (query == null)
			throw new IllegalArgumentException(
					"Die Abfrage muss übergeben werden.");
		
		int result = -1;
		try {
			Statement stm = createStatement();
			ResultSet rs = stm.executeQuery(query.count());
			if (rs.next())
				result = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			StatusBar.getInstance().setMessage(LogData.messageDatabaseError(
					"Datenbank: Die Anzahl der Datensätze für die Tabelle '" +
					query.getTableName() + "' konnte nicht ermittelt werden.",
					e));
		}
		
		return result;
	}

	/**
	 * Fügt einen neuen Datensatz in die angegebene Tabelle ein.
	 *  
	 * @param query Abfrage, die von Queryable abgeleitet wurde.
	 * 
	 * @param data Datensatz, der in die Tabelle eingefügt werden soll.
	 */
	public void insert(Queryable query, Data data) {
		if (query == null)
			throw new IllegalArgumentException(
					"Die Abfrage muss übergeben werden.");
		
		if (data == null)
			throw new IllegalArgumentException(
					"Der Datensatz muss übergeben werden.");
		
		if (sql(query.insert(data), DbStatus.notInsertInTableMessage(
				query.getTableName(), null)))
			DbStatus.insertInTable(query.getTableName());
	}

	/**
	 * Löscht den Datensatz mit der angegebenen ID.
	 * 
	 * @param query Abfrage, die von Queryable abgeleitet wurde.
	 * 
	 * @param id ID des Datensatzes, der gelöscht werden soll.
	 */
	public void delete(Queryable query, int id) {
		if (query == null)
			throw new IllegalArgumentException(
					"Die Abfrage muss übergeben werden.");
		
		if (id <= 0)
			throw new IllegalArgumentException("Die ID muss größer 0 sein.");
		
		if (sql(query.delete(id), DbStatus.notDeleteFromTableMessage(
				query.getTableName(), id, null)))
			DbStatus.deleteFromTable(query.getTableName(), id);
	}

	/**
	 * Speichert die Änderung des angegebenen Datensatzes in die Datenbank.
	 * 
	 * @param query Abfrage, die von Queryable abgeleitet wurde.
	 * 
	 * @param data Datensatz, der geändert werden soll.
	 */
	public void update(Queryable query, Data data) {
		if (query == null)
			throw new IllegalArgumentException(
					"Die Abfrage muss übergeben werden.");
		
		if (data == null)
			throw new IllegalArgumentException(
					"Der Datensatz muss angegeben werden.");
		
		if (sql(query.update(data), DbStatus.notUpdateInTableMessage(
				query.getTableName(), data.getId(), null)))
			DbStatus.updateInTable(query.getTableName(), data.getId());
	}
}
