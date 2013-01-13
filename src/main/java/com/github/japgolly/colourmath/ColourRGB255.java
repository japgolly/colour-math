package com.github.japgolly.colourmath;

import static com.github.japgolly.colourmath.MathFunc.scaleTo255;

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
	private final ColourRGB01 rgb01;

	ColourRGB255(int r, int g, int b) {
		Conditions.assert255(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
		this.rgb01 = null;
	}

	ColourRGB255(ColourRGB01 rgb01) {
		this.r = scaleTo255(rgb01.r);
		this.g = scaleTo255(rgb01.g);
		this.b = scaleTo255(rgb01.b);
		this.rgb01 = rgb01;
	}

	// This is only used by @Wither so hsl01 is ignored
	private ColourRGB255(int r, int g, int b, ColourRGB01 rgb01) {
		this(r, g, b);
	}

	public int[] toArray(int[] array) {
		array[0] = r;
		array[1] = g;
		array[2] = b;
		return array;
	}

	@Override
	public ColourRGB255 RGB255() {
		return this;
	}

	@Override
	public int RGB() {
		return (r << 16) | (g << 8) | b;
	}

	@Override
	public int ARGB() {
		return 0xFF000000 | RGB();
	}

	@Override
	public int RGBA() {
		return (r << 24) | (g << 16) | (b << 8) | 0xFF;
	}

	@Override
	public ColourRGB01 RGB01() {
		return (rgb01 != null) ? rgb01 : new ColourRGB01(r / 255., g / 255., b / 255.);
	}
}
