




/*
 * 
 * Errors:
 * 		-scroll bars being cut out of window [Partially fixed]
 * 
 */


import java.util.regex.Pattern;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.control.TextArea;

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
		final UIManager ui = new UIManager();
		
		//VBox mainBox = new VBox(5);
		//mainBox.setAlignment(Pos.CENTER);
		
		Label mainLabel = new Label("Electrical Resources:");
		mainLabel.setId("MainLabel");
		VBox labelBox = new VBox(mainLabel);
		labelBox.setAlignment(Pos.CENTER);
		
		FilteredDropDown favoritesDrop = new FilteredDropDown("Favorites", ui);
		favoritesDrop.add(new FavoritesTool(ui));
		
		FilteredDropDown notesDrop = new FilteredDropDown("Notes", ui);
		notesDrop.add(new NotesTool());
		
		FilteredDropDown ohmsDrop = new FilteredDropDown("Ohm's Law", ui);
		
		FilteredDropDown circuitsDrop = new FilteredDropDown("Circuits", ui);
		
		//FilteredDropDown estimateDrop = new FilteredDropDown("Estimates", ui);
		//estimateDrop.setToExternal(new EstimateTool(ui));
		//estimateDrop.setToExternal(new Label("Test"));
		Button estimateDrop = new Button("Estimates");
		estimateDrop.setId("DropButton");
		estimateDrop.setPrefSize(400, 60);
		estimateDrop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//StyledScene page = new StyledScene(new ExternalPage(ui,new EstimateTool(ui)));
				//ui.setScene(page.getScene());
				EstimateTool.display();
			}
		});
		
		FilteredDropDown calcDrop = new FilteredDropDown("Calculator", ui);
		calcDrop.add(new CalculatorTool());
		calcDrop.add(new CalculatorTool());
		calcDrop.add(new CalculatorTool());
		
		FilteredDropDown historyDrop = new FilteredDropDown("History", ui);
		
		// list of all main buttons
		ArrayList<Node> listItems = new ArrayList<Node>();
		listItems.add(labelBox);
		listItems.add(favoritesDrop);
		listItems.add(ohmsDrop);
		listItems.add(circuitsDrop);
		listItems.add(estimateDrop);
		listItems.add(calcDrop);
		listItems.add(historyDrop);
		listItems.add(notesDrop);
		
	
		
		//VBox outerWrap = new VBox(mainBox);
		//outerWrap.setId("ContentBox");
		//VBox.setMargin(mainBox, new Insets(60,60,60,60));
		//outerWrap.setAlignment(Pos.CENTER);
		
		//ScrollPane mainScroll = new ScrollPane(outerWrap);
		//outerWrap.translateXProperty().bind(mainScroll.widthProperty().subtract(outerWrap.widthProperty()).divide(2));					
		
		//mainScroll.setMaxHeight(600);
		//mainScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		//mainScroll.setBackground(Background.EMPTY);
		
		
		MainBar mainBar = new MainBar(ui);
		
		MenuItem view0 = new MenuItem("Tabs");
		view0.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ui.setToTabs();
			}
		});
		
		MenuItem view1 = new MenuItem("Pages");
		view1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//System.out.println("view 1 chosen");
				ui.setToPages();
			}
		});
	
		MenuButton view = new MenuButton("View", null, view0, view1);
		mainBar.addNode(view);
		
		Node[] nodes = new Node[listItems.size()];
		for(int i=0; i<nodes.length; i++)
			nodes[i] = listItems.get(i);
		CustomScroll mainScroll = new CustomScroll(nodes);
		
		VBox outermostBox = new VBox(mainBar, mainScroll);
		outermostBox.setId("MainBox");
		outermostBox.setAlignment(Pos.TOP_CENTER);
		outermostBox.setFillWidth(true);
		//outermostBox.setPrefSize(600,625);
		StyledScene mainScene = new StyledScene(outermostBox);
		//StyledScene mainScene = new StyledScene(mainBar, mainScroll);
		
		// initial size of program window
		primaryStage.setWidth(630);
		primaryStage.setHeight(675);
		
		outermostBox.prefWidthProperty().bind(primaryStage.widthProperty().subtract(13.5));
		outermostBox.prefHeightProperty().bind(primaryStage.heightProperty().subtract(13.5));
		mainScroll.prefHeightProperty().bind(outermostBox.heightProperty().subtract(50));
		/*outermostBox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.978));
		outermostBox.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.94));
		*/
		
		ui.set(primaryStage, mainScene.getScene(), mainScroll);
		
		primaryStage.setScene(mainScene.getScene());
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
 * UI Manager
 * 
 */

