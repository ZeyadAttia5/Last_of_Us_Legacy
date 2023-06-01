package views;

import java.io.IOException;
import javafx.scene.media.*;
import java.util.ArrayList;
import java.io.File;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.effect.Glow;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
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
import javafx.stage.Popup;
import javafx.scene.text.Font;

public class GamePlay extends Application {

	private static GridPane root = new GridPane();
//	private static BorderPane endGameScene = new BorderPane();

//	private ImageLoader imageLoader = ImageLoader.loadImageLoader();ِ

	private Image logo = ImageLoader.loadImage("icons/logo.png");
	private Image emptyCell = ImageLoader.loadImage("icons/emptyCell.png");
	private Image explorerImage = ImageLoader.loadImage("icons/explorerImage.png");
	private Image medicImage = ImageLoader.loadImage("icons/medicImage.png");
	private Image fighterImage = ImageLoader.loadImage("icons/fighterImage.png");
	private Image vaccineImage = ImageLoader.loadImage("icons/vaccineImage.png");
	private Image supplyImage = ImageLoader.loadImage("icons/supplyImage.png");
	private Image zombieImage = ImageLoader.loadImage("icons/zombieImage.png");
	private Image invisibleEmptyCell = ImageLoader.loadImage("icons/darkInvisibleEmptyCell.png");
	private Image texturedBar = ImageLoader.loadImage("icons/texturedBar.png");
	private Image fighterProfile = ImageLoader.loadImage("icons/fighterProfile.png");
	private Image explorerPrfile = ImageLoader.loadImage("icons/explorerProfile.png");
	private Image endTurnButtonImage = ImageLoader.loadImage("icons/endTurnButtonImage.png");
	private Image medicProfile = ImageLoader.loadImage("icons/medicProfile.png");
	private Image zombieProfile = ImageLoader.loadImage("icons/zombieProfile.png");
	private Image handCursorImage = ImageLoader.loadImage("icons/cursors/handCursor.png");
	private Image availableActionsText = ImageLoader.loadImage("icons/ActionsAvialable.png");
	private Image attackModeImage = ImageLoader.loadImage("icons/AttackMode.png");
	private Image cureModeImage = ImageLoader.loadImage("icons/CureMode.png");
	private Image ZombieAttackImg = ImageLoader.loadImage("endTurnResources/ZombieAttackImg.jpg");
	private Image cureModeHighlighted = ImageLoader.loadImage("icons/CureModeHighlighted.png");
	private Image attackModeHighlighted = ImageLoader.loadImage("icons/AttackModeHighlighted.png");
	private Image zombieHighlighted = ImageLoader.loadImage("icons/zombieHighlighted.png");
	private Image UseSpecialFighterHighlighted = ImageLoader.loadImage("icons/UseSpecialFighterHighlighted.png");
	private Image UseSpecialMedicHighlighted = ImageLoader.loadImage("icons/UseSpecialMedicHighlighted.png");
	private Image UseSpecialExplorerHighlighted = ImageLoader.loadImage("icons/UseSpecialExplorerHighlighted.png");
	private Image GunCursorImage = ImageLoader.loadImage("icons/cursors/GunCursor.png");
	// New Decs
	private Image StartTitleImg = ImageLoader.loadImage("icons/TitleScreen.jpg");
	private Image StartBtn = ImageLoader.loadImage("icons/Start.png");
	private Image StartBtnHighlighted = ImageLoader.loadImage("icons/StartGameHighlighted.png");
	private Image PickClass = ImageLoader.loadImage("icons/pickclassFullyEmpty.png");
	private Image FighterForMenu = ImageLoader.loadImage("icons/FighterSizeConsistent.png");
	private Image MedicForMenu = ImageLoader.loadImage("icons/MedicSizeConsistent.png");
	private Image ExplorerForMenu = ImageLoader.loadImage("icons/ExplorerSizeConsistent.png");
	private Image FighterForMenuFaded = ImageLoader.loadImage("icons/FighterSizeConsistentFaded.png");
	private Image MedicForMenuFaded = ImageLoader.loadImage("icons/MedicSizeConsistentFaded.png");
	private Image ExplorerForMenuFaded = ImageLoader.loadImage("icons/ExplorerSizeConsistentFaded.png");
	private Image SpecialMessageM = ImageLoader.loadImage("icons/MedicSpecial.png");
	private Image SpecialMessageE = ImageLoader.loadImage("icons/ExplorerSpecial.png");
	private Image SpecialMessageF = ImageLoader.loadImage("icons/FighterSpecial.png");
	private Image FighterPickScreen = ImageLoader.loadImage("icons/PickFighterScreen.png");
	private Image ExplorerPickScreen = ImageLoader.loadImage("icons/PickExplorerScreenNoBtn.jpg");
	private Image MedicPickScreen = ImageLoader.loadImage("icons/PickMedicScreen.jpg");
	private Image ChooseBtn = ImageLoader.loadImage("icons/ChooseButton.png");
	private Image ChooseBtnHighlighted = ImageLoader.loadImage("icons/ChooseButtonHighlighted.png");
	//private Media theme = new Media("media/TrimmedLast.mp3");  
	private boolean AttackMode = false;
	private boolean useSpecialMedicMode = false;
	private boolean CureMode = false;
	private boolean gameRunning = true;
	private int HeroIndex = -1;
	private boolean FinishedSelection = false;
	//
	Font minecraftFont = new Font("fonts/minecraftFont.ttf", 20);
	//private String css = this.getClass().getResource("css/application.css").toExternalForm();

