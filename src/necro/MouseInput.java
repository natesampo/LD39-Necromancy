package necro;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MouseInput extends MouseAdapter {
	int mouse_x, mouse_y, hover = 0;
	public boolean firing;
	private Handler handler;
	private Game game;
	public Random random;
	public void mousePressed(MouseEvent e) {
		this.mouse_x = e.getX();
		this.mouse_y = e.getY();
		if(!this.game.menu) {
			this.firing = true;
		} else if(this.game.player.mana > 0) {
			
			if(this.mouse_x > 900 && this.mouse_x < 1050 && this.mouse_y > 230 && this.mouse_y < 380) {
				if(this.game.player.perks > 0) {
					this.game.player.damage += 3;
					this.game.player.shotcost = this.game.player.shotcost*1.2;
					this.game.player.perks -= 1;
					int o = this.random.nextInt(2) + 1;
					AudioPlayer.load();
					AudioPlayer.getSound("upgrade" + o).play();
				}
			} else if(this.mouse_x > 1080 && this.mouse_x < 1230 && this.mouse_y > 230 && this.mouse_y < 380) {
				if(this.game.player.perks > 0) {
					this.game.player.attackspeed = (int) (this.game.player.attackspeed/1.35);
					this.game.player.shotcost = this.game.player.shotcost*0.7;
					this.game.player.perks -= 1;
					int o = this.random.nextInt(2) + 1;
					AudioPlayer.load();
					AudioPlayer.getSound("upgrade" + o).play();
				}
			} else if(this.mouse_x > 1260 && this.mouse_x < 1410 && this.mouse_y > 230 && this.mouse_y < 380) {
				if(this.game.player.perks > 0) {
					this.game.player.shots += 1;
					this.game.player.shotcost = this.game.player.shotcost*1.5;
					this.game.player.perks -= 1;
					int o = this.random.nextInt(2) + 1;
					AudioPlayer.load();
					AudioPlayer.getSound("upgrade" + o).play();
				}
			} else if(this.mouse_x > 1440 && this.mouse_x < 1590 && this.mouse_y > 230 && this.mouse_y < 380) {
				if(this.game.player.perks > 0) {
					this.game.player.explode += 1;
					this.game.player.shotcost = this.game.player.shotcost*1.3;
					this.game.player.perks -= 1;
					int o = this.random.nextInt(2) + 1;
					AudioPlayer.load();
					AudioPlayer.getSound("upgrade" + o).play();
				}
			} else if(hover == 5 && this.game.player.hat != null) {
				this.game.inventory.add(this.game.player.hat);
				this.game.player.hat = null;
			} else if(hover == 6 && this.game.player.robe != null) {
				this.game.inventory.add(this.game.player.robe);
				this.game.player.robe = null;
			} else if(hover == 7 && this.game.player.ring != null) {
				this.game.inventory.add(this.game.player.ring);
				this.game.player.ring = null;
			} else if(hover == 8 && this.game.player.staff != null) {
				this.game.inventory.add(this.game.player.staff);
				this.game.player.staff = null;
			} else if(hover == 9 && this.game.player.shoes != null) {
				this.game.player.speed -= this.game.player.shoes.movespeed;
				this.game.inventory.add(this.game.player.shoes);
				this.game.player.shoes = null;
			} else if(hover > 9 && hover < 67) {
				if(this.game.inventory.get(hover-10).type == "hat" && this.game.player.hat == null) {
					this.game.player.hat = this.game.inventory.get(hover-10);
					this.game.inventory.remove(hover-10);
					hover = 0;
				} else if(this.game.inventory.get(hover-10).type == "robe" && this.game.player.robe == null) {
					this.game.player.robe = this.game.inventory.get(hover-10);
					this.game.inventory.remove(hover-10);
					hover = 0;
				} else if(this.game.inventory.get(hover-10).type == "ring" && this.game.player.ring == null) {
					this.game.player.ring = this.game.inventory.get(hover-10);
					this.game.inventory.remove(hover-10);
					hover = 0;
				} else if(this.game.inventory.get(hover-10).type == "staff" && this.game.player.staff == null) {
					this.game.player.staff = this.game.inventory.get(hover-10);
					this.game.inventory.remove(hover-10);
					hover = 0;
				} else if(this.game.inventory.get(hover-10).type == "shoes" && this.game.player.shoes == null) {
					this.game.player.shoes = this.game.inventory.get(hover-10);
					this.game.player.speed += this.game.player.shoes.movespeed;
					this.game.inventory.remove(hover-10);
					hover = 0;
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		this.mouse_x = e.getX();
		this.mouse_y = e.getY();
		this.firing = false;
	}
	
	public MouseInput(Handler handler, Game game){
		this.firing = false;
		this.game = game;
		this.handler = handler;
		this.random = new Random();
	}
	
	public void mouseDragged(MouseEvent e) {
		this.mouse_x = e.getX();
		this.mouse_y = e.getY();
	}
	
	public void mouseMoved(MouseEvent e) {
		this.mouse_x = e.getX();
		this.mouse_y = e.getY();
		boolean fail = true;
		if(this.mouse_x > 900 && this.mouse_x < 1050 && this.mouse_y > 230 && this.mouse_y < 380) {
			hover = 1;
			fail = false;
		} else if(this.mouse_x > 1080 && this.mouse_x < 1230 && this.mouse_y > 230 && this.mouse_y < 380) {
			hover = 2;
			fail = false;
		} else if(this.mouse_x > 1260 && this.mouse_x < 1410 && this.mouse_y > 230 && this.mouse_y < 380) {
			hover = 3;
			fail = false;
		} else if(this.mouse_x > 1440 && this.mouse_x < 1590 && this.mouse_y > 230 && this.mouse_y < 380) {
			hover = 4;
			fail = false;
		} else if(this.mouse_x < 1000) {
			if(this.mouse_x > 850 && this.mouse_x < 914 && this.mouse_y > 600 && this.mouse_y < 664) {
				hover = 5;
				fail = false;
			}
			if(this.mouse_x > 850 && this.mouse_x < 914 && this.mouse_y > 684 && this.mouse_y < 748) {
				hover = 6;
				fail = false;
			}
			if(this.mouse_x > 766 && this.mouse_x < 830 && this.mouse_y > 684 && this.mouse_y < 748) {
				hover = 7;
				fail = false;
			}
			if(this.mouse_x > 934 && this.mouse_x < 1000 && this.mouse_y > 684 && this.mouse_y < 748) {
				hover = 8;
				fail = false;
			}
			if(this.mouse_x > 850 && this.mouse_x < 914 && this.mouse_y > 768 && this.mouse_y < 832) {
				hover = 9;
				fail = false;
			}
		} else {
			for(int i=0; i<this.game.inventory.size(); i++) {
				if(i < 11) {
					if(this.mouse_x > 1067 + i*64 && this.mouse_x < 1131 + i*64 && this.mouse_y > 553 && this.mouse_y < 617) {
						hover = 10 + i;
						fail = false;
					}
				} else if(i < 22) {
					if(this.mouse_x > 1067 + (i-11)*64 && this.mouse_x < 1131 + (i-11)*64 && this.mouse_y > 618 && this.mouse_y < 682) {
						hover = 10 + i;
						fail = false;
					}
				} else if(i < 33) {
					if(this.mouse_x > 1067 + (i-22)*64 && this.mouse_x < 1131 + (i-22)*64 && this.mouse_y > 682 && this.mouse_y < 746) {
						hover = 10 + i;
						fail = false;
					}
				} else if(i < 44) {
					if(this.mouse_x > 1067 + (i-33)*64 && this.mouse_x < 1131 + (i-33)*64 && this.mouse_y > 746 && this.mouse_y < 810) {
						hover = 10 + i;
						fail = false;
					}
				} else if(i < 55) {
					if(this.mouse_x > 1067 + (i-44)*64 && this.mouse_x < 1131 + (i-44)*64 && this.mouse_y > 810 && this.mouse_y < 874) {
						hover = 10 + i;
						fail = false;
					}
				} else if(i < 66) {
					if(this.mouse_x > 1067 + (i-55)*64 && this.mouse_x < 1131 + (i-55)*64 && this.mouse_y > 874 && this.mouse_y < 938) {
						hover = 10 + i;
						fail = false;
					}
				}
			}	
		} 
		if(fail) {
			hover = 0;
		}
	}
	
	public void tick(Game game) {
		this.game = game;
		if(this.firing) {
			this.game.player.fire(this.mouse_x, this.mouse_y);
		}
	}
	
	public void render(Graphics g, Game game) {
		if(game.menu && game.player.mana > 0) {
			if(hover == 1) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.darkGray);
				g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.black);
				g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.white);
				g.drawString("+3 Damage", this.mouse_x + game.camx - 180, this.mouse_y + game.camy - 28);
				g.drawString("+20% Mana Cost", this.mouse_x + game.camx - 180, this.mouse_y + game.camy + 22 - 28);
			} else if(hover == 2) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.darkGray);
				g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.black);
				g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.white);
				g.drawString("+35% Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28);
				g.drawString("-30% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy + 22 - 28);
			} else if(hover == 3) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.darkGray);
				g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.black);
				g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, 48);
				g.setColor(Color.white);
				g.drawString("+1 Projectile", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28);
				g.drawString("+50% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy + 22 - 28);
			} else if(hover == 4) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				if(game.player.explode == 0) {
					g.setColor(Color.darkGray);
					g.fillRect(this.mouse_x + game.camx - 250, this.mouse_y + game.camy - 70, 240, 70);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 250, this.mouse_y + game.camy - 70, 240, 70);
					g.setColor(Color.white);
					g.drawString("Your shots explode!", this.mouse_x + game.camx - 240, this.mouse_y + game.camy - 50);
					g.drawString("+50% Explosion Damage", this.mouse_x + game.camx - 240, this.mouse_y + game.camy - 28);
					g.drawString("+30% Mana Cost", this.mouse_x + game.camx - 240, this.mouse_y + game.camy + 22 - 28);
				} else {
					g.setColor(Color.darkGray);
					g.fillRect(this.mouse_x + game.camx - 250, this.mouse_y + game.camy - 48, 240, 48);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 250, this.mouse_y + game.camy - 48, 240, 48);
					g.setColor(Color.white);
					g.drawString("+50% Explosion Damage", this.mouse_x + game.camx - 240, this.mouse_y + game.camy - 28);
					g.drawString("+30% Mana Cost", this.mouse_x + game.camx - 240, this.mouse_y + game.camy + 22 - 28);
				}
			} else if(hover <= 9) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.darkGray);
				if(hover == 5 && game.player.hat != null) {
					g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.hat.effects*24);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.hat.effects*24);
					g.setColor(Color.white);
					int u = 0;
					if(game.player.hat.damage != 0) {
						g.drawString("+" + game.player.hat.damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.hat.regen != 0) {
						g.drawString("+" + game.player.hat.regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.hat.attackspeed != 0) {
						g.drawString("+" + game.player.hat.attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.hat.costred != 0) {
						g.drawString("-" + game.player.hat.costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.hat.movespeed != 0) {
						g.drawString("+" + game.player.hat.movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
				}
				if(hover == 6 && game.player.robe != null) {
					g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.robe.effects*24);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.robe.effects*24);
					g.setColor(Color.white);
					int u = 0;
					if(game.player.robe.damage != 0) {
						g.drawString("+" + game.player.robe.damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.robe.regen != 0) {
						g.drawString("+" + game.player.robe.regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.robe.attackspeed != 0) {
						g.drawString("+" + game.player.robe.attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.robe.costred != 0) {
						g.drawString("-" + game.player.robe.costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.robe.movespeed != 0) {
						g.drawString("+" + game.player.robe.movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
				}
				if(hover == 7 && game.player.ring != null) {
					g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.ring.effects*24);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.ring.effects*24);
					g.setColor(Color.white);
					int u = 0;
					if(game.player.ring.damage != 0) {
						g.drawString("+" + game.player.ring.damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.ring.regen != 0) {
						g.drawString("+" + game.player.ring.regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.ring.attackspeed != 0) {
						g.drawString("+" + game.player.ring.attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.ring.costred != 0) {
						g.drawString("-" + game.player.ring.costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.ring.movespeed != 0) {
						g.drawString("+" + game.player.ring.movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
				}
				if(hover == 8 && game.player.staff != null) {
					g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.staff.effects*24);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.staff.effects*24);
					g.setColor(Color.white);
					int u = 0;
					if(game.player.staff.damage != 0) {
						g.drawString("+" + game.player.staff.damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.staff.regen != 0) {
						g.drawString("+" + game.player.staff.regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.staff.attackspeed != 0) {
						g.drawString("+" + game.player.staff.attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.staff.costred != 0) {
						g.drawString("-" + game.player.staff.costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.staff.movespeed != 0) {
						g.drawString("+" + game.player.staff.movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
				}
				if(hover == 9 && game.player.shoes != null) {
					g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.shoes.effects*24);
					g.setColor(Color.black);
					g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.player.shoes.effects*24);
					g.setColor(Color.white);
					int u = 0;
					if(game.player.shoes.damage != 0) {
						g.drawString("+" + game.player.shoes.damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.shoes.regen != 0) {
						g.drawString("+" + game.player.shoes.regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.shoes.attackspeed != 0) {
						g.drawString("+" + game.player.shoes.attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.shoes.costred != 0) {
						g.drawString("-" + game.player.shoes.costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
					if(game.player.shoes.movespeed != 0) {
						g.drawString("+" + game.player.shoes.movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
						u += 1;
					}
				}
			} else if(hover >= 10) {
				Font f = new Font("f", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.darkGray);
				g.fillRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.inventory.get(hover-10).effects*24);
				g.setColor(Color.black);
				g.drawRect(this.mouse_x + game.camx - 200, this.mouse_y + game.camy - 48, 190, game.inventory.get(hover-10).effects*24);
				g.setColor(Color.white);
				int u = 0;
				if(game.inventory.get(hover-10).damage != 0) {
					g.drawString("+" + game.inventory.get(hover-10).damage + " Damage", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
					u += 1;
				}
				if(game.inventory.get(hover-10).regen != 0) {
					g.drawString("+" + game.inventory.get(hover-10).regen + " Mana Regen", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
					u += 1;
				}
				if(game.inventory.get(hover-10).attackspeed != 0) {
					g.drawString("+" + game.inventory.get(hover-10).attackspeed + " Attack Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
					u += 1;
				}
				if(game.inventory.get(hover-10).costred != 0) {
					g.drawString("-" + game.inventory.get(hover-10).costred + "% Mana Cost", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
					u += 1;
				}
				if(game.inventory.get(hover-10).movespeed != 0) {
					g.drawString("+" + game.inventory.get(hover-10).movespeed + " Move Speed", this.mouse_x + game.camx - 190, this.mouse_y + game.camy - 28 + 22*u);
					u += 1;
				}
			}
		}
	}
}
