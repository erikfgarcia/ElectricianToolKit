




/*
 * 
 * Errors/Requirements:
 * 		- scroll bars being cut out of window [Partially fixed]
 * 		- make ohms/circuits/voltage-drop tools
 * 		- refine UI
 * 
 */


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
		FavoritesTool favorites = new FavoritesTool(ui);
		favoritesDrop.add(favorites);
		
		FilteredDropDown notesDrop = new FilteredDropDown("Notes", ui);
		NotesTool notes = new NotesTool();
		notesDrop.add(notes);
		
		FilteredDropDown ohmsDrop = new FilteredDropDown("Ohm's Law", ui);
		
		FilteredDropDown circuitsDrop = new FilteredDropDown("Circuits", ui);
		
		//FilteredDropDown estimateDrop = new FilteredDropDown("Estimates", ui);
		//estimateDrop.setToExternal(new EstimateTool(ui));
		//estimateDrop.setToExternal(new Label("Test"));
		EstimateTool estimate = new EstimateTool(ui);
		Button estimateDrop = new Button("Estimates");
		estimateDrop.setId("DropButton");
		estimateDrop.setPrefSize(400, 60);
	   	estimateDrop.setOnAction(e-> {		
			ui.setScene(estimate.getPrimaryScene());							
		});
		
		FilteredDropDown calcDrop = new FilteredDropDown("Calculator", ui);
		CalculatorTool calculator = new CalculatorTool(ui);
		calcDrop.add(calculator);
		//calcDrop.add(new CalculatorTool());
		//calcDrop.add(new CalculatorTool());
		
		FilteredDropDown historyDrop = new FilteredDropDown("History", ui);
		HistoryTool history = new HistoryTool();
		historyDrop.add(history);
		
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
		
		SettingsManager sm = new SettingsManager(ui, favorites, 
				history, notes);
		sm.loadSettings();
		ui.setSettingsManager(sm);
		//ui.setTools(favorites, ohms, circuits, estimate, calculator, history, notes);
		ui.setTools(favorites, calculator, history, notes);
		
		
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
		
		/*MenuItem view0 = new MenuItem("Tabs");
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
		mainBar.addNode(view);*/
		
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
	SettingsManager settings;
	ArrayList<Pane> tools;
	
	// 0 for tabs, 1, external pages
	int view;
	
	public UIManager() {
		// default
	}
	
	public UIManager(Stage stage, Scene scene) {
		primary = stage;
		main = scene;
		//settings = new SettingsManager(this);
	}
	
	public void set(Stage stage, Scene scene, CustomScroll scroll) {
		primary = stage;
		main = scene;
		this.scroll = scroll;
		mainBox = scroll.getMainBox();
	}
	
	public void setSettingsManager(SettingsManager SM) {
		settings = SM;
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
			return new CalculatorTool(this);
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
	
	public SettingsManager getSM() {
		return settings;
	}
	
	public void addToHistory(String statement) {
		settings.getHistory().addStatement(statement);
	}
	
	public void setTools(Pane...panes) {
		tools = new ArrayList<Pane>();
		for(int i=0; i<panes.length; i++) {
			tools.add(panes[i]);
		}
	}
	
	public void clearAll() {
		//System.out.println(tools.size());
		for(int i=0; i<tools.size(); i++) {
			try {
				Tool tool = (Tool)tools.get(i);
				tool.clearDisplay();
			}
			catch(Exception e) {
				// not a sub-tool, cannot clear
				continue;
			}
		}
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
							drop.closeContent();
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
		save.setOnAction(e -> {
			ui.getSM().saveSettings();
		});
		
		Button clear = new Button("Clear");
		clear.setOnAction(e -> {
			ui.clearAll();
		});
		
		Button close = new Button("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ui.closeAll();
			}
		});
		
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
		//mainBar.addNode(view);
		
		return (new Node[]{save,clear,close, view});
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


