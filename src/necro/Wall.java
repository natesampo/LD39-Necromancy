package necro;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Wall extends GameObject {
	Image image;
	public Wall(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int hp, int explode, ID id) {
		super(x, y, velX, velY, speed, imgWidth, imgHeight, hp, explode, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.velX = velX;
		this.velY = velY;
		this.speed = speed;
		image = Toolkit.getDefaultToolkit().getImage("res/wall.png");
		BufferedImage readImage = null;
		try {
		    readImage = ImageIO.read(new File("res/wall.png"));
		    this.imgHeight = readImage.getHeight();
		    this.imgWidth = readImage.getWidth();
		} catch (Exception e) {
		    readImage = null;
		}
	}

	public void tick(Game game) {
		//this.x += velX;
		//this.y += velY;
	}

	public void render(Graphics g, Game game) {
		if(this.x + this.imgWidth > game.camx && this.x < game.camx + game.VIEW_WIDTH && this.y + this.imgHeight > game.camy && this.y < game.camy + game.VIEW_HEIGHT){
			g.drawImage(image, this.x, this.y, game);
		}
	}
	
}
