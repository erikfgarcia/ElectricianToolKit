
import javafx.scene.text.Text;
import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import java.util.List;

import javax.swing.JTextField;

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
import javafx.scene.layout.Pane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class creates estimates. Retrieves prices from an database (MySQL) and stores list of materials
 * into the database. Some prices are hard coded replacing the database function for demonstration.  
 * Without connection to a database this class cannot save lists,  retrieve lists, and make calculations.   
 * 
 * @author Erik F Garcia
 *
 */
public class EstimateTool extends Pane{
	//static Stage primaryStage;
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
	static int HEIGHT = 639;// scene height
	static int BWIDTH = 500; // Button width
	static int BHEIGHT = 55;// Button height
	static int TWIDTH = 75; //file Test width
	static int THEIGHT = 20;// file text height
	static int BW = 60; //back button width
	static int BH = 15; //back button height
	static boolean isBath = false;
	static String STYLE = "/resources/toolkit_style.css";//scene style file location
	static List<MaterialList> currentList = new ArrayList<>();
	static List<String> myLists = new ArrayList<>();
	static int brCir;
	static int myListIndex=0;
	static String message = "";
	static String message2 = "";
	static double total = 0;
	//Database info
	static Connection connection = null;
	static String databaseName = "electrician";
	static String databaseName2 = "saved";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";
	static String url2 = "jdbc:mysql://localhost:3306/" + databaseName2 + "?useSSL=false";
	static String username = "root";
	static String password = "*******"; //database password
	UIManager ui; // set scene from main program  
	
