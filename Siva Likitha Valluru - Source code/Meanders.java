import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;

public class Meanders {

	public void removePreviousRepresentation(int whichMenu) {
		try {
			// delete the previous representation (whatever it is)
			switch (whichMenu) {
			case 1:
				MainProgram.canvas1.getItems().remove(1);
				break;
			case 2:
				MainProgram.canvas2.getItems().remove(1);
				break;
			case 3:
				MainProgram.canvas3.getItems().remove(1);
				break;
			case 4:
				MainProgram.canvas4.getItems().remove(1);
				break;
			}
		} catch (Exception e) {
			// ignore

			/*
			 * This exception is raised when there is initially nothing present (index out
			 * of bounds exception)
			 */
		}

	}

	Pane pane;
	public static Meander[][] nodes = new Meander[12][1];

	static class Meander extends StackPane {

		Circle circle = new Circle(15);
		// each circle = one node

		// This constructor is called each time a new circle has to be defined
		public Meander(int x, int y) {
			// Add each circle as the pane's children
			getChildren().add(circle);
			// Each cell's dimensions are of a fixed radius
			setTranslateX(x * 40);
			setTranslateY(y * 40);
			// Each circle is initially of a green color - changed for each node
			Color randomColorFill = Color.rgb(0, 255 - x * 19, 0);

			circle.setFill(randomColorFill);
			circle.setStroke(Color.BLACK);

		}

		Text text = new Text();

		public Meander(int x, int y, int stroke) {
			getChildren().add(text);// add text as a child
			setTranslateX(x * 40); // scaling each vertex number
			setTranslateY(y * 40);
			text.setText(Integer.toString(x + 1));// parse the integer as a string
			text.setFill(Color.WHITE);// color the text
			text.setStrokeWidth(stroke);// make the text readable

		}

	}

	public Pane createMeander() {
		pane = new Pane();
		pane.setPadding(new Insets(10));
		// Define board's dimensions
		pane.setPrefSize(240, 240);
		// Fill the board with cells
		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			for (int j = 0; j < 1; j++) {
				// add node and vertex name in one stack and return that stack
				StackPane stack = new StackPane();
				nodes[i][j] = new Meander(i, j);
				Meander text = new Meander(i, j, 4);
				stack.getChildren().addAll(nodes[i][j], text);
				// add stack as a child to pane
				pane.getChildren().add(stack);
			}
		}

		// meander edges
		Path setOfPaths[] = quadraticCurves(MainProgram.permutation.size());

