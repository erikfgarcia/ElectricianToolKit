

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
	static Scene scene7;
	static int appBrCir;
	//static TableView<MaterialList> table;
	
	public static void display() {
		Stage primaryStage = new Stage();
		//scene 1 main menu
		Button gpc = new Button("General Purpose Circuit");
		gpc.setPrefSize(400, 60);
		gpc.setOnAction(e -> {
			primaryStage.setScene(scene2);
		});
		
		Button abc = new Button("Appliance Branch Circuit");
		abc.setPrefSize(400, 60);
		abc.setOnAction(e -> {
			primaryStage.setScene(scene3);
		});
			
		Button ic = new Button("Individual Circuit");
		ic.setPrefSize(400, 60);
		ic.setOnAction(e -> {
			primaryStage.setScene(scene4);
		});
		
		Button bl = new Button("Bathronn & Laundry Circuit");
		bl.setPrefSize(400, 60);
		bl.setOnAction(e -> {
			primaryStage.setScene(scene5);
		});
		
		Button exit = new Button("Exit");
		exit.setPrefSize(400, 60);
		exit.setOnAction(e -> System.exit(0));
		
		ImageView image = new ImageView("Ohms_law.png");
		Label menu1 = new Label("Select A Circuit Type");
		//Label menu1 = new Label("Select A Circuit Type", image);
		menu1.setBackground(null);
		menu1.setPrefSize(10,10);
		menu1.setPrefSize(400, 60);
		menu1.setAlignment(Pos.CENTER);
		
		VBox layout1 = new VBox(5);
		layout1.setAlignment(Pos.CENTER);
		
		layout1.getStylesheets().add("toolkit_style.css");
		//layout1.getChildren().addAll(image,menu1, gpc, abc, ic, bl, exit);
		layout1.getChildren().addAll(menu1, gpc, abc, ic, bl, exit);
		scene1 = new Scene(layout1, 600, 500);
		
		//-------------------------------------------------------------------//
		
		// scene2 General Purpose Circuit
		TextField input1 = new TextField();
		TextField input2 = new TextField();
		TextField input3 = new TextField();
		input1.insertText(0, "0");
		input2.insertText(0, "0");
		input3.insertText(0, "0");

		Button submit = new Button("Submit");
		submit.setPrefSize(400, 60);
		submit.setOnAction(e ->{
			isInt(input1.getText()); isInt(input2.getText());	isInt(input3.getText());
			primaryStage.setScene(scene7); //check
		});
		
		Button back2 = new Button("Back");
		back2.setPrefSize(400, 60);
		back2.setOnAction(e -> primaryStage.setScene(scene1));
		
		Label receptacles1 = new Label("Enter the number of duplex/triplex receptacles");
		Label receptacles2 = new Label("Enter the number of Double duplex receptacles");
		Label luminaries = new Label("Enter the total load in watts for luminaries");
		
		VBox layout2 = new VBox(5);
		layout2.setAlignment(Pos.CENTER);
		layout2.getStylesheets().add("toolkit_style.css");
		layout2.getChildren().addAll(receptacles1, input1, receptacles2, input2, luminaries, input3, submit, back2);
		scene2 = new Scene(layout2, 600, 500);
		
		
		//show time
		primaryStage.setTitle("Electrician Toolkit");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		//-------------------------------------------------------------------//
		//Scene 3 Appliance Branch Circuit 
		Label menu3 = new Label("Enter the number of receptacles");
		
		TextField recNum = new TextField();
		recNum.insertText(0, "0");
		
		Button submit3 = new Button("Submit");
		submit3.setPrefSize(400, 60);
		submit3.setOnAction(e ->{
			System.out.println(input3.getText());
			isInt(input1.getText());	
			//check
			primaryStage.setScene(scene7);
		});
		
		Button back3 = new Button("Back");
		back3.setPrefSize(400, 60);
		back3.setOnAction(e -> primaryStage.setScene(scene1));
		
		VBox layout3 = new VBox(14);
		layout3.setAlignment(Pos.CENTER);
		layout3.getStylesheets().add("toolkit_style.css");
		layout3.getChildren().addAll(menu3, recNum, submit3, back3);
		scene3 = new Scene(layout3, 600, 500);
		
		
		//-------------------------------------------------------------------//
		//Scene 4 Individual Circuit
		
		Label menu4 = new Label("Select A Circuit Type");
		
		Button microwave = new Button("Microwave");
		microwave.setPrefSize(400, 60);
		microwave.setOnAction(e -> {
			appBrCir = 1;
			primaryStage.setScene(scene6);
		});
		Button drayer = new Button("Drayer");
		drayer.setPrefSize(400, 60);
		drayer.setOnAction(e -> {
			appBrCir = 2;
			primaryStage.setScene(scene6);
		});
		Button washer = new Button("Washer");
		washer.setPrefSize(400, 60);
		washer.setOnAction(e -> {
			appBrCir = 3;
			primaryStage.setScene(scene6);
		});
		Button diswasher = new Button("Diswasher");
		diswasher.setPrefSize(400, 60);
		diswasher.setOnAction(e -> {
			appBrCir = 4;
			primaryStage.setScene(scene6);
		});
		Button waterHeater = new Button("Water Heater");
		waterHeater.setPrefSize(400, 60);
		waterHeater.setOnAction(e -> {
			appBrCir = 5;
			primaryStage.setScene(scene6);
		});
		Button range = new Button("Range/CookTop");
		range.setPrefSize(400, 60);
		range.setOnAction(e -> {
			appBrCir = 6;
			primaryStage.setScene(scene6);
		});
		Button eVCharger= new Button("EV Charger");
		eVCharger.setPrefSize(400, 60);
		eVCharger.setOnAction(e -> {
			appBrCir = 7;
			primaryStage.setScene(scene6);
		});
		Button ACCondenser = new Button("Condenser AC Unit");
		ACCondenser.setPrefSize(400, 60);
		ACCondenser.setOnAction(e -> {
			appBrCir = 8;
			primaryStage.setScene(scene6);
		});
		Button ACCentral = new Button("Central AC unit ");
		ACCentral.setPrefSize(400, 60);
		ACCentral.setOnAction(e -> {
			appBrCir = 9;
			primaryStage.setScene(scene6);
		});
		Button back4 = new Button("Back");
		back4.setPrefSize(400, 60);
		back4.setOnAction(e -> primaryStage.setScene(scene1));
		
		VBox layout4 = new VBox(5);
		layout4.setAlignment(Pos.CENTER);
		layout4.getStylesheets().add("toolkit_style.css");
		layout4.getChildren().addAll(menu4 ,microwave, drayer, washer, diswasher, 
				waterHeater, range, eVCharger, ACCondenser, ACCentral, back4);
				scene4 = new Scene(layout4, 600, 500);
				
				//-------------------------------------------------------------------//
				//Scene 5
				Label menu5 = new Label("Enter The Number of Receptacles");
				TextField recNum5 = new TextField();
				recNum5.insertText(0, "0");
				
				Button submit5 = new Button("Submit");
				submit5.setPrefSize(400, 60);
				submit5.setOnAction(e -> {
					System.out.println(recNum5.getText());
					isInt(recNum5.getText());	
					// check 
					primaryStage.setScene(scene7);
					
					
				});
				Button back5 = new Button("Back");
				back5.setPrefSize(400, 60);
				back5.setOnAction(e -> primaryStage.setScene(scene1));
				
				VBox layout5 = new VBox(5);
				layout5.setAlignment(Pos.CENTER);
				layout5.getStylesheets().add("toolkit_style.css");
				layout5.getChildren().addAll(menu5, recNum5, submit5, back5 );
						scene5 = new Scene(layout5, 600, 500);
				//-------------------------------------------------------------------//
				//Scene 6 from scene 4
						
						//appBrCir
						// list ampacity, list of materials and average cost
						Label menu6 = new Label("IDK Yet");
						
						Button back6 = new Button("Back");
						back6.setPrefSize(400, 60);
						back6.setOnAction(e -> primaryStage.setScene(scene4));
						 
						Button viewList6 = new Button("View List");
						viewList6.setPrefSize(400, 60);
						viewList6.setOnAction(e -> primaryStage.setScene(scene7));
						
						VBox layout6 = new VBox(5);
						layout6.setAlignment(Pos.CENTER);
						layout6.getStylesheets().add("toolkit_style.css");
						layout6.getChildren().addAll(menu6, viewList6, back6);
						scene6 = new Scene(layout6 , 600, 500);
						
						//-------------------------------------------------------------------//
						//Scene 7 table
						
						TableView<MaterialList> table;
						//Name
						TableColumn<MaterialList, String> nameCol = new TableColumn<>("Name");
						nameCol.setMinWidth(150);
						nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
						
						//type
						TableColumn<MaterialList, String> typeCol = new TableColumn<>("Type");
						typeCol.setMinWidth(150);
						typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
						
						//Price
						TableColumn<MaterialList, Double> priceCol = new TableColumn<>("Price");
						priceCol.setMinWidth(50);
						priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
						
						TableColumn<MaterialList, Integer> quantityCol = new TableColumn<>("Quantity");
						quantityCol.setMinWidth(50);
        				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        			
        				
						table = new TableView<>();
						table.setItems(getMaterialList());
						table.getColumns().addAll(nameCol, typeCol, priceCol, quantityCol);
						
						Button save = new Button("Save");
						save.setPrefSize(400, 60);
						save.setOnAction(e -> primaryStage.setScene(scene1));
						
						Button back7 = new Button("Go Back to Main Menu");
						back7.setPrefSize(400, 60);
						back7.setOnAction(e -> primaryStage.setScene(scene1));
						
						Text text7 = new Text(20, 20, "The Ampacity for this circuit is 15 Amperes" );
		
						
						VBox layout7 = new VBox(5);
						layout7.setAlignment(Pos.CENTER);
						layout7.getStylesheets().add("toolkit_style.css");
						layout7.getChildren().addAll(text7, table, save , back7);
						scene7 = new Scene(layout7 , 600, 700);
						
	}
	
	public static ObservableList<MaterialList> getMaterialList(){
		
		String type = ""; double price = 0.0; int qty = 0;
		
		
		
		ObservableList<MaterialList> materials = FXCollections.observableArrayList();
		materials.add(new MaterialList("Circuit Breaker","20 Amp", 10.5, 1));
		materials.add(new MaterialList("Wire", "14-2", 20.5, 1));
		materials.add(new MaterialList("Receptacles", "duplex", 10.5, 1));
		materials.add(new MaterialList("Swiches", "single", 10.5, 1));
		materials.add(new MaterialList("wirenuts", type, 10.5, 1));
		materials.add(new MaterialList("Staples", "100 unit ",10.5, 1));
		
		return materials;	
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


