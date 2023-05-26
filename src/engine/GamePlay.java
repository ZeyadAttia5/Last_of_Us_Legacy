package engine;

import java.io.IOException;
import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import views.ImageLoader;
import javafx.stage.Popup;

public class GamePlay extends Application {

	private static GridPane root = new GridPane();
	private static BorderPane endGameScene = new BorderPane();

//	private ImageLoader imageLoader = ImageLoader.loadImageLoader();ِ


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
	private Image availableActionsText = new Image("icons/ActionsAvialable.png");
	private Image attackModeImage = new Image("icons/AttackMode.png");
	private Image cureModeImage = new Image("icons/CureMode.png");
	private Image ZombieAttackImg = new Image("endTurnResources/ZombieAttackImg.jpg");
	private Image CureModeHighlighted = new Image("icons/CureModeHighlighted.png");
	private Image AttackModeHighlighted = new Image("icons/AttackModeHighlighted.png");
	private Image zombieHighlighted = new Image("icons/zombieHighlighted.png");
	private Image UseSpecialFighterHighlighted = new Image("icons/UseSpecialFighterHighlighted.png");
	private Image UseSpecialMedicHighlighted = new Image("icons/UseSpecialMedicHighlighted.png");
	private Image UseSpecialExplorerHighlighted = new Image("icons/UseSpecialExplorerHighlighted.png");
	private Image GunCursorImage = new Image("icons/cursors/GunCursor.png");
	private Image VaccineImage = new Image("icons/cursors/syringeImg.png");
	private boolean AttackMode = false;
	private boolean CureMode = false;

