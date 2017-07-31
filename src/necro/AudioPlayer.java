package necro;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	public static void load() {
		try {
			soundMap.put("hurt1", new Sound("res/hurt1.wav"));
			soundMap.put("hurt2", new Sound("res/hurt2.wav"));
			soundMap.put("hurt3", new Sound("res/hurt3.wav"));
			soundMap.put("explosion1", new Sound("res/explosion1.wav"));
			soundMap.put("explosion2", new Sound("res/explosion2.wav"));
			soundMap.put("shoot1", new Sound("res/shoot1.wav"));
			soundMap.put("shoot2", new Sound("res/shoot2.wav"));
			soundMap.put("shoot3", new Sound("res/shoot3.wav"));
			soundMap.put("shoot4", new Sound("res/shoot4.wav"));
			soundMap.put("shoot5", new Sound("res/shoot5.wav"));
			soundMap.put("shoot6", new Sound("res/shoot6.wav"));
			soundMap.put("upgrade1", new Sound("res/upgrade1.wav"));
			soundMap.put("upgrade2", new Sound("res/upgrade2.wav"));
			soundMap.put("pickup1", new Sound("res/pickup1.wav"));
			musicMap.put("music", new Music("res/music.wav"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
