package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;

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

	private static final double[][] sRGB_TO_XYZ = { { 0.4124564, 0.3575761, 0.1804375 },
			{ 0.2126729, 0.7151522, 0.0721750 }, { 0.0193339, 0.1191920, 0.9503041 } };

	@Override
	public ColourXYZ xyz(Illuminant illuminant) {
		double r = this.r;
		double g = this.g;
		double b = this.b;

		r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
		g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
		b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;

		r *= 100.0;
		g *= 100.0;
		b *= 100.0;

		double x = MatrixMath.multiplyRow(sRGB_TO_XYZ[0], r, g, b);
		double y = MatrixMath.multiplyRow(sRGB_TO_XYZ[1], r, g, b);
		double z = MatrixMath.multiplyRow(sRGB_TO_XYZ[2], r, g, b);

		final ColourXYZ c = new ColourXYZ(x, y, z, Illuminants.CIE1931.D65);

		return c.illuminant.equals(illuminant) ? c : c.xyz(illuminant);
	}
}
