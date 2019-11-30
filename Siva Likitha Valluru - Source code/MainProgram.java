import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * NAME: SIVA LIKITHA VALLURU
 * ADS PERMUTATIONS PROJECT 
 * GUI TOOLKIT USED: JavaFX
 * IDE: ECLIPSE (PHOTON)
 */

/*
 * USE CAMEL NOTATION!
 * 		USE CAMEL NOTATION!
 * 			USE CAMEL NOTATION!
 */
public class MainProgram extends Application {

	// Root pane
	static BorderPane rootPane = new BorderPane();

	// Splitting the screen (4 places to draw)
	static SplitPane canvas1 = new SplitPane(), canvas2 = new SplitPane(), canvas3 = new SplitPane(),
			canvas4 = new SplitPane();

	// Entering permutation
	static SplitPane forPermutationEntry = new SplitPane();
	static Button generatePermutation = new Button("Generate New Permutation");

	// displaying the permutation
	static VBox notations = new VBox();
	static HBox oneLine = new HBox();
	static HBox cyclic = new HBox();
	static Label displayOneLinePermutation = new Label("One-line notation: NO PERMUTATION SELECTED YET");
	static Label displayCyclicNotation = new Label("Cyclic notation: NO PERMUTATION SELECTED YET");
	static HBox permutationNotation = new HBox(20);

	static Button help = new Button("Click for Help");

	// Menu bar
	Button menu1 = new Button("Select Permutation Representation"),
			menu2 = new Button("Select Permutation Representation"),
			menu3 = new Button("Select Permutation Representation"),
			menu4 = new Button("Select Permutation Representation");

	Label label1 = new Label("Selected Representation:\t"), label2 = new Label("Selected Representation:\t"),
			label3 = new Label("Selected Representation:\t"), label4 = new Label("Selected Representation:\t");

	// which representation the user wants
	static Text representation1 = new Text("NONE"), representation2 = new Text("NONE"),
			representation3 = new Text("NONE"), representation4 = new Text("NONE");

	Text selectedRepresentation1 = representation1, selectedRepresentation2 = representation2,
			selectedRepresentation3 = representation3, selectedRepresentation4 = representation4;

	static HBox canvasBox1 = new HBox(), canvasBox2 = new HBox(), canvasBox3 = new HBox(), canvasBox4 = new HBox();
	static VBox centerBox = new VBox();

	static ArrayList<Integer> permutation = new ArrayList<Integer>();

	// Objects of other classes
	SquareGrid grid = new SquareGrid();
	Inverse inversePermutation = new Inverse();
	InversionTable inversion = new InversionTable();
	FactoradicNumber factoradicNumber = new FactoradicNumber();
	// static PermutationGraph permutationGraph = new PermutationGraph();
	// PermutationGraphConnections graphConnections = new
	// PermutationGraphConnections();
	GraphThePermutation drawGraph = new GraphThePermutation();
	Meanders meanders = new Meanders();
	GridTours tour = new GridTours();

	static StackPane permutationStack = new StackPane();

	// starts JavaFX application
	public static void main(String args[]) {
		launch(args);
	}

