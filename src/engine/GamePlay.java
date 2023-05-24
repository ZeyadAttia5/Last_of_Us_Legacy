package engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import model.characters.Character;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import javafx.util.Duration;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import model.world.CollectibleCell;
import javafx.scene.image.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.StrokeType;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.scene.text.TextAlignment;

import java.awt.Point;
import java.util.ArrayList;

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
	private Image fighterProfile = new Image("icons/fighterProfile.png");
	private Image explorerPrfile = new Image("icons/explorerProfile.png");
	private Image endTurnButtonImage = new Image("icons/endTurnButtonImage.png");
	private Image medicProfile = new Image("icons/medicProfile.png");
	private Image zombieProfile = new Image("icons/zombieProfile.png");
	private Image handCursorImage = new Image("icons/cursors/handCursor.png");
	private ImageCursor handCursor = new ImageCursor(handCursorImage);
	private ArrayList<Image> fighterSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> medicSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> explorerSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> vaccineImages = new ArrayList<Image>();

	private Scene scene1 = new Scene(root, Color.BEIGE);
	private static BorderPane endGameScene = new BorderPane();
	private Image logo = new Image("icons/logo.png");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		initializeGrid(primaryStage);
		putEndTurnButton(primaryStage);
		loadResources();
		Image image = new Image("icons/cursor.png");
		root.setCursor(new ImageCursor(image));
		Game.loadHeroes("src/test_heros.csv");
		Game.startGame(Game.availableHeroes.remove(0));
		updateMap(primaryStage);
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

	private void primaryStageInit(Stage primaryStage) {
		primaryStage.setTitle("Last of Us - Legacy");
		Image logo = new Image("icons/logo.png");
		primaryStage.getIcons().add(logo);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Alt + Enter"));
	}

	private void updateMap(Stage primaryStage) {
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15 && x < 15; y++) {

				if (Game.map[x][y] == null)
					return;
				int g = x;
				int h = y;
				ImageView emptyCellView = new ImageView(emptyCell);
				Image imaged = new Image("icons/arrowD.png");
				emptyCellView.setOnMouseEntered(e -> emptyCellView.setCursor(new ImageCursor(imaged)));
				emptyCellView.setScaleX(0.7);
				emptyCellView.setScaleY(0.3);
				root.add(emptyCellView, y, 14 - x);

				if (Game.map[x][y].isVisible()) {
					if (Game.map[x][y] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
							if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {
								ImageView medicImageView = new ImageView(medicImage);
								medicImageView.setOnMouseClicked(
										event -> cursor(((CharacterCell) Game.map[g][h]).getCharacter()));
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								medicImageView.setOnMouseEntered(e -> medicImageView.setCursor(handCursor));
								medicImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
								medicImageView.setScaleX(0.08);
								medicImageView.setScaleY(0.08);
								root.add(medicImageView, y, 14 - x);

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
								ImageView fighterImageView = new ImageView(fighterImage);
								fighterImageView.setOnMouseClicked(
										event -> cursor(((CharacterCell) Game.map[g][h]).getCharacter()));
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								fighterImageView.setOnMouseEntered(e -> fighterImageView.setCursor(handCursor));
								fighterImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
								fighterImageView.setScaleX(0.03);
								fighterImageView.setScaleY(0.03);
								root.add(fighterImageView, y, 14 - x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
								ImageView explorerImageView = new ImageView(explorerImage);
								explorerImageView.setOnMouseClicked(
										event -> cursor(((CharacterCell) Game.map[g][h]).getCharacter()));
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								explorerImageView.setOnMouseEntered(e -> explorerImageView.setCursor(handCursor));
								explorerImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
								explorerImageView.setScaleX(0.03);
								explorerImageView.setScaleY(0.03);
								root.add(explorerImageView, y, 14 - x);
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							ImageView zombieImageView = new ImageView(zombieImage);
							Image image = new Image("icons/swordImage.png");
							zombieImageView.setOnMouseClicked(
									event -> cursor(((CharacterCell) Game.map[g][h]).getCharacter()));
							model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
							chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
							zombieImageView.setOnMouseEntered(e -> zombieImageView.setCursor(handCursor));
							zombieImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
							zombieImageView.setOnMouseEntered(e -> zombieImageView.setCursor(new ImageCursor(image)));
							zombieImageView.setScaleX(0.09);
							zombieImageView.setScaleY(0.09);
							root.add(zombieImageView, y, 14 - x);
						}
					} else if (Game.map[x][y] instanceof CollectibleCell) {
						if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Vaccine) {
							ImageView vaccineImageView = new ImageView(vaccineImage);
							Image image = new Image("icons/Hand.png");
							vaccineImageView.setOnMouseEntered(e -> vaccineImageView.setCursor(new ImageCursor(image)));
							vaccineImageView.setScaleX(0.2);
							vaccineImageView.setScaleY(0.2);
							root.add(vaccineImageView, y, 14 - x);
						} else if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Supply) {
							ImageView supplyImageView = new ImageView(supplyImage);
							Image image = new Image("icons/Hand.png");
							supplyImageView.setOnMouseEntered(e -> supplyImageView.setCursor(new ImageCursor(image)));
							supplyImageView.setScaleX(0.1);
							supplyImageView.setScaleY(0.1);
							root.add(supplyImageView, y, 14 - x);
						}
					}

				} else if (!Game.map[x][y].isVisible()) {

					ImageView invisibleEmptyCellView = new ImageView(invisibleEmptyCell);
					invisibleEmptyCellView.setScaleX(0.58);
					invisibleEmptyCellView.setScaleY(0.292);
					root.add(invisibleEmptyCellView, y, 14 - x);
				}
			}
//			col.setFillWidth(true);
//			row.setFillHeight(true);
		}
	}

	private void initializeGrid(Stage primaryStage) {
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
		Game.zombies.get(0).setCurrentHp(0);
		Game.zombies.get(0).onCharacterDeath();
		try {
			Game.endTurn();
		} catch (NotEnoughActionsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game.checkWin();
		Game.checkGameOver();

		this.updateMap(primaryStage);

	}

	private void putEndTurnButton(Stage primaryStage) {
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

	private void move() {

	}

	private void cursor(Character character) {
		if (character instanceof Medic) {
			if (!((Character) character).isTargetAdjacent()) {
				check(character);
				Image image = new Image("icons/arrowD.png");
				root.setCursor(new ImageCursor(image));
			} else {
				Image image = new Image("icons/swordImage.png");
				root.setCursor(new ImageCursor(image));
			}
		}

	}

	private boolean check(Character character) {
		ArrayList<Point> x = ((model.characters.Character) character).getAdjacentIndices();
		ArrayList<Hero> h = Game.heroes;
		ArrayList<Zombie> z = Game.zombies;
//		ArrayList<Point> hx = new ArrayList<>();
		ArrayList<Point> zx = new ArrayList<>();
		int i;
//		for(i = 0; i<h.size();i++) {
//			hx.add((h.get(i)).getLocation());
//		}
		for (i = 0; i < z.size(); i++) {
			zx.add((h.get(i)).getLocation());
		}
		for (i = 0; i < x.size(); i++) {
			for (int j = 0; j < zx.size(); j++) {
				if ((x.get(i)).equals(zx.get(j)))
					return true;
			}
		}
		return false;
	}
}