	/**
	 * Constructor
	 * @param ui set scene from main program  
	 */
	public EstimateTool(UIManager ui) {
		 this.ui = ui;   	
	}
	/**
	 * This method creates user interface
	 * @return the first scene 
	 */
	public Scene  getPrimaryScene() {
	
		//-------------------------------------------------//
		// scene 1 Estimate main menu
		//------------------------------------------------//
		
		Button gpc = new Button("General Purpose Circuit");
		gpc.setPrefSize(BWIDTH, BHEIGHT);
		gpc.setId("estButton");
		gpc.setLayoutX(100);
		gpc.setOnAction(e -> {
			ui.setScene(scene2);
			//primaryStage.setScene(scene2);
		});

		Button abc = new Button("Kitchen"); ////
		abc.setPrefSize(BWIDTH, BHEIGHT);
		abc.setId("estButton");
		abc.setOnAction(e -> {
			ui.setScene(scene4);
			//primaryStage.setScene(scene4);
		});

		Button ic = new Button("Individual Circuit");
		ic.setPrefSize(BWIDTH, BHEIGHT);
		ic.setId("estButton");
		ic.setOnAction(e -> {
			//primaryStage.setScene(scene5);
			ui.setScene(scene5);
		});

		Button bl = new Button("Bathronn & Laundry Circuit");
		bl.setPrefSize(BWIDTH, BHEIGHT);
		bl.setId("estButton");
		bl.setOnAction(e -> {
			ui.setScene(scene6); 
			//primaryStage.setScene(scene7);
		});
		
		Button saved = new Button("Saved Lists");
		saved.setPrefSize(BWIDTH, BHEIGHT);
		saved.setId("estButton");
		saved.setOnAction(e -> {
			try {
				
				if(getTableNames()){
					updateCurrentList(myLists.get(0));
					showSavedList(ui);
				} else {
					 //primaryStage.setScene(scene10);
					 ui.setScene(scene10);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button back = new Button("Back");
		back.setPrefSize(BW, BH);
		back.setId("estButton");
//		back.setAlignment(Pos.CENTER);
		back.setOnAction(e -> ui.resetScene());

		Label menu1 = new Label("Select A Circuit Type");
		menu1.setId("estText");
		menu1.setAlignment(Pos.BASELINE_CENTER);
		VBox layout1 = new VBox(10);
		//layout1.setAlignment(Pos.CENTER);
	    layout1.getStylesheets().add(STYLE);
	    layout1.setId("estBackground");
		layout1.getChildren().addAll(back, menu1, gpc, abc, ic, bl, saved );
		//scene1 = new Scene(layout1, 700, 700);
		scene1 = new Scene(layout1);
	
	    
		// --------------------------------------------------//
		// scene 2 General Purpose Circuit
		//---------------------------------------------------//
		
		TextField input1 = new TextField();
		TextField input2 = new TextField();
		TextField input3 = new TextField();
		input1.setMaxSize( TWIDTH, THEIGHT);
		input2.setMaxSize( TWIDTH, THEIGHT);
		input3.setMaxSize( TWIDTH, THEIGHT);
		input1.insertText(0, "0");
		input2.insertText(0, "0");
		input3.insertText(0, "0");

		Button submit = new Button("Submit");
		submit.setId("estButton");
		submit.setPrefSize(BWIDTH, BHEIGHT);
		submit.setOnAction(e -> {

			
			if (!(isInt(input1.getText()) && isInt(input2.getText()) && isInt(input3.getText()))) {
				//primaryStage.setScene(invalid);
				ui.setScene(invalid);
			} else {
				
				//with database
/*				try {
					 if (Integer.parseInt(input1.getText())!=0) {
						addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(input1.getText()));
						addPriceToCurrentList("1-gang box", "Old/New Work", Integer.parseInt(input1.getText()));
						addPriceToCurrentList("Wall Plate", "1-Gang Outlet", Integer.parseInt(input1.getText()));
					 }
					 
					 if(Integer.parseInt(input2.getText())!=0) {
					     addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(input2.getText()));
					     addPriceToCurrentList("2-gang box", "Old/New Work", Integer.parseInt(input2.getText()));
					     addPriceToCurrentList("Wall Plate", "2-Gang Outlet", Integer.parseInt(input2.getText()));
					 }
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
*/				 
	//without database	needs database connectivity			 
/**/		if (Integer.parseInt(input1.getText())!=0) {		 
			currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(input1.getText())));
				 total += 0.9 * Integer.parseInt(input1.getText());
		}
				 
		if (Integer.parseInt(input2.getText())!=0) {
			currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(input2.getText())));
			total += 1.3 * Integer.parseInt(input2.getText());
		}    
/**/				
				//primaryStage.setScene(scene3);
				ui.setScene(scene3);
			} 
		});

		Button back2 = new Button("Back");
		back2.setPrefSize(BW, BH);
		back2.setId("estButton");
		back2.setOnAction(e -> ui.setScene(scene1) /*primaryStage.setScene(scene1)*/);

		Label receptacles1 = new Label("How many duplex or triplex receptacles?");
		Label receptacles2 = new Label("How many double duplex receptacles?");
		Label luminaries = new Label("Enter the lighting load sum in watts");
		VBox layout2 = new VBox(5);
		layout2.getStylesheets().add(STYLE);
		layout2.setId("estBackground");
		layout2.getChildren().addAll(back2, receptacles1, input1, receptacles2, input2, luminaries, input3, submit);
	//	scene2 = new Scene(layout2, WIDTH, HEIGHT);
		scene2 = new Scene(layout2);
		
		
		// --------------------------------------------------//
		// scene 3 from scene 2 General Purpose Circuit
		//---------------------------------------------------//
		
		Label menu3 = new Label("Please Select Overcurrent Protection");
		menu3.setId("estText");
		Button amp15 = new Button("15 Amps");
		amp15.setId("estButton");
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
			
			showList(ui);
		});

		Button amp20 = new Button("20 Amps");
		amp20.setId("estButton");
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
			
			showList(ui);
		});

		VBox layout3 = new VBox(5);
		//layout3.setAlignment(Pos.CENTER);
		layout3.getStylesheets().add(STYLE);
		layout3.setId("estBackground");
		layout3.getChildren().addAll(menu3, amp15, amp20);
		//scene3 = new Scene(layout3, WIDTH, HEIGHT);
		scene3 = new Scene(layout3);
	
		// -------------------------------------------------------------------//
		// Scene 4 Appliance Branch Circuit "Kitchen"
		//--------------------------------------------------------------------//

		Label menu4 = new Label("Enter the Number of Receptacles");
		menu4.setId("estText");
		TextField recNum1 = new TextField();
		TextField recNum2 = new TextField();
		recNum1.setMaxSize( TWIDTH, THEIGHT);
		recNum2.setMaxSize( TWIDTH, THEIGHT);
		recNum1.insertText(0, "0");
		recNum2.insertText(0, "0");
		Label recept1 = new Label("How many duplex or triplex receptacles?");
		Label recept2 = new Label("How many double duplex receptacles?");
		Button submit4 = new Button("Submit");
		submit4.setId("estButton");
		submit4.setPrefSize(BWIDTH, BHEIGHT);
		submit4.setOnAction(e -> {

			if (!(isInt(recNum1.getText()) && isInt(recNum2.getText()))) {
				//primaryStage.setScene(invalid);
                 ui.setScene(invalid);
			} else {
				//with database
				try {
					if(Integer.parseInt(recNum1.getText())!=0) {
						addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(recNum1.getText()));
						addPriceToCurrentList("1-gang box", "Old/New Work", Integer.parseInt(recNum1.getText()));
						addPriceToCurrentList("Wall Plate", "1-Gang Outlet", Integer.parseInt(recNum1.getText()));
					}
					if(Integer.parseInt(recNum2.getText())!=0) {
						addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(recNum2.getText()));
						addPriceToCurrentList("2-gang box", "Old/New Work", Integer.parseInt(recNum2.getText()));
						addPriceToCurrentList("Wall Plate", "2-Gang Outlet", Integer.parseInt(recNum2.getText()));
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
				//without database 
/**/				if (Integer.parseInt(recNum1.getText())!=0) {		 
					currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(recNum1.getText())));
						 total += 0.9 * Integer.parseInt(recNum1.getText());
				}
						 
				if (Integer.parseInt(recNum2.getText())!=0) {
					currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(recNum2.getText())));
					total += 1.3 * Integer.parseInt(recNum2.getText());
				}
               // with database	needs database connectivity			
