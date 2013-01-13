package com.github.japgolly.colourmath.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.japgolly.colourmath.Colour;
import com.github.japgolly.colourmath.ColourFactory;
import com.github.japgolly.colourmath.illuminant.Illuminants;

/**
 * TODOC: com.github.japgolly.colourmath.testdata.TestData
 * 
 * @since 13/01/2013
 */
public class TestData {

	private final static ColourFactory CF = ColourFactory.getInstance();

	public final static Map<String, Colour> TEST_DATA = new LinkedHashMap<String, Colour>();

	static {
		Illuminants.setDefault(Illuminants.CIE1931.D65);
		TEST_DATA.put("0x123456", get_123456());
	}

	public static Colour get_123456() {
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

		// XYZ 3.1565, 3.2561, 9.2644
		// Lab 21.0418, 1.0538, -24.1012
		// Luv 21.0418, -10.8326, -27.6459
		// LCHab 21.0418, 24.1242, 272.5037°
		// LCHuv 21.0418, 29.6924, 248.6031°

		ColourVerification c = new ColourVerification(0xFF123456, 0x123456FF, 0x123456);

		c.setRGB(0x12, 0x34, 0x56);
		c.setHSL(210. / 360., 0.654, 0.204);
		c.XYZ = CF.XYZ(3.1565, 3.2561, 9.2644);
		c.Lab = CF.Lab(21.0418, 1.0538, -24.1012);
		c.Luv = CF.Luv(21.0418, -10.8326, -27.6459);
		return c;
	}
}
