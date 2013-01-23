package com.github.japgolly.colourmath;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;
import com.github.japgolly.colourmath.illuminant.Surrounding;

/**
 * TODOC: com.github.japgolly.colourmath.ColourAdapter
 * 
 * @since 06/01/2013
 */
abstract class ColourAdapter implements Colour, Consts {

	@Override
	public int ARGB() {
		return RGB255().ARGB();
	}

	@Override
	public int RGBA() {
		return RGB255().RGBA();
	}

	@Override
	public int RGB() {
		return RGB255().RGB();
	}

	@Override
	public ColourRGB255 RGB255() {
		return RGB01().RGB255();
	}

	// @Override
	// public ColourRGB01 rgb01() {
	// }

	@Override
	public ColourHSL01 HSL01() {
		return RGB01().HSL01();
	}

	@Override
	public ColourHSL255 HSL255() {
		return HSL01().HSL255();
	}

	@Override
	public final ColourXYZ XYZ() {
		return XYZ(Illuminants.getDefault());
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		return RGB01().XYZ(illuminant);
	}

	@Override
	public final ColourLab Lab() {
		return Lab(Illuminants.getDefault());
	}

	@Override
	public ColourLab Lab(Illuminant illuminant) {
		return XYZ(illuminant).Lab(illuminant);
	}

	@Override
	public final ColourLuv Luv() {
		return Luv(Illuminants.getDefault());
	}

	@Override
	public ColourLuv Luv(Illuminant illuminant) {
		return XYZ(illuminant).Luv(illuminant);
	}

	@Override
	public final ColourCIECAM02 CIECAM02(Surrounding surrounding) {
		return CIECAM02(Illuminants.getDefault(), surrounding);
	}

	@Override
	public ColourCIECAM02 CIECAM02(Illuminant illuminant, Surrounding surrounding) {
		return XYZ(illuminant).CIECAM02(illuminant, surrounding);
	}

	@Override
	public double deltaE_94(Colour otherColour) {
		return Lab().deltaE_94(otherColour);
	}

	@Override
	public double deltaE_2000(Colour otherColour) {
		return Lab().deltaE_2000(otherColour);
	}
}
