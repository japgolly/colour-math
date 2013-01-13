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
	@Wither public final double h, s, l;

	ColourHSL01(double h, double s, double l) {
		Conditions.assert01(h, s, l);
		this.h = h;
		this.s = s;
		this.l = l;
	}

	@Override
	public ColourHSL01 HSL01() {
		return this;
	}

	@Override
	public ColourHSL255 HSL255() {
		return new ColourHSL255(this);
	}

	@Override
	public ColourRGB01 RGB01() {
		final double r, g, b;
		if (s == 0.) {
			r = g = b = l; // achromatic
		} else {
			double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
			double p = 2. * l - q;
			r = hue2rgb(p, q, h + 1. / 3.);
			g = hue2rgb(p, q, h);
			b = hue2rgb(p, q, h - 1. / 3.);
		}
		return new ColourRGB01(r, g, b);
	}

	private static double hue2rgb(double p, double q, double t) {
		if (t < 0.) {
			t += 1.;
		}
		if (t > 1.) {
			t -= 1.;
		}
		if (t < 1. / 6.) {
			return p + (q - p) * 6. * t;
		}
		if (t < 1. / 2.) {
			return q;
		}
		if (t < 2. / 3.) {
			return p + (q - p) * (2. / 3. - t) * 6.;
		}
		return p;
	}
}
