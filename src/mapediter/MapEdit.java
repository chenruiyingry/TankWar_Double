package mapediter;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import lightButton.LightButton;
import tankwar.Audio;
import tankwar.TankClient;
import tankwar.TankWar;


/**
 * 坦克大战的地图编辑器类
 * @author chenruiying
 *
 */
public class MapEdit implements ActionListener{
	public static final int COLS = TankWar.GAME_WIDTH / 60;//横轴
	public static final int ROWS = TankWar.GAME_HEIGHT / 60;//竖轴
	private JDialog f;//窗体
	private JPanel p;//面板
	private ButtonX[][] but;//按下数组
	private Image image[];//图片数组
	private JPanel pp;//面板
	private JButton butLoad;//读取按钮
	private JButton butSave;//保存按钮
	private JButton butExit;//退出按钮
	private FileNameExtensionFilter fs;//文件名
	private JFileChooser fc;//选择文件
	private final TankClient tc;//得到一个登录器
	
	/**
	 * 自定义地图的各种组件方法
	 * @param tc 实例化对象
	 */
	public MapEdit(TankClient tc) {
		this.tc = tc;
		f = new JDialog(tc.getF(),true);
		f.setTitle("自定义地图  多次点击方块切换材料");
		fc = new JFileChooser();
		fs = new FileNameExtensionFilter("Map file", "map");
		fc.setFileFilter(fs);
		fc.setCurrentDirectory(new File("map"));
		p = new JPanel(new GridLayout(ROWS,COLS));
		pp = new JPanel(null);
		p.setBounds(0, 0,TankWar.GAME_WIDTH , TankWar.GAME_HEIGHT);
		image=new Image[8];
		image[0] = new ImageIcon(MapEdit.class.getResource("/images/backGround.png")).getImage();
		image[1] = new ImageIcon(MapEdit.class.getResource("/images/walls.gif")).getImage();
		image[2] = new ImageIcon(MapEdit.class.getResource("/images/metals.gif")).getImage();
		image[3] = new ImageIcon(MapEdit.class.getResource("/images/grass.png")).getImage();
		image[4] = new ImageIcon(MapEdit.class.getResource("/images/water.gif")).getImage();
		image[5] = new ImageIcon(MapEdit.class.getResource("/images/born_1.png")).getImage();
		image[6] = new ImageIcon(MapEdit.class.getResource("/images/born_enemy_1.png")).getImage();
		image[7] = new ImageIcon(MapEdit.class.getResource("/images/home.jpg")).getImage();
		but = new ButtonX[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j <COLS; j++) {
				but[i][j]=new ButtonX(i, j);
				but[i][j].addActionListener(this);
				p.add(but[i][j]);
			}
		}
		butLoad=new LightButton(250, TankWar.GAME_HEIGHT + 2, 150, 50, "Load Map File");
		butSave=new LightButton(450, TankWar.GAME_HEIGHT + 2, 150, 50, "Save Map File");
		butExit=new LightButton(650, TankWar.GAME_HEIGHT + 2, 150, 50, "Exit Editer");
		butLoad.addActionListener(this);
		butSave.addActionListener(this);
		butExit.addActionListener(this);
		pp.add(butLoad);
		pp.add(butSave);
		pp.add(butExit);
		pp.add(p);
		f.add(pp);
		f.setSize(TankWar.GAME_WIDTH, TankWar.GAME_HEIGHT+100);
		f.setResizable(false);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)(toolkit.getScreenSize().getWidth() - f.getWidth()) / 2;
		int y = (int)(toolkit.getScreenSize().getHeight() - f.getHeight()) / 2;
		f.setLocation(x, y);
		f.setVisible(true);
	}
	
	/**
	 * 自定义地图的按下键盘反应的方法
	 * @author chenruiying
	 *
	 */
	private class ButtonX extends JButton {
		private static final long serialVersionUID = 1L;
		private int sr;//横轴
		private int sc;//竖轴
		private int w;//宽
		private int h;//高
		private int count;//质量
		/**
		 * 切换材料方法
		 * @param sr 横轴位置
		 * @param sc 竖轴位置
		 */
		public ButtonX(int sr,int sc) {
			this.sr = sr;
			this.sc = sc;
			setCount(0);
			w = image[0].getWidth(null) / (COLS);
			h = image[0].getHeight(null) / (ROWS);
		}
		/*
		 * 绘出点击后的图片的方法
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(getCount() == 0) {
				g.drawImage(image[0], 0, 0, this.getWidth(), this.getHeight(), w * sc, sr * h, w * sc + w, sr * h +h, null);
			} else {
				g.drawImage(image[getCount()], 0, 0, this.getWidth(), this.getHeight(), 0, 0, 60, 60, null);
			}
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getCount() {
			return count;
		}
	}
	
	/*
	 * 点击按钮后的反应的方法
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("resource")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == butLoad) {
			fc.showOpenDialog(f);
			FileInputStream read = null;
			try {
				read = new FileInputStream(fc.getSelectedFile());
			} catch (Exception e1) {return;}
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j <COLS; j++) {
					try {
						but[i][j].setCount(read.read());
						but[i][j].repaint();
					} catch (Exception e1) {return;}
				}
			}
		} else if(e.getSource() == butSave) {
			int c5 = 0,c6 = 0,c7 = 0;
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j <COLS; j++) {
					switch (but[i][j].count) {
					case 5:
						c5++;
						break;
					case 6:
						c6++;
						break;
					case 7:
						c7++;
						break;
					}
				}
			}
				if(c5 != 2) {
					JOptionPane.showMessageDialog(f, "必须有有两个个我方出生点（蓝毯子）");
					return;
				} if(c6 < 1) {
					JOptionPane.showMessageDialog(f, "必须有超过一个的敌方出生点（红毯子）");
					return;
				} if(c7 != 1) {
					JOptionPane.showMessageDialog(f, "必须有且只有一个我方基地");
					return;
				}
			fc.showSaveDialog(f);
			FileOutputStream write = null;
			try {
				write = new FileOutputStream(fc.getSelectedFile());
			} catch (Exception e1) {return;}
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j <COLS; j++) {
					try {
						write.write(but[i][j].getCount());
					} catch (Exception e1) {return;}
				}
			}
			try {
				write.close();
			} catch (Exception e1) {}
		} else if(e.getSource() == butExit) {
			f.setVisible(false);
			tc.getF().setVisible(true);
		} else {
			ButtonX b = (ButtonX)e.getSource();
			if(b.getCount()!=7) {
				b.setCount(b.getCount()+1);
			} else {
				b.setCount(0);
			}
			b.repaint();
		}
	}
}
