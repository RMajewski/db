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

package tests.org.db.datas;

import static org.hamcrest.MatcherAssert.*;

import org.db.datas.Data;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse {@link org.db.datas.Data}.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class TestData {
	/**
	 * Speichert die Instanz von Data.
	 */
	private Data _data;
	
	/**
	 * Initialisiert die Tests
	 */
	@Before
	public void setUp() {
		_data = new Data();
	}
	
	/**
	 * @test Testet, ob richtig initialisiert wurde.
	 * 
	 * @see org.db.datas.Data#Data()
	 */
	@Test
	public void testInitializeWithoutParameter() {
		assertThat(_data.getId(), is(equalTo(-1)));
	}
	
	/**
	 * @test Testet ob richtig initialisiert wurde.
	 * 
	 * @see org.db.datas.Data#Data(int)
	 */
	@Test
	public void testInitializeWithId() {
		int id = 100;
		_data = new Data(id);
		assertThat(_data.getId(), is(equalTo(id)));
	}
	
	/**
	 * @test Testet, ob die ID richtig gesetzt wird.
	 * 
	 * @see org.db.datas.Data#setId(int)
	 */
	@Test
	public void testSetId() {
		int id = 200;
		_data.setId(id);
		assertThat(_data.getId(), is(equalTo(id)));
	}
	
	/**
	 * @test Testet, ob die ID richtig zurück gegeben wird.
	 * 
	 * @see org.db.datas.Data#getId()
	 */
	@Test
	public void testGetId() {
		assertThat(_data.getId(), is(equalTo(-1)));
	}
	
	/**
	 * Testet, ob zwei Datensätze gleich sind.
	 * 
	 * @see org.db.datas.Data#equals(Object)
	 */
	@Test
	public void testEquals() {
		int id = 100;
		Data data = new Data(id);
		_data.setId(id);
		
		assertThat(_data.equals(data), is(true));
	}
	
	/**
	 * Testet, ob false zurückgegeben wird, wenn null übergeben wird.
	 * 
	 * @see org.db.datas.Data#equals(Object)
	 */
	@Test
	public void testEqualsWithNullAsParameter() {
		assertThat(_data.equals(null), is(false));
	}
	
	/**
	 * Testet, ob true zurückgegeben wird, wenn es mit sich selbst verglichen
	 * werden soll.
	 * 
	 * @see org.db.datas.Data#equals(Object)
	 */
	@Test
	public void testEqualsWithItselfAsParamter() {
		assertThat(_data.equals(_data), is(true));
	}
	
	/**
	 * Testet, ob false zurückgegeben wird, wenn eine andere Klasse übergeben
	 * wird.
	 * 
	 * @see org.db.datas.Data#equals(Object)
	 */
	@Test
	public void testEqualsWithStringAsParameter() {
		assertThat(_data.equals("Test"), is(false));
	}
	
	/**
	 * Testet, ob false zurückgegeben wird, wenn ein Datensatz mit einer anderen
	 * ID übergeben wird.
	 * 
	 * @see org.db.datas.Data#equals(Object)
	 */
	@Test
	public void testEqualsWithOtherIdAsParameter() {
		Data data = new Data(200);
		_data.setId(100);
		
		assertThat(_data.equals(data), is(false));
	}
}
