package com.github.japgolly.colourmath.illuminant;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.IlluminantImpl
 * 
 * @since 07/01/2013
 */
@Immutable
@EqualsAndHashCode(exclude = "name")
public class IlluminantImpl implements Illuminant {

	private final String name;
	private final double Y, x, y, X, Z;
	private final double u_, v_;

	public IlluminantImpl(String name, double Y, double x, double y, double X, double Z) {
		this.name = name;
		this.Y = Y;
		this.x = x;
		this.y = y;
		this.X = X;
		this.Z = Z;

		// TODO pre-calc u_/v_ in Illuminants
		// http://en.wikipedia.org/wiki/CIELUV
		final double uv_denom = X + 15. * Y + 3. * Z;
		this.u_ = 4. * X / uv_denom;
		this.v_ = 9. * Y / uv_denom;
	}

	@Override
	public double Y() {
		return Y;
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public double X() {
		return X;
	}

	@Override
	public double Z() {
		return Z;
	}

	@Override
	public double u_() {
		return u_;
	}

	@Override
	public double v_() {
		return v_;
	}

	@Override
	public String toString() {
		return name;
	}
}
