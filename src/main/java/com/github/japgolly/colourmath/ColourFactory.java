package com.github.japgolly.colourmath;

import javax.annotation.concurrent.ThreadSafe;


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

	public ColourLAB LAB(float l, float a, float b) {
		return new ColourLAB(l, a, b);
	}

	public ColourLAB LAB(float[] lab) {
		return new ColourLAB(lab[0], lab[1], lab[2]);
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

	public static ColourRGB255 RGB(int rgb) {
		return new ColourRGB255((rgb & 0xFF0000) >>> 16, (rgb & 0xFF00) >>> 8, rgb & 0xFF);
	}

	public static ColourRGB255 ARGB(int argb) {
		return RGB(argb);
	}

	public static ColourRGB255 RGBA(int rgba) {
		return new ColourRGB255((rgba & 0xFF000000) >>> 24, (rgba & 0xFF0000) >>> 16, (rgba & 0xFF00) >>> 8);
	}

	public ColourXYZ XYZ(float x, float y, float z) {
		return new ColourXYZ(x, y, z);
	}

	public ColourXYZ XYZ(float[] xyz) {
		return new ColourXYZ(xyz[0], xyz[1], xyz[2]);
	}
}