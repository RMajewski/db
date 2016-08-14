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

import org.db.datas.DataDescription;

import static org.hamcrest.CoreMatchers.*;

import org.db.datas.Data;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse
 * {@link org.db.datas.DataDescription}.
 * 
 * @author René Majewski
 *
 * @since 0.1
 */
public class TestDataDescription {
	/**
	 * Speichert die Instanz von DataDescription.
	 */
	private DataDescription _data;
	
	/**
	 * Initialisiert die Tests.
	 */
	@Before
	public void setUp() {
		_data = new DataDescription();
	}
	
	/**
	 * @test Testet, ob DataDescription von
	 * {@link org.db.datas.Data} abgeleitet wurde. 
	 */
	@Test
	public void testInheritData() {
		assertThat(DataDescription.class.getSuperclass().getName(),
				is(equalTo(Data.class.getName())));
	}

	/**
	 * @test Testet, ob richtig initialisiert wurde.
	 * 
	 * @see org.db.datas.DataDescription#DataDescription()
	 */
	@Test
	public void testDataDescription() {
		assertThat(_data.getId(), is(equalTo(-1)));
		assertThat(_data.getDescription(), is(equalTo(new String())));
	}

	/**
	 * @test Testet, ob richtig initialisiert wurde.
	 * 
	 * @see org.db.datas.DataDescription#DataDescription(int, java.lang.String)
	 */
	@Test
	public void testDataDescriptionIntString() {
		int id = 100;
		String description = "Dies ist ein Test";
		_data = new DataDescription(id, description);
		
		assertThat(_data.getId(), is(equalTo(id)));
		assertThat(_data.getDescription(), is(equalTo(description)));
	}

	/**
	 * @test Testet, ob die Beschreibung gesetzt werden kann.
	 * 
	 * @see org.db.datas.DataDescription#setDescription(java.lang.String)
	 */
	@Test
	public void testSetDescription() {
		String description = "Dies ist ein Test";
		_data.setDescription(description);
		
		assertThat(_data.getDescription(), is(equalTo(description)));
	}

	/**
	 * @test Testet, ob die Beschreibung richtig zurück gegeben wird.
	 * 
	 * @see org.db.datas.DataDescription#getDescription()
	 */
	@Test
	public void testGetDescription() {
		assertThat(_data.getDescription(), is(equalTo(new String())));
	}

}
