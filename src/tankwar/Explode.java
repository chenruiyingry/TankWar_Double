package tankwar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * 坦克大战的爆炸类 
 * @author chenruiying
 *
 */
public class Explode implements Runnable {
	/** x位置*/
	private int y;
	/** y位置*/
	private int x;
	/** 爆炸的次序*/
	private int step = 0;
	private ImageIcon[] boom = new ImageIcon[6];//爆炸的图片
	private boolean live = true;//0存活 1死亡

	/**
	 * 爆炸类主方法
	 * @param x x位置
	 * @param y y位置
	 */
	public Explode(int x, int y) {
		this.x = x;
		this.y = y;
		for (int i = 1; i <= 5; i++) {
			boom[i] = new ImageIcon(TankClient.class.getResource("/img/boom_" + i + ".png"));
		}
	}

	/**
	 * 画爆炸方法
	 * @param g 画笔
	 */
	public void drawBoom(Graphics g) {
		g.setColor(Color.black);
		try {
			g.drawImage(boom[step].getImage(), x + 5, y + 5, null);
		} catch (Exception e) {
		}
		if (step == 5)
			live = false;
	}

	/*
	 * 爆炸发生的线程方法
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (step <= 5) {
			step++;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}

	}

	public boolean isLive() {
		return live;
	}
}
