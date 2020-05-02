
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OhmsTool extends Tool {

    String name = "Ohm's Law";
    ArrayList<Double> enteredNums;
    ArrayList<Double> newNums;
    TextField vInput;
    TextField cInput;
    TextField rInput;
    TextField pInput;
    Label vLabel;
    Label cLabel;
    Label rLabel;
    Label pLabel;
    Label vResult;
    Label cResult;
    Label rResult;
    Label pResult;
    Button calc;
    Button print;
    int blankCount;

    public OhmsTool(UIManager ui) {
            super(ui);
            if (ui != null) {
                vInput = new TextField();
                cInput = new TextField();
                rInput = new TextField();
                pInput = new TextField();

                vInput.insertText(0, "");
                cInput.insertText(0, "");
                rInput.insertText(0, "");
                pInput.insertText(0, "");
                Label directions = new Label("Enter 2 or 3 values.");

                Label vLabel = new Label("Voltage");
                Label cLabel = new Label("Current");
                Label rLabel = new Label("Resistance");
                Label pLabel = new Label("Power");

                vResult = new Label();
                cResult = new Label();
                rResult = new Label();
                pResult = new Label();
                blankCount = 0;


                calc = new Button("Calculate");
                calc.setOnAction(e -> {
                    blankCount = 0;
                    directions.setText("Enter 2 or 3 values.");
                    enteredNums = new ArrayList<Double>();
                    if (vInput.getText().trim().isEmpty()) {
                        enteredNums.add(0.0);
                        blankCount += 1;
                    } else if (!(isNum(vInput.getText()))) {
                        directions.setText("Error! Only numerical values (excluding zero) can be entered.");
                    } else {
                        enteredNums.add(Double.parseDouble(vInput.getText()));
                    }
                    if (cInput.getText().trim().isEmpty()) {
                        enteredNums.add(0.0);
                        blankCount += 1;
                    } else if (!(isNum(cInput.getText()))) {
                        directions.setText("Error! Only numerical values (excluding zero) can be entered.");
                    } else {
                        enteredNums.add(Double.parseDouble(cInput.getText()));
                    }
                    if (rInput.getText().trim().isEmpty()) {
                        enteredNums.add(0.0);
                        blankCount += 1;
                    } else if (!(isNum(rInput.getText()))) {
                        directions.setText("Error! Only numerical values (excluding zero) can be entered.");
                    } else {
                        enteredNums.add(Double.parseDouble(rInput.getText()));
                    }
                    if (pInput.getText().trim().isEmpty()) {
                        enteredNums.add(0.0);
                        blankCount += 1;
                    } else if (!(isNum(pInput.getText()))) {
                        directions.setText("Error! Only numerical values (excluding zero) can be entered.");
                    } else {
                        enteredNums.add(Double.parseDouble(pInput.getText()));
                    }

                    newNums = new ArrayList<Double>();
                    if (directions.getText() != "Error! Only numerical values (excluding zero) can be entered.") {
                        try {
                            if (blankCount == 4) {
                                directions.setText("Error! No values were entered.");
                            } else {
                                newNums = OhmsLawCalculator(enteredNums.get(0), enteredNums.get(1), enteredNums.get(2), enteredNums.get(3));
                                directions.setText("Resulting Values: ");
                                vResult.setText("= " + Double.toString(newNums.get(0)));
                                cResult.setText("= " + Double.toString(newNums.get(1)));
                                rResult.setText("= " + Double.toString(newNums.get(2)));
                                pResult.setText("= " + Double.toString(newNums.get(3)));
                            }
                        } catch (Exception ex) {
                            directions.setText("Error! One or four values were entered. Try again.");
                        }
                    }
                });


                print = new Button("Print");
                print.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        printToHistory();
                    }
                });
                ;

                HBox boxOne = new HBox(vLabel, vInput, vResult);
                boxOne.setSpacing(5);

                HBox boxTwo = new HBox(cLabel, cInput, cResult);
                boxTwo.setSpacing(5);

                HBox boxThree = new HBox(rLabel, rInput, rResult);
                boxThree.setSpacing(5);

                HBox boxFour = new HBox(pLabel, pInput, pResult);
                boxFour.setSpacing(5);

                HBox buttons = new HBox(calc, print);
                buttons.setSpacing(5);

                VBox outerBox = new VBox(directions, boxOne, boxTwo, boxThree, boxFour, buttons);
                outerBox.setSpacing(5);

                VBox outerWrap = new VBox(outerBox);
                VBox.setMargin(outerBox, new Insets(10, 10, 10, 10));

                this.getChildren().add(outerWrap);
            }
    }
    public String getToolName() {
        return name;
    }

    public String printResult() {
        return "voltage " + vResult.getText() + " current " + cResult.getText() + " resistance " + rResult.getText() + " power " + pResult.getText();
    }

    public void clearDisplay() {
        vInput.clear();
        cInput.clear();
        rInput.clear();
        pInput.clear();
        vResult.setText("");
        cResult.setText("");
        rResult.setText("");
        pResult.setText("");
    }
    public static ArrayList<Double> OhmsLawCalculator(double voltage, double current, double resistance, double power) throws Exception {
        ArrayList<Double> countNums = new ArrayList<Double>();
        //used for storing the passed in values
        ArrayList<Double> remValues = new ArrayList<Double>();
        //the list which will contain the values that the user is looking for
        int count = 0;
        //used to count how many inputs the user gave in order to check if the minimum of 2 is met
        countNums.add(voltage); //index 0 will be voltage
        countNums.add(current); //index 1 will be current
        countNums.add(resistance); //index 2 will be resistance
        countNums.add(power); //index 3 will be power
        for (int i = 0; i < countNums.size(); ++i) {
            if (countNums.get(i) != 0) {
                count += 1;
            }
        }
        if (count < 2) {
            //error, need to enter atleast 2 values
            throw new Exception();
        }
        else if (count == 3) { //checks if only 1 value is missing
            if (voltage == 0) {
                countNums.set(0, calculateVoltage(current,resistance));
            }
            else if (current == 0) {
                countNums.set(1, calculateCurrent(voltage,resistance));
            }
            else if (resistance == 0) {
                countNums.set(2, calculateResistance(voltage,current));
            }
            else if (power == 0) {
                countNums.set(3, calculatePower(current,voltage));
            }

        }
        else if (count == 4) { //checks all values entered for correctness
            throw new Exception();
        }
        else if (count == 2) {
            /* Case 1 , Voltage & Current are given */
            if (countNums.get(0) != 0 && countNums.get(1) != 0) {
                countNums.set(2, calculateResistance(voltage,current));
                countNums.set(3, calculatePower(current,voltage));
            }
            /* Case 2 , Voltage & Resistance are given */
            else if (countNums.get(0) != 0 && countNums.get(2) != 0) {
                double newCurrent = calculateCurrent(voltage,resistance);
                countNums.set(1, newCurrent);
                countNums.set(3, calculatePower(newCurrent,voltage));
            }
            /* Case 3 , Voltage & Power are given */
            else if (countNums.get(0) != 0 && countNums.get(3) != 0) {
                double newCurrent = calculateCurrent2(voltage,power);
                countNums.set(1, newCurrent);
                countNums.set(2, calculateResistance(voltage,newCurrent));
            }
            /* Case 4 , Current & Resistance are given */
            else if (countNums.get(1) != 0 && countNums.get(2) != 0) {
                double newVoltage = calculateVoltage(current,resistance);
                countNums.set(0, newVoltage);
                countNums.set(3, calculatePower(current,newVoltage));
            }
            /* Case 5, Current & Power are given */
            else if (countNums.get(1) != 0 && countNums.get(3) != 0) {
                double newVoltage = calculateVoltage2(power,current);
                countNums.set(0, newVoltage);
                countNums.set(2, calculateResistance(newVoltage,current));
            }
            /* Case 6, Resistance & Power are given */
            else if (countNums.get(2) != 0 && countNums.get(3) != 0) {
                double newVoltage = calculateVoltage3(power,resistance);
                countNums.set(0, newVoltage);
                countNums.set(1, calculateCurrent(newVoltage,resistance));
            }
        }
        return countNums;
    }
    public static double calculateVoltage(double current, double resistance) {
        double voltage = 0;
        voltage = current * resistance;
        return voltage;
    }
    /* For Case 5 */
    public static double calculateVoltage2(double power, double current) {
        double voltage = 0;
        voltage = power / current;
        return voltage;
    }
    /* For Case 6 */
    public static double calculateVoltage3(double power, double resistance) {
        double voltage = 0;
        voltage = Math.abs(Math.sqrt(power * resistance));
        return voltage;
    }
    public static double calculateCurrent(double voltage, double resistance) {
        double current = 0;
        current = voltage / resistance;
        return current;
    }
    /* For Case 4 */
    public static double calculateCurrent2(double voltage, double power) {
        double current = 0;
        current = power / voltage;
        return current;
    }
    public static double calculateResistance(double voltage, double current) {
        double resistance = 0;
        resistance = voltage / current;
        return resistance;
    }
    public static double calculatePower(double current, double voltage) {
        double power = 0;
        power = current * voltage;
        return power;
    }
    private static boolean isNum(String number) {
        try {
            double num = Double.parseDouble(number);
            if (num <= 0.0 || num > 999999.0) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
