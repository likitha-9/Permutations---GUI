import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GridTours {

	Pane pane;
	static int sizeOfCellWidth = 27 - (2 * MainProgram.permutation.size()),
			sizeOfCellHeight = 27 - (2 * MainProgram.permutation.size()), boardWidth = sizeOfCellWidth * 12,
			boardHeight = sizeOfCellHeight * 12;
	static int cellsX = boardWidth / sizeOfCellWidth;
	static int cellsY = boardHeight / sizeOfCellHeight;
	public static NxNGrid[][] grid = new NxNGrid[cellsX * 2 + 1][cellsY * 2 + 1];

	// for 2nd permutation
	int[] array = new int[MainProgram.permutation.size()];
	ArrayList<Integer> rows = new ArrayList<Integer>(), columns = new ArrayList<Integer>();
	ArrayList<Integer> visitedRows = new ArrayList<Integer>(), visitedColumns = new ArrayList<Integer>();
	int currentRow = -1, currentColumn = -1, visitedRow = -1, visitedColumn = -1;

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
			// Each cell is initially of a light grey color
			border.setFill(Color.LIGHTGREY);
			if (x % 2 == 1 && y % 2 == 1) // create alternate cells initially (not visible to the user)
				setColor1();
			else
				setColor2();
			// if(x==0 || x==cellsX*2+1)
			// setColor2();
			// if(x==0)
			// setColor2();
		}

		public void setColor1() {
			border.setFill(Color.DARKGREY);
		}

		public void setColor2() {
			border.setFill(Color.BLUE);
		}

	}

	public Pane createGrid() {
		pane = new Pane();
		pane.setPadding(new Insets(10));
		// Define board's dimensions
		sizeOfCellWidth = 32 - (2 * MainProgram.permutation.size());
		sizeOfCellHeight = 32 - (2 * MainProgram.permutation.size());
		pane.setPrefSize(boardWidth, boardHeight);

		// Fill the board with cells
		for (int i = 0; i <= cellsX * 2; i++) {
			for (int j = 0; j <= cellsY * 2; j++) {
				grid[i][j] = new NxNGrid(i, j);
				pane.getChildren().add(grid[i][j]);

			}
		}

		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			rows.add(i);
			columns.add(i);
		}

		// call colorGrid() method
		int count = 0;
		int i = 0;
		while (incomplete()) {
			count++;
			if (count >= 3 * MainProgram.permutation.size())
				break;
			i++;
			int matrixVariables[] = colorGrid(MainProgram.permutation.get(rows.get(rows.size() - 1)) - 1, array[i]);
			currentRow = matrixVariables[0];
			currentColumn = matrixVariables[1];

			int[] dummy = new int[array.length];
			boolean breakFlag = false;
			for (int j = 0; j < array.length; j++) {
				try {
					dummy[j] = array[j]; // see if NullPointerException occurs.
					// if so, then it means that some of the indices of array[] have been assigned a
					// value yet.
					// this means that 2nd permutation has not been fully generated yet.
				} catch (NullPointerException e) {
					if (j == array.length - 1)
						breakFlag = true; // no exception occurs=>2nd permutation generated
					continue;
				}
			}
			if (checkForRightTurns() && rows.size() == 0 && columns.size() == 0 && breakFlag)
				break;
			else {
				// restart the loop again if there are any left turns present
				i = 0;
				count = 0;
				for (int j = 0; j < MainProgram.permutation.size(); j++) {
					rows.add(j);
					columns.add(j);
				}
				continue;
			}
		}

		if (incomplete())
			colorGrid(rows.get(0), columns.get(0));

		if (complete() && checkForRightTurns()) {
			redSquares(); // highlight the red colored squares.
		}
		Text helpText = new Text();

		String originalPermutation = new String("( ");
		for (int j = 0; j < MainProgram.permutation.size(); j++)
			originalPermutation += MainProgram.permutation.get(j) + " ";
		originalPermutation += " )";
		if (complete())
			helpText = new Text("Begin with a grid of N horizontal lines and N vertices lines.\n"
					+ "Here, N is the length of permutation. Also, (N%2)=0.\n"
					+ "The paths on the grid follow specific rules: each turn is\na right turn by 90 degrees.\n"
					+ "The path has exactly 1 segment on each horizontal line and \n1 segment on each vertical line.\n"
					+ "You need 2 permutations in order to draw a grid tour. \nOne represents the row and the other represents column."
					+ "\nRED squares simply represent a turn.\nGREEN squares represent the pathway on that turn.\n\n"
					+ "Original permutation:" + originalPermutation + "\n" + "Generated permutation:"
					+ displaySecondPermutation(array));

		// add all of the above information as children of a second pane and return this
		// pane
		Pane pane2 = new Pane();
		HBox hbox = new HBox();

		hbox.getChildren().addAll(pane, helpText);
		pane2.getChildren().addAll(hbox);
		// Return pane filled with children
		return pane2;
	}

	private boolean incomplete() {
		if (columns.size() != 0 || rows.size() != 0) // if all rows & columns haven't been visited, return false
			return false;
		else
			return true;
	}

	private boolean complete() {
		if (columns.size() == 0 || rows.size() == 0) // if all rows & columns haven't been visited, return false
			return true;
		else
			return false;
	}

	public int[] colorGrid(int row, int column) {
		Random random = new Random();
		if (row == -1 && column == -1) {
			// pick a row && column to start with
			row = random.nextInt(MainProgram.permutation.size() - 1) * 2;
			column = random.nextInt(MainProgram.permutation.size() - 1) * 2;
			currentRow = row;
			currentColumn = column;
			grid[currentColumn][currentRow].border.setFill(Color.DARKBLUE);
			// add that row & column to visited and update current node
			visitedRow = -1;
			visitedColumn = -1;
			visitedRows.add(currentRow);
			visitedColumns.add(currentColumn);
			// remove that row and column from rows/columns to be traversed
			rows.remove(rows.get(currentRow));
			columns.remove(columns.get(currentColumn));
			// start with 2nd permutation
			array[0] = currentColumn;
		} else {
			visitedRow = currentRow;
			visitedColumn = currentColumn;
			currentRow = row;
			currentColumn = column;
			// don't visit traversed nodes again.
			int newAddition = -1; // dummy initialization
			for (int i = 0; i < rows.get(row); i++) {
				grid[i][columns.get(column)].border.setFill(Color.LIME);
			}
			for (int j = 0; j < columns.get(column); j++) {
				grid[rows.get(row)][j].border.setFill(Color.LIME);
				if (j == columns.get(column) - 1)
					newAddition = columns.get(column);

			}
			// add that to the new permutation.
			int dummy[] = new int[array.length];
			boolean breakFlag = false;
			int additionIndex = -1;
			for (int j = 0; j < array.length; j++) {
				try {
					dummy[j] = array[j]; // see if NullPointerException occurs.
					// if so, then it means that some of the indices of array[] have been assigned a
					// value yet.
					// this means that 2nd permutation has not been fully generated yet.
					// wherever the FIRST exception occurs, that's the next addition of the 2nd
					// permutation
					additionIndex = j;
				} catch (NullPointerException e) {
					breakFlag = true;
					break;
				}
				if (breakFlag)
					break;
			}
			array[additionIndex] = columns.get(newAddition);
		}
		return array;
	}

	public boolean checkForRightTurns() {
		for (int i = 0; i < cellsX * 2 + 1; i++) {
			for (int j = 0; j < cellsY * 2 + 1; j++) {
				// only apply this method if the grid has been colored with anything other than
				// default grey
				if (grid[i][j].border.getFill().equals(Color.DARKBLUE) || grid[i][j].border.getFill().equals(Color.LIME)
						|| grid[i][j].border.getFill().equals(Color.RED)) {

					// check for each valid turn.

					// a valid turn occurs when grid[i][j]'s color matches one of its diagonal
					// neighbor's colors
					if (i > j) {
						if (!grid[2 * i][2 * j + 1].border.getFill().equals(Color.LIME))
							return false;
					}
					if (i == j) {
						if (!grid[2 * i - 1][2 * j].border.getFill().equals(Color.LIME))
							return false;
					}
					if (i < j) {
						if (!grid[2 * i][2 * j - 1].border.getFill().equals(Color.LIME))
							return false;
					}

				} else// ignore
					continue;
			}
		}
		return true;
	}

	public void redSquares() {
		for (int i = 0; i < cellsX * 2 + 1; i++) {
			for (int j = 0; j < cellsY * 2 + 1; j++) {
				// only apply this method if the grid has been colored with anything other than
				// default grey
				if (grid[i][j].border.getFill().equals(Color.DARKBLUE)
						|| grid[i][j].border.getFill().equals(Color.LIME)) {
					// check each diagonal neighbor
					// 4 different scenarios can exist!

					// scenario one: if one green/blue square is [row][column] & the other is at
					// [row+1][column+1]
					if ((grid[i][j].border.getFill().equals(Color.LIME)
							|| grid[i][j].border.getFill().equals(Color.DARKBLUE))
							&& (grid[i + 1][j + 1].border.getFill().equals(Color.LIME)
									|| grid[i + 1][j + 1].border.getFill().equals(Color.DARKBLUE))) {
						if ((grid[i - 1][j].border.getFill().equals(Color.LIME)
								|| grid[i - 1][j].border.getFill().equals(Color.DARKBLUE))
								&& (grid[i + 1][j + 1 + 1].border.getFill().equals(Color.LIME)
										|| grid[i + 1][j + 1 + 1].border.getFill().equals(Color.DARKBLUE)))
							grid[i + 1][j].border.setFill(Color.RED);
					}

					// scenario two: if one green/blue square is [row+1][column] & the other is at
					// [row][column+1]
					if ((grid[i + 1][j].border.getFill().equals(Color.LIME)
							|| grid[i + 1][j].border.getFill().equals(Color.DARKBLUE))
							&& (grid[i][j + 1].border.getFill().equals(Color.LIME)
									|| grid[i][j + 1].border.getFill().equals(Color.DARKBLUE))) {
						if ((grid[i + 1 + 1][j].border.getFill().equals(Color.LIME)
								|| grid[i + 1 + 1][j].border.getFill().equals(Color.DARKBLUE))
								&& (grid[i + 1 - 1][j + 1 + 1].border.getFill().equals(Color.LIME)
										|| grid[i + 1 - 1][j + 1 + 1].border.getFill().equals(Color.DARKBLUE)))
							grid[i][j].border.setFill(Color.RED);
					}

					// scenario three: if one green/blue square is [row][column] & the other is at
					// [row+1][column+1] (symmetric to scenario 1)
					if ((grid[i][j].border.getFill().equals(Color.LIME)
							|| grid[i][j].border.getFill().equals(Color.DARKBLUE))
							&& (grid[i + 1][j + 1].border.getFill().equals(Color.LIME)
									|| grid[i + 1][j + 1].border.getFill().equals(Color.DARKBLUE))) {
						if ((grid[i][j - 1].border.getFill().equals(Color.LIME)
								|| grid[i][j - 1].border.getFill().equals(Color.DARKBLUE))
								&& (grid[i + 1 + 1][j + 1].border.getFill().equals(Color.LIME)
										|| grid[i + 1 + 1][j + 1].border.getFill().equals(Color.DARKBLUE)))
							grid[i][j + 1].border.setFill(Color.RED);
					}

					// scenario four: if one green/blue square is [row+1][column] & the other is at
					// [row][column+1] (symmetric to scenario two)
					if ((grid[i + 1][j].border.getFill().equals(Color.LIME)
							|| grid[i + 1][j].border.getFill().equals(Color.DARKBLUE))
							&& (grid[i][j + 1].border.getFill().equals(Color.LIME)
									|| grid[i][j + 1].border.getFill().equals(Color.DARKBLUE))) {
						if ((grid[i + 1][j - 1].border.getFill().equals(Color.LIME)
								|| grid[i + 1][j - 1].border.getFill().equals(Color.DARKBLUE))
								&& (grid[i - 1][j + 1].border.getFill().equals(Color.LIME)
										|| grid[i - 1][j + 1].border.getFill().equals(Color.DARKBLUE)))
							grid[i][j].border.setFill(Color.RED);
					}
				}

				else
					continue;// ignore
			}

		}

	}

	public String displaySecondPermutation(int[] array) {
		String string = new String();
		string += "( ";
		for (int i = 0; i < array.length; i++) {
			string += Integer.toString(array[i]);
		}
		string += " )";
		return string;
	}

}
