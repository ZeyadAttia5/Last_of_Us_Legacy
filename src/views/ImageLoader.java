package views;

import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageLoader {

	private static HashMap<String, Image> imageCache = new HashMap<>();

	public static Image loadImage(String imagePath) {
		Image image = imageCache.get(imagePath);
		if (image == null) {
			image = new Image(imagePath);
			imageCache.put(imagePath, image);
		}
		return image;
	}
	public static void clearCache() {
	    imageCache.clear();
	}

}