/**
 * Pre-styled scene
 * @author RyanS
 *
 */
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
		//this.getChildren().addAll(boxes);
		for(int i=0; i<boxes.length; i++) {
			boxes[i].setAllowIndeterminate(false);
			this.getChildren().add(boxes[i]);
		}
	}
	
	// each index represents check box, cell in array is 1 if checked
	public int[] getIntArray() {
		ObservableList<Node> checkList = this.getChildren();
		int[] array = new int[checkList.size()];
		
		for(int i=0; i<array.length; i++) {
			try {
				CheckBox box = (CheckBox)checkList.get(i);
				box.setAllowIndeterminate(false);
		
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
	
	// un-checks all boxes in check box list
	public void uncheckAll() {
		ObservableList<Node> checkList = this.getChildren();
		for (int i = 0; i < checkList.size(); i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				box.setSelected(false);

			} catch (Exception e) {
				continue;
			}
		}
	}
	
	// attempts to check all boxes corresponding to name array
	public void checkByName(String...names) {
		ObservableList<Node> checkList = this.getChildren();
		for (int i = 0; i < checkList.size(); i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				for(int j=0; j<names.length; j++) {
					if(box.getText() == names[j])
						box.setSelected(true);
				}

			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public void setChecks(int[] checks) {
		ObservableList<Node> checkList = this.getChildren();
		
		if(checks.length != checkList.size())
			return;
		
		for (int i = 0; i < checkList.size(); i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				box.setSelected(checks[i]==1 ? true : false);

			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public void removeChecked() {
		ObservableList<Node> checkList = this.getChildren();
		
		for (int i = 0; i < checkList.size(); i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				if(box.isSelected())
					this.getChildren().remove(box);

			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public void addCheckBox(CheckBox box) {
		box.setAllowIndeterminate(false);
		this.getChildren().add(box);
	}
	
	public void checkAll() {
		ObservableList<Node> checkList = this.getChildren();
		for (int i = 0; i < checkList.size(); i++) {
			try {
				CheckBox box = (CheckBox) checkList.get(i);
				box.setSelected(true);

			} catch (Exception e) {
				continue;
			}
		}
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
	CheckList checks;
	
	public FavoritesTool(UIManager ui) {
		this.ui = ui;
		checks = new CheckList();
		checks.setSpacing(3);
		
		// updates tool list according to check boxes
		Button update = new Button("Update");
		update.setPrefSize(100, 30);
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
		
		toolDrop = new DropDown("Tools", 305, 30);
		
		HBox modBox = new HBox(modify, update);
		modBox.setSpacing(5);
		
		VBox wrap = new VBox(modBox, toolDrop);
		//VBox.setMargin(toolDrop, new Insets(40, 20, 20, 20));
		wrap.setSpacing(10);
		
		VBox outerWrap = new VBox(wrap);
		VBox.setMargin(wrap, new Insets(10,10,10,10));
		
		this.getChildren().addAll(outerWrap);
	}
	
	public void setFavorites(int[] checkArr, String[] names) {
		toolDrop.clearDrop();
		
		if(checkArr.length != names.length)
			System.out.println("Error: setting favorites");
		
		this.checks.setChecks(checkArr);
		
		// check check box and add by name...
		for(int i=0; i<checkArr.length; i++) {
			if(checkArr[i] == 1) {
				Tool tool = ui.getToolByName(names[i]);
				//System.out.println("Tool: "+tool.getClass().getSimpleName());
				
				if(tool != null) 
					toolDrop.add(tool);
			}
		}
		
	}
	
	public void uncheckAll() {
		checks.uncheckAll();
	}
	
	public void checkByNames(String...names) {
		checks.checkByName(names);
	}
	
	public String[] getNames() {
		return checks.getNameArray();
	}
	
	public int[] getChecks() {
		return checks.getIntArray();
	}
	
	public String printResult() {
		return null;
	}
	
	public void clearDisplay() {
		
	}
	
}

/**
 * 
 * @author RyanS
 *
 */
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
	
	public TextArea getTextArea() {
		return text;
	}
	
	public void setTextArea(String string) {
		text.setText(string);
	}
	
}

/**
 * 
 * @author RyanS
 *
 */
class HistoryTool extends Pane {
	
	CheckList checks;
	
	public HistoryTool() {
		Button removeMarked = new Button("Remove Marked");
		removeMarked.setOnAction(e -> {
			deleteMarked();
		});
		
		Button markAll = new Button("Mark All");
		markAll.setOnAction(e -> {
			markAll();
		});
		
		Button unmarkAll = new Button("Unmark All");
		unmarkAll.setOnAction(e -> {
			unmarkAll();
		});
		
		HBox buttons = new HBox(removeMarked, markAll, unmarkAll);
		buttons.setSpacing(5);
		
		checks = new CheckList();
		checks.setSpacing(3);
		
		Label label = new Label("Saved Results:");
		
		VBox wrap = new VBox(buttons, label, checks);
		wrap.setSpacing(5);
		
		VBox outerWrap = new VBox(wrap);
		VBox.setMargin(wrap, new Insets(10,10,10,10));
		
		this.getChildren().add(outerWrap);
	}
	
	public void markAll() {
		checks.checkAll();
	}
	
	public void unmarkAll() {
		checks.uncheckAll();
	}
	
	public void addStatement(String statement) {
		checks.addCheckBox(new CheckBox(statement));
	}
	
	public void deleteMarked() {
		checks.removeChecked();
	}
	
	public CheckList getChecks() {
		return checks;
	}
	
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
	

	UIManager ui;
	public Tool(UIManager ui) {
		this.ui = ui;
	}
	
	abstract public String getToolName();
	abstract public String printResult();
	abstract public void clearDisplay();
	
	public void printToHistory() {
		ui.addToHistory(printResult());
	}
	
}

/**
 * 
 * @author RyanS
 *
 */
class CalculatorTool extends Tool {
	
	//UIManager ui;
	String name = "Calculator";
	TextField input;
	Label result;
	Button enter;
	Button print;
	
	public CalculatorTool(UIManager ui) {
		super(ui);
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
		
		print = new Button("Print");
		print.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				printToHistory();
			}
		});;
		
		Label directions = new Label("Enter basic equation to evaluate:");
		HBox inputBox = new HBox(input, equals, result);
		inputBox.setSpacing(5);
		
		HBox buttons = new HBox(enter, print);
		buttons.setSpacing(5);
		
		VBox outerBox = new VBox(directions, inputBox, buttons);
		outerBox.setSpacing(5);
		
		VBox outerWrap = new VBox(outerBox);
		VBox.setMargin(outerBox, new Insets(10,10,10,10));
		
		this.getChildren().add(outerWrap);
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



/*
 * Load/Save tools
 */



/*
 * Save-able settings:
 * 		- View  [1 for pages, 0 for tabs]
 * 		- Favorites [checked favorites names]
 * 		- History [each line is history print]
 * 		- Notes  [copy of saved text]
 */

/**
 * Handles the loading and saving of user settings. Also invoves
 * 		general handling of modifiable UI components.
 * @author RyanS
 *
 */
class SettingsManager {
	
	UIManager ui;
	//MainBar bar;
	FavoritesTool favorites; 
	HistoryTool history; 
	NotesTool notes;
	File viewFile = new File("view.txt");
	File favoritesFile = new File("favorites.txt");
	File historyFile = new File("history.txt");
	File notesFile = new File("notes.txt");
	
	public SettingsManager(UIManager ui, FavoritesTool favorites, 
			HistoryTool history, NotesTool notes) {
		this.ui = ui;
		//this.bar = bar;
		this.favorites = favorites;
		this.history = history;
		this.notes = notes;
	}
	
	public void loadSettings() {
		if(!checkFiles())
			return;
		
		loadView();
		loadFavorites();
		loadHistory();
		loadNotes();
		
		System.out.println("Loading finished");
	}
	
	public void saveSettings() {
		if(!checkFiles())
			return;
		
		saveView();
		saveFavorites();
		saveHistory();
		saveNotes();
		
		System.out.println("Saving finished");
	}
	
	public boolean checkFiles() {
		try {
			if(!viewFile.exists())
				viewFile.createNewFile();
			
			if(!favoritesFile.exists())
				favoritesFile.createNewFile();
			
			if(!historyFile.exists())
				historyFile.createNewFile();
			
			if(!notesFile.exists())
				notesFile.createNewFile();
			
			return true;
		}
		catch(Exception e) {
			System.out.println("ERROR: settings files");
			return false;
		}
	}
	
	/*
	 * Load functions:
	 */
	
	public void loadView() {
		Scanner fileIn;
		try {
			fileIn = new Scanner(viewFile);
			
			int view = fileIn.nextInt();
			
			if(view == 0)
				ui.setToTabs();
			else if(view == 1)
				ui.setToPages();
			
			fileIn.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: view file reading");
		}
	}
	
	public void loadFavorites() {
		Scanner fileIn;
		try {
			fileIn = new Scanner(favoritesFile);
			String[] names = ui.getToolNames();
			int[] checks = new int[names.length];
			
			while(fileIn.hasNextLine()) {
				String name = fileIn.nextLine().trim();
				
				for(int i=0; i<names.length; i++) {
					//System.out.println("*"+name +" == "+names[i]+"*");
					
					if(name.equals(names[i]))
						checks[i] = 1;
					else checks[i] = 0;
				}
			}
			
			favorites.setFavorites(checks, names);
			
			fileIn.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: favorites file reading");
		}
	}
	
	public void loadHistory() {
		Scanner fileIn;
		try {
			fileIn = new Scanner(historyFile);
			
			while(fileIn.hasNextLine()) {
				String statement = fileIn.nextLine().trim();
				history.addStatement(statement);
			}
			
			fileIn.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: history file reading");
		}
	}
	
	public void loadNotes() {
		Scanner fileIn;
		try {
			fileIn = new Scanner(notesFile);
			
			String text = "";
			while(fileIn.hasNextLine()) {
				text = text.concat(fileIn.nextLine()+"\n");
			}
			
			notes.setTextArea(text);
			
			fileIn.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: notes file reading");
		}
	}
	
	/*
	 * Save functions:
	 */
	
	public void saveView() {
		try {
			PrintWriter fileOut = new PrintWriter(viewFile);
			
			if(ui.getView() == 0)
				fileOut.print(0);
			else if(ui.getView() == 1)
				fileOut.print(1);
			
			fileOut.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: view file writing");
		}
	}
	
	public void saveFavorites() {
		try {
			PrintWriter fileOut = new PrintWriter(favoritesFile);
			
			int[] checks = favorites.getChecks();
			String[] names = favorites.getNames();
			
			for(int i=0; i<checks.length; i++) {
				if(checks[i] == 1)
					fileOut.println(names[i]);
			}
			
			fileOut.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: favorites file writing");
		}
	}
	
	public void saveHistory() {
		try {
			PrintWriter fileOut = new PrintWriter(historyFile);
			CheckList boxes = history.getChecks();
			ObservableList<Node> checkList = boxes.getChildren();
			
			for(int i=0; i<checkList.size(); i++) {
				CheckBox box = (CheckBox)checkList.get(i);
				fileOut.println(box.getText());
			}
			
			fileOut.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: history file writing");
		}
	}
	
	public void saveNotes() {
		try {
			PrintWriter fileOut = new PrintWriter(notesFile);
			
			fileOut.print(notes.getTextArea().getText());
			
			fileOut.close();
		}
		catch(Exception e) {
			System.out.println("ERROR:  file writing");
		}
	}
	
	/*
	 * Getters for tools
	 */
	
	public FavoritesTool getFavorites() {
		return favorites;
	}
	
	public HistoryTool getHistory() {
		return history;
	}
	
	public NotesTool getNotes() {
		return notes;
	}
	
}












