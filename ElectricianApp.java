
import java.util.regex.Pattern;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.*;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * Electrician Toolkit application
 *
 * @author RyanS
 *
 */
public class ElectricianApp extends Application {

	public void start(Stage primaryStage) {
		VBox mainBox = new VBox(5);
		mainBox.setAlignment(Pos.CENTER);

		Label mainLabel = new Label("Electrical Resources:");
		mainLabel.setId("MainLabel");
		VBox labelBox = new VBox(mainLabel);
		labelBox.setAlignment(Pos.CENTER);

		DropDown favoritesDrop = new DropDown("Favorites");

		DropDown notesDrop = new DropDown("Notes");

		DropDown ohmsDrop = new DropDown("Ohm's Law");

		DropDown circuitsDrop = new DropDown("Circuits");

		Button estimateDrop = new Button("Estimate");
		estimateDrop.setPrefSize(400, 60);
		estimateDrop.setOnAction(e -> {
			EstimateTool.display();
		});
		DropDown calcDrop = new DropDown("Calculator");
		calcDrop.add(new CalculatorTool());
		//calcDrop.add(new CalculatorTool());
		//calcDrop.add(new CalculatorTool());

		mainBox.getChildren().addAll(labelBox, favoritesDrop, notesDrop, ohmsDrop, circuitsDrop, estimateDrop,
				calcDrop);

		for (int i = 0; i < mainBox.getChildren().size(); i++) {
			VBox.setMargin(mainBox.getChildren().get(i), new Insets(0, 40, 0, 40));
		}

		VBox outerWrap = new VBox(mainBox);
		VBox.setMargin(mainBox, new Insets(60, 60, 60, 60));
		outerWrap.setAlignment(Pos.CENTER);

		ScrollPane mainScroll = new ScrollPane(outerWrap);
		outerWrap.translateXProperty().bind(mainScroll.widthProperty().subtract(outerWrap.widthProperty()).divide(2));

		mainScroll.setMaxHeight(600);
		mainScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

		Scene mainScene = new Scene(mainScroll);

		try {
			URL styleURL = getClass().getResource("toolkit_style.css");
			String stylePath = styleURL.toExternalForm();

			mainScene.getStylesheets().add(stylePath);
		} catch (Exception ex) {
			System.out.println("Stylesheet error");
		}

		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Electrician Toolkit");
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Running");
		Application.launch(args);
		System.out.println("Finished");
	}

}

/*
 *
 * Electrician Tools:
 *
 *
 */

class DropDown extends Region {

	VBox outerBox;
	Button button;
	VBox dropBox;

	public DropDown(String name) {
		// this.setAlignment(Pos.CENTER);
		this.setId("DropDown");

		outerBox = new VBox();
		this.getChildren().add(outerBox);

		dropBox = new VBox();
		dropBox.setId("DropBox");
		VBox.setMargin(dropBox, new Insets(0, 0, 0, 20));

		button = new Button(name);
		button.setId("DropButton");
		button.setPrefSize(400, 60);

		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (outerBox.getChildren().contains(dropBox))
					outerBox.getChildren().remove(dropBox);
				else
					outerBox.getChildren().add(dropBox);
			}
		});

		outerBox.getChildren().add(button);
	}

	public VBox getDropBox() {
		return dropBox;
	}

	public void add(Node node) {
		dropBox.getChildren().add(node);
		node.setId("DropItem");
		// VBox.setMargin(node, new Insets(10,10,10,10));
	}

	public void remove(Node node) {
		dropBox.getChildren().remove(node);
	}
}

abstract interface Tool {
	public String printResult();
}

class CalculatorTool extends Pane implements Tool {

	TextField input;
	Label result;
	Button enter;

	public CalculatorTool() {
		Label equals = new Label(" = ");
		equals.setId("EqualsLabel");
		input = new TextField();
		result = new Label();
		enter = new Button("Enter");

		enter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					result.setText(Calculator.getResult(input.getText()));
				} catch (Exception ex) {
					result.setText("ERROR");
				}
			}
		});
		;

		HBox inputBox = new HBox(input, equals, result);
		VBox outerBox = new VBox(inputBox, enter);
		VBox.setMargin(inputBox, new Insets(10, 10, 5, 10));
		VBox.setMargin(enter, new Insets(5, 10, 10, 10));

		this.getChildren().add(outerBox);
	}

	public String printResult() {
		return "";
	}

}