		// add the returned edges into the pane.
		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			try {
				pane.getChildren().add(setOfPaths[i]);
			} catch (NullPointerException e) {
				// ignore
			}
		}

		// insert help content to be displayed beside the meander representation
		Pane pane2 = new Pane();
		HBox hbox = new HBox();
		Text helpText = new Text("\n\n\n\t\tTake a straight line and number it in equal segments with \n"
				+ "integers. There are two versions to every permutation: open and closed \n"
				+ "meanders. We want closed. Closed meanders cycle back to starting place \n"
				+ "without crisscrossing with other \"routes\" in the middle. \n"
				+ "Left to right edges are colored BLUE. \n" + "Right to left edges are colored RED.");
		hbox.getChildren().addAll(pane, helpText);
		pane2.getChildren().add(hbox);
		// Return pane filled with children
		return pane2;
	}

	public Path[] quadraticCurves(int size) {

		Path PATHS[] = new Path[size];

		/*
		 * ABOVE CURVES
		 */

		for (int i = 0; i < size; i = i + 2) {
			// draw quadratic path
			Path path = new Path();

			// Initializing MoveTo => specifying start coordinates
			MoveTo moveTo = new MoveTo();
			moveTo.setX((0 + 15) + (MainProgram.permutation.get(i) - 1) * 3 * 13);
			moveTo.setY(0);

			// Instantiating the class QuadCurveTo
			QuadCurveTo quadCurveTo = new QuadCurveTo();

			// Setting properties of the class QuadCurve
			try {
				quadCurveTo.setX((0 + 15) + ((MainProgram.permutation.get(i + 1) - 1) * 3 * 13));
			} catch (IndexOutOfBoundsException e) {
				quadCurveTo.setX((0 + 15) + ((MainProgram.permutation.get(0)) * 3 * 13));
			}
			quadCurveTo.setY(0);
			quadCurveTo.setControlX(0 + 15 + 5 * 3 * 13);
			quadCurveTo.setControlY(-140.0f + i * 11);

			// Adding the path elements to Observable list of the Path class
			path.getElements().add(moveTo);
			path.getElements().add(quadCurveTo);
			path.setStrokeWidth(1.5);
			// determining whether the path is going left or right
			try {
				// if the permutation[i] < permutation[i+1], then pathway is from LEFT to RIGHT
				if (MainProgram.permutation.get(i) < MainProgram.permutation.get(i + 1))
					path.setStroke(Color.DARKBLUE);
				// else RIGHT to LEFT
				else
					path.setStroke(Color.RED);
			} catch (Exception e) {
				// if permutation[last_index] < permutation[0], then pathway is going to go from
				// LEFT to RIGHT
				if (MainProgram.permutation.get(0) > MainProgram.permutation.get(i))
					path.setStroke(Color.DARKBLUE);
				// else RIGHT to LEFT
				else
					path.setStroke(Color.RED);
			}

			PATHS[i] = path;
		}

		/*
		 * BELOW CURVES
		 */

		for (int i = 1; i < size; i = i + 2) {
			// draw quadratic path
			Path path = new Path();

			// Initializing MoveTo => specifying start coordinates
			MoveTo moveTo = new MoveTo();
			moveTo.setX((0 + 15) + (MainProgram.permutation.get(i) - 1) * 3 * 13);
			moveTo.setY(0 + 30);

			// Instantiating the class QuadCurveTo
			QuadCurveTo quadCurveTo = new QuadCurveTo();

			// Setting properties of the class QuadCurve
			try {
				quadCurveTo.setX((0 + 15) + ((MainProgram.permutation.get(i + 1) - 1) * 3 * 13));
			} catch (IndexOutOfBoundsException e) {
				quadCurveTo.setX((0 + 15) + ((MainProgram.permutation.get(0) - 1) * 3 * 13));
			}
			quadCurveTo.setY(0 + 30);
			try {
				quadCurveTo.setControlX(0 + 15
						+ (MainProgram.permutation.get(i + 1) + MainProgram.permutation.get(i) - 1) / 2 * 3 * 13);
			} catch (Exception e) {
				quadCurveTo.setControlX(
						0 + 15 + (MainProgram.permutation.get(0) + MainProgram.permutation.get(i) - 1) / 2 * 3 * 13);
			}
			quadCurveTo.setControlY(-140.0f + 140 + 30 + i * 14);

			// Adding the path elements to Observable list of the Path class
			path.getElements().add(moveTo);
			path.getElements().add(quadCurveTo);
			path.setStrokeWidth(1.5);

			// determining whether the path is going left or right
			try {
				// if the permutation[i] < permutation[i+1], then pathway is from LEFT to RIGHT
				if (MainProgram.permutation.get(i) < MainProgram.permutation.get(i + 1))
					path.setStroke(Color.DARKBLUE);
				// else RIGHT to LEFT
				else
					path.setStroke(Color.RED);
			} catch (Exception e) {
				// if permutation[last_index] < permutation[0], then pathway is going to go from
				// LEFT to RIGHT
				if (MainProgram.permutation.get(0) > MainProgram.permutation.get(i))
					path.setStroke(Color.DARKBLUE);
				// else RIGHT to LEFT
				else
					path.setStroke(Color.RED);
			}
			PATHS[i] = path;
		}
		return PATHS;
	}

	public boolean checkForIntersections() {
		Path setOfPaths[] = quadraticCurves(MainProgram.permutation.size());

		// checking the first half of paths (above)
		for (int i = 0; i < MainProgram.permutation.size(); i += 2)
			for (int j = 0; j < i; j += 2)
				if (setOfPaths[i].intersect(setOfPaths[i], setOfPaths[j]) != null)
					return true;

		// checking below paths
		for (int i = 1; i < MainProgram.permutation.size(); i += 2)
			for (int j = 1; j < i; j += 2)
				if (setOfPaths[i].intersect(setOfPaths[i], setOfPaths[j]) != null)
					return true;

		// if no intersection is present
		return false;
	}
}
