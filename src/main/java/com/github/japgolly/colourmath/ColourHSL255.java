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
public class ColourHSL255 extends ColourAdapter {
	@Wither public final int h, s, l;

	ColourHSL255(int h, int s, int l) {
		Conditions.assert255(h, s, l);
		this.h = h;
		this.s = s;
		this.l = l;
	}

	@Override
	public ColourHSL255 hsl255() {
		return this;
	}

	@Override
	public ColourHSL01 hsl01() {
		return new ColourHSL01(h / 255f, s / 255f, l / 255f);
	}

	@Override
	public ColourRGB01 rgb01() {
		return hsl01().rgb01();
	}
}
