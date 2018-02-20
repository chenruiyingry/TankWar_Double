package barrier;

import javax.swing.ImageIcon;
/**
 * 坦克大战的水类 
 * @author chenruiying
 *
 */
public class Water extends Barrier{
	/**
	 * 水类主方法 
	 * @param x x位置
	 * @param y y位置
	 */
	public Water(int x, int y) {
		super(x, y);
		setFace(new ImageIcon(Water.class.getResource("/images/water.gif")).getImage());
	}

}
