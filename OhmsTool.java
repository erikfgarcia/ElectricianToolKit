

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

    public OhmsTool(UIManager ui) {
        super(ui);
        vInput = new TextField();
        cInput = new TextField();
        rInput = new TextField();
        pInput = new TextField();

        vInput.insertText(0,"0");
        cInput.insertText(0,"0");
        rInput.insertText(0,"0");
        pInput.insertText(0,"0");

        Label vLabel = new Label("Enter Voltage");
        Label cLabel = new Label("Enter Current");
        Label rLabel = new Label("Enter Resistance");
        Label pLabel = new Label("Enter Power");

        vResult = new Label();
        cResult = new Label();
        rResult = new Label();
        pResult = new Label();

        calc = new Button("Calculate");


        print = new Button("Print");
        print.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                printToHistory();
            }
        });;

        Label directions = new Label("Enter values");
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
    public String getToolName() {
        return name;
    }

    public String printResult() {
        return "v = " + vResult.getText() + "c = " + cResult.getText() + "r = " + rResult.getText() + "p = " + pResult.getText();
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
}