package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import barrier.*;
import tankwar.Tank.Direction;

/**
 * 坦克大战的游戏类 
 * @author chenruiying
 *
 */
public class TankWar implements KeyListener {
	static  boolean TIMEOUT=false;//0不暂停 1暂停
	private JFrame f;//JFrame
	private JPanel gamePanel;//游戏窗口
	private PanelShow messgePanel;//信息窗口
	private JMenuBar jmb = null;//我的菜单
	private myPanel p;//JPanel
	private Tank[] myTanks = new Tank[2];
	public static final int GAME_WIDTH = 1024;//游戏宽度
	public static final int GAME_HEIGHT = 750;//游戏高度
	private ArrayList<Missle> missles = new ArrayList<Missle>();
	private ArrayList<Tank> allTanks = new ArrayList<Tank>();
	private ArrayList<Explode> explodes = new ArrayList<Explode>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Metal> metals = new ArrayList<Metal>();
	private ArrayList<Grass> grasses = new ArrayList<Grass>();
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private ArrayList<Water> waters = new ArrayList<Water>();
	private ArrayList<EnemyBorn> enemyBorns = new ArrayList<EnemyBorn>();
	private ArrayList<SelfBorn> selfBorns = new ArrayList<SelfBorn>();
	private Home home;
	private Tank enemyTank;
	/** 产生随机数*/
	private Random r;
	private ImageIcon backGround;//背景图片
	private final String map;//地图
	/** 最大坦克数量数*/
	private int tankMax;
	private boolean over1 = false;
	private boolean over2 = false;
	private boolean over = false;//0未结束  1结束
	private static int selfMax1 = 3;//我方最大数量
	private static int selfMax2 = 3;
	private boolean win;//0赢  1输
	private boolean flash = false;//0未闪  1闪
	private TankWar tw = this;
	/** 得分*/
	static int SCORE = 0;
	private final JFrame mainF;//JFrame
	/** 类型*/
	private int style;
	
	/**
	 * 坦克游戏类主方法
	 * @param map 地图
	 * @param tankMax 最大坦克数量
	 * @param mainF 面板
	 * @param style 类型
	 */
	public TankWar(String map,int tankMax,JFrame mainF,int style) throws Exception {
		this.map = map;
		this.tankMax = tankMax;
		this.mainF = mainF;
		this.style = style;
		init();
	}
	
