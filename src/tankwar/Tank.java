package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import barrier.Grass;
import barrier.Home;
import barrier.Metal;
import barrier.Wall;
import barrier.Water;

/**
 * 坦克大战的坦克类 
 * @author chenruiying
 *
 */
public class Tank {
	public static final int SIZE = 60;//坦克的尺寸
	/** 坦克的最大血量*/
	public  int hpMax = 50;
	/** 坦克的位置x*/
	private int x;
	/** 坦克的位置y*/
	private int y;
	/** 坦克当前的血量*/
	private int hp=50;
	/** 坦克的等级*/
	private int level=1;
	/** 坦克的类型*/
	private int style;
	/** 坦克的攻击力*/
	private int power;
	/** 坦克的速度*/
	private int speed=5;
	/** 坦克炮筒的方向*/
	private Direction dir;
	private ImageIcon myPlane_U;//坦克向上的图片
	private ImageIcon myPlane_R;//坦克向右的图片
	private ImageIcon myPlane_D;//坦克向下的图片
	private ImageIcon myPlane_L;//坦克向左的图片
	private boolean up, left, right, down;//是否按下“上下左右”
    private boolean good;//0我 1敌
    private boolean live;//0活 1死
	private ArrayList<Tank> allTank;
	private final ArrayList<Wall> walls;
	private final ArrayList<Metal> metals;
	private final ArrayList<Water> waters;
	private final ArrayList<Grass> grasses;
	private final Home home;
	private final ArrayList<Missle> missles;
	private final ArrayList<Explode> explodes;
	private long fireTime;//开火间隔
	
	public Direction getDir() {
		return dir;
	}
	protected static enum Direction{U,L,D,R}//枚举四个方向
	
	/**
	 * 坦克类主方法
	 * @param x x位置
	 * @param y y位置
	 * @param self 敌我
	 * @param allTank 坦克
	 * @param walls 普通墙
	 * @param metals 金属墙
	 * @param waters 水
	 * @param grasses 草
	 * @param missles 子弹
	 * @param home 基地
	 * @param booms 爆炸
	 * @param style 类型
	 */
	public Tank(int x,int y,boolean self,ArrayList<Tank> allTank,ArrayList<Wall> walls,ArrayList<Metal> metals,ArrayList<Water> waters, ArrayList<Grass> grasses,ArrayList<Missle> missles,Home home,ArrayList<Explode>booms,int style) throws Exception{
		this.missles = missles;
		this.explodes = booms;
		this.setX(x);
		this.setY(y);
		this.good = self;
		this.style = style;
		this.allTank = allTank;
		this.walls = walls;
		this.metals = metals;
		this.waters = waters;
		this.grasses = grasses;
		this.home = home;
		dir = Direction.R;
		setLive(true);
		if(!self) level = 1 + new Random().nextInt(3);
		re();
		
	}
	/**
	 * 定义坦克能力的方法 
	 */
	public void re() {
		switch (style) {
		case 1:
			switch (level)
			{
			case 1:
				power=10;
				speed=3;
				hp=80;
				hpMax=80;
				fireTime=20l;
				break;
			case 2:
				power=12;
				speed=4;
				hp=100;
				hpMax=100;
				fireTime=15l;
				break;
			case 3:
				power=14;
				speed=5;
				hp=120;
				hpMax=120;
				fireTime=10l;
				break;
			}
			break;
		case 2:
			switch (level)
			{
			case 1:
				power=16;
				speed=4;
				hp=50;
				hpMax=50;
				fireTime=20l;
				break;
			case 2:
				power=18;
				speed=5;
				hp=60;
				hpMax=60;
				fireTime=15l;
				break;
			case 3:
				power=20;
				speed=6;
				hp=70;
				hpMax=70;
				fireTime=10l;
				break;
			}
			break;
		case 3:
			switch (level)
			{
			case 1:
				power=4;
				speed=6;
				hp=40;
				hpMax=40;
				fireTime=5l;
				break;
			case 2:
				power=6;
				speed=8;
				hp=50;
				hpMax=50;
				fireTime=2l;
				break;
			case 3:
				power=8;
				speed=10;
				hp=60;
				hpMax=60;
				fireTime=1l;
				break;
			}
		}
		if(good)
		{
		myPlane_U=new ImageIcon(Tank.class.getResource("/images/mytankU.gif"));
		myPlane_R=new ImageIcon(Tank.class.getResource("/images/mytankR.gif"));
		myPlane_D=new ImageIcon(Tank.class.getResource("/images/mytankD.gif"));
		myPlane_L=new ImageIcon(Tank.class.getResource("/images/mytankL.gif"));
		}
		else
		{
			myPlane_U=new ImageIcon(Tank.class.getResource("/images/enemy"+style+"U.gif"));
			myPlane_R=new ImageIcon(Tank.class.getResource("/images/enemy"+style+"R.gif"));
			myPlane_D=new ImageIcon(Tank.class.getResource("/images/enemy"+style+"D.gif"));
			myPlane_L=new ImageIcon(Tank.class.getResource("/images/enemy"+style+"L.gif"));
			hp/=2;
			hpMax/=2;
		}
	}
	