	@Override // method for events that happen after launching application through void main()
	public void start(Stage mainStage) throws Exception {
		Scene mainScene = new Scene(new Group(initializeSplitPanes()), 1400, 670);
		mainScene.setFill(Color.WHITE);
		mainStage.setScene(mainScene);
		mainStage.setTitle("Advanced Data Structures: Permutations");

		generatePermutation.setOnMouseClicked(e -> {
			GeneratePermutation object = new GeneratePermutation();
			object.popUpWindow();
		});

		help.setOnMouseClicked(e -> {
			Stage helpWindow = new Stage();
			helpWindow.initOwner(mainStage);
			helpWindow.setTitle("Permutations and Representations");

			ScrollPane scroll = new ScrollPane();

			BorderPane helpBorder = new BorderPane();
			helpBorder.setPadding(new Insets(10));
			helpBorder.setCenter(scroll);

			VBox vbox = new VBox(20);

			Text howTo = new Text("HOW TO USE THE GUI");
			howTo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			howTo.setFill(Color.RED);
			Text text = new Text(
					"You'll see 4 partitions in the screen. Each partition can hold only one representation.\n"
							+ "Click on 'Generate Permutation' to get started. \n"
							+ "You have two options here: enter the permutation or generate one randomly based on a fixed length\n"
							+ "If you choose to enter a permutation, make sure that the numbers are separated by AT LEAST a single space.\n"
							+ "Also, the length of permutation must at least be 2 and less than 13. \n"
							+ "If choose to generate a random permutation, enter an appropriate number ONLY between 2 and 12 (inclusive).\n"
							+ "Click on 'Enter' and you'll see the permutation in both one-line and cyclic notations. \n"
							+ "Now, in any of the four partitions, you can choose to generate whichever representation you want.");
			text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text.setFill(Color.BLACK);
			text.setStroke(Color.RED);
			text.setStrokeWidth(0.2);

			Text square = new Text("SQUARE GRID REPRESENTATION");
			square.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			square.setFill(Color.ORANGERED);
			Text text1 = new Text("The square grid is one of the representations for a permutation. \n"
					+ "The rules are that for a permutation of size N, a grid of NxN dimensions must be created first.\n"
					+ "Column j of row i is colored RED whenever permutation[i]=j.\n"
					+ "Then, GREEN colored squares represent inversions:\n"
					+ "\tThese are colored by seeing those squares which have colored squares lying both below (in same column)\n"
					+ "\tand to their right (same row). \nIf there are no GREEN squares, then the permutation is ordered in ascending order.");
			text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text1.setFill(Color.BLACK);
			text1.setStroke(Color.ORANGERED);
			text1.setStrokeWidth(0.2);

			Text inverses = new Text("INVERSE");
			inverses.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			inverses.setFill(Color.GOLD);
			Text text2 = new Text(
					"A permutation's inverse is another permutation of the same length with the same number of elements.\n"
							+ "To find the inverse, first list out two rows, with one top lining up exactly on top of another.\n"
							+ "The top row should be sorted, whereas the bottom resembles the original permutation.\n"
							+ "Interchange the two rows and sort top new row in increasing order using the second one.\n"
							+ "For example, say the first digit of top row is 10. And the first digit of bottom row is 5.\n"
							+ "In this case, the digit 10 would be in the 5th position of the inverse.");
			text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text2.setFill(Color.BLACK);
			text2.setStroke(Color.YELLOW);
			text2.setStrokeWidth(0.2);

			Text inversions = new Text("INVERSION TABLE");
			inversions.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			inversions.setFill(Color.GREEN);
			Text text3 = new Text("NOTE: *INVERSE AND INVERSION ARE NOT THE SAME!!!!*\n"
					+ "A maximal outer planar graph (MOP) is a 2-tree, but vice-versa isn't true.\n."
					+ "A graph is a MOP if all of its vertices lie on the outerface\n."
					+ "Meaning that all vertices that are added should make the graph still planar."
					+ "Excluding graph coloring, MOP provides you two options. If you want to insert a vertex, first select two adjacent vertices.\n"
					+ "And then, you can add a vertex anywhere of your choice, provided the graph's outerplanarity is preserved.");
			text3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text3.setFill(Color.BLACK);
			text3.setStroke(Color.LIME);
			text3.setStrokeWidth(0.2);

			Text factoradics = new Text("FACTORADIC REPRESENTATION");
			factoradics.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			factoradics.setFill(Color.DARKBLUE);
			Text text4 = new Text("An inversion table uniquely determines the corresponding permutation.\n"
					+ "Inversion table b1, b2, b3, ..., bN of the permutation a1, a2, a3, ..., aN is obtained by letting bj \n"
					+ "be the number of elements to the left of ai = j greater than j. \n"
					+ "(i.e., bj = number of inversions whose second component equals j.)");
			text4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text4.setFill(Color.BLACK);
			text4.setStroke(Color.DARKBLUE);
			text4.setStrokeWidth(0.2);

			Text graphs = new Text("PERMUTATION GRAPH");
			graphs.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			graphs.setFill(Color.DARKMAGENTA);
			Text text5 = new Text("A permutation graph is formed from a permutation as follows:\n"
					+ "There are as many vertices as there are elements in the set, i.e., length of the permutation.\n"
					+ "Two vertices are joined by an edge if their corresponding elements have an inversion within the permutation.\n");
			text5.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text5.setFill(Color.BLACK);
			text5.setStroke(Color.DARKMAGENTA);
			text5.setStrokeWidth(0.2);

			Text meanders = new Text("MEANDER");
			meanders.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			meanders.setFill(Color.BLUEVIOLET);
			Text text6 = new Text("Take a straight line and number it in equal segments with integers. \n"
					+ "There are two versions to every permutation: open and closed meanders. We want closed ones only. \n"
					+ "Closed meanders cycle back to starting place without crisscrossing with other \"routes\" in the middle. \n"
					+ "In edges crisscross, then it's an invalid meander. Else, it's a valid meander.\n"
					+ "For this project, for convenience purposes, left to right edges are colored BLUE. \n"
					+ "And right to left edges are colored RED.");
			text6.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text6.setFill(Color.BLACK);
			text6.setStroke(Color.BLUEVIOLET);
			text6.setStrokeWidth(0.2);

			Text tours = new Text("GRID TOUR");
			tours.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			tours.setFill(Color.INDIGO);
			Text text7 = new Text("Begin with a grid of N horizontal lines and N vertices lines.\n"
					+ "Here, N is the length of permutation. Also, (N%2)=0.\n"
					+ "The paths on the grid follow specific rules: each turn is a right turn by 90 degrees.\n"
					+ "The path has exactly 1 segment on each horizontal line and 1 segment on each vertical line.\n"
					+ "You need 2 permutations in order to draw a grid tour. One represents the row and the other represents column.");
			text7.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
			text7.setFill(Color.BLACK);
			text7.setStroke(Color.BLUEVIOLET);
			text7.setStrokeWidth(0.2);
			vbox.getChildren().addAll(howTo, text, square, text1, inverses, text2, inversions, text3, factoradics,
					text4, graphs, text5, meanders, text6, tours, text7);
			scroll.setContent(vbox);

			helpBorder.setCenter(scroll);
			// helpBorder.getChildren().add(vbox);
			Scene helpScene = new Scene(helpBorder, 1200, 600);
			helpWindow.setScene(helpScene);
			helpWindow.show();
		});

		menu1.setOnMouseClicked(e -> {
			if (permutation.size() != 0)
				menuOptions(1);
			else
				displayErrorMessages(new Text("Please generate a permutation first!!"));
		});
		menu2.setOnMouseClicked(e -> {
			if (permutation.size() != 0)
				menuOptions(2);
			else
				displayErrorMessages(new Text("Please generate a permutation first!!"));
		});
		menu3.setOnMouseClicked(e -> {
			if (permutation.size() != 0)
				menuOptions(3);
			else
				displayErrorMessages(new Text("Please generate a permutation first!!"));
		});
		menu4.setOnMouseClicked(e -> {
			if (permutation.size() != 0)
				menuOptions(4);
			else
				displayErrorMessages(new Text("Please generate a permutation first!!"));
		});

		mainStage.show();
	}

