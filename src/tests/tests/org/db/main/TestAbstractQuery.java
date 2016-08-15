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

import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse {@link org.db.main.AbstractQuery}.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class TestAbstractQuery {
	/**
	 * Speichert die Instanz der Test-Klasse
	 */
	private AbstractQueryImpl _test;
	
	/**
	 * Speichert den Namen der Tabelle.
	 */
	private String _tableName;
	
	/**
	 * Speichert den Namen der 1. Spalte.
	 */
	private String _id;
	
	/**
	 * Speichert den Namen der 2. Spalte.
	 */
	private String _dsc;
	
	/**
	 * Initialisiert die Tests.
	 */
	@Before
	public void setUp() {
		_id = "id";
		_dsc = "test";
		_tableName = "test";
		
		_test = new AbstractQueryImpl(_tableName);
	}

	/**
	 * Testet, ob ob richtig initialisiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#AbstractQuery(java.lang.String)
	 */
	@Test
	public void testAbstractQuery() {
		assertThat(_test.getTableName(), is(_tableName));
		assertThat(_test.getCloumnCount(), is(2));
	}

	/**
	 * Testet, ob der Tabellen-Name richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.AbstractQuery#getTableName()
	 */
	@Test
	public void testGetTableName() {
		assertThat(_test.getTableName(), is(_tableName));
	}

	/**
	 * Testet, ob die Liste mit den Spalten-Namen richtig zurückgegeben wird.
	 * 
	 * @see org.db.main.AbstractQuery#getColumnNames()
	 */
	@Test
	public void testGetColumnNames() {
		assertThat(_test.getColumnNames(), hasItems(_id, _dsc));
	}

	/**
	 * Testet, ob die richtige Anzahl an Spalten zurückgegeben wird.
	 * 
	 * @see org.db.main.AbstractQuery#getCloumnCount()
	 */
	@Test
	public void testGetCloumnCount() {
		assertThat(_test.getCloumnCount(), is(2));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um alle Datensätze
	 * anzuzeigen. Zum Test werden zwei Spalten eingefügt.
	 * 
	 * @see org.db.main.AbstractQuery#select()
	 */
	@Test
	public void testSelect() {
		assertThat(_test.select(), is("SELECT " + _id + ", " + _dsc + " FROM " +
				_tableName + " ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen neuen
	 * Datensatz in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insert()
	 */
	@Test
	public void testInsert() {
		assertThat(_test.insert(), is("INSERT INTO " + _tableName + " (" +
				_dsc + ") VALUES (\"?\");"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen neuen
	 * Datensatz in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insert(boolean, boolean)
	 */
	@Test
	public void testInsertBooleanBooleanWithTrueAndTrueAsParameter() {
		assertThat(_test.insert(true, true), is("INSERT INTO " + _tableName +
				" (" + _id + ", " + _dsc + ") VALUES (\"?\", \"?\");"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen neuen
	 * Datensatz in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insert(boolean, boolean)
	 */
	@Test
	public void testInsertBooleanBooleanWithFalseAndTrueAsParameter() {
		assertThat(_test.insert(false, true), is("INSERT INTO " + _tableName +
				" (" + _id + ", " + _dsc + ") VALUES (?, ?);"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen neuen
	 * Datensatz in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insert(boolean, boolean)
	 */
	@Test
	public void testInsertBooleanBooleanWithTrueAndFalseAsParameter() {
		assertThat(_test.insert(true, false), is("INSERT INTO " + _tableName +
				" (" + _dsc + ") VALUES (\"?\");"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen neuen
	 * Datensatz in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insert(boolean, boolean)
	 */
	@Test
	public void testInsertBooleanBooleanWithFalseAndFalseAsParameter() {
		assertThat(_test.insert(false, false), is("INSERT INTO " + _tableName +
				" (" + _dsc + ") VALUES (?);"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen Datensatz
	 * mit einer vorgegebenen ID in die Tabelle einzufügen.
	 * 
	 * @see org.db.main.AbstractQuery#insertWithId()
	 */
	@Test
	public void testInsertWithId() {
		assertThat(_test.insertWithId(), is("INSERT INTO " + _tableName + " (" +
				_id + ", " + _dsc + ") VALUES (\"?\", \"?\");"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen Datensatz
	 * zu ändern.
	 * 
	 * @see org.db.main.AbstractQuery#update(int)
	 */
	@Test
	public void testUpdate() {
		int id = 100;
		String test = "test";
		_test.getColumnNames().add(test);
		
		assertThat(_test.update(id), is("UPDATE " + _tableName + " SET " + 
				_dsc + " = '?', " + test + " = '?' WHERE " + _id + " = " +
				String.valueOf(id)));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um einen Datensatz
	 * zu ändern, wenn keine ID übergeben wird.
	 * 
	 * @see org.db.main.AbstractQuery#update(int)
	 */
	@Test
	public void testUpdateWithoutAsParameter() {
		assertThat(_test.update(-1), is("UPDATE " + _tableName + " SET " + 
				_dsc + " = '?' WHERE " + _id + " = ?"));
	}

	/**
	 * Testet, ob die richtig SQL-Abfrage generiert wird, um einen Datensatz zu
	 * löschen.
	 * 
	 * @see org.db.main.AbstractQuery#delete(int)
	 */
	@Test
	public void testDelete() {
		int id = 100;
		assertThat(_test.delete(id), is("DELETE FROM " + _tableName + 
				" WHERE " + _id + " = " + String.valueOf(id)));
	}

	/**
	 * Testet, ob die richtig SQL-Abfrage generiert wird, um einen Datensatz zu
	 * löschen, wenn keine ID übergeben wird.
	 * 
	 * @see org.db.main.AbstractQuery#delete(int)
	 */
	@Test
	public void testDeleteWithoutIdAsParamter() {
		assertThat(_test.delete(-1), is("DELETE FROM " + _tableName + 
				" WHERE " + _id + " = ?"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Anzahl der
	 * Datensätze in der Tabelle zu ermitteln.
	 * 
	 * @see org.db.main.AbstractQuery#count()
	 */
	@Test
	public void testCount() {
		assertThat(_test.count(), is("SELECT count(*) FROM '" + _tableName +
				"'"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um Datensätze
	 * zu suchen, die in der angegebenen Spalte die angegebene Zeichenkette
	 * enthalten.
	 * 
	 * @see org.db.main.AbstractQuery#search(java.lang.String, java.lang.String)
	 */
	@Test
	public void testSearchStringString() {
		String search = "Dies ist ein Test!";
		
		assertThat(_test.search(_dsc, search), is("SELECT " + _id + ", " +
				_dsc + " FROM " + _tableName + " WHERE " + _dsc + " = \"" +
				search + "\" ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um Datensätze zu
	 * suchen, die in der angegebenen Spalte eine angegebene Ganzzahl enthalten.
	 * 
	 * @see org.db.main.AbstractQuery#search(java.lang.String, int)
	 */
	@Test
	public void testSearchStringInt() {
		int search = 10;
		
		assertThat(_test.search(_dsc, search), is("SELECT " + _id + ", " +
				_dsc + " FROM " + _tableName + " WHERE " + _dsc + " = " +
				search + " ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um Datensätze zu
	 * suchen, die in der angegebenen Spalte ein angegebenen boolean-Wert
	 * enthalten.
	 * 
	 * @see org.db.main.AbstractQuery#search(java.lang.String, boolean)
	 */
	@Test
	public void testSearchStringBoolean() {
		boolean search = true;
		
		assertThat(_test.search(_dsc, search), is("SELECT " + _id + ", " +
				_dsc + " FROM " + _tableName + " WHERE " + _dsc + " = " +
				search + " ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um Datensätze zu
	 * suchen, die in der angegebenen Spalte ein angegebenen long-Wert
	 * enthalten.
	 * 
	 * @see org.db.main.AbstractQuery#search(java.lang.String, long)
	 */
	@Test
	public void testSearchStringLong() {
		long search = 19457389l;
		
		assertThat(_test.search(_dsc, search), is("SELECT " + _id + ", " +
				_dsc + " FROM " + _tableName + " WHERE " + _dsc + " = " +
				search + " ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um Datensätze zu
	 * suchen, die in der angegebenen Spalte ein angegebenen double-Wert
	 * enthalten.
	 * 
	 * @see org.db.main.AbstractQuery#search(java.lang.String, double)
	 */
	@Test
	public void testSearchStringDouble() {
		double search = 25.698;
		
		assertThat(_test.search(_dsc, search), is("SELECT " + _id + ", " +
				_dsc + " FROM " + _tableName + " WHERE " + _dsc + " = " +
				search + " ORDER BY " + _id + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach der angegebenen Spalte zu sortieren.
	 * 
	 * @see org.db.main.AbstractQuery#sort(java.lang.String)
	 */
	@Test
	public void testSortString() {
		assertThat(_test.sort(_dsc), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach den angegebenen Spalten zu sortieren.
	 * 
	 * Test method for {@link org.db.main.AbstractQuery#sort(java.lang.String[])}.
	 */
	@Test
	public void testSortStringArray() {
		String[] sort = {_dsc, _id};
		assertThat(_test.sort(sort), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + ", " + _id + 
				" ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach den angegebenen Spalten aufsteigend zu sortieren.
	 * 
	 * Test method for {@link org.db.main.AbstractQuery#sort(java.lang.String[])}.
	 */
	@Test
	public void testSortStringArrayBooleanWithTrueAsParameter() {
		String[] sort = {_dsc, _id};
		assertThat(_test.sort(sort, true), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + ", " + _id + 
				" ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach den angegebenen Spalten absteigend zu sortieren.
	 * 
	 * Test method for {@link org.db.main.AbstractQuery#sort(java.lang.String[])}.
	 */
	@Test
	public void testSortStringArrayBooleanWithFalseAsParameter() {
		String[] sort = {_dsc, _id};
		assertThat(_test.sort(sort, false), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + ", " + _id + 
				" DESC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach der angegebenen Spalte zu sortieren.
	 * 
	 * @see org.db.main.AbstractQuery#sort(java.lang.String)
	 */
	@Test
	public void testSortAscString() {
		assertThat(_test.sortAsc(_dsc), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + " ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach den angegebenen Spalten zu sortieren.
	 * 
	 * Test method for {@link org.db.main.AbstractQuery#sort(java.lang.String[])}.
	 */
	@Test
	public void testSortAscStringArray() {
		String[] sort = {_dsc, _id};
		assertThat(_test.sortAsc(sort), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + ", " + _id + 
				" ASC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach der angegebenen Spalte zu sortieren.
	 * 
	 * @see org.db.main.AbstractQuery#sort(java.lang.String)
	 */
	@Test
	public void testSortDescString() {
		assertThat(_test.sortDesc(_dsc), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + " DESC"));
	}

	/**
	 * Testet, ob die richtige SQL-Abfrage generiert wird, um die Datensätze
	 * nach den angegebenen Spalten zu sortieren.
	 * 
	 * Test method for {@link org.db.main.AbstractQuery#sort(java.lang.String[])}.
	 */
	@Test
	public void testSortDescStringArray() {
		String[] sort = {_dsc, _id};
		assertThat(_test.sortDesc(sort), is("SELECT " + _id + ", " + _dsc + 
				" FROM " + _tableName + " ORDER BY " + _dsc + ", " + _id + 
				" DESC"));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusInsertOk()
	 * 
	 * @deprecated Wird durch {@link org.db.main.DbStatus#insertInTable(String)}
	 * ersetzt.
	 */
	@Test
	public void testStatusInsertOk() {
		assertThat(_test.statusInsertOk(), is("Datenbank: In die Tabelle '" + 
				_tableName + "' wurde ein Datensatz eingefügt."));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusInsertError()
	 * 
	 * @deprecated Wurde durch
	 * {@link org.db.main.DbStatus#notInsertInTable(String, Exception)} ersetzt.
	 */
	@Test
	public void testStatusInsertError() {
		assertThat(_test.statusInsertError(), is("Datenbank: In die Tabelle '" +
				_tableName +"' konnte kein Datensatz eingefügt werden."));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusUpdateOk(int)
	 * 
	 * @deprecated Wurde durch
	 * {@link org.db.main.DbStatus#updateInTable(String, int)} ersetzt.
	 */
	@Test
	public void testStatusUpdateOk() {
		int id = 100;
		assertThat(_test.statusUpdateOk(id), is("Datenbank: Der Datensatz " +
				"mit der ID " + id +" aus der Tabelle '" + _tableName +
				"' wurde geändert."));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusUpdateError(int)
	 * 
	 * @deprecated Wurde durch
	 * {@link org.db.main.DbStatus#notUpdateInTable(String, int, Exception)}
	 * ersetzt.
	 */
	@Test
	public void testStatusUpdateError() {
		int id = 100;
		assertThat(_test.statusUpdateError(id), is("Datenbank: Der Datensatz " +
				"mit der ID " + id + " aus der Tabelle '" + _tableName +
				"' konnte nicht geändert werden."));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusDeleteOk(int)
	 * 
	 * @deprecated Wurde durch
	 * {@link org.db.main.DbStatus#deleteFromTable(String, int)} ersetzt.
	 */
	@Test
	public void testStatusDeleteOk() {
		int id = 100;
		assertThat(_test.statusDeleteOk(id), is("Datenbank: Der Datensatz mit" +
				" der ID " + id + " wurde aus der Tabelle '" + _tableName +
				"' gelöscht."));
	}

	/**
	 * Testet, ob die richtige Status-Nachricht generiert wurde.
	 * 
	 * @see org.db.main.AbstractQuery#statusDeleteError(int)
	 * 
	 * @deprecated Wurde durch
	 * {@link org.db.main.DbStatus#notDeleteFromTable(String, int, Exception)}
	 * ersetzt.
	 */
	@Test
	public void testStatusDeleteError() {
		int id = 100;
		assertThat(_test.statusDeleteError(id), is("Datenbank: Der Datensatz " +
				"mit der ID " + id +" konnte nicht aus der Tabelle '" +
				_tableName + "' gelöscht werden."));
	}
}