	private ImageCursor handCursor = new ImageCursor(handCursorImage);
	private ImageCursor GunCursor = new ImageCursor(GunCursorImage);
	private ImageCursor CureCursor = new ImageCursor(vaccineImage);
//	private Character selected = null;
	private Hero selected;
	private Medic medicSpecial;
	private Zombie selectedZombie;
	private ImageView selectedZombieImage;
	private ImageView selectedImage;
//	private ImageView emptyCellView = new ImageView(emptyCell);
	private ArrayList<Image> fighterSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> medicSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> explorerSupplyImages = new ArrayList<Image>();
	private ArrayList<Image> vaccineImages = new ArrayList<Image>();
	private ArrayList<Image> useSpecialImages = new ArrayList<Image>();
	private Scene scene1 = new Scene(root, Color.BEIGE);
	SoundLoader soundLoader = new SoundLoader();
	private Media theme = soundLoader.getSound("/media/TrimmedLast.mp3");
	private Media walk = soundLoader.getSound("/media/theWalk.mp3");
	private Media Gunshot = soundLoader.getSound("/media/Gunshot.mp3");
	private MediaPlayer mediaPlayer = new MediaPlayer(theme);
	//private 

	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		startGameMenu(primaryStage);
		primaryStage.show();
	}

	private void LoadMapGUI(Stage primaryStage) {
		loadResources();
		selected = Game.availableHeroes.remove(HeroIndex);
		Game.startGame(selected);
		mediaPlayer.stop();
		initializeGrid(primaryStage);
		moveHelper(selected, primaryStage);
		soundLoader.removeSound(theme);
		ImageLoader.clearCache();
//		putEndTurnButton(primaryStage);
//		updateMap(primaryStage);
	}

	private void primaryStageInit(Stage primaryStage) {
		primaryStage.setTitle("Last of Us - Legacy");
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
				ImageView emptyCellView = new ImageView(emptyCell);
				emptyCellView.setScaleX(0.7);
				emptyCellView.setScaleY(0.3);
				root.add(emptyCellView, y, 14 - x);
				if (Game.map[x][y].isVisible()) {
					addGlowEffect(emptyCellView, Color.BLACK, root);
					if (Game.map[x][y] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
							if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {
								ImageView medicImageView = new ImageView(medicImage);
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								medicImageView.setScaleX(0.08);
								medicImageView.setScaleY(0.08);
								root.add(medicImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								medicImageView.setOnMouseEntered(e -> medicImageView.setCursor(handCursor));
								medicImageView.setOnMouseClicked(e -> {
									select(h);
									selectMedic(h);
									root.requestFocus();
									moveHelper(selected, primaryStage);
									updateBar(selected, primaryStage);
//									System.out.println(useSpecialMedicMode);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, medicImageView);
									}
								});
								emptyCellView.setOnMouseClicked(e -> {
									select(h);
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, medicImageView);
									}
								});

							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
								ImageView fighterImageView = new ImageView(fighterImage);
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								fighterImageView.setOnMouseClicked(e -> select(h));
								fighterImageView.setScaleX(0.09);
								fighterImageView.setScaleY(0.09);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								fighterImageView.setOnMouseEntered(e -> fighterImageView.setCursor(handCursor));
								fighterImageView.setOnMouseClicked(e -> {
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
//									System.out.println(useSpecialMedicMode);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, fighterImageView);
									}
								});
								emptyCellView.setOnMouseClicked(e -> {
									select(h);
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, fighterImageView);
									}
								});

								root.add(fighterImageView, y, 14 - x);
							} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
								ImageView explorerImageView = new ImageView(explorerImage);
								Hero h = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
								explorerImageView.setScaleX(0.06);
								explorerImageView.setScaleY(0.06);
								root.add(explorerImageView, y, 14 - x);
								model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
								explorerImageView.setOnMouseEntered(e -> explorerImageView.setCursor(handCursor));
								explorerImageView.setOnMouseClicked(e -> {
									select(h);
									root.requestFocus();
									select(h);
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, explorerImageView);
									}
								});
								emptyCellView.setOnMouseClicked(e -> {
									select(h);
									root.requestFocus();
									moveHelper(chrctr, primaryStage);
									updateBar(chrctr, primaryStage);
									if (useSpecialMedicMode) {
										useSpecialActionMedic(h, primaryStage, explorerImageView);
									}
								});
