package com.github.japgolly.colourmath.illuminant;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.Illuminant
 * 
 * @since 07/01/2013
 */
public interface Illuminant {

	double Y();

	double x();

	double y();

	double X();

	double Z();

	/**
	 * <tt>u′,v′</tt> are chromaticity coordinates.
	 */
	double u_();

	/**
	 * <tt>u′,v′</tt> are chromaticity coordinates.
	 */
	double v_();
}
