package necro;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy extends GameObject {
	public int hp, speed, strategy, damage, xpdrop, enemynum, cd, attackspeed, droprate;
	public double rotationRequired, step;
	Image image;
	Random random;
	HashMap<Integer, HashMap<String, String>> enemyStats;
	public Enemy(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int enemynum, int hp, int explode, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.cd = 0;
		this.random = new Random();
		this.enemynum = enemynum;
		enemyStats = new HashMap<Integer, HashMap<String, String>>();
		enemyStats.put(0, new HashMap<String, String>());
		enemyStats.get(0).put("image",  "enemy1.png");
		enemyStats.get(0).put("hp",  "8");
		enemyStats.get(0).put("speed",  "3");
		enemyStats.get(0).put("strategy",  "0");
		enemyStats.get(0).put("damage",  "5");
		enemyStats.get(0).put("attackspeed",  "50");
		enemyStats.get(0).put("xpdrop",  "1");
		enemyStats.get(0).put("droprate",  "20");
		enemyStats.put(1, new HashMap<String, String>());
		enemyStats.get(1).put("image",  "enemy2.png");
		enemyStats.get(1).put("hp",  "12");
		enemyStats.get(1).put("speed",  "3");
		enemyStats.get(1).put("strategy",  "0");
		enemyStats.get(1).put("damage",  "4");
		enemyStats.get(1).put("attackspeed",  "20");
		enemyStats.get(1).put("xpdrop",  "2");
		enemyStats.get(1).put("droprate",  "30");
		enemyStats.put(2, new HashMap<String, String>());
		enemyStats.get(2).put("image",  "enemy3.png");
		enemyStats.get(2).put("hp",  "16");
		enemyStats.get(2).put("speed",  "3");
		enemyStats.get(2).put("strategy",  "0");
		enemyStats.get(2).put("damage",  "10");
		enemyStats.get(2).put("attackspeed",  "50");
		enemyStats.get(2).put("xpdrop",  "3");
		enemyStats.get(2).put("droprate",  "40");
		this.hp = Integer.parseInt(enemyStats.get(enemynum).get("hp"));
		this.speed = Integer.parseInt(enemyStats.get(enemynum).get("speed"));
		this.strategy = Integer.parseInt(enemyStats.get(enemynum).get("strategy"));
		this.damage = Integer.parseInt(enemyStats.get(enemynum).get("damage"));
		this.xpdrop = Integer.parseInt(enemyStats.get(enemynum).get("xpdrop"));
		this.attackspeed = Integer.parseInt(enemyStats.get(enemynum).get("attackspeed"));
		this.droprate = Integer.parseInt(enemyStats.get(enemynum).get("droprate"));
		BufferedImage readImage = null;
		try {
			readImage = ImageIO.read(new File("res/" + enemyStats.get(enemynum).get("image")));
			image = ImageIO.read(new File("res/" + enemyStats.get(enemynum).get("image")));
			this.imgHeight = readImage.getHeight() + 2;
		    this.imgWidth = readImage.getWidth() + 2;
		} catch (IOException e) {
			image = null;
		}
	}

	public void tick(Game game) {
		this.step += 0.1;
		if(this.cd > 0) {
			cd -= 1;
		}
		if(this.strategy == 0){
			double dist = Math.sqrt((this.x-game.player.x)*(this.x-game.player.x) + (this.y-game.player.y)*(this.y-game.player.y));
			this.velX = (int) (-this.speed * ((this.x-game.player.x)/dist));
			this.velY = (int) (-this.speed * ((this.y-game.player.y)/dist));
		}
		
		Rectangle r = new Rectangle(this.x, this.y, image.getWidth(null), image.getHeight(null));
		Rectangle p = new Rectangle(game.player.x, game.player.y, game.player.imgWidth, game.player.imgHeight);
		
		if(p.intersects(r)) {
			if(this.cd <= 0) {
				game.player.mana -= this.damage;
				this.cd = this.attackspeed;
				int o = this.random.nextInt(3) + 1;
				AudioPlayer.load();
				AudioPlayer.getSound("hurt" + o).play();
			}
		}
		else {
			this.x += this.velX;
			this.y += this.velY;
		}
		if(this.hp <= 0){
			game.player.xp += this.xpdrop;
			game.player.mana += 2*Integer.parseInt(enemyStats.get(enemynum).get("hp"));
			if(this.random.nextInt(100) < Integer.parseInt(enemyStats.get(enemynum).get("droprate"))) {
				game.handler.addObject(new Item(this.x, this.y, 0, 0, 0, 0, 0, 0, 0, true, ID.Item));
			}
			game.handler.objects.remove(this);
		}
		
		for(int i=0;i<game.handler.objects.size();i+=1){
			GameObject tempObject = game.handler.objects.get(i);
			if(tempObject.id == ID.Bullet) {
				Rectangle c = new Rectangle(this.x, this.y, this.imgWidth, this.imgHeight);
				Rectangle d = new Rectangle(tempObject.x, tempObject.y, tempObject.imgWidth, tempObject.imgHeight);
				if(c.intersects(d)){
					((Bullet) game.handler.objects.get(i)).hit(this);
				}
			}
		}
	}

	public void render(Graphics g2d, Game game) {
		if(this.x + this.imgWidth > game.camx && this.x < game.camx + game.VIEW_WIDTH && this.y + this.imgHeight > game.camy && this.y < game.camy + game.VIEW_HEIGHT){
			/*s
			Graphics2D g = (Graphics2D) g2d;
			rotationRequired = (1.5*Math.PI+Math.atan(velY/(velX + 0.01)));
			double locationX = image.getWidth(null) / 2;
			double locationY = image.getHeight(null) / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter((BufferedImage) image, null), this.x, this.y, null);
			*/
			Graphics2D g = (Graphics2D) g2d;
			float xDistance = game.player.x - this.x;
			float yDistance = game.player.y - this.y;
			double rotationAngle = Math.atan2(yDistance, xDistance);
			g.rotate(rotationAngle, this.x + this.imgWidth/2, this.y + this.imgHeight/2);
			g.drawImage(image, this.x, this.y, game);
			g.rotate(-rotationAngle, this.x + this.imgWidth/2, this.y + this.imgHeight/2);
		}
	}
}
