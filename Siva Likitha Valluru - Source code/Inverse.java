import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Inverse {

	public void findingInverse(int whichMenu) {
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

		ArrayList<Integer> inverse = new ArrayList<Integer>();

		for (int i = 1; i <= MainProgram.permutation.size(); i++)
			inverse.add(MainProgram.permutation.indexOf(i) + 1);

		String dummy = new String("INVERSE: ");
		for (int i = 0; i < inverse.size(); i++) {
			dummy += " " + inverse.get(i);
		}

		System.out.println(dummy);
		Label inverseString = new Label(dummy);
		inverseString.setTextFill(Color.DARKBLUE);
		inverseString.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
		Text helpText = new Text(
				"\n\n\tA permutation's inverse is another permutation of the same length with the same number of elements.\n"
						+ "\n\tTo find the inverse, first list out two rows, with one top lining up exactly on top of another.\n"
						+ "\tThe top row should be sorted, whereas the bottom resembles the original permutation.\n"
						+ "\tInterchange the two rows and sort top new row in increasing order using the second one.\n"
						+ "\n\tFor example, say the first digit of top row is 10. And the first digit of bottom row is 5.\n"
						+ "\tIn this case, the digit 10 would be in the 5th position of the inverse.");

		VBox vbox = new VBox();
		vbox.getChildren().addAll(inverseString, helpText);
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
}
