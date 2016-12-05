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
		List<Element> elements = new ArrayList<Element>();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isAlphabetic(c)) {
				if (Character.isUpperCase(c)) {
					String elementName = c + "";
					if (i < input.length() - 1) {
						char nc = input.charAt(++i);
						while (Character.isLowerCase(nc)) {
							elementName = elementName + nc;
							if (i >= input.length() - 1) break;
							nc = input.charAt(++i);
						}
					}
					boolean exists = false;
					for (Element e : elements) {
						if (elementName.equals(e.getElement())) exists = true;
					}
					if (exists) continue;
					int factor = 1;
					if (i < input.length() - 1) {
						String factorS = "";
						char nc = input.charAt(i++);
						while (Character.isLowerCase(c)) {
							factorS = factorS + nc;
							if (i >= input.length() - 1) break;
							nc = input.charAt(i++);
						}
						if (!factorS.isEmpty()) factor = Integer.parseInt(factorS);
					}
					elements.add(new Element(elementName, factor));
				}
			}
		}

		List<Molecule> leftTerm = new ArrayList<Molecule>();
		List<Molecule> rightTerm = new ArrayList<Molecule>();
		boolean rightTermActive = false;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '-' && i < input.length() + 1 && input.charAt(i + 1) == '>') rightTermActive = true;

			if (Character.isDigit(c) || c == '(') {
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
				if (input.charAt(i) == '(') {
					c = input.charAt(i);
					List<Element> molElements = new ArrayList<Element>();
					while (input.charAt(++i) != ')') {
						c = input.charAt(i);
						if (Character.isUpperCase(c)) {
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
								i--;
							}
							molElements.add(new Element(elementName, factor));
						}
						if (i >= input.length() - 1) break;
					}
					if (rightTermActive)
						rightTerm.add(new Molecule(molElements, coefficient));
					else 
						leftTerm.add(new Molecule(molElements, coefficient));
				}
			}
		}
		return new Formula(leftTerm, rightTerm, elements);
	}

}
