package tankwar;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import barrier.Grass;
import barrier.Home;

import barrier.Metal;
import barrier.Wall;
import barrier.Water;
import tankwar.Tank.Direction;


/**
 * 坦克大战的子弹类 
 * @author chenruiying
 *
 */
public class Missle implements Runnable{
	/** x位置*/
	private int x;
	/** y位置*/
	private int y;
	/** 速度*/
	private int speed;
	/** 方向*/
	private Direction dir;
	private boolean live = true;//0存活 1死亡
	/** 尺寸*/
	public int SIZE;
	private boolean self;//0我方 1敌方
	private ArrayList<Tank> allTanks;
	private ArrayList<Explode> explodes;
	private final ArrayList<Wall> walls;
	private final ArrayList<Metal> metals;
	private final ArrayList<Water> waters;
	private final ArrayList<Grass> grasses;
	private final Home home;
	/** 火力*/
	private int power;
	
	private ImageIcon enemymissleIcon1;//敌人子弹图片1
	private ImageIcon mymissIcon1;//我方子弹图片1
	private ImageIcon enemymissleIcon2;//敌人子弹图片2
	private ImageIcon mymissIcon2;//我方子弹图片2
	private ImageIcon enemymissleIcon3;//敌人子弹图片3
	private ImageIcon mymissIcon3;//我方子弹图片3
	
	/**
	 * 子弹类主方法
	 * @param x 位置x
	 * @param y 位置y
	 * @param tank 坦克
	 * @param allTanks 全部坦克
	 * @param walls 普通墙
	 * @param metals 金属墙
	 * @param waters 水
	 * @param grasses 草
	 * @param home 家
	 * @param explodes 爆炸
	 * @param missles 子弹
	 */
	public Missle(int x, int y, Tank tank, ArrayList<Tank> allTanks, ArrayList<Wall> walls, ArrayList<Metal> metals, ArrayList<Water> waters,ArrayList<Grass> grasses , Home home, ArrayList<Explode>explodes, ArrayList<Missle> missles) 
	{
		this.allTanks = allTanks;
		this.walls = walls;
		this.metals = metals;
		this.waters = waters;
		this.grasses = grasses;
		this.home = home;
		this.explodes = explodes;
		this.self = tank.isSelf();
		this.power = tank.getPower();
		SIZE = power;
		this.x = x + 22;
		this.y = y + 30;
		this.dir = tank.getDir();
		speed = 10;
	}
	
