package com.github.japgolly.colourmath;

/**
 * TODOC: com.github.japgolly.colourmath.Conditions
 * 
 * @since 06/01/2013
 */
class Conditions {

	public static void assert100(double v) {
		if (v < 0. || v > 100.) {
			throw err("Illegal value: %f. Valid range is [0,100].", v);
		}
	}

	public static void assert128_127(double a, double b) {
		if (a < -128. || a > 127. || b < -128. || b > 127.) {
			throw err("Illegal value(s): %f,%f. Valid range is [-128,127].", a, b);
		}
	}

	public static void assert01(float v) {
		if (v < 0f || v > 1f) {
			throw err("Illegal value: %f. Valid range is [0,1].", v);
		}
	}

	public static void assert01(float a, float b, float c) {
		if (a < 0f || a > 1f || b < 0f || b > 1f || c < 0f || c > 1f) {
			throw err("Illegal value(s) in %f,%f,%f. Valid range is [0,1].", a, b, c);
		}
	}

	public static void assert255(int v) {
		if (v < 0 || v > 255) {
			throw err("Illegal value: %f. Valid range is [0,255].", v);
		}
	}

	public static void assert255(int a, int b, int c) {
		if (a < 0 || a > 255 || b < 0 || b > 255 || c < 0 || c > 255) {
			throw err("Illegal value(s) in %f,%f,%f. Valid range is [0,255].", a, b, c);
		}
	}

	private static RuntimeException err(String fmt, Object... args) {
		throw new IllegalArgumentException(args.length == 0 ? fmt : String.format(fmt, args));
	}
}