//								explorerImageView.setOnMouseClicked(e -> updateBar(chrctr, primaryStage));
							}

						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Zombie) {
							ImageView zombieImageView = new ImageView(zombieImage);
							Zombie h = (Zombie) ((CharacterCell) Game.map[x][y]).getCharacter();

							zombieImageView.setScaleX(0.08);
							zombieImageView.setScaleY(0.08);
							zombieImageView.setOnMouseEntered(e -> {
								if (AttackMode)
									zombieImageView.setCursor(GunCursor);
								else if (CureMode)
									zombieImageView.setCursor(CureCursor);
								else
									zombieImageView.setCursor(handCursor);
							});
							root.add(zombieImageView, y, 14 - x);
							model.characters.Character chrctr = (((CharacterCell) Game.map[x][y]).getCharacter());
//							zombieImageView.setOnMouseEntered(e -> zombieImageView.setCursor(handCursor));
							zombieImageView.setOnMouseClicked(e -> {
								if (AttackMode || CureMode) {
									selectZombie(zombieImageView, h);
								}
								updateBar(chrctr, primaryStage);
							});
							emptyCellView.setOnMouseClicked(e -> {
								if (AttackMode || CureMode) {
									selectZombie(zombieImageView, h);

								}
								updateBar(chrctr, primaryStage);
							});
						}

					} else if (Game.map[x][y] instanceof CollectibleCell) {
						if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Vaccine) {

							ImageView vaccineImageView = new ImageView(vaccineImage);
							Image image = ImageLoader.loadImage("icons/Hand.png");
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
		if (FinishedSelection) {
//			primaryStage.setScene(scene1);
//			primaryStage.setFullScreen(true);
			FinishedSelection = false;
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
		addName(chrctr);

		if (chrctr instanceof Hero) {

			Text vaccineText = new Text("Vaccines");
			vaccineText.setFont(Font.font("Monospaced", 14));
			vaccineText.setFill(Color.WHITE);
			vaccineText.setStroke(Color.WHITE);
			vaccineText.setTranslateX(-10);
			vaccineText.setTranslateY(-10);
			root.add(vaccineText, 2, 16);

			addSupplies(chrctr, 3, 17);
			addActionsAvailable(5, 16, chrctr);

			ImageView attackImageView = new ImageView(attackModeImage);
			ImageView cureImageView = new ImageView(cureModeImage);
			putModeBtn(attackImageView, 9, 16, 20);
			putModeBtn(cureImageView, 7, 16, -20);
			attackImageView.setOnMouseClicked(e -> {
				if (!AttackMode) {
					setCureMode(false, cureImageView);
					setAttackMode(true, attackImageView);
					root.setCursor(GunCursor);
				} else {
					setAttackMode(false, attackImageView);
					root.setCursor(Cursor.DEFAULT);
				}
			});
			cureImageView.setOnMouseClicked(e -> {
				if (!CureMode) {
					setAttackMode(false, attackImageView);
					setCureMode(true, cureImageView);
					root.setCursor(CureCursor);

				} else {
					setCureMode(false, cureImageView);
					root.setCursor(Cursor.DEFAULT);
				}
			});
			attackImageView.setOnMouseEntered(e -> {
				attackImageView.setImage(attackModeHighlighted);
				root.setCursor(handCursor);
			});
			attackImageView.setOnMouseExited(e -> {
				if (AttackMode) {
					attackImageView.setImage(attackModeHighlighted);
					root.setCursor(GunCursor);
				} else {
					attackImageView.setImage(attackModeImage);
					root.setCursor(Cursor.DEFAULT);
				}
				if (AttackMode)
					root.setCursor(GunCursor);
				if (CureMode)
					root.setCursor(CureCursor);
			});
			cureImageView.setOnMouseEntered(e -> {
				cureImageView.setImage(cureModeHighlighted);
				root.setCursor(handCursor);
			});
			cureImageView.setOnMouseExited(e -> {
				if (CureMode) {
					cureImageView.setImage(cureModeHighlighted);
					root.setCursor(CureCursor);
				} else {
					cureImageView.setImage(cureModeImage);
					root.setCursor(Cursor.DEFAULT);
				}
				if (AttackMode)
					root.setCursor(GunCursor);
				if (CureMode)
					root.setCursor(CureCursor);
			});

			if (chrctr instanceof model.characters.Fighter) {
				ImageView fighterProfileView = new ImageView(fighterProfile);
				fighterProfileView.setScaleX(0.2);
				fighterProfileView.setScaleY(0.2);
				root.add(fighterProfileView, 0, 16);
				select((Hero) chrctr);

				ImageView useSpecialView = new ImageView(useSpecialImages.get(1));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				root.add(useSpecialView, 8, 16);
				useSpecialView.setOnMouseClicked(e -> {
					useSpecialAction(chrctr, primaryStage);
				});
				useSpecialView.setOnMouseEntered(e -> {
					root.setCursor(handCursor);
					useSpecialView.setImage(UseSpecialFighterHighlighted);
				});
				useSpecialView.setOnMouseExited(e -> {
					// root.setCursor(Cursor.DEFAULT);
					useSpecialView.setImage(useSpecialImages.get(1));
					if (AttackMode)
						root.setCursor(GunCursor);
					if (CureMode)
						root.setCursor(CureCursor);
				});
			}
			if (chrctr instanceof model.characters.Explorer) {
				ImageView explorerProfileView = new ImageView(explorerPrfile);
				explorerProfileView.setScaleX(0.2);
				explorerProfileView.setScaleY(0.2);
				root.add(explorerProfileView, 0, 16);
				select((Hero) chrctr);

				ImageView useSpecialView = new ImageView(useSpecialImages.get(0));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				root.add(useSpecialView, 8, 16);
				useSpecialView.setOnMouseClicked(e -> {
					useSpecialAction(chrctr, primaryStage);
				});
				useSpecialView.setOnMouseEntered(e -> {
					root.setCursor(handCursor);
					useSpecialView.setImage(UseSpecialExplorerHighlighted);
				});
				useSpecialView.setOnMouseExited(e -> {
					// root.setCursor(Cursor.DEFAULT);
					useSpecialView.setImage(useSpecialImages.get(0));
					if (AttackMode)
						root.setCursor(GunCursor);
					if (CureMode)
						root.setCursor(CureCursor);
				});
			}
			if (chrctr instanceof model.characters.Medic) {
				ImageView medicProfileView = new ImageView(medicProfile);
				medicProfileView.setScaleX(0.2);
				medicProfileView.setScaleY(0.2);
				root.add(medicProfileView, 0, 16);
				select((Hero) chrctr);

				ImageView useSpecialView = new ImageView(useSpecialImages.get(2));
				useSpecialView.setScaleX(0.4);
				useSpecialView.setScaleY(0.4);
				useSpecialView.setOnMouseClicked(e -> {
					useSpecialMedicMode = true;
//					System.out.println("useSpecialMedicMode activated");
					useSpecialAction(chrctr, primaryStage);
				});
				useSpecialView.setOnMouseEntered(e -> {
					root.setCursor(handCursor);
					useSpecialView.setImage(UseSpecialMedicHighlighted);
				});
				useSpecialView.setOnMouseExited(e -> {
					// root.setCursor(Cursor.DEFAULT);
					useSpecialView.setImage(useSpecialImages.get(2));
					if (AttackMode)
						root.setCursor(GunCursor);
					if (CureMode)
						root.setCursor(CureCursor);
				});
				useSpecialMedicMode = false;
				root.add(useSpecialView, 8, 16);
			}

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
		if (chrctr instanceof model.characters.Zombie) {
			ImageView zombieProfileView = new ImageView(zombieProfile);
			selectZombie(zombieProfileView, (Zombie) chrctr);
			zombieProfileView.setScaleX(0.2);
			zombieProfileView.setScaleY(0.2);
			root.add(zombieProfileView, 0, 16);
		}
		ProgressBar progressBar = new ProgressBar((double) chrctr.getCurrentHp() / (double) chrctr.getMaxHp());
		progressBar.setStyle("-fx-accent: red");
		progressBar.setBorder(Border.EMPTY);
		progressBar.setPadding(new Insets(15, 8, 0, 9));
		root.add(progressBar, 0, 17);
	}

	private void addActionsAvailable(int col, int row, Character chrctr) {
		ImageView actionsAvailableView = new ImageView(availableActionsText);
		actionsAvailableView.setScaleX(0.27);
		actionsAvailableView.setScaleY(0.27);
		root.add(actionsAvailableView, col, row);

		Text actionsAvailable = new Text(((Hero) chrctr).getActionsAvailable() + "");
		actionsAvailable.setFont(Font.font("Monospaced", 20));
		actionsAvailable.setFill(Color.WHITE);
		actionsAvailable.setStroke(Color.WHITE);
		actionsAvailable.setTranslateX(5);
		actionsAvailable.setTranslateY(4.8);
		root.add(actionsAvailable, col + 1, row);

	}

	private void addSupplies(Character chrctr, int col, int row) {

		Text supplyText = new Text("Supplies");
		supplyText.setFont(Font.font("Monospaced", 14));
		supplyText.setFill(Color.WHITE);
		supplyText.setStroke(Color.WHITE);
		supplyText.setTranslateX(-10);
		supplyText.setTranslateY(-8);
		root.add(supplyText, col - 1, 17);

		int suppliesNum = ((Hero) chrctr).getSupplyInventory().size();

		if (chrctr instanceof Fighter) {
			ImageView supplyImageView = new ImageView(fighterSupplyImages.get(0));
			if (suppliesNum <= 5 && suppliesNum > 0) {
				supplyImageView = new ImageView(fighterSupplyImages.get(suppliesNum));
			} else if (suppliesNum > 5) {
				supplyImageView = new ImageView(fighterSupplyImages.get(5));
			}
			supplyImageView.setScaleX(0.25);
			supplyImageView.setScaleY(0.25);
			supplyImageView.setTranslateY(-15);
			root.add(supplyImageView, col, row);

		} else if (chrctr instanceof Medic) {
			ImageView supplyImageView = new ImageView(medicSupplyImages.get(0));

			if (suppliesNum <= 5 && suppliesNum > 0) {
				supplyImageView = new ImageView(medicSupplyImages.get(suppliesNum));
			} else if (suppliesNum > 5) {
				supplyImageView = new ImageView(medicSupplyImages.get(5));
			}
			supplyImageView.setScaleX(0.25);
			supplyImageView.setScaleY(0.25);
			supplyImageView.setTranslateY(-15);
			root.add(supplyImageView, col, row);
		} else if (chrctr instanceof Explorer) {
			ImageView supplyImageView = new ImageView(explorerSupplyImages.get(0));
			if (suppliesNum <= 5 && suppliesNum > 0) {
				supplyImageView = new ImageView(explorerSupplyImages.get(suppliesNum));
			} else if (suppliesNum > 5) {
				supplyImageView = new ImageView(explorerSupplyImages.get(5));
			}
			supplyImageView.setScaleX(0.3);
			supplyImageView.setScaleY(0.3);
			supplyImageView.setTranslateY(-20);
			root.add(supplyImageView, col, row);
		}

	}

	private void putModeBtn(ImageView attackImageView, int col, int row, double translateX) {
		attackImageView.setScaleX(0.4);
		attackImageView.setScaleY(0.4);
		attackImageView.setTranslateX(translateX);
		root.add(attackImageView, col, row);
	}

	private void addName(Character chrctr) {
		Text name = new Text(chrctr.getName());
		if (chrctr.getName().length() < 10)
			name.setFont(Font.font("Monospaced", 14));
		else
			name.setFont(Font.font("Monospaced", 12));
		if (chrctr instanceof Zombie) {
			name.setFill(Color.ORANGERED);
			name.setStroke(Color.ORANGERED);
		}
		name.setFill(Color.WHITE);
		name.setStroke(Color.WHITE);
		root.add(name, 0, 15);

	}

	private void setAttackMode(boolean b, ImageView attackImageView) {
		if (b == true) {
			AttackMode = true;
			attackImageView.setImage(attackModeHighlighted);
		} else {
			AttackMode = false;
			attackImageView.setImage(attackModeImage);
		}

	}

	private void setCureMode(boolean b, ImageView cureImageView) {
		if (b == true) {
			CureMode = true;
			cureImageView.setImage(cureModeHighlighted);
		} else {
			CureMode = false;
			cureImageView.setImage(cureModeImage);
		}

	}

	private void useSpecialAction(Character chrctr, Stage primaryStage) {
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

	private void useSpecialActionMedic(Hero target, Stage primaryStage, ImageView targetView) {
		if (useSpecialMedicMode) {
			root.setCursor(CureCursor);
			try {
//				select(targetView, (Hero) target);
				((Medic) medicSpecial).setTarget(target);
				((Medic) medicSpecial).useSpecial();
				useSpecialMedicMode = false;
			} catch (NoAvailableResourcesException | InvalidTargetException e) {
				root.setCursor(Cursor.DEFAULT);
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
		updateBar(selected, primaryStage);
		primaryStage.setScene(scene1);
		primaryStage.setFullScreen(true);
	}

	private void controllerEndTurn(Stage primaryStage) {
		try {
			Game.endTurn();
		} catch (NotEnoughActionsException e) {
		} catch (InvalidTargetException e) {
		}

		AttackMode = false;
		CureMode = false;
		useSpecialMedicMode = false;
		root.setCursor(Cursor.DEFAULT);

		// TODO add sound to transition to zombieAttackImg
		checkEndGame(primaryStage);
		if (gameRunning) {
			ImageView zombieView = new ImageView(ZombieAttackImg);
			putImageFullScreen(zombieView, Duration.seconds(1), Duration.seconds(2), primaryStage, true);
			updateTexturedWall(primaryStage);
		}
	}

	private void putImageFullScreen(ImageView imageView, Duration transitionTime, Duration keepForTime,
			Stage primaryStage, boolean transitionBack) {
		BorderPane layout2 = new BorderPane(imageView);
		imageView.fitWidthProperty().bind(layout2.widthProperty());
		imageView.fitHeightProperty().bind(layout2.heightProperty());
		// TODO Show Dr.Nourhan
		if (!transitionBack) {
			ImageView endGameButton = new ImageView(ImageLoader.loadImage("icons/EndGame.png"));
			layout2.getChildren().add(endGameButton);
			endGameButton.setScaleX(1.2);
			endGameButton.setScaleY(1.2);
			endGameButton.setTranslateX(635);
			endGameButton.setTranslateY(735);
			endGameButton.setOnMouseEntered(e -> layout2.setCursor(handCursor));
			endGameButton.setOnMouseExited(e -> layout2.setCursor(Cursor.DEFAULT));
			endGameButton.setOnMouseClicked(e -> primaryStage.close());
		}
		primaryStage.getScene().setRoot(layout2);
		FadeTransition fadeInZombie = new FadeTransition(transitionTime, layout2);
		// opacity
		fadeInZombie.setFromValue(0.0);
		fadeInZombie.setToValue(1.0);
		fadeInZombie.setOnFinished(e -> {
			if (transitionBack == true) {
				PauseTransition keepImageView = new PauseTransition(keepForTime);

				keepImageView.setOnFinished(trigger -> {
					primaryStage.getScene().setRoot(root);
					layout2.getChildren().clear();
					root.requestFocus();
				});
				keepImageView.play();
			}
		});
		fadeInZombie.play();

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
		//walkSound.stop();
		
		root.setFocusTraversable(true);
		root.requestFocus();
		root.setOnKeyPressed(e -> {
			if (chrctr == null) {
//				System.out.println(selected);				
				return;
			} else if (chrctr instanceof Hero) {
				if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
					try {
						int previousHP = ((Hero) chrctr).getCurrentHp();
						((Hero) chrctr).move(Direction.UP);
						int currentHP = ((Hero) chrctr).getCurrentHp();
						MediaPlayer walkSound = new MediaPlayer(walk);
						walkSound.play();
						if (previousHP > currentHP) {
							showPopUp("You have stepped on a trap cell", primaryStage);
						}
					} catch (MovementException | NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}

				} else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
					try {
						int previousHP = ((Hero) chrctr).getCurrentHp();
						((Hero) chrctr).move(Direction.RIGHT);
						int currentHP = ((Hero) chrctr).getCurrentHp();
						MediaPlayer walkSound = new MediaPlayer(walk);
						walkSound.play();
						if (previousHP > currentHP) {
							showPopUp("You have stepped on a trap cell", primaryStage);
						}
					} catch (MovementException | NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}

				} else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
					try {
						int previousHP = ((Hero) chrctr).getCurrentHp();
						((Hero) chrctr).move(Direction.LEFT);
						MediaPlayer walkSound = new MediaPlayer(walk);
						walkSound.play();
						int currentHP = ((Hero) chrctr).getCurrentHp();
						if (previousHP > currentHP) {
							showPopUp("You have stepped on a trap cell", primaryStage);
						}
					} catch (MovementException | NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}

				} else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
					try {
						int previousHP = ((Hero) chrctr).getCurrentHp();
						((Hero) chrctr).move(Direction.DOWN);
						MediaPlayer walkSound = new MediaPlayer(walk);
						walkSound.play();
						int currentHP = ((Hero) chrctr).getCurrentHp();
						if (previousHP > currentHP) {
							showPopUp("You have stepped on a trap cell", primaryStage);
						}
					} catch (MovementException | NotEnoughActionsException e1) {
						showPopUp(e1.getMessage(), primaryStage);
					}
				} else
					return;
				//walkSound.play();
				checkEndGame(primaryStage);
				updateBar(chrctr, primaryStage);
			}
		});

	}

	private void showPopUp(String popUpContent, Stage primaryStage) {
		Text content = new Text(popUpContent);
//		content.setFont(Font.loadFont("MingLiU_HKSCS-ExtB",22));
		content.setFill(Color.WHITE	);
		content.setStyle("-fx-font-family: Minecraftia-Regular; -fx-font-size: 20");
		
		Popup popup = new Popup();
		popup.getContent().add(content);
		// Create the popup content
		VBox popupContent = new VBox();
		popup.getContent().add(popupContent);
		if (!popup.isShowing()) {
			popup.show(primaryStage);
		}

		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(e -> {
			popup.hide();
		});
		delay.play();
	}

	private void attackUI(Stage primaryStage) {

		try {
			selected.setTarget(selectedZombie);
			selected.attack();
			MediaPlayer ShootSound = new MediaPlayer(Gunshot); 
			ShootSound.play();
		} catch (NotEnoughActionsException | InvalidTargetException e) {
			showPopUp(e.getMessage(), primaryStage);
		}
	}

	private void cureUI(Stage primaryStage) {

		try {
			selected.setTarget(selectedZombie);
			selected.cure();
			checkEndGame(primaryStage);
		} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
			showPopUp(e.getMessage(), primaryStage);
		}

	}

	private void select(Hero character) {
		selected = character;
//		selectedImage = v;
	}

	private void selectMedic(Hero character) {
		medicSpecial = (Medic) character;
	}

	private void selectZombie(ImageView v, Zombie h) {
		selectedZombie = h;
		selectedZombieImage = v;
	}

	private void addGlowEffect(ImageView imageView, Color glowColor, Pane parentPane) {
		Glow glow = new Glow();
		glow.setLevel(0);

		ColorInput colorInput = new ColorInput();
		colorInput.setPaint(glowColor);

		Blend blend = new Blend();
		blend.setMode(BlendMode.MULTIPLY);
		blend.setTopInput(glow);
		blend.setBottomInput(colorInput);

		imageView.setEffect(blend);

		Duration duration = Duration.millis(1);
		double targetLevel = 0.35;

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0)),
				new KeyFrame(duration, new KeyValue(glow.levelProperty(), targetLevel)));

		imageView.setOnMouseEntered(event -> {
			imageView.setCursor(handCursor);
			timeline.playFromStart();
		});
		imageView.setOnMouseExited(event -> {
			glow.setLevel(0);
			imageView.setCursor(Cursor.DEFAULT);
		});
	}

	private void checkEndGame(Stage primaryStage) {

		if (Game.checkWin()) {
			ImageView winView = new ImageView(ImageLoader.loadImage("icons/Win.png"));
			winView.setScaleY(1.01);
			putImageFullScreen(winView, Duration.seconds(3), Duration.seconds(3), primaryStage, false);
			gameRunning = false;
		}

		else if (Game.checkGameOver()) {
			ImageView gameOverView = new ImageView(ImageLoader.loadImage("icons/GameOver.png"));
			gameOverView.setScaleY(1.01);
			putImageFullScreen(gameOverView, Duration.seconds(3), Duration.seconds(3), primaryStage, false);
			gameRunning = false;
		}
	}

	private void startGameMenu(Stage primaryStage) {
		//File file = new File("\"C:\\Users\\omar\\OneDrive\\Documents\\GitHub\\Last_of_Us_Legacy\\src\\media\\TrimmedLast.wav\"");
		mediaPlayer.play();
		ImageView TitleView = new ImageView(StartTitleImg);
		ImageView StartButton = new ImageView(StartBtn);
		BorderPane layout01 = new BorderPane();
		layout01.getChildren().add(TitleView);
		layout01.setBottom(StartButton);
		StartButton.setTranslateX(520);
		StartButton.setTranslateY(-50);
		TitleView.fitWidthProperty().bind(layout01.widthProperty());
		TitleView.fitHeightProperty().bind(layout01.heightProperty());
		StartButton.setOnMouseEntered(event -> {
			StartButton.setImage(StartBtnHighlighted);
			StartButton.setCursor(handCursor);
		});
		StartButton.setOnMouseExited(event -> StartButton.setImage(StartBtn));
		StartButton.setOnMouseClicked(event -> {
			TitleView.setImage(PickClass);
			layout01.getChildren().remove(StartButton);
			ImageView FighterClass = new ImageView(FighterForMenu);
			ImageView MedicClass = new ImageView(MedicForMenu);
			ImageView ExplorerClass = new ImageView(ExplorerForMenu);
			ImageView SpecialMessage = new ImageView();
			MedicClass.setScaleX(0.57);
			MedicClass.setScaleY(0.57);
			FighterClass.setScaleX(0.57);
			FighterClass.setScaleY(0.57);
			ExplorerClass.setScaleX(0.55);
			ExplorerClass.setScaleY(0.55);
			layout01.setLeft(MedicClass);
			layout01.setCenter(FighterClass);
			layout01.setRight(ExplorerClass);
			FighterClass.setTranslateX(-25);
			FighterClass.setTranslateY(150);
			layout01.getChildren().add(SpecialMessage);
			SpecialMessage.setTranslateX(1180);
			SpecialMessage.setTranslateY(500);

			MedicClass.setTranslateX(340);
			MedicClass.setTranslateY(250);

			ExplorerClass.setTranslateX(-370);
			ExplorerClass.setTranslateY(250);
			FighterClass.setOnMouseEntered(event2 -> {
				FighterClass.setCursor(handCursor);
				MedicClass.setImage(MedicForMenuFaded);
				ExplorerClass.setImage(ExplorerForMenuFaded);
				SpecialMessage.setImage(SpecialMessageF);
			});
			FighterClass.setOnMouseExited(event2 -> {
				MedicClass.setImage(MedicForMenu);
				ExplorerClass.setImage(ExplorerForMenu);
				SpecialMessage.setImage(null);
			});
			MedicClass.setOnMouseEntered(event2 -> {
				MedicClass.setCursor(handCursor);
				FighterClass.setImage(FighterForMenuFaded);
				ExplorerClass.setImage(ExplorerForMenuFaded);
				SpecialMessage.setImage(SpecialMessageM);
			});
			MedicClass.setOnMouseExited(event2 -> {
				FighterClass.setImage(FighterForMenu);
				ExplorerClass.setImage(ExplorerForMenu);
				SpecialMessage.setImage(null);
			});
			ExplorerClass.setOnMouseEntered(event2 -> {
				ExplorerClass.setCursor(handCursor);
				MedicClass.setImage(MedicForMenuFaded);
				FighterClass.setImage(FighterForMenuFaded);
				SpecialMessage.setImage(SpecialMessageE);
			});
			ExplorerClass.setOnMouseExited(event2 -> {
				FighterClass.setImage(FighterForMenu);
				MedicClass.setImage(MedicForMenu);
				SpecialMessage.setImage(null);
			});

			FighterClass.setOnMouseClicked(event2 -> {
				layout01.getChildren().remove(SpecialMessage);
				layout01.getChildren().remove(ExplorerClass);
				layout01.getChildren().remove(FighterClass);
				layout01.getChildren().remove(MedicClass);
				TitleView.setImage(FighterPickScreen);
				ImageView ChooseJoel = new ImageView(ChooseBtn);
				ImageView ChooseDavid = new ImageView(ChooseBtn);
				layout01.setCenter(ChooseJoel);
				ChooseJoel.setScaleX(0.6);
				ChooseJoel.setScaleY(0.6);
				ChooseJoel.setTranslateX(90);
				ChooseJoel.setTranslateY(90);
				layout01.setBottom(ChooseDavid);
				ChooseDavid.setScaleX(0.6);
				ChooseDavid.setScaleY(0.6);
				ChooseDavid.setTranslateX(725);
				ChooseDavid.setTranslateY(-52);
				ChooseJoel.setOnMouseEntered(event3 -> {
					ChooseJoel.setCursor(handCursor);
					ChooseJoel.setImage(ChooseBtnHighlighted);
				});
				ChooseJoel.setOnMouseExited(event3 -> {
					ChooseJoel.setImage(ChooseBtn);
				});
				ChooseJoel.setOnMouseClicked(event3 -> {
					HeroIndex = 0;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
				ChooseDavid.setOnMouseEntered(event3 -> {
					ChooseDavid.setCursor(handCursor);
					ChooseDavid.setImage(ChooseBtnHighlighted);
				});
				ChooseDavid.setOnMouseExited(event3 -> {
					ChooseDavid.setImage(ChooseBtn);
				});
				ChooseDavid.setOnMouseClicked(event3 -> {
					HeroIndex = 6;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});

			});

			ExplorerClass.setOnMouseClicked(event2 -> {
				layout01.getChildren().remove(SpecialMessage);
				layout01.getChildren().remove(ExplorerClass);
				layout01.getChildren().remove(FighterClass);
				layout01.getChildren().remove(MedicClass);
				TitleView.setImage(ExplorerPickScreen);
				ImageView ChooseTess = new ImageView(ChooseBtn);
				ImageView ChooseRiley = new ImageView(ChooseBtn);
				ImageView ChooseTommy = new ImageView(ChooseBtn);
				layout01.setTop(ChooseTess);
				layout01.setCenter(ChooseRiley);
				layout01.setBottom(ChooseTommy);
				ChooseTess.setScaleX(0.5);
				ChooseTess.setScaleY(0.5);
				ChooseRiley.setScaleX(0.5);
				ChooseRiley.setScaleY(0.5);
				ChooseTommy.setScaleX(0.5);
				ChooseTommy.setScaleY(0.5);
				ChooseTess.setTranslateX(830);
				ChooseTess.setTranslateY(240);
				ChooseRiley.setTranslateX(192);
				ChooseRiley.setTranslateY(60);
				ChooseTommy.setTranslateX(830);
				ChooseTommy.setTranslateY(-110);
				ChooseTess.setOnMouseEntered(event3 -> {
					ChooseTess.setCursor(handCursor);
					ChooseTess.setImage(ChooseBtnHighlighted);
				});
				ChooseTess.setOnMouseExited(event3 -> {
					ChooseTess.setImage(ChooseBtn);
				});
				ChooseTess.setOnMouseClicked(event3 -> {
					HeroIndex = 2;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
				ChooseRiley.setOnMouseEntered(event3 -> {
					ChooseRiley.setCursor(handCursor);
					ChooseRiley.setImage(ChooseBtnHighlighted);
				});
				ChooseRiley.setOnMouseExited(event3 -> {
					ChooseRiley.setImage(ChooseBtn);
				});
				ChooseRiley.setOnMouseClicked(event3 -> {
					HeroIndex = 3;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
				ChooseTommy.setOnMouseEntered(event3 -> {
					ChooseTommy.setCursor(handCursor);
					ChooseTommy.setImage(ChooseBtnHighlighted);
				});
				ChooseTommy.setOnMouseExited(event3 -> {
					ChooseTommy.setImage(ChooseBtn);
				});
				ChooseTommy.setOnMouseClicked(event3 -> {
					HeroIndex = 4;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});

			});
			MedicClass.setOnMouseClicked(event2 -> {
				layout01.getChildren().remove(SpecialMessage);
				layout01.getChildren().remove(ExplorerClass);
				layout01.getChildren().remove(FighterClass);
				layout01.getChildren().remove(MedicClass);
				TitleView.setImage(MedicPickScreen);
				ImageView ChooseEllie = new ImageView(ChooseBtn);
				ImageView ChooseBill = new ImageView(ChooseBtn);
				ImageView ChooseHenry = new ImageView(ChooseBtn);
				layout01.setTop(ChooseEllie);
				layout01.setCenter(ChooseBill);
				layout01.setBottom(ChooseHenry);
				ChooseEllie.setScaleX(0.5);
				ChooseEllie.setScaleY(0.5);
				ChooseBill.setScaleX(0.5);
				ChooseBill.setScaleY(0.5);
				ChooseHenry.setScaleX(0.5);
				ChooseHenry.setScaleY(0.5);
				ChooseEllie.setTranslateX(830);
				ChooseEllie.setTranslateY(240);
				ChooseBill.setTranslateX(192);
				ChooseBill.setTranslateY(60);
				ChooseHenry.setTranslateX(830);
				ChooseHenry.setTranslateY(-110);
				ChooseEllie.setOnMouseEntered(event3 -> {
					ChooseEllie.setCursor(handCursor);
					ChooseEllie.setImage(ChooseBtnHighlighted);
				});
				ChooseEllie.setOnMouseExited(event3 -> {
					ChooseEllie.setImage(ChooseBtn);
				});
				ChooseEllie.setOnMouseClicked(event3 -> {
					HeroIndex = 1;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
				ChooseBill.setOnMouseEntered(event3 -> {
					ChooseBill.setCursor(handCursor);
					ChooseBill.setImage(ChooseBtnHighlighted);
				});
				ChooseBill.setOnMouseExited(event3 -> {
					ChooseBill.setImage(ChooseBtn);
				});
				ChooseBill.setOnMouseClicked(event3 -> {
					HeroIndex = 5;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
				ChooseHenry.setOnMouseEntered(event3 -> {
					ChooseHenry.setCursor(handCursor);
					ChooseHenry.setImage(ChooseBtnHighlighted);
				});
				ChooseHenry.setOnMouseExited(event3 -> {
					ChooseHenry.setImage(ChooseBtn);
				});
				ChooseHenry.setOnMouseClicked(event3 -> {
					HeroIndex = 7;
					LoadMapGUI(primaryStage);
					FinishedSelection = true;
				});
			});
		});

		Scene scene = new Scene(layout01);
		primaryStage.setScene(scene);
	}
}
