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
import javafx.scene.layout.BorderPane;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.text.TextAlignment;

import java.awt.Paint;
import java.io.File;
import java.lang.invoke.WrongMethodTypeException;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class GamePlay extends Application {

	private static GridPane root = new GridPane();
	private static BorderPane endGameScene = new BorderPane();
	Image logo = new Image("icons/logo.png");
	private Image emptyCell = new Image("icons/emptyCell.png");
	private Image explorerImage = new Image("icons/explorerImage.png");
	private Image medicImage = new Image("icons/medicImage.png");
	private Image fighterImage = new Image("icons/fighterImage.png");
	private Image vaccineImage = new Image("icons/vaccineImage.png");
	private Image supplyImage = new Image("icons/supplyImage.png");
	private Image zombieImage = new Image("icons/zombieImage.png");
	private Image invisibleEmptyCell = new Image("icons/darkInvisibleEmptyCell.png");
	private Image texturedBar = new Image("icons/texturedBar.png");
	private Image fighterProfile = new Image("icons/fighterProfile.png");
	private Image explorerPrfile = new Image ("icons/explorerProfile.png");
	private Image endTurnButtonImage = new Image("icons/endTurnButtonImage.png");

	private Font mineCraftFont = new Font("fonts/Minecraftia-Regular.ttf", 12);

	private Scene scene1 = new Scene(root, Color.BEIGE);
	private Scene scene2 = new Scene(endGameScene, Color.BISQUE);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		initializeGrid();
		putEndTurnButton(primaryStage);
		Game.loadHeroes("src/test_heros.csv");
		Game.startGame(Game.availableHeroes.remove(0));
		updateMap();
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

	private void primaryStageInit(Stage primaryStage) {
		primaryStage.setTitle("Last of Us - Legacy");

		primaryStage.getIcons().add(logo);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Alt + Enter"));
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
								medicImageView.setScaleX(0.08);
								medicImageView.setScaleY(0.08);
								root.add(medicImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								medicImageView.setOnMouseClicked(e -> updateBar(chrctr));

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {

								ImageView fighterImageView = new ImageView(fighterImage);
								fighterImageView.setScaleX(0.09);
								fighterImageView.setScaleY(0.09);
								root.add(fighterImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								fighterImageView.setOnMouseClicked(e -> updateBar(chrctr));
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {

								ImageView explorerImageView = new ImageView(explorerImage);
								explorerImageView.setScaleX(0.06);
								explorerImageView.setScaleY(0.06);
								root.add(explorerImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								explorerImageView.setOnMouseClicked(e -> updateBar(chrctr));
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							ImageView zombieImageView = new ImageView(zombieImage);
							zombieImageView.setScaleX(0.08);
							zombieImageView.setScaleY(0.08);
							root.add(zombieImageView, y, 14 - x);
							model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
							zombieImageView.setOnMouseClicked(e -> updateBar(chrctr));
						}

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
		}
	}

	private void updateTexturedWall() {
		for (int i = 15; i < 18; i++) {
//			RowConstraints row = root.getRowConstraints().get(18-i);
//			row.setPercentHeight(100);
//			row.setValignment(VPos.TOP);
//			root.getRowConstraints().add(row);
			for (int j = 0; j < root.getColumnCount()-3; j++) {
				ImageView texturedBarView = new ImageView(texturedBar);
				texturedBarView.setScaleX(0.29);
				texturedBarView.setScaleY(0.17);

				root.add(texturedBarView, j, i);
			}
		}
	}

	private void updateBar(model.characters.Character chrctr) {

//		root.getRowConstraints().get(15).setVgrow(Priority.ALWAYS);

//		for (int i = 0; i < root.getColumnCount(); i++) {
//		ImageView oldNameCover = new ImageView(texturedBar);
//		ImageView oldBarCover = new ImageView(texturedBar);
//		oldNameCover.setScaleX(0.3);
//		oldNameCover.setScaleY(0.3);
//		oldBarCover.setScaleX(0.3);
//		oldBarCover.setScaleY(0.19);
//		root.getRowConstraints().get(15).setValignment(VPos.TOP);
//		root.getRowConstraints().get(17).setValignment(VPos.BOTTOM);
//		root.add(oldNameCover, 0, 15);
//		root.add(oldBarCover, 0, 17);
//		}
		updateTexturedWall();
		Text name = new Text(chrctr.getName());
		name.setFont(mineCraftFont);
		name.setEffect(new DropShadow(5, Color.ANTIQUEWHITE));
		name.setScaleX(1.1);
		name.setScaleY(1.1);
		name.setStroke(Color.WHITESMOKE);
		root.add(name, 0, 15);
		if (chrctr instanceof model.characters.Fighter) {
			ImageView fighterProfileView = new ImageView(fighterProfile);
			fighterProfileView.setScaleX(0.2);
			fighterProfileView.setScaleY(0.2);
			root.add(fighterProfileView, 0, 16);
		}
		if (chrctr instanceof model.characters.Explorer) {
			ImageView fighterProfileView = new ImageView(explorerPrfile);
			fighterProfileView.setScaleX(0.2);
			fighterProfileView.setScaleY(0.2);
			root.add(fighterProfileView, 0, 16);
		}
		if (chrctr instanceof model.characters.Medic) {
			ImageView fighterProfileView = new ImageView(fighterProfile);
			fighterProfileView.setScaleX(0.2);
			fighterProfileView.setScaleY(0.2);
			root.add(fighterProfileView, 0, 16);
		}
		if (chrctr instanceof model.characters.Zombie) {
			ImageView fighterProfileView = new ImageView(fighterProfile);
			fighterProfileView.setScaleX(0.2);
			fighterProfileView.setScaleY(0.2);
			root.add(fighterProfileView, 0, 16);
		}
		ProgressBar progressBar = new ProgressBar((double) chrctr.getCurrentHp() / (double) chrctr.getMaxHp());
		progressBar.setStyle("-fx-accent: blue");
		progressBar.setBorder(Border.EMPTY);
		progressBar.setPadding(new Insets(15,0,0,8));
//		root.getRowConstraints().get(17).setValignment(VPos.BOTTOM);
		root.add(progressBar, 0, 17);

	}

	private void initializeGrid() {
		root.setGridLinesVisible(true);
		for (int i = 0; i < 18; i++) {
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
			if (i > 14) {
				for (int j = 0; j < root.getColumnCount(); j++) {
					ImageView texturedBarView = new ImageView(texturedBar);
					texturedBarView.setScaleX(0.3);
					texturedBarView.setScaleY(0.21);
					root.add(texturedBarView, j, i);
				}
			}
		}

	}


	private void controllerEndTurn(Stage primaryStage) {
		Game.zombies.get(0).setCurrentHp(0);
		Game.zombies.get(0).onCharacterDeath();
		try {
			Game.endTurn();
		} catch (NotEnoughActionsException e) {
		} catch (InvalidTargetException e) {
		}
		if (Game.checkWin()) {
			Text txt = new Text("You Won!");
			txt.setFont(mineCraftFont);
			txt.setFill(Color.BLACK);
			txt.setScaleX(2);
			txt.setScaleY(2);
			endGameScene.getChildren().add(txt);
			primaryStage.setScene(scene2);
			primaryStage.show();
		}
		if (Game.checkGameOver()) {
			Text txt = new Text("Game Over!");
			endGameScene.getChildren().add(txt);
			txt.setFill(Color.BLACK);
			txt.setScaleX(2);
			txt.setScaleY(2);
			primaryStage.setScene(scene2);
			primaryStage.show();
		}
		this.updateMap();

	}

	private void putEndTurnButton(Stage primaryStage) {

		ImageView imageView = new ImageView(endTurnButtonImage);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
//		imageView.setScaleZ(0.3);
		root.add(imageView, 14, 16);
		imageView.setTranslateY(-20);
		imageView.setTranslateX(-50);
		imageView.setOnMouseClicked(event -> controllerEndTurn(primaryStage));
	}

}
