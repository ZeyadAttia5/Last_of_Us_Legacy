package views;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class SoundLoader {
    private Map<String, Media> soundCache;

    public SoundLoader() {
        soundCache = new HashMap<>();
    }

    public Media getSound(String soundPath) {
        if (soundCache.containsKey(soundPath)) {
            return soundCache.get(soundPath);
        } else {
            Media sound = new Media(getClass().getResource(soundPath).toExternalForm());
            soundCache.put(soundPath, sound);
            return sound;
        }
    }
    public void removeSound(Media media) {
        soundCache.remove(media);
    }

    
    public void clearCache() {
        soundCache.clear();
    }

}
