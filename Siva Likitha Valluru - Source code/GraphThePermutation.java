import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;

public class GraphThePermutation {
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
	public static PermutationGraph[][] nodes = new PermutationGraph[12][1];

	// private static Random randomColorPicker = new Random();

	static class PermutationGraph extends StackPane {

		Circle circle = new Circle(15);
		// each circle = one node

		// This constructor is called each time a new node has to be defined
		public PermutationGraph(int x, int y) {
			// Add each cell as the pane's children
			getChildren().add(circle);
			int scale = x % 3;
			setTranslateX(scale * 150);
			setTranslateY((int) (x / 3) * 60);

			// Each cell is initially of a light blue color
			Color randomColorFill = Color.rgb(0, 0, 255 - x * 19);

			circle.setFill(randomColorFill);
			circle.setStroke(Color.BLACK);

		}

		Text text = new Text();

		// for numbering each node
		public PermutationGraph(int x, int y, int stroke) {
			getChildren().add(text);
			int scale = x % 3;
			setTranslateX(scale * 150);
			setTranslateY((int) (x / 3) * 60);
			text.setText(Integer.toString(x + 1));
			text.setFill(Color.WHITE);
			text.setStrokeWidth(stroke);

		}

	}

	public Pane createTable() {
		pane = new Pane();
		pane.setPadding(new Insets(10));
		// Define board's dimensions
		pane.setPrefSize(240, 240);
		// Fill the board with cells
		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			for (int j = 0; j < 1; j++) {

				StackPane stack = new StackPane();

				nodes[i][j] = new PermutationGraph(i, j);

				PermutationGraph text = new PermutationGraph(i, j, 4);

				stack.getChildren().addAll(nodes[i][j], text);
				pane.getChildren().addAll(stack);

			}
		}

		SquareGrid squares = new SquareGrid();
		squares.createTable();
		int count = 0;
		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			for (int j = 0; j < MainProgram.permutation.size(); j++) {
				if (squares.grid[i][j].border.getFill().equals(Color.LIME)) {
					Path inversionEdge = inversions(i, j);
					pane.getChildren().add(inversionEdge);
					count++;
				}

			}
		}
		System.out.println(count);
		return pane;
	}

//	static int colorIncrement = 10, currentStatus = 100;

	public Path inversions(int i, int j) {// i=1;j=1;
		// initializations
		System.out.println("i: " + i + "\tj:" + j);
		Path edge = new Path();

		int x1 = (MainProgram.permutation.get(j) % 3);
		int y1 = (int) Math.floor((MainProgram.permutation.get(j) + 1) / 3) - 1;

		int x2 = ((i) % 3);
		int y2 = (i) / 3;

		MoveTo move = new MoveTo();
		QuadCurveTo quad = new QuadCurveTo();
		// draw the edges
		move.setX(15 + (x1) * 150);
		move.setY(15 + 30 * 2 * y1 - 0);

		quad.setX(15 + x2 * 150);
		quad.setY(15 + 15 * y2 + 30 * y2);
		quad.setControlX(quad.getX());
		quad.setControlY(quad.getY());

		// edge.setFill(Color.BLACK);
		edge.getElements().addAll(move, quad);
		edge.setStroke(Color.BLACK);
		edge.setStrokeWidth(1.5);

		return edge;

	}
}