/*
 *
 * Calculator Code:
 *
 *
 */

// Calculator, alternate GUI

/*
 * public class CalculatorGUI2 extends Application {
 * 
 * public void start(Stage primaryStage) { File f = new File("calcStyle.css");
 * 
 * Label inputLabel = new Label("Function: "); TextField inputText = new
 * TextField(); inputText.setPrefWidth(300); Button enter = new Button("Enter");
 * Label previous = new Label("Previous: ");
 * 
 * ScrollPane ansScroll = new ScrollPane(); ansScroll.setContent(previous);
 * ansScroll.setHmax(300); ansScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
 * 
 * enter.setOnAction(new EventHandler<ActionEvent>() {
 * 
 * public void handle(ActionEvent e) { String function = inputText.getText();
 * String answer = Calculator.getResult(function);
 * previous.setText("Previous: "+function+" = "+answer);
 * inputText.setText(answer);
 * 
 * ansScroll.setContent(previous); }
 * 
 * });
 * 
 * HBox textPane = new HBox(); textPane.getChildren().addAll(inputLabel,
 * inputText); //textPane.setAlignment(Pos.CENTER);
 * 
 * VBox inputPane = new VBox(); inputPane.getChildren().addAll(textPane, enter,
 * previous); inputPane.setAlignment(Pos.CENTER); VBox.setMargin(textPane, new
 * Insets(50,20,10,20)); VBox.setMargin(enter, new Insets(10,20,20,20));
 * VBox.setMargin(previous, new Insets(10,20,20,20));
 * 
 * Scene inputScene = new Scene(inputPane); //File f = new
 * File("calcStyle.css"); inputScene.getStylesheets().clear();
 * inputScene.getStylesheets().add("file:///" +
 * f.getAbsolutePath().replace("\\", "/"));
 * 
 * 
 * primaryStage.setTitle("Calculator"); primaryStage.setScene(inputScene);
 * primaryStage.show(); }
 * 
 * public static void main(String[] args) { /*try {
 * System.out.println(Calculator.calculate("5+1"));
 * System.out.println(Calculator.calcGroups("5+1"));
 * System.out.println(Calculator.fixGroups("(1/2)")); } catch(Exception ex) {
 * 
 * } Application.launch(args); }
 * 
 * }
 */

// ===========================================================================

class Calculator {

	final static String operands = Pattern.quote("+-*/");
	final static String notStartChar = Pattern.quote("*/");
	final static String multDiv = Pattern.quote("*/");
	final static String addSub = Pattern.quote("+-");
	final static String decimal = Pattern.quote(".");
	final static String paren = Pattern.quote("()");
	final static String parenOpen = Pattern.quote("(");
	final static String parenClose = Pattern.quote(")");
	final static String power = Pattern.quote("^");
	final static String space = Pattern.quote(" ");

