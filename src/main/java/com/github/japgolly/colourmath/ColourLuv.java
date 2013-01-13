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
		Conditions.assert100(L);
		Conditions.assert_pm_100(u, v);
		this.L = L;
		this.u = u;
		this.v = v;
		this.illuminant = illuminant;
	}

	@Override
	public ColourRGB01 RGB01() {
		return XYZ().RGB01();
	}

	@Override
	public ColourXYZ XYZ(Illuminant i) {
		final double L13 = L * 13.;
		final double u_ = u / L13 + illuminant.u_();
		final double v_ = v / L13 + illuminant.v_();

		final double Y = (L <= 8.) ? illuminant.Y() * L * _3_DIV_29_CUBED : illuminant.Y() * pow((L + 16.) / 116., 3.);
		final double v_4 = v_ * 4.;
		final double X = Y * 9. * u_ / v_4;
		final double Z = Y * (12. - 3. * u_ - 20 * v_) / v_4;

		return new ColourXYZ(X, Y, Z, illuminant).XYZ(i);
	}
}