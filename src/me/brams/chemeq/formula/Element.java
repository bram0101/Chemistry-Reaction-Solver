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

/**
 * This class holds an element.
 * @author Bram
 *
 */
public class Element {
	
	/**Amount of atoms in this element*/
	private int factor;
	/**The name of the element*/
	private String element;
	
	/**
	 * Constructor
	 * @param element
	 * @param factor
	 */
	public Element(String element, int factor) {
		this.element = element;
		this.factor = factor;
	}
	
	/**
	 * Getter for element
	 * @return
	 */
	public String getElement() {
		return element;
	}
	
	/**
	 * Getter for factor
	 * @return
	 */
	public int getFactor() {
		return factor;
	}
	
	/**
	 * Setter for factor
	 * @param factor
	 */
	public void setFactor(int factor) {
		this.factor = factor;
	}
	
	/**
	 * Convert the data this class holds to a string
	 */
	public String toString() {
		if(factor == 1) {
			return element;
		}else {
			return element + factor;
		}
	}
	
}
