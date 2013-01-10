package com.github.japgolly.colourmath;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;

/**
 * TODOC: com.github.japgolly.colourmath.ColourAdapter
 * 
 * @since 06/01/2013
 */
abstract class ColourAdapter implements Colour, Consts {

	@Override
	public int argb() {
		return rgb255().argb();
	}

	@Override
	public int rgba() {
		return rgb255().rgba();
	}

	@Override
	public int rgb() {
		return rgb255().rgb();
	}

	@Override
	public ColourRGB255 rgb255() {
		return rgb01().rgb255();
	}

	// @Override
	// public ColourRGB01 rgb01() {
	// }

	@Override
	public ColourHSL01 hsl01() {
		return rgb01().hsl01();
	}

	@Override
	public ColourHSL255 hsl255() {
		return hsl01().hsl255();
	}

	@Override
	public final ColourXYZ xyz() {
		return xyz(Illuminants.getDefault());
	}

	@Override
	public ColourXYZ xyz(Illuminant illuminant) {
		return rgb01().xyz(illuminant);
	}

	@Override
	public final ColourLAB lab() {
		return lab(Illuminants.getDefault());
	}

	@Override
	public ColourLAB lab(Illuminant illuminant) {
		return xyz(illuminant).lab(illuminant);
	}

	@Override
	public double deltaE_94(Colour otherColour) {
		return lab().deltaE_94(otherColour);
	}

	@Override
	public double deltaE_2000(Colour otherColour) {
		return lab().deltaE_2000(otherColour);
	}
}