class UIManager {
	
	Stage primary;
	Scene main;
	VBox mainBox;
	CustomScroll scroll;
	
	// 0 for tabs, 1, external pages
	int view;
	
	public UIManager() {
		// default
	}
	
	public UIManager(Stage stage, Scene scene) {
		primary = stage;
		main = scene;
	}
	
	public void set(Stage stage, Scene scene, CustomScroll scroll) {
		primary = stage;
		main = scene;
		this.scroll = scroll;
		mainBox = scroll.getMainBox();
	}
	
	public void setToTabs() {
		view = 0;
	}
	
	public void setToPages() {
		view = 1;
	}
	
	public int getView() {
		return view;
	}
	
	public Stage getStage() {
		return primary;
	}

	public Scene getScene() {
		return main;
	}
	
	public void resetScene() {
		primary.setScene(main);
	}
	
	public void setScene(Scene scene) {
		primary.setScene(scene);
	}
	
	public boolean isMainScene() {
		return primary.getScene().equals(main);
	}
	
	public VBox getVBox() {
		return mainBox;
	}
	
	public void closeAll() {
		ObservableList<Node> children = mainBox.getChildren();
		
		for(int i=0; i<children.size(); i++) {
			Node child = children.get(i);
			if(child.getClass().getSimpleName() == "FilteredDropDown") {
				DropDown drop = ((FilteredDropDown)(child)).getDrop();
				drop.closeContent();
			}
		}
	}
	
	public Tool getToolByName(String name) {
		if(name == "Calculator") {
			return new CalculatorTool();
		}
		else if(name == "") {
			
		}
		
		return null;
	}
	
	public String[] getToolNames() {
		String[] names = {"",
				"Calculator"};
		
		return names;
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
	EventHandler<ActionEvent> defaultClick = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			if(outerBox.getChildren().contains(dropBox))
				outerBox.getChildren().remove(dropBox);
			else
				outerBox.getChildren().add(dropBox);
		}
	};
	
	public DropDown(String name) {
		//this.setAlignment(Pos.CENTER);
		this.setId("DropDown");
		
		outerBox = new VBox();
		this.getChildren().add(outerBox);
		
		dropBox = new VBox();
		dropBox.setId("DropBox");
		VBox.setMargin(dropBox, new Insets(0,0,0,20));
		
		button= new Button(name);
		button.setId("DropButton");
		button.setPrefSize(400, 60);
		
		button.setOnAction(defaultClick);
		
		outerBox.getChildren().add(button);
	}
	
	public DropDown(String name, double width, double height) {
		//this.setAlignment(Pos.CENTER);
		this.setId("DropDown");
		
		outerBox = new VBox();
		this.getChildren().add(outerBox);
		
		dropBox = new VBox();
		dropBox.setId("DropBox");
		VBox.setMargin(dropBox, new Insets(0,0,0,20));
		
		button= new Button(name);
		button.setId("DropButton");
		button.setPrefSize(width, height);
		
		button.setOnAction(defaultClick);
		
		outerBox.getChildren().add(button);
	}
	
	public void clearDrop() {
		dropBox.getChildren().clear();
	}
	
	public void setDrop(Node...nodes) {
		clearDrop();
		add(nodes);
	}
	
	public VBox getDropBox() {
		return dropBox;
	}
	
	public VBox getOuterBox() {
		return outerBox;
	}
	
	public void add(Node node) {
		dropBox.getChildren().add(node);
		node.setId("DropItem");
		//VBox.setMargin(node, new Insets(10,10,10,10));
	}
	
	public void add(Node...nodes) {
		for(int i=0; i<nodes.length; i++) {
			add(nodes[i]);
		}
	}
	
	public void remove(Node node) {
		dropBox.getChildren().remove(node);
	}
	
	public void click() {
		button.fire();
	}
	
	public EventHandler<ActionEvent> getDefaultClick() {
		return defaultClick;
	}
	
	public void setClick(EventHandler<ActionEvent> handler) {
		button.setOnAction(handler);
	}
	
	public boolean isDropped() {
		return outerBox.getChildren().contains(dropBox);
	}
	
	public void dropContent() {
		if(!outerBox.getChildren().contains(dropBox))
			outerBox.getChildren().add(dropBox);
	}
	
	public void closeContent() {
		if(outerBox.getChildren().contains(dropBox))
			outerBox.getChildren().remove(dropBox);
	}
	
}

