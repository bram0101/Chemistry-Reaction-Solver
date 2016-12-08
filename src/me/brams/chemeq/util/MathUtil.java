/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq.util;

import java.math.BigInteger;

/**
 * Class for some utility methods
 * @author Bram
 *
 */
public class MathUtil {
	
	/**
	 * Get the greatest common divider for a list of longs
	 * @param a
	 * @return
	 */
	public static long getGCD(long... a) {
		// If we got less than 2 inputs, just return 1
		if(a.length < 2)
			return 1;
		// If we got 2 inputs, we use the method that the BigInteger class
		// has to calculate the greatest common divider.
		if(a.length == 2) {
			BigInteger bi1 = BigInteger.valueOf(a[0]);
			return bi1.gcd(BigInteger.valueOf(a[1])).intValue();
		}
		// If we got more than 2 inputs, then remove the first 2 inputs and calculate
		// the greatest common divider of those two and set that as the first input in the new list.
		// Then call this method again but with the new list.
		long[] newA = new long[a.length - 1];
		newA[0] = getGCD(a[0], a[1]);
		for(int i = 1; i < newA.length; i++) {
			newA[i] = a[i + 1];
		}
		return getGCD(newA);
	}
	
	/**
	 * Get the greatest common divider for a list of doubles
	 * @param a
	 * @return
	 */
	public static double getGCD(double... a) {
		// Convert everything to a long. To accommodate for the decimal,
		// multiply it by a factor of 10 and later on divide by it.
		long[] newA = new long[a.length];
		for(int i = 0; i < a.length; i++) {
			newA[i] = (long) (a[i] * 1000000.0);
		}
		return ((double) getGCD(newA)) / 1000000.0;
	}
	
}
