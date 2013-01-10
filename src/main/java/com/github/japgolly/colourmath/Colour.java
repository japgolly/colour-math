package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.japgolly.colourmath.illuminant.Illuminant;

/**
 * TODOC: com.github.japgolly.colourmath.Colour
 * 
 * @since 05/01/2013
 */
@Immutable
@ThreadSafe
public interface Colour {

	int argb();

	int rgba();

	int rgb();

	ColourRGB255 rgb255();

	ColourRGB01 rgb01();

	ColourHSL01 hsl01();

	ColourHSL255 hsl255();

	ColourXYZ xyz();

	ColourXYZ xyz(Illuminant illuminant);

	ColourLAB lab();

	ColourLAB lab(Illuminant illuminant);

	double deltaE_94(Colour otherColour);

	double deltaE_2000(Colour otherColour);
}