class FilteredDropDown extends Region {
	
	UIManager ui;
	DropDown drop;
	Insets insets;
	EventHandler<ActionEvent> defaultHandler;
	
	public FilteredDropDown(String name, UIManager ui) {
		drop = new DropDown(name);
		this.getChildren().add(drop);
		this.ui = ui;
		insets = this.getInsets();
		
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// normal drop behavior
				if(ui.getView()==0 || !ui.isMainScene()) {
					if(drop.getOuterBox().getChildren().contains(drop.getDropBox()))
						drop.getOuterBox().getChildren().remove(drop.getDropBox());
					else
						drop.getOuterBox().getChildren().add(drop.getDropBox());
				}
				// external page behavior
				else {
					Button back = new Button("Back");
					back.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							//ui.getVBox().getChildren().add(position, drop);
							//VBox.setMargin(drop, new Insets(0,0,0,0));
							addDrop(drop);
							ui.resetScene();
						}
					});
					
					/*
					ToolBar bar = new ToolBar(back);
					CustomScroll scroll = new CustomScroll(drop);
					VBox wrap = new VBox(bar, scroll);
					wrap.setId("MainBox");
					
					wrap.prefWidthProperty().bind(ui.getStage().widthProperty().subtract(13.5));
					wrap.prefHeightProperty().bind(ui.getStage().heightProperty().subtract(13.5));
					scroll.prefHeightProperty().bind(wrap.heightProperty().subtract(50));
					
					drop.dropContent();*/
					
					drop.dropContent();
					ExternalPage page = new ExternalPage(ui, drop);
					page.setBarItems(back);
					
					StyledScene external = new StyledScene(page);
					ui.setScene(external.getScene());
				}
			}
		};
		
		defaultHandler = handler;
		drop.setClick(handler);
	}
	
	public void setToDefault() {
		drop.setClick(defaultHandler);
	}
	
	public void setToExternal(Node content) {
		ExternalPage page = new ExternalPage(ui, content);
		
		EventHandler<ActionEvent> externalHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				StyledScene external = new StyledScene(page);
				ui.setScene(external.getScene());
				ui.setScene(new Scene(page));
			}
		};
		drop.setClick(externalHandler);
	}
	
	public void addDrop(DropDown drop) {
		this.getChildren().clear();
		this.getChildren().add(drop);
		VBox.setMargin(this, insets);
	}
	
	public void add(Node node) {
		drop.add(node);
	}
	
	public DropDown getDrop() {
		return drop;
	}
	
}

/**
 * External page custom pane
 * 
 * @author RyanS
 *
 */
class ExternalPage extends VBox {
	
	UIManager ui;
	CustomScroll scroll;
	SubBar bar;
	
	public ExternalPage(UIManager UIM) {
		super();
		this.setId("MainBox");
		this.ui = UIM;
		
		CustomScroll contentScroll = new CustomScroll();
		SubBar sub = new SubBar(ui);
		
		this.scroll = contentScroll;
		this.bar = sub;
		
		this.prefWidthProperty().bind(ui.getStage().widthProperty().subtract(13.5));
		this.prefHeightProperty().bind(ui.getStage().heightProperty().subtract(13.5));
		scroll.prefHeightProperty().bind(this.heightProperty().subtract(50));
		
		this.getChildren().addAll(bar, scroll);
	}
	
	public ExternalPage(UIManager UIM, Node... nodes) {
		super();
		this.setId("MainBox");
		this.ui = UIM;
		
		CustomScroll contentScroll = new CustomScroll(nodes);
		SubBar sub = new SubBar(ui);
		
		this.scroll = contentScroll;
		this.bar = sub;
		this.getChildren().addAll(bar, scroll);
		
		this.prefWidthProperty().bind(ui.getStage().widthProperty().subtract(13.5));
		this.prefHeightProperty().bind(ui.getStage().heightProperty().subtract(13.5));
		scroll.prefHeightProperty().bind(this.heightProperty().subtract(50));
	}
	
