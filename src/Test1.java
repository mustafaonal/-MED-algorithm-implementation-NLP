import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.TextArea;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
public class Test1 extends JFrame{
	private JTextField textField;
	private JTextField textField1;
	
	public Test1(int num) {
		if (num == 1) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 800, 436);
			getContentPane().setLayout(null);

			textField = new JTextField();
			textField.setBounds(84, 57, 393, 19);
			getContentPane().add(textField);
			textField.setColumns(10);

			TextArea textArea = new TextArea();
			textArea.setBounds(10, 133, 766, 256);
			getContentPane().add(textArea);

			Button button = new Button("Search");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						long startTime = System.nanoTime();
						textArea.setText(MED(textField.getText()));
						long endTime   = System.nanoTime();
						long totalTime = endTime - startTime;
						System.out.println(totalTime/1000000 + " millisecond");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			});
			button.setBounds(573, 57, 66, 21);
			getContentPane().add(button);
		} else if (num == 2) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 800, 436);
			getContentPane().setLayout(null);

			textField = new JTextField();
			textField.setBounds(84, 57, 393, 19);
			getContentPane().add(textField);
			textField.setColumns(10);

			textField1 = new JTextField();
			textField1.setBounds(84, 80, 393, 19);
			getContentPane().add(textField1);
			textField1.setColumns(10);

			TextArea textArea = new TextArea();
			textArea.setBounds(10, 133, 766, 256);
			getContentPane().add(textArea);

			Button button = new Button("Find MED Value");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					long startTime = System.nanoTime();
					textArea.setText(Med_Value_Between_Two_Words(textField.getText(), textField1.getText()));
					long endTime   = System.nanoTime();
					long totalTime = endTime - startTime;
					System.out.println(totalTime/1000000 + " millisecond");
				}
			});
			button.setBounds(573, 57, 90, 21);
			getContentPane().add(button);
		}

	}
	
	private static String MED(String input) throws FileNotFoundException {
		String outStr = "";
		String[] tempWordArr = new String[5];
		int[] tempCountArr = new int[5];
		countingArray[] output = new countingArray[5];

		String content = new Scanner(new File("sozluk.txt")).useDelimiter("\\Z").next();
		String[] dictionary = content.split("[\r\n]+");

		for (int i = 0; i < dictionary.length; i++) {

			int[][] medMatrix = new int[input.length() + 1][dictionary[i].length() + 1];
			String compare = dictionary[i];
			char[] inputWord = new char[input.length() + 1];
			char[] compareWord = new char[compare.length() + 1];
			inputWord[0] = ' ';
			compareWord[0] = ' ';
			int tempLeftUpperCorner = 0;
			int tempUpperCorner = 0;
			int tempLeftCorner = 0;

			for (int j = 1; j <= medMatrix.length; j++) { // Set first column with row count
				medMatrix[j - 1][0] = j - 1;
				if (j != medMatrix.length) {
					inputWord[j] = input.charAt(j - 1);
				}
			}

			for (int j = 1; j <= medMatrix[0].length; j++) { // Set first row with column count
				medMatrix[0][j - 1] = j - 1;
				if (j != medMatrix[0].length) {
					compareWord[j] = compare.charAt(j - 1);
				}
			}

			for (int y = 1; y < medMatrix.length; y++) {
				for (int x = 1; x < medMatrix[0].length; x++) {
					if (inputWord[y] == compareWord[x]) {
						medMatrix[y][x] = medMatrix[y - 1][x - 1];
					} else {
						tempLeftCorner = medMatrix[y][x - 1] + 1;
						tempLeftUpperCorner = medMatrix[y - 1][x - 1] + 1;
						tempUpperCorner = medMatrix[y - 1][x] + 1;

						if (tempLeftCorner <= tempLeftUpperCorner) {
							if (tempLeftCorner <= tempUpperCorner) {
								medMatrix[y][x] = tempLeftCorner;
							} else {
								medMatrix[y][x] = tempUpperCorner;
							}
						} else if (tempLeftUpperCorner <= tempLeftCorner) {
							if (tempLeftUpperCorner <= tempUpperCorner) {
								medMatrix[y][x] = tempLeftUpperCorner;
							} else {
								medMatrix[y][x] = tempUpperCorner;
							}
						}
					}
				}

			}
			int med = medMatrix[medMatrix.length - 1][medMatrix[0].length - 1];
			if (i < 5) {
				tempWordArr[i] = dictionary[i];
				tempCountArr[i] = med;
			} else {
				if (i == 5) {
					output = sortFirstFive(tempCountArr, tempWordArr, medMatrix);
				} else if (med < output[4].getCount()) {
					output = pushNewWord(med, dictionary[i], output, medMatrix);
				}
			}

		}

		for (int i = 0; i < output.length; i++) {

			outStr = outStr + (i + 1) + ". Word: " + output[i].getWord() + " - Med Count: " + output[i].getCount()
					+ "\n------------- \n";
			for (int j = 0; j < output[i].getMedMatrix().length; j++) {
				for (int j2 = 0; j2 < output[i].getMedMatrix()[0].length; j2++) {
					outStr = outStr + output[i].getMedMatrix()[j][j2] + " ";
				}
				outStr = outStr + "\n";
			}
			outStr = outStr + "-------------\n";
		}
		return outStr;
	}

	private static String Med_Value_Between_Two_Words(String input, String input1) {
		String outStr = "";
		String[] tempWordArr = new String[5];
		int[] tempCountArr = new int[5];
		countingArray[] output = new countingArray[5];

		int[][] medMatrix = new int[input.length() + 1][input1.length() + 1];
		String compare = input1;
		char[] inputWord = new char[input.length() + 1];
		char[] compareWord = new char[compare.length() + 1];
		inputWord[0] = ' ';
		compareWord[0] = ' ';
		int tempLeftUpperCorner = 0;
		int tempUpperCorner = 0;
		int tempLeftCorner = 0;

		for (int j = 1; j <= medMatrix.length; j++) { // Set first column with row count
			medMatrix[j - 1][0] = j - 1;
			if (j != medMatrix.length) {
				inputWord[j] = input.charAt(j - 1);
			}
		}

		for (int j = 1; j <= medMatrix[0].length; j++) { // Set first row with column count
			medMatrix[0][j - 1] = j - 1;
			if (j != medMatrix[0].length) {
				compareWord[j] = compare.charAt(j - 1);
			}
		}

		for (int y = 1; y < medMatrix.length; y++) {
			for (int x = 1; x < medMatrix[0].length; x++) {
				if (inputWord[y] == compareWord[x]) {
					medMatrix[y][x] = medMatrix[y - 1][x - 1];
				} else {
					tempLeftCorner = medMatrix[y][x - 1] + 1;
					tempLeftUpperCorner = medMatrix[y - 1][x - 1] + 1;
					tempUpperCorner = medMatrix[y - 1][x] + 1;

					if (tempLeftCorner <= tempLeftUpperCorner) {
						if (tempLeftCorner <= tempUpperCorner) {
							medMatrix[y][x] = tempLeftCorner;
						} else {
							medMatrix[y][x] = tempUpperCorner;
						}
					} else if (tempLeftUpperCorner <= tempLeftCorner) {
						if (tempLeftUpperCorner <= tempUpperCorner) {
							medMatrix[y][x] = tempLeftUpperCorner;
						} else {
							medMatrix[y][x] = tempUpperCorner;
						}
					}
				}
			}

		}
		int med = medMatrix[medMatrix.length - 1][medMatrix[0].length - 1];

		outStr = ". Words: " + input + " - " + input1 + " - Med Count: " + med + "\n------------- \n";
		for (int j = 0; j < medMatrix.length; j++) {
			for (int j2 = 0; j2 < medMatrix[0].length; j2++) {
				outStr = outStr + medMatrix[j][j2] + " ";
			}
			outStr = outStr + "\n";
		}
		outStr = outStr + "-------------\n";
		return outStr;
	}

	private static countingArray[] sortFirstFive(int[] countArr, String[] wordArr, int[][] medMatrix) {
		int tempNum = 0;
		String tempWord = "";
		countingArray[] returnArr = new countingArray[5];
		for (int m = 0; m < countArr.length - 1; m++) {
			for (int n = m + 1; n < countArr.length; n++) {
				if (countArr[m] > countArr[n]) {
					// Count sort
					tempNum = countArr[m];
					countArr[m] = countArr[n];
					countArr[n] = tempNum;
					// Word sort
					tempWord = wordArr[m];
					wordArr[m] = wordArr[n];
					wordArr[n] = tempWord;
				}
			}

		}
		for (int i = 0; i < returnArr.length; i++) {
			returnArr[i] = new countingArray(wordArr[i], countArr[i], medMatrix);

		}
		return returnArr;
	}

	private static countingArray[] pushNewWord(int newCount, String newWord, countingArray[] output,
			int[][] medMatrix) {
		countingArray temp = null;
		for (int i = 0; i < output.length; i++) {
			if (newCount < output[i].getCount()) {
				for (int j = i; j < output.length; j++) {
					if (j == i) {
						temp = output[i];
						output[i] = new countingArray(newWord, newCount, medMatrix);
					} else {
						output[j] = temp;
						if (j + 1 != output.length) {
							temp = output[j + 1];
						}
					}
				}
				break;
			}
		}

		return output;
	}
	

}
