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

public class CircuitsTool extends Tool {

    String name = "Circuits";

    static int BWIDTH = 550;
    static int BHEIGHT = 55;
    static String STYLE = "/resources/toolkit_style.css";

    Scene seriesSelect;
    Scene parallelSelect;
    Scene sCapCalc;
    Scene sResCalc;
    Scene pCapCalc;
    Scene pResCalc;

    String sCapSave = "";
    String sResSave = "";
    String pCapSave = "";
    String pResSave = "";

    public CircuitsTool(UIManager ui) {
        super(ui);
        Label selectCircuit = new Label("Select Circuit Type.");

        Button seriesButton = new Button("Series");
        seriesButton.setPrefSize(BWIDTH, BHEIGHT);
        seriesButton.setOnAction(e -> {
            ui.setScene(seriesSelect);
        });

        Button parallelButton = new Button("Parallel");
        parallelButton.setPrefSize(BWIDTH, BHEIGHT);
        parallelButton.setOnAction(e -> {
            ui.setScene(parallelSelect);
        });
        VBox menu1 = new VBox(5);
        menu1.getStylesheets().add(STYLE);
        menu1.getChildren().addAll(selectCircuit, seriesButton, parallelButton);
        this.getChildren().add(menu1);

        //------------------------------//
        // Scene when user selects 'Series'
        //------------------------------//

        Button sCap = new Button("Calculate Capacitance");
        sCap.setPrefSize(BWIDTH, BHEIGHT);
        sCap.setOnAction(e -> {
            ui.setScene(sCapCalc);
        });

        Button sRes = new Button("Calculate Resistance/Inductance");
        sRes.setPrefSize(BWIDTH, BHEIGHT);
        sRes.setOnAction(e -> {
            ui.setScene(sResCalc);
        });
        Button back1 = new Button("Back"); //to be fixed
        back1.setPrefSize(BWIDTH, BHEIGHT);

        VBox menu2 = new VBox(20);
        menu2.getStylesheets().add(STYLE);
        menu2.getChildren().addAll(sCap, sRes, back1);
        seriesSelect = new Scene(menu2);

        //------------------------------//
        // Scene when user selects 'Parallel'
        //------------------------------//
        Button pCap = new Button("Calculate Capacitance");
        pCap.setPrefSize(BWIDTH, BHEIGHT);
        pCap.setOnAction(e -> {
            ui.setScene(pCapCalc);
        });

        Button pRes = new Button("Calculate Resistance/Inductance");
        pRes.setPrefSize(BWIDTH, BHEIGHT);
        pRes.setOnAction(e -> {
            ui.setScene(pResCalc);
        });

        Button back2 = new Button("Back"); //to be fixed
        back2.setPrefSize(BWIDTH, BHEIGHT);

        VBox menu3 = new VBox(5);
        menu3.getStylesheets().add(STYLE);
        menu3.getChildren().addAll(pCap, pRes, back2);
        parallelSelect = new Scene(menu3);

        //-------------------------------------//
        // (SERIES) Capacitance Calculation menu
        //-------------------------------------//

        TextField sC1 = new TextField();
        TextField sC2 = new TextField();
        Label sCDirections = new Label("Enter two capacitor values");
        Label c1Label = new Label("C1");
        Label c2Label = new Label("C2");
        Label sCapResult = new Label("");

        Button sCapStart = new Button("Calculate");
        sCapStart.setOnAction(e -> {
            if ((sC1.getText().trim().isEmpty()) || (sC2.getText().trim().isEmpty())) {
                sCapResult.setText("Error! Must enter a value in both boxes.");
            }
            else if (!(isNum(sC1.getText()) && isNum(sC2.getText()))) {
                sCapResult.setText("Error! Symbols were entered.");
            }
            else if ((sC1.getText() == "0") || (sC2.getText() == "0")){
                sCapResult.setText("Error! Zeros were entered");
            }
            else {
                double input1 = Double.parseDouble(sC1.getText());
                double input2 = Double.parseDouble(sC2.getText());
                sCapResult.setText("Capacitance = " + Double.toString(seriesCapacitance(input1,input2)));
                sCapSave = sCapResult.getText();
            }
        });
        Button print1 = new Button("Print");
        print1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });;

        Button back3 = new Button("Back");
        back3.setOnAction(e -> ui.setScene(seriesSelect));
        VBox calc1 = new VBox(5);
        calc1.getStylesheets().add(STYLE);
        calc1.getChildren().addAll(sCDirections, c1Label, sC1, c2Label, sC2, sCapStart, print1, back3, sCapResult);
        sCapCalc = new Scene(calc1);

        //-------------------------------------//
        // (SERIES) Resistance/Inductance Calculation menu
        //-------------------------------------//

        TextField sR1 = new TextField();
        TextField sR2 = new TextField();
        Label sRDirections = new Label("Enter two resistor or inductor values");
        Label sr1Label = new Label("R1/I1");
        Label sr2Label = new Label("R2/I2");
        Label sResResult = new Label("");

        Button sResStart = new Button("Calculate");
        sResStart.setOnAction(e -> {
            if ((sR1.getText().trim().isEmpty()) || (sR2.getText().trim().isEmpty())) {
                sCapResult.setText("Error! Must enter a value in both boxes.");
            }
            else if (!(isNum(sR1.getText()) && isNum(sR2.getText()))) {
                sCapResult.setText("Error! Symbols were entered.");
            }
            else if ((sR1.getText() == "0") || (sR2.getText() == "0")){
                sCapResult.setText("Error! Zeros were entered");
            }
            else {
                double input1 = Double.parseDouble(sR1.getText());
                double input2 = Double.parseDouble(sR2.getText());
                sResResult.setText("Resistance/Inductance = " + Double.toString(seriesResistance(input1,input2)));
            }
        });
        Button print2 = new Button("Print");
        print2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });;

        Button back4 = new Button("Back");
        back4.setOnAction(e -> ui.setScene(seriesSelect));

        VBox calc2 = new VBox(5);
        calc2.getStylesheets().add(STYLE);
        calc2.getChildren().addAll(sRDirections, sr1Label, sR1, sr2Label, sR2, sResStart, print2, back4, sResResult);
        sResCalc = new Scene(calc2);

        //-------------------------------------//
        // (PARALLEL) Capacitance Calculation menu
        //-------------------------------------//

        TextField pC1 = new TextField();
        TextField pC2 = new TextField();
        Label pCDirections = new Label("Enter two capacitor values");
        Label p1Label = new Label("C1");
        Label p2Label = new Label("C2");
        Label pCapResult = new Label("");

        Button pCapStart = new Button("Calculate");
        pCapStart.setOnAction(e -> {
            if ((pC1.getText().trim().isEmpty()) || (pC2.getText().trim().isEmpty())) {
                pCapResult.setText("Error! Must enter a value in both boxes.");
            }
            else if (!(isNum(pC1.getText()) && isNum(pC2.getText()))) {
                pCapResult.setText("Error! Symbols were entered.");
            }
            else if ((pC1.getText() == "0") || (pC2.getText() == "0")){
                pCapResult.setText("Error! Zeros were entered");
            }
            else {
                double input1 = Double.parseDouble(pC1.getText());
                double input2 = Double.parseDouble(pC2.getText());
                pCapResult.setText("Capacitance = " + Double.toString(parallelCapacitance(input1,input2)));
            }
        });
        Button print3 = new Button("Print");
        print3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });;

        Button back5 = new Button("Back");
        back5.setOnAction(e -> ui.setScene(parallelSelect));

        VBox calc3 = new VBox(5);
        calc3.getStylesheets().add(STYLE);
        calc3.getChildren().addAll(pCDirections, p1Label, pC1, p2Label, pC2, pCapStart, print3, back5, pCapResult);
        pCapCalc = new Scene(calc3);

        //-------------------------------------//
        // (PARALLEL) Resistance/Inductance Calculation menu
        //-------------------------------------//

        TextField pR1 = new TextField();
        TextField pR2 = new TextField();
        Label pRDirections = new Label("Enter two resistor or inductor values");
        Label pr1Label = new Label("R1/I1");
        Label pr2Label = new Label("R2/I2");
        Label pResResult = new Label("");

        Button pResStart = new Button("Calculate");
        pResStart.setOnAction(e -> {
            if ((pR1.getText().trim().isEmpty()) || (pR2.getText().trim().isEmpty())) {
                pResResult.setText("Error! Must enter a value in both boxes.");
            }
            else if (!(isNum(pR1.getText()) && isNum(pR2.getText()))) {
                pResResult.setText("Error! Symbols were entered.");
            }
            else if ((pR1.getText() == "0") || (pR2.getText() == "0")){
                pCapResult.setText("Error! Zeros were entered");
            }
            else {
                double input1 = Double.parseDouble(pR1.getText());
                double input2 = Double.parseDouble(pR2.getText());
                pResResult.setText("Resistance/Inductance = " + Double.toString(parallelResistance(input1,input2)));
            }
        });
        Button print4 = new Button("Print");
        print4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });;

        Button back6 = new Button("Back");
        back6.setOnAction(e -> ui.setScene(parallelSelect));

        VBox calc4 = new VBox(5);
        calc4.getStylesheets().add(STYLE);
        calc4.getChildren().addAll(pRDirections, pr1Label, pR1, pr2Label, pR2, pResStart, print4, back5, pResResult);
        pResCalc = new Scene(calc4);

    }
    public String getToolName() {
        return name;
    }
    public String printResult() {
        if (sCapSave.length() != 0) {
            String s = sCapSave;
            sCapSave = "";
            return s;
        }
        else if (sResSave.length() != 0) {
            String s = sResSave;
            sResSave = "";
            return s;
        }
        else if (pCapSave.length() != 0) {
            String s = pCapSave;
            pCapSave = "";
            return s;
        }
        else if (pResSave.length() != 0) {
            String s = pResSave;
            pResSave = "";
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