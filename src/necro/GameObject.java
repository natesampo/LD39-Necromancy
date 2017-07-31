package necro;
import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class GameObject {
	protected int x, y, velX, velY, speed, imgHeight, imgWidth, hp, explode, damage;
	protected ID id;
	public GameObject(int x, int y, int velX, int velY, int speed, int imgWidth, int imgHeight, int hp, int explode, ID id){
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.speed = speed;
		this.id = id;
		this.imgHeight = imgHeight;
		this.imgWidth = imgWidth;
	}
	
	public abstract void tick(Game game);
	public abstract void render(Graphics g, Game game);

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}
	
	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public boolean collision(int xdelta, int ydelta, GameObject self, GameObject other) {
		Rectangle play = new Rectangle(self.x + xdelta, self.y + ydelta, self.imgWidth, self.imgHeight);
		Rectangle wall = new Rectangle(other.x, other.y, other.imgWidth, other.imgHeight);
		return wall.intersects(play);
	}
}