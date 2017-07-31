package necro;
import java.util.LinkedList;
import java.awt.Graphics;
public class Handler {
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public void tick(Game game) {
		for(int i=0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.tick(game);
		}
	}
	public void render(Graphics g, Game game) {
		for(int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.render(g, game);
		}
	}
	public void addObject(GameObject objects) {
		this.objects.add(objects);
	}
	public void removeObject(GameObject objects) {
		this.objects.remove(objects);
	}
}