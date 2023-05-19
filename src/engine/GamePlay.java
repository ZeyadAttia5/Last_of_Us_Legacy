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
//	private double fullScreenHeight = 0;
//	private double fullScreenWidth = 0;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		root.setGridLinesVisible(true);
		Scene scene1 = new Scene(root, Color.BEIGE);
		root.setPadding(new Insets(10, 10, 10, 10));
//		GridPane.setMargin(root, new Insets(0, 0, 0, 0));

//		Text text = new Text();
//		text.setText("Welcome to the Last of Us - Legacy");
//		text.setFont(Font.font("Arial", 20));
//		text.setX(630);
//		text.setY(350);
//		text.setTextAlignment(TextAlignment.CENTER);
//		text.setFill(Color.ANTIQUEWHITE);
//
//		Line line = new Line();
//		line.setStartX(100);
//		line.setEndX(1000);
//		line.setStartY(100);
//		line.setEndY(600);
//		line.setStrokeWidth(5);
//		line.setStroke(Color.DARKORANGE);
//
//		Rectangle rect = new Rectangle();
//		rect.setX(200);
//		rect.setY(200);
//		rect.setWidth(100);
//		rect.setHeight(300);
//		rect.setStrokeWidth(5);
//		rect.setStroke(Color.DARKSLATEBLUE);
//
//		Circle circ = new Circle();
//		circ.setRadius(10);
//		circ.setCenterX(200);
//		circ.setCenterY(200);
//		circ.setFill(Color.BLUE);

		Image image = new Image("icons/logo.png");
		ImageView imageView = new ImageView(image);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
		imageView.setScaleZ(0.3);
		root.add(imageView, 15, 14, 3, 3);
		imageView.setOnMouseClicked(event -> controllerEndTurn());
		// root.add(imageView, 7, 7);
//		root.getChildren().add(rect);
//		root.getChildren().add(line);
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

		for (int x = 0; x < 17; x++) {
			ColumnConstraints col = new ColumnConstraints();
			RowConstraints row = new RowConstraints();
			col.setPercentWidth(100);
			row.setPercentHeight(100);
			col.setHalignment(HPos.CENTER);
			row.setValignment(VPos.CENTER);
			root.getColumnConstraints().add(col);
			root.getRowConstraints().add(row);
			for (int y = 0; y < 15 && x < 15; y++) {
				Text text = new Text("Cell " + " " + x + " " + y);
//				root.add(text, y, x);
				if (Game.map[x][y] == null)
					return;
				if (Game.map[x][y] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero) {
						if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Medic) {
							Image medicImage = new Image("icons/medicImage.png");
							ImageView medicImageView = new ImageView(medicImage);
							medicImageView.setScaleX(0.1);
							medicImageView.setScaleY(0.1);
							root.add(medicImageView, y, x);

							Text text1 = new Text("Image " + " " );
//							root.add(text1, x, y);

						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Fighter) {
							Image fighterImage = new Image("icons/fighterImage.png");
							ImageView fighterImageView = new ImageView(fighterImage);
							fighterImageView.setScaleX(0.1);
							fighterImageView.setScaleY(0.1);
							root.add(fighterImageView, y, x);
						} else if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Explorer) {
//							medicImageView.setScaleX(0.1);
//							medicImageView.setScaleY(0.1);
//							root.add(medicImageView, y, x);
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
//						Image vaccineImage = new Image("icons/vaccineImage.png");
//						ImageView vaccineImageView = new ImageView(vaccineImage);
//						root.getChildren().add(vaccineImageView);
					} else if (((CollectibleCell) Game.map[x][y]).getCollectible() instanceof Supply) {
//						Image supplyImage = new Image("icons/supplyImage.png");
//						ImageView supplyImageView = new ImageView(supplyImage);
//						root.getChildren().add(supplyImageView);
					}
				}

////				Button btn = new Button("Cell\n" + "row: " + x + "\ncol: " + y);
//				Button btn = new Button();
//				btn.setStyle("color:#FFFF;");
////				btn.setPrefWidth(100);
////				btn.setPrefHeight(80);
////				root.add(btn, x, y);
			}
//			col.setFillWidth(true);
//			row.setFillHeight(true);
		}
	}

	private void controllerEndTurn() {
		Game.endTurn();
		updateMap();
	}
}
