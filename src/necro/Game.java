package necro;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = -843414644064382358L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	ArrayList<ArrayList<Integer>> rooms;
	ArrayList<Item> inventory;
	private Thread thread;
	private boolean running = false;
	public boolean menu = false;
	public int WIDTH = 5000, HEIGHT = 4000, VIEW_WIDTH = (int) screenSize.getWidth(), VIEW_HEIGHT = (int) screenSize.getHeight();
	public int camx, camy, offsetMaxX, offsetMaxY, offsetMinX, offsetMinY, floor, m, roomNum;
	public Random random;
	public Handler handler;
	public MouseInput mouseInput;
	public KeyInput keyInput;
	public Player player;
	
	private void tick() {
		if(!menu) {
			if(this.player.mana <= 0) {
				menu = true;
			}
			this.handler.tick(this);
			if(random.nextInt(9)==1) {
				int spawnx = random.nextInt(WIDTH);
				int spawny = random.nextInt(HEIGHT);
				boolean fail = true;
				for(int i=0; i<this.rooms.size(); i++) {
					fail = true;
						
					Rectangle test = new Rectangle(spawnx, spawny, 256, 256);
					Rectangle room = new Rectangle(this.rooms.get(i).get(0), this.rooms.get(i).get(1), this.rooms.get(i).get(2)*64, this.rooms.get(i).get(3)*64);
					
					if(test.intersects(room)) {
						fail = false;
					}
				}
				if(!fail) {
					this.handler.addObject(new Enemy(spawnx + random.nextInt(3000) - 1500, spawny + random.nextInt(3000) - 1500, 0, 0, 0, 0, 0, 0, 0, 0, ID.Enemy));
					this.handler.addObject(new Enemy(spawnx + random.nextInt(3000) - 1500, spawny + random.nextInt(3000) - 1500, 0, 0, 0, 0, 0, 0, 0, 0, ID.Enemy));
				}
			}
			this.mouseInput.tick(this);
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		offsetMaxX = WIDTH - VIEW_WIDTH;
		offsetMaxY = HEIGHT - VIEW_HEIGHT;
		offsetMinX = 0;
		offsetMinY = 0;
		
		camx = this.player.x - VIEW_WIDTH/2;
		camy = this.player.y - VIEW_HEIGHT/2;
		
		if(camx > offsetMaxX) {
		    camx = offsetMaxX;
		}
		else if(camx < offsetMinX) {
		    camx = offsetMinX;
		}
		
		if(camy > offsetMaxY) {
		    camy = offsetMaxY;
		}
		else if(camy < offsetMinY) {
		    camy = offsetMinY;
		}
		
		g.translate(-camx, -camy);
		
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i=0; i<this.rooms.size(); i++) {
			g.setColor(Color.black);
			g.fillRect(this.rooms.get(i).get(0), this.rooms.get(i).get(1), 64 + this.rooms.get(i).get(2)*64, 64 + this.rooms.get(i).get(3)*64);
		}
		
		this.handler.render(g, this);
		
		g.setColor(Color.white);
		g.fillRect(camx + 80, camy + 990, 400, 32);
		Graphics2D g2 = (Graphics2D) g;
		double thickness = 5;
		Stroke oldStroke = g2.getStroke();
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) thickness));
		g2.drawRect(camx + 80, camy + 990, 400, 32);
		g.setColor(Color.blue);
		g.fillRect(camx + 83, camy + 993, (int) (396*(this.player.mana/100)), 27);
		
		if(menu && this.player.mana > 0) {
			g.setColor(Color.darkGray);
			g.fillRect(camx + 100, camy + 100, VIEW_WIDTH - 200, VIEW_HEIGHT - 200);
			thickness = 5;
			oldStroke = g2.getStroke();
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke((float) thickness));
			g2.drawRect(camx + 100, camy + 100, VIEW_WIDTH - 200, VIEW_HEIGHT - 200);
			g2.setStroke(oldStroke);
			g.setColor(Color.white);
			g.fillRect(camx + 200, camy + 180, 500, 700);
			BufferedImage readImage = null;
			BufferedImage readImage1 = null;
			BufferedImage readImage2 = null;
			BufferedImage readImage3 = null;
			BufferedImage nextImage = null;
			try {
			    readImage = ImageIO.read(new File("res/profile.png"));
			} catch (Exception e) {
			    readImage = null;
			}
			g.drawImage(readImage, camx + 200, camy + 200, this);
			g.setColor(Color.GRAY);
			g.fillRect(camx + 850, camy + 180, 800, 250);
			g.setColor(Color.black);
			g.fillRect(camx + 1170, camy + 180, 135, 30);
			g.setColor(Color.white);
			Font f = new Font("f", Font.PLAIN, 20);
			g.setFont(f);
			g.drawString("Perk Points: " + this.player.perks, camx + 1175, camy + 203);
			thickness = 5;
			oldStroke = g2.getStroke();
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke((float) thickness));
			g2.drawRect(camx + 850, camy + 180, 800, 250);
			g2.drawRect(camx + 200, camy + 180, 500, 720);
			try {
			    readImage = ImageIO.read(new File("res/gold.png"));
			} catch (Exception e) {
			    readImage = null;
			}
			g.drawImage(readImage, camx + 780, camy + 490, this);
			g.setColor(Color.yellow);
			Font k = new Font("k", Font.PLAIN, 34);
			g.setFont(k);
			g.drawString("" + this.player.gold, camx + 855, camy + 533);
			g.setColor(Color.white);
			g.fillRect(camx + 850, camy + 600, 64, 64);
			g.fillRect(camx + 850, camy + 684, 64, 64);
			g.fillRect(camx + 766, camy + 684, 64, 64);
			g.fillRect(camx + 934, camy + 684, 64, 64);
			g.fillRect(camx + 850, camy + 768, 64, 64);
			g.drawString("Inventory", camx + 1340, camy + 533);
			g.setColor(Color.gray);
			g.fillRect(camx + 1064, camy + 550, 710, 390);
			g.setColor(Color.black);
			g.drawRect(camx + 1064, camy + 550, 710, 390);
			/*
			if(this.inventory.size() == 0) {
				for(int i=0; i<3; i++) {
					this.inventory.add(new Item(0, 0, 0, 0, 0, 0, 0, 0, 0, false, ID.Item));
				}
			}
			*/
			for(int i=0; i<this.inventory.size(); i++) {
				if(i <= 10) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + i*64, camy + 553, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + i*64, camy + 553, 64, 64);
				} else if(i <= 21) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + (i-11)*64, camy + 618, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + (i-11)*64, camy + 618, 64, 64);
				} else if(i <= 32) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + (i-22)*64, camy + 682, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + (i-22)*64, camy + 682, 64, 64);
				} else if(i <= 43) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + (i-33)*64, camy + 746, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + (i-33)*64, camy + 746, 64, 64);
				} else if(i <= 54) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + (i-44)*64, camy + 810, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + (i-44)*64, camy + 810, 64, 64);
				} else if(i <= 65) {
					g.drawImage(this.inventory.get(i).readImage, camx + 1067 + (i-55)*64, camy + 874, this);
					g.setColor(Color.black);
					g.drawRect(camx + 1067 + (i-55)*64, camy + 874, 64, 64);
				}
			}
			if(this.player.hat != null) {
				readImage = this.player.hat.readImage;
			} else {
				try {
				    readImage = ImageIO.read(new File("res/hat.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(readImage, camx + 850, camy + 600, this);
			if(this.player.ring != null) {
				readImage = this.player.ring.readImage;
			} else {
				try {
				    readImage = ImageIO.read(new File("res/ring.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(readImage, camx + 766, camy + 684, this);
			if(this.player.staff != null) {
				readImage = this.player.staff.readImage;
			} else {
				try {
				    readImage = ImageIO.read(new File("res/staff.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(readImage, camx + 934, camy + 684, this);
			if(this.player.shoes != null) {
				readImage = this.player.shoes.readImage;
			} else {
				try {
				    readImage = ImageIO.read(new File("res/shoes.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(readImage, camx + 850, camy + 768, this);
			if(this.player.robe != null) {
				readImage = this.player.robe.readImage;
			} else {
				g.drawImage(readImage, camx + 850, camy + 684, this);
				try {
				    readImage = ImageIO.read(new File("res/robe.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(readImage, camx + 850, camy + 684, this);
			g.setColor(Color.black);
			g.drawRect(camx + 850, camy + 600, 64, 64);
			g.drawRect(camx + 850, camy + 684, 64, 64);
			g.drawRect(camx + 766, camy + 684, 64, 64);
			g.drawRect(camx + 934, camy + 684, 64, 64);
			g.drawRect(camx + 850, camy + 768, 64, 64);
			//g2.setStroke(oldStroke);
			g.setColor(Color.white);
			g.fillRect(camx + 1700, camy + 200, 50, 210);
			g2.setColor(Color.black);
			g2.drawRect(camx + 1700, camy + 200, 50, 210);
			g.setColor(Color.yellow);
			g.fillRect(camx + 1703, (int) (camy + 410 - 206*(this.player.xp/this.player.xpneeded)), 45,(int) (206*(this.player.xp/this.player.xpneeded)));
			if(this.player.level == 1) {
				try {
				    readImage = ImageIO.read(new File("res/1.png"));
				    nextImage = ImageIO.read(new File("res/2.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 2) {
				try {
				    readImage = ImageIO.read(new File("res/2.png"));
				    nextImage = ImageIO.read(new File("res/3.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 3) {
				try {
				    readImage = ImageIO.read(new File("res/3.png"));
				    nextImage = ImageIO.read(new File("res/4.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 4) {
				try {
				    readImage = ImageIO.read(new File("res/4.png"));
				    nextImage = ImageIO.read(new File("res/5.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 5) {
				try {
				    readImage = ImageIO.read(new File("res/5.png"));
				    nextImage = ImageIO.read(new File("res/6.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 6) {
				try {
				    readImage = ImageIO.read(new File("res/6.png"));
				    nextImage = ImageIO.read(new File("res/7.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 7) {
				try {
				    readImage = ImageIO.read(new File("res/7.png"));
				    nextImage = ImageIO.read(new File("res/8.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level == 8) {
				try {
				    readImage = ImageIO.read(new File("res/8.png"));
				    nextImage = ImageIO.read(new File("res/9.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			} else if(this.player.level >= 9) {
				try {
				    readImage = ImageIO.read(new File("res/9.png"));
				    nextImage = ImageIO.read(new File("res/9.png"));
				} catch (Exception e) {
				    readImage = null;
				}
			}
			g.drawImage(nextImage, camx + 1693, camy + 130, this);
			g.drawImage(readImage, camx + 1693, camy + 417, this);
			try {
			    readImage = ImageIO.read(new File("res/Levelup1.png"));
			    readImage1 = ImageIO.read(new File("res/Levelup2.png"));
			    readImage2 = ImageIO.read(new File("res/Levelup3.png"));
			    readImage3 = ImageIO.read(new File("res/Levelup4.png"));
			} catch (Exception e) {
			    readImage = null;
			}
			g.drawImage(readImage, camx + 900, camy + 230, this);
			g.drawImage(readImage1, camx + 1080, camy + 230, this);
			g.drawImage(readImage2, camx + 1260, camy + 230, this);
			g.drawImage(readImage3, camx + 1440, camy + 230, this);
			g2.setColor(Color.black);
			g2.drawRect(camx + 900, camy + 230, 150, 150);
			g2.drawRect(camx + 1080, camy + 230, 150, 150);
			g2.drawRect(camx + 1260, camy + 230, 150, 150);
			g2.drawRect(camx + 1440, camy + 230, 150, 150);
		} else if(menu && this.player.mana <= 0) {
			Font r = new Font("r", Font.PLAIN, 128);
			g.setFont(r);
			g.setColor(Color.white);
			g.drawString("YOU DIED", camx + 700, camy + 500);
			Font p = new Font("p", Font.PLAIN, 64);
			g.setFont(p);
			g.drawString("PRESS ~ TO RESTART", camx + 680, camy + 630);
		}
		
		this.mouseInput.render(g, this);
		
		g.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restart() {
		ArrayList<Item> newinventory = new ArrayList<Item>();
		this.player = new Player(100, 100, 0, 0, 4, 0, 0, 100, 0, ID.Player);
		this.inventory = newinventory;
		menu = false;
		generateFloor(this.player);
	}
	
	public void generateFloor(Player player) {
		LinkedList<GameObject> objects = new LinkedList<GameObject>();
		this.handler.objects = objects;
		this.rooms = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<roomNum*2; i++) {
			ArrayList<Integer> d = new ArrayList<Integer>();
			this.rooms.add(d);
			for(int t=0; t<=3; t++){
				this.rooms.get(i).add(0);
			}
		}
		int thisRoomX = 1024, thisRoomY = 1024, lastRoomX = 1024, lastRoomY = 1024;
		for(int i=0; i<roomNum; i++) {
			boolean pass = true;
			
			int roomHeight = random.nextInt(2) + floor + 7, roomWidth = random.nextInt(2) + floor + 8;
			
			while(pass) {
				if(random.nextInt(11) <= 5) {
					if(random.nextInt(11) <= 5) {
						thisRoomX = lastRoomX + 64*(random.nextInt(7) + 13);
					}
					else {
						thisRoomX = lastRoomX + 64*(random.nextInt(7) - 15);
					}
				}
				else if(random.nextInt(11) <= 5) {
					thisRoomY = lastRoomY + 64*(random.nextInt(6) + 13);
				}
				else {
					thisRoomY = lastRoomY + 64*(random.nextInt(6) - 15);
				}
				if(thisRoomY + roomHeight*64 + 128 < HEIGHT && thisRoomY > 0 && thisRoomX + roomWidth*64 + 128 < WIDTH && thisRoomX > 0) {
					pass = false;
				}
			}
			
			lastRoomX = thisRoomX;
			lastRoomY = thisRoomY;
			
			this.rooms.get(i).set(0, thisRoomX);
			this.rooms.get(i).set(1, thisRoomY);
			this.rooms.get(i).set(2, roomWidth);
			this.rooms.get(i).set(3, roomHeight);
			
			if(random.nextInt(11)<=2 && i > 0) {
				int enemynum = random.nextInt(3);
				this.handler.addObject(new Enemy(this.rooms.get(i).get(0) + random.nextInt(200) - 100, this.rooms.get(i).get(1) + random.nextInt(200) - 100, 0, 0, 0, 0, 0, enemynum, 0, 0, ID.Enemy));
			}
		}
		
		this.player.x = this.rooms.get(0).get(0) + this.rooms.get(0).get(2)*32;
		this.player.y = this.rooms.get(0).get(1) + this.rooms.get(0).get(3)*32;
		
		for(int i=0; i<this.rooms.size()/2; i++) {
			
			if(i > 0) {
				if(!this.rooms.get(i).get(0).equals(this.rooms.get(i-1).get(0))) {
					if(this.rooms.get(i).get(0) > this.rooms.get(i-1).get(0)) {
						
						this.rooms.get(i+roomNum).set(0, this.rooms.get(i-1).get(0) + this.rooms.get(i-1).get(2));
						this.rooms.get(i+roomNum).set(1, this.rooms.get(i-1).get(1) + 128);
						this.rooms.get(i+roomNum).set(2, (this.rooms.get(i).get(0) - this.rooms.get(i-1).get(0))/64);
						this.rooms.get(i+roomNum).set(3, 3+random.nextInt(2));
						
					}
					else {
						
						this.rooms.get(i+roomNum).set(0, this.rooms.get(i).get(0) + this.rooms.get(i).get(2));
						this.rooms.get(i+roomNum).set(1, this.rooms.get(i).get(1) + 128);
						this.rooms.get(i+roomNum).set(2, (this.rooms.get(i-1).get(0) - this.rooms.get(i).get(0))/64);
						this.rooms.get(i+roomNum).set(3, 3+random.nextInt(2));
						
					}
				}
				else {
					if(this.rooms.get(i).get(1) > this.rooms.get(i-1).get(1)) {
						
						this.rooms.get(i+roomNum).set(0, this.rooms.get(i-1).get(0) + 128);
						this.rooms.get(i+roomNum).set(1, this.rooms.get(i-1).get(1) + this.rooms.get(i-1).get(3));
						this.rooms.get(i+roomNum).set(2, 3+random.nextInt(2));
						this.rooms.get(i+roomNum).set(3, (this.rooms.get(i).get(1) - this.rooms.get(i-1).get(1))/64);
						
					}
					else {
						
						this.rooms.get(i+roomNum).set(0, this.rooms.get(i).get(0) + 128);
						this.rooms.get(i+roomNum).set(1, this.rooms.get(i).get(1) + this.rooms.get(i).get(3));
						this.rooms.get(i+roomNum).set(2, 3+random.nextInt(2));
						this.rooms.get(i+roomNum).set(3, (this.rooms.get(i-1).get(1) - this.rooms.get(i).get(1))/64);
						
					}
				}
			}
		}
		for(int i=0; i<this.rooms.size(); i++) {
			for(int w=-1; w<=this.rooms.get(i).get(2)+1; w++) {
				for(int h=-1; h<=this.rooms.get(i).get(3)+1; h++) {
					if((w==-1 || w==this.rooms.get(i).get(2)+1) || (h==-1 || h==this.rooms.get(i).get(3)+1)) {
						boolean fail = false;
						for(int t=0; t<this.rooms.size(); t++) {
								
							Rectangle test = new Rectangle(this.rooms.get(i).get(0) + w*64 - 2, this.rooms.get(i).get(1) + h*64 - 2, 1, 1);
							Rectangle room = new Rectangle(this.rooms.get(t).get(0), this.rooms.get(t).get(1), this.rooms.get(t).get(2)*64, this.rooms.get(t).get(3)*64);
							
							if(test.intersects(room)) {
								fail = true;
							}	
						}
						if(!fail) {
							this.handler.addObject(new Wall(this.rooms.get(i).get(0) + w*64, this.rooms.get(i).get(1) + h*64, 0, 0, 0, 0, 0, 0, 0, ID.Wall));
						}
					}
				}
			}
		}
		this.handler.addObject(this.player);
	}
	
	public Game() {
		ArrayList<Item> inventory = new ArrayList<Item>();
		this.inventory = inventory;
		Player player = new Player(100, 100, 0, 0, 4, 0, 0, 100, 0, ID.Player);
		this.player = player;
		floor = 1;
		random = new Random();
		this.handler = new Handler();
		roomNum = 2*(random.nextInt(2) + floor) + 12;
		generateFloor(this.player);
		new Window(WIDTH, HEIGHT, "Woohoo", this);
		this.addKeyListener(new KeyInput(this.handler, this));
		this.mouseInput = new MouseInput(this.handler, this);
		this.addMouseListener(this.mouseInput);
		this.addMouseMotionListener(this.mouseInput);
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames ++;
			if(System.currentTimeMillis() - timer > 200) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
