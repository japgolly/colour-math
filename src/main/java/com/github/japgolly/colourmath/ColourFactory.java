package com.github.japgolly.colourmath;

import javax.annotation.concurrent.ThreadSafe;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;

/**
 * TODOC: com.github.japgolly.colourmath.ColourFactory
 * 
 * @since 06/01/2013
 */
@ThreadSafe
public class ColourFactory {

	private static final ColourFactory INSTANCE = new ColourFactory();

	public static ColourFactory getInstance() {
		return INSTANCE;
	}

	// ------------------------------------------------------------------------------------

	public ColourRGB255 RGB(int rgb) {
		return new ColourRGB255((rgb & 0xFF0000) >>> 16, (rgb & 0xFF00) >>> 8, rgb & 0xFF);
	}

	public ColourRGB255 ARGB(int argb) {
		return RGB(argb);
	}

	public ColourRGB255 RGBA(int rgba) {
		return new ColourRGB255((rgba & 0xFF000000) >>> 24, (rgba & 0xFF0000) >>> 16, (rgba & 0xFF00) >>> 8);
	}

	public ColourRGB01 RGB01(double r, double g, double b) {
		return new ColourRGB01(r, g, b);
	}

	public ColourRGB01 RGB01(double[] rgb) {
		return new ColourRGB01(rgb[0], rgb[1], rgb[2]);
	}

	public ColourRGB255 RGB255(int r, int g, int b) {
		return new ColourRGB255(r, g, b);
	}

	public ColourRGB255 RGB255(int[] rgb) {
		return new ColourRGB255(rgb[0], rgb[1], rgb[2]);
	}

	// ------------------------------------------------------------------------------------

	public ColourHSL01 HSL01(double h, double s, double l) {
		return new ColourHSL01(h, s, l);
	}

	public ColourHSL01 HSL01(double[] hsl) {
		return new ColourHSL01(hsl[0], hsl[1], hsl[2]);
	}

	public ColourHSL255 HSL255(int h, int s, int l) {
		return new ColourHSL255(h, s, l);
	}

	public ColourHSL255 HSL255(int[] hsl) {
		return new ColourHSL255(hsl[0], hsl[1], hsl[2]);
	}

	// ------------------------------------------------------------------------------------

	public ColourXYZ XYZ(double X, double Y, double Z) {
		return new ColourXYZ(X, Y, Z, Illuminants.getDefault());
	}

	public ColourXYZ XYZ(double X, double Y, double Z, Illuminant illuminant) {
		return new ColourXYZ(X, Y, Z, illuminant);
	}

	public ColourXYZ XYZ(double[] XYZ) {
		return new ColourXYZ(XYZ[0], XYZ[1], XYZ[2], Illuminants.getDefault());
	}

	public ColourXYZ XYZ(double[] XYZ, Illuminant illuminant) {
		return new ColourXYZ(XYZ[0], XYZ[1], XYZ[2], illuminant);
	}

	// ------------------------------------------------------------------------------------

	public ColourLab Lab(double L, double a, double b) {
		return new ColourLab(L, a, b, Illuminants.getDefault());
	}

	public ColourLab Lab(double L, double a, double b, Illuminant illuminant) {
		return new ColourLab(L, a, b, illuminant);
	}

	public ColourLab Lab(double[] Lab) {
		return new ColourLab(Lab[0], Lab[1], Lab[2], Illuminants.getDefault());
	}

	public ColourLab Lab(double[] Lab, Illuminant illuminant) {
		return new ColourLab(Lab[0], Lab[1], Lab[2], illuminant);
	}

	// ------------------------------------------------------------------------------------

	public ColourLuv Luv(double L, double u, double v) {
		return new ColourLuv(L, u, v, Illuminants.getDefault());
	}

	public ColourLuv Luv(double L, double u, double v, Illuminant illuminant) {
		return new ColourLuv(L, u, v, illuminant);
	}

	public ColourLuv Luv(double[] Luv) {
		return new ColourLuv(Luv[0], Luv[1], Luv[2], Illuminants.getDefault());
	}

	public ColourLuv Luv(double[] Luv, Illuminant illuminant) {
		return new ColourLuv(Luv[0], Luv[1], Luv[2], illuminant);
	}

}
