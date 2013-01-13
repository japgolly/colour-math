package com.github.japgolly.colourmath;

import static com.github.japgolly.colourmath.MathFunc.invtan;
import static com.github.japgolly.colourmath.MathFunc.toRadians;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

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
public class ColourLab extends ColourAdapter {
	@Wither public final double L, a, b;
	public final Illuminant illuminant;

	ColourLab(double L, double a, double b, Illuminant illuminant) {
		// Conditions.assert100(L); // TODO L*a*b*, L* can be > 100 due to illuminant
		Conditions.assert128_127(a, b);
		this.L = L;
		this.a = a;
		this.b = b;
		this.illuminant = illuminant;
	}

	@Override
	public ColourRGB01 RGB01() {
		return XYZ().RGB01();
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		double Y = (L + 16.0) / 116.0;
		double X = a / 500.0 + Y;
		double Z = Y - b / 200.0;

		final double x3 = pow(X, 3);
		final double y3 = pow(Y, 3);
		final double z3 = pow(Z, 3);

		X = (x3 > _6_DIV_29_CUBED) ? x3 : (X - _16_DIV_116) / _29_DIV_6_SQR_DIV_3;
		Y = (y3 > _6_DIV_29_CUBED) ? y3 : (Y - _16_DIV_116) / _29_DIV_6_SQR_DIV_3;
		Z = (z3 > _6_DIV_29_CUBED) ? z3 : (Z - _16_DIV_116) / _29_DIV_6_SQR_DIV_3;

		X *= this.illuminant.X();
		Y *= this.illuminant.Y();
		Z *= this.illuminant.Z();

		return new ColourXYZ(X, Y, Z, this.illuminant).XYZ(illuminant);
	}

	@Override
	public ColourLab Lab(Illuminant illuminant) {
		if (this.illuminant.equals(illuminant)) {
			return this;
		}
		return XYZ(illuminant).Lab(illuminant);
	}

	@Override
	public double deltaE_94(Colour otherColour) {
		final ColourLab lab1 = this;
		final ColourLab lab2 = otherColour.Lab(illuminant);

		final double c1 = sqrt(lab1.a * lab1.a + lab1.b * lab1.b);
		final double c2 = sqrt(lab2.a * lab2.a + lab2.b * lab2.b);
		final double dc = c1 - c2;
		final double dl = lab1.L - lab2.L;
		final double da = lab1.a - lab2.a;
		final double db = lab1.b - lab2.b;
		final double dh = sqrt((da * da) + (db * db) - (dc * dc));
		final double first = dl;
		final double second = dc / (1.0 + 0.045 * c1);
		final double third = dh / (1.0 + 0.015 * c1);
		return sqrt(first * first + second * second + third * third);
	}

	@Override
	public double deltaE_2000(Colour otherColour) {
		final ColourLab col1 = this;
		final ColourLab col2 = otherColour.Lab(illuminant);

		// steps 2 - 7
		final double C_1 = sqrt(pow(col1.a, 2) + pow(col1.b, 2));
		final double C_2 = sqrt(pow(col2.a, 2) + pow(col2.b, 2));
		final double C_b = (C_1 + C_2) * 0.5;
		final double C_b7 = pow(C_b, 7);
		final double G = 0.5 * (1 - sqrt(C_b7 / (C_b7 + _25_POW_7)));
		final double a1 = (1 + G) * col1.a;
		final double a2 = (1 + G) * col2.a;
		final double C1 = sqrt(pow(a1, 2) + pow(col1.b, 2));
		final double C2 = sqrt(pow(a2, 2) + pow(col2.b, 2));
		final double h1 = (a1 == 0 && col1.b == 0) ? 0 : invtan(col1.b, a1);
		final double h2 = (a2 == 0 && col2.b == 0) ? 0 : invtan(col2.b, a2);

		// steps 8 - 11
		final double dL = col2.L - col1.L;
		final double dC = C2 - C1;
		// final double dh = deltaH(C1, C2, h1, h2);
		final double h2m1 = h2 - h1;
		final boolean C1C2_zero = (C1 == 0 || C2 == 0);
		final double dh = C1C2_zero ? 0 : //
				(abs(h2m1) <= 180) ? h2m1 : //
						(h2m1 > 180) ? h2m1 - 360 : //
								h2m1 + 360;
		final double dH = 2 * sqrt(C1 * C2) * sin(toRadians(dh * 0.5));

		// steps 12 - 22
		final double Lb = (col1.L + col2.L) * 0.5; // 12
		final double Cb = (C1 + C2) * 0.5; // 13
		final double h1m2 = h1 - h2; // 14
		final double h1p2 = h1 + h2;
		final double hb = C1C2_zero ? h1p2 : //
				(abs(h1m2) <= 180) ? 0.5 * h1p2 : //
						(h1p2 < 360) ? 0.5 * (h1p2 + 360) : //
								0.5 * (h1p2 - 360);
		final double T = // 15
				1 - 0.17 * cos(toRadians(hb - 30)) + 0.24 * cos(toRadians(2 * hb)) + 0.32 * cos(toRadians(3 * hb + 6))
						- 0.2 * cos(toRadians(4 * hb - 63));

		final double dTh = 30 * Math.exp(-pow((hb - 275) / 25, 2)); // 16
		final double Cb7 = pow(Cb, 7); // 17
		final double Rc = 2 * sqrt(Cb7 / (Cb7 + _25_POW_7));
		final double tmp1 = pow(Lb - 50, 2); // 18
		final double Sl = 1 + (0.015 * tmp1) / (sqrt(20 + tmp1));
		final double Sc = 1 + 0.045 * Cb;
		final double Sh = 1 + 0.015 * Cb * T;
		final double Rt = -sin(toRadians(2 * dTh)) * Rc;

		// TODO dE2000 k values hardcoded to 1.0
		final double kl = 1.0, kc = 1.0, kh = 1.0;

		final double xh = dH / kh / Sh;
		final double xc = dC / kc / Sc;
		final double xl = dL / kl / Sl;
		final double dE = sqrt(pow(xl, 2) + pow(xc, 2) + pow(xh, 2) + Rt * xc * xh);

		return dE;
	}
}