	/*
	 * 子弹的运动方法
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while((x >- 10 && x < TankWar.GAME_WIDTH && y >- 10 && y < TankWar.GAME_HEIGHT && live)) {
			switch (dir) {
			case U:
				y -= speed;
				break;
			case R:
				x += speed;
				break;
			case D:
				y += speed;
				break;
			case L:
				x -= speed;
				break;
			}
			for (int i = 0; i < walls.size(); i++) {
				if(x > walls.get(i).getX() && x < walls.get(i).getX() + 60 && y > walls.get(i).getY() && y < walls.get(i).getY() + 60) {
					walls.get(i).setHp(walls.get(i).getHp() - power);
					if(self) {
 						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\hit.wav").start();
					}
					if(walls.get(i).getHp() <= 0) {
						Explode temp = new Explode(walls.get(i).getX(), walls.get(i).getY());
						new Thread(temp).start();
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\explode.wav").start();
						explodes.add(temp);
						walls.remove(i);
					}
					this.live = false;
				} 
			}
			if(x > home.getX() && x < home.getX() + 60 && y > home.getY() && y < home.getY() + 60) {
				home.setHp(home.getHp() - power);
				if(home.getHp() <= 0) {
					Explode temp=new Explode(home.getX(), home.getY());
					Explode temp1=new Explode(home.getX() + 60, home.getY());
					Explode temp2=new Explode(home.getX() - 60, home.getY());
					Explode temp3=new Explode(home.getX(), home.getY() - 60);
					new Thread(temp).start();
					new Thread(temp1).start();
					new Thread(temp2).start();
					new Thread(temp3).start();
					new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\blast.wav").start();
					explodes.add(temp);
					explodes.add(temp1);
					explodes.add(temp2);
					explodes.add(temp3);
					home.setFace(new ImageIcon("images/destory.gif").getImage());
					home.setLive(false);
				}
				this.live = false;
			}
			for (int i = 0; i < metals.size(); i++) {
				if(x > metals.get(i).getX() && x < metals.get(i).getX() + 60 && y > metals.get(i).getY() && y < metals.get(i).getY() + 60)
				{
					this.live = false;
				}
			}
			
			if(self) {
				for (int i = 2; i < allTanks.size(); i++) {
					if((x > allTanks.get(i).getX() && x < allTanks.get(i).getX() + Tank.SIZE && y>allTanks.get(i).getY() && y < allTanks.get(i).getY() + Tank.SIZE)) {
						allTanks.get(i).setHp(allTanks.get(i).getHp() - power);
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\hit.wav").start();
						if(allTanks.get(i).getHp()<=0) {
							Explode temp = new Explode(allTanks.get(i).getX(), allTanks.get(i).getY());
							new Thread(temp).start();
							new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\blast.wav").start();
							explodes.add(temp);
							TankWar.SCORE += 100;
							try{
								allTanks.remove(i);
							} catch (Exception e) {}
						}
						this.live = false;
					}
				}
			} else {	
				try {
					if(x > allTanks.get(0).getX() && x < allTanks.get(0).getX() + Tank.SIZE && y > allTanks.get(0).getY() && y < allTanks.get(0).getY() + Tank.SIZE) {
						allTanks.get(0).setHp(allTanks.get(0).getHp() - power);
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\hit.wav").start();	
					if(allTanks.get(0).getHp() <= 0) {
						Explode temp = new Explode(allTanks.get(0).getX(), allTanks.get(0).getY());
						new Thread(temp).start();
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\blast.wav").start();
						explodes.add(temp);
						allTanks.get(0).setLive(false);
					}
					this.live = false;
				}
			} catch(Exception e){}
				try {
					if(x > allTanks.get(1).getX() && x < allTanks.get(1).getX() + Tank.SIZE && y > allTanks.get(1).getY() && y < allTanks.get(1).getY() + Tank.SIZE) {
						allTanks.get(1).setHp(allTanks.get(1).getHp() - power);
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\hit.wav").start();	
					if(allTanks.get(1).getHp() <= 0) {
						Explode temp = new Explode(allTanks.get(1).getX(), allTanks.get(1).getY());
						new Thread(temp).start();
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\blast.wav").start();
						explodes.add(temp);
						allTanks.get(1).setLive(false);
					}
					this.live = false;
				}
			} catch(Exception e){}
		}
		try {Thread.sleep(30);
		} catch (InterruptedException e) {}	
	}
	this.live = false;
}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}
	
	/**
	 * 画出子弹的方法
	 * @param g 画笔
	 */
	public void drawMissle(Graphics g)
	{
		enemymissleIcon1 = new ImageIcon(Missle.class.getResource("/img/enemymissle_1.gif"));
		mymissIcon1 = new ImageIcon(Missle.class.getResource("/img/tankmissle_1.gif"));
		enemymissleIcon2 = new ImageIcon(Missle.class.getResource("/img/enemymissle_2.gif"));
		mymissIcon2 = new ImageIcon(Missle.class.getResource("/img/tankmissle_2.gif"));
		enemymissleIcon3 = new ImageIcon(Missle.class.getResource("/img/enemymissle_3.gif"));
		mymissIcon3 = new ImageIcon(Missle.class.getResource("/img/tankmissle_3.gif"));
		if(!self) {
			if(SIZE >= 10 && SIZE <= 14) {
				g.drawImage(enemymissleIcon2.getImage(), x, y, null);
			} else if (SIZE >= 16 && SIZE <= 20) {
				g.drawImage(enemymissleIcon1.getImage(), x, y, null);
			} else {
				g.drawImage(enemymissleIcon3.getImage(), x, y, null);
			}
		} else {
			if(SIZE >= 10 && SIZE <= 14) {
				g.drawImage(mymissIcon2.getImage(), x, y, null);
			} else if (SIZE >= 16 && SIZE <= 20) {
				g.drawImage(mymissIcon1.getImage(), x, y, null);
			} else {
				g.drawImage(mymissIcon3.getImage(), x, y, null);
			}
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
