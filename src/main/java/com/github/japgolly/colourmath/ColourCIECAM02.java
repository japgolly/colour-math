package com.github.japgolly.colourmath;

import static com.github.japgolly.colourmath.MathFunc.multiply;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Surrounding;

/**
 * TODOC: com.github.japgolly.colourmath.ColourCIECAM02
 * 
 * @since 23/01/2013
 */
@Immutable
@ToString
@EqualsAndHashCode(callSuper = false)
public class ColourCIECAM02 extends ColourAdapter {

	@Wither public final double H, J, Q, C, M, s;
	public final Illuminant illuminant;
	public final Surrounding surrounding;

	ColourCIECAM02(double H, double J, double Q, double C, double M, double s, Illuminant illuminant,
			Surrounding surrounding) {
		this.H = H;
		this.J = J;
		this.Q = Q;
		this.C = C;
		this.M = M;
		this.s = s;
		this.illuminant = illuminant;
		this.surrounding = surrounding;
	}

	ColourCIECAM02(ColourXYZ src, Surrounding surrounding) {
		// https://sites.google.com/site/clifframes/ciecam02plugin

		final Illuminant w = src.illuminant;

		// http://www.mathworks.com.au/matlabcentral/fileexchange/36315-computational-colour-toolbox/content/ciecam02.m
		// la and yb are the luminance and Y tristimulus values of the achromatic background against which the sample is
		// viewed

		Colour background = ColourFactory.getInstance().RGB(0x000000);

		// Background Luminance Factor, range 1-100%, relative to the luminance of the adopted white. Yb is usually
		// estimated to be 20%, however some researchers suggest that the background can be considered to be the whole
		// image, in which case Yb would be the average luminance of the image. To facilitate the latter, the plug-in
		// automatically calculates the average Y of the image, and provides aveY just below the sampler info readout
		// (4).
		final double Yb = 0.7;
		// final double Yb = background.XYZ(src.illuminant).Y;

		// Adapting Field Luminance in cd/m^2. Note that the Adapting Luminance (La) is not the same as the luminance of
		// white (Lw). La is usually estimated to be 20% of the white luminance Lw, or 0.2 * Lw. More generally, La can
		// be calculated: La = Lw * Yb / 100 (see Luo). (The luminance of white Lw is not specified as an input to the
		// model, it is calculated internally as 5 * La). For example, to model a monitor with a white luminance of 100
		// cd/m^2, set La = 0.2 * 100 = 20 cd/m^2
		final double LA = 20;

		// LMS
		final double[] LMS = multiply(new double[][] { { src.X, src.Y, src.Z } }, CAT02_M)[0];

		// LMSw
		final double[] LMSw = multiply(new double[][] { { w.X(), w.Y(), w.Z() } }, CAT02_M)[0];

		// LMSc
		final double D = surrounding.F * (1.0 - (1.0 / 3.6) * Math.exp(-(LA + 42.0) / 92.0));
		final double Lc = LMS[0] * (w.Y() / LMSw[0] * D + 1.0 - D);
		final double Mc = LMS[1] * (w.Y() / LMSw[1] * D + 1.0 - D);
		final double Sc = LMS[2] * (w.Y() / LMSw[2] * D + 1.0 - D);

		// Post-adaption
		final double LA5 = 5.0 * LA;
		final double k = 1.0 / (LA5 + 1.0);
		final double k4 = pow(k, 4);
		final double FL = 0.2 * k4 * LA5 + 0.1 * pow(1 - k4, 2) * pow(LA5, 1.0 / 3.0);
		final double[] LMS_ = multiply(multiply(new double[][] { { Lc, Mc, Sc } }, CAT02_MI), CAT02_MH)[0];
		final double[] LMSw_ = multiply(multiply(new double[][] { LMSw }, CAT02_MI), CAT02_MH)[0];
		final double L_a = LMS_a(FL, LMS_[0]);
		final double M_a = LMS_a(FL, LMS_[1]);
		final double S_a = LMS_a(FL, LMS_[2]);
		final double Lw_a = LMS_a(FL, LMSw_[0]);
		final double Mw_a = LMS_a(FL, LMSw_[1]);
		final double Sw_a = LMS_a(FL, LMSw_[2]);

		// final double C1 = L_a - M_a;
		// final double C2 = M_a - S_a;
		// final double C3 = S_a - L_a;
		// final double a = C1 - C2/11.0;
		// final double b = 0.5 * (C2-C1)
		//
		final double a = L_a - 12.0 * M_a / 11.0 + S_a / 11.0;
		final double b = (1.0 / 9.0) * (L_a + M_a - 2 * S_a);
		double h = $180_DIV_PI * Math.atan2(b, a);
		if (h < 0) {
			h += 360.0;
		}
		final int i = (h < hi[0]) ? 0 : (h < hi[1]) ? 1 : (h < hi[2]) ? 2 : (h < hi[3]) ? 3 : 4;
		final int iNext = (i == 4) ? 0 : i + 1;
		final double $hhi_div_e = (h - hi[i]) / ei[i];
		final double H = Hi[i] + 100.0 * $hhi_div_e / ($hhi_div_e + (hi[iNext] - h) / ei[iNext]);

		final double n = Yb / w.Y();
		final double Nbb = 0.725 * pow(n, -0.2);
		final double A = (2 * L_a + M_a + 0.05 * S_a - 0.305) * Nbb;
		final double Aw = (2 * Lw_a + Mw_a + 0.05 * Sw_a - 0.305) * Nbb;
		final double z = 1.48 + sqrt(n);
		final double J = 100.0 * pow(A / Aw, surrounding.c * z);

		final double sqrt_J_100 = sqrt(0.01 * J);
		final double FL_pow_qtr = pow(FL, 0.25);
		final double Q = (4.0 / surrounding.c) * sqrt_J_100 * (Aw + 4.0) * FL_pow_qtr;

		final double et = 0.25 * (cos(h / $180_DIV_PI + 2) + 3.8);
		final double t =
				(50000.0 / 13.0) * surrounding.Nc * Nbb * et * sqrt(a * a + b * b) / (L_a + M_a + 21.0 / 20.0 * S_a);
		final double C = pow(t, 0.9) * sqrt_J_100 * pow(1.64 - pow(0.29, n), 0.73);

		final double M = C * FL_pow_qtr;
		final double s = 100.0 * sqrt(M / Q);

		this.H = H;
		this.J = J;
		this.Q = Q;
		this.C = C;
		this.M = M;
		this.s = s;
		this.illuminant = src.illuminant;
		this.surrounding = surrounding;
	}

