package com.github.japgolly.colourmath;

import static java.lang.Math.pow;

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
public class ColourXYZ extends ColourAdapter {

	@Wither public final double X, Y, Z;
	public final Illuminant illuminant;

	ColourXYZ(double X, double Y, double Z, Illuminant illuminant) {
		// TODO XYZ range?
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.illuminant = illuminant;
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		if (this.illuminant.equals(illuminant)) {
			return this;
		}

		// Create cone response domains (source + dest)
		final double[] crdS =
				MathFunc.multiply(new double[][] { { this.illuminant.X(), this.illuminant.Y(), this.illuminant.Z() } },
						BRADFORD_M)[0];
		final double[] crdD =
				MathFunc.multiply(new double[][] { { illuminant.X(), illuminant.Y(), illuminant.Z() } }, BRADFORD_M)[0];

		// Generate XYZ transformation matrix
		final double[][] centre =
				new double[][] { { crdD[0] / crdS[0], 0, 0 }, { 0, crdD[1] / crdS[1], 0 }, { 0, 0, crdD[2] / crdS[2] } };
		final double[][] m = MathFunc.multiply(MathFunc.multiply(BRADFORD_M, centre), BRADFORD_MI);

		// Transform
		double xyz[] = MathFunc.multiply(new double[][] { { X, Y, Z } }, m)[0];
		return new ColourXYZ(xyz[0], xyz[1], xyz[2], illuminant);
	}

	@Override
	public ColourRGB01 RGB01() {
		if (!illuminant.equals(sRGB_ILLUMINANT)) {
			return XYZ(sRGB_ILLUMINANT).RGB01();
		}

		double x = this.X / 100.0;
		double y = this.Y / 100.0;
		double z = this.Z / 100.0;

		double r = MathFunc.multiplyRow(XYZ_TO_sRGB[0], x, y, z);
		double g = MathFunc.multiplyRow(XYZ_TO_sRGB[1], x, y, z);
		double b = MathFunc.multiplyRow(XYZ_TO_sRGB[2], x, y, z);

		r = (r > 0.0031308) ? 1.055 * pow(r, 1.0 / 2.4) - 0.055 : 12.92 * r;
		g = (g > 0.0031308) ? 1.055 * pow(g, 1.0 / 2.4) - 0.055 : 12.92 * g;
		b = (b > 0.0031308) ? 1.055 * pow(b, 1.0 / 2.4) - 0.055 : 12.92 * b;

		double fr = Math.max(0, Math.min(1, r));
		double fg = Math.max(0, Math.min(1, g));
		double fb = Math.max(0, Math.min(1, b));

		return new ColourRGB01(fr, fg, fb);
	}

	@Override
	public ColourLab Lab(Illuminant illuminant) {
		if (!this.illuminant.equals(illuminant)) {
			return XYZ(illuminant).Lab(illuminant);
		}

		double _x = X / illuminant.X();
		double _y = Y / illuminant.Y();
		double _z = Z / illuminant.Z();

		_x = (_x > _6_DIV_29_CUBED) ? pow(_x, 1.0 / 3.0) : _29_DIV_6_SQR_DIV_3 * _x + _16_DIV_116;
		_y = (_y > _6_DIV_29_CUBED) ? pow(_y, 1.0 / 3.0) : _29_DIV_6_SQR_DIV_3 * _y + _16_DIV_116;
		_z = (_z > _6_DIV_29_CUBED) ? pow(_z, 1.0 / 3.0) : _29_DIV_6_SQR_DIV_3 * _z + _16_DIV_116;

		double L = 116.0 * _y - 16.0;
		double a = 500.0 * (_x - _y);
		double b = 200.0 * (_y - _z);

		return new ColourLab(L, a, b, illuminant);
	}

	@Override
	public ColourLuv Luv(Illuminant illuminant) {
		if (!this.illuminant.equals(illuminant)) {
			return XYZ(illuminant).Luv(illuminant);
		}

		final double y_div_yn = Y / illuminant.Y();
		final double L =
				(y_div_yn <= _6_DIV_29_CUBED) ? _29_DIV_3_CUBED * y_div_yn : 116. * pow(y_div_yn, 1. / 3.) - 16;

		final double uv_denom = X + 15. * Y + 3. * Z;
		final double u_, v_;
		if (uv_denom == 0) {
			u_ = v_ = 0;
		} else {
			u_ = 4. * X / uv_denom;
			v_ = 9. * Y / uv_denom;
		}

		final double L13 = L * 13.;
		final double u = L13 * (u_ - illuminant.u_());
		final double v = L13 * (v_ - illuminant.v_());

		return new ColourLuv(L, u, v, illuminant);
	}
}
