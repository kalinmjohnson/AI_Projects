package sudoku;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * View and Controller for the Sudoku Game
 * 
 * @author Kalin Johnson
 * @version 1.2
 */

public class SudokuVC extends Application implements PropertyChangeListener {

	/**
	 * This attribute stores the model or back end of the game
	 */
	private SudokuModel myModel;

	/**
	 * This attribute stores the AI player for the game
	 */

	private SudokuPlayer player;

	/**
	 * This attribute is a 2D array of the buttons on the GUI side of the game board
	 */
	private Button[][] gameBoard;

	/**
	 * This is where the number along the bottom of the screen is stored before it
	 * is inputed into the model
	 */
	private int storedNumber;

	/**
	 * This 2D array stores the colors of the borders of the game tiles
	 */
	private String[][] colors;

	/**
	 * Stores the coordinates of the numbers currently being highlighted, so they
	 * can be un-highlighted in the future
	 */

	private int[][] currentHighlights;

	/**
	 * These attributes store the outline color for each player
	 */
	private String player1Color;
	private String player2Color;

	/**
	 * These attributes are used in the home screen
	 */

	/**
	 * This button is clicked to go to the game screen and play the game
	 */
	private Button play;

	/**
	 * This button is clicked to go to the settings screen
	 */
	private Button settings;

	/**
	 * Just a label that displays a fun fact about sudoku
	 */
	private Label funFacts;

	/**
	 * Holds the background image, while is read in at the beginning
	 */
	private BackgroundImage homeScreenBackground;

	/**
	 * Game Board Stage The structure (GridPanes), buttons, labels, and background
	 * visible on the gameboard screen
	 */

	/**
	 * This gridpane displays the buttons for the main board
	 */
	private GridPane board;

	/**
	 * This gridpane stores the buttons on the bottom that are used for inputting
	 * and highlighting
	 */
	private GridPane inputValues;

	/**
	 * This button is clicked to start a new game
	 */
	private Button newGame;

	/**
	 * This button brings you back to the home screen so you can access the settings
	 */
	private Button exit;

	/**
	 * This label displays the feedback provided by the model
	 */
	private Label feedback;

	/**
	 * This 2D array stores the buttons across the button for future acess
	 */
	private Button[] inputNumbers;

	/**
	 * This image is displayed on the background of the game stage
	 */
	private BackgroundImage gameBackground;

	/**
	 * Settings Stage Below are all the elements for the settings stage
	 */

	/**
	 * This button brings you the home screen
	 */
	private Button exitS;

	/**
	 * Tells the user they are looking at the setting page
	 */
	private Label SettingsTitle;

	/**
	 * Displays the highscore
	 */
	private Label Highscore;

	/**
	 * Displays the options for the colors of the border for the two players
	 */
	private ComboBox<String> colorOptions1;
	private ComboBox<String> colorOptions2;

	/**
	 * Allows the players to choose the number of points for different completions
	 * between games
	 */
	private ComboBox<String> columnPoints;
	private ComboBox<String> rowPoints;
	private ComboBox<String> squarePoints;
	private ComboBox<String> wrongMovePoints;

	/**
	 * Stores the image displayed in the background of the settings stage
	 */
	private BackgroundImage settingsBackground;

	/**
	 * The following Strings use css in java to customize the look of different
	 * elements on the screen
	 * 
	 * The ones below are used for elements that are repeated so they can be used in
	 * multiple places
	 * 
	 * These elements include the buttons, the labels, and the comboboxes There are
	 * different variations on these depending on their purpose
	 */

	static final String buttonStyle = "-fx-background-color: rgba(28,77,77,0.7);" + "-fx-background-radius: 5,4,3,5;"
			+ "-fx-background-insets: 0,1,2,0;" + "-fx-text-fill: white;"
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
			+ "-fx-font-family: \"Times New Roman\";" + "-fx-text-fill: linear-gradient(white, #d0d0d0);"
			+ "-fx-font-size: 35px;" + "-fx-padding: 10 20 10 20;" + "-fx-border-radius: 3 3 3 3;"
			+ "-fx-background-radius: 3 3 3 3;";

