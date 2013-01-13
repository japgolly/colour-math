package com.github.japgolly.colourmath;

import static com.github.japgolly.colourmath.MathFunc.scaleTo255;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants.CIE1931;

/**
 * TODOC: com.github.japgolly.colourmath.ColourRGB
 * 
 * @since 05/01/2013
 */
@Immutable
@ToString
@EqualsAndHashCode(callSuper = false)
public class ColourRGB01 extends ColourAdapter {
	@Wither public final double r, g, b;

	ColourRGB01(double r, double g, double b) {
		Conditions.assert01(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public ColourRGB01 RGB01() {
		return this;
	}

	@Override
	public ColourRGB255 RGB255() {
		return new ColourRGB255(scaleTo255(r), scaleTo255(g), scaleTo255(b));
	}

	@Override
	public ColourHSL01 HSL01() {
		double max = Math.max(r, Math.max(g, b));
		double min = Math.min(r, Math.min(g, b));
		double h, s, l = (max + min) * 0.5;

		if (max == min) {
			h = s = 0; // achromatic
		} else {
			double d = max - min;
			s = l > 0.5 ? d / (2. - max - min) : d / (max + min);
			if (r == max) {
				h = (g - b) / d + (g < b ? 6. : 0.);
			} else if (g == max) {
				h = (b - r) / d + 2.;
			} else {
				h = (r - g) / d + 4.;
			}
			h /= 6.;
		}

		return new ColourHSL01(h, s, l);
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		double r = this.r;
		double g = this.g;
		double b = this.b;

		r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
		g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
		b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;

		r *= 100.0;
		g *= 100.0;
		b *= 100.0;

		double x = MathFunc.multiplyRow(sRGB_TO_XYZ[0], r, g, b);
		double y = MathFunc.multiplyRow(sRGB_TO_XYZ[1], r, g, b);
		double z = MathFunc.multiplyRow(sRGB_TO_XYZ[2], r, g, b);

		final ColourXYZ c = new ColourXYZ(x, y, z, CIE1931.D65);

		return c.illuminant.equals(illuminant) ? c : c.XYZ(illuminant);
	}
}