	/**
	 * 坦克大战绘制游戏窗口方法
	 */
	private void init() {
		f = new JFrame("穷人版坦克大战");
		gamePanel = new JPanel(null);
		p = new myPanel();
		p.setBackground(Color.WHITE);
		r = new Random();
		jmb = new JMenuBar();
		f.setJMenuBar(jmb);
		messgePanel = new PanelShow();
		initMap(new File("map/" + map));
		try {
			myTanks[0] = new Tank(selfBorns.get(0).getX(), selfBorns.get(0).getY(), 
					true, allTanks, walls, metals, waters, grasses, missles, home, explodes, style);
			myTanks[1] = new Tank(selfBorns.get(1).getX(), selfBorns.get(1).getY(), 
					true, allTanks, walls, metals, waters, grasses, missles, home, explodes, style);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		myTanks[0].setDir(Direction.U);	
		myTanks[1].setDir(Direction.U);
		allTanks.add(myTanks[0]);
		allTanks.add(myTanks[1]);
		addTank();
		try {
			backGround = new ImageIcon(TankWar.class.getResource("/images/backGround.png"));
			} catch(Exception e){}
		
		p.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
		p.setSize(GAME_WIDTH, GAME_HEIGHT);
		messgePanel.setBounds(GAME_WIDTH, 0, 200, GAME_HEIGHT);
		gamePanel.add(messgePanel);
		gamePanel.add(p);
		f.add(gamePanel);
		f.setBounds(0, 0, GAME_WIDTH + 200 , GAME_HEIGHT);
		f.setDefaultCloseOperation(3);
		f.setResizable(false);
		f.setFocusable(true);
		f.addKeyListener(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)(toolkit.getScreenSize().getWidth() - f.getWidth()) / 2;
		int y = (int)(toolkit.getScreenSize().getHeight() - f.getHeight()) / 2;
		new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\start.wav").start();
		f.setLocation(x, y);
		f.setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	private class PaintThread implements Runnable  {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!over) {
				if(!myTanks[1].isLive()) {
					selfMax1--;
					if(selfMax1 < 0) {
						f.removeKeyListener(tw);
						over = true;
						win = false;
						break;
					} else {
						myTanks[1].setLevel(1);
						myTanks[1].setX(selfBorns.get(1).getX());
						myTanks[1].setY(selfBorns.get(1).getY());
						myTanks[1].setDir(Direction.U);
						myTanks[1].setHp(myTanks[1].hpMax);
						myTanks[1].setLive(true);
					}	
				}
				if(!myTanks[0].isLive()) {
					selfMax2--;
					if(selfMax2 < 0) {
						f.removeKeyListener(tw);
						over = true;
						win = false;
						break;	
					} else {
						myTanks[0].setLevel(1);
						myTanks[0].setX(selfBorns.get(0).getX());
						myTanks[0].setY(selfBorns.get(0).getY());
						myTanks[0].setDir(Direction.U);
						myTanks[0].setHp(myTanks[0].hpMax);
						myTanks[0].setLive(true);
					}
				}
				if(tankMax <= 0 && allTanks.size() <= 2 && 
						(myTanks[0].isLive() || myTanks[1].isLive())) {
					f.removeKeyListener(tw);
					over = true;
					win = true;
				}
				if(!home.isLive()) {
					f.removeKeyListener(tw);
					over = true;
					win = false;
				}
				myTanks[0].move();
				myTanks[1].move();
				p.repaint();
				for (int i = 2; i < allTanks.size(); i++) {
					allTanks.get(i).move();
					aI(allTanks.get(i));
				}
				if(allTanks.size() <= enemyBorns.size() + 1) addTank();
				messgePanel.setEnemyCount(tankMax);
				messgePanel.setSelfCount2(selfMax1);
				messgePanel.setSelfCount(selfMax2);
				messgePanel.setScore(SCORE);
				if(SCORE % 500 == 0) {
					SCORE += 100;
					Resource resource = new Resource(allTanks, explodes);
					resources.add(resource);
					resource.start();
				}
				try {Thread.sleep(30);
				} catch (InterruptedException e) {}
			}
		over();
		}
	}
	
