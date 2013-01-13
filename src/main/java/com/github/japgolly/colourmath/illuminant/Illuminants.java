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

	private static final ThreadLocal<Illuminant> DEFAULTS = new ThreadLocal<Illuminant>() {
		@Override
		protected Illuminant initialValue() {
			return CIE1964.D65;
		};
	};

	public static Illuminant getDefault() {
		return DEFAULTS.get();
	}

	public static Illuminant setDefault(Illuminant illuminant) {
		DEFAULTS.set(illuminant);
		return illuminant;
	}

	/**
	 * CIE 1931 2° Standard Observer.
	 */
	public static interface CIE1931 {
		// @formatter:off
		/** Incandescent / Tungsten */ Illuminant A = new IlluminantImpl("A 2°", 2856, 100., 0.44757, 0.40745, 109.84660694563752, 35.582280034360046, 0.25596417633888363, 0.5242942069639962);
		/** {obsolete} Direct sunlight at noon */ Illuminant B = new IlluminantImpl("B 2°", 4874, 100., 0.34842, 0.35161, 99.09274480248003, 85.31327322886155, 0.21367332671008576, 0.48516668506457666);
		/** {obsolete} Average / North sky Daylight */ Illuminant C = new IlluminantImpl("C 2°", 6774, 100., 0.31006, 0.31616, 98.0705971659919, 118.22494939271256, 0.20088762188603454, 0.46088956558359523);
		/** Horizon Light. ICC profile PCS */ Illuminant D50 = new IlluminantImpl("D50 2°", 5003, 100., 0.3456530489086881, 0.35859615970831904, 96.39061644994781, 82.47461200464488, 0.2091113155581168, 0.48811852992980576);
		/** Mid-morning / Mid-afternoon Daylight */ Illuminant D55 = new IlluminantImpl("D55 2°", 5503, 100., 0.3324301305532206, 0.34754509958884927, 95.65093305774994, 92.081508338493, 0.20439374997192403, 0.48079608084865166);
		/** Noon Daylight: Television, sRGB color space */ Illuminant D65 = new IlluminantImpl("D65 2°", 6504, 100., 0.31271405688264753, 0.3291190991371872, 95.01546938553648, 108.82590676722474, 0.19779506874235347, 0.4683857350264697);
		/** North sky Daylight */ Illuminant D75 = new IlluminantImpl("D75 2°", 7504, 100., 0.299045177218904, 0.3149756045645974, 94.94232978210658, 122.54257555916185, 0.19350612115906676, 0.45858235584393287);
		/** Equal energy */ Illuminant E = new IlluminantImpl("E 2°", 5454, 100., 0.3333333333333333, 0.3333333333333333, 99.99999999999999, 100.00000000000003, 0.21052631578947364, 0.47368421052631576);
		/** Daylight Fluorescent */ Illuminant F1 = new IlluminantImpl("F1 2°", 643, 100., 0.3131, 0.33727, 92.833634773327, 103.66471966080589, 0.19504628533695476, 0.4727318316036031);
		/** Cool White Fluorescent */ Illuminant F2 = new IlluminantImpl("F2 2°", 423, 100., 0.37208, 0.37529, 99.14466146180288, 67.31594233792534, 0.22018782954498384, 0.49969671505417707);
		/** White Fluorescent */ Illuminant F3 = new IlluminantImpl("F3 2°", 345, 100., 0.4091, 0.3943, 103.75348719249304, 49.86051230027898, 0.23669974252900167, 0.5133074898024127);
		/** Warm White Fluorescent */ Illuminant F4 = new IlluminantImpl("F4 2°", 294, 100., 0.44018, 0.40329, 109.14726375561011, 38.813260928860124, 0.25300900113807495, 0.5215616342296153);
		/** Daylight Fluorescent */ Illuminant F5 = new IlluminantImpl("F5 2°", 635, 100., 0.31379, 0.34531, 90.87197011381078, 98.72288668153254, 0.19262324013910076, 0.4769372665412346);
		/** Lite White Fluorescent */ Illuminant F6 = new IlluminantImpl("F6 2°", 415, 100., 0.3779, 0.38835, 97.30912836358955, 60.190549761812804, 0.2189328544116795, 0.5062206708765424);
		/** D65 simulator, Daylight simulator */ Illuminant F7 = new IlluminantImpl("F7 2°", 65, 100., 0.31292, 0.32933, 95.0171560440895, 108.6296420004251, 0.197859035238029, 0.468528892907501);
		/** D50 simulator, Sylvania F40 Design 50 */ Illuminant F8 = new IlluminantImpl("F8 2°", 5, 100., 0.34588, 0.35875, 96.41254355400697, 82.33310104529617, 0.20920456538701152, 0.4882251362418421);
		/** Cool White Deluxe Fluorescent */ Illuminant F9 = new IlluminantImpl("F9 2°", 415, 100., 0.37417, 0.37281, 100.36479708162335, 67.8683511708377, 0.22254207197214135, 0.4988996904264146);
		/** Philips TL85, Ultralume 50 */ Illuminant F10 = new IlluminantImpl("F10 2°", 5, 100., 0.34609, 0.35986, 96.17351192130273, 81.71233257377868, 0.2089240492956684, 0.4887823076481934);
		/** Philips TL84, Ultralume 40 */ Illuminant F11 = new IlluminantImpl("F11 2°", 4, 100., 0.38052, 0.37713, 100.89889428048683, 64.26166043539362, 0.22500931329939153, 0.5017606570754466);
		/** Philips TL83, Ultralume 30 */ Illuminant F12 = new IlluminantImpl("F12 2°", 3, 100., 0.43695, 0.40441, 108.04628965653669, 39.22751662916349, 0.25043630767643593, 0.5215187805737769);
		/** Less-precise version of D65 often found around the internet. */ Illuminant D65_LESS_PRECISE = new IlluminantImpl("D65 2° (LP)", 6504, 100., 0.31271, 0.32902, 95.047, 108.883, 0.19783982482140777, 0.46833630293240974);
		// @formatter:on
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
		// @formatter:off
		/** Incandescent / Tungsten */ Illuminant A = new IlluminantImpl("A 10°", 2856, 100., 0.45117, 0.40594, 111.14204069566931, 35.19978321919493, 0.2589604731853051, 0.5242490249593196);
		/** {obsolete} Direct sunlight at noon */ Illuminant B = new IlluminantImpl("B 10°", 4874, 100., 0.3498, 0.3527, 99.17777147717605, 84.34930535866175, 0.21418074944893456, 0.4859019103600294);
		/** {obsolete} Average / North sky Daylight */ Illuminant C = new IlluminantImpl("C 10°", 6774, 100., 0.31039, 0.31905, 97.28569189782166, 116.14480488951577, 0.19999935565142032, 0.4625536822910458);
		/** Horizon Light. ICC profile PCS */ Illuminant D50 = new IlluminantImpl("D50 10°", 5003, 100., 0.34773, 0.35952, 96.72062750333777, 81.42801513128616, 0.21014748941647854, 0.4888635065676757);
		/** Mid-morning / Mid-afternoon Daylight */ Illuminant D55 = new IlluminantImpl("D55 10°", 5503, 100., 0.33411, 0.34877, 95.7966568225478, 90.92525159847462, 0.20506918806448343, 0.48165112275242367);
		/** Noon Daylight: Television, sRGB color space */ Illuminant D65 = new IlluminantImpl("D65 10°", 6504, 100., 0.31382, 0.331, 94.809667673716, 107.30513595166163, 0.19785762472495255, 0.46955090820823536);
		/** North sky Daylight */ Illuminant D75 = new IlluminantImpl("D75 10°", 7504, 100., 0.29968, 0.3174, 94.41713925645873, 120.64272211720227, 0.19304800432889277, 0.4600414852224999);
		/** Equal energy */ Illuminant E = new IlluminantImpl("E 10°", 5454, 100., 0.3333333333333333, 0.3333333333333333, 99.99999999999999, 100.00000000000003, 0.21052631578947364, 0.47368421052631576);
		/** Daylight Fluorescent */ Illuminant F1 = new IlluminantImpl("F1 10°", 643, 100., 0.31811, 0.33559, 94.79126314848476, 103.19139426085403, 0.1991030941062705, 0.4725983670429332);
		/** Cool White Fluorescent */ Illuminant F2 = new IlluminantImpl("F2 10°", 423, 100., 0.37925, 0.36733, 103.24503852122069, 68.98973674897233, 0.22813882631070792, 0.49717871827185967);
		/** White Fluorescent */ Illuminant F3 = new IlluminantImpl("F3 10°", 345, 100., 0.41761, 0.38324, 108.9682705354347, 51.96482621855753, 0.24697279283701426, 0.5099546695132517);
		/** Warm White Fluorescent */ Illuminant F4 = new IlluminantImpl("F4 10°", 294, 100., 0.4492, 0.39074, 114.96135537697702, 40.96330040436096, 0.26460574215666643, 0.51788091563483);
		/** Daylight Fluorescent */ Illuminant F5 = new IlluminantImpl("F5 10°", 635, 100., 0.31975, 0.34246, 93.36856859195234, 98.63633709046313, 0.19768099634931577, 0.4763725614449415);
		/** Lite White Fluorescent */ Illuminant F6 = new IlluminantImpl("F6 10°", 415, 100., 0.3866, 0.37847, 102.14812270457368, 62.073612175337544, 0.22847214424594145, 0.5032518571487669);
		/** D65 simulator, Daylight simulator */ Illuminant F7 = new IlluminantImpl("F7 10°", 65, 100., 0.31569, 0.3296, 95.77973300970875, 107.61832524271844, 0.19968310293461866, 0.46908356025313813);
		/** D50 simulator, Sylvania F40 Design 50 */ Illuminant F8 = new IlluminantImpl("F8 10°", 5, 100., 0.34902, 0.35939, 97.1145552185648, 81.13470046467627, 0.211059105257429, 0.48899259823663865);
		/** Cool White Deluxe Fluorescent */ Illuminant F9 = new IlluminantImpl("F9 10°", 415, 100., 0.37829, 0.37045, 102.11634498582805, 67.82561749223918, 0.22622226341865978, 0.4984511468390538);
		/** Philips TL85, Ultralume 50 */ Illuminant F10 = new IlluminantImpl("F10 10°", 5, 100., 0.3509, 0.35444, 99.00124139487642, 83.13395779257422, 0.21424166753161114, 0.486906775262994);
		/** Philips TL84, Ultralume 40 */ Illuminant F11 = new IlluminantImpl("F11 10°", 4, 100., 0.38541, 0.37123, 103.81973439646579, 65.55504673652452, 0.2306483900214544, 0.4998653488810492);
		/** Philips TL83, Ultralume 30 */ Illuminant F12 = new IlluminantImpl("F12 10°", 3, 100., 0.44256, 0.39717, 111.42835561598308, 40.352997457008314, 0.2572679234753492, 0.5194843131441726);
		// @formatter:on
	}

}
