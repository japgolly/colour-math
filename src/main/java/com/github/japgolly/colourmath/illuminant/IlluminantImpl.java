package com.github.japgolly.colourmath.illuminant;

import javax.annotation.concurrent.Immutable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.IlluminantImpl
 * 
 * @since 07/01/2013
 */
@Immutable
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class IlluminantImpl implements Illuminant {

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
}
