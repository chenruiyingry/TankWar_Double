package barrier;

import javax.swing.ImageIcon;

/**
 * 坦克大战的敌人出生点类
 * @author chenruiying
 *
 */
public class EnemyBorn extends Barrier {
	/**
	 * 游戏敌人出生点类主方法
	 * @param x x位置
	 * @param y y位置
	 */
	public EnemyBorn(int x, int y) {
		super(x, y);
			setFace(new ImageIcon(EnemyBorn.class.getResource("/images/born_enemy_1.PNG")).getImage());
	}

}
