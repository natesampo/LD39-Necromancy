package necro;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends GameObject {
	Image image;
	private Game game;
	public int damage, bulletspeed, cd, attackspeed, level, perks, shots, explode, gold;
	public double rotationRequired, xp, xpneeded, hp, mana, shotcost;
	public Item hat, robe, ring, shoes, staff;
	Bullet bullet;
	public Player(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int hp, int explode, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.velX = velX;
		this.velY = velY;
		this.speed = speed;
		this.hp = hp;
		this.xp = 0;
		this.damage = 2;
		this.bulletspeed = 10;
		this.attackspeed = 30;
		this.cd = 0;
		this.level = 1;
		this.perks = 1;
		this.shots = 1;
		this.explode = explode;
		this.xpneeded = (level*3) + 3;
		this.mana = 100;
		this.shotcost = 4;
		this.gold = 500;
		this.hat = null;
		this.robe = null;
		this.shoes = null;
		this.staff = null;
		this.ring = null;
		BufferedImage readImage = null;
		try {
			readImage = ImageIO.read(new File("res/player.png"));
			image = ImageIO.read(new File("res/player.png"));
			this.imgHeight = readImage.getHeight() + 2;
		    this.imgWidth = readImage.getWidth() + 2;
		} catch (IOException e) {
			image = null;
		}
	}
	
	public void fire(int mouse_x, int mouse_y) {
		if(this.cd==0 && this.mana >= this.shotcost) {
			for(int i=0; i<shots; i++) {
				Bullet bullet = new Bullet((int) (this.x + this.imgWidth/2), (int) (this.y + this.imgHeight/2), 0, 0, this.bulletspeed, 0, 0, mouse_x + 128*i, mouse_y + 128*i, this.game, 0, this.damage, this.explode, ID.Bullet);
				this.game.handler.addObject(bullet);
				int r = this.game.random.nextInt(6) + 1;
				AudioPlayer.load();
				AudioPlayer.getSound("shoot" + r).play();
			}
			this.cd = attackspeed;
			this.mana -= this.shotcost;
		}
	}

	public void tick(Game game) {
		if(!game.menu) {
			if(this.mana > 100) {
				this.mana = 100;
			}
			this.mana -= 0.05;
		}
		this.xpneeded = (level*3) + 2;
		if(this.xp >= this.xpneeded) {
			this.perks += 1;
			this.level += 1;
			this.xp = 0;
		}
		if(this.cd > 0) {
			this.cd-=1;
		}
		this.game = game;
		boolean collide = false;
		for(int i=0; i < game.handler.objects.size(); i++) {
			GameObject wallObject = game.handler.objects.get(i);
			if(wallObject.id == ID.Wall && this.collision(velX, velY, this, wallObject)) {
				collide = true;
			}
			if(wallObject.id == ID.Item && this.collision(velX, velY, this, wallObject)) {
				AudioPlayer.load();
				AudioPlayer.getSound("pickup1").play();
				game.inventory.add((Item) wallObject);
				game.handler.objects.remove(wallObject);
			}
		}
		if(collide==false) {
			this.x += velX;
			this.y += velY;
		}
		/*
		for(int i=0; i < game.handler.objects.size(); i++) {
			GameObject wallObject = game.handler.objects.get(i);
			if(this.collision(velX, velY, this, wallObject)) {
				clamp(this, wallObject);
			}
		}
		*/
	}

	public void render(Graphics g2d, Game game) {
		this.game = game;
		//g.drawImage(image, this.x, this.y, game);
		Graphics2D g = (Graphics2D) g2d;
		float xDistance = this.game.mouseInput.mouse_x - this.x + this.game.camx;
		float yDistance = this.game.mouseInput.mouse_y - this.y + this.game.camy;
		double rotationAngle = Math.atan2(yDistance, xDistance);
		g.rotate(rotationAngle, this.x + this.imgWidth/2, this.y + this.imgHeight/2);
		g.drawImage(image, this.x, this.y, this.game);
		g.rotate(-rotationAngle, this.x + this.imgWidth/2, this.y + this.imgHeight/2);
	}	
}
