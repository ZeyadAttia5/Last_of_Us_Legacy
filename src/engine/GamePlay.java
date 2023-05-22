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
import engine.Game;

public class GamePlay extends Application {

	private static GridPane root = new GridPane();
	private boolean canHeroMove = false;
//	private double fullScreenHeight = 0;
//	private double fullScreenWidth = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		Scene scene1 = new Scene(root, Color.BEIGE);
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
//		fullScreenHeight = primaryStage.getX();
//		fullScreenWidth = primaryStage.getWidth();
		primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
	}

	private void updateMap() {
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15 && x < 15; y++) {
//				root.getChildren().remove(0);
//				Text text = new Text("Cell " + " " + x + " " + y);
//				root.add(text, y, x);

				if (Game.map[x][y] == null)
					return;
				Image emptyCell = new Image("icons/emptyCell.png");
				ImageView emptyCellView = new ImageView(emptyCell);
				emptyCellView.setScaleX(0.8);
				emptyCellView.setScaleY(0.295);
				root.add(emptyCellView, y, x);

				if (Game.map[x][y].isVisible()) {
					if (Game.map[x][y] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
							if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {
								Image medicImage = new Image("icons/medicImage.png");
								ImageView medicImageView = new ImageView(medicImage);
								medicImageView.setScaleX(0.1);
								medicImageView.setScaleY(0.1);
								root.add(medicImageView, y, x);

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
								Image fighterImage = new Image("icons/fighterImage.png");
								ImageView fighterImageView = new ImageView(fighterImage);
								fighterImageView.setScaleX(0.1);
								fighterImageView.setScaleY(0.1);
								root.add(fighterImageView, y, x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
								Image explorerImage = new Image("icons/explorerImage.png");
								ImageView explorerImageView = new ImageView(explorerImage);
								explorerImageView.setScaleX(0.1);
								explorerImageView.setScaleY(0.1);
								root.add(explorerImageView, y, x);
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							Image zombieImage = new Image("icons/zombieImage.png");
							ImageView zombieImageView = new ImageView(zombieImage);
							zombieImageView.setScaleX(0.1);
							zombieImageView.setScaleY(0.1);
							root.add(zombieImageView, y, x);
						}
					} else if (Game.map[x][y] instanceof CollectibleCell) {
						if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Vaccine) {
							Image vaccineImage = new Image("icons/vaccineImage.png");
							ImageView vaccineImageView = new ImageView(vaccineImage);
							vaccineImageView.setScaleX(0.2);
							vaccineImageView.setScaleY(0.2);
							root.add(vaccineImageView, y, x);
						} else if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Supply) {
							Image supplyImage = new Image("icons/supplyImage.png");
							ImageView supplyImageView = new ImageView(supplyImage);
							supplyImageView.setScaleX(0.1);
							supplyImageView.setScaleY(0.1);
							root.add(supplyImageView, y, x);
						}
					}

				} else if (!Game.map[x][y].isVisible()) {
					Image invisibleEmptyCell = new Image("icons/invisibleEmptyCell.png");
					ImageView invisibleEmptyCellView = new ImageView(invisibleEmptyCell);
					invisibleEmptyCellView.setScaleX(0.8);
					invisibleEmptyCellView.setScaleY(0.3);
					root.add(invisibleEmptyCellView, y, x);
				}
			}
//			col.setFillWidth(true);
//			row.setFillHeight(true);
		}
	}

	private void initializeGrid() {
//		root.setGridLinesVisible(true);
		root.setPadding(new Insets(2, 10, 10, 10));
		root.setGridLinesVisible(true);
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
			}
		}
	}

	private void controllerEndTurn() {
		// remove control form hero
		canHeroMove = false;
		Game.zombies.get(0).setCurrentHp(0);
		Game.zombies.get(0).onCharacterDeath();
		Game.endTurn();
		Game.checkWin();
		Game.checkGameOver();

		this.updateMap();
		// set control form hero
		canHeroMove = true;

	}

	private void putEndTurnButton() {
		Image image = new Image("icons/EndTurnButton.png");
		ImageView imageView = new ImageView(image);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
		imageView.setScaleZ(0.3);
		root.add(imageView, 14, 16);
		imageView.setTranslateY(-20);
		imageView.setTranslateX(-30);
		imageView.setOnMouseClicked(event -> controllerEndTurn());
	}
}
