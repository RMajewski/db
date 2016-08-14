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

package tests.org.db.comparator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.db.comparator.CompInteger;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet den Komparator für zwei Integer.
 * 
 * @author René Majewski
 *
 * @version 0.1
 * @since 0.1
 */
public class TestCompInteger {
	/**
	 * Speichert die Instanz des Komparators.
	 */
	private CompInteger _comp;
	
	/**
	 * Initialisiert die Tests
	 */
	@Before
	public void setUp() {
		_comp = new CompInteger();
	}
	
	/**
	 * @test Testet ob -1 das Ergebnis is, wenn Integer 1 kleiner als Integer 2
	 * ist.
	 */
	@Test
	public void testCompareLess() {
		assertThat(_comp.compare(1, 2), is(equalTo(-1)));
	}
	
	/**
	 * @test Testet ob 0 das Ergebnis ist, wenn Integer 1 und Integer 2 gleich
	 * groß sind.
	 */
	@Test
	public void testCompareEqual() {
		assertThat(_comp.compare(2, 2), is(equalTo(0)));
	}
	
	/**
	 * @test Testet ob +1 das Ergebnis ist, wenn Integer 1 größer als Integer 2
	 * ist.
	 */
	@Test
	public void testCompareHigher() {
		assertThat(_comp.compare(2, 1), is(equalTo(1)));
	}

}