	public static boolean isValid(String s) {
		if (s == null)
			return false;

		s = s.replaceAll(" ", "");

		if (s.equals(""))
			return false;
		if (s.length() < 1)
			return false;
		if (s.substring(0, 1).matches("[" + power + "]") || s.substring(s.length() - 1).matches("[^0-9" + paren + "]"))
			return false;

		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i + 1).matches("[^0-9" + operands + paren + power + space + decimal + "]")) {
				System.out.println("not valid: " + s.charAt(i));
				return false;
			}
		}

		boolean ret = true;
		/*
		 * int prev = 0; // 0 for num, 1 for operand int curr = 1; for(int i=0;
		 * i<s.length(); i++) { if(!s.substring(i, i+1).matches("[0-9"+operands+"]"))
		 * return false;
		 * 
		 * if(i>0) { if(s.substring(i, i+1).matches("["+operands+"]")) curr = 1; else
		 * curr = 0;
		 * 
		 * if(curr==1 && prev==1) return false;
		 * 
		 * prev = curr; } }
		 */

		return ret;
	}

	public static double calculate(String s) throws Exception {
		if (!isValid(s))
			throw new Exception();

		// System.out.println("String: " + s);

		// s = format(s);
		s = fixPosNegAdd(s);
		s = fixPosNegMult(s);
		s = fixPosNegAdd(s);
		s = fixPowers(s);

		// System.out.println("String: " +s);

		boolean startsNeg = false;
		if (s.charAt(0) == '-') {
			startsNeg = true;
			s = s.substring(1);
		} else if (s.charAt(0) == '+')
			s = s.substring(1);

		// System.out.println("String: " +s);

		double[] toSum = null;
		String[] splitByAdd = s.split("[" + addSub + "]");
		toSum = new double[splitByAdd.length];

		for (int j = 0; j < splitByAdd.length; j++) {
			String[] splitByMult = splitByAdd[j].split("[" + multDiv + "]");
			String multOper = splitByAdd[j].replaceAll("[^" + multDiv + "]", "");

			// System.out.println(splitByAdd[j]);
			// System.out.println(splitByMult[0]);

			// System.out.println(splitByAdd[j]);
			if (splitByMult.length == 1) {
				toSum[j] = Double.parseDouble(splitByMult[0]);
				continue;
			}

			if (splitByMult[0].equals(""))
				toSum[j] = Double.parseDouble(splitByAdd[j]);
			else
				toSum[j] = Double.parseDouble(splitByMult[0]);

			for (int k = 1; k < splitByMult.length && multOper.length() > 0; k++) {
				if (multOper.charAt(0) == '/')
					toSum[j] /= Double.parseDouble(splitByMult[k]);
				else
					toSum[j] *= Double.parseDouble(splitByMult[k]);

				multOper = multOper.substring(1);
			}
		}

		if (startsNeg)
			toSum[0] *= -1;

		// System.out.println(toSum[1]);
		String addOper = s.replaceAll("[^" + addSub + "]", "");
		double total = toSum[0];
		for (int i = 1; i < toSum.length && addOper.length() > 0; i++) {
			if (addOper.charAt(0) == '+')
				total += toSum[i];
			else
				total -= toSum[i];

			addOper = addOper.substring(1);
		}

		return total;
	}

	public static String fixPosNegMult(String s) {
		if (!isValid(s))
			return "error";

		s = s.replaceAll(" ", "");
		for (int i = 1; i < s.length() - 1; i++) {
			if (s.substring(i, i + 1).matches("[" + multDiv + "]")
					&& s.substring(i + 1, i + 2).matches("[" + addSub + "]")) {
				double n1 = 0;
				double n2 = 0;

				if (i <= 0)
					i = 1;

				int min = i - 1;
				for (int j = min; j >= 0; j--) {
					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					min = j;
					// System.out.println("loop");
				}
				String n1String = s.substring(min, i);
				n1 = Double.parseDouble(n1String);
				// System.out.println(n1);

				int max = i + 3;
				for (int j = max; j < s.length(); j++) {
					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					max = j + 1;
					// System.out.println("loop");
				}
				String n2String = s.substring(i + 2, max);
				n2 = Double.parseDouble(n2String);
				// System.out.println(n2);

				double res = 0;
				if (s.charAt(i) == '*') {
					if (s.charAt(i + 1) == '+') {
						res = n1 * n2;
					} else {
						res = -1 * n1 * n2;
					}
				} else {
					if (s.charAt(i + 1) == '+') {
						res = n1 / n2;
					} else {
						res = -1 * n1 / n2;
					}
				}

				// System.out.println("Res: "+res);

				String resString = Double.toString(res);

				int left = n1String.length();
				int right = n2String.length();

				s = s.substring(0, i - left) + resString + s.substring(i + 2 + right);

				i = 0;
			}
		}

		// System.out.println("FixNeg"+s);
		return s;
	}

	public static String fixPosNegAdd(String s) {
		if (!isValid(s))
			return "error";

		s = s.replaceAll(" ", "");
		for (int i = 0; i < s.length() - 1; i++) {
			boolean condition1 = s.charAt(i) == '+' && s.charAt(i + 1) == '-';
			boolean condition2 = s.charAt(i) == '-' && s.charAt(i + 1) == '+';
			boolean condition3 = s.charAt(i) == '+' && s.charAt(i + 1) == '+';
			boolean condition4 = s.charAt(i) == '-' && s.charAt(i + 1) == '-';
			if (condition1 || condition2) {
				s = s.substring(0, i) + '-' + s.substring(i + 2);
				i = -1;
			} else if (condition3 || condition4) {
				s = s.substring(0, i) + '+' + s.substring(i + 2);
				i = -1;
			}
		}

		return s;
	}

	public static String fixPowers1(String s) {
		if (!isValid(s))
			return "error";

		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == '^' && (s.charAt(i + 1) == '-' || s.charAt(i + 1) == '+') && i < s.length()) {
				double n1 = 0;
				double n2 = 0;

				if (i <= 0)
					i = 1;

				int min = i - 1;
				for (int j = min; j >= 0; j--) {
					/*
					 * String test1 = "[0-9"+decimal+parenOpen+"]"; String test2 =
					 * "[0-9"+decimal+parenClose+"]"; String testString = test2;
					 * 
					 * if(s.charAt(j)==')') testString = test1;
					 */

					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					min = j;
					// System.out.println("loop");
				}
				String n1String = s.substring(min, i);
				n1 = Double.parseDouble(n1String);
				// System.out.println(n1);

				int max = i + 3;
				for (int j = max; j < s.length(); j++) {
					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					max = j + 1;
					// System.out.println("loop");
				}
				String n2String = s.substring(i + 2, max);
				n2 = Double.parseDouble(n2String);
				// System.out.println(n2);

				double res = 0;
				if (s.charAt(i + 1) == '+') {
					res = Math.pow(n1, n2);
				} else {
					res = Math.pow(n1, -1 * n2);
				}

				// System.out.println("Res: "+res);

				String resString = Double.toString(res);

				int left = n1String.length();
				int right = n2String.length();

				s = s.substring(0, i - left) + resString + s.substring(i + 2 + right);

				i = 0;
			} else if (s.charAt(i) == '^') {
				double n1 = 0;
				double n2 = 0;

				if (i <= 0)
					i = 1;

				int min = i - 1;
				for (int j = min; j >= 0; j--) {
					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					min = j;
					// System.out.println("loop");
				}
				String n1String = s.substring(min, i);
				n1 = Double.parseDouble(n1String);
				// System.out.println(n1);

				int max = i + 1;
				for (int j = max; j < s.length(); j++) {
					if (!s.substring(j, j + 1).matches("[0-9" + decimal + "]"))
						break;

					max = j + 1;
					// System.out.println("loop");
				}
				String n2String = s.substring(i + 1, max);
				n2 = Double.parseDouble(n2String);
				// System.out.println(n2);

				double res = Math.pow(n1, n2);

				// System.out.println("Res: "+res);

				String resString = Double.toString(res);

				int left = n1String.length();
				int right = n2String.length();

				s = s.substring(0, i - left) + resString + s.substring(i + 1 + right);

				i = 0;
			}
		}

		return s;
	}

	public static String fixPowers(String s) {
		if (!isValid(s))
			return "error";

		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == '^' && (s.charAt(i + 1) == '-' || s.charAt(i + 1) == '+') && i < s.length()) {
				double n1 = 0;
				double n2 = 0;

				if (i <= 0)
					i = 1;

				int min = i - 1;
				String testString = "[0-9" + decimal + paren + "]";
				int parenBal = 0;
				boolean parenExists = false;
				for (int j = min; j >= 0; j--) {
					String test1 = "[0-9" + decimal + paren + "]";
					String test2 = "[0-9" + decimal + paren + operands + "]";
					// String testString = test1;
					// int parenBal = 0;

					if (s.charAt(j) == ')') {
						// testString = test1;
						parenBal++;
						testString = test2;
						parenExists = true;
					}

					if (s.charAt(j) == '(' && parenBal != 0) {
						// testString = test1;
						parenBal--;
						// testString=test1;
					} else if (s.charAt(j) == '(' && parenBal == 0) {
						min = j + 1;
						break;
					}

					/*
					 * if(parenBal==0) testString=test1; else testString=test2;
					 */

					if (parenBal == 0 && parenExists) {
						min = j;
						break;
					}

					if (!s.substring(j, j + 1).matches(testString) && parenBal == 0)
						break;

					min = j;
					// System.out.println("loop "+parenBal+s.substring(min, i));
				}

				String n1Initial = s.substring(min, i);
				String n1String = getResult(n1Initial);
				n1 = Double.parseDouble(n1String);
				// System.out.println(n1);

				int max = i + 2;
				testString = "[0-9" + decimal + paren + "]";
				parenBal = 0;
				parenExists = false;
				for (int j = max; j < s.length(); j++) {
					String test1 = "[0-9" + decimal + paren + "]";
					String test2 = "[0-9" + decimal + paren + operands + "]";
					// String testString = test1;
					// int parenBal = 0;

					if (s.charAt(j) == '(') {
						// testString = test1;
						parenBal--;
						testString = test2;
						parenExists = true;
					}

					if (s.charAt(j) == ')' && parenBal != 0) {
						// testString = test1;
						parenBal++;
						// testString=test1;
					} else if (s.charAt(j) == ')' && parenBal == 0) {
						max = j;
						break;
					}

					/*
					 * if(parenBal==0) testString=test1; else testString=test2;
					 */

					if (parenBal == 0 && parenExists) {
						max = j + 1;
						break;
					}

					if (!s.substring(j, j + 1).matches(testString) && parenBal == 0)
						break;

					max = j + 1;
					// System.out.println("loop "+parenBal+" "+s.substring(i+2, max));
				}

				String n2Initial = s.substring(i + 2, max);
				String n2String = getResult(n2Initial);
				n2 = Double.parseDouble(n2String);
				// System.out.println(n2);

				double res = 0;
				if (s.charAt(i + 1) == '+') {
					res = Math.pow(n1, n2);
				} else {
					res = Math.pow(n1, -1 * n2);
				}

				// System.out.println("Res: "+res);

				String resString = Double.toString(res);

				int left = n1Initial.length();
				int right = n2Initial.length();

				s = s.substring(0, i - left) + resString + s.substring(i + 2 + right);

				i = 0;
			} else if (s.charAt(i) == '^') {
				double n1 = 0;
				double n2 = 0;

				if (i <= 0)
					i = 1;

				int min = i - 1;
				String testString = "[0-9" + decimal + paren + "]";
				int parenBal = 0;
				boolean parenExists = false;
				for (int j = min; j >= 0; j--) {
					String test1 = "[0-9" + decimal + paren + "]";
					String test2 = "[0-9" + decimal + paren + operands + "]";
					// String testString = test1;
					// int parenBal = 0;

					if (s.charAt(j) == ')') {
						// testString = test1;
						parenBal++;
						testString = test2;
						parenExists = true;
					}

					if (s.charAt(j) == '(' && parenBal != 0) {
						// testString = test1;
						parenBal--;
						// testString=test1;
					} else if (s.charAt(j) == '(' && parenBal == 0) {
						min = j + 1;
						break;
					}

					/*
					 * if(parenBal==0) testString=test1; else testString=test2;
					 */

					if (parenBal == 0 && parenExists) {
						min = j;
						break;
					}

					if (!s.substring(j, j + 1).matches(testString))
						break;

					min = j;
					// System.out.println("loop "+parenBal+s.substring(min, i));
				}

				String n1Initial = s.substring(min, i);
				String n1String = getResult(n1Initial);
				n1 = Double.parseDouble(n1String);
				// System.out.println(n1);

				int max = i + 1;
				testString = "[0-9" + decimal + paren + "]";
				parenBal = 0;
				parenExists = false;
				for (int j = max; j < s.length(); j++) {
					String test1 = "[0-9" + decimal + paren + "]";
					String test2 = "[0-9" + decimal + paren + operands + "]";
					// String testString = test1;
					// int parenBal = 0;

					if (s.charAt(j) == '(') {
						// testString = test1;
						parenBal--;
						testString = test2;
						parenExists = true;
					}

					if (s.charAt(j) == ')' && parenBal != 0) {
						// testString = test1;
						parenBal++;
						// testString=test1;
					} else if (s.charAt(j) == ')' && parenBal == 0) {
						max = j;
						break;
					}

					/*
					 * if(parenBal==0) testString=test1; else testString=test2;
					 */

					if (parenBal == 0 && parenExists) {
						max = j + 1;
						break;
					}

					if (!s.substring(j, j + 1).matches(testString) && parenBal == 0)
						break;

					max = j + 1;
					// System.out.println("loop "+parenBal+s.substring(i+1, max));
				}

				String n2Initial = s.substring(i + 1, max);
				String n2String = getResult(n2Initial);
				n2 = Double.parseDouble(n2String);
				// System.out.println(n2);

				double res = Math.pow(n1, n2);

				// System.out.println("Res: "+res+" "+n1+" "+n2);

				String resString = Double.toString(res);

				int left = n1Initial.length();
				int right = n2Initial.length();

				s = s.substring(0, i - left) + resString + s.substring(i + right + 1);

				i = 0;
			}
		}

		return s;
	}

	public static boolean validGroups(String s) {
		s = s.replaceAll("[^" + paren + "]", "");

		int lastOpen = 0;
		boolean closeExists = false;
		for (int i = 0; i < s.length(); i++) {
			closeExists = false;
			s = s.replaceAll("[" + Pattern.quote("*") + "]", "");
			if ((lastOpen = s.lastIndexOf('(')) == -1)
				return true;
			// System.out.println("current: "+s+lastOpen);

			if (s.charAt(lastOpen + 1) == ')') {
				closeExists = true;
				s = s.substring(0, lastOpen) + "*" + s.substring(lastOpen + 1);
				s = s.substring(0, lastOpen + 1) + "*" + s.substring(lastOpen + 2);
				i = -1;
				// System.out.println("exists");
			}

			if (closeExists == false)
				return false;
		}

		return true;
	}

	public static String fixGroups(String s) throws Exception {
		/*
		 * if(!validGroups(s)) throw new Exception();
		 */

		int lastOpen = 0;
		int nextClose = 0;
		for (int i = 0; i < s.length(); i++) {
			lastOpen = s.lastIndexOf('(');
			nextClose = s.indexOf(')', lastOpen);
			if (lastOpen == -1 && nextClose == -1) {
				// return Double.toString(calculate(s));
				return s;
			}

			// System.out.println(s);
			double res = calculate(s.substring(lastOpen + 1, nextClose));
			String resString = Double.toString(res);

			s = s.substring(0, lastOpen) + resString + s.substring(nextClose + 1);
			i = -1;
		}

		return s;
	}

	public static double calcGroups(String s) throws Exception {
		if (!validGroups(s))
			throw new Exception();

		int lastOpen = 0;
		int nextClose = 0;
		for (int i = 0; i < s.length(); i++) {
			lastOpen = s.lastIndexOf('(');
			nextClose = s.indexOf(')', lastOpen);
			if (lastOpen == -1 && nextClose == -1) {
				return calculate(s);
			}

			double res = calculate(s.substring(lastOpen + 1, nextClose));
			String resString = Double.toString(res);

			s = s.substring(0, lastOpen) + resString + s.substring(nextClose + 1);
			i = -1;
		}

		return Double.MAX_VALUE;
	}

	public static String fixParenMult(String s) {
		for (int i = 0; i < s.length() - 1; i++) {
			if (s.charAt(i) == ')' && s.charAt(i + 1) == '(') {
				s = s.substring(0, i + 1) + "*" + s.substring(i + 1);
				i = -1;
			} else if (s.substring(i, i + 1).matches("[0-9]") && s.charAt(i + 1) == '(') {
				s = s.substring(0, i + 1) + "*" + s.substring(i + 1);
				i = -1;
			} else if (s.charAt(i) == ')' && s.substring(i + 1, i + 2).matches("[0-9]")) {
				s = s.substring(0, i + 1) + "*" + s.substring(i + 1);
				i = -1;
			}
		}

		return s;
	}

	public static void displayResult(String s) throws Exception {
		String res = "";
		s = s.replaceAll(" ", "");
		String fixed;
		fixed = fixPosNegMult(s);
		fixed = fixPosNegAdd(fixed);

		if (isValid(s)) {
			res = Double.toString(calculate(s));
		}

		if (s.contentEquals(fixed))
			System.out.println(s + " = " + res);
		else if (res.equals(fixed))
			System.out.println(s + " = " + fixed);
		else
			System.out.println(s + " = " + fixed + " = " + res);
	}

	public static String format(String s) throws Exception {
		s = s.replaceAll(" ", "");
		s = s.replaceAll("x", "*");
		s = fixParenMult(s);
		s = fixPosNegAdd(s);
		s = fixPowers(s);
		s = fixGroups(s);
		// s = fixPowers(s);
		s = fixPosNegMult(s);
		// s = fixParenMult(s);
		// s = fixPowers(s);

		return s;
	}

	public static String getResult(String s) {
		try {
			s = format(s);
			if (!isValid(s))
				throw new Exception();
			else
				return Double.toString(calcGroups(s));
		} catch (Exception ex) {
			// ex.printStackTrace();
			return "error";
		}
	}

}
