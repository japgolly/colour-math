package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import org.fest.assertions.data.Offset;
import org.junit.Test;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;
import com.github.japgolly.colourmath.illuminant.Illuminants.CIE1931;

/**
 * TODOC: com.github.japgolly.colourmath.IlluminantConversionTest
 * 
 * @since 07/01/2013
 */
public class IlluminantConversionTest {

	private static final ColourFactory CF = ColourFactory.getInstance();

	@Test
	public void testRGBToXYZ() throws Exception {
		Colour c = CF.RGB255(32, 128, 200);

		// sRGB -> D65 2deg
		assertXYZ(c.xyz(CIE1931.D65), 18.740, 19.916, 57.500, CIE1931.D65);

		// sRGB -> F2 2deg
		assertXYZ(c.xyz(CIE1931.F2), 16.6180, 18.8858, 35.2862, CIE1931.F2);
	}

	@Test
	public void testXYZToRGB() throws Exception {
		// D65 2deg -> sRGB
		assertRGB255(CF.XYZ(18.740, 19.916, 57.500, CIE1931.D65).rgb255(), 32, 128, 200);

		// sRGB -> F2 2deg
		assertRGB255(CF.XYZ(16.6180, 18.8858, 35.2862, CIE1931.F2).rgb255(), 32, 127, 200);
	}

	@Test
	public void testXYZToXYZ() throws Exception {
		// D65 2deg -> D65 2deg
		assertXYZ(CF.XYZ(40, 20, 60, CIE1931.D65).xyz(CIE1931.D65), 40, 20, 60, CIE1931.D65);

		// D65 2deg -> F2 2deg
		assertXYZ(CF.XYZ(40, 20, 60, CIE1931.D65).xyz(CIE1931.F2), 39.6001, 20.16, 36.5192, CIE1931.F2);

		// F2 2deg -> D65 2deg
		assertXYZ(CF.XYZ(39.6001, 20.16, 36.5192, CIE1931.F2).xyz(CIE1931.D65), 40, 20, 60, CIE1931.D65);
	}

	@Test
	public void testXYZToLAB() throws Exception {
		// http://www.brucelindbloom.com/index.html?ChromAdaptEval.html

		// D65 2deg -> D65 2deg
		assertLAB(CF.XYZ(40, 20, 60, CIE1931.D65).lab(CIE1931.D65), 51.8372, 82.2926, -47.0078, CIE1931.D65);

		// F2 2deg -> F2 2deg
		assertLAB(CF.XYZ(39.6001, 20.16, 36.5192, CIE1931.F2).lab(CIE1931.F2), 52.0176, 74.9936, -45.7825, CIE1931.F2);

		// D65 2deg -> F2 2deg
		assertLAB(CF.XYZ(40, 20, 60, CIE1931.D65).lab(CIE1931.F2), 52.0176, 74.9936, -45.7825, CIE1931.F2);
	}

	@Test
	public void testLABToXYZ() throws Exception {

		// D65 2deg -> D65 2deg
		assertXYZ(CF.LAB(51.8372, 82.2926, -47.0078, CIE1931.D65).xyz(CIE1931.D65), 40, 20, 60, CIE1931.D65);

		// F2 2deg -> F2 2deg
		assertXYZ(CF.LAB(52.0176, 74.9936, -45.7825, CIE1931.F2).xyz(CIE1931.D65), 40, 20, 60, CIE1931.D65);
	}

	@Test
	public void testLABToLAB() throws Exception {

		// D65 2deg -> D65 2deg
		assertLAB(CF.LAB(51.8372, 82.2926, -47.0078, CIE1931.D65).lab(CIE1931.D65), 51.8372, 82.2926, -47.0078,
				CIE1931.D65);

		// D65 2deg -> F2 2deg
		assertLAB(CF.LAB(51.8372, 82.2926, -47.0078, CIE1931.D65).lab(CIE1931.F2), 52.0176, 74.9936, -45.7825,
				CIE1931.F2);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	public void testUsesDefaultIlluminant() throws Exception {
		Illuminants.setDefault(Illuminants.CIE1964.D65);
		Colour c = ColourFactory.getInstance().RGB255(32, 128, 200);
		assertThat(c.xyz().illuminant).isEqualTo(Illuminants.CIE1964.D65);
	}

	protected void assertXYZ(ColourXYZ c, double x, double y, double z, Illuminant illuminant) {
		final Offset<Double> tolerance = offset(.1);
		assertThat(c.illuminant).isEqualTo(illuminant);
		assertThat(c.x).isEqualTo(x, tolerance);
		assertThat(c.y).isEqualTo(y, tolerance);
		assertThat(c.z).isEqualTo(z, tolerance);
	}

	protected void assertLAB(ColourLAB c, double x, double y, double z, Illuminant illuminant) {
		final Offset<Double> tolerance = offset(.1);
		assertThat(c.illuminant).isEqualTo(illuminant);
		assertThat(c.l).isEqualTo(x, tolerance);
		assertThat(c.a).isEqualTo(y, tolerance);
		assertThat(c.b).isEqualTo(z, tolerance);
	}

	protected void assertRGB255(ColourRGB255 c, int r, int g, int b) {
		assertThat(c.r).isEqualTo(r);
		assertThat(c.g).isEqualTo(g);
		assertThat(c.b).isEqualTo(b);
	}
}
