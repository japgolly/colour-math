package com.github.japgolly.colourmath;

import org.junit.Before;

/**
 * TODOC: com.github.japgolly.colourmath.ColourRGB255Test2
 * 
 * @since 06/01/2013
 */
public class ColourRGB01Test extends Conversion123456TestBase {

	@Before
	public void setup() {
		c = new ColourRGB01(0x12 / 255f, 0x34 / 255f, 0x56 / 255f);
	}
}
