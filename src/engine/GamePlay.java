package engine;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import javafx.scene.image.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;

import java.awt.Paint;
import java.lang.invoke.WrongMethodTypeException;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class GamePlay extends Application {

	private static GridPane root = new GridPane();
	private Image emptyCell = new Image("icons/emptyCell.png");
	private Image explorerImage = new Image("icons/explorerImage.png");
	private Image medicImage = new Image("icons/medicImage.png");
	private Image fighterImage = new Image("icons/fighterImage.png");
	private Image vaccineImage = new Image("icons/vaccineImage.png");
	private Image supplyImage = new Image("icons/supplyImage.png");
	private Image zombieImage = new Image("icons/zombieImage.png");
	private Image invisibleEmptyCell = new Image("icons/darkInvisibleEmptyCell.png");
	private Image texturedBar = new Image("icons/texturedBar.png");

	private Scene scene1 = new Scene(root, Color.BEIGE);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		initializeGrid();
		putEndTurnButton();
		Game.loadHeroes("src/test_heros.csv");
		Game.startGame(Game.availableHeroes.remove(0));
		updateMap();
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

	private void primaryStageInit(Stage primaryStage) {
		primaryStage.setTitle("Last of Us - Legacy");
		Image logo = new Image("icons/logo.png");
		primaryStage.getIcons().add(logo);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
	}

	private void updateMap() {
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15 && x < 15; y++) {

				if (Game.map[x][y] == null)
					return;

				ImageView emptyCellView = new ImageView(emptyCell);
				emptyCellView.setScaleX(0.7);
				emptyCellView.setScaleY(0.3);
				root.add(emptyCellView, y, 14 - x);

				if (Game.map[x][y].isVisible()) {
					if (Game.map[x][y] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
							if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {

								ImageView medicImageView = new ImageView(medicImage);
								medicImageView.setScaleX(0.09);
								medicImageView.setScaleY(0.09);
								root.add(medicImageView, y, 14 - x);

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {

								ImageView fighterImageView = new ImageView(fighterImage);
								fighterImageView.setScaleX(0.03);
								fighterImageView.setScaleY(0.03);
								root.add(fighterImageView, y, 14 - x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {

								ImageView explorerImageView = new ImageView(explorerImage);
								explorerImageView.setScaleX(0.03);
								explorerImageView.setScaleY(0.03);
								root.add(explorerImageView, y, 14 - x);
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {

							ImageView zombieImageView = new ImageView(zombieImage);
							zombieImageView.setScaleX(0.09);
							zombieImageView.setScaleY(0.09);
							root.add(zombieImageView, y, 14 - x);
						}
						updateBar(((CharacterCell) Game.map[x][y]).getCharacter());
					} else if (Game.map[x][y] instanceof CollectibleCell) {
						if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Vaccine) {

							ImageView vaccineImageView = new ImageView(vaccineImage);
							vaccineImageView.setScaleX(0.2);
							vaccineImageView.setScaleY(0.2);
							root.add(vaccineImageView, y, 14 - x);
						} else if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Supply) {

							ImageView supplyImageView = new ImageView(supplyImage);
							supplyImageView.setScaleX(0.1);
							supplyImageView.setScaleY(0.1);
							root.add(supplyImageView, y, 14 - x);
						}
					}

				} else if (!Game.map[x][y].isVisible()) {
					ImageView invisibleEmptyCellView = new ImageView(invisibleEmptyCell);
					invisibleEmptyCellView.setScaleX(0.7);
					invisibleEmptyCellView.setScaleY(0.3);
					root.add(invisibleEmptyCellView, y, 14 - x);
				}
			}
//			col.setFillWidth(true);
//			row.setFillHeight(true);
		}
	}

	private void updateBar(model.characters.Character chrctr) {
		ProgressBar progressBar = new ProgressBar(1);
		progressBar.setStyle("-fx-accent: green");
		progressBar.setBorder(Border.EMPTY);
		root.add(progressBar, 0, 15);
	}

	private void initializeGrid() {
//		root.setPadding(new Insets(2, 10, 10, 10));
//		root.setGridLinesVisible(true);
//		root.setHgap(30);
//		root.setVgap(30);
		for (int i = 0; i < 17; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100);
			row.setValignment(VPos.CENTER);
			root.getRowConstraints().add(row);

			if (i < 15) {
				ColumnConstraints col = new ColumnConstraints();
				col.setPercentWidth(100);
				col.setHalignment(HPos.CENTER);
				root.getColumnConstraints().add(col);
				ImageView texturedBarView = new ImageView(texturedBar);
//				texturedBarView.setScaleX(0.523);
//				texturedBarView.setScaleY(0.523);
//				texturedBarView.setTranslateY(-20);
				root.add(texturedBarView, i, 16);
			}
		}

	}

	private void controllerEndTurn() {
		Game.zombies.get(0).setCurrentHp(0);
		Game.zombies.get(0).onCharacterDeath();
		try {
			Game.endTurn();
		} catch (NotEnoughActionsException e) {
			// TODO Auto-generated catch block
		} catch (InvalidTargetException e) {
			// TODO Auto-generated catch block
		}
		Game.checkWin();
		Game.checkGameOver();
		this.updateMap();

	}

	private void putEndTurnButton() {
		Image image = new Image("icons/EndTurnButton.png");
		ImageView imageView = new ImageView(image);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
		imageView.setScaleZ(0.3);
		root.add(imageView, 14, 16);
		imageView.setTranslateY(-20);
		imageView.setTranslateX(-50);
		imageView.setOnMouseClicked(event -> controllerEndTurn());
	}

}
