/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.brams.chemeq.formula.Element;
import me.brams.chemeq.formula.Formula;
import me.brams.chemeq.formula.Molecule;
import me.brams.chemeq.util.Logger;
import me.brams.chemeq.util.MathUtil;

/** This is the class that will solve the equation */
public class Solver {

	/**
	 * Solve the formula and save the correct coefficients in the same Formula instance. The equation is made by adding all the atoms on the other side and
	 * subtracting the atoms on the current side
	 * 
	 * @param formula
	 * @return Formula
	 */
	public static Formula solve(Formula formula) {
		// Save the different equations to solve it
		List<Equation> equations = new ArrayList<Equation>();

		// Loop through ever element
		for (Element e : formula.getElements()) {

			// Go through every molecule on the left term
			for (int i = 0; i < formula.getLeftTerm().size(); i++) {
				Molecule m = formula.getLeftTerm().get(i);
				// If it doesn't have the element we are currently working on, the skip this one
				if (!m.hasElement(e.getElement())) continue;
				// The result term. The factor is the amount of atoms in that molecule
				Term resultTerm = new Term(i, m.getCoefficient() * m.getElementFactor(e.getElement()));
				// The terms for the equation
				List<Term> terms = new ArrayList<Term>();
				// Go through all the molecules on the right side, if it has the material add it and divide by the result term's factor so,
				// the result term's factor is 1
				for (int j = 0; j < formula.getRightTerm().size(); j++) {
					Molecule rm = formula.getRightTerm().get(j);
					if (rm.hasElement(e.getElement()))
						terms.add(new Term(formula.getLeftTerm().size() + j, rm.getCoefficient() * rm.getElementFactor(e.getElement()) / resultTerm.getFactor()));
				}
				// Do the same thing for the left term, but we want to subtract so we multiply by -1
				for (int k = 0; k < formula.getLeftTerm().size(); k++) {
					Molecule lm = formula.getLeftTerm().get(k);
					if (lm.hasElement(e.getElement()) && k != i)
						terms.add(new Term(k, lm.getCoefficient() * lm.getElementFactor(e.getElement()) * -1 / resultTerm.getFactor()));
				}
				// Set the result term to 1. We need to do this since we divided every other term by the factor
				resultTerm.setFactor(1);
				// Save the equation
				Equation eq = new Equation(resultTerm, terms);
				Logger.log(e.getElement() + " : " + eq.toString(), 0);
				boolean shouldAdd = true;
				for (Equation equation : equations) {
					if (equation.equals(eq)) {
						shouldAdd = false;
						break;
					}
				}
				if (shouldAdd) equations.add(eq);
			}

			// Do the exact same thing as we did but with the sides switched
			for (int i = 0; i < formula.getRightTerm().size(); i++) {
				Molecule m = formula.getRightTerm().get(i);
				if (!m.hasElement(e.getElement())) continue;
				Term resultTerm = new Term(formula.getLeftTerm().size() + i, m.getCoefficient() * m.getElementFactor(e.getElement()));
				List<Term> terms = new ArrayList<Term>();
				for (int j = 0; j < formula.getLeftTerm().size(); j++) {
					Molecule lm = formula.getLeftTerm().get(j);
					if (lm.hasElement(e.getElement()))
						terms.add(new Term(j, lm.getCoefficient() * lm.getElementFactor(e.getElement()) / resultTerm.getFactor()));
				}
				for (int k = 0; k < formula.getRightTerm().size(); k++) {
					Molecule rm = formula.getRightTerm().get(k);
					if (rm.hasElement(e.getElement()) && k != i)
						terms.add(new Term(formula.getLeftTerm().size() + k, rm.getCoefficient() * rm.getElementFactor(e.getElement()) * -1
								/ resultTerm.getFactor()));
				}
				resultTerm.setFactor(1);
				Equation eq = new Equation(resultTerm, terms);
				Logger.log(e.getElement() + " : " + eq.toString(), 0);
				boolean shouldAdd = true;
				for (Equation equation : equations) {
					if (equation.equals(eq)) {
						shouldAdd = false;
						break;
					}
				}
				if (shouldAdd) equations.add(eq);
			}
		}

		// Print out the equations but flattened
		// When the equations where printed out earlier,
		// it was for every element, this is everything
		Logger.log("\n=========FLATTENED==========", 0);
		for (Equation eq : equations)
			Logger.log(eq.toString(), 0);
		Logger.log("\n", 0);

		// Save the characters we now
		Map<Character, Double> solvedTerms = new HashMap<Character, Double>();
		// If this is true, then we haven't set an equation to 1 meaning that we don't
		// have a starting point for the solving.
		boolean firstEq = true;
		// Loop through all the equations.
		// Once an equation has been solved, 'i' is put back to -1.
		// This is done so that past equations that couldn't be solved
		// because of that one term can be solved.
		// It's -1 since after the loop it adds 1 to it and we want it to be 0.
		// Also added a label to the for loop, this makes it so I can use break or continue
		// that would then affect this loop instead of the innermost loop
		equationsLoop: for (int i = 0; i < equations.size(); i++) {
			// Get the current equation we are working on
			Equation eq = equations.get(i);

			// If the current term has been solved already, just skip it
			if (solvedTerms.containsKey(eq.getResultTerm().getName())) continue;

			// Check if we haven't solved any terms and if the current equation got only one term, set it to 1.0
			if (firstEq && eq.getTerms().size() == 1) {
				// Save the current term
				solvedTerms.put(eq.getResultTerm().getName(), 1.0D);
				// Set it to false so that it doesn't run this part anymore
				// and actually starts to solve it.
				firstEq = false;
				// Rewind the loop
				i = -1;
				// Print out debug info
				Logger.log("Solved " + eq.getResultTerm().getName() + " = 1.0", 0);

				// Continue so it starts back at the top
				continue;
			}

			// Loop trough all the terms and see if a term hasn't been solved yet
			// If this is so, then skip it.
			// Here I use the label of the for loop
			for (Term term : eq.getTerms())
				if (!solvedTerms.containsKey(term.getName())) continue equationsLoop;

			// The variable to save the result in
			double result = 0;

			// Loop through all the terms and add it's answer multiplied by the term's factor
			for (Term term : eq.getTerms()) {
				result += term.getFactor() * solvedTerms.get(term.getName()).doubleValue();
			}

			// Print out debug info
			Logger.log("Solved " + eq.getResultTerm().getName() + " = " + result, 0);

			// Save it
			solvedTerms.put(eq.getResultTerm().getName(), result);

			// Rewind
			i = -1;

		}

		// Currently we got decimal numbers
		// We want whole numbers. The way to do it is by finding
		// the greatest common divider and dividing it by that.
		// We are multiplying it. We calculate the multiply factor
		// by having 1 divided by the greatest common divider.
		// We start by having a list of the factors
		double[] factors = new double[solvedTerms.size()];
		// The current index we are on in the list
		int ii = 0;
		// We characters we solved, later used.
		List<Character> keyOrder = new ArrayList<Character>();
		// For all the solved terms
		for (Entry<Character, Double> entry : solvedTerms.entrySet()) {
			// Save the factor to the list and increase the index
			factors[ii++] = entry.getValue();
			// Add the term name to the keyOrder list
			keyOrder.add(entry.getKey());
		}
		// Calculate the greatest common divider
		double gcd = 1.0 / MathUtil.getGCD(factors);
		// Print it out
		Logger.log("\nFound GCD: " + gcd, 0);
		// Loop through the keys and multiply the factor value by the greatest common divider
		for (Character c : keyOrder) {
			solvedTerms.put(c, solvedTerms.get(c) * gcd);
		}
		Logger.log("\nSolution: ", 0);
		// Get the amount of terms we have, this is so that we can print the solution out
		int amtTerms = formula.getLeftTerm().size() + formula.getRightTerm().size();
		// Loop through all the term names
		// Term names are made by having a start name that is equal to 'A'
		// and adding i to it.
		for (int i = 0; i < amtTerms; i++) {
			// Get the term name
			char c = (char) (Term.startName + i);
			// Check if we solved it, if yes, print it out. If we didn't solve it, print a question mark.
			if (solvedTerms.containsKey(c)) 
				Logger.log(c + " = " + solvedTerms.get(c).intValue(), 0);
			else 
				Logger.log(c + " = ?", 0);
			
		}
		
		Formula result = new Formula(formula.getLeftTerm(), formula.getRightTerm(), formula.getElements());
		for(int i = 0; i < result.getLeftTerm().size(); i++) {
			char c = (char) (Term.startName + i);
			if(solvedTerms.containsKey(c))
				result.getLeftTerm().get(i).setCoefficient(solvedTerms.get(c));
		}
		for(int i = 0; i < result.getRightTerm().size(); i++) {
			char c = (char) (Term.startName + i + result.getLeftTerm().size());
			if(solvedTerms.containsKey(c))
				result.getLeftTerm().get(i).setCoefficient(solvedTerms.get(c));
		}
		return result;
	}

}
