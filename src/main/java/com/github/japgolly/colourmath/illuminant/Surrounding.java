package com.github.japgolly.colourmath.illuminant;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.Surrounding
 * 
 * @since 23/01/2013
 */
@Immutable
@EqualsAndHashCode
@ToString
public class Surrounding {
	public final double SR;
	public final double F, c, Nc;

	public Surrounding(double SR) {
		this.SR = SR;
		if (SR == 0) { // Dark
			F = Nc = 0.8;
			c = 0.525;
		} else if (SR <= 0.2) { // Dim
			F = 0.9;
			c = 0.59;
			Nc = 0.95;
		} else { // Average
			F = Nc = 1.0;
			c = 0.69;
		}
	}

	/**
	 * Using a projector in a dark room
	 */
	public static final Surrounding DARK = new Surrounding(0);

	/**
	 * Viewing television
	 */
	public static final Surrounding DIM = new Surrounding(0.15);

	/**
	 * Viewing surface colors
	 */
	public static final Surrounding AVERAGE = new Surrounding(0.3);
}
