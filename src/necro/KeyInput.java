package necro;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyInput extends KeyAdapter{
	private Game game;
	public int key, dead_key;
	public int upkey = 87, downkey = 83, leftkey = 65, rightkey = 68, menukey = 69, entkey = 10, restartkey = 192;
	public boolean pressed, collide;
	private Handler handler;
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == menukey || key == entkey || key == restartkey) {
			if(key == menukey && this.game.player.mana > 0) {
				this.game.menu = !this.game.menu;
			} else if(key == entkey && this.game.player.mana > 0) {
				this.game.generateFloor(this.game.player);
			} else if(key == restartkey) {
				this.game.restart();
			}
		} else {
			for(int i=0; i < this.handler.objects.size(); i++) {
				collide = false;
				GameObject tempObject = this.handler.objects.get(i);
				if(tempObject.id == ID.Player) {
					if(key == upkey) {
						for(int t=0; t < this.handler.objects.size(); t++) {
							GameObject wallObject = this.handler.objects.get(t);	
							if(wallObject.id == ID.Wall && tempObject.collision(0, -tempObject.speed, tempObject, wallObject)) {
								collide = true;
							}
						}
						if(!collide) {
							tempObject.setVelY(-tempObject.speed);
						}
					}
					if(key == downkey) {
						for(int t=0; t < this.handler.objects.size(); t++) {
							GameObject wallObject = this.handler.objects.get(t);	
							if(wallObject.id == ID.Wall && tempObject.collision(0, tempObject.speed, tempObject, wallObject)) {
								collide = true;
							}
						}
						if(!collide) {
							tempObject.setVelY(tempObject.speed);
						}
					}
					if(key == leftkey) {
						for(int t=0; t < this.handler.objects.size(); t++) {
							GameObject wallObject = this.handler.objects.get(t);	
							if(wallObject.id == ID.Wall && tempObject.collision(-tempObject.speed, 0, tempObject, wallObject)) {
								collide = true;
							}
						}
						if(!collide) {
							tempObject.setVelX(-tempObject.speed);
						}
					}
					if(key == rightkey) {
						for(int t=0; t < this.handler.objects.size(); t++) {
							GameObject wallObject = this.handler.objects.get(t);	
							if(wallObject.id == ID.Wall && tempObject.collision(tempObject.speed, 0, tempObject, wallObject)) {
								collide = true;
							}
						}
						if(!collide) {
							tempObject.setVelX(tempObject.speed);
						}
					}
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		this.key = e.getKeyCode();
		
		for(int i=0; i < this.handler.objects.size(); i++) {
			GameObject tempObject = this.handler.objects.get(i);
			if(tempObject.id == ID.Player) {
				
				if(key == upkey) tempObject.setVelY(0);
				if(key == downkey) tempObject.setVelY(0);
				if(key == leftkey) tempObject.setVelX(0);
				if(key == rightkey) tempObject.setVelX(0);
					
			}
		}
		/*
		if(this.pressed && (key != dead_key)){
			this.game.menu = false;
		}
		if(!this.pressed){
			this.dead_key = this.key;
			this.pressed = true;
		}
		*/
	}
	
	public KeyInput(Handler handler, Game game){
		this.game = game;
		this.handler = handler;
		//this.pressed = false;
	}
}