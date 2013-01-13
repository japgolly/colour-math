package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.github.japgolly.colourmath.data.ColourVerification;
import com.github.japgolly.colourmath.data.TestData;
import com.github.japgolly.colourmath.illuminant.Illuminant;
import com.github.japgolly.colourmath.illuminant.Illuminants;

/**
 * TODOC: com.github.japgolly.colourmath.Blah
 * 
 * @since 13/01/2013
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class ConversionTest {

	@Parameterized.Parameters
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection<?> testData() {
		Illuminants.setDefault(Illuminants.CIE1931.D65_LESS_PRECISE);
		List names = new ArrayList();
		for (String name : TestData.TEST_DATA.keySet()) {
			names.add(new Object[] { name });
		}
		return names;
	}

	private final String dataName;
	private ColourVerification testData;
	private Colour tmpLatestActualColour;

	@Before
	public void setup() {
		Illuminants.setDefault(Illuminants.CIE1931.D65_LESS_PRECISE);
		testData = TestData.TEST_DATA.get(dataName);
	}

	@Test
	public void RGB01_conversions() throws Exception {
		test(testData.RGB01());
	}

	@Test
	public void RGB255_conversions() throws Exception {
		test(testData.RGB255());
	}

	@Test
	public void HSL01_conversions() throws Exception {
		test(testData.HSL01());
	}

	@Test
	public void HSL255_conversions() throws Exception {
		test(testData.HSL255());
	}

	@Test
	public void XYZ_conversions() throws Exception {
		test(testData.XYZ());
	}

	@Test
	public void Lab_conversions() throws Exception {
		test(testData.Lab());
	}

	@Test
	public void Luv_conversions() throws Exception {
		test(testData.Luv());
	}

	private void test(final Colour subject) throws Exception {
		try {
			for (Method m : Colour.class.getMethods()) {
				if (m.getParameterTypes().length == 0) {
					final Class<?> type = m.getReturnType();
					final Object a = m.invoke(subject);
					final Object e = m.invoke(testData);
					// assertValues(subject.getClass().getSimpleName() + "." + m.getName() + "()", type, a, e, true);
					assertValues(subject.getClass().getSimpleName(), type, a, e, true);
				}
			}
		} catch (AssertionError e) {
			System.out.printf("FAIL: %s\n", tmpLatestActualColour);
			throw e;
		} finally {
			log("");
		}
	}

	private void log(String fmt, Object... args) {
		// System.out.println(String.format(fmt, args));
	}

	protected void assertValues(String desc, Class<?> type, Object actual, Object expected, boolean coloursOnly)
			throws Exception {
		if (Colour.class.isAssignableFrom(type)) {
			assertColours(desc, (Colour) actual, (Colour) expected);
		}//
		else if (coloursOnly) {
			// Ignore
		} //
		else if (int.class.equals(type)) {
			log(desc);
			assertInts(desc, (Integer) actual, (Integer) expected);
		}//
		else if (double.class.equals(type)) {
			log(desc);
			assertDoubles(desc, (Double) actual, (Double) expected);
		}//
		else if (Illuminant.class.isAssignableFrom(type)) {
			log(desc);
			assertIlluminants(desc, (Illuminant) actual, (Illuminant) expected);
		}//
		else {
			throw new RuntimeException(desc + ": Can't handle " + type.getCanonicalName());
		}
	}

	@SuppressWarnings("rawtypes")
	protected void assertColours(String desc, Colour actual, Colour expected) throws Exception {
		assertThat((Class) actual.getClass()).isEqualTo(expected.getClass());
		tmpLatestActualColour = actual;

		for (Field f : expected.getClass().getFields()) {
			if ((f.getModifiers() & (Modifier.STATIC)) != 0) {
				continue;
			}
			final Class<?> type = f.getType();
			final Object a = f.get(actual);
			final Object e = f.get(expected);
			assertValues(desc + " → " + actual.getClass().getSimpleName() + "." + f.getName(), type, a, e, false);
		}
	}

	protected void assertInts(String desc, int actual, int expected) {
		final int tolerance = getToleranceForInts(desc);
		if (tolerance == 0) {
			assertThat(actual).describedAs(desc).isEqualTo(expected);
		} else {
			assertThat((float) actual).describedAs(desc).isEqualTo(expected, offset((float) tolerance));
		}
	}

	protected void assertDoubles(String desc, double actual, double expected) {
		final double tolerance = getToleranceForDoubles(desc);
		assertThat(actual).describedAs(desc).isEqualTo(expected, offset(tolerance));
	}

	protected void assertIlluminants(String desc, Illuminant actual, Illuminant expected) {
		assertThat(actual).describedAs(desc).isEqualTo(expected);
	}

	public static final Pattern DESC_TYPES = Pattern.compile("^Colour(.+?)\\d* → Colour(.+?)\\d*\\.\\S+$");

	protected double getToleranceForDoubles(String d) {
		Double r = testData.getSpecialCaseTolerance(d, tmpLatestActualColour);
		if (r != null) {
			return r;
		}
		if (isHSLandColourless(d)) {
			return 9999;
		}
		if (sameColourSpace(d)) {
			return 1. / 255.;
		}
		if (from(d, "HSL255")) {
			return to(d, "XYZ") ? 1.0 : 2.0;
		}
		if (from(d, "HSL01")) {
			return to(d, "XYZ") ? 0.2 : 0.4;
		}
		return 0.008;
	}

	protected int getToleranceForInts(String d) {
		Double r = testData.getSpecialCaseTolerance(d, tmpLatestActualColour);
		if (r != null) {
			return r.intValue();
		}
		if (isHSLandColourless(d)) {
			return 9999;
		}
		if (sameColourSpace(d)) {
			return 0;
		}
		if (to(d, "RGB255|HSL255")) {
			return from(d, "RGB255|HSL255") ? 2 : 1;
		}
		return 0;
	}

	protected boolean isHSLandColourless(String d) {
		// Ignore hue & sat when lum is 0 or 1
		if (d.matches(".*HSL\\d+\\.[hs]")) {
			if (tmpLatestActualColour instanceof ColourHSL255) {
				ColourHSL255 c = (ColourHSL255) tmpLatestActualColour;
				if (c.l == 0 || c.l == 255) {
					return true;
				}
			}
			if (tmpLatestActualColour instanceof ColourHSL01) {
				ColourHSL01 c = (ColourHSL01) tmpLatestActualColour;
				if (c.l < (1. / 255.) || c.l > (254. / 255.)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean sameColourSpace(String d) {
		// Check if same colour space (eg. RGB01 -> RGB255)
		Matcher m = DESC_TYPES.matcher(d);
		if (m.matches()) {
			if (m.group(1).equals(m.group(2))) {
				return true;
			}
		}
		return false;
	}

	protected boolean from(String desc, String colourSpaces) {
		return desc.matches("^Colour(?:" + colourSpaces + ").* → Colour.*");
	}

	protected boolean to(String desc, String colourSpaces) {
		return desc.matches(".* → Colour(?:" + colourSpaces + ")\\.\\S+$");
	}
}
