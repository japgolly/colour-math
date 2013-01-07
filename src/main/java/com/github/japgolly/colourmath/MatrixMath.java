package com.github.japgolly.colourmath;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * TODOC: com.github.japgolly.colourmath.MatrixMath
 * 
 * @since 07/01/2013
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class MatrixMath {

	public static double multiplyRow(double[] mRow, double a, double b, double c) {
		return a * mRow[0] + b * mRow[1] + c * mRow[2];
	}

	public static double[][] multiply(double[][] a, double[][] b) {
		final int aRows = a.length;
		final int aCols = a[0].length;
		final int bRows = b.length;
		final int bCols = b[0].length;

		// Ensure multiplication is applicable
		if (aCols != bRows) {
			throw new IllegalArgumentException(String.format(
					"Number of columns in matrix 1 (%d) should match number of rows in matrix 2 (%d).", aCols, bRows));
		}

		// Multiply
		final double[][] results = new double[aRows][bCols];
		double sum;
		for (int i = 0; i < aRows; i++) {
			final double[] ai = a[i];
			final double[] ri = results[i];
			for (int j = 0; j < bCols; j++) {

				sum = 0;
				for (int k = 0; k < bRows; k++) {
					sum += ai[k] * b[k][j];
				}
				ri[j] = sum;
			}
		}

		return results;
	}
}
