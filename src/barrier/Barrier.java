package barrier;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 坦克游戏的物块类 
 * @author chenruiying
 *
 */
public class Barrier {
		private static final int SIZE = 60;//物块的尺寸
		/** x位置*/
		private int x;
		/** y位置*/
		private int y;
		/** 血量*/
		private int hp;
		private ImageIcon face = null;//物块图片
		/**
		 * 物块的主方法
		 * @param x x位置
		 * @param y y位置
		 */
		public Barrier(int x,int y) {
			this.x=x;
			this.y=y;
			face=new ImageIcon();
		}
		/**
		 * 画出物块的方法
		 * @param g 画笔
		 */
		public void draw(Graphics g) {
			try {
				
			} catch (Exception e) {
				
			}
			g.drawImage(face.getImage(), x, y, x + SIZE, y + SIZE, 0, 0, face.getIconWidth(), face.getIconHeight(), null);
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getHp() {
			return hp;
		}
		public void setHp(int hp) {
			this.hp = hp;
		}
		public Image getFace() {
			return face.getImage();
		}
		public void setFace(Image face) {
			this.face.setImage(face);
		}
}
