
import javafx.scene.text.Text;
import java.sql.*;
import java.util.ArrayList;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import java.util.List;

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
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class creates estimates.
 * 
 * @author Erik F Garcia
 *
 */
public class EstimateTool extends ElectricianApp {
	static Stage primaryStage;
	static Scene scene1;
	static Scene scene2;
	static Scene scene3;
	static Scene scene4;
	static Scene scene5;
	static Scene scene6;
	static Scene scene7;
	static Scene scene8;
	static Scene scene9;
	static Scene scene10;
	static Scene invalid;
	static int WIDTH = 620; // scene width
	static int HEIGHT = 590;// scene height
	static int BWIDTH = 400; // Button width
	static int BHEIGHT = 45;// Button height
	static String STYLE = "/resources/toolkit_style.css";//scene style
	static List<MaterialList> currentList = new ArrayList<>();
	static List<String> myLists = new ArrayList<>();
	static int brCir;
	static int myListIndex=0;
	static String message = "";
	static String message2 = "";
	static double total = 0;
	//database
	static Connection connection = null;
	static String databaseName = "electrician";
	static String databaseName2 = "saved";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";
	static String url2 = "jdbc:mysql://localhost:3306/" + databaseName2 + "?useSSL=false";
	static String username = "root";
	static String password = "4050lsDF.";
	
