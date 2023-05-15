package engine;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GamePlay extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStageInit(primaryStage);
		Group root = new Group();
		Scene scene1 = new Scene(root, Color.DARKORCHID);
		Text text = new Text();
		text.setText("Welcome to the Last of Us - Legacy");
		text.setFont(Font.font("Arial",20));
		text.setX(630);
		text.setY(350);
		text.setFill(Color.ANTIQUEWHITE);
		
		Line line = new Line();
		line.setStartX(100);
		line.setEndX(1000);
		line.setStartY(100);
		line.setEndY(600);
		line.setStrokeWidth(5);
		line.setStroke(Color.DARKORANGE);
		
		Rectangle rect = new Rectangle();
		rect.setX(200);
		rect.setY(200);
		rect.setWidth(100);
		rect.setHeight(300);
//		rect.setStrokeWidth(5);
//		rect.setStroke(Color.DARKSLATEBLUE);
		
		Circle circ = new Circle();
		circ.setRadius(10);
		circ.setCenterX(200);
		circ.setCenterY(200);
		circ.setFill(Color.BLUE);
		
		Image image = new Image("icons/logo.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(390);
		imageView.setY(-300);
		imageView.setScaleX(0.3);
		imageView.setScaleY(0.3);
		imageView.setScaleZ(0.3);
		
		root.getChildren().add(imageView);
		root.getChildren().add(text);
//		root.getChildren().add(rect);
//		root.getChildren().add(line);
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

}
