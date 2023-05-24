package engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import model.characters.Character;
import model.characters.Direction;
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
import javafx.scene.input.MouseEvent;
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

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.MovementException;
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
	private Character selected;
	private Image imaged = new Image("icons/move.png");
	private ImageView selectedImage;
	private ImageView emptyCellView = new ImageView(emptyCell);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		Scene scene1 = new Scene(root, Color.BEIGE);
		initializeGrid();
		putEndTurnButton();
		Image image = new Image("icons/cursor.png");
		root.setCursor(new ImageCursor(image));
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

				emptyCellView.setScaleX(0.58);
				emptyCellView.setScaleY(0.292);

				ImageView emptyCellView = new ImageView(emptyCell);
				emptyCellView.setScaleX(0.7);
				emptyCellView.setScaleY(0.3);

				root.add(emptyCellView, y, 14 - x);
				root.getColumnConstraints().get(x);

				if (Game.map[x][y].isVisible()) {
					if (Game.map[x][y] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
							int g = x;
							int h = y;
							if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {
								ImageView medicImageView = new ImageView(medicImage);
								medicImageView.setOnMouseClicked(
										e -> select(medicImageView, ((CharacterCell) Game.map[g][h]).getCharacter()));
								medicImageView.setScaleX(0.09);
								medicImageView.setScaleY(0.09);
								root.add(medicImageView, y, 14 - x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
								ImageView fighterImageView = new ImageView(fighterImage);
								fighterImageView.setOnMouseClicked(
										e -> select(fighterImageView, ((CharacterCell) Game.map[g][h]).getCharacter()));
								fighterImageView.setScaleX(0.03);
								fighterImageView.setScaleY(0.03);
								root.add(fighterImageView, y, 14 - x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
								ImageView explorerImageView = new ImageView(explorerImage);
								explorerImageView.setOnMouseClicked(e -> select(explorerImageView,
										((CharacterCell) Game.map[g][h]).getCharacter()));
								explorerImageView.setScaleX(0.03);
								explorerImageView.setScaleY(0.03);
								root.add(explorerImageView, y, 14 - x);
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							ImageView zombieImageView = new ImageView(zombieImage);
							Image image = new Image("icons/swordImage.png");
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
					Image image = new Image("icons/cross.png");
					ImageView invisibleEmptyCellView = new ImageView(invisibleEmptyCell);
					invisibleEmptyCellView
							.setOnMouseEntered(e -> invisibleEmptyCellView.setCursor(new ImageCursor(image)));
					invisibleEmptyCellView.setScaleX(0.7);
					invisibleEmptyCellView.setScaleY(0.3);
					root.add(invisibleEmptyCellView, y, 14 - x);
				}
			}
		}
	}

	private void initializeGrid() {
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
		Game.endTurn();
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
		imageView.setTranslateX(-30);
		imageView.setOnMouseClicked(event -> controllerEndTurn());
	}

	private void moveGUI(Direction x) {
		if (selected instanceof Hero) {
			try {
				((Hero) selected).move(x);
			} catch (MovementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotEnoughActionsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (x == Direction.UP)

		{
			root.add(selectedImage, selected.getLocation().y, (14 - selected.getLocation().x + 1));
			root.add(emptyCellView, selected.getLocation().y, (14 - selected.getLocation().x));
		} else if (x == Direction.DOWN) {
			root.add(selectedImage, selected.getLocation().y, (14 - selected.getLocation().x - 1));
			root.add(emptyCellView, selected.getLocation().y, (14 - selected.getLocation().x));
		} else if (x == Direction.RIGHT) {
			root.add(selectedImage, selected.getLocation().y + 1, (14 - selected.getLocation().x));
			root.add(emptyCellView, selected.getLocation().y, (14 - selected.getLocation().x));
		} else if (x == Direction.LEFT) {
			root.add(selectedImage, selected.getLocation().y - 1, (14 - selected.getLocation().x));
			root.add(emptyCellView, selected.getLocation().y, (14 - selected.getLocation().x));
		}

	}

	private void select(ImageView v, Character character) {
		selected = character;
		root.setCursor(new ImageCursor(imaged));
		selectedImage = v;
	}
}
