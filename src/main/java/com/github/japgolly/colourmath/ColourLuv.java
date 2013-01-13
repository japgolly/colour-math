package com.github.japgolly.colourmath;

import static java.lang.Math.pow;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;

/**
 * TODOC: com.github.japgolly.colourmath.ColourLuv
 * 
 * @since 13/01/2013
 */
@Immutable
@ToString
@EqualsAndHashCode(callSuper = false)
public class ColourLuv extends ColourAdapter {
	@Wither public final double L, u, v;
	public final Illuminant illuminant;

	ColourLuv(double L, double u, double v, Illuminant illuminant) {
		// Conditions.assert100(L); // TODO L*u*v*, L* can be > 100 due to illuminant
		// Conditions.assert_pm_100(u, v); // TODO L*u*v* ranges > 100.
		this.L = L;
		this.u = u;
		this.v = v;
		this.illuminant = illuminant;
	}

	public double[] toArray(double[] array) {
		array[0] = L;
		array[1] = u;
		array[2] = v;
		return array;
	}

	public float[] toArray(float[] array) {
		array[0] = (float) L;
		array[1] = (float) u;
		array[2] = (float) v;
		return array;
	}

	@Override
	public ColourRGB01 RGB01() {
		return XYZ().RGB01();
	}

	@Override
	public ColourXYZ XYZ(Illuminant i) {
		final double u_, v_;
		if (L == 0) {
			u_ = illuminant.u_();
			v_ = illuminant.v_();
		} else {
			final double L13 = L * 13.;
			u_ = u / L13 + illuminant.u_();
			v_ = v / L13 + illuminant.v_();
		}

		final double Y = (L <= 8.) ? illuminant.Y() * L * $3_DIV_29_CUBED : illuminant.Y() * pow((L + 16.) / 116., 3.);
		final double X, Z;
		if (v_ == 0) {
			X = Z = 0;
		} else {
			final double v_4 = v_ * 4.;
			X = Y * 9. * u_ / v_4;
			Z = Y * (12. - 3. * u_ - 20 * v_) / v_4;
		}

		return new ColourXYZ(X, Y, Z, illuminant).XYZ(i);
	}
}
