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
public class ColourLAB extends ColourAdapter {
	@Wither public final float l, a, b;

	ColourLAB(float l, float a, float b) {
		Conditions.assert100(l);
		this.l = l;
		this.a = a;
		this.b = b;
	}

	@Override
	public ColourLAB lab() {
		return this;
	}

	@Override
	public ColourRGB01 rgb01() {
		return xyz().rgb01();
	}

	@Override
	public ColourXYZ xyz() {
		float y = (l + 16f) / 116f;
		float x = a / 500f + y;
		float z = y - b / 200f;

		final float x3 = x * x * x;
		final float y3 = y * y * y;
		final float z3 = z * z * z;

		x = (x3 > 0.008856f) ? x3 : (x - 16f / 116f) / 7.787f;
		y = (y3 > 0.008856f) ? y3 : (y - 16f / 116f) / 7.787f;
		z = (z3 > 0.008856f) ? z3 : (z - 16f / 116f) / 7.787f;

		// TODO light hardcoded
		x *= 95.047f;
		y *= 100f;
		z *= 108.883f;

		return new ColourXYZ(x, y, z);
	}

	@Override
	public float deltaE_94(Colour otherColour) {
		final ColourLAB lab1 = this;
		final ColourLAB lab2 = otherColour.lab();

		final float c1f = (float) Math.sqrt(lab1.a * lab1.a + lab1.b * lab1.b);
		final float c2f = (float) Math.sqrt(lab2.a * lab2.a + lab2.b * lab2.b);
		final float dc = c1f - c2f;
		final float dl = lab1.l - lab2.l;
		final float da = lab1.a - lab2.a;
		final float db = lab1.b - lab2.b;
		final float dh = (float) Math.sqrt((da * da) + (db * db) - (dc * dc));
		final float first = dl;
		final float second = dc / (1f + 0.045f * c1f);
		final float third = dh / (1f + 0.015f * c1f);
		return (float) Math.sqrt(first * first + second * second + third * third);
	}
}