	public void setBarItems(Node... nodes) {
		bar.setNodes(nodes);
	}
	
	public void setPage(Node... nodes) {
		scroll.setItems(nodes);
	}
	
	public CustomScroll getScroll() {
		return scroll;
	}
	
	public SubBar getBar() {
		return bar;
	}
	
}


class ToolBar extends HBox {
	
	public ToolBar() {
		// default
		this.setId("ToolBar");
	}
	
	public ToolBar(Node... nodes) {
		this.setId("ToolBar");
		for(int i=0; i<nodes.length; i++)
			addNode(nodes[i]);
	}
	
	public void addNode(Node node) {
		this.getChildren().add(node);
	}
	
	public void addButton(Button button) {
		button.setId("ToolBarButton");
		this.getChildren().add(button);
	}
	
	public void addNodes(Node... nodes) {
		for(int i=0; i<nodes.length; i++)
			addNode(nodes[i]);
	}
	
	public void removeNodes() {
		this.getChildren().clear();
	}
	
}

class MainBar extends ToolBar {
		
	UIManager ui;
	
	public MainBar(UIManager ui) {
		super();
		super.addNodes(buildBar());
		this.ui = ui;
	}
	
	public Node[] buildBar() {
		Button save = new Button("Save");
		Button clear = new Button("Clear");
		Button close = new Button("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ui.closeAll();
			}
		});
		
		
		return (new Node[]{save,clear,close});
	}
	
}

class SubBar extends ToolBar {
	
	UIManager ui;
	
	public SubBar(UIManager ui) {
		super();
		super.addNodes(buildBar());
		this.ui = ui;
	}
	
	public Node[] buildBar() {
		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ui.resetScene();
			}
		});
		
		
		return (new Node[]{back});
	}
	
	public void addButton(Button button) {
		super.addButton(button);
	}
	
	public void setNodes(Node... nodes) {
		super.removeNodes();
		super.addNodes(nodes);
	}
	
}

class StyledScene {
	
	Scene scene;
	
	public StyledScene(Parent... parents) {
		if(parents.length==0)
			return;
		
		FlowPane pane = new FlowPane();
		for(int i=0; i<parents.length; i++)
			pane.getChildren().add(parents[i]);
		
		scene = new Scene(pane);
		try {
			URL styleURL = getClass().getResource("/resources/toolkit_style.css");
			String stylePath = styleURL.toExternalForm();
			
			scene.getStylesheets().add(stylePath);
		}
		catch(Exception ex) {
			System.out.println("Stylesheet error");
		}
	}
	
	public Scene getScene() {
		return scene;
	}
	
}


class CustomScroll extends ScrollPane {
	
	VBox mainBox;
	
	public CustomScroll(Node... nodes) {
		setItems(nodes);
	}
	
	public void setItems(Node... nodes) {
		mainBox = new VBox(5);
		mainBox.setAlignment(Pos.CENTER);
		
		for(int i=0; i<nodes.length; i++) {
			VBox.setMargin(nodes[i], new Insets(0,40,0,40));
			mainBox.getChildren().add(nodes[i]);
		}
		
		VBox.setMargin(mainBox, new Insets(60,60,60,60));
		//VBox.setVgrow(mainBox, Priority.ALWAYS);
		
		VBox outerWrap = new VBox(mainBox);
		//VBox.setVgrow(outerWrap, Priority.NEVER);
		outerWrap.setId("ContentBox");
		//outerWrap.setAlignment(Pos.CENTER);
		outerWrap.translateXProperty().bind(this.widthProperty().subtract(outerWrap.widthProperty()).divide(2));					
		//outerWrap.setPrefHeight(600);
		
		this.setContent(outerWrap);
		//this.setMaxHeight(400);
		this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setBackground(Background.EMPTY);
	}
	
	public VBox getMainBox() {
		return mainBox;
	}
	
}

/**
 * VBox of CheckBox nodes
 * @author RyanS
 *
 */
class CheckList extends VBox {
	
	public CheckList(CheckBox... boxes) {
		this.getChildren().addAll(boxes);
	}
	
