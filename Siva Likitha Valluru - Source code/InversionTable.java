import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class InversionTable {

	public void inversion(int whichMenu) {
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

		int[] inversion = new int[MainProgram.permutation.size()];

		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			int count = 0;
			for (int j = 0; j < i; j++) {
				/*
				 * if the previous numbers from 1 to i are greater than the number in the
				 * correct index, then do count++;
				 */
				if (MainProgram.permutation.get(j) > MainProgram.permutation.get(i))
					count++;
			}
			// add this value of count variable into the inversion table
			System.out.println(count);
			inversion[MainProgram.permutation.get(i) - 1] = count;
		}

		String dummy = new String("INVERSION TABLE: ");
		for (int i = 0; i < inversion.length; i++) {
			dummy += " " + inversion[i];
		}

		System.out.println(dummy);
		Label inversionString = new Label(dummy);
		inversionString.setTextFill(Color.DARKBLUE);
		inversionString.setFont(Font.font("Times New Roman", FontWeight.BOLD, 34));
		Text helpText = new Text("\n\n\tAn inversion table uniquely determines the corresponding permutation.\n"
				+ "\n\tInversion table b1, b2, b3, ..., bN of the permutation a1, a2, a3, ..., aN is obtained by letting bj \n"
				+ "\tbe the number of elements to the left of ai = j greater than j. \n"
				+ "\t\t(i.e., bj = number of inversions whose second component equals j.)");

		VBox vbox = new VBox();
		vbox.getChildren().addAll(inversionString, helpText);
		// add this new representation into the correct partition
		switch (whichMenu) {
		case 1:
			Pane pane1 = new Pane();
			pane1.getChildren().add(vbox);
			MainProgram.canvas1.getItems().add(pane1);
			break;
		case 2:
			Pane pane2 = new Pane();
			pane2.getChildren().add(vbox);
			MainProgram.canvas2.getItems().add(pane2);
			break;
		case 3:
			Pane pane3 = new Pane();
			pane3.getChildren().add(vbox);
			MainProgram.canvas3.getItems().add(pane3);
			break;
		case 4:
			Pane pane4 = new Pane();
			pane4.getChildren().add(vbox);
			MainProgram.canvas4.getItems().add(pane4);
			break;
		}
	}

	// for finding permutation's factoradic representation
	public int[] inversion() {
		int[] inversion = new int[MainProgram.permutation.size()];

		for (int i = 0; i < MainProgram.permutation.size(); i++) {
			int count = 0;
			for (int j = 0; j < i; j++) {
				/*
				 * if the previous numbers from 1 to i are greater than the number in the
				 * correct index, then do count++;
				 */
				if (MainProgram.permutation.get(j) > MainProgram.permutation.get(i))
					count++;
			}
			// add this value of count variable into the inversion table
			inversion[MainProgram.permutation.get(i) - 1] = count;
		}
		return inversion;
	}
}
