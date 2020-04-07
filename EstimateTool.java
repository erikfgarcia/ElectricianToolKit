

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EstimateTool {
	static Scene scene1;
	static Scene scene2;
	static Scene scene3;
	static Scene scene4;
	static Scene scene5;
	static Scene scene6;
	static int appBrCir;

	public static void display() {
		Stage primaryStage = new Stage();
		//scene 1 main menu
		Button gpc = new Button("General Purpose Circuit");
		gpc.setOnAction(e -> {
			primaryStage.setScene(scene2);
		});
		
		Button abc = new Button("Appliance Branch Circuit");
		abc.setOnAction(e -> {
			primaryStage.setScene(scene3);
		});
			
		Button ic = new Button("Individual Circuit");
		ic.setOnAction(e -> {
			primaryStage.setScene(scene4);
		});
		
		Button bl = new Button("Bathronn & Laundry Circuit");
		bl.setOnAction(e -> {
			primaryStage.setScene(scene5);
		});
		
		Button exit = new Button("Exit");
		exit.setOnAction(e -> System.exit(0));
		
		Label menu1 = new Label("Select A Circuit Type");
		
		VBox layout1 = new VBox(14);
		layout1.getChildren().addAll(menu1, gpc, abc, ic, bl, exit);
		scene1 = new Scene(layout1, 250, 300);
		
		//-------------------------------------------------------------------//
		
		// scene2 General Purpose Circuit
		TextField input1 = new TextField();
		TextField input2 = new TextField();
		TextField input3 = new TextField();
		Button submit = new Button("Submit");
		submit.setOnAction(e ->{
			System.out.println(input1.getText());
			System.out.println(input2.getText());
			System.out.println(input3.getText());
			isInt(input1.getText());
			isInt(input2.getText());
			isInt(input3.getText());
		});
		Button back2 = new Button("Back");
		back2.setOnAction(e -> primaryStage.setScene(scene1));
		Label receptacles1 = new Label("Enter the number of duplex/triplex receptacles");
		Label receptacles2 = new Label("Enter the number of Double duplex receptacles");
		Label luminaries = new Label("Enter the total load in watts for luminaries");
		
		
		VBox layout2 = new VBox(14);
		layout2.getChildren().addAll(receptacles1, input1, receptacles2, input2, luminaries, input3, submit, back2);
		scene2 = new Scene(layout2, 250, 400);
		
		
		//show time
		primaryStage.setTitle("Electrician Toolkit");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		//-------------------------------------------------------------------//
		//Scene 3 Appliance Branch Circuit 
		Label menu3 = new Label("Enter the number of receptacles");
		TextField recNum = new TextField(); 
		Button submit3 = new Button("Submit");
		submit3.setOnAction(e ->{
			System.out.println(input3.getText());
			isInt(input1.getText());
			
		});
		Button back3 = new Button("Back");
		back3.setOnAction(e -> primaryStage.setScene(scene1));
		VBox layout3 = new VBox(14);
		layout3.getChildren().addAll(menu3, recNum, submit3, back3);
		scene3 = new Scene(layout3, 250, 400);
		
		
		//-------------------------------------------------------------------//
		//Scene 4 Individual Circuit
		
		Label menu4 = new Label("Select A Circuit Type");
		Button microwave = new Button("Microwave");
		microwave.setOnAction(e -> {
			appBrCir = 1;
			primaryStage.setScene(scene6);
		});
		Button drayer = new Button("Drayer");
		drayer.setOnAction(e -> {
			appBrCir = 2;
			primaryStage.setScene(scene6);
		});
		Button washer = new Button("Washer");
		washer.setOnAction(e -> {
			appBrCir = 3;
			primaryStage.setScene(scene6);
		});
		Button diswasher = new Button("Diswasher");
		diswasher.setOnAction(e -> {
			appBrCir = 4;
			primaryStage.setScene(scene6);
		});
		Button waterHeater = new Button("Water Heater");
		waterHeater.setOnAction(e -> {
			appBrCir = 5;
			primaryStage.setScene(scene6);
		});
		Button range = new Button("Range/CookTop");
		range.setOnAction(e -> {
			appBrCir = 6;
			primaryStage.setScene(scene6);
		});
		Button eVCharger= new Button("EV Charger");
		eVCharger.setOnAction(e -> {
			appBrCir = 7;
			primaryStage.setScene(scene6);
		});
		Button ACCondenser = new Button("Condenser AC Unit");
		ACCondenser.setOnAction(e -> {
			appBrCir = 8;
			primaryStage.setScene(scene6);
		});
		Button ACCentral = new Button("Central AC unit ");
		ACCentral.setOnAction(e -> {
			appBrCir = 9;
			primaryStage.setScene(scene6);
		});
		Button back4 = new Button("Back");
		back4.setOnAction(e -> primaryStage.setScene(scene1));
		
		VBox layout4 = new VBox(5);
		layout4.getChildren().addAll(menu4,microwave, drayer, washer, diswasher, 
				waterHeater, range, eVCharger, ACCondenser, ACCentral, back4);
				scene4 = new Scene(layout4, 250, 500);
				
				//-------------------------------------------------------------------//
				//Scene 5
				Label menu5 = new Label("Enter The Number of Receptacles");
				TextField recNum5 = new TextField();
				Button submit5 = new Button("Submit");
				submit5.setOnAction(e -> {
					System.out.println(recNum5.getText());
				});
				Button back5 = new Button("Back");
				back5.setOnAction(e -> primaryStage.setScene(scene1));
				
				VBox layout5 = new VBox(14);
				layout5.getChildren().addAll(menu5, recNum5, submit5, back5 );
						scene5 = new Scene(layout5, 250, 500);
				//-------------------------------------------------------------------//
				//Scene 6
						
						//appBrCir
						
						Button back6 = new Button("Save");
						back6.setOnAction(e -> primaryStage.setScene(scene4));
						
						
	}
	
	private static Boolean isInt(String number) {
		try {
			int age = Integer.parseInt(number);
			System.out.println("yes");
			return true;
		}catch(NumberFormatException e) {
			System.out.println("no a integer");
			return false;
		}
				
	}

}


