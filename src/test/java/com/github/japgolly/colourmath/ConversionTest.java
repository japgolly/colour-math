package com.github.japgolly.colourmath;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
		List names = new ArrayList();
		for (String name : TestData.TEST_DATA.keySet()) {
			names.add(new Object[] { name });
		}
		return names;
	}

	private final String dataName;
	private Colour testData;

	@Before
	public void setup() {
		Illuminants.setDefault(Illuminants.CIE1931.D65);
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

	protected double getToleranceForDoubles(String d) {
		if (from(d, "RGB255")) {
			return 0.2;
		}
		if (from(d, "HSL255")) {
			return to(d, "XYZ") ? 1.0 : 2.0;
		}
		if (from(d, "HSL01")) {
			return to(d, "XYZ") ? 0.2 : 0.4;
		}
		return 0.005;
	}

	protected int getToleranceForInts(String d) {
		// if (from(d, "Luv|Lab|XYZ") && to(d, "RGB255|HSL255")) {
		if (to(d, "RGB255|HSL255")) {
			return 1;
		} else {
			return 0;
		}
	}

	protected boolean from(String desc, String colourSpaces) {
		return desc.matches("^Colour(?:" + colourSpaces + ").* → Colour.*");
	}

	protected boolean to(String desc, String colourSpaces) {
		return desc.matches(".* → Colour(?:" + colourSpaces + ")\\.\\S+$");
	}
}