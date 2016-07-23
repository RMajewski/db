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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.log.LogData;
import org.log.StatusBar;

/**
 * Stellt die Verbindung zur Datenbank her und stellt die einzelnen Abfragen.
 * 
 * @author Ren� Majewski
 *
 *@version 0.1
 *@since 0.1
 */
public class DbController {
	/**
	 * Speichert die Instanz dieser Klasse.
	 */
	private DbController _controller;
	
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
	 * Beendet eine offende Verbindung zur Datenbank.
	 */
	public void close() {
		try {
			if ((_connection != null) && !_connection.isClosed())
				_connection.close();
		} catch (SQLException e) {
			StatusBar.getInstance().setMessage(statusDbError(e));
		}
	}
	
	/**
	 * Gibt die Instanz des Controllers zur�ck. Ist noch keine Instanz
	 * initialisiert wurden, so wird eine initialisiert.
	 * 
	 * @return Instanz des Controllers.
	 */
	public DbController getInstance() {
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
	 * Datenbank-Namen noch "_debug" angeh�ngt.
	 * 
	 * @param name Name der Datenbank
	 */
	public void initConnection(String name) {
		// �berpr�fen ob getestet wird
		if (System.getProperty("testing") != null) {
			// Beim testen nur die Datenbank im Speicher anlegen
			_dbName = new String(":memory:");
		} else {
			// �berpr�fen ob ein Datenbank-Name �bergeben wurde
			if ((name != null) && !name.isEmpty()) {
				// �berpr�fen ob das Verzeichnis existiert
				File file = new File(System.getProperty("user.home") + 
						File.separator + name);
				if (!file.exists())
					file.mkdirs();
				
				// Pfad und Name der Datenbank speichern
				_dbName = file.getAbsolutePath();
				
				// �berpr�fen ob debugging l�uft.
				if (System.getProperty("debugging") != null)
					_dbName += "_debug";
			}
		}
		
		// Wurde der Datenbank-Name gesetzt?
		if ((_dbName != null) && !_dbName.isEmpty()) {
			try {
				// Besteht schon eine Verbindung zur Datenbank, dann diese
				// schlie�en
				if (_connection != null)
					_connection.close();
				
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
	 * Gibt die Verbindung zur Datenbank zur�ck.
	 * 
	 * @return Verbindung zur Datenbank.
	 */
	public Connection getConnection() {
		return _connection;
	}
	
	/**
	 * �berpr�ft ob eine Verbindung zur Datenbank besteht.
	 * 
	 * @return Besteht eine Verindung zur Datenbank?
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
	 * Erueigt ein {@link java.sql.PreparedStatement}.
	 * 
	 * @param sql SQL-Befehl, der ausgef�hrt werden soll.
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
	 * Legt fest, ob der AutoCommit ausgef�hrt werden soll oder nicht.
	 * 
	 * @param autoCommit Soll der AutoCommit ausgef�hrt werden? Wird true
	 * �bergeben, soll der AutoCommit durchgef�hrt werden. Bei false soll er
	 * nicht ausgef�hrt werden.
	 *  
	 * @throws SQLException Wird erzeugt, wenn ein Fehler beim Zugriff auf die
	 * Datenbank stattgefunden hat.
	 */
	public void setAutoCOmmit(boolean autoCommit) throws SQLException {
		_connection.setAutoCommit(autoCommit);
	}

	/**
	 * Erzeugt die Status-Nachricht, wenn ein Datenbank-Fehler aufgetreten ist.
	 * 
	 * @return Status-Nachricht bei Datenbank-Fehler.
	 */
	private static LogData statusDbError(Exception e) {
		return LogData.messageError("Fehler beim Zugriff auf die Datenbank", e);
	}
	
	/**
	 * Gibt den Namen der Datenbank zur�ck.
	 * 
	 * @return Name der Datenbank.
	 */
	public String getDatabaseName() {
		return _dbName;
	}
}
