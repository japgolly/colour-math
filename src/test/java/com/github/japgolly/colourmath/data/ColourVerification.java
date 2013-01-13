package com.github.japgolly.colourmath.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import com.github.japgolly.colourmath.Colour;
import com.github.japgolly.colourmath.ColourFactory;
import com.github.japgolly.colourmath.ColourHSL01;
import com.github.japgolly.colourmath.ColourHSL255;
import com.github.japgolly.colourmath.ColourLab;
import com.github.japgolly.colourmath.ColourLuv;
import com.github.japgolly.colourmath.ColourRGB01;
import com.github.japgolly.colourmath.ColourRGB255;
import com.github.japgolly.colourmath.ColourXYZ;
import com.github.japgolly.colourmath.illuminant.Illuminant;

/**
 * TODOC: com.github.japgolly.colourmath.ColourVerification
 * 
 * @since 13/01/2013
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class ColourVerification implements Colour {

	private final static ColourFactory CF = ColourFactory.getInstance();

	private final int ARGB, RGBA, RGB;
	ColourHSL01 HSL01;
	ColourHSL255 HSL255;
	ColourRGB01 RGB01;
	ColourRGB255 RGB255;

	ColourXYZ XYZ;
	ColourLab Lab;
	ColourLuv Luv;

	void setRGB(int r, int g, int b) {
		this.RGB255 = CF.RGB255(r, g, b);
		this.RGB01 = CF.RGB01(r / 255., g / 255., b / 255.);
	}

	void setHSL01(double h, double s, double l) {
		this.HSL01 = CF.HSL01(h, s, l);
		this.HSL255 = CF.HSL255((int) (h * 255 + 0.5), (int) (s * 255 + 0.5), (int) (l * 255 + 0.5));
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ColourLab Lab(Illuminant illuminant) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ColourLuv Luv(Illuminant illuminant) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double deltaE_94(Colour otherColour) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double deltaE_2000(Colour otherColour) {
		throw new UnsupportedOperationException();
	}

	public Double getSpecialCaseTolerance(String desc, Colour actual) {
		return null;
	}

}
