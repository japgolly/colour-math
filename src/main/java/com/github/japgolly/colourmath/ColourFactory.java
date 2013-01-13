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

	public ColourRGB01 RGB01(float r, float g, float b) {
		return new ColourRGB01(r, g, b);
	}

	public ColourRGB01 RGB01(float[] rgb) {
		return new ColourRGB01(rgb[0], rgb[1], rgb[2]);
	}

	public ColourRGB255 RGB255(int r, int g, int b) {
		return new ColourRGB255(r, g, b);
	}

	public ColourRGB255 RGB255(int[] rgb) {
		return new ColourRGB255(rgb[0], rgb[1], rgb[2]);
	}

	// ------------------------------------------------------------------------------------

	public ColourHSL01 HSL01(float h, float s, float l) {
		return new ColourHSL01(h, s, l);
	}

	public ColourHSL01 HSL01(float[] hsl) {
		return new ColourHSL01(hsl[0], hsl[1], hsl[2]);
	}

	public ColourHSL255 HSL255(int h, int s, int l) {
		return new ColourHSL255(h, s, l);
	}

	public ColourHSL255 HSL255(int[] hsl) {
		return new ColourHSL255(hsl[0], hsl[1], hsl[2]);
	}

	// ------------------------------------------------------------------------------------

	public ColourXYZ XYZ(double x, double y, double z) {
		return new ColourXYZ(x, y, z, Illuminants.getDefault());
	}

	public ColourXYZ XYZ(double x, double y, double z, Illuminant illuminant) {
		return new ColourXYZ(x, y, z, illuminant);
	}

	public ColourXYZ XYZ(double[] xyz) {
		return new ColourXYZ(xyz[0], xyz[1], xyz[2], Illuminants.getDefault());
	}

	public ColourXYZ XYZ(double[] xyz, Illuminant illuminant) {
		return new ColourXYZ(xyz[0], xyz[1], xyz[2], illuminant);
	}

	// ------------------------------------------------------------------------------------

	public ColourLab Lab(double l, double a, double b) {
		return new ColourLab(l, a, b, Illuminants.getDefault());
	}

	public ColourLab Lab(double l, double a, double b, Illuminant illuminant) {
		return new ColourLab(l, a, b, illuminant);
	}

	public ColourLab Lab(double[] lab) {
		return new ColourLab(lab[0], lab[1], lab[2], Illuminants.getDefault());
	}

	public ColourLab Lab(double[] lab, Illuminant illuminant) {
		return new ColourLab(lab[0], lab[1], lab[2], illuminant);
	}

	// ------------------------------------------------------------------------------------

	public ColourLuv Luv(double l, double u, double v) {
		return new ColourLuv(l, u, v, Illuminants.getDefault());
	}

	public ColourLuv Luv(double[] lab) {
		return new ColourLuv(lab[0], lab[1], lab[2], Illuminants.getDefault());
	}

	public ColourLuv Luv(double l, double u, double v, Illuminant illuminant) {
		return new ColourLuv(l, u, v, illuminant);
	}

	public ColourLuv Luv(double[] lab, Illuminant illuminant) {
		return new ColourLuv(lab[0], lab[1], lab[2], illuminant);
	}

}
