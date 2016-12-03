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

/**This is a term of the equation.
 * It holds the types of elements and the amount of them.
 * @author Bram
 *
 */
public class Molecule {
	
	private double coefficient;
	private List<Element> elements;
	
	public Molecule(List<Element> elements, double coefficient) {
		this.elements = elements;
		this.coefficient = coefficient;
	}
	
	public List<Element> getElements(){
		return elements;
	}
	
	public boolean hasElement(String element) {
		for(Element e : elements)
			if(e.getElement().equals(element))
				return true;
		return false;
	}
	
	public int getElementFactor(String element) {
		for(Element e : elements)
			if(e.getElement().equals(element))
				return e.getFactor();
		return 0;
	}
	
	public Element getElement(String element) {
		for(Element e : elements)
			if(e.getElement().equals(element))
				return e;
		return null;
	}
	
	public double getCoefficient() {
		return coefficient;
	}
	
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
}
