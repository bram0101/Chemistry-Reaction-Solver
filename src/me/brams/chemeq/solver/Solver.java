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

import me.brams.chemeq.formula.Element;
import me.brams.chemeq.formula.Formula;
import me.brams.chemeq.formula.Molecule;


/**This is the class that will solve the equation*/
public class Solver {
	
	/**
	 * Solve the formula and save the correct coefficients in the same Formula instance.
	 * The equation is made by adding all the atoms on the other side and subtracting
	 * the atoms on the current side
	 * @param formula
	 */
	public static void solve(Formula formula) {
		//Save the different equations to solve it per element for now
		Map<String, List<Equation>> equations = new HashMap<String, List<Equation>>();
		
		//Loop through ever element
		for(Element e : formula.getElements()) {
			//Create a list for the equations, if a list was already saved, then use that one.
			List<Equation> eqs = new ArrayList<Equation>();
			if(equations.containsKey(e.getElement()))
				eqs = equations.get(e.getElement());
			
			//Go through every molecule on the left term
			for(int i = 0; i < formula.getLeftTerm().size(); i++) {
				Molecule m = formula.getLeftTerm().get(i);
				//If it doesn't have the element we are currently working on, the skip this one
				if(!m.hasElement(e.getElement())) 
					continue;
				//The result term. The factor is the amount of atoms in that molecule
				Term resultTerm = new Term(i, m.getCoefficient() * m.getElementFactor(e.getElement()));
				//The terms for the equation
				List<Term> terms = new ArrayList<Term>();
				//Go through all the molecules on the right side, if it has the material add it and divide by the result term's factor so,
				//the result term's factor is 1
				for(int j = 0; j < formula.getRightTerm().size(); j++) {
					Molecule rm = formula.getRightTerm().get(j);
					if(rm.hasElement(e.getElement()))
						terms.add(new Term(formula.getLeftTerm().size() + j, rm.getCoefficient() * rm.getElementFactor(e.getElement()) / resultTerm.getFactor()));
				}
				//Do the same thing for the left term, but we want to subtract so we multiply by -1
				for(int k = 0; k < formula.getLeftTerm().size(); k++) {
					Molecule lm = formula.getLeftTerm().get(k);
					if(lm.hasElement(e.getElement()) && k != i)
						terms.add(new Term(k, lm.getCoefficient() * lm.getElementFactor(e.getElement()) * -1 / resultTerm.getFactor()));
				}
				//Set the result term to 1. We need to do this since we divided every other term by the factor
				resultTerm.setFactor(1);
				//Save the equation
				Equation eq = new Equation(resultTerm, terms);
				System.out.println(e.getElement() + " : " + eq.toString());
				eqs.add(eq);
			}
			
			//Do the exact same thing as we did but with the sides switched
			for(int i = 0; i < formula.getRightTerm().size(); i++) {
				Molecule m = formula.getRightTerm().get(i);
				if(!m.hasElement(e.getElement())) 
					continue;
				Term resultTerm = new Term(formula.getLeftTerm().size() + i, m.getCoefficient() * m.getElementFactor(e.getElement()));
				List<Term> terms = new ArrayList<Term>();
				for(int j = 0; j < formula.getLeftTerm().size(); j++) {
					Molecule lm = formula.getLeftTerm().get(j);
					if(lm.hasElement(e.getElement()))
						terms.add(new Term(j, lm.getCoefficient() * lm.getElementFactor(e.getElement()) / resultTerm.getFactor()));
				}
				for(int k = 0; k < formula.getRightTerm().size(); k++) {
					Molecule rm = formula.getRightTerm().get(k);
					if(rm.hasElement(e.getElement()) && k != i)
						terms.add(new Term(formula.getLeftTerm().size() + k, rm.getCoefficient() * rm.getElementFactor(e.getElement()) * -1 / resultTerm.getFactor()));
				}
				resultTerm.setFactor(1);
				Equation eq = new Equation(resultTerm, terms);
				System.out.println(e.getElement() + " : " + eq.toString());
				eqs.add(eq);
			}
			
			//Save the list
			equations.put(e.getElement(), eqs);
		}
	}
	
}
