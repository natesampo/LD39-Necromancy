package necro;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;

public class Item extends GameObject {
	Image image;
	int imgNum, imgType, damage, costred, attackspeed, regen, movespeed, effects;
	String type;
	public BufferedImage readImage, groundImage;
	public boolean ground;
	public Item(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int hp, int explode, boolean ground, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.x = x;
		this.y = y;
		Random random = new Random();
		this.effects = random.nextInt(2) + 1;
		this.damage = 0;
		this.costred = 0;
		this.attackspeed = 0;
		this.regen = 0;
		this.movespeed = 0;
		this.ground = ground;
		for(int i=0; i<this.effects; i++) {
			int r = random.nextInt(4);
			if(r == 0) {
				if(this.damage != 0) {
					this.effects -= 1;
				}
				this.damage += random.nextInt(2) + 1;
			} else if(r == 1) {
				if(this.costred != 0) {
					this.effects -= 1;
				}
				this.costred += random.nextInt(9) + 5;
			} else if(r == 2) {
				if(this.attackspeed != 0) {
					this.effects -= 1;
				}
				this.attackspeed += random.nextInt(16) + 5;
			} else if(r == 3) {
				if(this.regen != 0) {
					this.effects -= 1;
				}
				this.regen += random.nextInt(10) + 1;
			}
		}
		
		imgNum = random.nextInt(5) + 1;
		imgType = random.nextInt(5) + 1;
		if(imgType == 1) {
			type = "hat";
		}
		if(imgType == 2) {
			type = "robe";
		}
		if(imgType == 3) {
			type = "ring";
		}
		if(imgType == 4) {
			type = "shoes";
			this.movespeed = random.nextInt(5) + 1;
			this.effects += 1;
		}
		if(imgType == 5) {
			type = "staff";
		}
		BufferedImage readImage = null;
		try {
			readImage = ImageIO.read(new File("res/" + type + imgNum + ".png"));
			groundImage = ImageIO.read(new File("res/" + type + imgNum + " - Copy.png"));
			this.imgHeight = readImage.getHeight() - 2;
		    this.imgWidth = readImage.getWidth() - 2;
		} catch (IOException e) {
			image = null;
		}
		this.readImage = readImage;
	}

	public void tick(Game game) {
		
	}

	public void render(Graphics g, Game game) {
		if(this.ground) {
			g.drawImage(this.groundImage, this.x, this.y, game);
		}
	}

}
