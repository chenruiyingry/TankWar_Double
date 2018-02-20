package tankwar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 坦克大战的帮助类
 * @author chenruiying
 *
 */
public class Help extends JDialog{
	
	private static final long serialVersionUID = 1L;
	/** 面板*/
	private JPanel p;
	/** 文字*/
	private JTextArea text;
	/** 文字面板*/
	private ScrollPane sp;
	/**
	 * 帮助类主方法
	 */
	public Help() {
		this.setBounds(300, 200, 430, 430);
		this.setTitle("帮助文档");

		p=new JPanel(new BorderLayout());
		sp=new ScrollPane();
		setText(new JTextArea());
		sp.add(getText());
		getText().setFont(new Font("宋体", 1, 15));
		getText().setBorder(BorderFactory.createTitledBorder("帮助文档"));
		getText().setText(str());
		getText().setEditable(false);
		p.add(sp,BorderLayout.CENTER);
		this.add(p);
	}
	/**
	 * 
	* @Title: str 
	* @Description: 帮助内容 
	 */
	/**
	 * 帮助类帮助内容方法
	 * @return 返回输出的文字
	 */
	public String str() {
		return "1.控制：\r\n" +
				"\t玩家1\r\n" +
				"\tW坦克向上\r\n" +
				"\tS坦克向下\r\n" +
				"\tA坦克向左\r\n" +
				"\tD坦克向右\r\n" +
				"\tJ 开火\r\n" +
				"\t玩家2\r\n" +
				"\t↑坦克向上\r\n" +
				"\t↓坦克向下\r\n" +
				"\t←坦克向左\r\n" +
				"\t→坦克向右\r\n" +
				"\tNum0  开火\r\n" +
				"2.关于地图：\r\n" +
				"\t地图中棕色障碍是可以摧毁的障碍\r\n" +
				"\t蓝色障碍和绿色障碍不可摧毁\r\n" +
				"\t蓝色地毯为我方坦克出生点\r\n" +
				"\t红色地毯为敌方坦克出生点\r\n" +
				"\t地图可使用地图编辑器生成新地图或修改地图\r\n" +
				"3.游戏资源:\r\n" +
				"\t炸弹：炸死地图内可见的敌人\r\n" +
				"\t暂停：停止敌人的移动5s\r\n" +
				"\t爱心：加满血\r\n" +
				"\t星星：增加一条生命\r\n" +
				"4.游戏规则:\r\n" +
				"\t我方坦克剩余数量为0时游戏结束，判负\r\n" +
				"\t我方基地被摧毁时游戏结束，判负\r\n" +
				"\t敌方坦克剩余数量为0时游戏结束，判胜\r\n" +
				"\r\n\r\n\t\t祝游戏愉快";
		
	}
	public void setText(JTextArea text) {
		this.text = text;
	}
	public JTextArea getText() {
		return text;
	}
}
