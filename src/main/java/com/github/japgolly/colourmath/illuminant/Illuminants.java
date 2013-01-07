package com.github.japgolly.colourmath.illuminant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * TODOC: com.github.japgolly.colourmath.illuminant.Illuminants
 * 
 * @since 07/01/2013
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Illuminants {

	/**
	 * CIE 1931 2° Standard Observer.
	 */
	public static interface CIE1931 {

		/** Incandescent / Tungsten */
		Illuminant A = new IlluminantImpl(100, 0.44757, 0.40745, 109.846606945637, 35.58228003436);

		/** {obsolete} Direct sunlight at noon */
		Illuminant B = new IlluminantImpl(100, 0.34842, 0.35161, 99.09274480248, 85.3132732288615);

		/** {obsolete} Average / North sky Daylight */
		Illuminant C = new IlluminantImpl(100, 0.31006, 0.31616, 98.0705971659919, 118.224949392713);

		/** Horizon Light. ICC profile PCS */
		Illuminant D50 = new IlluminantImpl(100, 0.34567, 0.3585, 96.42119944212, 82.5188284518828);

		/** Mid-morning / Mid-afternoon Daylight */
		Illuminant D55 = new IlluminantImpl(100, 0.33242, 0.34743, 95.6797052643698, 92.1480586017327);

		/** Noon Daylight: Television, sRGB color space */
		Illuminant D65 = new IlluminantImpl(100, 0.31271, 0.32902, 95.0428545377181, 108.890037079813);

		/** North sky Daylight */
		Illuminant D75 = new IlluminantImpl(100, 0.29902, 0.31485, 94.9722089884072, 122.639352072415);

		/** Equal energy */
		Illuminant E = new IlluminantImpl(100, 0.333333333333333, 0.333333333333333, 100, 100);

		/** Daylight Fluorescent */
		Illuminant F1 = new IlluminantImpl(100, 0.3131, 0.33727, 92.833634773327, 103.664719660806);

		/** Cool White Fluorescent */
		Illuminant F2 = new IlluminantImpl(100, 0.37208, 0.37529, 99.1446614618029, 67.3159423379253);

		/** White Fluorescent */
		Illuminant F3 = new IlluminantImpl(100, 0.4091, 0.3943, 103.753487192493, 49.860512300279);

		/** Warm White Fluorescent */
		Illuminant F4 = new IlluminantImpl(100, 0.44018, 0.40329, 109.14726375561, 38.8132609288601);

		/** Daylight Fluorescent */
		Illuminant F5 = new IlluminantImpl(100, 0.31379, 0.34531, 90.8719701138108, 98.7228866815326);

		/** Lite White Fluorescent */
		Illuminant F6 = new IlluminantImpl(100, 0.3779, 0.38835, 97.3091283635896, 60.1905497618128);

		/** D65 simulator, Daylight simulator */
		Illuminant F7 = new IlluminantImpl(100, 0.31292, 0.32933, 95.0171560440895, 108.629642000425);

		/** D50 simulator, Sylvania F40 Design 50 */
		Illuminant F8 = new IlluminantImpl(100, 0.34588, 0.35875, 96.412543554007, 82.3331010452962);

		/** Cool White Deluxe Fluorescent */
		Illuminant F9 = new IlluminantImpl(100, 0.37417, 0.37281, 100.364797081623, 67.8683511708377);

		/** Philips TL85, Ultralume 50 */
		Illuminant F10 = new IlluminantImpl(100, 0.34609, 0.35986, 96.1735119213027, 81.7123325737787);

		/** Philips TL84, Ultralume 40 */
		Illuminant F11 = new IlluminantImpl(100, 0.38052, 0.37713, 100.898894280487, 64.2616604353936);

		/** Philips TL83, Ultralume 30 */
		Illuminant F12 = new IlluminantImpl(100, 0.43695, 0.40441, 108.046289656537, 39.2275166291634);
	}

	/**
	 * CIE 1964 10° Standard Observer.
	 * <p>
	 * <em>A more modern but less-used alternative (to the CIE 1931 2° Standard Observer) is the CIE 1964 10° Standard
	 * Observer, which is derived from the work of Stiles and Burch, and Speranskaya. The 1964 Supplementary Standard
	 * Observer is recommended for more than about a 4° field of view.
	 * </em>
	 */
	public static interface CIE1964 {

		/** Incandescent / Tungsten */
		Illuminant A = new IlluminantImpl(100, 0.45117, 0.40594, 111.142040695669, 35.1997832191949);

		/** {obsolete} Direct sunlight at noon */
		Illuminant B = new IlluminantImpl(100, 0.3498, 0.3527, 99.1777714771761, 84.3493053586617);

		/** {obsolete} Average / North sky Daylight */
		Illuminant C = new IlluminantImpl(100, 0.31039, 0.31905, 97.2856918978217, 116.144804889516);

		/** Horizon Light. ICC profile PCS */
		Illuminant D50 = new IlluminantImpl(100, 0.34773, 0.35952, 96.7206275033378, 81.4280151312861);

		/** Mid-morning / Mid-afternoon Daylight */
		Illuminant D55 = new IlluminantImpl(100, 0.33411, 0.34877, 95.7966568225478, 90.9252515984746);

		/** Noon Daylight: Television, sRGB color space */
		Illuminant D65 = new IlluminantImpl(100, 0.31382, 0.331, 94.809667673716, 107.305135951662);

		/** North sky Daylight */
		Illuminant D75 = new IlluminantImpl(100, 0.29968, 0.3174, 94.4171392564587, 120.642722117202);

		/** Equal energy */
		Illuminant E = new IlluminantImpl(100, 0.333333333333333, 0.333333333333333, 100, 100);

		/** Daylight Fluorescent */
		Illuminant F1 = new IlluminantImpl(100, 0.31811, 0.33559, 94.7912631484848, 103.191394260854);

		/** Cool White Fluorescent */
		Illuminant F2 = new IlluminantImpl(100, 0.37925, 0.36733, 103.245038521221, 68.9897367489723);

		/** White Fluorescent */
		Illuminant F3 = new IlluminantImpl(100, 0.41761, 0.38324, 108.968270535435, 51.9648262185575);

		/** Warm White Fluorescent */
		Illuminant F4 = new IlluminantImpl(100, 0.4492, 0.39074, 114.961355376977, 40.9633004043609);

		/** Daylight Fluorescent */
		Illuminant F5 = new IlluminantImpl(100, 0.31975, 0.34246, 93.3685685919523, 98.6363370904631);

		/** Lite White Fluorescent */
		Illuminant F6 = new IlluminantImpl(100, 0.3866, 0.37847, 102.148122704574, 62.0736121753375);

		/** D65 simulator, Daylight simulator */
		Illuminant F7 = new IlluminantImpl(100, 0.31569, 0.3296, 95.7797330097088, 107.618325242718);

		/** D50 simulator, Sylvania F40 Design 50 */
		Illuminant F8 = new IlluminantImpl(100, 0.34902, 0.35939, 97.1145552185648, 81.1347004646762);

		/** Cool White Deluxe Fluorescent */
		Illuminant F9 = new IlluminantImpl(100, 0.37829, 0.37045, 102.116344985828, 67.8256174922391);

		/** Philips TL85, Ultralume 50 */
		Illuminant F10 = new IlluminantImpl(100, 0.3509, 0.35444, 99.0012413948764, 83.1339577925742);

		/** Philips TL84, Ultralume 40 */
		Illuminant F11 = new IlluminantImpl(100, 0.38541, 0.37123, 103.819734396466, 65.5550467365245);

		/** Philips TL83, Ultralume 30 */
		Illuminant F12 = new IlluminantImpl(100, 0.44256, 0.39717, 111.428355615983, 40.3529974570083);
	}

	public static final Illuminant DEFAULT = CIE1964.D65;
}
