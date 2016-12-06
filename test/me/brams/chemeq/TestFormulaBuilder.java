/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq;

import me.brams.chemeq.formula.Formula;
import me.brams.chemeq.formula.FormulaBuilder;

/**
 * A test class to see how FormulaBuilder parses the string
 * and print it out.
 * @author Bram
 *
 */
public class TestFormulaBuilder {

	public static void main(String[] args) {
		String formula = "4(FeO2Fe) + (H2O) -> 1(Fe)+ 2(H2)+(O2)";
		Formula f = FormulaBuilder.getFormulaFromString(formula);
		System.out.println(f.toString());
	}

}