	/**
	 * 根据炮筒方向画出坦克的方法
	 * @param g 画笔
	 */
	public void drawTank(Graphics g)
	{
		switch (dir) {

		case U:
			g.drawImage(myPlane_U.getImage(), getX(), getY(), null);
			g.setColor(Color.RED);
			g.drawRect(getX(), getY()-11, SIZE,5);
			g.fillRect(getX(), getY()-11, SIZE*hp/hpMax, 5);
			break;
		case R:
			g.drawImage(myPlane_R.getImage(), getX(), getY(), null);
			g.setColor(Color.RED);
			g.drawRect(getX(), getY()-11, SIZE,5);
			g.fillRect(getX(), getY()-11, SIZE*hp/hpMax, 5);
			break;
		case D:
			g.drawImage(myPlane_D.getImage(), getX(), getY(), null);
			g.setColor(Color.RED);
			g.drawRect(getX(), getY()-11, SIZE,5);
			g.fillRect(getX(), getY()-11, SIZE*hp/hpMax, 5);
			break;
		case L:
			g.drawImage(myPlane_L.getImage(), getX(), getY(), null);
			g.setColor(Color.RED);
			g.drawRect(getX(), getY()-11, SIZE,5);
			g.fillRect(getX(), getY()-11, SIZE*hp/hpMax, 5);
			break;
		}
	}
	
	/**
	 * 坦克移动的方法
	 */
	public void move() {
		if(up && getY() > 0 && stay(getX(), getY()) && stay(getX() + SIZE - 1, getY() - speed))setY(getY() - speed);
		else if(right && getX() < TankWar.GAME_WIDTH - 50 && stay(getX() + SIZE, getY()) && stay(getX() + speed + SIZE, getY() + SIZE - 1))setX(getX() + speed);
		else if(down && getY() < TankWar.GAME_HEIGHT - 80 && stay(getX(), getY() + speed + SIZE) && stay(getX() + SIZE - 1, getY() + speed + SIZE))setY(getY() + speed);
		else if(left && getX() > 0 && stay(getX(), getY()) && stay(getX(), getY() + SIZE - 1))setX(getX() - speed);
	}
	
	/**
	 * 键盘监听方法
	 * @param e 键盘监听
	 */
	public void keyPress(KeyEvent e) {
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_UP:
			up = true;
			dir = Direction.U;    
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			dir = Direction.R;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			dir = Direction.D;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			dir = Direction.L;
			break;
		}
	}
	
	public void keyPress1(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_W:
			up = true;
			dir = Direction.U;
			break;
		case KeyEvent.VK_D:
			right = true;
			dir = Direction.R;
			break;
		case KeyEvent.VK_S:
			down = true;
			dir = Direction.D;
			break;
		case KeyEvent.VK_A:
			left = true;
			dir = Direction.L;
			break;
		}
	}
	
	/**
	 * 键盘监听方法
	 * @param e 键盘监听
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_NUMPAD0:
			fire();
			break;
		case KeyEvent.VK_UP:
			up=false;
			break;
		case KeyEvent.VK_RIGHT:
			right=false;
			break;
		case KeyEvent.VK_DOWN:
			down=false;
			break;
		case KeyEvent.VK_LEFT:
			left=false;
			break;
		}
	}
	
	public void keyReleased1(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_J:
			fire();
			break;
		case KeyEvent.VK_W:
			up=false;
			break;
		case KeyEvent.VK_D:
			right=false;
			break;
		case KeyEvent.VK_S:
			down=false;
			break;
		case KeyEvent.VK_A:
			left=false;
			break;
		}
	}
	
	/**
	 * 碰撞方法
	 * @param willX 将移动到的x位置
	 * @param willY 将移动到的y位置
	 * @return 碰撞到了返回true，没碰撞到返回false
	 */
	public boolean stay(int willX, int willY) {
		for (int i = 0; i < allTank.size(); i++) {
			if(willX > allTank.get(i).getX() && willX < allTank.get(i).getX() + SIZE&&willY>allTank.get(i).getY()&&willY<allTank.get(i).getY()+SIZE) return false;
		}
		for (int i = 0; i < walls.size(); i++) {
			if(willX>walls.get(i).getX()&&willX<walls.get(i).getX()+SIZE&&willY>walls.get(i).getY()&&willY<walls.get(i).getY()+SIZE) return false;
		}
		for (int i = 0; i < metals.size(); i++) {
			if(willX>metals.get(i).getX()&&willX<metals.get(i).getX()+SIZE&&willY>metals.get(i).getY()&&willY<metals.get(i).getY()+SIZE) return false;
		}
		for (int i = 0; i < waters.size(); i++) {
			if(willX>waters.get(i).getX()&&willX<waters.get(i).getX()+SIZE&&willY>waters.get(i).getY()&&willY<waters.get(i).getY()+SIZE) return false;
		}
		if(willX>home.getX()&&willX<home.getX()+SIZE&&willY>home.getY()&&willY<home.getY()+SIZE) return false;
		return true;
	}
	
	/**
	 * 坦克开火的方法
	 */
	public void fire() {
		Missle mis = new Missle(x, y, this, allTank, walls, metals, waters, grasses, home, explodes, missles);
		if(good) {
			new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\fire.wav").start();
		}
		new Thread(mis).start();
		missles.add(mis);
		
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public void setDir(Direction u) {
		this.dir = u;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isSelf() {
		return good;
	}
	public void setHp(int hp){
		this.hp=hp;
	}
	public int getHp(){
		return hp;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPower()
	{
		return power;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public int getHpMax() {
		return hpMax;
	}
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
	
}
