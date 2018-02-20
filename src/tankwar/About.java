package tankwar;

import javax.swing.BorderFactory;
/**
 * 
* @ClassName: About 
* @Description: 坦克游戏的关于稳定 
* @author chenruiying
* @date 2017年9月6日 上午11:28:18 
*
 */
/**
 * 坦克大战的关于类
 * @author chenruiying
 *
 */
public class About extends Help {

	private static final long serialVersionUID = 1L;
	/**
	 * 关于类的主方法
	 */
	public About() {
		this.setBounds(200, 200, 400, 150);
		this.setTitle("关于本游戏");
		this.getText().setBorder(BorderFactory.createTitledBorder("关于"));
	}
	/*
	 * 内容
	 * @see tankwar.Help#str()
	 */
	@Override
	public String str() {
		return 	
				"\n"+
				"\t     Tank War 坦克大战\r\n" +
				"\t       作者：陈瑞颖\n" +
				"\t    本游戏图片均来自于网络";
		
	}
}