	private static final double hi[] = { 20.14, 90, 164.25, 237.53, 380.14 };
	private static final double ei[] = { 0.8, 0.7, 1.0, 1.2, 0.8 };
	private static final double Hi[] = { 0, 100, 200, 300, 400 };

	private static double LMS_a(double FL, double v) {
		final double x = pow(FL * v / 100.0, 0.42);
		return (400.0 * x) / (27.13 + x) + 0.1;
	}

	/**
	 * @return [0,360)
	 */
	public double getHueAngle() {
		return H;
	}

	public double getLightness() {
		return J;
	}

	public double getBrightness() {
		return Q;
	}

	public double getChroma() {
		return C;
	}

	public double getColourfulness() {
		return M;
	}

	public double getSaturation() {
		return s;
	}

	public ColourCIECAM02 withHueAngle(double value) {
		return withH(value);
	}

	public ColourCIECAM02 withLightness(double value) {
		return withJ(value);
	}

	public ColourCIECAM02 withBrightness(double value) {
		return withQ(value);
	}

	public ColourCIECAM02 withChroma(double value) {
		return withC(value);
	}

	public ColourCIECAM02 withColourfulness(double value) {
		return withM(value);
	}

	public ColourCIECAM02 withSaturation(double value) {
		return withS(value);
	}

	@Override
	public ColourXYZ XYZ(Illuminant illuminant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColourRGB01 RGB01() {
		return XYZ().RGB01();
	}
}
