package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;

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

	private static final double[][] BRADFORD_M = { { 0.8951, -0.7502, 0.0389 }, { 0.2664, 1.7135, -0.0685 },
			{ -0.1614, 0.0367, 1.0296 } };
	private static final double[][] BRADFORD_MI = { { 0.9869929, 0.4323053, -0.0085287 },
			{ -0.1470543, 0.5183603, 0.0400428 }, { 0.1599627, 0.0492912, 0.9684867 } };

	@Override
	public ColourXYZ xyz(Illuminant illuminant) {
		if (this.illuminant.equals(illuminant)) {
			return this;
		}

		// Create cone response domains (source + dest)
		final double[] crdS =
				MatrixMath.multiply(
						new double[][] { { this.illuminant.X(), this.illuminant.Y(), this.illuminant.Z() } },
						BRADFORD_M)[0];
		final double[] crdD =
				MatrixMath.multiply(new double[][] { { illuminant.X(), illuminant.Y(), illuminant.Z() } }, BRADFORD_M)[0];

		// Generate XYZ transformation matrix
		final double[][] centre =
				new double[][] { { crdD[0] / crdS[0], 0, 0 }, { 0, crdD[1] / crdS[1], 0 }, { 0, 0, crdD[2] / crdS[2] } };
		final double[][] m = MatrixMath.multiply(MatrixMath.multiply(BRADFORD_M, centre), BRADFORD_MI);

		// Transform
		double xyz[] = MatrixMath.multiply(new double[][] { { x, y, z } }, m)[0];
		return new ColourXYZ(xyz[0], xyz[1], xyz[2], illuminant);
	}

	private static final double[][] XYZ_TO_sRGB = { { 3.2404542, -1.5371385, -0.4985314 },
			{ -0.9692660, 1.8760108, 0.0415560 }, { 0.0556434, -0.2040259, 1.0572252 } };

	@Override
	public ColourRGB01 rgb01() {
		if (!illuminant.equals(Illuminants.CIE1931.D65)) {
			return xyz(Illuminants.CIE1931.D65).rgb01();
		}

		double x = this.x / 100.0;
		double y = this.y / 100.0;
		double z = this.z / 100.0;

		double r = MatrixMath.multiplyRow(XYZ_TO_sRGB[0], x, y, z);
		double g = MatrixMath.multiplyRow(XYZ_TO_sRGB[1], x, y, z);
		double b = MatrixMath.multiplyRow(XYZ_TO_sRGB[2], x, y, z);

		r = (r > 0.0031308) ? 1.055 * Math.pow(r, 1.0 / 2.4) - 0.055 : 12.92 * r;
		g = (g > 0.0031308) ? 1.055 * Math.pow(g, 1.0 / 2.4) - 0.055 : 12.92 * g;
		b = (b > 0.0031308) ? 1.055 * Math.pow(b, 1.0 / 2.4) - 0.055 : 12.92 * b;

		float fr = Math.max(0f, Math.min(1.0f, (float) r));
		float fg = Math.max(0f, Math.min(1.0f, (float) g));
		float fb = Math.max(0f, Math.min(1.0f, (float) b));

		return new ColourRGB01(fr, fg, fb);
	}

	@Override
	public ColourLAB lab(Illuminant illuminant) {
		// TODO test this stuff,
		if (!this.illuminant.equals(illuminant)) {
			return xyz(illuminant).lab(illuminant);
		}

		double _x = x / illuminant.X();
		double _y = y / illuminant.Y();
		double _z = z / illuminant.Z();

		_x = (_x > ColourLAB.V_00886) ? Math.pow(_x, 1.0 / 3.0) : ColourLAB.V7_787 * _x + ColourLAB.V_16_116;
		_y = (_y > ColourLAB.V_00886) ? Math.pow(_y, 1.0 / 3.0) : ColourLAB.V7_787 * _y + ColourLAB.V_16_116;
		_z = (_z > ColourLAB.V_00886) ? Math.pow(_z, 1.0 / 3.0) : ColourLAB.V7_787 * _z + ColourLAB.V_16_116;

		double l = 116.0 * _y - 16.0;
		double a = 500.0 * (_x - _y);
		double b = 200.0 * (_y - _z);

		return new ColourLAB(l, a, b, illuminant);
	}
}
