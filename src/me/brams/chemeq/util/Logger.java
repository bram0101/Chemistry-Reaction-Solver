/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq.util;

/**
 * This class is to log everything
 * 
 * @author Bram
 *
 */
public class Logger {

	public static final int LEVEL_DEBUG = 0;
	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_WARNING = 2;

	public static int ignoreLevel = 1;
	public static boolean showName = false;

	public static void log(String log, int level) {
		// This is a 'cheat' to get the caller's name.
		// It's deprecated but I still use it.
		@SuppressWarnings("deprecation")
		String caller = sun.reflect.Reflection.getCallerClass(1).getName();
		if (level < ignoreLevel) return;
		if (showName) {
			if (level == LEVEL_DEBUG)
				System.out.println("[DEBUG: " + caller + "] " + log);
			else if (level == LEVEL_INFO)
				System.out.println("[INFO: " + caller + "] " + log);
			else if (level == LEVEL_WARNING) System.out.println("[WARNING: " + caller + "] " + log);
		} else {
			System.out.println(log);
		}
	}

}
