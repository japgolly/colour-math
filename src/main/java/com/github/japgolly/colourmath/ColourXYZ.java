package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants.CIE1931;

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
	public final Illuminant illuminant;

	ColourXYZ(double x, double y, double z, Illuminant illuminant) {
		// TODO XYZ range?
		this.x = x;
		this.y = y;
		this.z = z;
		this.illuminant = illuminant;
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		if (this.illuminant.equals(illuminant)) {
			return this;
		}

		// Create cone response domains (source + dest)
		final double[] crdS =
				MathFunc.multiply(
						new double[][] { { this.illuminant.X(), this.illuminant.Y(), this.illuminant.Z() } },
						BRADFORD_M)[0];
		final double[] crdD =
				MathFunc.multiply(new double[][] { { illuminant.X(), illuminant.Y(), illuminant.Z() } }, BRADFORD_M)[0];

		// Generate XYZ transformation matrix
		final double[][] centre =
				new double[][] { { crdD[0] / crdS[0], 0, 0 }, { 0, crdD[1] / crdS[1], 0 }, { 0, 0, crdD[2] / crdS[2] } };
		final double[][] m = MathFunc.multiply(MathFunc.multiply(BRADFORD_M, centre), BRADFORD_MI);

		// Transform
		double xyz[] = MathFunc.multiply(new double[][] { { x, y, z } }, m)[0];
		return new ColourXYZ(xyz[0], xyz[1], xyz[2], illuminant);
	}

	@Override
	public ColourRGB01 RGB01() {
		if (!illuminant.equals(CIE1931.D65)) {
			return XYZ(CIE1931.D65).RGB01();
		}

		double x = this.x / 100.0;
		double y = this.y / 100.0;
		double z = this.z / 100.0;

		double r = MathFunc.multiplyRow(XYZ_TO_sRGB[0], x, y, z);
		double g = MathFunc.multiplyRow(XYZ_TO_sRGB[1], x, y, z);
		double b = MathFunc.multiplyRow(XYZ_TO_sRGB[2], x, y, z);

		r = (r > 0.0031308) ? 1.055 * Math.pow(r, 1.0 / 2.4) - 0.055 : 12.92 * r;
		g = (g > 0.0031308) ? 1.055 * Math.pow(g, 1.0 / 2.4) - 0.055 : 12.92 * g;
		b = (b > 0.0031308) ? 1.055 * Math.pow(b, 1.0 / 2.4) - 0.055 : 12.92 * b;

		float fr = Math.max(0f, Math.min(1.0f, (float) r));
		float fg = Math.max(0f, Math.min(1.0f, (float) g));
		float fb = Math.max(0f, Math.min(1.0f, (float) b));

		return new ColourRGB01(fr, fg, fb);
	}

	@Override
	public ColourLab Lab(Illuminant illuminant) {
		if (!this.illuminant.equals(illuminant)) {
			return XYZ(illuminant).Lab(illuminant);
		}

		double _x = x / illuminant.X();
		double _y = y / illuminant.Y();
		double _z = z / illuminant.Z();

		_x = (_x > DIV_6_29_CUBED) ? Math.pow(_x, 1.0 / 3.0) : DIV_29_6_SQR_DIV_3 * _x + DIV_16_116;
		_y = (_y > DIV_6_29_CUBED) ? Math.pow(_y, 1.0 / 3.0) : DIV_29_6_SQR_DIV_3 * _y + DIV_16_116;
		_z = (_z > DIV_6_29_CUBED) ? Math.pow(_z, 1.0 / 3.0) : DIV_29_6_SQR_DIV_3 * _z + DIV_16_116;

		double l = 116.0 * _y - 16.0;
		double a = 500.0 * (_x - _y);
		double b = 200.0 * (_y - _z);

		return new ColourLab(l, a, b, illuminant);
	}
}
