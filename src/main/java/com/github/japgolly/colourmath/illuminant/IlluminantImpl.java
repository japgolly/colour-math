package com.github.japgolly.colourmath.illuminant;

import javax.annotation.concurrent.Immutable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.IlluminantImpl
 * 
 * @since 07/01/2013
 */
@Immutable
@AllArgsConstructor
@EqualsAndHashCode(exclude = "name")
public class IlluminantImpl implements Illuminant {

	private final String name;
	private final double Y, x, y, X, Z;

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
	public String toString() {
		return name;
	}
}
