package barrier;

import javax.swing.ImageIcon;
/**
 * 坦克大战的我方出生点类
 * @author chenruiying
 *
 */
public class SelfBorn extends Barrier {
	/**
	 * 我方出生点类主方法
	 * @param x x位置
	 * @param y y位置
	 */
	public SelfBorn(int x, int y) {
		super(x, y);
			setFace(new ImageIcon(SelfBorn.class.getResource("/img/born_1.png")).getImage());
	}

}
