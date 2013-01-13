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

	int ARGB();

	int RGBA();

	int RGB();

	ColourRGB255 RGB255();

	ColourRGB01 RGB01();

	ColourHSL01 HSL01();

	ColourHSL255 HSL255();

	ColourXYZ XYZ();

	ColourXYZ XYZ(Illuminant illuminant);

	ColourLab Lab();

	ColourLab Lab(Illuminant illuminant);

	double deltaE_94(Colour otherColour);

	double deltaE_2000(Colour otherColour);
}