/**/				
/*				try {
					if (applianceBranchCircuit(Integer.parseInt(recNum1.getText()),
							Integer.parseInt(recNum2.getText())) == 0) {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
*/			
				showList(ui);
			}
		});

		Button back4 = new Button("Back");
		back4.setId("estButton");
		back4.setPrefSize(BWIDTH, BHEIGHT);
		back4.setPrefSize(BW, BH);
		back4.setOnAction(e -> ui.setScene(scene1) /* primaryStage.setScene(scene1)*/);

		VBox layout4 = new VBox(14);
		//layout4.setAlignment(Pos.CENTER);
		layout4.getStylesheets().add(STYLE);
		layout4.setId("estBackground");
		layout4.getChildren().addAll(back4, menu4, recept1, recNum1, recept2, recNum2, submit4);
		//scene4 = new Scene(layout4, WIDTH, HEIGHT);
		scene4 = new Scene(layout4);
		// ---------------------------------------------------------------------//
		// Scene 5 Individual Circuit
		//----------------------------------------------------------------------//

		Label menu5 = new Label("Select a Circuit");
		menu5.setId("estText");
		Button microwave = new Button("Microwave");
		microwave.setPrefSize(BWIDTH, BHEIGHT);
		microwave.setId("estButton");
		microwave.setOnAction(e -> {
			brCir = 1;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			showList(ui);
		});
		Button drayer = new Button("Drayer");
		drayer.setPrefSize(BWIDTH, BHEIGHT);
		drayer.setId("estButton");
		drayer.setOnAction(e -> {
			brCir = 2;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
			
		});
		Button washer = new Button("Washer");
		washer.setPrefSize(BWIDTH, BHEIGHT);
		washer.setId("estButton");
		washer.setOnAction(e -> {
			brCir = 3;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
		});
		Button diswasher = new Button("Diswasher");
		diswasher.setPrefSize(BWIDTH, BHEIGHT);
		diswasher.setId("estButton");
		diswasher.setOnAction(e -> {
			brCir = 4;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
			
		});
		Button waterHeater = new Button("Water Heater");
		waterHeater.setPrefSize(BWIDTH, BHEIGHT);
		waterHeater.setId("estButton");
		waterHeater.setOnAction(e -> {
			brCir = 5;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
			
		});
		Button range = new Button("Range/CookTop");
		range.setPrefSize(BWIDTH, BHEIGHT);
		range.setId("estButton");
		range.setOnAction(e -> {
			brCir = 6;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
			
		});
		Button eVCharger = new Button("EV Charger");
		eVCharger.setPrefSize(BWIDTH, BHEIGHT);
		eVCharger.setId("estButton");
		eVCharger.setOnAction(e -> {
			brCir = 7;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
		});
		Button ACCondenser = new Button("Condenser AC Unit");
		ACCondenser.setPrefSize(BWIDTH, BHEIGHT);
		ACCondenser.setId("estButton");
		ACCondenser.setOnAction(e -> {
			brCir = 8;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
		});
		Button ACCentral = new Button("Central AC unit ");
		ACCentral.setPrefSize(BWIDTH, BHEIGHT);
		ACCentral.setId("estButton");
		ACCentral.setOnAction(e -> {
			brCir = 9;
			try {
				individualCircuit(brCir);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			showList(ui);
			
		});
		
		Button back5 = new Button("Back");
		back5.setPrefSize(BW, BH);
		back5.setId("estButton");
		back5.setOnAction(e -> ui.setScene(scene1) /*primaryStage.setScene(scene1)*/);

		VBox layout5 = new VBox(5);
		//layout5.setAlignment(Pos.CENTER);
		layout5.getStylesheets().add(STYLE);
		layout5.setId("estBackground");
		layout5.getChildren().addAll(back5, menu5, microwave, drayer, washer, diswasher, waterHeater, range, eVCharger,
				ACCondenser, ACCentral);
		//scene5 = new Scene(layout5, WIDTH, HEIGHT);
		scene5 = new Scene(layout5);

		//------------------------------------------------//
		//Scene 6 from Scene 5 Individual Circuit
		//------------------------------------------------//	
	
	  	Label menu6 = new Label("Please Choice");
		Button bath = new Button("Bathroon");
		bath.setId("estButton");
		bath.setPrefSize(BWIDTH, BHEIGHT);
		bath.setOnAction(e -> {
			isBath = true;
			ui.setScene(scene7);
		}); 

		Button  laundry = new Button("Laundry");
		laundry.setId("estButton");
		laundry.setPrefSize(BWIDTH, BHEIGHT);
		laundry.setOnAction(e -> {
			isBath = false;
			ui.setScene(scene7);
		});

		VBox layout6 = new VBox(5);
		//layout6.setAlignment(Pos.CENTER);
		layout6.getStylesheets().add(STYLE);
		layout6.setId("estBackground");
		layout6.getChildren().addAll( menu6, laundry, bath );
		//scene6 = new Scene(layout6, WIDTH, HEIGHT);
		scene6 = new Scene(layout6); 

		// -------------------------------------------------------------------//
		// Scene 7 bath/laundry
		//---------------------------------------------------------------------//
		
		Label menu7 = new Label("Enter The Number of Receptacles");
		menu7.setId("estText");
		TextField recNum5 = new TextField();
		TextField recNum6 = new TextField();
		recNum5.setMaxSize( TWIDTH, THEIGHT);
		recNum6.setMaxSize( TWIDTH, THEIGHT);
		recNum5.insertText(0, "0");
		recNum6.insertText(0, "0");
		Label recept5 = new Label("How many duplex or triplex receptacles?");
		Label recept6 = new Label("How many double duplex receptacles?");

		Button submit7 = new Button("Submit");
		submit7.setId("estButton");
		submit7.setPrefSize(BWIDTH, BHEIGHT);
		submit7.setOnAction(e -> {

			if (!(isInt(recNum5.getText()) && isInt(recNum6.getText()))) {
				//primaryStage.setScene(invalid);
				ui.setScene(invalid);
			} else {
		
				//with database
				 try {
					 if(Integer.parseInt(recNum5.getText())!=0) {
						 addPriceToCurrentList("Receptacle", "Duplex", Integer.parseInt(recNum5.getText()));
					     addPriceToCurrentList("1-gang box", "Old/New Work", Integer.parseInt(recNum5.getText()));
					     addPriceToCurrentList("Wall Plate", "1-Gang Outlet", Integer.parseInt(recNum5.getText()));
					 }
					 
					 if( Integer.parseInt(recNum6.getText())!=0) {
						 addPriceToCurrentList("Receptacle", "Double Duplex", Integer.parseInt(recNum6.getText()));
						 addPriceToCurrentList("2-gang box", "Old/New Work", Integer.parseInt(recNum6.getText()));
						 addPriceToCurrentList("Wall Plate", "2-Gang Outlet", Integer.parseInt(recNum6.getText()));
					 }
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
				 //without database 
/**/					if (Integer.parseInt(recNum5.getText())!=0) {		 
						currentList.add(new MaterialList("Receptacle", "Duplex", 0.9 , Integer.parseInt(recNum5.getText())));
							 total += 0.9 * Integer.parseInt(recNum5.getText());
					}
							 
					if (Integer.parseInt(recNum6.getText())!=0) {
						currentList.add(new MaterialList("Receptacle", "Double Duplex", 1.3, Integer.parseInt(recNum5.getText())));
						total += 1.3 * Integer.parseInt(recNum6.getText());
					} 
/**/
				
				 //with database needs database connectivity	
/*				try {
					if (bathLoundry(Integer.parseInt(recNum5.getText()), Integer.parseInt(recNum6.getText()), isBath) == 0) {
						currentList.clear();
					}
				} catch (NumberFormatException | InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
*/
				showList(ui);
			}

		});
		
		Button back7 = new Button("Back");
		back7.setPrefSize(BW, BH);
		back7.setId("estButton");
		back7.setOnAction(e -> ui.setScene(scene1)/*primaryStage.setScene(scene1)*/);

		VBox layout7 = new VBox(5);
		//layout7.setAlignment(Pos.CENTER);
		layout7.getStylesheets().add(STYLE);
		layout7.setId("estBackground");
		layout7.getChildren().addAll(back7, menu7, recept5, recNum5, recept6, recNum6, submit7);
		//scene7 = new Scene(layout7, WIDTH, HEIGHT);
		scene7 = new Scene(layout7);

		// -----------------------------------------------------//
		// Scene invalid
		//------------------------------------------------------//

		Button exitInv = new Button("Try Again");
		exitInv.setPrefSize(BWIDTH, BHEIGHT);
		exitInv.setId("estButton");
		exitInv.setOnAction(e -> ui.setScene(scene1) /* primaryStage.setScene(scene1)*/);

		Label menuInv = new Label("Invalid Input!!! Please Enter Positive Integers in the Text Field");
		//menu1.setAlignment(Pos.CENTER);

		VBox layoutInv = new VBox(5);
		//layoutInv.setAlignment(Pos.CENTER);
		layoutInv.getStylesheets().add(STYLE);
		layoutInv.setId("estBackground");
		layoutInv.getChildren().addAll(exitInv, menuInv);
		//invalid = new Scene(layoutInv, WIDTH, HEIGHT);
		invalid = new Scene(layoutInv);
		
			
			//---------------------------------------------------------------//
			//scene 10 empty 
			//---------------------------------------------------------------//
				
			Label menu10 = new Label("You have no saved lists");
			menu10.setId("estText");
			Button back10 = new Button("Back");
			back10.setPrefSize(BW, BH);
			back10.setId("estButton");
			back10.setOnAction(e -> ui.setScene(scene1) /*primaryStage.setScene(scene1)*/);

			VBox layout10 = new VBox(5);
			//layout10.setAlignment(Pos.CENTER);
			layout10.getStylesheets().add(STYLE);
			layout10.setId("estBackground");
			layout10.getChildren().addAll(back10, menu10);
			//scene10 = new Scene(layout10, WIDTH, HEIGHT);
			scene10 = new Scene(layout10);
				
			return 	scene1;
				
				//--------------//
				// show time 
				//---------------//
				//primaryStage.setTitle("Electrician Toolkit");
			    //primaryStage.setScene(scene1);
			    //primaryStage.show();
	}

	/**
	 * This method displays material list, price, and circuit info.
	 * @param ui user interface from main class
	 */
	public static void showList(UIManager ui) {

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
		save.setId("estButton");
		save.setOnAction(e -> {
			try {
				saveCurrentList();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			total = 0.00;
			currentList.clear();
	  		//primaryStage.setScene(scene1);
	  		ui.setScene(scene1);
		   	
		});

		Button back = new Button("Go Back to Estimates");
		back.setPrefSize(BWIDTH, BHEIGHT);
		back.setId("estButton");
		back.setOnAction(e -> {
			total = 0.00;
			currentList.clear();
			//primaryStage.setScene(scene1);
			ui.setScene(scene1);
		});
	    
		message2 = "Total: $"+total;
		Text text = new Text(20, 20, message);
		Text text2 = new Text(20, 20, message2);
		Text text3 = new Text(20, 20,"You may also need: wire connectors, staples, tape, swithes, wire crimps, ligth bulbs");
		text.setId("estText");
		text2.setId("estText");
		VBox layout = new VBox(5);
		//layout.setAlignment(Pos.CENTER);
		layout.getStylesheets().add(STYLE);
		layout.setId("estBackground");
		layout.getChildren().addAll(text, table, text2, text3 ,save, back);
		//scene8 = new Scene(layout, WIDTH, HEIGHT);
		//primaryStage.setScene(scene8);
		scene8 = new Scene(layout);
		ui.setScene(scene8);
		
	}
	
	/**
	 * This method displays material list, price, and circuit info store in database.
	 * @param ui user interface from main class
	 */
	public static void showSavedList(UIManager ui) {

		//--------------------------------------------------//
		//scene 9 Table
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
		next.setId("estButton");
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
			//primaryStage.setScene(scene10);
			currentList.clear();
			ui.setScene(scene10);
		}else {
			try {
				currentList.clear();
				updateCurrentList(myLists.get(myListIndex));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			showSavedList(ui);
		}
		
		} );

		Button back = new Button("Next");
		back.setPrefSize(BWIDTH, BHEIGHT);
		back.setId("estButton");
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
			showSavedList(ui);
			
		});
	
		Button back2 = new Button("Go Back to Estimates");
		back2.setPrefSize(BWIDTH, BHEIGHT);
		back2.setId("estButton");
		back2.setOnAction(e -> {
			currentList.clear();
			//primaryStage.setScene(scene1);
			ui.setScene(scene1);
		});
		message2 = "Total: $"+total;
		total = 0.00;
		Text text = new Text(20, 20, "SAVED LISTS");
		Text text2 = new Text(20, 20, message);
		Text text3 = new Text(20, 20, message2);
		Text text4 = new Text(20, 20,"You may also need: wire connectors, staples, tape, swithes, wire crimps, ligth bulbs");
		text.setId("estText");
		text2.setId("estText");
		VBox layout = new VBox(5);
		//layout.setAlignment(Pos.CENTER);
		layout.getStylesheets().add(STYLE);
		layout.setId("estBackground");
		layout.getChildren().addAll(text, text2,table, text3, text4, next, back, back2);
		//scene9 = new Scene(layout, WIDTH, HEIGHT);
		//primaryStage.setScene(scene8);
		scene9 = new Scene(layout);
		ui.setScene(scene9);
		
	}
	
		

	//-----------------------------------------------------------------------------------------//
	//                                          Logic
	//-----------------------------------------------------------------------------------------//
	
	/**
	 * This method returns the minimum number of circuits require based on the circuit load
	 * @param dT duplex or triplex receptacle quantity.
	 * @param dD double duplex receptacle quantity
	 * @param constantLoad load of lighting circuits in watts  
	 * @param breakerSide overcurrent protection 
	 * @return minimum number of circuits 
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
        
		//with database  needs database connectivity	
/*		if(numberOfCircuits!=0) {
			addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole", numberOfCircuits);					
			int temp = 10 *(dT +dD); 
			temp +=  (int) (0.1* (double)constantLoad);
			if(breakerSide == 20) {	addPriceToCurrentList("Wire", "12-2", temp);}
			else if(breakerSide == 15) { addPriceToCurrentList("Wire", "14-2", temp);}		
		}
*/		
		//without database
