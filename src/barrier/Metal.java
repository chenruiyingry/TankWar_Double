package barrier;

import javax.swing.ImageIcon;

/**
 * 坦克大战的金属墙类 
 * @author chenruiying
 *
 */
public class Metal extends Barrier {
	/** 
	 * 金属墙类主方法  
	 * @param x x位置
	 * @param y y位置
	 */
	public Metal(int x, int y) {
		super(x, y);
		setHp(50);
			setFace(new ImageIcon(Metal.class.getResource("/img/metals.gif")).getImage());
	}

}