	/**
	 * 
	 */
	static void display()  {
	
		//-------------------------------------------------//
		// scene 1 Estimate main menu
		//------------------------------------------------//
		
		primaryStage = new Stage();
		Button gpc = new Button("General Purpose Circuit");
		gpc.setPrefSize(BWIDTH, BHEIGHT);
		gpc.setOnAction(e -> {
			primaryStage.setScene(scene2);
		});

		Button abc = new Button("Appliance Branch Circuit");
		abc.setPrefSize(BWIDTH, BHEIGHT);
		abc.setOnAction(e -> {
			primaryStage.setScene(scene4);
		});

		Button ic = new Button("Individual Circuit");
		ic.setPrefSize(BWIDTH, BHEIGHT);
		ic.setOnAction(e -> {
			primaryStage.setScene(scene5);
		});

		Button bl = new Button("Bathronn & Laundry Circuit");
		bl.setPrefSize(BWIDTH, BHEIGHT);
		bl.setOnAction(e -> {
			primaryStage.setScene(scene7);
		});
		
		Button saved = new Button("Saved Lists");
		saved.setPrefSize(BWIDTH, BHEIGHT);
		saved.setOnAction(e -> {
			try {
				
				if(getTableNames()){
					updateCurrentList(myLists.get(0));
					showSavedList();
				} else {
					 primaryStage.setScene(scene10);	
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button exit = new Button("Exit");
		exit.setPrefSize(BWIDTH, BHEIGHT);
		exit.setOnAction(e -> System.exit(0));

		Label menu1 = new Label("Select A Circuit Type");
		menu1.setAlignment(Pos.CENTER);

		VBox layout1 = new VBox(5);
		layout1.setAlignment(Pos.CENTER);
		layout1.getStylesheets().add(STYLE);
		layout1.getChildren().addAll(menu1, gpc, abc, ic, bl, saved , exit);
		scene1 = new Scene(layout1, WIDTH, HEIGHT);

		// --------------------------------------------------//
		// scene 2 General Purpose Circuit
		//---------------------------------------------------//
		
		TextField input1 = new TextField();
		TextField input2 = new TextField();
		TextField input3 = new TextField();
		input1.insertText(0, "0");
		input2.insertText(0, "0");
		input3.insertText(0, "0");

		Button submit = new Button("Submit");
		submit.setPrefSize(BWIDTH, BHEIGHT);
		submit.setOnAction(e -> {

			
			if (!(isInt(input1.getText()) && isInt(input2.getText()) && isInt(input3.getText()))) {
				primaryStage.setScene(invalid);
			} else {
				//with database
				 try {
					 if (Integer.parseInt(input1.getText())!=0)
						addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(input1.getText()));
					 
					 if(Integer.parseInt(input2.getText())!=0)
					     addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(input2.getText()));
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 
	//without database				 
	/*	if (Integer.parseInt(input1.getText())!=0) {		 
			currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(input1.getText())));
				 total += 0.9 * Integer.parseInt(input1.getText());
		}
				 
		if (Integer.parseInt(input2.getText())!=0) {
			currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(input2.getText())));
			total += 1.3 * Integer.parseInt(input2.getText());
		} */
				primaryStage.setScene(scene3);			
			} 
		});

		Button back2 = new Button("Back");
		back2.setPrefSize(BWIDTH, BHEIGHT);
		back2.setOnAction(e -> primaryStage.setScene(scene1));

		Label receptacles1 = new Label("How many duplex or triplex receptacles");
		Label receptacles2 = new Label("How many double duplex receptacles");
		Label luminaries = new Label("Enter the total load in WATTS for luminaries");

		VBox layout2 = new VBox(5);
		layout2.setAlignment(Pos.CENTER);
		layout2.getStylesheets().add(STYLE);
		layout2.getChildren().addAll(receptacles1, input1, receptacles2, input2, luminaries, input3, submit, back2);
		scene2 = new Scene(layout2, WIDTH, HEIGHT);

		//--------------//
		// show time 
		//---------------//
		primaryStage.setTitle("Electrician Toolkit");
		primaryStage.setScene(scene1);
		primaryStage.show();

		// --------------------------------------------------//
		// scene 3 from scene 2 General Purpose Circuit
		//---------------------------------------------------//
		
		Label menu3 = new Label("Select Type of Circuit");
		Button amp15 = new Button("15 Amps");
		amp15.setPrefSize(BWIDTH, BHEIGHT);
		amp15.setOnAction(e -> {

				try {
					if (generalPurposeCircuit(Integer.parseInt(input1.getText()), Integer.parseInt(input2.getText()),
							Integer.parseInt(input3.getText()), 15) == 0)  {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			currentList.add(new MaterialList("Total", "$", total, 0));
			showList();
		});

		Button amp20 = new Button("20 Amps");
		amp20.setPrefSize(BWIDTH, BHEIGHT);
		amp20.setOnAction(e -> {

		
				try {
					if (generalPurposeCircuit(Integer.parseInt(input1.getText()), Integer.parseInt(input2.getText()),
							Integer.parseInt(input3.getText()), 20) == 0) {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			currentList.add(new MaterialList("Total", "$", total, 0));
			showList();
		});

		VBox layout3 = new VBox(5);
		layout3.setAlignment(Pos.CENTER);
		layout3.getStylesheets().add(STYLE);
		layout3.getChildren().addAll(menu3, amp15, amp20);
		scene3 = new Scene(layout3, WIDTH, HEIGHT);

		// -------------------------------------------------------------------//
		// Scene 4 Appliance Branch Circuit
		//--------------------------------------------------------------------//

		Label menu4 = new Label("Enter the number of receptacles");
		TextField recNum1 = new TextField();
		TextField recNum2 = new TextField();
		recNum1.insertText(0, "0");
		recNum2.insertText(0, "0");
		Label recept1 = new Label("How many duplex or triplex receptacles");
		Label recept2 = new Label("How many double duplex receptacles");
		Button submit4 = new Button("Submit");
		submit4.setPrefSize(BWIDTH, BHEIGHT);
		submit4.setOnAction(e -> {

			if (!(isInt(recNum1.getText()) && isInt(recNum2.getText()))) {
				primaryStage.setScene(invalid);

			} else {
				//with database
				try {
					if(Integer.parseInt(recNum1.getText())!=0)
						addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(recNum1.getText()));
					
					if(Integer.parseInt(recNum2.getText())!=0)
						addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(recNum2.getText()));
					
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
				//without database
			/*	if (Integer.parseInt(recNum1.getText())!=0) {		 
					currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(recNum1.getText())));
						 total += 0.9 * Integer.parseInt(recNum1.getText());
				}
						 
				if (Integer.parseInt(recNum2.getText())!=0) {
					currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(recNum2.getText())));
					total += 1.3 * Integer.parseInt(recNum2.getText());
				}
				*/
				
				try {
					if (applianceBranchCircuit(Integer.parseInt(recNum1.getText()),
							Integer.parseInt(recNum2.getText())) == 0) {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				currentList.add(new MaterialList("Total", "$", total, 0));
				showList();
			}
		});

		Button back4 = new Button("Back");
		back4.setPrefSize(BWIDTH, BHEIGHT);
		back4.setOnAction(e -> primaryStage.setScene(scene1));

		VBox layout4 = new VBox(14);
		layout4.setAlignment(Pos.CENTER);
		layout4.getStylesheets().add(STYLE);
		layout4.getChildren().addAll(menu4, recept1, recNum1, recept2, recNum2, submit4, back4);
		scene4 = new Scene(layout4, WIDTH, HEIGHT);

		// ---------------------------------------------------------------------//
		// Scene 5 Individual Circuit
		//----------------------------------------------------------------------//

		Label menu5 = new Label("Select A Circuit Type");
		Button microwave = new Button("Microwave");
		microwave.setPrefSize(BWIDTH, BHEIGHT);
		microwave.setOnAction(e -> {
			brCir = 1;
			primaryStage.setScene(scene6);
		});
		Button drayer = new Button("Drayer");
		drayer.setPrefSize(BWIDTH, BHEIGHT);
		drayer.setOnAction(e -> {
			brCir = 2;
			primaryStage.setScene(scene6);
		});
		Button washer = new Button("Washer");
		washer.setPrefSize(BWIDTH, BHEIGHT);
		washer.setOnAction(e -> {
			brCir = 3;
			primaryStage.setScene(scene6);
		});
		Button diswasher = new Button("Diswasher");
		diswasher.setPrefSize(BWIDTH, BHEIGHT);
		diswasher.setOnAction(e -> {
			brCir = 4;
			primaryStage.setScene(scene6);
		});
		Button waterHeater = new Button("Water Heater");
		waterHeater.setPrefSize(BWIDTH, BHEIGHT);
		waterHeater.setOnAction(e -> {
			brCir = 5;
			primaryStage.setScene(scene6);
		});
		Button range = new Button("Range/CookTop");
		range.setPrefSize(BWIDTH, BHEIGHT);
		range.setOnAction(e -> {
			brCir = 6;
			primaryStage.setScene(scene6);
		});
		Button eVCharger = new Button("EV Charger");
		eVCharger.setPrefSize(BWIDTH, BHEIGHT);
		eVCharger.setOnAction(e -> {
			brCir = 7;
			primaryStage.setScene(scene6);
		});
		Button ACCondenser = new Button("Condenser AC Unit");
		ACCondenser.setPrefSize(BWIDTH, BHEIGHT);
		ACCondenser.setOnAction(e -> {
			brCir = 8;
			primaryStage.setScene(scene6);
		});
		Button ACCentral = new Button("Central AC unit ");
		ACCentral.setPrefSize(BWIDTH, BHEIGHT);
		ACCentral.setOnAction(e -> {
			brCir = 9;
			primaryStage.setScene(scene6);
		});
		
		Button back5 = new Button("Back");
		back5.setPrefSize(BWIDTH, BHEIGHT);
		back5.setOnAction(e -> primaryStage.setScene(scene1));

		VBox layout5 = new VBox(5);
		layout5.setAlignment(Pos.CENTER);
		layout5.getStylesheets().add(STYLE);
		layout5.getChildren().addAll(menu5, microwave, drayer, washer, diswasher, waterHeater, range, eVCharger,
				ACCondenser, ACCentral, back5);
		scene5 = new Scene(layout5, WIDTH, HEIGHT);

		//------------------------------------------------//
		//Scene 6 from Scene 5 Individual Circuit
		//------------------------------------------------//
		
		Label menu6 = new Label("Please Choice");
		Button back6 = new Button("Back");
		back6.setPrefSize(BWIDTH, BHEIGHT);
		back6.setOnAction(e -> primaryStage.setScene(scene5));

		Button viewList6 = new Button("View List");
		viewList6.setPrefSize(BWIDTH, BHEIGHT);
		viewList6.setOnAction(e -> {

			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			currentList.add(new MaterialList("Total", "$", total, 0));
			showList();
		});

		VBox layout6 = new VBox(5);
		layout6.setAlignment(Pos.CENTER);
		layout6.getStylesheets().add(STYLE);
		layout6.getChildren().addAll(menu6, viewList6, back6);
		scene6 = new Scene(layout6, WIDTH, HEIGHT);

		// -------------------------------------------------------------------//
		// Scene 7 bath/laundry
		//---------------------------------------------------------------------//
		
		Label menu7 = new Label("Enter The Number of Receptacles");
		TextField recNum5 = new TextField();
		TextField recNum6 = new TextField();
		recNum5.insertText(0, "0");
		recNum6.insertText(0, "0");
		Label recept5 = new Label("How many duplex or triplex receptacles");
		Label recept6 = new Label("How many double duplex receptacles");

		Button submit7 = new Button("Submit");
		submit7.setPrefSize(BWIDTH, BHEIGHT);
		submit7.setOnAction(e -> {

			if (!(isInt(recNum5.getText()) && isInt(recNum6.getText()))) {
				primaryStage.setScene(invalid);
			} else {
				
				 try {
					 if(Integer.parseInt(recNum5.getText())!=0)
					 addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(recNum5.getText()));
					 
					 if( Integer.parseInt(recNum6.getText())!=0)
					 addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(recNum6.getText()));
					 
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
				 
				 //without database
				/*	if (Integer.parseInt(recNum5.getText())!=0) {		 
						currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(recNum5.getText())));
							 total += 0.9 * Integer.parseInt(recNum5.getText());
					}
							 
					if (Integer.parseInt(recNum6.getText())!=0) {
						currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(recNum5.getText())));
						total += 1.3 * Integer.parseInt(recNum6.getText());
					} */
				
				try {
					if (bathLoundry(Integer.parseInt(recNum5.getText()), Integer.parseInt(recNum6.getText())) == 0) {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				currentList.add(new MaterialList("Total", "$", total, 0));
				showList();
			}

		});
		
		Button back7 = new Button("Back");
		back7.setPrefSize(BWIDTH, BHEIGHT);
		back7.setOnAction(e -> primaryStage.setScene(scene1));

		VBox layout7 = new VBox(5);
		layout7.setAlignment(Pos.CENTER);
		layout7.getStylesheets().add(STYLE);
		layout7.getChildren().addAll(menu7, recept5, recNum5, recept6, recNum6, submit7, back7);
		scene7 = new Scene(layout7, WIDTH, HEIGHT);

		// -----------------------------------------------------//
		// Scene invalid
		//------------------------------------------------------//

		Button exitInv = new Button("Try Again");
		exitInv.setPrefSize(BWIDTH, BHEIGHT);
		exitInv.setOnAction(e -> primaryStage.setScene(scene1));

		Label menuInv = new Label("Invalid Input!! Please Enter Positive Integer Only");
		menu1.setAlignment(Pos.CENTER);

		VBox layoutInv = new VBox(5);
		layoutInv.setAlignment(Pos.CENTER);
		layoutInv.getStylesheets().add(STYLE);
		layoutInv.getChildren().addAll(exitInv, menuInv);
		invalid = new Scene(layoutInv, WIDTH, HEIGHT);
		
		
		//---------------------------------------------------------------//
		//scene 9 saved lists
		//---------------------------------------------------------------//
		/*
		Label menu9 = new Label("Saved Lists");
		Button next9 = new Button("Test");// chabge this 
		next9.setPrefSize(BWIDTH, BHEIGHT);
		next9.setOnAction(e -> {
		myListIndex = 0;
			try {
				if(getTableNames()){
					updateCurrentList(myLists.get(myListIndex));
					showSavedList();
				} else {
					 primaryStage.setScene(scene10);	
				}
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
			Button back9 = new Button("Back");
			back9.setPrefSize(BWIDTH, BHEIGHT);
			back9.setOnAction(e -> primaryStage.setScene(scene1));

			VBox layout9 = new VBox(5);
			layout9.setAlignment(Pos.CENTER);
			layout9.getStylesheets().add(STYLE);
			layout9.getChildren().addAll(menu9, next9, back9);
			scene9 = new Scene(layout9, WIDTH, HEIGHT);

			*/
			
			//---------------------------------------------------------------//
			//scene 10 empty 
			//---------------------------------------------------------------//
				
			Label menu10 = new Label("You have no saved lists");
			
				Button back10 = new Button("Back");
				back10.setPrefSize(BWIDTH, BHEIGHT);
				back10.setOnAction(e -> primaryStage.setScene(scene1));

				VBox layout10 = new VBox(5);
				layout10.setAlignment(Pos.CENTER);
				layout10.getStylesheets().add(STYLE);
				layout10.getChildren().addAll(menu10, back10);
				scene10 = new Scene(layout10, WIDTH, HEIGHT);
	}

	/**
	 * 
	 */
	public static void showList() {

		//--------------------------------------------------//
		//scene 8 Table
		//--------------------------------------------------//
		
		TableView<MaterialList> table;
		// Name
		TableColumn<MaterialList, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(150);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		// type
		TableColumn<MaterialList, String> typeCol = new TableColumn<>("Type");
		typeCol.setMinWidth(150);
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

		// Price
		TableColumn<MaterialList, Double> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<MaterialList, Integer> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		table = new TableView<>();
		table.setItems(getMaterialList());
		table.getColumns().addAll(nameCol, typeCol, priceCol, quantityCol);

		Button save = new Button("Save");
		save.setPrefSize(BWIDTH, BHEIGHT);
		save.setOnAction(e -> {
			try {
				saveCurrentList();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			currentList.clear();
			primaryStage.setScene(scene1);
		});

		Button back = new Button("Go Back to Main Menu");
		back.setPrefSize(BWIDTH, BHEIGHT);
		back.setOnAction(e -> {
			currentList.clear();
			primaryStage.setScene(scene1);
		});
		message2 = "Total: $"+total;
		total = 0.00;
		Text text = new Text(20, 20, message);
		Text text2 = new Text(20, 20, message2);
		VBox layout = new VBox(5);
		layout.setAlignment(Pos.CENTER);
		layout.getStylesheets().add(STYLE);
		layout.getChildren().addAll(text, table, text2, save, back);
		scene8 = new Scene(layout, WIDTH, HEIGHT);
		primaryStage.setScene(scene8);
		
	}
	
	public static void showSavedList() {

		//--------------------------------------------------//
		//scene 8 Table
		//--------------------------------------------------//
		
		TableView<MaterialList> table;
		// Name
		TableColumn<MaterialList, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(150);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		// type
		TableColumn<MaterialList, String> typeCol = new TableColumn<>("Type");
		typeCol.setMinWidth(150);
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

		// Price
		TableColumn<MaterialList, Double> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<MaterialList, Integer> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		table = new TableView<>();
		table.setItems(getMaterialList());
		table.getColumns().addAll(nameCol, typeCol, priceCol, quantityCol);

		Button next = new Button("Delete");
		next.setPrefSize(BWIDTH, BHEIGHT);
		next.setOnAction(e ->{
			try {
				deleteTable(myLists.get(myListIndex), myListIndex);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		if(myLists.size() == myListIndex) {
			 myListIndex--;
		 }
		
		if(myLists.size()==0) {
			myListIndex=0;
			primaryStage.setScene(scene10);
		}else {
			try {
				currentList.clear();
				updateCurrentList(myLists.get(myListIndex));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			showSavedList();
		}
		
		} );

		Button back = new Button("Next");
		back.setPrefSize(BWIDTH, BHEIGHT);
		back.setOnAction(e -> {
	
			myListIndex++;
			
			if(myListIndex == myLists.size()) {
				myListIndex = 0;				
			}
	
			try {
				currentList.clear();
				updateCurrentList(myLists.get(myListIndex));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			showSavedList();
			
		});
	
		Button back2 = new Button("Go Back to Main Menu");
		back2.setPrefSize(BWIDTH, BHEIGHT);
		back2.setOnAction(e -> {
			currentList.clear();
			primaryStage.setScene(scene1);
		});
		
		VBox layout = new VBox(5);
		layout.setAlignment(Pos.CENTER);
		layout.getStylesheets().add(STYLE);
		layout.getChildren().addAll(table, next, back, back2);
		scene8 = new Scene(layout, WIDTH, HEIGHT);
		primaryStage.setScene(scene8);
		
	}
	
		

	//-----------------------------------------------------------------------------------------//
	//Logic
	//-----------------------------------------------------------------------------------------//
	
	/**
	 * 
	 * @param dT
	 * @param dD
	 * @param constantLoad
	 * @param breakerSide
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int generalPurposeCircuit(int dT, int dD, int constantLoad, int breakerSide) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
				
		int numOfRecCircuit = 0, numOfLighCircuit = 0, mixCir = 0, numberOfCircuits = 0, totalPower = 0;
		double totalAmps = 0, volt = 120;
		boolean cLoad = false, nCLoad = false;
		totalPower += (dT * 180);
		totalPower += (dD * 360);

		totalAmps = (double) totalPower / volt;
		numberOfCircuits = (int) totalAmps / breakerSide;
		numOfRecCircuit = numberOfCircuits;
		totalAmps %= breakerSide;

		if (totalAmps != 0)
			nCLoad = true;

		double cAmps = (double) constantLoad / volt;
		numberOfCircuits += ((int) cAmps / (breakerSide * 0.8));

		numOfLighCircuit = numberOfCircuits - numOfRecCircuit;
		cAmps %= (breakerSide * 0.8);

		if (cAmps != 0)
			cLoad = true;

		totalAmps += cAmps;

		if (nCLoad == true && cLoad == true) {
			if (totalAmps > (breakerSide * 0.8)) {
				numberOfCircuits += 2;
				numOfLighCircuit++;
				numOfRecCircuit++;

			} else {
				numberOfCircuits++;
				mixCir = 1;
			}

		} else {

			if (nCLoad == true) {
				numberOfCircuits++;
				numOfRecCircuit++;
			} else if (cLoad == true) {
				numberOfCircuits++;
				numOfLighCircuit++;
			}

		}
        
		//with database
		if(numberOfCircuits!=0) {
			addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole", numberOfCircuits);					
			int temp = 10 *(dT +dD); 
			temp +=  (int) (0.1* (double)constantLoad);
			if(breakerSide == 20) {	addPriceToCurrentList("Wire", "12-2", temp);}
			else if(breakerSide == 15) { addPriceToCurrentList("Wire", "14-2", temp);}		
		}
		
		//without database
	/*	if(numberOfCircuits!=0) {
			currentList.add(new MaterialList("Circuit Breaker", "" + breakerSide + "-Amp 1-Pole", 8.5, numberOfCircuits));					
			int temp = 10 *(dT +dD); 
			temp +=  (int) (0.1* (double)constantLoad);
			if(breakerSide == 20) {	currentList.add(new MaterialList("Wire", "12-2", 0.23, temp)); total += 0.23*temp;}
			else if(breakerSide == 15) { currentList.add(new MaterialList("Wire", "14-2", 0.3, temp)); total += 0.23*temp;}	
		} */

		message = "Total circuits: " + numberOfCircuits + " Ligth circuits: " + numOfLighCircuit
				+ " Receptacle Circuits: " + numOfRecCircuit + " Mixed circuits: " + mixCir;	

		return numberOfCircuits;
	}

	/**
	 * 
	 * @param dT
	 * @param dD
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int applianceBranchCircuit(int dT, int dD) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int numberOfCircuits = 0, totalPower = 0, breakerSide = 20;
		double totalAmps = 0, volt = 120;
		totalPower += (dT * 180);
		totalPower += (dD * 360);

		totalAmps = (double) totalPower / volt;
		numberOfCircuits = (int) totalAmps / breakerSide;
		totalAmps %= breakerSide;

		if (totalAmps != 0)
			numberOfCircuits++;

		if (numberOfCircuits == 1)// Minimum 2 circuits in kitchen
			numberOfCircuits++;

		//with database 
		if(numberOfCircuits!=0) {
			 addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole", numberOfCircuits);
			 int temp = 10 *(dT + dD); 
			 addPriceToCurrentList("Wire", "12-2", temp);
		 } 
		 
		//without database
		/* if(numberOfCircuits!=0) {
			 currentList.add(new MaterialList("Circuit Breaker", ""+ breakerSide+"-Amp 1-Pole", 8.5, numberOfCircuits));
			 int temp = 10 *(dT + dD); 
			 currentList.add(new MaterialList("Wire", "12-2", 1.3, temp));
			 total += 1.3*temp;
		 } */
			
		message = "Total circuits: " + numberOfCircuits;	
		return numberOfCircuits;
	}

	/**
	 * 
	 * @param brCir
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void individualCircuit(int brCir) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		currentList.clear();
		message = "Total circuits: 0";

		switch (brCir) {
		case 1:
			System.out.println("microwave");
			addPriceToCurrentList("Circuit Breaker", "20-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "12-2", 100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			message = "Microwave";
			break;
		case 2:
			System.out.println("drayer");
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1 ); 
			addPriceToCurrentList("Wire", "10-3", 100 );
			addPriceToCurrentList("Receptacle", "30A 125/250", 1 );
			addPriceToCurrentList("2-gang box", "Old/New Work", 1 );
			message = "Drayer";
			break;
		case 3:
			System.out.println("washer");
			addPriceToCurrentList("Circuit Breaker","20-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "12-2",  100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			message = "Washer";
			break;
		case 4:
			System.out.println("diswasher");
			addPriceToCurrentList("Circuit Breaker", "15-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "14-2", 100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			message = "Diswasher";
			break;
		case 5:
			System.out.println("Water Heater");
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "10-2", 100);
			message = "Water Heater";	
			break;
		case 6:
			System.out.println("range");
			addPriceToCurrentList("Circuit Breaker", "50-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "6-3", 100);
			addPriceToCurrentList("Receptacle", "50A 125/250", 1);
			addPriceToCurrentList("2-gang box", "Old/New Work", 1);		
			message = "Range";
			break;
		case 7:
			System.out.println("eVCharger");
			addPriceToCurrentList("Circuit Breaker", "50-Amp 2-Pole",1);
			addPriceToCurrentList("Wire", "6-3", 100);
			addPriceToCurrentList("Receptacle", "50A 125/250", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			message = "EV Charger";		
			break;
		case 8:
			System.out.println("ACCondenser");
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "10-2", 100);
			addPriceToCurrentList("Fused AC Disconnect", "30 amps 2-Pole", 1);
			message = "AC Condenser";
			break;
		case 9:
			System.out.println("ACCentral");
			
			addPriceToCurrentList("Circuit Breaker", "60-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "6-3", 50);
		
			//currentList.add(new MaterialList("Circuit Breaker", "60-Amp 2-Pole",price, 1));
		    //currentList.add(new MaterialList("Wire", "6-3", price, 50));
			message = "AC Central";
			break;
		default:
			message = "Total circuits: 0";
			break;
		}
		//no implementation without database
	}
    
	/**
	 * 
	 * @param dT
	 * @param dD
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int bathLoundry(int dT, int dD) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {		
		
		int numberOfCircuits = 0, totalPower = 0, breakerSide = 20;
		double totalAmps = 0, volt = 120;
		totalPower += (dT * 180);
		totalPower += (dD * 360);

		totalAmps = (double) totalPower / volt;
		numberOfCircuits = (int) totalAmps / breakerSide;
		totalAmps %= breakerSide;

		if (totalAmps != 0)
			numberOfCircuits++;
		
		//with database
		if(numberOfCircuits!=0) {
			addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole",  numberOfCircuits);
			int temp = 10 *(dT + dD); 
			addPriceToCurrentList("Wire", "12-2", temp);
		}
    		
		//without database
	/*	if(numberOfCircuits!=0) {
			currentList.add(new MaterialList("Circuit Breaker", ""+ breakerSide+"-Amp 1-Pole", 8.5, numberOfCircuits));
			int temp = 10 *(dT + dD);
			currentList.add(new MaterialList("Wire", "12-2", 1.3, temp));
			total += 1.3*temp;
		} */
		
		message = "Total circuits: " + numberOfCircuits;
		return numberOfCircuits;
	}

	/**
	 * 
	 * @return
	 */
	public static ObservableList<MaterialList> getMaterialList() {

		ObservableList<MaterialList> materials = FXCollections.observableArrayList();
		 		
		for (int i = 0; i < currentList.size(); i++) {
			materials.add(new MaterialList(currentList.get(i).getName(), currentList.get(i).getType(),
					currentList.get(i).getPrice(), currentList.get(i).getQuantity()));
		}

		return materials;
	}
	/**
	 * 
	 * @param name
	 * @param type
	 * @param quantity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void  addPriceToCurrentList(String name, String type, int quantity) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url, username, password);
		Statement st = connection.createStatement();
		ResultSet rs;
		String query = "select price from electrician.materials where name = '"+name+"' and type = '"+type+"';";
		rs = st.executeQuery(query);
		rs.next();
		double price = rs.getDouble("price");
		total += price*quantity;
		currentList.add(new MaterialList(name, type, price, quantity));
		st.close();
		connection.close();
		
	}
	
	public static void  saveCurrentList() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url2, username, password);
		Statement st = connection.createStatement();
		String name = "";
		String type = "";
		double price = 0;
		int quantity = 0;
		int listNumber= 0; 

		//check if table name already exists
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables;
		boolean exists = true;
			while (exists){
				tables = dbm.getTables(null, null, "saved_list_"+listNumber, null);
				if (tables.next()) {
					// Table exists
					System.out.println("yes"); listNumber++;
				}
				else {
					// Table does not exist
					System.out.println("no");
					exists = false;
				}	
		}
			
		//creates a table
		String query = "CREATE TABLE `saved`.`saved_list_"+listNumber+"` ("
				+ "`item_id` INT NOT NULL AUTO_INCREMENT,"
				+ "`name` VARCHAR(45) NOT NULL,"
				+ "`type` VARCHAR(45) NOT NULL,"
				+ "`price` DOUBLE NOT NULL,"
				+ "`quantity` INT NOT NULL,"
				+ "PRIMARY KEY (`item_id`));";
		
		System.out.println(st.executeUpdate(query)); // change this 
		
		//inserts values in table
		String query2="";			
		for(int i = 0; i < currentList.size(); i++) {
		 	name = currentList.get(i).getName();
		 	type = currentList.get(i).getType();
		 	price = currentList.get(i).getPrice();
		 	quantity = currentList.get(i).getQuantity();		
		 	query2 = "INSERT INTO `saved`.`saved_list_"+listNumber+"` (`name`, `type`, `price`, `quantity`)"
		 	 		+ " VALUES ('"+name+"', '"+type+"', '"+price+"', '"+quantity+"');";
		 	System.out.println(st.executeUpdate(query2));	
		}
						
		st.close();
		connection.close();
		
	}
	
	public static boolean getTableNames() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url2, username, password);
		Statement st = connection.createStatement();

		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, null, null);
	            
				myLists.clear();
				while(tables.next()){
					myLists.add(tables.getString("TABLE_NAME"));
				}
		
		if(myLists.size()==0) {
			st.close();
			connection.close();
			return false;
		}else {
	
			st.close();
			connection.close();
			return true;
		}
	}
	
	public static void updateCurrentList(String tableName) throws SQLException {
		System.out.println("yooooooooooooooooooooooo");
		String name="", type=""; double price=0; int quantity=0;
		connection = DriverManager.getConnection(url, username, password);
		Statement st = connection.createStatement();
		ResultSet rs;
	    String query = "SELECT * from saved."+tableName+";";
		rs = st.executeQuery(query);
		
		System.out.println("yooooooooooooooooooooooo");

		while(rs.next()) {			
		name = rs.getString("name");
		type = rs.getString("type");
		price = rs.getDouble("price");
		quantity = rs.getInt("quantity");
		System.out.println("yooooooooooooooooooooooo"+name+" "+type+" "+price+" "+quantity);
		
		currentList.add(new MaterialList(name, type, price, quantity));
		
		}
		
	}
	/**
	 * 
	 * @param tableName
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void  deleteTable(String tableName, int index) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url2, username, password);
		Statement st = connection.createStatement();
		
		//check tbleName
		String query = "DROP TABLE `saved`.`"+tableName+"`;";
		st.executeUpdate(query);
		myLists.remove(index);
		st.close();
		connection.close();
		
	}

	/**
	 * This function returns true if the string is an integer, false otherwise
	 * 
	 * @param number
	 *            string number
	 * @return true if the string is an integer, false otherwise
	 */
	private static Boolean isInt(String number) {

		try {
			int num = Integer.parseInt(number);
			if (num < 0 || num > 999999)
				return false;

			return true;

		} catch (NumberFormatException e) {
			return false;
		}
	}
}
