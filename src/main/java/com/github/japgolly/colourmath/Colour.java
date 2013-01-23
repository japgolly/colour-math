package com.github.japgolly.colourmath;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Surrounding;

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

	ColourLuv Luv();

	ColourLuv Luv(Illuminant illuminant);

	ColourCIECAM02 CIECAM02(Surrounding surrounding);

	ColourCIECAM02 CIECAM02(Illuminant illuminant, Surrounding surrounding);

	double deltaE_94(Colour otherColour);

	double deltaE_2000(Colour otherColour);
}
