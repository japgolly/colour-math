package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

/**
 * TODOC: com.github.japgolly.colourmath.ColourXyz
 * 
 * @since 06/01/2013
 */
@Immutable
@ToString
@EqualsAndHashCode(callSuper = false)
public class ColourXYZ extends ColourAdapter {
	@Wither public final double x, y, z;

	ColourXYZ(double x, double y, double z) {
		// TODO xyz range?
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public ColourXYZ xyz() {
		return this;
	}

	@Override
	public ColourRGB01 rgb01() {
		// TODO light hardcoded
		double _x = x / 95.047;
		double _y = y / 100.0;
		double _z = z / 108.883;

		double r = _x * 3.2406 + _y * -1.5372 + _z * -0.4986;
		double g = _x * -0.9689 + _y * 1.8758 + _z * 0.0415;
		double b = _x * 0.0557 + _y * -0.2040 + _z * 1.0570;

		r = (r > 0.0031308) ? 1.055 * Math.pow(r, 1.0 / 2.4) - 0.055 : 12.92 * r;
		g = (g > 0.0031308) ? 1.055 * Math.pow(g, 1.0 / 2.4) - 0.055 : 12.92 * g;
		b = (b > 0.0031308) ? 1.055 * Math.pow(b, 1.0 / 2.4) - 0.055 : 12.92 * b;

		return new ColourRGB01((float) r, (float) g, (float) b);
	}

	@Override
	public ColourLAB lab() {
		// TODO light hardcoded
		double _x = x / 95.047;
		double _y = y / 100.0;
		double _z = z / 108.883;

		_x = (_x > ColourLAB.V_00886) ? Math.pow(_x, 1.0 / 3.0) : ColourLAB.V7_787 * _x + ColourLAB.V_16_116;
		_y = (_y > ColourLAB.V_00886) ? Math.pow(_y, 1.0 / 3.0) : ColourLAB.V7_787 * _y + ColourLAB.V_16_116;
		_z = (_z > ColourLAB.V_00886) ? Math.pow(_z, 1.0 / 3.0) : ColourLAB.V7_787 * _z + ColourLAB.V_16_116;

		double l = 116.0 * _y - 16.0;
		double a = 500.0 * (_x - _y);
		double b = 200.0 * (_y - _z);

		return new ColourLAB(l, a, b);
	}
}
