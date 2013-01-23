package com.github.japgolly.colourmath;


// /**
// * JCh is a three-component space derived from the CIECAM02 color appearance model, where J represents lightness, C
// * represents chroma, and h represents hue angle. It is thought to be the best currently-implemented means of
// * representing value, and so it is employed wherever value is used as a metric. Note the the conversion to and from
// * CIECAM02 is somewhat expensive; it is best to code such that it need be performed as infrequently as possible.
// */
// public class JCh extends PColor {
// /**
// * Lightness
// */
// public static final int J = 0;
//
// /**
// * Chroma
// */
// public static final int C = 1;
//
// /**
// * Hue
// */
// public static final int h = 2;
//
// /**
// * Use a and b correlates derived directly from C and h.
// */
// public static final int QUALITY_STANDARD = 0;
//
// /**
// * Use a and b correlates derived from CIECAM02 model.
// */
// public static final int QUALITY_ENHANCED = 1;
//
// public JCh(PColor color) {
// this(color, new CS_JCh());
// }
//
// public JCh(PColor color, CS_JCh cspace) {
// super(cspace, color);
// }
//
// public JCh(float J, float C, float h) {
// this(J, C, h, 1f);
// }
//
// public JCh(float J, float C, float h, float alpha) {
// super(new CS_JCh(), new float[] { J, C, h }, alpha);
// }
//
// public JCh(float J, float C, float h, float alpha, CS_JCh cspace) {
// super(cspace, new float[] { J, C, h }, alpha);
// }
//
// /**
// * Calculates the mean JCh color in an array of JCh colors.
// *
// * @param jchColors - An array of JCh colors
// * @return A new average JCh color in the default colorspace.
// */
// public static JCh average(JCh[] jchColors) {
// double J = 0, a = 0, b = 0, alpha = 0;
//
// // perform average in Jab space
// for (int i = 0; i < jchColors.length; i++) {
// // handle lightness
// J += jchColors[i].get(0);
//
// // handle hue and chroma
// double hRad = Math.toRadians(jchColors[i].get(2));
// a += jchColors[i].get(1) * Math.cos(hRad);
// b += jchColors[i].get(1) * Math.sin(hRad);
//
// // handle alpha
// alpha += jchColors[i].getAlpha();
// }
// J /= jchColors.length;
// a /= jchColors.length;
// b /= jchColors.length;
// alpha /= jchColors.length;
//
// // convert back to JCh
// double h = ColorTools.calculateAtan(a, b);
// float C = (float) (a / Math.cos(Math.toRadians(h)));
//
// return new JCh((float) J, C, (float) h, (float) alpha);
// }
//
// /**
// * Returns a new array blending an array of JCh colors. weights[] should be an array of floats equal of length equal
// * to colors[], with each value representing that color's weight in the blend. For example, if a color is weighted
// * 1.0 colorBlend() will return just that color, whereas if two colors are both weighted 0.5 the result will be
// * halfway between them.
// * <P>
// * Note that weights[] must be normalized such that total sum of all values in the array == 1.0; otherwise, the
// * result will be distorted.
// *
// * @param colors - The array of colors to be blended together
// * @param weights - The array of weights specifying how much each color figures in the final result
// * @return A new blended JCh color in the default colorspace.
// */
// public static JCh colorBlend(JCh[] colors, float[] weights) {
// double J = 0, a = 0, b = 0;
//
// // perform blending in Jab space
// for (int i = 0; i < colors.length; i++) {
// J += colors[i].get(0) * weights[i];
//
// double hRad = Math.toRadians(colors[i].get(2));
// a += (colors[i].get(1) * Math.cos(hRad)) * weights[i];
// b += (colors[i].get(1) * Math.sin(hRad)) * weights[i];
// }
//
// // return to JCh
// double h = ColorTools.calculateAtan(a, b);
// double C = a / Math.cos(Math.toRadians(h));
//
// // return result
// return new JCh((float) J, (float) C, (float) h, 1f);
// }
//
// /**
// * Calculates the distance between two JCh colors using the CIE's recommended CAM02-UCS calculation.
// *
// * @param color1 the first color
// * @param color2 the second color
// * @return the distance between two JCh colors.
// */
// public static double distance(JCh color1, JCh color2) {
// return distance(color1, color2, 1, 1);
// }
//
// /**
// * Calculates the distance between two JCh colors using the CIE's recommended CAM02-UCS calculation and the
// * specified weights.
// *
// * @param color1 the first color
// * @param color2 the second color
// * @param lightnessWeight the extent, relative to colorfulnessWeight, that lightness should figure into the
// * calculation
// * @param colorfulnessWeight the extent, relative to lightnessWeight, that colorfulness should figure into the
// * calculation
// * @return the distance between two JCh colors.
// */
// public static double distance(JCh color1, JCh color2, float lightnessWeight, float colorfulnessWeight) {
// // Normalize weights
// float max = Math.max(lightnessWeight, colorfulnessWeight);
// if (max <= 0) {
// lightnessWeight = colorfulnessWeight = 0;
// } else {
// lightnessWeight = lightnessWeight / max;
// colorfulnessWeight = colorfulnessWeight / max;
// }
//
// // Calculate J' a'M b'M colors
// double[] JabPrime1 = getJabPrime(color1);
// double[] JabPrime2 = getJabPrime(color2);
//
// // calculate Cartesian distance between JabPrime colors
// return Math.sqrt(Math.pow((JabPrime1[0] - JabPrime2[0]) * lightnessWeight, 2)
// + Math.pow((JabPrime1[1] - JabPrime2[1]) * colorfulnessWeight, 2)
// + Math.pow((JabPrime1[2] - JabPrime2[2]) * colorfulnessWeight, 2));
// }
//
// private static double[] getJabPrime(JCh color) {
// double[] result = new double[4];
//
// CS_JCh cspace1 = (CS_JCh) color.getColorSpace();
// double M = cspace1.calculateM(color.get(1));
// double MPrime = (1d / 0.0228) * Math.log(1d + 0.0228 * M);
//
// result[0] = ((1 + 100 * 0.007) * color.get(0)) / (1d + 0.007 * color.get(0));
// result[1] = MPrime * Math.cos(Math.toRadians(color.get(2)));
// result[2] = MPrime * Math.sin(Math.toRadians(color.get(2)));
// result[3] = color.getAlpha();
//
// return result;
// }
//
// public static class LightnessComparator implements Comparator<JCh> {
// @Override
// public int compare(JCh a, JCh b) {
// if (a.get(0) < b.get(0)) {
// return -1;
// } else if (a.get(0) > b.get(0)) {
// return 1;
// } else {
// return 0;
// }
// }
// }
//
// public static PColorCanvas<JCh> gaussianBlur(PColorCanvas<JCh> canvas, float blurAmount) {
// PColorCanvas<JCh> result = canvas.clone();
//
// if (blurAmount != 0) {
// // create new image for output of blur
// BufferedImage src = canvas.getBufferedImage();
// BufferedImage out = result.getBufferedImage();
//
// // apply Gaussian filter
// JCh.GaussianFilter filter = new JCh.GaussianFilter(blurAmount);
// filter.filter(src, out);
// }
//
// return result;
// }
//
// public static class GaussianFilter extends com.jhlabs.image.GaussianFilter {
// public GaussianFilter(float radius) {
// super(radius);
// }
//
// public BufferedImage filter(BufferedImage src, BufferedImage dst) {
// int width = src.getWidth();
// int height = src.getHeight();
//
// if (dst == null) {
// dst = createCompatibleDestImage(src, null);
// }
//
// // grab colors from src
// float[] inPixels = new float[width * height * src.getColorModel().getNumComponents()];
// float[] outPixels = new float[inPixels.length];
// src.getRaster().getPixels(0, 0, width, height, inPixels);
//
// // convert to Jab so that hue can be handled correctly
// for (int i = 0; i < inPixels.length; i += 4) {
// float C = inPixels[i + 1];
// double h = Math.toRadians(inPixels[i + 2]);
// inPixels[i + 1] = (float) (C * Math.cos(h));
// inPixels[i + 2] = (float) (C * Math.sin(h));
// }
//
// // run convolution
// convolveAndTranspose(kernel, inPixels, outPixels, width, height, alpha, CLAMP_EDGES);
// convolveAndTranspose(kernel, outPixels, inPixels, height, width, alpha, CLAMP_EDGES);
//
// // convert back to JCh
// for (int i = 0; i < inPixels.length; i += 4) {
// float a = inPixels[i + 1];
// float b = inPixels[i + 2];
// double h = ColorTools.calculateAtan(a, b);
// inPixels[i + 1] = (float) (a / Math.cos(Math.toRadians(h)));
// inPixels[i + 2] = (float) h;
// }
//
// // set dst with new pixels
// dst.getRaster().setPixels(0, 0, width, height, inPixels);
// return dst;
// }
//
// public static void convolveAndTranspose(Kernel kernel, float[] inPixels, float[] outPixels, int width,
// int height, boolean alpha, int edgeAction) {
// float[] matrix = kernel.getKernelData(null);
// int cols = kernel.getWidth();
// int cols2 = cols / 2;
//
// for (int y = 0; y < height; y++) {
// int index = y * 4;
// int ioffset = y * width * 4;
// for (int x = 0; x < width; x++) {
// float J = 0, a = 0, b = 0, al = 0;
//
// // float hue = inPixels[ioffset + x * 4];
//
// int moffset = cols2;
// for (int col = -cols2; col <= cols2; col++) {
// float f = matrix[moffset + col];
//
// if (f != 0) {
// int ix = x + col;
// if (ix < 0) {
// if (edgeAction == CLAMP_EDGES) {
// ix = 0;
// } else if (edgeAction == WRAP_EDGES) {
// ix = (x + width) % width;
// }
// } else if (ix >= width) {
// if (edgeAction == CLAMP_EDGES) {
// ix = width - 1;
// } else if (edgeAction == WRAP_EDGES) {
// ix = (x + width) % width;
// }
// }
//
// int ind = ioffset + ix * 4;
// J += f * inPixels[ind + 0];
// a += f * inPixels[ind + 1];
// b += f * inPixels[ind + 2];
// al += f * inPixels[ind + 3];
// }
// }
// // int ind = ioffset + x * 4;
// outPixels[index + 0] = J;
// outPixels[index + 1] = a;
// outPixels[index + 2] = b;
// outPixels[index + 3] = al;
// // outPixels[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
// index += height * 4;
// }
// }
// }
// }
//
// /**
// * Scales a PColorCanvas<JCh> in Jab color space, where a and b are derived directly from Chroma and Hue (which is
// * must faster than going back to sRGB). This method can result in artifacts when mapping JCh colors back to RGB due
// * to quirkiness in the CIECAM02->XYZ conversion algortihms at very low lightness values.
// */
// public static PColorCanvas<JCh> getScaledInstance(PColorCanvas<JCh> source, int newWidth, int newHeight) {
// return getScaledInstance(source, newWidth, newHeight, QUALITY_STANDARD);
// }
//
// /**
// * Scales a PColorCanvas<JCh> in Jab color space.
// * <p>
// * When quality is set to QUALITY_STANDARD, a and b are derived directly from Chroma and Hue (which is must faster
// * than going back to sRGB). This method can result in artifacts when mapping JCh colors back to RGB due to
// * quirkiness in the CIECAM02->XYZ conversion algortihms at very low lightness values.
// * <p>
// * When quality is set to QUALITY_ENHANCED, a and b are derived using CIECAM02's official red-green and yellow-blue
// * correlates. This calculation is more expensive than getting Cartesian a and b correlates directly from JCh's
// * polar coordinate system, but it avoids introducing strange artifacts in low-lightness areas upon conversion back
// * to RGB.
// */
// public static PColorCanvas<JCh>
// getScaledInstance(PColorCanvas<JCh> source, int newWidth, int newHeight, int quality) {
// if (newWidth == (int) source.getWidth() && newHeight == (int) source.getHeight()) {
// return source.clone();
// } else {
// PColorCanvas<Jab> jabCanvas = createJabCanvas(source, quality);
//
// return createJChCanvas(jabCanvas.getScaledInstance(newWidth, newHeight), quality);
// }
// }
//
// private static PColorCanvas<Jab> createJabCanvas(PColorCanvas<JCh> source, int quality) {
// PColorCanvas<Jab> result =
// new PColorCanvas<Jab>(
// source.getName(), source.getWidth(), source.getHeight(), source.getResolution(), new Jab(
// 0, 0, 0));
//
// if (quality == QUALITY_ENHANCED) {
// CS_Jab cspace = new CS_Jab();
//
// for (int y = 0; y < source.getHeight(); y++) {
// for (int x = 0; x < source.getWidth(); x++) {
// float[] temp = new float[4];
// source.getBufferedImage().getRaster().getDataElements(x, y, temp);
//
// float[] jab = cspace.fromJCh(temp);
// for (int i = 0; i < 3; i++) {
// temp[i] = jab[i];
// }
//
// result.getBufferedImage().getRaster().setDataElements(x, y, temp);
// }
// }
// } else {
// for (int y = 0; y < source.getHeight(); y++) {
// for (int x = 0; x < source.getWidth(); x++) {
// float[] jch = new float[4];
// source.getBufferedImage().getRaster().getDataElements(x, y, jch);
//
// double h = Math.toRadians(jch[2]);
// result.getBufferedImage()
// .getRaster()
// .setDataElements(
// x,
// y,
// new float[] { jch[0], (float) (jch[1] * Math.cos(h)),
// (float) (jch[1] * Math.sin(h)), jch[3] });
// }
// }
// }
//
// return result;
// }
//
// private static PColorCanvas<JCh> createJChCanvas(PColorCanvas<Jab> source, int quality) {
// PColorCanvas<JCh> result =
// new PColorCanvas<JCh>(
// source.getName(), source.getWidth(), source.getHeight(), source.getResolution(), new JCh(
// 0, 0, 0));
//
// if (quality == QUALITY_ENHANCED) {
// CS_Jab cspace = new CS_Jab();
//
// for (int y = 0; y < source.getHeight(); y++) {
// for (int x = 0; x < source.getWidth(); x++) {
// float[] temp = new float[4];
// source.getBufferedImage().getRaster().getDataElements(x, y, temp);
//
// float[] jch = cspace.toJCh(temp);
// for (int i = 0; i < 3; i++) {
// temp[i] = jch[i];
// }
//
// result.getBufferedImage().getRaster().setDataElements(x, y, temp);
// }
// }
// } else {
// for (int y = 0; y < result.getHeight(); y++) {
// for (int x = 0; x < result.getWidth(); x++) {
// float[] jab = new float[4];
// source.getBufferedImage().getRaster().getDataElements(x, y, jab);
//
// double h = ColorTools.calculateAtan(jab[1], jab[2]);
// double C = (jab[1] / Math.cos(Math.toRadians(h)));
//
// result.getBufferedImage().getRaster()
// .setDataElements(x, y, new float[] { jab[0], (float) C, (float) h, jab[3] });
// }
// }
// }
//
// return result;
// }
//
// public static JCh[] getPaletteFromXML(Element configRoot, String paletteFileNodeName, String colorNodeName,
// boolean ignoreDuplicateColors) {
// JCh[] palColors;
//
// Element e = findLastChildByName(configRoot, paletteFileNodeName);
// if (e != null) {
// File paletteFile = new File(e.getTextContent());
// try {
// // get HSV colours from palette file
// palColors = ImageUtils.getJChPaletteColors(paletteFile, ignoreDuplicateColors);
//
// } catch (FileNotFoundException cause) {
// throw new ConfigFileFormatException("The supplied image file does not exist", cause);
// } catch (IOException cause) {
// throw new PaletteInstantiationException("Unable to read image file " + paletteFile, cause);
// }
// } else {
// ArrayList<JCh> colors = new ArrayList<JCh>();
//
// for (Element el : findChildrenByName(configRoot, colorNodeName)) {
// String[] inputs = el.getTextContent().trim().split("\\s+");
// float[] color = new float[inputs.length];
// for (int j = 0; j < inputs.length; j++) {
// color[j] = parseIntOrFloat(inputs[j]);
// }
// colors.add(new JCh(color[0], color[1], color[2]));
// }
//
// // if no colors then go to black and white and tell user
// if (colors.isEmpty()) {
// colors.add(new JCh(0, 0, 0));
// colors.add(new JCh(100, 0, 0));
// System.err.println("WARNING: Palette lacks palette-file or hsv elements."
// + " Using black and white as only colors.");
// }
//
// palColors = colors.toArray(new JCh[colors.size()]);
// }
//
// return palColors;
// }
//
// public JCh convertFrom(PColor color) {
// return new JCh(color, (CS_JCh) this.getColorSpace());
// }
//
// public JCh clone() {
// return new JCh(this);
// }
// }
