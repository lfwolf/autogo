package sz.fengzfeng.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jbarcode.encode.InvalidAtributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sz.fengzfeng.utils.BarcodeCreater;
import sz.fengzfeng.utils.BarcodeCreater.BarcodeEncoder;

@Controller
@RequestMapping("/barcode")
public class BarcodeController2 {
	protected String suffix = "barcode";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/")
	public String index(Model model) throws InvalidAtributeException, IOException {
		String barcode = "4P11796048839";
		List<String> imgs = new ArrayList<String>();
		List<String> codenames = new ArrayList<String>();

		BarcodeCreater creater = new BarcodeCreater();
		creater.setDefaultSize();
		String TEXTUp = "KF-35W/FNhV11-A1";
	    String TEXTDown = "GREE  ";
		for (BarcodeEncoder e : BarcodeEncoder.values()) {
			if( e != BarcodeEncoder.Code93)continue;
			creater.setEncoder(e);
			creater.setDefaultSize();
			
			//文件目录
	        Path rootLocation = Paths.get("src/main/resources/static/images");
	        if(Files.notExists(rootLocation)){
	            Files.createDirectories(rootLocation);
	        }
	        File f = new File(rootLocation +"/"+ barcode +".png") ;        // 实例化File类的对象，给出路径
	        
	        f.delete();
			//creater.write(barcode, f);
			
		    
			Font font = new Font("817-CAI978", Font.TRUETYPE_FONT, 28);
			
			 //设置透明  start
			
			//添加文字  
			BufferedImage bi = creater.toBufferedImage(barcode);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			//设置透明背景
			//bi = g2.getDeviceConfiguration().createCompatibleImage(bi.getWidth(), bi.getWidth(), Transparency.TRANSLUCENT);  
			//g2=bi.createGraphics();  
			
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, bi.getWidth(), bi.getHeight()*1/4);

            //Color bgcolor = new Color(182,181,194);
			//Color bgcolor2 = new Color(201,201,211);
			
			//clearRect(width, height, g2, bgcolor2, 0);

			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setFont(font);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTUp, 15, 25);
			
			
			
			int posy= bi.getHeight()*3/4+5;
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, posy, bi.getWidth(), bi.getHeight()*1/4);
			//clearRect(width, height, g2, bgcolor2, posy);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTDown + barcode, 15, posy+20);
			
			//替换背景颜色
			int[] rgb = new int[3];
			int width1 = bi.getWidth();
			int height1 = bi.getHeight();
			int minx = bi.getMinTileX();
			int miny = bi.getMinTileY();
			
			/*
			 * 遍历像素点，判断是否更换颜色
			 * */
			Color startcolor = new Color(219,219,219);
			Color endcolor = new Color(217,217,217);
			int range = 20;
			int step_r = (endcolor.getRed() - startcolor.getRed())/ range;
			int step_g = (endcolor.getGreen() - startcolor.getGreen())/range ;
			int step_b = (endcolor.getBlue() - startcolor.getBlue())/range ;
			int step_area = (width1 - minx)/range;
			int count = 0;
			step_r = step_r > 0 && step_r < 1 ? 1 : step_r;
			step_g = step_g > 0 && step_g < 1 ? 1 : step_g;
			step_b = step_b > 0 && step_b < 1 ? 1 : step_b;
			step_r = step_g = step_b = 0;
			logger.info("step/r/g/b:" + step_r + "/" + step_g + "/"+ step_b);
			logger.info("step_area/width1/minx:" + step_area + "/" + width1 + "/"+ minx);
			for (int i = minx; i < width1; i++) {
				
				count++;
				int step = count / step_area;
		 		int r = startcolor.getRed() + step_r*step;
		 		int g = startcolor.getGreen() + step_g*step;
		 		int b = startcolor.getBlue() + step_b*step;
		 		//最后2个区域设置为结束色。
		 		
		 		if( step >= range -1) {
		 			r = endcolor.getRed();
		 			g = endcolor.getGreen();
		 			b = endcolor.getBlue();
		 		}
		 		
		 		
		 		
		 		logger.info("step/r/g/b:" + step + "/"+ r + "/"+ g + "/" + b);
		 		
				for (int j = miny; j < height1; j++) {
					/*
					 * 换色
					 * */
					int pixel = bi.getRGB(i, j);
					rgb[0] = (pixel & 0xff0000) >>16;
					rgb[1] = (pixel & 0xff00) >>8;
				 	rgb[2] = (pixel & 0xff) ;
				 	
				 	if (rgb[0]>200 && rgb[1]>200 && rgb[2]>200) {
				 		int bgcolor = ((0xFF << 24)|(r << 16)|(g << 8)|b);
						bi.setRGB(i, j, bgcolor);
					
					}
				}
			}
			
			g2.dispose();
			try {
				ImageIO.write(bi, "png", f);
			} catch (IOException ee) {
				ee.printStackTrace();
			}
 
			//合并图片
			File bgfile = new File(rootLocation +"/bg2.jpeg") ;
			BufferedImage big = ImageIO.read(bgfile);
			//int bgwidth = big.getWidth();
			//int bgheight = big.getHeight();
			Graphics2D g = big.createGraphics();
			g.shear(0, +0.024);// 倾斜图像
			g.drawImage(bi,370,500,bi.getWidth()+30,bi.getHeight()+35,null);
			g.dispose();
			ImageIO.write(big, "png", new File(rootLocation + "/merge2.png"));
			
			String code = creater.toBase64(barcode);
			if (code != null) {

				imgs.add(code);
				codenames.add(e.toString());
			}
		}
		if ( model != null ) {
		model.addAttribute("textup", TEXTUp);
		model.addAttribute("imgsrc", "/images/"+barcode+".png");
		model.addAttribute("barcode", barcode);
		model.addAttribute("codenames", codenames.toArray());
		model.addAttribute("imgs", imgs);
		}

		return suffix + "/index";
	}
	
	 public static void main(String[] args) {
		try {
			
			new BarcodeController2().index( null);
			System.out.println("done.");
		} catch (InvalidAtributeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
