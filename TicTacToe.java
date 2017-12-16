package javaFX;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToe extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private boolean Xturn = true;
	private boolean playable = true;
	private ArrayList<Combo> combos = new ArrayList<>();
	private Tile[][] board = new Tile[3][3];
	
	private Pane root = new Pane();
	
	public Parent createContent() {
		root.setPrefSize(595, 595);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Tile tile = new Tile();
				tile.setLayoutX(j * 200 - 5);
				tile.setLayoutY(i * 200 - 5);
				
				root.getChildren().add(tile);
				
				board[j][i] = tile;
			}
		}
		
		for (int y = 0; y < 3; y++) {
			combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
		}
		
		for (int x = 0; x < 3; x++) {
			combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
		}
		
		combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
		combos.add(new Combo(board[0][2], board[1][1], board[2][0]));
		
		return root;
	}
	
	private class Combo {
		private Tile[] tiles;
		public Combo (Tile... tiles) {
			this.tiles = tiles;
		}
	
		private boolean isComplete() {
			if (tiles[0].getValue().isEmpty()) 
			return false;
			else {
				if (tiles[0].getValue().equals(tiles[1].getValue()) 
				&& tiles[0].getValue().equals(tiles[2].getValue())) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	
	private Line line = new Line();
	private void playWinLine(Combo combo) {
		if (combo.tiles[0].getLayoutX() + 400 == combo.tiles[2].getLayoutX()
			&& combo.tiles[0].getLayoutY() + 400 == combo.tiles[2].getLayoutY()) {
			line.setStartX(combo.tiles[0].getLayoutX() + 50);
			line.setStartY(combo.tiles[0].getLayoutY() + 50);
			line.setEndX(combo.tiles[2].getLayoutX() + 150);
			line.setEndY(combo.tiles[2].getLayoutY() + 150);
		}
		else if (combo.tiles[0].getLayoutX() + 400 == combo.tiles[2].getLayoutX()
				&& combo.tiles[0].getLayoutY() - 400 == combo.tiles[2].getLayoutY()) {
			line.setStartX(combo.tiles[0].getLayoutX() + 50);
			line.setStartY(combo.tiles[0].getLayoutY() + 150);
			line.setEndX(combo.tiles[2].getLayoutX() + 150);
			line.setEndY(combo.tiles[2].getLayoutY() + 50);
		}
		else if (combo.tiles[0].getLayoutX() + 400 == combo.tiles[2].getLayoutX()
				&& combo.tiles[0].getLayoutY() == combo.tiles[2].getLayoutY()) {
			line.setStartX(combo.tiles[0].getLayoutX() + 50);
			line.setStartY(combo.tiles[0].getLayoutY() + 100);
			line.setEndX(combo.tiles[2].getLayoutX() + 150);
			line.setEndY(combo.tiles[2].getLayoutY() + 100);
		}
		else {
			line.setStartX(combo.tiles[0].getLayoutX() + 100);
			line.setStartY(combo.tiles[0].getLayoutY() + 50);
			line.setEndX(combo.tiles[2].getLayoutX() + 100);
			line.setEndY(combo.tiles[2].getLayoutY() + 150);
		}
		
		line.setStrokeWidth(13);
		root.getChildren().add(line);
	}
	
	private StackPane winScreen = new StackPane();
	private StackPane playAgain = new StackPane();
	
	private void playScreen() {
		playAgain.setPrefSize(595, 100);
		Text again = new Text("Right click to play again");
		again.setFont(Font.font("Arial Black", 30));
		again.setStroke(Color.WHITE);
		again.setStrokeWidth(1);
		playAgain.getChildren().add(again);
		playAgain.setLayoutY(85);
		onMouseClicked(playAgain);
	}
	
	private void winningText(Combo combo) {
		winScreen.getChildren().clear();
		winScreen.setPrefSize(595, 100);
		winScreen.setLayoutY(20);
		Text win = new Text(combo.tiles[0].getValue() + " wins!");
		win.setFont(Font.font("Arial Black", 70));
		if (combo.tiles[0].getValue().equals("X")) {
			win.setFill(Color.BLUE);
		}
		else {
			win.setFill(Color.GREEN);
		}
		win.setStroke(Color.BLACK);
		win.setStrokeWidth(1.5);
		winScreen.getChildren().add(win);
		onMouseClicked(winScreen);
		
		playScreen();
		
		root.getChildren().addAll(winScreen, playAgain);
	}
	
	private void onMouseClicked(StackPane stackPane) {
		stackPane.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j].text.setText("");
				}
			}
			root.getChildren().removeAll(winScreen, playAgain, line);
			Xturn = true;
			playable = true;	
			}
		});
	}
	
	private StackPane tieScreen;
	
	//Working on this
	private void tieText() {
		tieScreen.getChildren().clear();
		tieScreen.setPrefSize(595, 100);
		tieScreen.setLayoutY(20);
		Text tie = new Text("TIE");
		tie.setFont(Font.font("Arial Black", 70));
		tie.setStroke(Color.BLACK);
		tie.setStrokeWidth(1.5);
		tieScreen.getChildren().add(tie);
		onMouseClicked(tieScreen);
		
		playScreen();
		
		root.getChildren().addAll(tieScreen, playAgain);
	}
	
	private void checkState() {
		for (Combo combo : combos) {
			if (combo.isComplete()) {
				playable = false;
				playWinLine(combo);
				winningText(combo);
				break;
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.setTitle("Tic-Tac-Toe");
		primaryStage.show();
	}
	private class Tile extends StackPane {
		Text text = new Text();
		public Tile() {
			Rectangle rect = new Rectangle(200, 200);
			rect.setFill(null);
			rect.setStroke(Color.SADDLEBROWN);
			rect.setStrokeWidth(5);
			setAlignment(Pos.CENTER);
			getChildren().addAll(rect, text);
			text.setFont(Font.font("Arial Black", 100));
			setPrefSize(200, 200);
			
			setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && playable == true) {
					if (!text.getText().isEmpty()) {}
					else {
						if (Xturn) {
							drawX();
							Xturn = false;
						}
						else if (!Xturn) {
							drawO();
							Xturn = true;
						}
					}
				}
				
				else if (event.getButton() == MouseButton.SECONDARY) {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							board[i][j].text.setText("");
						}
					}
					root.getChildren().removeAll(winScreen, playAgain, line);
					Xturn = true;
					playable = true;
				}
				checkState();
			});
			
		}
		
		public void drawX() {
			text.setText("X");
			text.setFill(Color.BLUE);
		}
		
		public void drawO() {
			text.setText("O");
			text.setFill(Color.GREEN);
		}
		
		public String getValue() {
			return text.getText();
		}
	}
}
