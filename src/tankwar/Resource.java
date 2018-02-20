package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * 坦克游戏的资源类
 * @author chenruiying
 *
 */
public class Resource  extends Thread{		
	/** 产生随机数*/
	private Random r;
	/** x位置*/
	private int x;
	/** y位置*/
	private int y;
	/** 图片*/
	private Image face;
	private boolean live = true;//0存在 1不存在
	private boolean flash;//0闪烁 1不闪烁
	/** 类型*/
	private int style;
	private final ArrayList<Tank> tanks;
	private final ArrayList<Explode> explodes;

	
	/**
	 * 资源类主方法 
	 * @param tanks 坦克
	 * @param booms 爆炸
	 */
	public Resource(ArrayList<Tank> tanks, ArrayList<Explode> booms)
	{
		this.tanks = tanks;
		this.explodes = booms;
		r = new Random();
		x = r.nextInt(1000);
		y = r.nextInt(650)+30;
		style = r.nextInt(4);
		face = new ImageIcon(Resource.class.getResource("/img/" + style + ".gif")).getImage();
	}
	
	/**
	 * 画出资源的图片方法
	 * @param g 画笔
	 */
	public void draw(Graphics g)
	{
		if(flash)g .drawImage(face, x, y, null);
	}
	/*
	 * 坦克使用资源方法
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		flash = true;
		for (int i = 0; i < 40; i++) {
			flash = !flash;
			if(!this.live) break;
				Tank tank = tanks.get(0);
				Tank tank2 = tanks.get(1);
				if((tank.getX() > x && tank.getX() < x + 60 && tank.getY() > y && tank.getY() < y + 60) ||
					(tank.getX() + 60 > x &&tank.getX() < x + 60 && tank.getY() > y && tank.getY() < y + 60) ||
					(tank.getX() > x && tank.getX() < x + 60 && tank.getY() + 60 > y && tank.getY() < y + 60) ||
					(tank.getX() + 60 > x && tank.getX() + 60 < x +50 && tank.getY() + 50 > y && tank.getY() + 60 < y + 60)) {
					use(tank);
					new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\life.wav").start();	
				}
				if((tank2.getX() > x && tank2.getX() < x + 60 && tank2.getY() > y && tank2.getY() < y + 60) ||
						(tank2.getX() + 60 > x &&tank2.getX() < x + 60 && tank2.getY() > y && tank2.getY() < y + 60) ||
						(tank2.getX() > x && tank2.getX() < x + 60 && tank2.getY() + 60 > y && tank2.getY() < y + 60) ||
						(tank2.getX() + 60 > x && tank2.getX() + 60 < x +50 && tank2.getY() + 50 > y && tank2.getY() + 60 < y + 60)) {
						use(tank2);
						new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\life.wav").start();	
					}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setLive(false);
	}
	
	/**
	 * 各种资源的作用的方法
	 * @param tank 坦克
	 */
	public void use(Tank tank) {
		switch (style) {
		case 0:
			Explode temp = new Explode(tanks.get(tanks.size() - 1).getX(), tanks.get(tanks.size() - 1).getY());
			new Thread(temp).start();
			explodes.add(temp);
			TankWar.SCORE += 100;
			tanks.remove(tanks.size() - 1);
			this.live = false;
			break;
		case 1:
			TankWar.TIMEOUT = true;
			new Thread(new Runnable()
			{			
				public void run()
				{
					try
					{
						Thread.sleep(5000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					TankWar.TIMEOUT = false;
				}
			}).start();
			break;
		case 2:
			tank.setHp(tank.hpMax);
			break;
		case 3:
			TankWar.addMyTankLives();
			break;
		}
		this.live = false;	
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}
}
