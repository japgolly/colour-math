package com.github.japgolly.colourmath.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.japgolly.colourmath.ColourFactory;
import com.github.japgolly.colourmath.illuminant.Illuminants;

/**
 * TODOC: com.github.japgolly.colourmath.testdata.TestData
 * 
 * @since 13/01/2013
 */
public class TestData {

	// Misc: http://www.easyrgb.com/index.php?X=CALC#Result
	// CIE: http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
	// HSL: http://colorizer.org/

	private final static ColourFactory CF = ColourFactory.getInstance();

	public final static Map<String, ColourVerification> TEST_DATA = new LinkedHashMap<String, ColourVerification>();

	static {
		Illuminants.setDefault(Illuminants.CIE1931.D65_LESS_PRECISE);
		TEST_DATA.put("0x123456", _123456());
		TEST_DATA.put("0xDC1030", DC1030());
		TEST_DATA.put("Black", black());
		TEST_DATA.put("White", white());
	}

	public static ColourVerification _123456() {
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
		c.setHSL01(210. / 360., 0.654, 0.204);
		c.XYZ = CF.XYZ(3.1565, 3.2561, 9.2644);
		c.Lab = CF.Lab(21.0418, 1.0538, -24.1012);
		c.Luv = CF.Luv(21.0418, -10.8326, -27.6459);
		return c;
	}

	public static ColourVerification DC1030() {
		// HTTP = #DC1030
		// Web safe = #CC0033
		// RGB 0-255 = 220.00 16.00 48.00
		// RGB 0-FF = DC 10 30
		// RGB 0-0.1 = 0.86275 0.06275 0.18824
		// CMY 0-0.1 = 0.13725 0.93725 0.81176
		// CMYK % = 0.000 92.727 78.182 13.725
		// XYZ = 30.234 15.800 4.252
		// Yxy = 15.800 0.60124 0.31420
		// CIE-L*ab = 46.710 71.011 40.265
		// CIE-L*CH = 46.710 81.633 29.554
		// CIE-L*uv = 46.710 142.152 24.007
		// HunterLab = 39.749 66.212 21.481
		//
		// Illuminant = D65
		// Observer = 2° (1931)

		// ColourRGB01(r=0.8627450980392157, g=0.06274509803921569, b=0.18823529411764706)

		// XYZ - 30.2378, 15.8047, 4.2543
		// Lab - 46.7173, 70.9967, 40.2673
		// Luv - 46.7173, 142.1252, 24.0155
		// LCHab - 46.7173, 81.6210, 29.5607°
		// LCHuv - 46.7173, 144.1399, 9.5909°

		ColourVerification c = new ColourVerification(0xFFDC1030, 0xDC1030FF, 0xDC1030);
		c.setRGB(0xDC, 0x10, 0x30);
		c.setHSL01(350.6 / 360., 0.864, 0.463);
		c.XYZ = CF.XYZ(30.2378, 15.8047, 4.2543);
		c.Lab = CF.Lab(46.7173, 70.9967, 40.2673);
		c.Luv = CF.Luv(46.7173, 142.1252, 24.0155);
		return c;
	}

	public static ColourVerification black() {
		ColourVerification c = new ColourVerification(0xFF000000, 0x000000FF, 0x000000);
		c.setRGB(0, 0, 0);
		c.setHSL01(0, 0, 0);
		c.XYZ = CF.XYZ(0, 0, 0);
		c.Lab = CF.Lab(0, 0, 0);
		c.Luv = CF.Luv(0, 0, 0);
		return c;
	}

	public static ColourVerification white() {
		ColourVerification c = new ColourVerification(0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFF);
		c.setRGB(255, 255, 255);
		c.setHSL01(0, 0, 1);
		c.XYZ = CF.XYZ(95.0470, 100, 108.8830);
		c.Lab = CF.Lab(100, 0, 0);
		c.Luv = CF.Luv(100, 0, 0);

		return c;
	}
}
