package necro;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Explode extends GameObject {
	public int time;
	public int count = 0;
	Game game;
	ArrayList<Enemy> hit;
	public Explode(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int hp, int damage, int time, int explode, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.time = time;
		this.damage = damage;
		this.hit = new ArrayList<Enemy>();
		Random random = new Random();
		int r = random.nextInt(1) + 1;
		AudioPlayer.load();
		AudioPlayer.getSound("explosion" + r).play();
	}

	public void tick(Game game) {
		this.game = game;
		count += time/50;
		if(count >= time) {
			game.handler.removeObject(this);
		}
		for(int i=0; i < this.game.handler.objects.size(); i++) {
			boolean fail = false;
			GameObject tempObject = this.game.handler.objects.get(i);
			
			if(tempObject.id == ID.Enemy) {
				
				Rectangle test = new Rectangle(this.x - count/2, this.y - count/2, count, count);
				Rectangle tester = new Rectangle(tempObject.x, tempObject.y, tempObject.imgWidth, tempObject.imgHeight);
				
				if(test.intersects(tester)) {
					for(int t=0; t<this.hit.size(); t++) {
						if(this.hit.get(t) == tempObject) {
							fail = true;
						}
					}
					if(!fail) {
						((Enemy) this.game.handler.objects.get(i)).hp -= this.damage;
						this.hit.add((Enemy) tempObject);
						System.out.println(this.game.handler.objects.get(i).hp);
					}
				}
			}
		}
	}

	public void render(Graphics g, Game game) {
		Color myColor = new Color(255, 0, 0, 110);
		g.setColor(myColor);
		g.fillOval((int) (this.x - count/2), (int) (this.y - count/2), count, count);
	}
}
