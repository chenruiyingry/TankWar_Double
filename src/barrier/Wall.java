package barrier;

import javax.swing.ImageIcon;
/**
 * 坦克大战的普通墙类 
 * @author chenruiying
 *
 */
public class Wall extends Barrier {
	/**
	 * 
	* @Title: Wall
	* @Description: 普通墙类 主方法
	* @param x x位置
	* @param y y位置
	 */
	/**
	 * 普通墙类 主方法
	 * @param x x位置
	 * @param y y位置
	 */
	public Wall(int x, int y) {
		super(x, y);
		setHp(20);
			setFace(new ImageIcon(Wall.class.getResource("/img/walls.gif")).getImage());
	}
}