/**/		if(numberOfCircuits!=0) {
			currentList.add(new MaterialList("Circuit Breaker", "" + breakerSide + "-Amp 1-Pole", 8.5, numberOfCircuits));					
			int temp = 10 *(dT +dD); 
			temp +=  (int) (0.1* (double)constantLoad);
			if(breakerSide == 20) {	currentList.add(new MaterialList("Wire", "12-2", 0.23, temp)); total += 0.23*temp;}
			else if(breakerSide == 15) { currentList.add(new MaterialList("Wire", "14-2", 0.3, temp)); total += 0.23*temp;}	
		} 
/**/
		message = "Total circuits: " + numberOfCircuits + "  Ligth circuits: " + numOfLighCircuit
				+ "  Receptacle Circuits: " + numOfRecCircuit + "  Mixed circuits: " + mixCir;	

		return numberOfCircuits;
	}

	/**
	 * This method returns the minimum number of circuits require based on the circuit load
	 * @param dT duplex or triplex receptacle quantity.
	 * @param dD double duplex receptacle quantity
	 * @return the minimum number of circuits require based on the circuit load
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

		//with database needs database connectivity	
/*		if(numberOfCircuits!=0) {
			 addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole", numberOfCircuits);
			 int temp = 10 *(dT + dD); 
			 addPriceToCurrentList("Wire", "12-2", temp);
		 } 
*/		 
		//without database
