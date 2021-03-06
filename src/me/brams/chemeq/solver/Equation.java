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

import java.util.List;

public class Equation {
	
	/**The end for this equation*/
	private Term resultTerm;
	/**The list of terms in the equation*/
	private List<Term> terms;
	
	/**
	 * Constructor
	 * @param resultTerm
	 * @param terms
	 */
	public Equation(Term resultTerm, List<Term> terms) {
		this.resultTerm = resultTerm;
		this.terms = terms;
	}
	
	/**
	 * Getter for result term
	 * @return
	 */
	public Term getResultTerm() {
		return resultTerm;
	}
	
	/**
	 * Getter for terms
	 * @return
	 */
	public List<Term> getTerms(){
		return terms;
	}
	
	/**
	 * Checks if the equation needs a specific term
	 * @param termOffset
	 * @return
	 */
	public boolean needsTerm(int termOffset) {
		for(Term t : terms) {
			if(t.getName() == (Term.startName + termOffset))
				return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the equation is the name as the eq variable
	 */
	@Override
	public boolean equals(Object eq) {
		if(!(eq instanceof Equation))
			return false;
		
		if(!((Equation) eq).resultTerm.equals(resultTerm))
			return false;
		
		if(((Equation) eq).terms.size() != terms.size())
			return false;
		
		for(int i = 0; i < terms.size(); i++) {
			if(!((Equation)eq).terms.get(i).equals(terms.get(i)))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Convert the data this class holds to a string
	 */
	public String toString() {
		String string = "Equation: { " + resultTerm.toString() + " = ";
		for(Term t : terms) {
			string = string + t.toString() + " + ";
		}
		string = string.substring(0, string.length() - 2) + "}";
		return string;
	}
	
}
