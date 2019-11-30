import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SquareGrid {

	static final int sizeOfCellWidth = 22, sizeOfCellHeight = 22, boardWidth = 275, boardHeight = 275;
	static int cellsX = boardWidth / sizeOfCellWidth;
	static int cellsY = boardHeight / sizeOfCellHeight;
	public static NxNGrid[][] grid = new NxNGrid[cellsX][cellsY];
	Pane pane;

	public void removePreviousRepresentation(int whichMenu) {
		cellsX = MainProgram.permutation.size();
		cellsY = MainProgram.permutation.size();
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

	static class NxNGrid extends StackPane {

		// each rectangle is defined as a square.
		// A square = 1 cell;
		// A grid = (cellsX) * (cellsY) number of squares/cells

		Rectangle border = new Rectangle(sizeOfCellWidth, sizeOfCellHeight);

		// This constructor is called each time a new cell has to be defined
		public NxNGrid(int x, int y) {
			// Add each cell as the pane's children
			getChildren().add(border);
			// Each cell's dimensions are of sizeOfCellWidth and sizeOfCellHeight
			setTranslateX(x * sizeOfCellWidth);
			setTranslateY(y * sizeOfCellHeight);
			// Each cell is initially of a light blue color
			border.setFill(Color.LIGHTGREY);
			border.setStroke(Color.BLACK);
		}

	}

	public Pane createTable() {
		pane = new Pane();
		pane.setPadding(new Insets(10));
		// Define board's dimensions
		pane.setPrefSize(boardWidth, boardHeight);
		// Fill the board with cells
		for (int i = 0; i < cellsX; i++) {
			for (int j = 0; j < cellsY; j++) {
				grid[i][j] = new NxNGrid(i, j);
				pane.getChildren().add(grid[i][j]);
			}
		}

		coloredSquares(); // red ones
		inversions(); // green ones
		Pane pane2 = new Pane();
		HBox hbox = new HBox();
		Text helpText = new Text("The square grid on the left shows one of the representations for a \n"
				+ "permutation. \n\nThe rules are that for a permutation of size N, a grid \n"
				+ "of NxN dimensions must be created first.\n\n"
				+ "Column j of row i is colored RED whenever permutation[i]=j.\n\n"
				+ "Then, GREEN colored squares represent inversions:\n"
				+ "These are colored by seeing those squares which have colored \n"
				+ "squares lying both below (in same column) and to their right (same \n"
				+ "row). If there are no GREEN squares, then the permutation is ordered.");
		hbox.getChildren().addAll(pane, helpText);
		pane2.getChildren().addAll(hbox);
		// Return pane filled with children
		return pane2;
	}

	public void coloredSquares() {
		// coloring those squares that will not be part of set of inversions
		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			// horizontally coloring squares
			int colorRow = MainProgram.permutation.get(i);
			for (int j = colorRow; j < cellsY; j++) {
				grid[j][i].border.setFill(Color.WHITE);
			}
			// vertically coloring squares
			for (int j = i; j < cellsY; j++) {
				grid[colorRow - 1][j].border.setFill(Color.WHITE);
			}
		}

		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			int colorRow = MainProgram.permutation.get(i);
			/*
			 * Remember that the permutation's 'row' is from 1 to N. However, an ArrayList's
			 * indices are from 0 to N-1
			 */
			grid[colorRow - 1][i].border.setFill(Color.RED);
		}

	}

	// remaining squares are inversions
	public void inversions() {
		for (int i = 0; i < cellsX; i++) {
			for (int j = 0; j < cellsY; j++) {
				if (grid[i][j].border.getFill().equals(Color.LIGHTGREY))
					grid[i][j].border.setFill(Color.LIME);
			}
		}
	}

}
