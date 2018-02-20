package barrier;

import javax.swing.ImageIcon;
/**
 * 坦克大战的草地类
 * @author chenruiying
 *
 */
public class Grass extends Barrier{
	/**
	 * 草地类主方法
	 * @param x x位置
	 * @param y y位置
	 */
	public Grass(int x, int y) {
		super(x, y);
		setFace(new ImageIcon(Grass.class.getResource("/images/grass.png")).getImage());
	}


	
}