	private ImageCursor handCursor = new ImageCursor(handCursorImage);
	private ImageCursor GunCursor = new ImageCursor(GunCursorImage);
	private ImageCursor CureCursor = new ImageCursor(VaccineImage);
//	private Character selected = null;
	private Hero selected;
	private Zombie selectedZombie;
	private ImageView selectedZombieImage;
	private ImageView selectedImage;
	private ImageView emptyCellView = new ImageView(emptyCell);
	private ArrayList<Image> fighterSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> medicSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> explorerSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> vaccineImages = new ArrayList<Image>();
	private ArrayList<Image> useSpecialImages = new ArrayList<Image>();
	private Scene scene1 = new Scene(root, Color.BEIGE);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		initializeGrid(primaryStage);
		putEndTurnButton(primaryStage);
		loadResources();
		Game.startGame(Game.availableHeroes.remove(0));
		updateMap(primaryStage);
//		moveHelper();
		primaryStage.setScene(scene1);
		primaryStage.show();
	}

	private void primaryStageInit(Stage primaryStage) {
		primaryStage.setTitle("Last of Us - Legacy");

		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Alt + Enter"));
	}

	private void updateMap(Stage primaryStage) {
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
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								medicImageView.setOnMouseClicked(e -> select(medicImageView, h));
								medicImageView.setScaleX(0.08);
								medicImageView.setScaleY(0.08);
								root.add(medicImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								medicImageView.setOnMouseEntered(e -> medicImageView.setCursor(handCursor));
								medicImageView.setOnMouseClicked(e -> {
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
								});

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
								ImageView fighterImageView = new ImageView(fighterImage);
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								fighterImageView.setOnMouseClicked(e -> select(fighterImageView, h));
								fighterImageView.setScaleX(0.09);
								fighterImageView.setScaleY(0.09);
								root.add(fighterImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								fighterImageView.setOnMouseEntered(e -> fighterImageView.setCursor(handCursor));
								fighterImageView.setOnMouseClicked(e -> {
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
								});

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
								ImageView explorerImageView = new ImageView(explorerImage);
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								explorerImageView.setOnMouseClicked(e -> select(explorerImageView, h));
								explorerImageView.setScaleX(0.06);
								explorerImageView.setScaleY(0.06);
								root.add(explorerImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								// TODO delete TEST
								explorerImageView.setOnMouseClicked(e -> {
									root.requestFocus();
									select(explorerImageView, h);
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
								});
								explorerImageView.setOnMouseEntered(e -> explorerImageView.setCursor(handCursor));
//								explorerImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
							}
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							ImageView zombieImageView = new ImageView(zombieImage);
							Zombie h = (Zombie) ((CharacterCell) Game.map[x][y]).getCharacter();
							if (AttackMode) {
								zombieImageView.setOnMouseClicked(e -> selectZombie(zombieImageView, h));
							}
							if (CureMode) {
								zombieImageView.setOnMouseClicked(e -> selectZombie(zombieImageView, h));
							}
							zombieImageView.setScaleX(0.08);
							zombieImageView.setScaleY(0.08);
							Image image = new Image("icons/swordImage.png");
							zombieImageView.setOnMouseEntered(e -> zombieImageView.setCursor(new ImageCursor(image)));
							root.add(zombieImageView, y, 14 - x);
							model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
							zombieImageView.setOnMouseEntered(e -> zombieImageView.setCursor(handCursor));
							zombieImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
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
					Image image = new Image("icons/cross.png");
					invisibleEmptyCellView
							.setOnMouseEntered(e -> invisibleEmptyCellView.setCursor(new ImageCursor(image)));
					invisibleEmptyCellView.setScaleX(0.7);
					invisibleEmptyCellView.setScaleY(0.3);
					root.add(invisibleEmptyCellView, y, 14 - x);
				}
			}
		}
	}

	private void updateTexturedWall(Stage primaryStage) {
		for (int i = 15; i < root.getRowCount(); i++) {
			for (int j = 0; j < root.getColumnCount(); j++) {
				ImageView texturedBarView = new ImageView(texturedBar);
				root.add(texturedBarView, j, i);
			}
		}
		updateMap(primaryStage);
		putEndTurnButton(primaryStage);
	}

	private void updateBar(model.characters.Character chrctr, Stage primaryStage) {
		if (chrctr instanceof Zombie && AttackMode) {
			attackUI(primaryStage);
		}
		if (chrctr instanceof Zombie && CureMode) {
			cureUI(primaryStage);
		}
		root.getChildren().clear();
		updateTexturedWall(primaryStage);
		Text name = new Text(chrctr.getName());
		name.setFont(Font.font("Monospaced", 14));
		name.setFill(Color.WHITE);
		name.setStroke(Color.WHITE);
		root.add(name, 0, 15);
		if (chrctr instanceof Hero) {

			Text vaccineText = new Text("Vaccines");
			vaccineText.setFont(Font.font("Monospaced", 14));
			vaccineText.setFill(Color.WHITE);
			vaccineText.setStroke(Color.WHITE);
			vaccineText.setTranslateX(-10);
			vaccineText.setTranslateY(-10);
			root.add(vaccineText, 2, 16);

			Text supplyText = new Text("Supplies");
			supplyText.setFont(Font.font("Monospaced", 14));
			supplyText.setFill(Color.WHITE);
			supplyText.setStroke(Color.WHITE);
			supplyText.setTranslateX(-10);
			supplyText.setTranslateY(-8);
			root.add(supplyText, 2, 17);

			ImageView actionsAvailableView = new ImageView(availableActionsText);
			actionsAvailableView.setScaleX(0.27);
			actionsAvailableView.setScaleY(0.27);
			root.add(actionsAvailableView, 5, 16);

			Text actionsAvailable = new Text(((Hero) chrctr).getActionsAvailable() + "");
			actionsAvailable.setFont(Font.font("Monospaced", 20));
			actionsAvailable.setFill(Color.WHITE);
			actionsAvailable.setStroke(Color.WHITE);
			actionsAvailable.setTranslateX(5);
			actionsAvailable.setTranslateY(4.8);
			root.add(actionsAvailable, 6, 16);

			ImageView attackImageView = new ImageView(attackModeImage);
			// attackImageView.setOnMouseClicked(e -> attackUI(primaryStage,
			// attackImageView));
			attackImageView.setOnMouseClicked(e -> {
				if (!AttackMode) {
					AttackMode = true;
					root.setCursor(GunCursor);
				} else {
					AttackMode = false;
					root.setCursor(Cursor.DEFAULT);
				}
			});
			attackImageView.setScaleX(0.4);
			attackImageView.setScaleY(0.4);
//			attackImageView.setTranslateX(-20);

			ImageView cureImageView = new ImageView(cureModeImage);
			cureImageView.setOnMouseClicked(e -> {
				if (!CureMode) {
					CureMode = true;
					root.setCursor(CureCursor);
				} else {
					CureMode = false;
					root.setCursor(Cursor.DEFAULT);
				}
			});
			cureImageView.setScaleX(0.4);
			cureImageView.setScaleY(0.4);
//			cureImageView.setTranslateX(20);

			root.add(cureImageView, 7, 16);
			root.add(attackImageView, 10, 16);

			int suppliesNum = ((Hero) chrctr).getSupplyInventory().size();

			if (chrctr instanceof model.characters.Fighter) {
				ImageView fighterProfileView = new ImageView(fighterProfile);
				fighterProfileView.setScaleX(0.2);
				fighterProfileView.setScaleY(0.2);
				root.add(fighterProfileView, 0, 16);
				select(new ImageView(fighterImage), (Hero) chrctr);

				ImageView supplyImageView = new ImageView(fighterSupplyImages.get(0));

				if (suppliesNum <= 5 && suppliesNum > 0) {
					supplyImageView = new ImageView(fighterSupplyImages.get(suppliesNum));
				} else if (suppliesNum > 5) {
					supplyImageView = new ImageView(fighterSupplyImages.get(5));
				}
				supplyImageView.setScaleX(0.25);
				supplyImageView.setScaleY(0.25);
				supplyImageView.setTranslateY(-15);
				root.add(supplyImageView, 3, 17);

				ImageView useSpecialView = new ImageView(useSpecialImages.get(1));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				root.add(useSpecialView, 9, 16);
				useSpecialView.setOnMouseClicked(e -> {
					useSpecialAction(chrctr, primaryStage, useSpecialView);
				});
			}
			if (chrctr instanceof model.characters.Explorer) {
				ImageView fighterProfileView = new ImageView(explorerPrfile);
				fighterProfileView.setScaleX(0.2);
				fighterProfileView.setScaleY(0.2);
				root.add(fighterProfileView, 0, 16);
				select(new ImageView(explorerImage), (Hero) chrctr);

				ImageView supplyImageView = new ImageView(explorerSupplyImages.get(0));
				if (suppliesNum <= 5 && suppliesNum > 0) {
					supplyImageView = new ImageView(explorerSupplyImages.get(suppliesNum));
				} else if (suppliesNum > 5) {
					supplyImageView = new ImageView(explorerSupplyImages.get(5));
				}
				supplyImageView.setScaleX(0.3);
				supplyImageView.setScaleY(0.3);
				supplyImageView.setTranslateY(-20);
				root.add(supplyImageView, 3, 17);

				// TODO editing
				ImageView useSpecialView = new ImageView(useSpecialImages.get(0));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				root.add(useSpecialView, 9, 16);
				useSpecialView.setOnMouseClicked(e -> {
					useSpecialAction(chrctr, primaryStage, useSpecialView);
				});
			}
			if (chrctr instanceof model.characters.Medic) {
				ImageView fighterProfileView = new ImageView(medicProfile);
				fighterProfileView.setScaleX(0.2);
				fighterProfileView.setScaleY(0.2);
				root.add(fighterProfileView, 0, 16);
				select(new ImageView(medicImage), (Hero) chrctr);

				ImageView supplyImageView = new ImageView(medicSupplyImages.get(0));

				if (suppliesNum <= 5 && suppliesNum > 0) {
					supplyImageView = new ImageView(medicSupplyImages.get(suppliesNum));
				} else if (suppliesNum > 5) {
					supplyImageView = new ImageView(medicSupplyImages.get(5));
				}
				supplyImageView.setScaleX(0.25);
				supplyImageView.setScaleY(0.25);
				supplyImageView.setTranslateY(-15);
				root.add(supplyImageView, 3, 17);

				ImageView useSpecialView = new ImageView(useSpecialImages.get(2));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				root.add(useSpecialView, 9, 16);
			}
		}
		if (chrctr instanceof model.characters.Zombie) {
			name.setStroke(Color.ORANGERED);
			ImageView zombieProfileView = new ImageView(zombieProfile);
			selectZombie(zombieProfileView, (Zombie) chrctr);
			zombieProfileView.setScaleX(0.2);
			zombieProfileView.setScaleY(0.2);
			root.add(zombieProfileView, 0, 16);
		}
		ProgressBar progressBar = new ProgressBar((double) chrctr.getCurrentHp() / (double) chrctr.getMaxHp());
		progressBar.setStyle("-fx-accent: blue");
		progressBar.setBorder(Border.EMPTY);
		progressBar.setPadding(new Insets(15, 0, 0, 8));
		root.add(progressBar, 0, 17);

		int vaccinesNum = ((Hero) chrctr).getVaccineInventory().size();
		ImageView vaccineImageView = new ImageView(vaccineImages.get(0));
		if (vaccinesNum <= 5 && vaccinesNum > 0) {
			vaccineImageView = new ImageView(vaccineImages.get(vaccinesNum));
		} else if (vaccinesNum > 5) {
			vaccineImageView = new ImageView(vaccineImages.get(5));
		}
		vaccineImageView.setScaleX(0.3);
		vaccineImageView.setScaleY(0.3);
		vaccineImageView.setTranslateY(-20);
		root.add(vaccineImageView, 3, 16);
	}

	private void useSpecialAction(Character chrctr, Stage primaryStage, ImageView useSpecialView) {
		if (chrctr instanceof Explorer) {
			try {
				((Explorer) chrctr).useSpecial();
				updateBar(chrctr, primaryStage);
			} catch (NoAvailableResourcesException | InvalidTargetException e) {
				showPopUp(e.getMessage(), primaryStage);
			}
		} else if (chrctr instanceof Fighter) {
			try {
				((Fighter) chrctr).useSpecial();
				updateBar(chrctr, primaryStage);
			} catch (NoAvailableResourcesException | InvalidTargetException e) {
				showPopUp(e.getMessage(), primaryStage);
			}
		}

	}

	private void initializeGrid(Stage primaryStage) {

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
		}
		updateTexturedWall(primaryStage);
	}

	private void controllerEndTurn(Stage primaryStage) {
		try {
			Game.endTurn();
		} catch (NotEnoughActionsException e) {
		} catch (InvalidTargetException e) {
		}
		// TODO add sound to transition to zombieAttackImg
		ImageView zombieView = new ImageView(ZombieAttackImg);
		BorderPane layout2 = new BorderPane(zombieView);
		zombieView.fitWidthProperty().bind(layout2.widthProperty());
		zombieView.fitHeightProperty().bind(layout2.heightProperty());
		primaryStage.getScene().setRoot(layout2);

		FadeTransition fadeInZombie = new FadeTransition(Duration.seconds(5), layout2);
		// opacity
		fadeInZombie.setFromValue(0.0);
		fadeInZombie.setToValue(1.0);
		fadeInZombie.setOnFinished(e -> {
			primaryStage.getScene().setRoot(root);
			layout2.getChildren().clear();
		});
		fadeInZombie.play();

//		if (Game.checkWin()) {
//			Text txt = new Text("You Won!");
//			txt.setFont(Font.font("Yu Gothic Regular", 5));
//			txt.setFill(Color.BLACK);
//			txt.setScaleX(2);
//			txt.setScaleY(2);
//			endGameScene.getChildren().add(txt);
////			primaryStage.setScene(scene2);
//			primaryStage.show();
//		}
//		if (Game.checkGameOver()) {
//			Text txt = new Text("Game Over!");
//			endGameScene.getChildren().add(txt);
//			txt.setFill(Color.BLACK);
//			txt.setScaleX(2);
//			txt.setScaleY(2);
////			primaryStage.setScene(scene2);
//			primaryStage.show();
//		}
		updateTexturedWall(primaryStage);
	}

	private void putEndTurnButton(Stage primaryStage) {
		ImageView imageView = new ImageView(endTurnButtonImage);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
		root.add(imageView, 14, 17);
		imageView.setTranslateY(-30);
		imageView.setTranslateX(-50);
		imageView.setOnMouseEntered(event -> imageView.setCursor(handCursor));
		imageView.setOnMouseClicked(event -> controllerEndTurn(primaryStage));
	}

	private void loadResources() {
		try {
			Game.loadHeroes("src/Heroes.csv");
		} catch (IOException e) {
		}
		Image useSpecialExplorer = ImageLoader.loadImage("icons/UseSpecialExplorer.png");
		Image useSpecialFighter = ImageLoader.loadImage("icons/UseSpecialFighter.png");
		Image useSpecialMedic = ImageLoader.loadImage("icons/UseSpecialMedic.png");
		Image explorerSupply0 = ImageLoader.loadImage("icons/ExplorerSupply0.png");
		Image explorerSupply1 = ImageLoader.loadImage("icons/ExplorerSupply1.png");
		Image explorerSupply2 = ImageLoader.loadImage("icons/ExplorerSupply2.png");
		Image explorerSupply3 = ImageLoader.loadImage("icons/ExplorerSupply3.png");
		Image explorerSupply4 = ImageLoader.loadImage("icons/ExplorerSupply4.png");
		Image explorerSupply5 = ImageLoader.loadImage("icons/ExplorerSupply5.png");
		Image fighterSupply0 = ImageLoader.loadImage("icons/FighterSupply0.png");
		Image fighterSupply1 = ImageLoader.loadImage("icons/FighterSupply1.png");
		Image fighterSupply2 = ImageLoader.loadImage("icons/FighterSupply2.png");
		Image fighterSupply3 = ImageLoader.loadImage("icons/FighterSupply3.png");
		Image fighterSupply4 = ImageLoader.loadImage("icons/FighterSupply4.png");
		Image fighterSupply5 = ImageLoader.loadImage("icons/FighterSupply5.png");
		Image medicSupply0 = ImageLoader.loadImage("icons/MedicSupply0.png");
		Image medicSupply1 = ImageLoader.loadImage("icons/MedicSupply1.png");
		Image medicSupply2 = ImageLoader.loadImage("icons/MedicSupply2.png");
		Image medicSupply3 = ImageLoader.loadImage("icons/MedicSupply3.png");
		Image medicSupply4 = ImageLoader.loadImage("icons/MedicSupply4.png");
		Image medicSupply5 = ImageLoader.loadImage("icons/MedicSupply5.png");
		Image vaccine0 = ImageLoader.loadImage("icons/0Vaccine.png");
		Image vaccine1 = ImageLoader.loadImage("icons/1Vaccine.png");
		Image vaccine2 = ImageLoader.loadImage("icons/2Vaccine.png");
		Image vaccine3 = ImageLoader.loadImage("icons/3Vaccine.png");
		Image vaccine4 = ImageLoader.loadImage("icons/4Vaccine.png");
		Image vaccine5 = ImageLoader.loadImage("icons/5Vaccine.png");
		useSpecialImages.add(useSpecialExplorer);
		useSpecialImages.add(useSpecialFighter);
		useSpecialImages.add(useSpecialMedic);
		explorerSupplyImages.add(explorerSupply0);
		explorerSupplyImages.add(explorerSupply1);
		explorerSupplyImages.add(explorerSupply2);
		explorerSupplyImages.add(explorerSupply3);
		explorerSupplyImages.add(explorerSupply4);
		explorerSupplyImages.add(explorerSupply5);
		fighterSupplyImages.add(fighterSupply0);
		fighterSupplyImages.add(fighterSupply1);
		fighterSupplyImages.add(fighterSupply2);
		fighterSupplyImages.add(fighterSupply3);
		fighterSupplyImages.add(fighterSupply4);
		fighterSupplyImages.add(fighterSupply5);
		medicSupplyImages.add(medicSupply0);
		medicSupplyImages.add(medicSupply1);
		medicSupplyImages.add(medicSupply2);
		medicSupplyImages.add(medicSupply3);
		medicSupplyImages.add(medicSupply4);
		medicSupplyImages.add(medicSupply5);
		vaccineImages.add(vaccine0);
		vaccineImages.add(vaccine1);
		vaccineImages.add(vaccine2);
		vaccineImages.add(vaccine3);
		vaccineImages.add(vaccine4);
		vaccineImages.add(vaccine5);

	}

	private void moveHelper(Character chrctr, Stage primaryStage) {
		root.setFocusTraversable(true);
		root.setOnKeyPressed(e -> {
			root.requestFocus();
			if (chrctr == null) {
//				System.out.println(selected);				
				return;
			} else if (chrctr instanceof Hero) {
				if (e.getCode() == KeyCode.W) {
					try {
						int previousHP = ((Hero) chrctr).getCurrentHp();
						((Hero) chrctr).move(Direction.UP);
						int currentHP = ((Hero) chrctr).getCurrentHp();
						if (previousHP > currentHP) {
							showPopUp("You have stepped on a trap cell", primaryStage);
						}
					} catch (MovementException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					} catch (NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}
				} else if (e.getCode() == KeyCode.D) {
					try {
						((Hero) chrctr).move(Direction.RIGHT);
					} catch (MovementException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					} catch (NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}
				} else if (e.getCode() == KeyCode.A) {
					try {
						((Hero) chrctr).move(Direction.LEFT);
					} catch (MovementException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					} catch (NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}
				} else if (e.getCode() == KeyCode.S) {
					try {
						((Hero) chrctr).move(Direction.DOWN);
					} catch (MovementException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					} catch (NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}
				} else
					return;
//				updateMap(primaryStage);
				updateBar(chrctr, primaryStage);
			}
		});

	}

	private void showPopUp(String popUpContent, Stage primaryStage) {
		Text content = new Text(popUpContent);
		content.setFont(Font.font("Monospaced", 20));
		content.setFill(Color.ANTIQUEWHITE);
		Popup popup = new Popup();
		popup.getContent().add(content);
		// Create the popup content
		VBox popupContent = new VBox();
		popup.getContent().add(popupContent);
		if (!popup.isShowing()) {
			popup.show(primaryStage);
		}

		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(e -> {
			popup.hide();
		});
		delay.play();
	}

	private void attackUI(Stage primaryStage, ImageView attackImgView) {
		int column = GridPane.getColumnIndex(attackImgView);
		int row = GridPane.getRowIndex(attackImgView);

		ImageView cureHighlightedImgView = new ImageView(AttackModeHighlighted);
		cureHighlightedImgView.setScaleX(0.4);
		cureHighlightedImgView.setScaleY(0.4);
		root.add(cureHighlightedImgView, column, row);

		PauseTransition delayClick = new PauseTransition(Duration.millis(100));
		delayClick.setOnFinished(e -> {
			root.getChildren().remove(cureHighlightedImgView);
			root.add(attackImgView, column, row);
		});
		delayClick.play();

		try {
			selected.setTarget(selectedZombie);
			selected.attack();
			if (Game.zombies.contains(selectedZombie)) {
				ImageView zombieAttacked = new ImageView(zombieHighlighted);
				zombieAttacked.setScaleX(0.08);
				zombieAttacked.setScaleY(0.08);
				PauseTransition delay = new PauseTransition(Duration.seconds(1));
				delay.setOnFinished(e -> {
					ImageView imgView = new ImageView(zombieImage);
					imgView.setScaleX(0.08);
					imgView.setScaleY(0.08);
					root.add(imgView, selectedZombie.getLocation().y, 14 - selectedZombie.getLocation().x);
				});
				root.add(zombieAttacked, selectedZombie.getLocation().y, 14 - selectedZombie.getLocation().x);
				delay.play();
			}
		} catch (NotEnoughActionsException | InvalidTargetException e) {
			showPopUp(e.getMessage(), primaryStage);
		}
	}

	private void attackUI(Stage primaryStage) {

		try {
			selected.setTarget(selectedZombie);
			selected.attack();
			if (Game.zombies.contains(selectedZombie)) {
				ImageView zombieAttacked = new ImageView(zombieHighlighted);
				zombieAttacked.setScaleX(0.08);
				zombieAttacked.setScaleY(0.08);
				PauseTransition delay = new PauseTransition(Duration.seconds(1));
				delay.setOnFinished(e -> {
					ImageView imgView = new ImageView(zombieImage);
					imgView.setScaleX(0.08);
					imgView.setScaleY(0.08);
					root.add(imgView, selectedZombie.getLocation().y, 14 - selectedZombie.getLocation().x);
				});
				root.add(zombieAttacked, selectedZombie.getLocation().y, 14 - selectedZombie.getLocation().x);
				delay.play();
			}
		} catch (NotEnoughActionsException | InvalidTargetException e) {
			showPopUp(e.getMessage(), primaryStage);
		}
	}
	private void cureUI(Stage primaryStage) {

	try {
		selected.setTarget(selectedZombie);
		selected.cure();
	} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
		showPopUp(e.getMessage(), primaryStage);
	}

}

	/*private void cureUI(Stage primaryStage, ImageView cureImgView) {
		int column = GridPane.getColumnIndex(cureImgView);
		int row = GridPane.getRowIndex(cureImgView);

		ImageView cureHighlightedImgView = new ImageView(CureModeHighlighted);
		cureHighlightedImgView.setScaleX(0.4);
		cureHighlightedImgView.setScaleY(0.4);
		root.add(cureHighlightedImgView, column, row);

		PauseTransition delay = new PauseTransition(Duration.millis(100));
		delay.setOnFinished(e -> {
			root.getChildren().remove(cureHighlightedImgView);
			root.add(cureImgView, column, row);
		});
		delay.play();

		try {
			selected.setTarget(selectedZombie);
			selected.cure();
		} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
			showPopUp(e.getMessage(), primaryStage);
		}

	}*/
	

	private void select(ImageView v, Hero character) {
		selected = character;
		selectedImage = v;
	}

	private void selectZombie(ImageView v, Zombie h) {
		selectedZombie = h;
		selectedZombieImage = v;
	}

}