	// each index represents check box, cell in array is 1 if checked
	public int[] getIntArray() {
		ObservableList<Node> checkList = this.getChildren();
		int[] array = new int[checkList.size()];
		
		for(int i=0; i<array.length; i++) {
			try {
				CheckBox box = (CheckBox)checkList.get(i);
				box.setIndeterminate(false);
		
				if(box.isSelected())
					array[i] = 1;
				else
					array[i] = 0;
			}
			catch(Exception e) {
				array[i] = 0;
			}
		}
		
		return array;
	}
	
	// returns list of check box names
	public String[] getNameArray() {
		ObservableList<Node> checkList = this.getChildren();
		String[] array = new String[checkList.size()];

		for (int i = 0; i < array.length; i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				array[i] = box.getText();

			} catch (Exception e) {
				array[i] = "";
			}
		}

		return array;
	}

}




/*
 * Tools:
 */




/**
 * 
 * @author RyanS
 *
 */
class FavoritesTool extends Pane {
	
	UIManager ui;
	DropDown modify;
	DropDown toolDrop;
	
	public FavoritesTool(UIManager ui) {
		this.ui = ui;
		CheckList checks = new CheckList();
		
		// updates tool list according to check boxes
		Button update = new Button("Update");
		update.setOnAction(e -> {
			setFavorites(checks.getIntArray(), checks.getNameArray());
		});
		
		//Check Boxes
		String[] toolNames = ui.getToolNames();
		for(int i=0; i<toolNames.length; i++) {
			checks.getChildren().add(new CheckBox(toolNames[i]));
		}
		
		VBox checksWrap = new VBox(checks);
		checksWrap.setSpacing(5);
		VBox.setMargin(checks, new Insets(5,5,5,5));
		
		modify = new DropDown("Modify", 200, 30);
		modify.add(checksWrap);
		
		toolDrop = new DropDown("Tools", 200, 30);
		
		HBox modBox = new HBox(modify, update);
		modBox.setSpacing(5);
		
		VBox wrap = new VBox(modBox, toolDrop);
		//VBox.setMargin(toolDrop, new Insets(40, 20, 20, 20));
		wrap.setSpacing(10);
		
		VBox outerWrap = new VBox(wrap);
		VBox.setMargin(wrap, new Insets(10,10,10,10));
		
		this.getChildren().addAll(outerWrap);
	}
	
	public void setFavorites(int[] checks, String[] names) {
		toolDrop.clearDrop();
		
		if(checks.length != names.length)
			System.out.println("Error: setting favorites");
		
		// check check box and add by name...
		for(int i=0; i<checks.length; i++) {
			if(checks[i] == 1) {
				toolDrop.add(ui.getToolByName(names[i]));
			}
		}
	}
	
	public String printResult() {
		return null;
	}
	
	public void clearDisplay() {
		
	}
	
}

class NotesTool extends Pane {
	
	TextArea text;
	
	public NotesTool() {
		text = new TextArea();
		text.setPrefSize(340, 300);
		
		Button clear = new Button("Clear");
		clear.setOnAction(e -> {
			text.clear();
		});
		
		VBox textWrap = new VBox(clear, text);
		textWrap.setSpacing(5);
		
		VBox outerWrap = new VBox(textWrap);
		VBox.setMargin(textWrap, new Insets(10,10,10,10));
		
		this.getChildren().add(outerWrap);
	}
	
}

class HistoryTool extends Pane {
	
	
	
}


/*
 * Sub-tools
 */

/**
 * Abstract class for sub-tools
 * @author RyanS
 *
 */
abstract class Tool extends Pane {
	
	abstract public String getToolName();
	abstract public String printResult();
	abstract public void clearDisplay();
	
}

/**
 * 
 * @author RyanS
 *
 */
class CalculatorTool extends Tool {
	
	String name = "Calculator";
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
				}
				catch(Exception ex) {
					result.setText("ERROR");
				}
			}
		});;
		
		HBox inputBox = new HBox(input, equals, result);
		VBox outerBox = new VBox(inputBox, enter);
		VBox.setMargin(inputBox, new Insets(10,10,5,10));
		VBox.setMargin(enter, new Insets(5,10,10,10));
		
		this.getChildren().add(outerBox);
	}
	
	public String getToolName() {
		return name;
	}
	
	public String printResult() {
		return input.getText() + " = " + result.getText();
	}
	
	public void clearDisplay() {
		input.clear();
		result.setText("");
	}
	
}







