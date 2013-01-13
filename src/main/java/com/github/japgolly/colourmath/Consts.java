package com.github.japgolly.colourmath;

import static java.lang.Math.pow;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants.CIE1931;

/**
 * TODOC: com.github.japgolly.colourmath.Consts
 * 
 * @since 07/01/2013
 */
interface Consts {

	Illuminant sRGB_ILLUMINANT = CIE1931.D65_LESS_PRECISE;

	double[][] sRGB_TO_XYZ = { { 0.4124564, 0.3575761, 0.1804375 }, { 0.2126729, 0.7151522, 0.0721750 },
			{ 0.0193339, 0.1191920, 0.9503041 } };

	double[][] XYZ_TO_sRGB = { { 3.2404542, -1.5371385, -0.4985314 }, { -0.9692660, 1.8760108, 0.0415560 },
			{ 0.0556434, -0.2040259, 1.0572252 } };

	double[][] BRADFORD_M = { { 0.8951, -0.7502, 0.0389 }, { 0.2664, 1.7135, -0.0685 }, { -0.1614, 0.0367, 1.0296 } };

	double[][] BRADFORD_MI = { { 0.9869929, 0.4323053, -0.0085287 }, { -0.1470543, 0.5183603, 0.0400428 },
			{ 0.1599627, 0.0492912, 0.9684867 } };

	double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679;

	double TAU = 2. * PI;

	double $3_DIV_29_CUBED = pow(3. / 29., 3.);

	double $6_DIV_29_CUBED = pow(6. / 29., 3.); // 0.008856451679035631

	double $16_DIV_116 = 16. / 116.;

	double $25_POW_7 = pow(25., 7.);

	double $29_DIV_3_CUBED = pow(29. / 3., 3.);

	double $29_DIV_6_SQR_DIV_3 = pow(29. / 6., 2.) / 3.; // 7.787037037037035

	double $180_DIV_PI = 180. / PI;
}
