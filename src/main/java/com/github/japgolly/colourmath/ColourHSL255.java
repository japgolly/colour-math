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
	public ColourHSL255 HSL255() {
		return this;
	}

	@Override
	public ColourHSL01 HSL01() {
		return new ColourHSL01(h / 255., s / 255., l / 255.);
	}

	@Override
	public ColourRGB01 RGB01() {
		return HSL01().RGB01();
	}
}