/**/		 if(numberOfCircuits!=0) {
			 currentList.add(new MaterialList("Circuit Breaker", ""+ breakerSide+"-Amp 1-Pole", 8.5, numberOfCircuits));
			 int temp = 10 *(dT + dD); 
			 currentList.add(new MaterialList("Wire", "12-2", 1.3, temp));
			 total += 1.3*temp;
		 } 
/**/			
		message = "Total circuits: " + numberOfCircuits+ " (Kitchen)";	
		return numberOfCircuits;
	}

	/**
	 * This method updates current list with info for a particular circuit to be displayed. 
	 * @param brCir circuit type
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
			addPriceToCurrentList("Circuit Breaker", "20-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "12-2", 100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			addPriceToCurrentList("Wall Plate", "1-Gang Outlet",1);
			 
			message = "Microwave Circuit";
			break;
		case 2:
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1 ); 
			addPriceToCurrentList("Wire", "10-3", 100 );
			addPriceToCurrentList("Receptacle", "30A 125/250", 1 );
			addPriceToCurrentList("2-gang box", "Old/New Work", 1 );
			addPriceToCurrentList("Wall Plate", "2-Gang Double Round" ,1);
			message = "Drayer Circuit";
			break;
		case 3:
			addPriceToCurrentList("Circuit Breaker","20-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "12-2",  100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			addPriceToCurrentList("Wall Plate", "1-Gang Outlet",1);
			message = "Washer Circuit";
			break;
		case 4:
			addPriceToCurrentList("Circuit Breaker", "15-Amp 1-Pole", 1);
			addPriceToCurrentList("Wire", "14-2", 100);
			addPriceToCurrentList("Receptacle", "Duplex", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			addPriceToCurrentList("Wall Plate", "1-Gang Outlet",1);
			message = "Diswasher Circuit";
			break;
		case 5:
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "10-2", 100);
			message = "Water Heater Circuit";	
			break;
		case 6:
			addPriceToCurrentList("Circuit Breaker", "50-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "6-3", 100);
			addPriceToCurrentList("Receptacle", "50A 125/250", 1);
			addPriceToCurrentList("2-gang box", "Old/New Work", 1);
			addPriceToCurrentList("Wall Plate", "2-Gang Double Round",1);
			message = "Range Circuit";
			break;
		case 7:
			addPriceToCurrentList("Circuit Breaker", "50-Amp 2-Pole",1);
			addPriceToCurrentList("Wire", "6-3", 100);
			addPriceToCurrentList("Receptacle", "50A 125/250", 1);
			addPriceToCurrentList("1-gang box", "Old/New Work", 1);
			addPriceToCurrentList("Wall Plate", "2-Gang Double Round ",1);
			message = "EV Charger Circuit";		
			break;
		case 8:
			addPriceToCurrentList("Circuit Breaker", "30-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "10-2", 100);
			addPriceToCurrentList("Fused AC Disconnect", "30 amps 2-Pole", 1);
			message = "AC Condenser Circuit";
			break;
		case 9:
			addPriceToCurrentList("Circuit Breaker", "60-Amp 2-Pole", 1);
			addPriceToCurrentList("Wire", "6-3", 50);
			message = "AC Central Circuit";
			break;
		default:
			message = "Total circuits: 0";
			break;
		}
		
	}
    
	/**
	 * This method returns the minimum number of circuits required. That number  depends on  
	 * the number of double and double duplex receptacles. This method also updates the global message. 
	 *    
	 * @param dT double or triple receptacle. 
	 * @param dD double duplex receptacle. 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int bathLoundry(int dT, int dD, boolean isBath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {		
		
		int numberOfCircuits = 0, totalPower = 0, breakerSide = 20; 
		String bth = "";
		double totalAmps = 0, volt = 120;
		totalPower += (dT * 180);
		totalPower += (dD * 360);

		totalAmps = (double) totalPower / volt;
		numberOfCircuits = (int) totalAmps / breakerSide;
		totalAmps %= breakerSide;

		if (totalAmps != 0)
			numberOfCircuits++;
		
		//with database needs database connectivity	
/*		if(isBath) {
			if(numberOfCircuits!=0) {
				addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole GFCI",  numberOfCircuits);
				int temp = 10 *(dT + dD); 
				addPriceToCurrentList("Wire", "12-2", temp);
				bth = "Bathroom";
			}
		}else {
			if(numberOfCircuits!=0) {
				addPriceToCurrentList("Circuit Breaker", breakerSide+"-Amp 1-Pole",  numberOfCircuits);
				int temp = 10 *(dT + dD); 
				addPriceToCurrentList("Wire", "12-2", temp);
				bth = "Laundry";
			}
		}
*/	
		//without database
