import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GeneratePermutation {

	Label enterLabel = new Label("Enter your own permutation (separated by spaces):\t"),
			randomLabel = new Label("Enter the size of random permutation:\t");
	TextField enterText = new TextField(), randomText = new TextField();
	Button enterYourOwn = new Button("Enter"), generateRandomly = new Button("Enter");

	Text separator1 = new Text("\t");
	Text separator2 = new Text("\t");

	int integers[];// for user-given permutation
	int size;// for random permutation

	ArrayList<Integer> listOfNumbers = new ArrayList<Integer>();

	public void popUpWindow() {

		// setting hint texts for the user
		enterText.setPromptText("Separate each digit.");
		randomText.setPromptText("1 < Size <= 12");

		// opening a new stage
		Stage stage = new Stage();
		stage.setTitle("Generating New Permutation...");

		// place the content inside.
		HBox hbox1 = new HBox(), hbox2 = new HBox();
		hbox1.getChildren().addAll(enterLabel, enterText, separator1, enterYourOwn);
		hbox2.getChildren().addAll(randomLabel, randomText, separator2, generateRandomly);

		VBox dialogBox = new VBox(20);
		dialogBox.setPadding(new Insets(10));
		dialogBox.getChildren().addAll(hbox1, hbox2);

		// set content into scene
		Scene scene = new Scene(dialogBox, 600, 100);
		stage.setScene(scene);

		// set implementations for user actions
		enterYourOwn.setOnMouseClicked(e -> {
			// if permutation is entered correctly
			if (checkOwnPermutationFields()) {
				MainProgram.permutation = new ArrayList<Integer>();
				for (int i = 0; i < integers.length; i++)
					MainProgram.permutation.add(integers[i]);

				// for permutation of size N, make sure that it includes numbers from 1-N
				boolean flag = true;
				for (int i = 1; i <= MainProgram.permutation.size(); i++) {
					if (MainProgram.permutation.contains(i))
						continue;
					else {
						flag = false;
						displayErrorMessages(
								new Text("Permutation of size N should include distinct numbers from 1 to N."));
					}
				}
				// if user entered permutation correctly && followed the rules
				if (flag) {
					// replace the previous one with the current permutation
					String dummy = new String("One-line notation: ");
					for (int i = 0; i < MainProgram.permutation.size(); i++) {
						dummy = dummy + " " + MainProgram.permutation.get(i).toString();
					}

					// remove the previously filled entries.
					MainProgram.permutationNotation.getChildren().remove(MainProgram.notations);
					MainProgram.notations.getChildren().removeAll(MainProgram.oneLine, MainProgram.cyclic);
					MainProgram.oneLine.getChildren().remove(MainProgram.displayOneLinePermutation);
					MainProgram.cyclic.getChildren().remove(MainProgram.displayCyclicNotation);
					MainProgram.forPermutationEntry.getItems().remove(MainProgram.permutationNotation);

					// initialize new values

					MainProgram.displayOneLinePermutation = new Label(dummy);

					// Call the method for cyclic notation!!
					for (int i = 0; i < MainProgram.permutation.size(); i++)
						listOfNumbers.add(i);

					String string = new String();
					for (int i = 0; i < listOfNumbers.size(); i++)
						if (listOfNumbers.get(i) != -1)
							string = string + cyclicNotation(i);
					System.out.println(string);

					// Show the cyclic notation on screen.
					MainProgram.displayCyclicNotation = new Label("Cyclic Notation: " + string);

					// display the new values
					// add children in the correct order!
					MainProgram.oneLine.getChildren().addAll(new Text(), MainProgram.displayOneLinePermutation);
					MainProgram.cyclic.getChildren().addAll(new Text(), MainProgram.displayCyclicNotation);
					MainProgram.notations.getChildren().addAll(MainProgram.oneLine, MainProgram.cyclic);
					MainProgram.permutationNotation.getChildren().add(MainProgram.notations);
					MainProgram.forPermutationEntry.getItems().add(MainProgram.permutationNotation);

					// close the window
					stage.close();
					MainProgram.resetPreviousRepresentations();
				}
			}

			else {
				displayErrorMessages(new Text(
						"Error: Please enter a valid permutation!\nRefer to help topics if there's any trouble."));
			}

		});

		generateRandomly.setOnMouseClicked(e -> {
			// if size of permutation is entered correctly
			if (checkRandomPermutationFields()) {
				integers = new int[size];
				ArrayList<Integer> randomIntegers = new ArrayList<Integer>();
				MainProgram.permutation = new ArrayList<Integer>();

				// if size=N, keep a storage of numbers from 1-N
				for (int i = 1; i <= size; i++)
					randomIntegers.add(i);
				// Pick a random index from the above list to constitute a random permutation
				Random randomIndex = new Random();
				for (int i = 0; i < integers.length; i++) {
					int index = randomIndex.nextInt(randomIntegers.size());
					integers[i] = randomIntegers.get(index);
					randomIntegers.remove(randomIntegers.get(index));
				}
				// make this set of numbers as the permutation
				for (int i = 0; i < integers.length; i++)
					MainProgram.permutation.add(integers[i]);

				// replace the previous one with the current permutation
				String dummy = new String("One-line notation: ");
				for (int i = 0; i < MainProgram.permutation.size(); i++) {
					dummy = dummy + " " + MainProgram.permutation.get(i).toString();
				}
				// remove the previously filled entries.
				MainProgram.permutationNotation.getChildren().remove(MainProgram.notations);
				MainProgram.notations.getChildren().removeAll(MainProgram.oneLine, MainProgram.cyclic);
				MainProgram.oneLine.getChildren().remove(MainProgram.displayOneLinePermutation);
				MainProgram.cyclic.getChildren().remove(MainProgram.displayCyclicNotation);
				MainProgram.forPermutationEntry.getItems().remove(MainProgram.permutationNotation);

				// initialize new values

				MainProgram.displayOneLinePermutation = new Label(dummy);

				// Call the method for cyclic notation!!
				for (int i = 0; i < MainProgram.permutation.size(); i++)
					listOfNumbers.add(i);

				String string = new String();
				for (int i = 0; i < listOfNumbers.size(); i++)
					if (listOfNumbers.get(i) != -1)
						string = string + cyclicNotation(i);
				System.out.println(string);

				// Show the cyclic notation on screen.
				MainProgram.displayCyclicNotation = new Label("Cyclic Notation: " + string);

				// display the new values
				// add children in the correct order!
				MainProgram.oneLine.getChildren().addAll(new Text(), MainProgram.displayOneLinePermutation);
				MainProgram.cyclic.getChildren().addAll(new Text(), MainProgram.displayCyclicNotation);
				MainProgram.notations.getChildren().addAll(MainProgram.oneLine, MainProgram.cyclic);
				MainProgram.permutationNotation.getChildren().add(MainProgram.notations);
				MainProgram.forPermutationEntry.getItems().add(MainProgram.permutationNotation);

				// close the window
				stage.close();
				MainProgram.resetPreviousRepresentations();
			}
		});

		// show content to user
		stage.show();
	}

	// check if user entered permutation properly
	public boolean checkOwnPermutationFields() {
		String string = enterText.getText();
		String array[] = string.split("\\s+");
		integers = new int[array.length];
		if (array.length > 12) {
			displayErrorMessages(new Text("Error: Only a maximum of 12 entries are allowed!"));
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			try {
				integers[i] = Integer.parseInt(array[i]);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	// check if user entered appropriate size
	public boolean checkRandomPermutationFields() {
		try {
			size = Integer.parseInt(randomText.getText());
			if (size > 1 && size <= 12)
				return true;
			else
				displayErrorMessages(new Text(
						"Error: Please try again and follow the condition: 1 < size <=12\nRefer to help topics if there's any trouble."));

		} catch (NumberFormatException e) {
			// if user entered anything than a valid number such as a random text
			displayErrorMessages(new Text(
					"Error: Please enter size of permutation appropriately!\n(Only natural numbers are allowed.)"));
		}
		return false;
	}

	public String cyclicNotation(int startingIndex) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		// 5 9 1 8 2 6 4 7 3
		int index = listOfNumbers.get(startingIndex);
		array.add(index);
		// System.out.print((index + 1) + " ");
		int i = 0;
		while (true) {
			if (i == MainProgram.permutation.size())
				break; // just a precaution & condition to prevent infinite looping
			i++;

			int number = MainProgram.permutation.get(index) - 1;
			if (!array.contains(number))
				array.add(number);
			else
				break; // one cycle has been identified and the integers stored till now will be
						// returned

			for (int j = 0; j < listOfNumbers.size(); j++) {
				if (listOfNumbers.get(j) == number) {
					index = listOfNumbers.get(j);
					listOfNumbers.set(j, -1); // set a negative number to say that the index has been traversed through
												// and there's no need to visit it again
					break;
				}
			}

		}

		// Turn all the integers in the array into a string format.
		String string = new String();
		string += "( "; // add '(' to denote starting of cycle
		for (int j = 0; j < array.size(); j++) {
			string += Integer.toString(array.get(j) + 1) + " "; // convert int to String
		}
		string += ") "; // add ')' to denote ending of cycle

		System.out.println(string);

		// return that string
		return string;
	}

	public void displayErrorMessages(Text text) {
		Stage errorStage = new Stage();
		errorStage.setTitle("ERROR!");

		VBox errorDialogBox = new VBox(20);
		errorDialogBox.setPadding(new Insets(20));
		errorDialogBox.getChildren().addAll(text);

		Scene errorScene = new Scene(errorDialogBox, 390, 70);
		errorStage.setScene(errorScene);
		errorStage.show();
	}
}