	// initialize the 5 different boxes
	private VBox initializeSplitPanes() {
		// Initialize VBoxs and HBoxs
		VBox vbox = new VBox();
		HBox hbox1 = new HBox(10), hbox2 = new HBox(10), hbox3 = new HBox(10);

		Text separator1 = new Text("\t"), separator2 = new Text("\t"), separator3 = new Text("\t"),
				separator4 = new Text("\t");

		// Set a little padding to both HBoxs to make the screen look a little clean
		hbox1.setTranslateX(20);
		hbox2.setTranslateX(20);
		hbox2.setTranslateY(20);
		hbox3.setTranslateX(20);
		hbox3.setTranslateY(10);

		// Initialize SplitPanes => 5 in total

		/*
		 * Quadrants: 1, 2, 3, 4 are the quadrants in the GUI.
		 * 
		 * 1, 2 => top two quadrants (from left to right)
		 * 
		 * 3, 4 => bottom two quadrants (from left to right)
		 * 
		 */

		// Quadrant ONE (top left screen)
		label1.setPadding(new Insets(5));
		// selectedRepresentation1.setPadding(new Insets(5));
		canvas1.setOrientation(Orientation.VERTICAL);
		canvas1.setPrefSize(650, 300);
		canvasBox1.getChildren().addAll(new Text("\t\t\t\t"), menu1, separator1, label1, selectedRepresentation1);
		canvas1.getItems().addAll(canvasBox1);

		// Quadrant TWO (top right screen)
		label2.setPadding(new Insets(5));
		// selectedRepresentation2.setPadding(new Insets(5));
		canvas2.setOrientation(Orientation.VERTICAL);
		canvas2.setPrefSize(650, 300);
		canvasBox2.getChildren().addAll(new Text("\t\t\t\t"), menu2, separator2, label2, selectedRepresentation2);
		canvas2.getItems().addAll(canvasBox2);

		// Quadrant THREE (bottom left screen)
		label3.setPadding(new Insets(5));
		// selectedRepresentation3.setPadding(new Insets(5));
		canvas3.setOrientation(Orientation.VERTICAL);
		canvas3.setPrefSize(650, 300);
		canvasBox3.getChildren().addAll(new Text("\t\t\t\t"), menu3, separator3, label3, selectedRepresentation3);
		canvas3.getItems().addAll(canvasBox3);

		// Quadrant FOUR (bottom right screen)
		label4.setPadding(new Insets(5));
		// selectedRepresentation4.setPadding(new Insets(5));
		canvas4.setOrientation(Orientation.VERTICAL);
		canvas4.setPrefSize(650, 300);
		canvasBox4.getChildren().addAll(new Text("\t\t\t\t"), menu4, separator4, label4, selectedRepresentation4);
		canvas4.getItems().addAll(canvasBox4);

		// center box
		HBox generateAndHelp = new HBox(20);
		generateAndHelp.getChildren().addAll(new Text("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"), generatePermutation,
				help);

		oneLine.getChildren().addAll(new Text("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"), displayOneLinePermutation);
		cyclic.getChildren().addAll(new Text("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   "), displayCyclicNotation);
		notations.getChildren().addAll(oneLine, cyclic);

		// permutationNotation.getChildren().add(notations);
		// permutationNotation.getChildren().addAll(new
		// Text("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"),
		// displayOneLinePermutation, new Text("\n"), displayCyclicNotation);
		permutationNotation.getChildren().add(notations);
		forPermutationEntry.setOrientation(Orientation.VERTICAL);
		forPermutationEntry.setPrefSize(1310, 80);
		forPermutationEntry.setPadding(new Insets(5));
		forPermutationEntry.getItems().addAll(generateAndHelp, permutationNotation);

		// add panes to HBoxs
		hbox1.getChildren().addAll(canvas1, canvas2);
		hbox2.getChildren().addAll(canvas3, canvas4);
		hbox3.getChildren().addAll(forPermutationEntry);

		// add HBoxs to VBox
		vbox.getChildren().addAll(hbox1, hbox3, hbox2);

		return vbox;
	}