	/**
	 * 坦克游戏我的游戏面板方法
	 * @author chenruiying
	 *
	 */
	private class myPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		/*
		 * 绘制地图方法
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backGround.getImage(), 0, 0,null);
			for (int j = 0; j < walls.size(); j++) {
				walls.get(j).draw(g);
			}
			for (int j = 0; j < metals.size(); j++) {
				metals.get(j).draw(g);
			}
			for (int j = 0; j < waters.size(); j++) {
				waters.get(j).draw(g);
			}
			for (int j = 0; j < grasses.size(); j++) {
				grasses.get(j).draw(g);
			}
			for (int j = 0; j < enemyBorns.size(); j++) {
				enemyBorns.get(j).draw(g);
			}
			for (int j = 0; j < selfBorns.size(); j++) {
				selfBorns.get(j).draw(g);
			}
				home.draw(g);
			for (int j = 0; j < allTanks.size(); j++) {
				allTanks.get(j).drawTank(g);
			}
			for (int i = 0; i < missles.size(); i++) {
				missles.get(i).drawMissle(g);
				if(!missles.get(i).isLive()) missles.remove(i);
			}
			for (int i = 0; i < explodes.size(); i++) {
				if(explodes.get(i).isLive()) explodes.get(i).drawBoom(g);
				else explodes.remove(i);
			}
			for (int j = 0; j < resources.size(); j++) {
				if(!resources.get(j).isLive())
				{
					resources.remove(j);
					continue;
				}
				resources.get(j).draw(g);
			}
			if(over) drawOver(g);
			messgePanel.repaint();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * 按下键盘后的反应的方法
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(over) {
			if(e.getKeyCode() == KeyEvent.VK_F1) {
				over = false;
				missles.clear();
				allTanks.clear();
				explodes.clear();
				walls.clear();
				metals.clear();
				grasses.clear();
				waters.clear();
				enemyBorns.clear();
				try {
					init();
				} catch (Exception e1) {}
			} else {
				f.setVisible(false);
				mainF.setSize(1024,750);
				mainF.setVisible(true);
			}
		} else {
			myTanks[0].keyPress1(e);
			myTanks[1].keyPress(e);
		}
	}
	
	/*
	 * 释放键盘的反应的方法
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		myTanks[0].keyReleased1(e);
		myTanks[1].keyReleased(e);
	}
	
	/**
	 * 敌人坦克自动行走开火的方法
	 * @param tank 坦克
	 */
	public void aI(Tank tank) {
		if(TIMEOUT) {
			tank.setUp(false);
			tank.setLeft(false);
			tank.setDown(false);
			tank.setRight(false);
			return;
		}
		if(r.nextInt(25) == 0) 	tank.fire();
		if(r.nextInt(10) == 0) {
			if(tank.getX() >= myTanks[1].getX() && tank.getX() <= myTanks[1].getX() 
					+ Tank.SIZE && tank.getY() > myTanks[1].getY()) {
				tank.setUp(true);
				tank.setLeft(false);
				tank.setDown(false);
				tank.setRight(false);
				tank.setDir(Direction.U);
				return;
			} else if(tank.getX() >= myTanks[1].getX() && tank.getX() <= myTanks[1].getX() 
					+ Tank.SIZE&&tank.getY() < myTanks[1].getY()) {	
				tank.setUp(false);
				tank.setLeft(false);
				tank.setDown(true);
				tank.setRight(false);
				tank.setDir(Direction.D);
				return;
			} else if(tank.getX() > myTanks[1].getX() && tank.getY() >= myTanks[1].getY() 
					&& tank.getY() <= myTanks[1].getY() + Tank.SIZE) {
				tank.setUp(false);
				tank.setLeft(true);
				tank.setDown(false);
				tank.setRight(false);
				tank.setDir(Direction.L);
				return;
			} else if(tank.getX() < myTanks[1].getX() && tank.getY() >= myTanks[1].getY() 
					&& tank.getY() <= myTanks[1].getY() + Tank.SIZE) {
					tank.setUp(false);
					tank.setLeft(false);
					tank.setDown(false);
					tank.setRight(true);
					tank.setDir(Direction.R);
					return;
			}
		}
		if(tank.getX() <= 0)  {
			tank.setUp(false);
			tank.setLeft(false);
			tank.setDown(false);
			tank.setRight(true);
			tank.setDir(Direction.R);
		}
		if(tank.getY() <= 0) {
			tank.setUp(false);
			tank.setLeft(false);
			tank.setDown(true);
			tank.setRight(false);
			tank.setDir(Direction.D);
		}
		if(tank.getX() >= GAME_WIDTH - Tank.SIZE){
			tank.setUp(false);
			tank.setLeft(true);
			tank.setDown(false);
			tank.setRight(false);
			tank.setDir(Direction.L);
		}
		if(tank.getY() >= GAME_HEIGHT - Tank.SIZE) {	
			tank.setUp(true);
			tank.setLeft(false);
			tank.setDown(false);
			tank.setRight(false);
			tank.setDir(Direction.U);
		} else if(r.nextInt(300) == 1) {
			tank.setUp(true);
			tank.setLeft(false);
			tank.setDown(false);
			tank.setRight(false);
			tank.setDir(Direction.U);
		} else if(r.nextInt(300) == 2) {
			tank.setUp(false);
			tank.setLeft(true);
			tank.setDown(false);
			tank.setRight(false);
			tank.setDir(Direction.L);
		} else if(r.nextInt(300) == 3) {
			tank.setUp(false);
			tank.setLeft(false);
			tank.setDown(true);
			tank.setRight(false);
			tank.setDir(Direction.D);
		} else if(r.nextInt(300) == 4) {
			tank.setUp(false);
			tank.setLeft(false);
			tank.setDown(false);
			tank.setRight(true);
			tank.setDir(Direction.R);
		}
	}
	
