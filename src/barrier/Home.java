package barrier;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * 坦克游戏的基地类
 * @author chenruiying
 *
 */
public class Home extends Barrier {

	private boolean live;//0存活 1死亡
	public static int HPMAX = 50;//最大血量
	/**
	 * 基地的主类
	 * @param x x位置
	 * @param y y位置
	 */
	public Home(int x, int y) {
		super(x, y);
		setHp(HPMAX);
		setLive(true);

			setFace(new ImageIcon(Home.class.getResource("/img/home.jpg")).getImage());

	}
	/*
	 * 画出基地图片的方法
	 * @see barrier.Barrier#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g)
	{
		super.draw(g);
		g.setColor(Color.RED);
		g.drawRect(getX(), getY(), 60, 5);
		g.fillRect(getX(), getY(), 60*this.getHp()/HPMAX, 5);
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}

}
