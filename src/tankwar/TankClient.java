 package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lightButton.LightButton;
import mapediter.MapEdit;


/**
 * 坦克大战的登录器类
 * @author chenruiying
 *
 */
public class TankClient implements ActionListener{
		/** 窗口容器*/
		private JFrame f;
		/** 流布局*/
		private MyPanel p;
		/** 开始按钮*/
		private LightButton butStart;
		/** 编辑地图按钮*/
		private LightButton butEdit;
		/** 帮助按钮*/
		private LightButton butHelp;
		/** 关于按钮*/
		private LightButton butAbout;
		/** 关于按钮*/
		private LightButton butExit;
		
		/** 对话窗口*/
		private JDialog set;
		/** 地图*/
		private String map;
		/** 最大数量*/
		private int max;
		/** 类型*/
		protected int style;
		
		/** 登录窗口的宽度*/
		private int width;
		/** 登录窗口的高度*/
		private int height;
		private Help help=new Help();
		private About about=new About(); 
		private Audio menuAudio;
		
		
		/**
		 * 坦克大战的主方法
		 */
		public TankClient() {
			setF(new JFrame("坦克大战双人版"));
			p=new MyPanel();
			p.setLayout(null);
			butStart=new LightButton(442,305,140,50,"START");
			butEdit=new LightButton(442,358,140,50,"Map Editer");
			butHelp=new LightButton(442,411,140,50,"Help");
			butAbout=new LightButton(442,464,140,50,"About");
			butExit=new LightButton(442,517,140,50,"Exit");
			butStart.addActionListener(this);
			butEdit.addActionListener(this);
			butHelp.addActionListener(this);
			butAbout.addActionListener(this);
			butAbout.addActionListener(this);
			butExit.addActionListener(this);
			p.add(butStart);
			p.add(butEdit);
			p.add(butHelp);
			p.add(butAbout);
			p.add(butExit);
			getF().add(p);
			getF().setSize(1024,750);
			width=1030;height=765;
			getF().setDefaultCloseOperation(3);
			getF().setResizable(false);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)(toolkit.getScreenSize().getWidth()-f.getWidth())/2;
			int y = (int)(toolkit.getScreenSize().getHeight()-f.getHeight())/2;
			getF().setLocation(x, y );
			getF().setVisible(true);
			menuAudio = new Audio("E:\\workspace\\MyTankWar2.0\\src\\music\\main.wav");
			menuAudio.start();
		}
	
	public static void main(String[] args) {
		new TankClient();
	}

	/**
	 * 在面板上创建需要的组件的方法
	 * @author chenruiying
	 *
	 */
	private class MyPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		/** 背景图片*/
		private ImageIcon backgrond;
		
		
		/*
		 * 在面板上绘制背景的方法
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			backgrond=new ImageIcon(TankClient.class.getResource("/images/center.jpg"));
			g.drawImage(backgrond.getImage(), 0, 0, 1024, 780, 0, 0, backgrond.getIconWidth(), backgrond.getIconHeight(), null);
		}
	}
	
	/**
	 * 关闭窗口方法 
	 */
	public void over()
	{
		
		getF().setVisible(false);
		
	}
	
	/*
	 * 监听五个按钮并作出相应的反应的方法
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == butExit)
		{
			over();
		}
		else if(e.getSource() == butEdit)
		{
			new MapEdit(this);
		}
		else if(e.getSource() == butStart)
		{
			dialog();
			try {
				new TankWar(map,max,f,style);
			} catch (Exception e1) {
			}
		}
		else if(e.getSource() == butHelp)
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)(toolkit.getScreenSize().getWidth()-f.getWidth())/2;
			int y = (int)(toolkit.getScreenSize().getHeight()-f.getHeight())/2;
			help.setLocation(x + 300, y + 200);
			help.setResizable(false);
			help.setVisible(true);
		}
		else if(e.getSource() == butAbout)
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)(toolkit.getScreenSize().getWidth()-f.getWidth())/2;
			int y = (int)(toolkit.getScreenSize().getHeight()-f.getHeight())/2;
			about.setLocation(x + 320, y + 300);
			about.setResizable(false);
			about.setVisible(true);
		}
		
	}
	
	/**
	 * 游戏的设置窗口方法
	 */
	private void dialog() {
		set = new JDialog(f,true);
		set.setVisible(false);
		set.setTitle("游戏设置");
		set.setBounds(100, 100, 400, 480);
		ButtonGroup tankGroup=new ButtonGroup();
		final JRadioButton tank1=new JRadioButton("重型坦克：");
		JLabel heavy = new JLabel("防御型坦克设定");
		JLabel heavysize = new JLabel("攻击力A 防御S 速度B");
		heavysize.setForeground(Color.RED);
		heavy.setFont(new Font("宋体", 1, 20));
		heavysize.setFont(new Font("宋体", 1, 22));
		final JRadioButton tank2=new JRadioButton("主战坦克：");
		JLabel main = new JLabel("攻击型坦克设定");
		JLabel mainsize = new JLabel("攻击力S 防御B 速度A");
		mainsize.setForeground(Color.RED);
		main.setFont(new Font("宋体", 1, 20));
		mainsize.setFont(new Font("宋体", 1, 22));
		final JRadioButton tank3=new JRadioButton("轻型坦克：");
		JLabel light = new JLabel("速度型坦克设定");
		JLabel lightsize = new JLabel("攻击力A 防御B 速度S");
		lightsize.setForeground(Color.RED);
		light.setFont(new Font("宋体", 1, 20));
		lightsize.setFont(new Font("宋体", 1, 22));
		heavy.setBounds(150, 0, 150, 120);
		heavysize.setBounds(150, 35, 250, 100);
		main.setBounds(150, 85, 150, 120);
		mainsize.setBounds(150, 120, 250, 100);
		light.setBounds(150, 165, 150, 120);
		lightsize.setBounds(150, 200, 250, 100);
		tank1.setSelected(true);
		tankGroup.add(tank1);
		tankGroup.add(tank2);
		tankGroup.add(tank3);
		Font tankFont = new Font("黑体",3,20);
		tank1.setFont(tankFont);
		tank2.setFont(tankFont);
		tank3.setFont(tankFont);
		tank1.setBounds(0, 0, 150, 80);
		tank2.setBounds(0, 85, 150, 80);
		tank3.setBounds(0, 165, 150, 80);
		JPanel dp = new JPanel(null);
		File dir = new File("map");
		String mapNames[]=dir.list();
		final JComboBox list = new JComboBox();
		for (int i = 0; i < mapNames.length; i++) {
			list.addItem(mapNames[i]);			
		}
		list.setBounds(110, 300, 265, 30);
		JLabel labMap = new JLabel("地图：");
		labMap.setFont(new Font("宋体", 1, 20));
		labMap.setBounds(15, 300, 100, 30);
		JLabel labMax=new JLabel("敌军数量：");
		labMax.setFont(new Font("宋体", 1, 20));
		labMax.setBounds(15, 340, 130, 30);
		final JSlider slider = new JSlider(10, 30);
		slider.setBounds(150, 340, 230, 50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(2);
		final TextField showMax = new TextField(String.valueOf(slider.getValue()));
		showMax.setFont(new Font("", 0, 20));
		showMax.setBackground(Color.WHITE);
		showMax.setEditable(false);
		showMax.setBounds(110, 340, 30, 30);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				showMax.setText(String.valueOf(slider.getValue()));
			}
		});
		JButton butOk = new JButton("开始游戏");
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				max=slider.getValue();
				map=(String)list.getSelectedItem();
				set.setVisible(false);
				if(tank1.isSelected())	style=1;		
				else if(tank2.isSelected()) style=2;
				else if (tank3.isSelected()) style=3;
				over();
				menuAudio.stop();
			}
		});
		butOk.setBounds(130, 400, 140, 30);
		dp.add(tank1);
		dp.add(tank2);
		dp.add(tank3);
		dp.add(heavy);
		dp.add(main);
		dp.add(light);
		dp.add(heavysize);
		dp.add(mainsize);
		dp.add(lightsize);
		dp.add(butOk);
		dp.add(showMax);
		dp.add(labMap);
		dp.add(list);
		dp.add(labMax);
		dp.add(slider);
		set.add(dp);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)(toolkit.getScreenSize().getWidth()-f.getWidth())/2;
		int y = (int)(toolkit.getScreenSize().getHeight()-f.getHeight())/2;
		set.setLocation(x + 300, y + 180);
		set.setResizable(false);
		set.setVisible(true);		
	}
	
	public void setF(JFrame f) {
		this.f = f;
	}
	public JFrame getF() {
		return f;
	}
}
