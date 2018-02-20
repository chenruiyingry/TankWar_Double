package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 坦克大战的得分信息界面类
 * @author chenruiying
 *
 */
public class PanelShow extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 分数*/
	private int score;
	/** 我方数量*/
	private int selfCount1;
	private int selfCount2;
	public int getSelfCount2() {
		return selfCount2;
	}
	public void setSelfCount2(int selfCount2) {
		this.selfCount2 = selfCount2;
	}
	/** 敌方数量*/
	private int enemyCount;
	public void setScore(int score) {
		this.score = score;
	}
	public void setSelfCount(int selfCount) {
		this.selfCount1 = selfCount;
	}
	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}
	private ImageIcon enemy;//敌人坦克图片
	private ImageIcon self;//我方坦克图片
	private ImageIcon bg;//游戏面板背景图片
	public PanelShow() {
		bg = new ImageIcon(PanelShow.class.getResource("/img/bg.png"));
		enemy = new ImageIcon(PanelShow.class.getResource("/images/enemy1U.gif"));
		self = new ImageIcon(PanelShow.class.getResource("/images/myTankU.gif"));
	}
	
	/*
	 * 绘制面板方法
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, this.getWidth(), 760, 0, 0, bg.getIconWidth(), bg.getIconHeight(), null);
		g.setFont(new  Font("Arial", Font.BOLD, 22));
		g.setColor(Color.WHITE);
		g.drawString("Enemy Tank:", 5, 60);
		int j = 0, jj = 0;
		for (int i = 0; i < enemyCount; i++) {
			g.drawImage(enemy.getImage(), 40 * jj, 80 + 40 * j, 40 * (jj + 1), 80 + 40 * (j + 1), 0, 0, enemy.getIconWidth(), enemy.getIconHeight(), null);
			jj++;
			if(i % 5 == 0 && i != 0) {
				j++;
				jj = 0;
			}
		}
		g.drawString("My Tank1:", 5, 350);
		j = 0;
		jj = 0;
		for (int i = 0; i < selfCount1; i++) {
			g.drawImage(self.getImage(), 40 * jj, 360 + 40 * j, 40 * (jj + 1),360 + 40 * (j + 1), 0, 0, self.getIconWidth(), self.getIconHeight(), null);
			jj++;
			if(i % 5 == 0 && i != 0) {
				j++;
				jj = 0;
			}
		}
		g.drawString("My Tank2:", 5, 450);
		j = 0;
		jj = 0;
		for (int i = 0; i < selfCount2; i++) {
			g.drawImage(self.getImage(), 40 * jj, 460 + 40 * j, 40 * (jj + 1),460 + 40 * (j + 1), 0, 0, self.getIconWidth(), self.getIconHeight(), null);
			jj++;
			if(i % 5 == 0 && i != 0) {
				j++;
				jj = 0;
			}
		}
		g.setColor(Color.YELLOW);
		g.drawString("Score:", 5, 600);
		g.setFont(new  Font("Arial", 2, 22));
		g.drawString(String.valueOf(score), 50, 650);
	}

}
