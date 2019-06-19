package sz.fengzfeng.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GREETextPainter implements org.jbarcode.paint.TextPainter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 设置条形码字体样式
    //private static final String FONT_FAMILY = "Microsoft Sans Serif";//"";
	//"817-CAI978"
    private static final String FONT_FAMILY = "817-CAI978";
    // 设置条形码字体大小
    //private static final int FONT_SIZE = 14;
    private static final int FONT_SIZE = 14;
    // 条码上端文字
    private String TEXTUp = "KF-26W/NhR01-3";
    private String TEXTDown = "GREE  ";
    
    // 条码下端文字
    private static GREETextPainter instance = new GREETextPainter();
    
    
    public static GREETextPainter getInstance() {
        return instance;
    }

	@Override
	public void paintText(BufferedImage barCodeImage, String text, int width) {
		// TODO Auto-generated method stub
        int imgwidth= barCodeImage.getWidth();
        int imgheight = barCodeImage.getHeight();
        logger.info("width: "  + width);
        logger.info("imgwidth: "  + imgwidth);
        logger.info("imgheight: " + imgheight);
        
		// 绘图
		Graphics2D g2d = (Graphics2D)barCodeImage.getGraphics();
		
		
		//
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	      g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
	      g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 140);
	      g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
	      g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        // 条形内容上下的颜色
        g2d.setColor(Color.green);
        g2d.fillRect(0, 0, barCodeImage.getWidth(), barCodeImage.getHeight()*1/4);
        // 创建字体
        Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE * width);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int hgt = metrics.getHeight();
        int adv = metrics.stringWidth(text);
        logger.info("font height:"+ hgt);
        logger.info("font weight:"+ adv);
        Dimension size = new Dimension(adv+2, hgt+2);
        		
        g2d.setFont(font);
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        /*
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 140);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        */
        //    显示的字体颜色（包括条形码下方的数字）
        //g2d.setPaint(new Color(0, 0, 0, 64));//阴影颜色
        //g2d.drawString(TEXTUp, 8, metrics.getAscent());//先绘制阴影
        g2d.setColor(Color.BLACK);
        g2d.drawString(TEXTUp, 8, metrics.getAscent());
      
        // 绘制下文本
        int posy= barCodeImage.getHeight()*3/4+5;
        g2d.setColor(Color.green);
        g2d.fillRect(0,posy,  barCodeImage.getWidth(), barCodeImage.getHeight()*1/4);
        g2d.setPaint(new Color(0, 0, 0, 64));//阴影颜色
        g2d.drawString(TEXTDown + text, 10, posy+10);//先绘制阴影
        g2d.setColor(Color.BLACK);
        g2d.drawString(TEXTDown + text, 10, posy+10);

    }
	


}