	static final String buttonGameScreenStyle = "-fx-background-color: rgba(28,77,77,0.7);"
			+ "-fx-background-radius: 5,4,3,5;" + "-fx-background-insets: 0,1,2,0;" + "-fx-text-fill: white;"
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
			+ "-fx-font-family: \"Times New Roman\";" + "-fx-text-fill: linear-gradient(white, #d0d0d0);"
			+ "-fx-font-size: 25px;" + "-fx-padding: 5 5 5 5;" + "-fx-border-radius: 3 3 3 3;"
			+ "-fx-background-radius: 3 3 3 3;";

	static final String inputNumberStyle = "-fx-background-color: rgba(127,176,176,0.8);"
			+ "-fx-background-radius: 3,3,3,3;" + "-fx-font-size: 25px;" + "-fx-font-family: \"Times New Roman\";"
			+ "-fx-font-weight: bold;" + "-fx-text-fill: black;" + "-fx-border-radius: 3,3,3,3;"
			+ "-fx-border-color: rgba(230, 230, 230 ,0.99);" + "-fx-border-width: 1px;" + "-fx-background-insets: 1px;";
	static final String boardTilesStyle = "-fx-background-color: rgba(200,200,200,0.9);"
			+ "-fx-background-radius: 3,3,3,3;" + "-fx-font-size: 25px;" + "-fx-font-family: \"Times New Roman\";"
			+ "-fx-font-weight: bold;" + "-fx-text-fill: black;" + "-fx-border-radius: 3 3 3 3;"
			+ "-fx-border-width: 1.5;" + "-fx-background-insets: 1px;";
	static final String highlightedStyle = "-fx-background-color: rgba(173,199,212,0.9);"
			+ "-fx-background-radius: 3,3,3,3;" + "-fx-font-size: 25px;" + "-fx-font-family: \"Times New Roman\";"
			+ "-fx-font-weight: bold;" + "-fx-text-fill: black;" + "-fx-border-radius: 3 3 3 3;"
			+ "-fx-border-color: rgba(174,240,255,0.99);" + "-fx-border-width: 1;" + "-fx-background-insets: 1px;";
	static final String labelsStyle = "-fx-background-color: rgba(255,255,255,0.5);"
			+ "-fx-font-family: \"Times New Roman\";" + "-fx-font-size: 20px;" + "-fx-padding: 3;"
			+ "-fx-border-radius: 3 3 3 3;" + "-fx-background-radius: 3 3 3 3;"
			+ "-fx-border-color: rgba(230, 230, 230,0.9);" + "-fx-border-width: 1;";
	static final String comboboxStyle = "-fx-background-color: rgba(220, 220, 220, 0.7);"
			+ "-fx-font-family: \"Times New Roman\";" + "-fx-font-size: 20px;" + "-fx-padding: 3;"
			+ "-fx-border-radius: 3 3 3 3;" + "-fx-background-radius: 3 3 3 3;"
			+ "-fx-border-color: rgba(230, 230, 230, 0.9);" + "-fx-border-width: 1;";

	/**
	 * The arrays below store the options that will be put in the combo boxes
	 */

	static final String[] colorOptions = { "Red", "Blue", "Green", "Orange", "Yellow", "Purple", "Brown", "Pink" };
	static final String[] pointOptions = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	/**
	 * Method run when program is launched
	 * 
	 * @param the primary stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		/**
		 * First make an instance of SudokuModel to keep track of the back-end And make
		 * an instance of AI player to take the place of player 2
		 */
		myModel = new SudokuModel(9);
		myModel.addPropertyChangeListener(this);
		player = new SudokuPlayer(myModel.getCurrentBoard(), myModel.getPoints());

