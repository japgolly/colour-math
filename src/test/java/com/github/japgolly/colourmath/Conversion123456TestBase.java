package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import org.fest.assertions.data.Offset;
import org.junit.Test;

/**
 * TODOC: com.github.japgolly.colourmath.ColourRGB255Test
 * 
 * @since 06/01/2013
 */
public abstract class Conversion123456TestBase {

	// HTTP = #123456
	// Web safe = #003366
	// RGB 0-255 = 18.00 52.00 86.00
	// RGB 0-FF = 12 34 56
	// RGB 0-0.1 = 0.07059 0.20392 0.33725
	// CMY 0-0.1 = 0.92941 0.79608 0.66275
	// CMYK % = 79.070 39.535 0.000 66.275
	// XYZ = 3.157 3.256 9.266
	// Yxy = 3.256 0.20135 0.20768
	// CIE-L*ab = 21.043 1.059 -24.105
	// CIE-L*CH = 21.043 24.128 272.515
	// CIE-L*uv = 21.043 -10.831 -27.651
	// HunterLab = 18.046 -0.351 -17.813
	//
	// Illuminant = D65
	// Observer = 2° (1931)

	// RGB = (18, 52, 86).
	// CMY = (237, 203, 169);
	// HSB = (210, 0.791, 0.337);
	// HSV = (210, 0.791, 0.337);
	// HSI = (210, 0.654, 0.204);
	// HSL = (210, 0.654, 0.204);
	// YIQ = (0.179, -0.122, 0.013);
	// YUV = (0.179, 0.078, -0.095);
	// YCbCr = (55, 148, 111);
	// CIE = (0.163, 0.185, 0.346);

	protected Colour c;

	@Test
	public void testRGB() {
		assertThat(c.rgb()).isEqualTo(0x123456);
	}

	@Test
	public void testARGB() {
		assertThat(c.argb()).isEqualTo(0xFF123456);
	}

	@Test
	public void testRGBA() {
		assertThat(c.rgba()).isEqualTo(0x123456FF);
	}

	@Test
	public void testRGB01() {
		assertRGB01(0.07059f, 0.20392f, 0.33725f);
	}

	@Test
	public void testRGB255() {
		assertRGB255(0x12, 0x34, 0x56);
	}

	@Test
	public void testHSL01() {
		assertHSL01(210 / 360f, .654f, .204f);
	}

	@Test
	public void testHSL255() {
		assertHSL255(148, 166, 52);
	}

	@Test
	public void testXYZ() {
		assertXYZ(3.157f, 3.256f, 9.266f);
	}

	@Test
	public void testLAB() {
		assertLAB(21.043f, 1.059f, -24.105f);
	}

	// -----------------------------------------------------------------------------------------------------------------

	protected void assertRGB01(float r, float g, float b) {
		final Offset<Float> tolerance = offset(.00001f);
		final ColourRGB01 c2 = c.rgb01();
		assertThat(c2.r).isEqualTo(r, tolerance);
		assertThat(c2.g).isEqualTo(g, tolerance);
		assertThat(c2.b).isEqualTo(b, tolerance);
	}

	protected void assertRGB255(int r, int g, int b) {
		final ColourRGB255 c2 = c.rgb255();
		assertThat(c2.r).isEqualTo(r);
		assertThat(c2.g).isEqualTo(g);
		assertThat(c2.b).isEqualTo(b);
	}

	protected void assertHSL01(float h, float s, float l) {
		final Offset<Float> tolerance = offset(.0005f);
		final ColourHSL01 c2 = c.hsl01();
		assertThat(c2.h).isEqualTo(h, tolerance);
		assertThat(c2.s).isEqualTo(s, tolerance);
		assertThat(c2.l).isEqualTo(l, tolerance);
	}

	protected void assertHSL255(int h, int s, int l) {
		final ColourHSL255 c2 = c.hsl255();
		assertThat(c2.h).isEqualTo(h);
		assertThat(c2.s).isEqualTo(s);
		assertThat(c2.l).isEqualTo(l);
	}

	protected void assertXYZ(float x, float y, float z) {
		final Offset<Float> tolerance = offset(.0005f);
		final ColourXYZ c2 = c.xyz();
		assertThat(c2.x).isEqualTo(x, tolerance);
		assertThat(c2.y).isEqualTo(y, tolerance);
		assertThat(c2.z).isEqualTo(z, tolerance);
	}

	protected void assertLAB(float l, float a, float b) {
		final Offset<Float> tolerance = offset(.0005f);
		final ColourLAB c2 = c.lab();
		assertThat(c2.l).isEqualTo(l, tolerance);
		assertThat(c2.a).isEqualTo(a, tolerance);
		assertThat(c2.b).isEqualTo(b, tolerance);
	}
}