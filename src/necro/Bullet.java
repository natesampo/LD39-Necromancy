package necro;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Bullet extends GameObject {
	int damage, speed, reload, thickness, bulletseq, mouse_x, mouse_y, explode;
	private Game game;
	HashMap<Integer, HashMap<String, String>> bulletStats;
	Image image;
	public Bullet(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int mouse_x, int mouse_y, Game game, int hp, int damage, int explode, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.velX = velX;
		this.velY = velY;
		this.speed = speed;
		this.game = game;
		this.mouse_x = mouse_x;
		this.mouse_y = mouse_y;
		this.damage = damage;
		this.explode = explode;
		double spd = Math.sqrt((this.x-this.game.camx-this.mouse_x)*(this.x-this.game.camx-this.mouse_x) + (this.y-this.game.camy-this.mouse_y)*(this.y-this.game.camy-this.mouse_y));
		int xspd = (int) (-this.speed * ((this.x-this.game.camx-this.mouse_x)/spd));
		int yspd = (int) (-this.speed * ((this.y-this.game.camy-this.mouse_y)/spd));
		this.velX = xspd;
		this.velY = yspd;
		image = Toolkit.getDefaultToolkit().getImage("res/bullet.png");
		BufferedImage readImage = null;
		try {
		    readImage = ImageIO.read(new File("res/bullet.png"));
		    this.imgHeight = readImage.getHeight() + 2;
		    this.imgWidth = readImage.getWidth() + 2;
		} catch (Exception e) {
		    readImage = null;
		}
		/*
		bulletStats = new HashMap<Integer, HashMap<String, String>>();
		bulletStats.put(0, new HashMap<String, String>());
		bulletStats.get(0).put("damage",  "1");
		bulletStats.get(0).put("speed",  "20");
		bulletStats.get(0).put("reload",  "20");
		bulletStats.get(0).put("thickness",  "5");
		this.damage = Integer.parseInt(bulletStats.get(bulletnum).get("damage"));
		this.speed = Integer.parseInt(bulletStats.get(bulletnum).get("speed"));
		this.reload = Integer.parseInt(bulletStats.get(bulletnum).get("reload"));
		this.thickness = Integer.parseInt(bulletStats.get(bulletnum).get("thickness"));
		this.bulletseq = bulletseq;
		if(this.bulletseq == 1){
			double spd = Math.sqrt((this.x-game.mouseInput.mouse_x)*(this.x-game.mouseInput.mouse_x) + (this.y-game.mouseInput.mouse_y)*(this.y-game.mouseInput.mouse_y));
			int xspd = (int) (-this.speed * ((this.x-game.mouseInput.mouse_x)/spd));
			int yspd = (int) (-this.speed * ((this.y-game.mouseInput.mouse_y)/spd));
			this.velX = xspd;
			this.velY = yspd;
		} else if(this.bulletseq == 2){
			double spd = Math.sqrt((this.x-game.mouseInput.mouse_x-100)*(this.x-game.mouseInput.mouse_x-100) + (this.y-game.mouseInput.mouse_y-100)*(this.y-game.mouseInput.mouse_y-100));
			int xspd = (int) (-this.speed * ((this.x-game.mouseInput.mouse_x-100)/spd));
			int yspd = (int) (-this.speed * ((this.y-game.mouseInput.mouse_y-100)/spd));
			this.velX = xspd;
			this.velY = yspd;
		} else if(this.bulletseq == 3){
			double spd = Math.sqrt((this.x-game.mouseInput.mouse_x+100)*(this.x-game.mouseInput.mouse_x+100) + (this.y-game.mouseInput.mouse_y+100)*(this.y-game.mouseInput.mouse_y+100));
			int xspd = (int) (-this.speed * ((this.x-game.mouseInput.mouse_x+100)/spd));
			int yspd = (int) (-this.speed * ((this.y-game.mouseInput.mouse_y+100)/spd));
			this.velX = xspd;
			this.velY = yspd;
		}
		*/
	}
	
	public void hit(Enemy enemy) {
		if(this.explode > 0) {
			enemy.hp -= (int) (this.damage/2);
			this.game.handler.addObject(new Explode(this.x + this.imgWidth/2, this.y + this.imgHeight/2, 0, 0, 0, 0, 0, 0, (int) (this.damage *(0.5*this.explode)), this.explode*100, 0, ID.Explode));
		} else {
			enemy.hp -= this.damage;
		}
		this.game.handler.objects.remove(this);
	}

	public void tick(Game game) {
		this.game = game;
		this.x += this.velX;
		this.y += this.velY;
		for(int i=0; i < this.game.handler.objects.size(); i++) {
			GameObject tempObject = this.game.handler.objects.get(i);
			
			Rectangle test = new Rectangle(this.x, this.y, this.imgWidth, this.imgHeight);
			Rectangle tester = new Rectangle(tempObject.x, tempObject.y, tempObject.imgWidth, tempObject.imgHeight);
			
			if(test.intersects(tester)) {
				if(tempObject.id == ID.Wall) {
					if(this.explode > 0) {
						this.game.handler.addObject(new Explode(this.x + this.imgWidth/2, this.y + this.imgHeight/2, 0, 0, 0, 0, 0, 0, (int) (this.damage *(0.5*this.explode)), this.explode*100, 0, ID.Explode));
					}
					this.game.handler.objects.remove(this);
				}
				/*
				if(tempObject.id == ID.Enemy) {
					System.out.println(tempObject.id);
					tempObject.hp -= this.damage;
					this.game.handler.objects.remove(this);
				}
				*/
			}
		}
		if(this.x > game.WIDTH || this.x < 0 || this.y > game.HEIGHT || this.y < 0){
			this.game.handler.objects.remove(this);
		}
	}

	public void render(Graphics g, Game game) {
		if(this.x + this.imgWidth > game.camx && this.x < game.camx + game.VIEW_WIDTH && this.y + this.imgHeight > game.camy && this.y < game.camy + game.VIEW_HEIGHT){
			g.drawImage(image, this.x, this.y, game);
		}
	}

}