package com.github.japgolly.colourmath;


/**
 * TODOC: com.github.japgolly.colourmath.ColourAdapter
 * 
 * @since 06/01/2013
 */
abstract class ColourAdapter implements Colour {

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
	public ColourXYZ xyz() {
		return rgb01().xyz();
	}

	@Override
	public ColourLAB lab() {
		return xyz().lab();
	}

	@Override
	public float deltaE_94(Colour otherColour) {
		return lab().deltaE_94(otherColour);
	}
}
