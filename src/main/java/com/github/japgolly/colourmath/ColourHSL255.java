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
public class ColourHSL255 extends ColourAdapter {
	@Wither public final int h, s, l;
	private final ColourHSL01 hsl01;

	ColourHSL255(int h, int s, int l) {
		Conditions.assert255(h, s, l);
		this.h = h;
		this.s = s;
		this.l = l;
		this.hsl01 = null;
	}

	ColourHSL255(ColourHSL01 hsl01) {
		this.h = scaleTo255(hsl01.h);
		this.s = scaleTo255(hsl01.s);
		this.l = scaleTo255(hsl01.l);
		this.hsl01 = hsl01;
	}

	// This is only used by @Wither so hsl01 is ignored
	private ColourHSL255(int h, int s, int l, ColourHSL01 hsl01) {
		this(h, s, l);
	}

	@Override
	public ColourHSL255 HSL255() {
		return this;
	}

	@Override
	public ColourHSL01 HSL01() {
		return (hsl01 != null) ? hsl01 : new ColourHSL01(h / 255., s / 255., l / 255.);
	}

	@Override
	public ColourRGB01 RGB01() {
		return HSL01().RGB01();
	}
}