		/**
		 * Load background images for all three screens - The home screen, the game
		 * screen, and the settings screen Please make sure all three images are in the
		 * correct location or change the path to find them In Eclipse, place the images
		 * in the src folder, not the package folder
		 */
		homeScreenBackground = new BackgroundImage(new Image("HomeScreenBackground.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(1.0, 1.0, true, true, false, false));

		gameBackground = new BackgroundImage(new Image("GameScreenBackground.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(1.0, 1.0, true, true, false, false));

		settingsBackground = new BackgroundImage(new Image("Settingsbackground.jpg"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(1.0, 1.0, true, true, false, false));

		/**
		 * Initialize players colors and highlights
		 */
		player1Color = "Red";
		player2Color = "Blue";

		/**
		 * Start with the home screen
		 */
		new HomeScreenStage();
	}

	/**
	 * The Stage for the home screen of the game
	 * 
	 * @author kalinjohnson
	 *
	 */

	class HomeScreenStage extends Stage {

		/**
		 * The HomeScreen Constructor
		 */
		HomeScreenStage() {
			try {
				/**
				 * Setup the gridpane and scene
				 */
				GridPane root = new GridPane();
				Scene scene = new Scene(root, 650, 800);

				/**
				 * Set up buttons and labels
				 */

				play = new Button("PLAY");
				play.setOnAction(e -> {
					new GameStage();
				});

				settings = new Button("Settings");
				settings.setOnAction(e -> {
					new SettingsStage();
				});

				funFacts = new Label(myModel.getFunFact());
				funFacts.setWrapText(true);

				/**
				 * Add buttons and labels to main gridpane
				 */
				root.add(play, 3, 1, 4, 1);
				root.add(settings, 3, 2);
				root.add(funFacts, 3, 3, 4, 5);

				/**
				 * Make things look semi decent with backgrounds and styles from css
				 */
				root.setBackground(new Background(homeScreenBackground));
				funFacts.setStyle(
						"-fx-font-weight: normal;" + "-fx-font-size:22;" + "-fx-font-family: \"Times New Roman\";");
				play.setStyle(buttonStyle);
				settings.setStyle(buttonStyle);

				/**
				 * Set the column and row widths so things are where they are supposed to be
				 */
				int[] columnWidths = { 15, 30, 5, 42, 10 };
				for (int i = 0; i < 5; i++) {
					ColumnConstraints column = new ColumnConstraints();
					column.setPercentWidth(columnWidths[i]);
					column.fillWidthProperty();
					root.getColumnConstraints().add(column);
				}

				int rowLength = 100 / 7;
				for (int i = 0; i < 7; i++) {
					RowConstraints row = new RowConstraints();
					row.setPercentHeight(rowLength);
					row.fillHeightProperty();
					root.getRowConstraints().add(row);
				}

				/**
				 * Set the gaps and padding around everything
				 */
				root.setHgap(10); // horizontal gap in pixels
				root.setVgap(10); // vertical gap in pixels
				root.setPadding(new Insets(10, 10, 10, 10));

				/**
				 * Final setup steps
				 */
				this.setScene(scene);
				this.setTitle("Sudoku");
				this.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The stage for playing the game
	 * 
	 * @author kalinjohnson
	 *
	 */

	class GameStage extends Stage {

		/**
		 * The Game Stage constructor
		 */

		GameStage() {
			try {
				/**
				 * The main Gridpane is root and it holds everything in the GUI Setup the main
				 * gridpane and the scene
				 */
				GridPane root = new GridPane();
				Scene scene = new Scene(root, 650, 800);

				/**
				 * Setup the small gridpane for the board and input values across the bottom
				 */
				board = new GridPane();
				inputValues = new GridPane();

				/**
				 * Initialize Variables
				 */
				int s = myModel.getSize();
				storedNumber = 0;

				/**
				 * If it is a new game, reset the border colors to gray
				 */
				if (!myModel.isGameInProgress()) {
					resetColors();
				}

				/**
				 * Set up buttons and labels As well as their lambdas
				 */
				feedback = new Label(myModel.getFeedback());

				newGame = new Button("New Game");
				newGame.setOnAction(e -> {
					resetColors();
					myModel.newGame();
					myModel.highlightCoordinates(1);
				});

				exit = new Button("Exit");
				exit.setOnAction(e -> {
					new HomeScreenStage();
				});

				/**
				 * Set up numbers used to input values Set up their lambdas and styles in the
				 * same for loop
				 */

				inputNumbers = new Button[s];
				for (int i = 0; i < s; i++) {
					int value = i + 1;
					inputNumbers[i] = new Button(Integer.toString(value));

					inputNumbers[i].setOnAction(e -> {
						storedNumber = value;
						myModel.highlightCoordinates(value);
					});

					inputValues.add(inputNumbers[i], i, 0);
					inputNumbers[i].setStyle(inputNumberStyle);
					inputNumbers[i].setMinSize(50, 50);

				}

				/**
				 * Set up the actual board This method is also used in between each turn
				 */

				updateBoard();

				currentHighlights = new int[1][2];
				currentHighlights[0][0] = 0;
				currentHighlights[0][1] = 0;
				myModel.highlightCoordinates(1);

				/**
				 * Add everything to the main grid pane
				 */

				root.add(board, 1, 1, 9, 9);
				root.add(inputValues, 1, 11, 9, 11);
				root.add(feedback, 1, 13, 7, 14);
				root.add(newGame, 7, 12, 8, 12);
				root.add(exit, 7, 14, 8, 14);

				/**
				 * Make things look nice with backgrounds, css styles, and alignments
				 */
				root.setBackground(new Background(gameBackground));

				feedback.setStyle(labelsStyle);
				feedback.setWrapText(true);

				newGame.setStyle(buttonGameScreenStyle);
				GridPane.setHalignment(newGame, HPos.RIGHT);
				GridPane.setValignment(newGame, VPos.CENTER);

				exit.setStyle(buttonGameScreenStyle);
				GridPane.setHalignment(exit, HPos.RIGHT);
				GridPane.setValignment(exit, VPos.CENTER);

				board.setStyle("-fx-padding: 1;" + "-fx-margin: 1;");
				inputValues.setStyle("-fx-padding: 1;" + "-fx-margin: 1;");

				/**
				 * Setup column and row widths and lengths so things are in the right spot
				 */

				int[] columnWidths = { 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 8 };
				for (int i = 0; i <= 10; i++) {
					ColumnConstraints column = new ColumnConstraints();
					column.setPercentWidth(columnWidths[i]);
					column.fillWidthProperty();
					root.getColumnConstraints().add(column);
				}

				int[] rowLength = { 3, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 5, 5, 5 };
				for (int i = 0; i < 14; i++) {
					RowConstraints row = new RowConstraints();
					row.setPercentHeight(rowLength[i]);
					row.fillHeightProperty();
					root.getRowConstraints().add(row);
				}

				/**
				 * Set the paddings and gaps for all the gridpanes
				 */

				root.setHgap(10); // horizontal gap in pixels
				root.setVgap(10); // vertical gap in pixels
				root.setPadding(new Insets(10, 10, 10, 10));
				board.setHgap(10); // horizontal gap in pixels
				board.setVgap(10); // vertical gap in pixels
				board.setPadding(new Insets(10, 10, 10, 10));
				inputValues.setHgap(10);
				inputValues.setVgap(10);
				inputValues.setPadding(new Insets(10, 10, 10, 10));

				/**
				 * Final setup steps
				 */
				this.setScene(scene);
				this.setTitle("Sudoku");
				this.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The third stage that can be used to change settings. Not implemented yet
	 * 
	 * @author kalinjohnson
	 *
	 */

	class SettingsStage extends Stage {

		/**
		 * The Settings Stage Constructor
		 */
		SettingsStage() {
			try {
				/**
				 * The main Gridpane is root and it holds everything in the GUI Set up root and
				 * scene
				 */
				GridPane root = new GridPane();
				Scene scene = new Scene(root, 650, 800);

				/**
				 * Set up the main labels
				 */

				SettingsTitle = new Label("Settings and Options");

				Highscore = new Label("Highscore: " + Integer.toString(myModel.getHighscore()));

				Label info = new Label("Points for completion may only be changed when a game is not in progress.");

				Label col = new Label("Points for Completing a Column");

				/**
				 * Set up combo boxes and their corresponding labels
				 */

				columnPoints = new ComboBox<String>();
				columnPoints.getItems().addAll(pointOptions);
				columnPoints.valueProperty().addListener((e -> {
					myModel.setColumnScore(Integer.parseInt(columnPoints.getValue()));
					setPromptComboBoxes();
				}));

				Label row = new Label("Points for Completing a Row");

				rowPoints = new ComboBox<String>();
				rowPoints.getItems().addAll(pointOptions);
				rowPoints.valueProperty().addListener((e -> {
					myModel.setRowScore(Integer.parseInt(rowPoints.getValue()));
					setPromptComboBoxes();
				}));

				Label squ = new Label("Points for Completing a Square");

				squarePoints = new ComboBox<String>();
				squarePoints.getItems().addAll(pointOptions);
				squarePoints.valueProperty().addListener((e -> {
					myModel.setSquareScore(Integer.parseInt(squarePoints.getValue()));
					setPromptComboBoxes();
				}));

				Label wrong = new Label("Points Deducted for a Wrong Input Value");

				wrongMovePoints = new ComboBox<String>();
				wrongMovePoints.getItems().addAll(pointOptions);
				wrongMovePoints.valueProperty().addListener((e -> {
					myModel.setWrongMoveScore(Integer.parseInt(wrongMovePoints.getValue()));
					setPromptComboBoxes();
				}));

				Label p1 = new Label("Player 1's Color");

				colorOptions1 = new ComboBox<String>();
				colorOptions1.getItems().addAll(colorOptions);
				colorOptions1.valueProperty().addListener((e -> {
					player1Color = colorOptions1.getValue();
					setPromptComboBoxes();
				}));

				Label p2 = new Label("Player 2's Color");

				colorOptions2 = new ComboBox<String>();
				colorOptions2.getItems().addAll(colorOptions);
				colorOptions2.valueProperty().addListener((e -> {
					player2Color = colorOptions2.getValue();
					setPromptComboBoxes();
				}));

				/**
				 * Set up exit button
				 */

				exitS = new Button("Exit");
				exitS.setOnAction(e -> {
					new HomeScreenStage();
				});

				/**
				 * Adding all the elements to the root gridpane
				 */
				root.add(SettingsTitle, 0, 1);
				root.add(Highscore, 0, 2);
				root.add(info, 0, 3);
				root.add(col, 0, 4);
				root.add(columnPoints, 0, 5);
				root.add(row, 0, 6);
				root.add(rowPoints, 0, 7);
				root.add(squ, 0, 8);
				root.add(squarePoints, 0, 9);
				root.add(wrong, 0, 10);
				root.add(wrongMovePoints, 0, 11);
				root.add(p1, 0, 12);
				root.add(colorOptions1, 0, 13);
				root.add(p2, 0, 14);
				root.add(colorOptions2, 0, 15);
				root.add(exitS, 0, 16);

				/**
				 * Sets what appears in the combo boxes
				 */
				setPromptComboBoxes();

				/**
				 * Make things look nice with css styles and background
				 */

				SettingsTitle.setStyle("-fx-background-color: rgba(255,255,255,0.5);"
						+ "-fx-font-family: \"Times New Roman\";" + "-fx-font-size: 30px;" + "-fx-padding: 3;"
						+ "-fx-border-radius: 3 3 3 3;" + "-fx-background-radius: 3 3 3 3;" + "-fx-font-weight: bold;");
				root.setBackground(new Background(settingsBackground));
				exitS.setStyle(buttonStyle);
				setStyleLabels(Highscore, info, col, row, squ, wrong, p1, p2);
				setStyleComboBoxes(columnPoints, rowPoints, squarePoints, wrongMovePoints, colorOptions1,
						colorOptions2);
				info.setWrapText(true);

				/**
				 * Setups column widths so things are in the right spots
				 */
				ColumnConstraints column = new ColumnConstraints();
				column.setPercentWidth(100);
				column.fillWidthProperty();
				root.getColumnConstraints().add(column);

				/**
				 * Set padding and gaps for gridpane
				 */
				root.setHgap(10); // horizontal gap in pixels
				root.setVgap(10); // vertical gap in pixels
				root.setPadding(new Insets(20, 20, 20, 20));

				/**
				 * Final setup steps
				 */
				this.setScene(scene);
				this.setTitle("Sudoku");
				this.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the same style for all inputed labels
	 * 
	 * @param labels
	 */
	private void setStyleLabels(Label... labels) {
		for (Label label : labels) {
			label.setStyle(labelsStyle);
		}
	}

	/**
	 * Sets the same style for all inputed combo boxes
	 * 
	 * @param combobox
	 */
	@SafeVarargs
	private void setStyleComboBoxes(ComboBox<String>... combobox) {
		for (ComboBox<String> cb : combobox) {
			cb.setStyle(comboboxStyle);
		}
	}

	/**
	 * Resets all the border colors to dark gray
	 */
	private void resetColors() {
		colors = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				colors[i][j] = "-fx-border-color: rgba(83, 81, 90, 1);";
			}
		}
	}

	/**
	 * Sets the values of what appears in the combo boxes before things are changed
	 * So the user can see the current values
	 */
	private void setPromptComboBoxes() {
		int[] points = myModel.getPoints();
		columnPoints.setPromptText(Integer.toString(points[0]));
		rowPoints.setPromptText(Integer.toString(points[1]));
		squarePoints.setPromptText(Integer.toString(points[2]));
		wrongMovePoints.setPromptText(Integer.toString(points[3]));
		colorOptions1.setPromptText(player1Color);
		colorOptions2.setPromptText(player2Color);
	}

	/**
	 * Highlights the buttons are the coordinates inputed with the same light blue
	 * style
	 * 
	 * @param coords
	 */

	private void highlightValues(int[][] coords) {
		if (coords[0][0] != currentHighlights[0][0] || coords[0][1] != currentHighlights[0][1]) {

			for (int i = 0; i < coords.length; i++) {

				if (coords[i][0] != -1 && coords[i][1] != -1) {

					gameBoard[coords[i][0]][coords[i][1]].setStyle(highlightedStyle);
				}
			}

			for (int i = 0; i < currentHighlights.length; i++) {

				if (currentHighlights[i][0] != -1 && currentHighlights[i][1] != -1) {

					gameBoard[currentHighlights[i][0]][currentHighlights[i][1]]
							.setStyle(boardTilesStyle + colors[currentHighlights[i][0]][currentHighlights[i][1]]);
				}
			}
			currentHighlights = coords;
		}
	}

	/**
	 * Updated the board to reflect current board in the model
	 */

	private void updateBoard() {
		int s = myModel.getSize();
		gameBoard = new Button[s][s];
		String[][] theBoard = myModel.getBoard();

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				Button button = new Button(theBoard[i][j]);
				int first = i;
				int second = j;

				button.setOnAction(e -> {
					myModel.insertOrNoteNumber(storedNumber, first, second);
					if (storedNumber == 1) {
						resetHighlights();
					}
				});

				gameBoard[first][second] = button;
				board.add(button, second, first);

				button.setStyle(boardTilesStyle + colors[first][second]);
				button.setMinSize(50, 50);
			}
		}
		// AI agent makes it's move in the property change method
	}

	/**
	 * The lines to have the AI player make its move and then give its move to the
	 * model
	 */
	private void AIplayer() {
		int[] move = player.makeAMove(myModel.getCurrentBoard());
		myModel.insertOrNoteNumber(move[0], move[1], move[2]);
	}

	private void resetHighlights() {
		currentHighlights = new int[1][2];
		myModel.highlightCoordinates(1);
	}

	/**
	 * Called whenever something is changed in the model
	 */

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		feedback.setText(myModel.getFeedback());

		/**
		 * Start by highlighting all the ones
		 */

		/**
		 * Reset everything if is a new game
		 */
		if (name.equals("new game")) {

			board.getChildren().clear();
			updateBoard();
			player.newGame(myModel.getCurrentBoard(), myModel.getPoints());

			resetHighlights();

		} else if (name.equals("insert number")) {
			/**
			 * Update things and have AI player go if it its turn if the inserted number was
			 * correct
			 */
			boolean inserted = (boolean) evt.getNewValue();

			if (inserted) {
				board.getChildren().clear();
				int[] coords = (int[]) evt.getOldValue();

				if (myModel.getTurn()) {
					colors[coords[0]][coords[1]] = "-fx-border-color: " + player2Color + ";";
				} else {
					colors[coords[0]][coords[1]] = "-fx-border-color: " + player1Color + ";";
				}

				updateBoard();

				if (!myModel.getTurn()) {
					AIplayer();
				} else {
					myModel.highlightCoordinates(1);
				}
			}
		} else if (name.equals("highlight")) {
			/**
			 * Highlight numbers if an input number is clicked
			 */
			int[][] coords = (int[][]) evt.getNewValue();
			highlightValues(coords);
		}
	}

	/**
	 * The main method for the Sudoku Game
	 * 
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
