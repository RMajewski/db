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

package tests.org.db.tables.models;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.db.datas.Data;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse {@link org.db.tables.models.TableModelData}.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class TestTableModelData {
	/**
	 * Speichert die Instanz des TableModelData
	 */
	private TableModelImpl _data;

	/**
	 */
	@Before
	public void setUp() {
		_data = new TableModelImpl();
	}

	/**
	 * Testet ob die Klasse richtig initialisiert wurde.
	 * 
	 * @see org.db.tables.models.TableModelData#TableModelData()
	 */
	@Test
	public void testTableModelData() {
		assertThat(_data.getRowCount(), is(1));
	}

	/**
	 * Testet, ob die Anzahl der Zeilen richtig zurückgegeben wird.
	 * 
	 * @see org.db.tables.models.TableModelData#getRowCount()
	 */
	@Test
	public void testGetRowCount() {
		assertThat(_data.getRowCount(), is(1));
	}

	/**
	 * Testet, ob die 1. Zeile und die 1. Spalte ausgelesen werden kann.
	 * 
	 * @see org.db.tables.models.TableModelData#getValueAt(int, int)
	 */
	@Test
	public void testGetValueAtRight() {
		assertThat(_data.getValueAt(0, 0), is (100));
	}

	/**
	 * Testet, ob dataRefresh richtig ausgeführt wird.
	 * 
	 * @see org.db.tables.models.TableModelData#dataRefresh(boolean)
	 */
	@Test
	public void testDataRefresh() {
		_data.dataRefresh(false);
		
		assertThat(_data.getRowCount(), is(1));
	}

	/**
	 * Testet, ob der richtige Datensatz zurückgegeben wird.
	 * 
	 * @see org.db.tables.models.TableModelData#getDataRecord(int)
	 */
	@Test
	public void testGetDataRecord() {
		Data data = new Data(100);
		assertThat(_data.getDataRecord(0), is(data));
	}

	/**
	 * Testet, ob die richtige Klasse für die 1. Spalte zurückgegeben wird.
	 * 
	 * @see org.db.tables.models.TableModelData#getColumnClass(int)
	 */
	@Test
	public void testGetColumnClassIntRight() {
		assertThat(_data.getColumnClass(0).getName(),
				is(Integer.class.getName()));
	}

}
