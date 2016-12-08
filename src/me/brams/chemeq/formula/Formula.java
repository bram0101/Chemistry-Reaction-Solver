/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq.formula;

import java.util.List;

/**
 * This holds the formula.
 * @author Bram
 *
 */
public class Formula {
	
	/**Holds the molecules on the left side*/
	private List<Molecule> leftTerm;
	/**Holds the molecules on the right side*/
	private List<Molecule> rightTerm;
	/**Holds all the elements in it*/
	private List<Element> elements;
	
	/**
	 * Constructor
	 * @param leftTerm
	 * @param rightTerm
	 * @param elements
	 */
	public Formula(List<Molecule> leftTerm, List<Molecule> rightTerm, List<Element> elements) {
		this.leftTerm = leftTerm;
		this.rightTerm = rightTerm;
		this.elements = elements;
	}

	/**
	 * Getter for left term
	 * @return
	 */
	public List<Molecule> getLeftTerm() {
		return leftTerm;
	}

	/**
	 * Getter for right term
	 * @return
	 */
	public List<Molecule> getRightTerm() {
		return rightTerm;
	}

	/**
	 * Getter for elements
	 * @return
	 */
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 * Convert the data this class holds to a string
	 */
	public String toString() {
		String string = "Formula: { Equation: { ";
		for(Molecule m : leftTerm) {
			string = string + m.toString() + " + ";
		}
		string = string.substring(0, string.length() - 2) + "-> ";
		for(Molecule m : rightTerm) {
			string = string + m.toString() + " + ";
		}
		string = string.substring(0, string.length() - 2) + "}, Elements: { ";
		for(Element e : elements) {
			string = string + e.toString() + ", ";
		}
		string = string.substring(0, string.length() - 2) + " } }";
		return string;
	}
	
	public String getSolvedEquation() {
		String string = "";
		for(Molecule m : leftTerm) {
			string = string + m.toSolveString() + " + ";
		}
		string = string.substring(0, string.length() - 2) + "-> ";
		for(Molecule m : rightTerm) {
			string = string + m.toSolveString() + " + ";
		}
		string = string.substring(0, string.length() - 3);
		return string;
	}
	
}