	/**
	 * 绘制地图方法
	 * @param file 存储的地图文件
	 */
	public void initMap(File file) {
		try{
			FileInputStream read = new FileInputStream(file);
			for (int i = 0; i < GAME_HEIGHT / 60 ; i++) {
				for (int j = 0; j < GAME_WIDTH / 60; j++) {
					switch (read.read()) {
					case 1:
						 walls.add(new Wall(j * 60, i * 60));
						break;
					case 2:
						metals.add(new Metal(j * 60, i * 60));
						break;
					case 3:
						grasses.add(new Grass(j * 60, i * 60));
						break;
					case 4:
						waters.add(new Water(j * 60, i * 60));
						break;
					case 5:
						selfBorns.add(new SelfBorn(j * 60, i * 60));
						break;
					case 6:
						enemyBorns.add(new EnemyBorn(j * 60, i * 60));
						break;
					case 7:
						home=new Home(j * 60, i * 60);
						break;
					}
				}
			}
			read.close();
		}catch(Exception e){};
	}

	/**
	 * 敌人坦克自动添加的方法
	 */
	public void addTank() {
		if (tankMax <= 0) return; 
		for (int i = allTanks.size(); i < enemyBorns.size() + 1; i++) {
			try {
				int temp = r.nextInt(enemyBorns.size());
				enemyTank = new Tank(enemyBorns.get(temp).getX(), enemyBorns.get(temp).getY(), 
						false, allTanks, walls, metals, waters, grasses, 
							missles, home, explodes, r.nextInt(3) + 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			enemyTank.setDir(Direction.D);
			enemyTank.setDown(true);
			allTanks.add(enemyTank);
			tankMax--;
			if (tankMax <= 0) return; 
		}
	}
	
	/**
	 * 增加我方坦克生命的方法
	 */
	public static void addMyTankLives() {
		selfMax1++;
		selfMax2++;
	}
	
	/**
	 * 游戏结束的方法
	 */
	private void over() {
		
		for (int i = 0; i < GAME_HEIGHT / 60 ; i++) {
			for (int j = 0; j < GAME_WIDTH / 60; j++) {
				metals.add(new Metal(j * 60, i * 60));
				p.repaint();
				try {Thread.sleep(5);} catch (InterruptedException e) {}
			}
		}
		while(true)
		{
			flash =! flash;
			p.repaint();
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			f.addKeyListener(this);
		}
	}
	
	/**
	 * 绘制游戏结束界面的方法
	 * @param g 画笔
	 */
	private void drawOver(Graphics g) {
		p.repaint();
		g.setColor(Color.red);
		g.setFont(new Font("Arial", 1, 100));
		g.drawString("GAME OVER", 170, 200);
		g.setFont(new Font("Arial", 2, 50));
		if(win) g.drawString("Congratulation!  You Win!", 190, 400);
		else g.drawString("So Sorry,  You Lose!", 190, 400);
		if(flash){
			g.setFont(new Font("Arial", 2, 30));
			g.setColor(Color.BLACK);
			g.drawString("Press F1 to try again...,", 210, 500);
			g.drawString("Press the other Key to Return the Title...,", 210, 600);
		}
	}
}
