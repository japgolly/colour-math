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
	@Wither public final double l, a, b;

	ColourLAB(double l, double a, double b) {
		Conditions.assert100(l);
		Conditions.assert128_127(a, b);
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

	static final double V_16_116 = 16.0 / 116.0;
	static final double V_00886 = Math.pow(6.0 / 29.0, 3); // 0.008856451679035631
	static final double V7_787 = Math.pow(29.0 / 6.0, 2.0) / 3.0; // 7.787037037037035

	@Override
	public ColourXYZ xyz() {
		double y = (l + 16.0) / 116.0;
		double x = a / 500.0 + y;
		double z = y - b / 200.0;

		final double x3 = Math.pow(x, 3);
		final double y3 = Math.pow(y, 3);
		final double z3 = Math.pow(z, 3);

		x = (x3 > V_00886) ? x3 : (x - V_16_116) / V7_787;
		y = (y3 > V_00886) ? y3 : (y - V_16_116) / V7_787;
		z = (z3 > V_00886) ? z3 : (z - V_16_116) / V7_787;

		// TODO light hardcoded
		x *= 95.047;
		y *= 100.0;
		z *= 108.883;

		return new ColourXYZ(x, y, z);
	}

	@Override
	public double deltaE_94(Colour otherColour) {
		final ColourLAB lab1 = this;
		final ColourLAB lab2 = otherColour.lab();

		final double c1 = Math.sqrt(lab1.a * lab1.a + lab1.b * lab1.b);
		final double c2 = Math.sqrt(lab2.a * lab2.a + lab2.b * lab2.b);
		final double dc = c1 - c2;
		final double dl = lab1.l - lab2.l;
		final double da = lab1.a - lab2.a;
		final double db = lab1.b - lab2.b;
		final double dh = Math.sqrt((da * da) + (db * db) - (dc * dc));
		final double first = dl;
		final double second = dc / (1.0 + 0.045 * c1);
		final double third = dh / (1.0 + 0.015 * c1);
		return Math.sqrt(first * first + second * second + third * third);
	}
}
