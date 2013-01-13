package com.github.japgolly.colourmath;

import org.junit.Before;

/**
 * TODOC: com.github.japgolly.colourmath.ColourLuvTest
 * 
 * @since 13/01/2013
 */
public class ColourLuvTest extends Conversion123456TestBase {

	@Before
	public void setup() {
		c = ColourFactory.getInstance().Luv(21.041724990153774, -10.829707530829948, -27.644817767924394);
	}
}
