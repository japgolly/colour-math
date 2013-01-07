package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;

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
	public final Illuminant illuminant;

	ColourLAB(double l, double a, double b, Illuminant illuminant) {
		Conditions.assert100(l);
		Conditions.assert128_127(a, b);
		this.l = l;
		this.a = a;
		this.b = b;
		this.illuminant = illuminant;
	}

	@Override
	public ColourRGB01 rgb01() {
		return xyz().rgb01();
	}

	@Override
	public ColourXYZ xyz(Illuminant illuminant) {
		double y = (l + 16.0) / 116.0;
		double x = a / 500.0 + y;
		double z = y - b / 200.0;

		final double x3 = Math.pow(x, 3);
		final double y3 = Math.pow(y, 3);
		final double z3 = Math.pow(z, 3);

		x = (x3 > DIV_6_29_CUBED) ? x3 : (x - DIV_16_116) / DIV_29_6_SQR_DIV_3;
		y = (y3 > DIV_6_29_CUBED) ? y3 : (y - DIV_16_116) / DIV_29_6_SQR_DIV_3;
		z = (z3 > DIV_6_29_CUBED) ? z3 : (z - DIV_16_116) / DIV_29_6_SQR_DIV_3;

		x *= this.illuminant.X();
		y *= this.illuminant.Y();
		z *= this.illuminant.Z();

		return new ColourXYZ(x, y, z, this.illuminant).xyz(illuminant);
	}

	@Override
	public ColourLAB lab(Illuminant illuminant) {
		if (this.illuminant.equals(illuminant)) {
			return this;
		}
		return xyz(illuminant).lab(illuminant);
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
