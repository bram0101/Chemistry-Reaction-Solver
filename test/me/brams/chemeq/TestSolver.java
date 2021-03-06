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
import me.brams.chemeq.solver.Solver;

/**
 * A test class to test the solver
 * @author Bram
 *
 */
public class TestSolver {
	
	public static void main(String[] args) {
		//String formula = "(K) + (Br2) -> (KBr)";
		//String formula = "(FeS2) + (O2) -> (Fe2O3) + (SO2)";
		//String formula = "(C6H6O) + (O2) -> (CO2) + (H2O)";
		String formula = "(FeCl3) + (Na) -> (Fe) + (NaCl)";
		Formula f = FormulaBuilder.getFormulaFromString(formula);
		System.out.println(f.toString() + "\n");
		Formula solved = Solver.solve(f);
		System.out.println(solved.toString());
	}
	
}
