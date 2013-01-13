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
	private final int temperature;
	private final double Y, x, y, X, Z;
	private final double u_, v_;

	public IlluminantImpl(String name, int temperature, double Y, double x, double y, double X, double Z) {
		this(name, temperature, Y, x, y, X, Z, 4. * X / (X + 15. * Y + 3. * Z), 9. * Y / (X + 15. * Y + 3. * Z));
	}

	public IlluminantImpl(String name, int temperature, double Y, double x, double y, double X, double Z, double u_,
			double v_) {
		this.name = name;
		this.temperature = temperature;
		this.Y = Y;
		this.x = x;
		this.y = y;
		this.X = X;
		this.Z = Z;
		this.u_ = u_;
		this.v_ = v_;
	}

	@Override
	public int temperature() {
		return temperature;
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