/**/		if(numberOfCircuits!=0) {
			currentList.add(new MaterialList("Circuit Breaker", ""+ breakerSide+"-Amp 1-Pole", 8.5, numberOfCircuits));
			int temp = 10 *(dT + dD);
			currentList.add(new MaterialList("Wire", "12-2", 1.3, temp));
			total += 1.3*temp;
		} 
/**/		
		message = "Total circuits: " + numberOfCircuits +" ("+bth+")";
		return numberOfCircuits;
	}

	/**
	 * This method adds the elements from currentList to materials (observable list)
	 * so that the elements can be display.
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
	
	//--------------------------------------------------------------------//
	//                             Database
	//-------------------------------------------------------------------//
	
	/**
	 * This method stores 'name', 'type', 'price', and 'quantity' into currentList. This is the information of one
	 * element of the list. The price is retrieve from the database using 'name' and 'type'.  
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
		total = Math.round(total *100.0) /100.0;
		currentList.add(new MaterialList(name, type, price, quantity));
		st.close();
		connection.close();
		
	}
	/**
	 * This method save the current calculation done by the user into the database.
	 * This method also stores the values of the globals 'message' and 'total' in a different table 
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void  saveCurrentList() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url2, username, password);
		Statement st = connection.createStatement();
		String name = "";
		String type = "";
		double price = 0;
		int quantity = 0;
		int listNumber= 0; 

		//check if table name already exists and update listNumber so is not repeated
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables;
		boolean exists = true;
			while (exists){
				tables = dbm.getTables(null, null, "saved_list_"+listNumber, null);
				if (tables.next()) {
					// Table exists
					 listNumber++;
				}
				else {
					// Table does not exist
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
		
		st.executeUpdate(query);  
		
		//inserts values into the created table
		String query2="";			
		for(int i = 0; i < currentList.size(); i++) {
		 	name = currentList.get(i).getName();
		 	type = currentList.get(i).getType();
		 	price = currentList.get(i).getPrice();
		 	quantity = currentList.get(i).getQuantity();		
		 	query2 = "INSERT INTO `saved`.`saved_list_"+listNumber+"` (`name`, `type`, `price`, `quantity`)"
		 	 		+ " VALUES ('"+name+"', '"+type+"', '"+price+"', '"+quantity+"');";
		 	System.out.println(st.executeUpdate(query2)); // change this	
		}
		
		//stores message and total in a different table
		String query3 ="INSERT INTO `electrician`.`table_info` (`table_id`,`name`, `message`, `total`)"
				+ "VALUES ('"+listNumber+"' , 'saved_list_"+listNumber+"' , '"+message+"', '"+total+"');";
			
		st.executeUpdate(query3);
					
		st.close();
		connection.close();
		
	}
	/**
	 * This method retrieves the names of lists saved by the user and place them into myList. 
	 * @return true if there is at least one list, false otherwise.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	/**
	 * This method update currentList with the content of tableName from MySQL.
	 * This method also update the globals message and total with the corresponding values to the table.
	 *  
	 * @param tableName contains the name of the list saves by the user stored in MySQL.
	 * @throws SQLException 
	 */
	public static void updateCurrentList(String tableName) throws SQLException {
		String name="", type=""; double price=0; int quantity=0;
		connection = DriverManager.getConnection(url, username, password);
		Statement st = connection.createStatement();
		ResultSet rs;
	    String query = "SELECT * from saved."+tableName+";";
		rs = st.executeQuery(query);
		
		// updates currentList
		while(rs.next()) {			
			name = rs.getString("name");
			type = rs.getString("type");
			price = rs.getDouble("price");
			quantity = rs.getInt("quantity");
			currentList.add(new MaterialList(name, type, price, quantity));
		}
		
		// gets the message and total from table_info
		String query2 = "SELECT total from electrician.table_info where name = '"+tableName+"';";
		rs = st.executeQuery(query2);
		rs.next();
		total = rs.getDouble("total");
		query2 = "SELECT message from electrician.table_info where name = '"+tableName+"';";
		rs = st.executeQuery(query2);
		rs.next();
		message = rs.getString("message");
		
		st.close();
		connection.close();
	}
	/**
	 * This method deletes tableName from MySQL and removes tableName from myList. This method is designed 
	 * to deleted the saved lists from the user.
	 *  
	 * @param tableName is the name of the list saved by the user, stored in MySQL.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void  deleteTable(String tableName, int index) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url2, username, password);
		Statement st = connection.createStatement();
		
		//check tableName-----------------------------------------------------------
		
		//deletes table and remove index
		String query = "DROP TABLE `saved`.`"+tableName+"`;";
		st.executeUpdate(query);
		myLists.remove(index);
		// deletes total and message
		String query2 ="DELETE FROM `electrician`.`table_info` WHERE (`name` = '"+tableName+"')";
		st.executeUpdate(query2);
		
		st.close();
		connection.close();
	}

	/**
	 * This function returns true if the string is an integer, false otherwise.
	 *
	 * @param number string that must be a  number.
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
