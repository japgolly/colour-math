package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @since 13/01/2013
 */
public class ColourRGB01Test {

	@Test
	public void testRGB255RetainsPrecisionFromRGB01() {
		ColourRGB01 a = ColourFactory.getInstance().RGB01(0.312345, 0.456789, 0.976813);
		assertThat(a.RGB255().RGB01()).isSameAs(a);
		assertThat(a.RGB255().XYZ()).isEqualTo(a.XYZ());
	}
}
