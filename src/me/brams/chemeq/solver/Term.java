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

public class Term {
	
	/**The first name for a term*/
	public static final char startName = 'A';
	
	/**Name for the term*/
	private char name;
	/**The factor for the term*/
	private double factor;
	
	public Term(int nameOffset, double factor) {
		name = (char) (startName + nameOffset);
		this.factor = factor;
	}
	
	/**
	 * Getter for name
	 * @return
	 */
	public char getName() {
		return name;
	}
	
	/**
	 * Getter for factor
	 * @return
	 */
	public double getFactor() {
		return factor;
	}
	
	/**
	 * Setter for factor
	 * @param factor
	 */
	public void setFactor(double factor) {
		this.factor = factor;
	}
	
	/**
	 * This method checks if the term is the same
	 */
	@Override
	public boolean equals(Object object) {
		if(object == null)
			return false;
		if(object == this)
			return true;
		if(!(object instanceof Term))
			return false;
		if(((Term)object).name == name && ((Term)object).factor == factor)
			return true;
		else return false;
	}
	
	/**
	 * Convert the data this class holds to a string
	 */
	public String toString() {
		return Double.toString(factor) + Character.toString(name);
	}
	
}
