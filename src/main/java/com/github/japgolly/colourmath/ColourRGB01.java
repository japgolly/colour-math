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
public class ColourRGB01 extends ColourAdapter {
	@Wither public final float r, g, b;

	ColourRGB01(float r, float g, float b) {
		Conditions.assert01(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public ColourRGB01 rgb01() {
		return this;
	}

	@Override
	public ColourRGB255 rgb255() {
		return new ColourRGB255((int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
	}

	@Override
	public ColourHSL01 hsl01() {
		float max = Math.max(r, Math.max(g, b));
		float min = Math.min(r, Math.min(g, b));
		float h, s, l = (max + min) * 0.5f;

		if (max == min) {
			h = s = 0f; // achromatic
		} else {
			float d = max - min;
			s = l > 0.5f ? d / (2f - max - min) : d / (max + min);
			if (r == max) {
				h = (g - b) / d + (g < b ? 6f : 0f);
			} else if (g == max) {
				h = (b - r) / d + 2f;
			} else {
				h = (r - g) / d + 4f;
			}
			h /= 6f;
		}

		return new ColourHSL01(h, s, l);
	}

	@Override
	public ColourXYZ xyz() {
		float r = this.r;
		float g = this.g;
		float b = this.b;
		if (r > 0.04045f) {
			r = (r + 0.055f) / 1.055f;
			r = (float) Math.pow(r, 2.4f);
		} else {
			r = r / 12.92f;
		}
		if (g > 0.04045f) {
			g = (g + 0.055f) / 1.055f;
			g = (float) Math.pow(g, 2.4f);
		} else {
			g = g / 12.92f;
		}
		if (b > 0.04045f) {
			b = (b + 0.055f) / 1.055f;
			b = (float) Math.pow(b, 2.4f);
		} else {
			b = b / 12.92f;
		}
		r *= 100f;
		g *= 100f;
		b *= 100f;

		// TODO light hardcoded
		float x = r * 0.4124f + g * 0.3576f + b * 0.1805f;
		float y = r * 0.2126f + g * 0.7152f + b * 0.0722f;
		float z = r * 0.0193f + g * 0.1192f + b * 0.9505f;
		return new ColourXYZ(x, y, z);
	}
}
