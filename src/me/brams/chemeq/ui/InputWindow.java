/**Copyright (c) 2016 Bram Stout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

package me.brams.chemeq.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import me.brams.chemeq.formula.Formula;
import me.brams.chemeq.formula.FormulaBuilder;
import me.brams.chemeq.solver.Solver;

public class InputWindow {

	JFrame window;

	public InputWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		window = new JFrame("ChemEq");

		JTextPane inputPane = new JTextPane();
		//inputPane.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
		inputPane.setPreferredSize(new Dimension(400, 24));

		JTextPane resPane = new JTextPane();
		resPane.setEditable(false);
		resPane.setPreferredSize(new Dimension(400, 300));

		inputPane.addKeyListener(new KeyListener() {

			private char lastChar = ' ';
			private boolean small = false;

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				StyledDocument doc = inputPane.getStyledDocument();
				if (Character.isDigit(e.getKeyChar()) && !small && Character.isAlphabetic(lastChar)) {
					SimpleAttributeSet keyWord = new SimpleAttributeSet();
					StyleConstants.setSubscript(keyWord, true);
					try {
						doc.insertString(doc.getLength(), e.getKeyChar() + "", keyWord);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					e.consume();
					small = true;
				}
				if (!Character.isDigit(e.getKeyChar()) && small) {
					SimpleAttributeSet keyWord = new SimpleAttributeSet();
					StyleConstants.setSubscript(keyWord, false);
					try {
						doc.insertString(doc.getLength(), e.getKeyChar() + "", keyWord);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					e.consume();
					small = false;
				}
				/*if (e.getKeyChar() == '\b') {
					if (inputPane.getCaretPosition() > 0) {
						try {
							doc.remove(inputPane.getCaretPosition() - 1, 1);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					e.setKeyChar((char) 0);
					e.consume();
				}*/
				if (e.getKeyChar() == '\r' || e.getKeyChar() == '\n') {
					e.consume();
					try {
						Formula f = FormulaBuilder.getFormulaFromString(inputPane.getText());
						System.out.println(f.toString() + "\n");
						Formula solved = Solver.solve(f);
						System.out.println(solved.toString());
						
						
						String s = solved.getSolvedEquation();
						char lastC = ' ';
						boolean sm = false;
						StyledDocument doc2 = resPane.getStyledDocument();
						for (int i = 0; i < s.length(); i++) {
							char c = s.charAt(i);
							if (Character.isDigit(c) && !sm && Character.isAlphabetic(lastC)) {
								SimpleAttributeSet keyWord = new SimpleAttributeSet();
								StyleConstants.setSubscript(keyWord, true);
								try {
									doc2.insertString(doc2.getLength(), c + "", keyWord);
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
								sm = true;
							} else if (!Character.isDigit(c) && sm) {
								SimpleAttributeSet keyWord = new SimpleAttributeSet();
								StyleConstants.setSubscript(keyWord, false);
								try {
									doc2.insertString(doc2.getLength(), c + "", keyWord);
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
								sm = false;
							} else {
								doc2.insertString(doc2.getLength(), c + "", null);
							}
							lastC = c;
						}
						doc2.insertString(doc2.getLength(), "\n", null);
					} catch (Exception e1) {
						e1.printStackTrace();
						try {
							StyledDocument doc2 = resPane.getStyledDocument();
							doc2.insertString(doc2.getLength(), "Error!", null);
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					inputPane.setText("");
				}

				lastChar = e.getKeyChar();
			}

		});

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

		window.setLayout(new BorderLayout());

		window.add(inputPane, BorderLayout.SOUTH);
		window.add(separator, BorderLayout.CENTER);
		window.add(resPane, BorderLayout.NORTH);

		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.show();
	}

}