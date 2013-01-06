package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

/**
 * TODOC: com.github.japgolly.colourmath.ColourRGB
 * 
 * @since 05/01/2013
 */
@Immutable
@ToString
@EqualsAndHashCode(callSuper = false)
public class ColourRGB255 extends ColourAdapter {
	@Wither public final int r, g, b;

	ColourRGB255(int r, int g, int b) {
		Conditions.assert255(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getBlue() {
		return b;
	}

	public int getRed() {
		return r;
	}

	public int getGreen() {
		return g;
	}

	@Override
	public ColourRGB255 rgb255() {
		return this;
	}

	@Override
	public int rgb() {
		return (r << 16) | (g << 8) | b;
	}

	@Override
	public int argb() {
		return 0xFF000000 | rgb();
	}

	@Override
	public int rgba() {
		return (r << 24) | (g << 16) | (b << 8) | 0xFF;
	}

	@Override
	public ColourRGB01 rgb01() {
		return new ColourRGB01(r / 255f, g / 255f, b / 255f);
	}
}
