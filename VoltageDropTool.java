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
import javafx.scene.control.ChoiceBox;
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

public class VoltageDropTool extends Tool{

	String name = "Voltage Drop Calculator";
	TextField fieldWireLength;
	TextField fieldWireDiameter;
	TextField fieldCurrent;
	TextField fieldVoltageLoad;
	ChoiceBox<String> matBox;
	ChoiceBox<String> lenBox;
	//ChoiceBox<String> diamBox;
	Button buttonCalculate;
	Label voltDropResult;
	
	
	public VoltageDropTool(UIManager ui) {
		super(ui);
		
		fieldWireLength = new TextField();
		fieldWireDiameter = new TextField();
		fieldCurrent = new TextField();
		fieldVoltageLoad = new TextField();
		
		Label labelWireMaterial = new Label("Wire Material");
		Label labelWireLength = new Label("Enter Wire Length");
		Label labelLengthUnits = new Label("Units");
		Label labelWireDiameter = new Label("Enter Wire Diameter (cmil)");
		Label labelCurrent = new Label("Enter Current");
		
		voltDropResult = new Label();
		
		matBox = new ChoiceBox<>();
		matBox.getItems().add("Copper");
		matBox.getItems().add("Aluminum");
		
		lenBox = new ChoiceBox<>();
		lenBox.getItems().addAll("feet", "meters");
		
		buttonCalculate = new Button("Calculate");
		
		
		HBox hMaterial = new HBox(labelWireMaterial, matBox);
		hMaterial.setSpacing(5);
		HBox hLength = new HBox(labelWireLength, fieldWireLength, labelLengthUnits, lenBox);
		hLength.setSpacing(5);
		HBox hDiameter = new HBox(labelWireDiameter, fieldWireDiameter);
		hDiameter.setSpacing(5);
		HBox hCurrent = new HBox(labelCurrent, fieldCurrent);
		hCurrent.setSpacing(5);
		HBox hButtons = new HBox(buttonCalculate);
		hButtons.setSpacing(5);
		HBox output = new HBox(voltDropResult);
		output.setSpacing(5);
		
		VBox fieldsVertical = new VBox(hMaterial, hLength, hDiameter, hCurrent, hButtons, output);
		fieldsVertical.setSpacing(5);
		
		VBox outerWrap = new VBox(fieldsVertical);
        VBox.setMargin(fieldsVertical, new Insets(10, 10, 10, 10));

        this.getChildren().add(outerWrap);
        
        buttonCalculate.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					String strMaterial, strUnits;
					double wireLength, wireDiameter, current;
					
					if (matBox.getValue() != null) {
						strMaterial = matBox.getValue();
						//System.out.println(strMaterial);
					}
					else {
						//ERROR
						voltDropResult.setText("Please select a wire material");
						return;
					}
					if (lenBox.getValue() != null) {
						strUnits = lenBox.getValue();
						//System.out.println(strUnits);
					}
					else {
						//ERROR
						voltDropResult.setText("Please select a measurement unit for length");
						return;
					}
					
					if (fieldWireLength != null) {
						wireLength = Double.parseDouble(fieldWireLength.getText());
						//System.out.println(wireLength);
					}
					else {
						//ERROR
						voltDropResult.setText("Please enter a wire length");
						return;
					}
					if (fieldWireDiameter != null) {
						wireDiameter = Double.parseDouble(fieldWireDiameter.getText());
						//System.out.println(wireDiameter);
					}
					else {
						//ERROR
						voltDropResult.setText("Please enter a wire diameter");
						return;
					}
					if (fieldCurrent != null) {
						current = Double.parseDouble(fieldCurrent.getText());
						//System.out.println(current);
					}
					else {
						//ERROR
						voltDropResult.setText("Please enter the current");
						return;
					}
					
					//VD = 2 * K * D * I / CM
					
					//Set material K constant
					double k, d, vd;
					if (strMaterial.equals("Copper")){
						k = 12.9;
						//System.out.println("k value for copper:" + k);
					}
					else {
						k = 21.2;
						//System.out.println("k value for aluminum:" + k);
					}
					
					//convert units to feet
					if (strUnits.equals("meters")) {
						d = wireLength * 3.28084;
						//System.out.println(wireLength + "Meters converted to "+ d +" feet.");	
					}
					else {
						d = wireLength;
					}
					
					vd = 2 * k * d * current / wireDiameter;
					voltDropResult.setText("Voltage drop: " + String.valueOf(vd));
				}
				catch(Exception ex) {
					voltDropResult.setText("ERROR");
				}
			}
		});
		
	}

	public String getToolName() {
		return name;
	}

	public String printResult() {
		return (voltDropResult.getText());
	}
	
	public void clearDisplay() {
		
	}

	

}
