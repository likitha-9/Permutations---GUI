import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FactoradicNumber {
	public void factoradic(int whichMenu) {
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

		InversionTable inversion = new InversionTable();
		int[] permutationInversion = inversion.inversion();
		int factorialStartingSize = MainProgram.permutation.size() - 1;
		String dummy = new String("FACTORADIC REPRESENTATION: \n");

		long factoradic = 0;
		for (int i = factorialStartingSize, j = 0; i >= 0; i--, j++) {
			int result = factorial(i);
			factoradic += permutationInversion[j] * result;
		}

		dummy += " " + factoradic;

		System.out.println(dummy);
		Label factoradicString = new Label(dummy);
		factoradicString.setTextFill(Color.DARKBLUE);
		factoradicString.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));

		String inversionTable = new String();
		for (int i = 0; i < permutationInversion.length; i++) {
			inversionTable += Integer.toString(permutationInversion[i]) + " ";
		}
		String explanation = construction(factorialStartingSize, permutationInversion);
		Text helpText = new Text(
				"\n\tA factoradic number is a sequence of digits where each digit has a different radix in particular, the radix \n"
						+ "\tfor digit dI = (n-I)!, where I = index and N = length of permutation."
						+ "\n\ti.e., rightmost digit has base!, next digit has base 2!, and so on such that each digit\n"
						+ "\tin a factoradic number is smaller than its base.\n"
						+ "\n\tFirst, find the inversion table of the given permutation: " + inversionTable
						+ "\n\tNext, calculate the factoradic number: " + "\n\t\t" + explanation + " = " + factoradic);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(factoradicString, helpText);
		
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

	private int factorial(int i) {
		int result = 1;
		for (int j = 2; j <= i; j++)
			result *= j;
		return result;
	}

	//for showing for the calculation of factoradic number
	private String construction(int size, int[] inversion) {
		String work = new String();
		for (int i = size - 1, j = 0; i >= 0 && j < inversion.length; i--, j++) {
			work += "( " + Integer.toString(inversion[j]) + " x " + Integer.toString(i) + "! )";
			if (i == 4)
				work += "\n\t\t\t";
			if (i != 0)
				work += " + ";
		}
		return work;
	}
}
