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
public class ColourHSL01 extends ColourAdapter {
	@Wither public final float h, s, l;

	ColourHSL01(float h, float s, float l) {
		Conditions.assert01(h, s, l);
		this.h = h;
		this.s = s;
		this.l = l;
	}

	@Override
	public ColourHSL01 hsl01() {
		return this;
	}

	@Override
	public ColourHSL255 hsl255() {
		return new ColourHSL255((int) (h * 255f), (int) (s * 255f), (int) (l * 255f));
	}

	@Override
	public ColourRGB01 rgb01() {
		final float r, g, b;
		if (s == 0f) {
			r = g = b = l; // achromatic
		} else {
			float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
			float p = 2f * l - q;
			r = hue2rgb(p, q, h + 1f / 3f);
			g = hue2rgb(p, q, h);
			b = hue2rgb(p, q, h - 1f / 3f);
		}
		return new ColourRGB01(r, g, b);
	}

	private static float hue2rgb(float p, float q, float t) {
		if (t < 0f) {
			t += 1f;
		}
		if (t > 1f) {
			t -= 1f;
		}
		if (t < 1f / 6f) {
			return p + (q - p) * 6f * t;
		}
		if (t < 1f / 2f) {
			return q;
		}
		if (t < 2f / 3f) {
			return p + (q - p) * (2f / 3f - t) * 6f;
		}
		return p;
	}
}
