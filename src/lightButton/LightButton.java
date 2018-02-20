package lightButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * 游戏窗口的按钮类
 * @author chenruiying
 *
 */
public class LightButton extends JButton {

	private static final long serialVersionUID = 1L;
	/** x位置*/
	private float shadowOffsetX;
	/** y位置*/
    private float shadowOffsetY;
    
    private Color shadowColor = Color.BLACK;//设置颜色
    private int shadowDirection = 60;//设置阴影方向
    
    private Image normalButton, normalButtonPressed, buttonHighlight;//三种情况图片
    private int shadowDistance = 1;//阴影长度
    private Insets sourceInsets = new Insets(6, 7, 6, 8);//容器边框显示
    private Dimension buttonDimension = new Dimension(116, 35);//尺寸
    private Color buttonForeground = Color.WHITE;//背景
    private Font buttonFont = new Font("Arial", Font.BOLD, 22);//文字
 
    /**
     * 按钮类输入文本主方法
     * @param text 输入的文本
     */
    public LightButton(String text) {
        this();
        setText(text);
    }
    /**
     * 按钮类放置按钮主方法
     * @param x x位置
     * @param y y位置
     * @param width 宽度
     * @param height 高度
     * @param string 文本
     */
    public LightButton(int x, int y,int width,int height, String string) {
    	this();
		this.setBounds(x, y, width, height);
		 setText(string);
	}
    
    /**
     * 按钮类键盘监听方法
     * @param a 键盘监听
     */
    public LightButton(Action a) {
        this();
        setAction(a);
    }
    
    /**
     * 按钮类主方法
     */
    public LightButton() {
        computeShadow();

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setForeground(buttonForeground);
        setFont(buttonFont);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        
        normalButton = loadImage("/lightButton/button-normal.png");
        normalButtonPressed = loadImage("/lightButton/button-normal-pressed.png");
        buttonHighlight = loadImage("/lightButton/header-halo.png");
        
        setUI(new BasicButtonUI() {
            @Override
            public Dimension getMinimumSize(JComponent c) {
                return getPreferredSize(c);
            }
            
            @Override
            public Dimension getMaximumSize(JComponent c) {
                return getPreferredSize(c);
            }
            
            @Override
            public Dimension getPreferredSize(JComponent c) {
                Insets insets = c.getInsets();
                Dimension d = new Dimension(buttonDimension);
                d.width += insets.left + insets.right;
                d.height += insets.top + insets.bottom;
                return d;
            }
        });
    }    

    /**
     * 按钮类读取图片方法
     * @param fileName 文件名
     * @return 读取到该图片就返回该图片，没有返回空
     */
	private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(LightButton.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }
   
	/**
	 * 按钮类阴影方法
	 */
    private void computeShadow() {
        double rads = Math.toRadians(shadowDirection);
        shadowOffsetX = (float) Math.cos(rads) * shadowDistance;
        shadowOffsetY = (float) Math.sin(rads) * shadowDistance;
    }
    
    /**
     * 按钮类使用图片主方法 
     * @param armed 是否按下
     * @return 按下返回normalButtonPressed，否则返回normalButton
     */
    private Image getImage(boolean armed) {
        return armed ? normalButtonPressed : normalButton;
    }
    /*
     * 画出图片方法
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        ButtonModel m = getModel();
        Insets insets = getInsets();
        
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        
        tileStretchPaint(g2,this,(BufferedImage) getImage(m.isArmed()), sourceInsets);
        
        if (getModel().isRollover()) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(buttonHighlight,
                    insets.left + 2, insets.top + 2,
                    width - 4, height - 4, null);
        }
        
        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();
        
        int x = (int) (getWidth() - insets.left - insets.right -
                bounds.getWidth()) / 2;
        int y = (getHeight() - insets.top - insets.bottom -
                 fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;
        
        if (m.isArmed()) {
            x += 1;
            y += 1;
        }
        
        g2.setColor(shadowColor);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        g2.setColor(getForeground());
        layout.draw(g2, x, y);
    }
    
   
    /**
     * 图片拉伸的方法
     * @param g 画笔
     * @param comp 组件
     * @param img 图片
     * @param ins 来源
     */
    public static void tileStretchPaint(Graphics g, 
                JComponent comp,
                BufferedImage img,
                Insets ins) {
        
        int left = ins.left;
        int right = ins.right;
        int top = ins.top;
        int bottom = ins.bottom;
        
        // top
        g.drawImage(img,
                    0,0,left,top,
                    0,0,left,top,
                    null);
        g.drawImage(img,
                    left,                 0, 
                    comp.getWidth() - right, top, 
                    left,                 0, 
                    img.getWidth()  - right, top, 
                    null);
        g.drawImage(img,
                    comp.getWidth() - right, 0, 
                    comp.getWidth(),         top, 
                    img.getWidth()  - right, 0, 
                    img.getWidth(),          top, 
                    null);

        // middle
        g.drawImage(img,
                    0,    top, 
                    left, comp.getHeight()-bottom,
                    0,    top,   
                    left, img.getHeight()-bottom,
                    null);
        
        g.drawImage(img,
                    left,                  top, 
                    comp.getWidth()-right,      comp.getHeight()-bottom,
                    left,                  top,   
                    img.getWidth()-right,  img.getHeight()-bottom,
                    null);
         
        g.drawImage(img,
                    comp.getWidth()-right,     top, 
                    comp.getWidth(),           comp.getHeight()-bottom,
                    img.getWidth()-right, top,   
                    img.getWidth(),       img.getHeight()-bottom,
                    null);
        
        // bottom
        g.drawImage(img,
                    0,comp.getHeight()-bottom, 
                    left, comp.getHeight(),
                    0,img.getHeight()-bottom,   
                    left,img.getHeight(),
                    null);
        g.drawImage(img,
                    left,                    comp.getHeight()-bottom, 
                    comp.getWidth()-right,        comp.getHeight(),
                    left,                    img.getHeight()-bottom,   
                    img.getWidth()-right,    img.getHeight(),
                    null);
        g.drawImage(img,
                    comp.getWidth()-right,     comp.getHeight()-bottom, 
                    comp.getWidth(),           comp.getHeight(),
                    img.getWidth()-right, img.getHeight()-bottom,   
                    img.getWidth(),       img.getHeight(),
                    null);
    }

}
