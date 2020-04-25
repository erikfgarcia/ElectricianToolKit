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
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CircuitsTool extends Tool {

    String name = "Circuits";
    TextField scInput1;
    TextField scInput2;
    TextField srInput1;
    TextField srInput2;
    TextField pcInput1;
    TextField pcInput2;
    TextField prInput1;
    TextField prInput2;

    Button calc1;
    Button calc2;
    Button calc3;
    Button calc4;
    Button print1;
    Button print2;
    Button print3;
    Button print4;

    Label series;
    Label parallel;

    Label scLabel;
    Label srLabel;
    Label pcLabel;
    Label prLabel;
    Label scResult;
    Label srResult;
    Label pcResult;
    Label prResult;

    String scSave = "";
    String srSave = "";
    String pcSave = "";
    String prSave = "";

    public CircuitsTool(UIManager ui) {
        super(ui);
        scInput1 = new TextField();
        scInput2 = new TextField();
        srInput1 = new TextField();
        srInput2 = new TextField();
        pcInput1 = new TextField();
        pcInput2 = new TextField();
        prInput1 = new TextField();
        prInput2 = new TextField();
        series = new Label("Series");
        series.setTextFill(Color.web("#CD5C5C"));
        scLabel = new Label("Capacitance: Enter two capacitors.");
        srLabel = new Label("Resistance/Inductance: Enter two resistors or inductors.");
        parallel = new Label("Parallel");
        parallel.setTextFill(Color.web("#CD5C5C"));
        pcLabel = new Label("Capacitance: Enter two capacitors.");
        prLabel = new Label("Resistance/Inductance: Enter two resistors or inductors.");
        scResult = new Label();
        scResult.setTextFill(Color.web("#0076a3"));
        srResult = new Label();
        srResult.setTextFill(Color.web("#0076a3"));
        pcResult = new Label();
        pcResult.setTextFill(Color.web("#0076a3"));
        prResult = new Label();
        prResult.setTextFill(Color.web("#0076a3"));

        calc1 = new Button("Calculate");
        calc1.setOnAction(e -> {
            if ((scInput1.getText().trim().isEmpty()) || (scInput2.getText().trim().isEmpty())) {
                scResult.setText("Error! Must enter two values.");
            }
            else if (!(isNum(scInput1.getText()) && isNum(scInput2.getText()))) {
                scResult.setText("Error! Symbols were entered.");
            }
            else if ((Double.parseDouble(scInput1.getText()) == 0.0) || (Double.parseDouble(scInput2.getText()) == 0.0)){
                scResult.setText("Error! Zeros were entered.");
            }
            else {
                double input1 = Double.parseDouble(scInput1.getText());
                double input2 = Double.parseDouble(scInput2.getText());
                scResult.setText("Capacitance = " + Double.toString(seriesCapacitance(input1,input2)));
                scSave = scResult.getText();
            }
        });

        calc2 = new Button("Calculate");
        calc2.setOnAction(e -> {
            if ((srInput1.getText().trim().isEmpty()) || (srInput2.getText().trim().isEmpty())) {
                srResult.setText("Error! Must enter two values.");
            }
            else if (!(isNum(srInput1.getText()) && isNum(srInput2.getText()))) {
                srResult.setText("Error! Symbols were entered.");
            }
            else if ((Double.parseDouble(srInput1.getText()) == 0.0) || (Double.parseDouble(srInput2.getText()) == 0.0)){
                srResult.setText("Error! Zeros were entered.");
            }
            else {
                double input1 = Double.parseDouble(srInput1.getText());
                double input2 = Double.parseDouble(srInput2.getText());
                srResult.setText("Resistance/Inductance = " + Double.toString(seriesResistance(input1,input2)));
                srSave = srResult.getText();
            }
        });

        calc3 = new Button("Calculate");
        calc3.setOnAction(e -> {
            if ((pcInput1.getText().trim().isEmpty()) || (pcInput2.getText().trim().isEmpty())) {
                pcResult.setText("Error! Must enter two values.");
            }
            else if (!(isNum(pcInput1.getText()) && isNum(pcInput2.getText()))) {
                pcResult.setText("Error! Symbols were entered.");
            }
            else if ((Double.parseDouble(pcInput1.getText()) == 0.0) || (Double.parseDouble(pcInput2.getText()) == 0.0)){
                pcResult.setText("Error! Zeros were entered.");
            }
            else {
                double input1 = Double.parseDouble(pcInput1.getText());
                double input2 = Double.parseDouble(pcInput2.getText());
                pcResult.setText("Capacitance = " + Double.toString(parallelCapacitance(input1,input2)));
                pcSave = pcResult.getText();
            }
        });

        calc4 = new Button("Calculate");
        calc4.setOnAction(e -> {
            if ((prInput1.getText().trim().isEmpty()) || (prInput2.getText().trim().isEmpty())) {
                prResult.setText("Error! Must enter two values.");
            }
            else if (!(isNum(prInput1.getText()) && isNum(prInput2.getText()))) {
                prResult.setText("Error! Symbols were entered.");
            }
            else if ((Double.parseDouble(prInput1.getText()) == 0.0) || (Double.parseDouble(prInput2.getText()) == 0.0)){
                prResult.setText("Error! Zeros were entered.");
            }
            else {
                double input1 = Double.parseDouble(prInput1.getText());
                double input2 = Double.parseDouble(prInput2.getText());
                prResult.setText("Resistance/Inductance = " + Double.toString(parallelResistance(input1,input2)));
                prSave = prResult.getText();
            }
        });

        print1 = new Button("Print");
        print1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });

        print2 = new Button("Print");
        print2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });

        print3 = new Button("Print");
        print3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });

        print4 = new Button("Print");
        print4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });

        HBox boxOne = new HBox(scInput1, scInput2, calc1, print1);
        boxOne.setSpacing(3);

        HBox boxTwo = new HBox(srInput1, srInput2, calc2, print2);
        boxTwo.setSpacing(3);

        HBox boxThree = new HBox(pcInput1, pcInput2, calc3, print3);
        boxThree.setSpacing(3);

        HBox boxFour = new HBox(prInput1, prInput2, calc4, print4);
        boxFour.setSpacing(3);

        VBox outerBox = new VBox(series, scLabel, boxOne, scResult, srLabel, boxTwo, srResult, parallel, pcLabel, boxThree, pcResult, prLabel, boxFour, prResult);
        outerBox.setSpacing(5);

        VBox outerWrap = new VBox(outerBox);
        VBox.setMargin(outerBox, new Insets(10, 10, 10, 10));
        this.getChildren().add(outerWrap);
    }
    public String getToolName() {
        return name;
    }

    public String printResult() {
        if (scSave.length() != 0) {
            String s = scSave;
            scSave = "";
            return s;
        }
        else if (srSave.length() != 0) {
            String s = srSave;
            srSave = "";
            return s;
        }
        else if (pcSave.length() != 0) {
            String s = pcSave;
            pcSave = "";
            return s;
        }
        else if (prSave.length() != 0) {
            String s = prSave;
            prSave = "";
            return s;

        }
        else {
            return "";
        }
    }

    public void clearDisplay() {
    }
    public static double seriesCapacitance(double c1, double c2) {
        double capacitance = 1/c1 + 1/c2;
        capacitance = 1 / capacitance;
        return capacitance;
    }
    public static double seriesResistance(double r1, double r2) {
        //equivalent equation to series inductance;
        double resistance = r1 + r2;
        return resistance;
    }
    public static double parallelCapacitance(double c1, double c2) {
        double capacitance = c1 + c2;
        return capacitance;
    }
    public static double parallelResistance(double r1, double r2) {
        //equivalent equation to parallel inductance;
        double resistance = 1/r1 + 1/r2;
        resistance = 1 / resistance;
        return resistance;
    }
    private static boolean isNum(String number) {
        try {
            double num = Double.parseDouble(number);
            if (num < 0 || num > 999999) {
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
