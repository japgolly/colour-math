package com.github.japgolly.colourmath.illuminant;

import static java.lang.Math.pow;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.IlluminantGen
 * 
 * @since 13/01/2013
 */
public class IlluminantGen {

	public static void main(String[] args) {
		gen("D65_LESS_PRECISE", "D65 2°", "Less-precise version of D65 often found around the internet.", 6504, 100.,
				0.31271, 0.32902, 95.047, 108.883);

		gen("A", "A 2°", "Incandescent / Tungsten", 2856, 0.44757, 0.40745);
		gen("B", "B 2°", "{obsolete} Direct sunlight at noon", 4874, 0.34842, 0.35161);
		gen("C", "C 2°", "{obsolete} Average / North sky Daylight", 6774, 0.31006, 0.31616);
		genByTemp("D50", "D50 2°", "Horizon Light. ICC profile PCS", 5003);
		genByTemp("D55", "D55 2°", "Mid-morning / Mid-afternoon Daylight", 5503);
		genByTemp("D65", "D65 2°", "Noon Daylight: Television, sRGB color space", 6504);
		genByTemp("D75", "D75 2°", "North sky Daylight", 7504);
		gen("E", "E 2°", "Equal energy", 5454, 1. / 3., 1. / 3.);
		gen("F1", "F1 2°", "Daylight Fluorescent", 6430, 0.3131, 0.33727);
		gen("F2", "F2 2°", "Cool White Fluorescent", 4230, 0.37208, 0.37529);
		gen("F3", "F3 2°", "White Fluorescent", 3450, 0.4091, 0.3943);
		gen("F4", "F4 2°", "Warm White Fluorescent", 2940, 0.44018, 0.40329);
		gen("F5", "F5 2°", "Daylight Fluorescent", 6350, 0.31379, 0.34531);
		gen("F6", "F6 2°", "Lite White Fluorescent", 4150, 0.3779, 0.38835);
		gen("F7", "F7 2°", "D65 simulator, Daylight simulator", 6500, 0.31292, 0.32933);
		gen("F8", "F8 2°", "D50 simulator, Sylvania F40 Design 50", 5000, 0.34588, 0.35875);
		gen("F9", "F9 2°", "Cool White Deluxe Fluorescent", 4150, 0.37417, 0.37281);
		gen("F10", "F10 2°", "Philips TL85, Ultralume 50", 5000, 0.34609, 0.35986);
		gen("F11", "F11 2°", "Philips TL84, Ultralume 40", 4000, 0.38052, 0.37713);
		gen("F12", "F12 2°", "Philips TL83, Ultralume 30", 3000, 0.43695, 0.40441);

		System.out.println("----------------------------------------------------------------------------------------");

		gen("A", "A 10°", "Incandescent / Tungsten", 2856, 0.45117, 0.40594);
		gen("B", "B 10°", "{obsolete} Direct sunlight at noon", 4874, 0.3498, 0.3527);
		gen("C", "C 10°", "{obsolete} Average / North sky Daylight", 6774, 0.31039, 0.31905);
		gen("D50", "D50 10°", "Horizon Light. ICC profile PCS", 5003, 0.34773, 0.35952);
		gen("D55", "D55 10°", "Mid-morning / Mid-afternoon Daylight", 5503, 0.33411, 0.34877);
		gen("D65", "D65 10°", "Noon Daylight: Television, sRGB color space", 6504, 0.31382, 0.331);
		gen("D75", "D75 10°", "North sky Daylight", 7504, 0.29968, 0.3174);
		gen("E", "E 10°", "Equal energy", 5454, 1. / 3., 1. / 3.);
		gen("F1", "F1 10°", "Daylight Fluorescent", 6430, 0.31811, 0.33559);
		gen("F2", "F2 10°", "Cool White Fluorescent", 4230, 0.37925, 0.36733);
		gen("F3", "F3 10°", "White Fluorescent", 3450, 0.41761, 0.38324);
		gen("F4", "F4 10°", "Warm White Fluorescent", 2940, 0.4492, 0.39074);
		gen("F5", "F5 10°", "Daylight Fluorescent", 6350, 0.31975, 0.34246);
		gen("F6", "F6 10°", "Lite White Fluorescent", 4150, 0.3866, 0.37847);
		gen("F7", "F7 10°", "D65 simulator, Daylight simulator", 6500, 0.31569, 0.3296);
		gen("F8", "F8 10°", "D50 simulator, Sylvania F40 Design 50", 5000, 0.34902, 0.35939);
		gen("F9", "F9 10°", "Cool White Deluxe Fluorescent", 4150, 0.37829, 0.37045);
		gen("F10", "F10 10°", "Philips TL85, Ultralume 50", 5000, 0.3509, 0.35444);
		gen("F11", "F11 10°", "Philips TL84, Ultralume 40", 4000, 0.38541, 0.37123);
		gen("F12", "F12 10°", "Philips TL83, Ultralume 30", 3000, 0.44256, 0.39717);
	}

	private static void genByTemp(String name, String text, String desc, final double temp) {

		final double x;
		if (temp < 4000) {
			throw new IllegalArgumentException("Colour temperature must be above 4000K.");
		} else if (temp <= 7000) {
			x = 0.244063 //
					+ 0.09911 * pow(10, 3) / temp //
					+ 2.9678 * pow(10, 6) / pow(temp, 2) //
					- 4.607 * pow(10, 9) / pow(temp, 3);
		} else if (temp <= 25000) {
			x = 0.23704 //
					+ 0.24748 * pow(10, 3) / temp //
					+ 1.9018 * pow(10, 6) / pow(temp, 2) //
					- 2.0064 * pow(10, 9) / pow(temp, 3);
		} else {
			throw new IllegalArgumentException("Colour temperature must be below 25000K.");
		}

		final double y = -3.000 * pow(x, 2) + 2.870 * x - 0.275;

		gen(name, text, desc, (int) temp, x, y);
	}

	private static void gen(String name, String text, String desc, final int temp, final double x, final double y) {

		final double Y = 100;
		final double X = x * Y / y;
		final double Z = (1 - x - y) * (Y / y);

		gen(name, text, desc, temp, Y, x, y, X, Z);
	}

	private static void gen(String name, String text, String desc, final int temp, final double Y, final double x,
			final double y, final double X, final double Z) {

		final double uv_denom = X + 15. * Y + 3. * Z;
		final double u_ = 4. * X / uv_denom;
		final double v_ = 9. * Y / uv_denom;

		// temp, Y x y X Z u v
		String str =
				String.format(
						"/** %s */ Illuminant %s = new IlluminantImpl(\"%s\", %d, %.20f, %.20f, %.20f, %.20f, %.20f, %.20f, %.20f);",
						desc, name, text, temp, Y, x, y, X, Z, u_, v_) //
						.replaceAll("0+(?=[,)])", "");
		System.out.println(str);
	}
}
