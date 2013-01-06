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
	@Wither public final float x, y, z;

	ColourXYZ(float x, float y, float z) {
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
		float _x = x / 95.047f;
		float _y = y / 100f;
		float _z = z / 108.883f;

		float r = _x * 3.2406f + _y * -1.5372f + _z * -0.4986f;
		float g = _x * -0.9689f + _y * 1.8758f + _z * 0.0415f;
		float b = _x * 0.0557f + _y * -0.2040f + _z * 1.0570f;

		r = (r > 0.0031308f) ? 1.055f * (float) Math.pow(r, 1f / 2.4f) - 0.055f : 12.92f * r;
		g = (g > 0.0031308f) ? 1.055f * (float) Math.pow(g, 1f / 2.4f) - 0.055f : 12.92f * g;
		b = (b > 0.0031308f) ? 1.055f * (float) Math.pow(b, 1f / 2.4f) - 0.055f : 12.92f * b;

		return new ColourRGB01(r, g, b);
	}

	@Override
	public ColourLAB lab() {
		// TODO light hardcoded
		float _x = x / 95.047f;
		float _y = y / 100f;
		float _z = z / 108.883f;

		_x = (_x > 0.008856f) ? (float) Math.pow(_x, 1f / 3f) : 7.787f * _x + 16f / 116f;
		_y = (_y > 0.008856f) ? (float) Math.pow(_y, 1f / 3f) : 7.787f * _y + 16f / 116f;
		_z = (_z > 0.008856f) ? (float) Math.pow(_z, 1f / 3f) : 7.787f * _z + 16f / 116f;

		float l = 116f * _y - 16f;
		float a = 500f * (_x - _y);
		float b = 200f * (_y - _z);

		return new ColourLAB(l, a, b);
	}
}
