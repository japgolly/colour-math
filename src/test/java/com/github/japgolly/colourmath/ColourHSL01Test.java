package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @since 13/01/2013
 */
public class ColourHSL01Test {

	@Test
	public void testHSL255RetainsPrecisionFromHSL01() {
		ColourHSL01 a = ColourFactory.getInstance().HSL01(0.312345, 0.456789, 0.976813);
		assertThat(a.HSL255().HSL01()).isSameAs(a);
		assertThat(a.HSL255().RGB01()).isEqualTo(a.RGB01());
	}
}
