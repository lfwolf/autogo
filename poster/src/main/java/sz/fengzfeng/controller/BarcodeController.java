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
import javax.swing.ImageIcon;

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
public class BarcodeController {
	protected String suffix = "barcode";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/")
	public String index(Model model) throws InvalidAtributeException, IOException {
		String barcode = "6381594026991";
		List<String> imgs = new ArrayList<String>();
		List<String> codenames = new ArrayList<String>();

		BarcodeCreater creater = new BarcodeCreater();
		creater.setDefaultSize();
		String TEXTUp = "KF-35G(35392)/NhAa-3";
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
			
		    
			int width = 527;
			int height = 30;
			

			

			//
			
			//添加文字  
			BufferedImage bi = creater.toBufferedImage(barcode);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			//
			int fontsize = 33;
			int fontheight = bi.getHeight()*1/4;
			int fontpos_x1 = 18;
			int fontpos_y1 = 30;
			int fontpos_x2 = 25;
			int fontpos_y2 = bi.getHeight() -5;
			 
			
			Font font = new Font("817-CAI978", Font.TRUETYPE_FONT, fontsize);
			//设置透明背景
			//bi = g2.getDeviceConfiguration().createCompatibleImage(bi.getWidth(), bi.getWidth(), Transparency.TRANSLUCENT);  
			//g2=bi.createGraphics();  
			
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, bi.getWidth(), fontheight);

			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setFont(font);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTUp, fontpos_x1, fontpos_y1);

			
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, fontpos_y2 - 30, bi.getWidth(), fontheight);
			//clearRect(width, height, g2, bgcolor2, posy);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTDown + barcode, fontpos_x2, fontpos_y2);
			
			logger.info("fontheight:" + fontheight);

			
			try {
				ImageIO.write(bi, "png", f);
				g2.dispose();
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			
			//将图片背景设为透明
			sz.fengzfeng.utils.IamgeUtil.RemoveWhiteBackground(f.getAbsolutePath());
 
			BufferedImage barcodeimg = ImageIO.read(f);
			//合并图片
			int replace_start_x = 580;
			int replace_start_y = 850;
			float replace_angel = -0.35f; //旋转角度。
			float replace_shear = -0.021f; //向上倾斜
			File bgfile = new File(rootLocation +"/bg1.jpeg") ;
			BufferedImage big = ImageIO.read(bgfile);
			//int bgwidth = big.getWidth();
			//int bgheight = big.getHeight();
			Graphics2D g = big.createGraphics();
			g.shear(0, replace_shear);// 倾斜图像
			//g.rotate(Math.toRadians(replace_angel), replace_start_x,replace_start_y);
			g.drawImage(barcodeimg,replace_start_x,replace_start_y,bi.getWidth(),bi.getHeight(),null);
			g.dispose();
			ImageIO.write(big, "png", new File(rootLocation + "/merge.png"));
			
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
			
			new BarcodeController().index( null);
			System.out.println("done.");
		} catch (InvalidAtributeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
