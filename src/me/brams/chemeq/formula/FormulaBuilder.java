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

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a Formula instance from a string
 * @author Bram
 *
 */
public class FormulaBuilder {

	/**
	 * Get the formula from the string.
	 * It parses the string searching for queues on that a molecule starts and reads it's content.
	 * @param input
	 * @return
	 */
	public static Formula getFormulaFromString(String input) {
		//The list of elements
		List<Element> elements = new ArrayList<Element>();
		//Loop through all the characters, if it starts with a upper case then save the element
		for (int i = 0; i < input.length(); i++) {
			//Get the char
			char c = input.charAt(i);
			//Check if it is alphabetic and upper case
			if (Character.isAlphabetic(c)) {
				if (Character.isUpperCase(c)) {
					//Get the full name by adding the next character until it's not lower case anymore
					String elementName = c + "";
					if (i < input.length() - 1) {
						char nc = input.charAt(++i);
						while (true) {
							if(!Character.isLowerCase(nc) || !Character.isAlphabetic(nc)) {
								break;
							}
							elementName = elementName + nc;
							if (i >= input.length() - 1) break;
							nc = input.charAt(++i);
						}
					}
					//Check if the element exists, if so, skip this
					boolean exists = false;
					for (Element e : elements) {
						if (elementName.equals(e.getElement())) exists = true;
					}
					if (exists) continue;
					//Try to get the number after it with the same logic as with the name
					int factor = 1;
					c = input.charAt(i);
					if (c >= '0' && c <= '9' && i < input.length() - 1) {
						String factorS = "";
						char nc = '\0';
						while (true) {
							if(nc == '\0') {
								nc = c;
								continue;
							}
							factorS = factorS + nc;
							if (i >= input.length() - 1) break;
							nc = input.charAt(++i);
							if(!(nc >= '0' && nc <= '9')) {
								i--;
								break;
							}
						}
						if(!factorS.isEmpty())
							factor = Integer.parseInt(factorS);
					}else {
						//Need to add this to make sure it processes the next molecule correctly
						i--;
					}
					//Save the new element
					elements.add(new Element(elementName, factor));
				}
			}
		}

		//Lists for the left term and right term
		List<Molecule> leftTerm = new ArrayList<Molecule>();
		List<Molecule> rightTerm = new ArrayList<Molecule>();
		//Check which term we are changing
		boolean rightTermActive = false;

		//Loop through all the characters
		for (int i = 0; i < input.length(); i++) {
			//Get the character
			char c = input.charAt(i);
			//If it is this '->' then change it to the right term
			if (c == '-' && i < input.length() + 1 && input.charAt(i + 1) == '>') rightTermActive = true;

			//If it stars with a number of '(' then it's a molecule
			if (Character.isDigit(c) || c == '(') {
				//Try to get the coefficient.
				//It checks if it's a number, if so keep checking the next character
				//to see if it's a number, if so then add it to the string and parse it to a double.
				double coefficient = 1;
				if (i < input.length() - 1 && Character.isDigit(c)) {
					String coefficientS = "";
					char nc = input.charAt(i);
					while (true) {
						if(!(nc >= '0' && nc <= '9')) {
							break;
						}
						coefficientS = coefficientS + nc;
						if (i >= input.length() - 1) break;
						nc = input.charAt(++i);
					}
					if (!coefficientS.isEmpty()) coefficient = Double.parseDouble(coefficientS);
				}
				//Check if it's a '(' to indicate that it's actually a molecule
				if (input.charAt(i) == '(') {
					//Get the char
					c = input.charAt(i);
					//List for the elements
					List<Element> molElements = new ArrayList<Element>();
					//While the brackets hasn't ended yet
					while (input.charAt(++i) != ')') {
						//Get the chracter
						c = input.charAt(i);
						//Check if it's upper case, if so it's a new element/
						if (Character.isUpperCase(c)) {
							//Try to get the element with the same logic as all the other ones
							String elementName = c + "";
							if (i < input.length() - 1) {
								char nc = input.charAt(++i);
								while (true) {
									if(!Character.isLowerCase(nc)) {
										break;
									}
									elementName = elementName + nc;
									if (i >= input.length() - 1) break;
									nc = input.charAt(++i);
								}
							}
							//Check if it already exists, if so, we just add the factor to that.
							Element existingEl = null;
							for(Element e : molElements) {
								if(elementName.equals(e.getElement()))
									existingEl = e;
							}
							//Try to get the factor with again the same piece of logic
							int factor = 1;
							c = input.charAt(i);
							if (c >= '0' && c <= '9' && i < input.length() - 1) {
								String factorS = "";
								char nc = '\0';
								while (true) {
									if(nc == '\0') {
										nc = c;
										continue;
									}
									factorS = factorS + nc;
									if (i >= input.length() - 1) break;
									nc = input.charAt(++i);
									if(!(nc >= '0' && nc <= '9')) {
										i--;
										break;
									}
								}
								if(!factorS.isEmpty())
									factor = Integer.parseInt(factorS);
							}else {
								//Need to add this to make sure it processes the next molecule correctly
								i--;
							}
							//If the element doesn't exists, add it, else add the factor to it.
							if(existingEl == null)
								molElements.add(new Element(elementName, factor));
							else
								existingEl.setFactor(existingEl.getFactor() + factor);
						}
						if (i >= input.length() - 1) break;
					}
					//Add it to the correct side of the equation
					if (rightTermActive)
						rightTerm.add(new Molecule(molElements, coefficient));
					else 
						leftTerm.add(new Molecule(molElements, coefficient));
				}
			}
		}
		//Return the new formula.
		return new Formula(leftTerm, rightTerm, elements);
	}

}