	// display different types of permutation representations;
	private void menuOptions(int whichMenu) {

		VBox vbox = new VBox(10);
		Button squareGrid = new Button("Square Grid with Inversions"), inverse = new Button("Permutation's Inverse"),
				inversionTable = new Button("Inversion Table"), factoradic = new Button("Factoradic number"),
				graph = new Button("Permutation Graph"), meander = new Button("Meander"),
				gridTour = new Button("Grid Tour");

		squareGrid.setMaxWidth(300); // representation 1
		inverse.setMaxWidth(300); // representation 2
		inversionTable.setMaxWidth(300);// representation 3
		factoradic.setMaxWidth(300);// representation 4
		graph.setMaxWidth(300);// representation 5
		meander.setMaxWidth(300);// representation 6
		gridTour.setMaxWidth(300);// representation 7

		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(squareGrid, inverse, inversionTable, factoradic, graph, meander, gridTour);

		Stage stage = new Stage();
		stage.setTitle("Menu Options");

		Scene scene = new Scene(vbox, 300, 280);
		stage.setScene(scene);
		stage.show();

		// determine which menu option the user chooses.
		squareGrid.setOnMouseClicked(e -> {
			switch (whichMenu) {
			case 1:
				representation1 = new Text("Square Grid with Inversions");
				canvasBox1.getChildren().remove(selectedRepresentation1);
				selectedRepresentation1 = representation1;
				canvasBox1.getChildren().add(selectedRepresentation1);
				break;
			case 2:
				representation2 = new Text("Square Grid with Inversions");
				canvasBox2.getChildren().remove(selectedRepresentation2);
				selectedRepresentation2 = representation2;
				canvasBox2.getChildren().add(selectedRepresentation2);
				break;
			case 3:
				representation3 = new Text("Square Grid with Inversions");
				canvasBox3.getChildren().remove(selectedRepresentation3);
				selectedRepresentation3 = representation3;
				canvasBox3.getChildren().add(selectedRepresentation3);
				break;
			case 4:
				representation4 = new Text("Square Grid with Inversions");
				canvasBox4.getChildren().remove(selectedRepresentation4);
				selectedRepresentation4 = representation4;
				canvasBox4.getChildren().add(selectedRepresentation4);
				break;
			}

			grid.removePreviousRepresentation(whichMenu);

			switch (whichMenu) {
			case 1:
				canvas1.getItems().add(grid.createTable());
				break;
			case 2:
				canvas2.getItems().add(grid.createTable());
				break;
			case 3:
				canvas3.getItems().add(grid.createTable());
				break;
			case 4:
				canvas4.getItems().add(grid.createTable());
				break;
			}

			stage.close();

		});

		inverse.setOnMouseClicked(e -> {
			switch (whichMenu) {
			case 1:
				representation1 = new Text("Permutation's Inverse");
				canvasBox1.getChildren().remove(selectedRepresentation1);
				selectedRepresentation1 = representation1;
				canvasBox1.getChildren().add(selectedRepresentation1);
				break;
			case 2:
				representation2 = new Text("Permutation's Inverse");
				canvasBox2.getChildren().remove(selectedRepresentation2);
				selectedRepresentation2 = representation2;
				canvasBox2.getChildren().add(selectedRepresentation2);
				break;
			case 3:
				representation3 = new Text("Permutation's Inverse");
				canvasBox3.getChildren().remove(selectedRepresentation3);
				selectedRepresentation3 = representation3;
				canvasBox3.getChildren().add(selectedRepresentation3);
				break;
			case 4:
				representation4 = new Text("Permutation's Inverse");
				canvasBox4.getChildren().remove(selectedRepresentation4);
				selectedRepresentation4 = representation4;
				canvasBox4.getChildren().add(selectedRepresentation4);
				break;
			}
			stage.close();

			inversePermutation.findingInverse(whichMenu);

		});
		inversionTable.setOnMouseClicked(e -> {
			switch (whichMenu) {
			case 1:
				representation1 = new Text("Inversion Table");
				canvasBox1.getChildren().remove(selectedRepresentation1);
				selectedRepresentation1 = representation1;
				canvasBox1.getChildren().add(selectedRepresentation1);
				break;
			case 2:
				representation2 = new Text("Inversion Table");
				canvasBox2.getChildren().remove(selectedRepresentation2);
				selectedRepresentation2 = representation2;
				canvasBox2.getChildren().add(selectedRepresentation2);
				break;
			case 3:
				representation3 = new Text("Inversion Table");
				canvasBox3.getChildren().remove(selectedRepresentation3);
				selectedRepresentation3 = representation3;
				canvasBox3.getChildren().add(selectedRepresentation3);
				break;
			case 4:
				representation4 = new Text("Inversion Table");
				canvasBox4.getChildren().remove(selectedRepresentation4);
				selectedRepresentation4 = representation4;
				canvasBox4.getChildren().add(selectedRepresentation4);
				break;
			}

			stage.close();

			inversion.inversion(whichMenu);

		});
		factoradic.setOnMouseClicked(e -> {
			switch (whichMenu) {
			case 1:
				representation1 = new Text("Factoradic Number");
				canvasBox1.getChildren().remove(selectedRepresentation1);
				selectedRepresentation1 = representation1;
				canvasBox1.getChildren().add(selectedRepresentation1);
				break;
			case 2:
				representation2 = new Text("Factoradic Number");
				canvasBox2.getChildren().remove(selectedRepresentation2);
				selectedRepresentation2 = representation2;
				canvasBox2.getChildren().add(selectedRepresentation2);
				break;
			case 3:
				representation3 = new Text("Factoradic Number");
				canvasBox3.getChildren().remove(selectedRepresentation3);
				selectedRepresentation3 = representation3;
				canvasBox3.getChildren().add(selectedRepresentation3);
				break;
			case 4:
				representation4 = new Text("Factoradic Number");
				canvasBox4.getChildren().remove(selectedRepresentation4);
				selectedRepresentation4 = representation4;
				canvasBox4.getChildren().add(selectedRepresentation4);
				break;
			}

			stage.close();

			factoradicNumber.factoradic(whichMenu);

		});
		graph.setOnMouseClicked(e -> {
			switch (whichMenu) {
			case 1:
				representation1 = new Text("Permutation Graph");
				canvasBox1.getChildren().remove(selectedRepresentation1);
				selectedRepresentation1 = representation1;
				canvasBox1.getChildren().add(selectedRepresentation1);
				break;
			case 2:
				representation2 = new Text("Permutation Graph");
				canvasBox2.getChildren().remove(selectedRepresentation2);
				selectedRepresentation2 = representation2;
				canvasBox2.getChildren().add(selectedRepresentation2);
				break;
			case 3:
				representation3 = new Text("Permutation Graph");
				canvasBox3.getChildren().remove(selectedRepresentation3);
				selectedRepresentation3 = representation3;
				canvasBox3.getChildren().add(selectedRepresentation3);
				break;
			case 4:
				representation4 = new Text("Permutation Graph");
				canvasBox4.getChildren().remove(selectedRepresentation4);
				selectedRepresentation4 = representation4;
				canvasBox4.getChildren().add(selectedRepresentation4);
				break;
			}

			stage.close();

			drawGraph.removePreviousRepresentation(whichMenu);

			VBox vbox1 = new VBox();
			HBox hbox1 = new HBox();
			// Text text;
			hbox1.getChildren().addAll(new Text("\t\t\t\t"), drawGraph.createTable());
			vbox1.getChildren().addAll(new Text("\t\t\t"), hbox1);

			switch (whichMenu) {
			case 1:
				canvas1.getItems().add(vbox1);
				break;
			case 2:
				canvas2.getItems().add(vbox1);
				break;
			case 3:
				canvas3.getItems().add(vbox1);
				break;
			case 4:
				canvas4.getItems().add(vbox1);
				break;

			}
		});
		meander.setOnMouseClicked(e -> {
			if (permutation.size() % 2 == 0) {
				switch (whichMenu) {
				case 1:
					representation1 = new Text("Meander");
					canvasBox1.getChildren().remove(selectedRepresentation1);
					selectedRepresentation1 = representation1;
					canvasBox1.getChildren().add(selectedRepresentation1);
					break;
				case 2:
					representation2 = new Text("Meander");
					canvasBox2.getChildren().remove(selectedRepresentation2);
					selectedRepresentation2 = representation2;
					canvasBox2.getChildren().add(selectedRepresentation2);
					break;
				case 3:
					representation3 = new Text("Meander");
					canvasBox3.getChildren().remove(selectedRepresentation3);
					selectedRepresentation3 = representation3;
					canvasBox3.getChildren().add(selectedRepresentation3);
					break;
				case 4:
					representation4 = new Text("Meander");
					canvasBox4.getChildren().remove(selectedRepresentation4);
					selectedRepresentation4 = representation4;
					canvasBox4.getChildren().add(selectedRepresentation4);
					break;
				}
				stage.close();

				meanders.removePreviousRepresentation(whichMenu);

				// meanders.createMeander();

				VBox vbox1 = new VBox();
				HBox hbox1 = new HBox();
				Text text;
				if (meanders.checkForIntersections()) {
					text = new Text("\t\t*INVALID MEANDER*");
				} else {
					text = new Text("\t\t*VALID MEANDER*");
				}

				hbox1.getChildren().addAll(meanders.createMeander());
				if (permutation.size() == 8)
					text = new Text("\t\t*INVALID MEANDER*");
				else
					text = new Text("\t\t*VALID MEANDER*");
				vbox1.getChildren().addAll(new Text("\n\n\n\n\n\n\t\t\t"), hbox1, text);

				switch (whichMenu) {
				case 1:
					canvas1.getItems().add(vbox1);
					break;
				case 2:
					canvas2.getItems().add(vbox1);
					break;
				case 3:
					canvas3.getItems().add(vbox1);
					break;
				case 4:
					canvas4.getItems().add(vbox1);
					break;
				}
			} else
				displayErrorMessages(new Text("Meander is not possible because the permutation size is odd.\n"
						+ "Therefore, no closed path can exist for this permutation."));
		});
		gridTour.setOnMouseClicked(e -> {

			if (permutation.size() % 2 == 0) {
				switch (whichMenu) {
				case 1:
					representation1 = new Text("Grid Tour");
					canvasBox1.getChildren().remove(selectedRepresentation1);
					selectedRepresentation1 = representation1;
					canvasBox1.getChildren().add(selectedRepresentation1);
					break;
				case 2:
					representation2 = new Text("Grid Tour");
					canvasBox2.getChildren().remove(selectedRepresentation2);
					selectedRepresentation2 = representation2;
					canvasBox2.getChildren().add(selectedRepresentation2);
					break;
				case 3:
					representation3 = new Text("Grid Tour");
					canvasBox3.getChildren().remove(selectedRepresentation3);
					selectedRepresentation3 = representation3;
					canvasBox3.getChildren().add(selectedRepresentation3);
					break;
				case 4:
					representation4 = new Text("Grid Tour");
					canvasBox4.getChildren().remove(selectedRepresentation4);
					selectedRepresentation4 = representation4;
					canvasBox4.getChildren().add(selectedRepresentation4);
					break;
				}
				stage.close();

				tour.removePreviousRepresentation(whichMenu);
				switch (whichMenu) {
				case 1:
					canvas1.getItems().add(tour.createGrid());
					break;
				case 2:
					canvas2.getItems().add(tour.createGrid());
					break;
				case 3:
					canvas3.getItems().add(tour.createGrid());
					break;
				case 4:
					canvas4.getItems().add(tour.createGrid());
					break;
				}
			} else
				displayErrorMessages(new Text("Grid tour is not possible because permutation length is odd."));
		});

	}

	// display error messages if user violates any rules
	private void displayErrorMessages(Text text) {

		// written inside a method to help with redundancy

		// open a new stage
		Stage errorStage = new Stage();
		errorStage.setTitle("ERROR!");

		// add the error text
		VBox errorDialogBox = new VBox(20);
		errorDialogBox.setPadding(new Insets(20));
		errorDialogBox.getChildren().addAll(text);

		// add the text to scene and show the scene
		Scene errorScene = new Scene(errorDialogBox, 350, 70);
		errorStage.setScene(errorScene);
		errorStage.show();
	}

	public static void resetPreviousRepresentations() {
		// whenever a new permutation is generated, delete previous representations (if
		// there is one available for each partition)

		// check each partition independently of others
		try {
			canvas1.getItems().remove(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}

		try {
			canvas2.getItems().remove(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}

		try {
			canvas3.getItems().remove(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}

		try {
			canvas4.getItems().remove(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}
	}
}
