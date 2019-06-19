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
			Font font = new Font("817-CAI978", Font.TRUETYPE_FONT, 28);
			
			//添加文字
			BufferedImage bi = creater.toBufferedImage(barcode);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			//替换背景颜色
			int[] rgb = new int[3];
			int width1 = bi.getWidth();
			int height1 = bi.getHeight();
			int minx = bi.getMinTileX();
			int miny = bi.getMinTileY();
			/*
			 * 遍历像素点，判断是否更换颜色
			 * */
			for (int i = minx; i < width1; i++) {
				for (int j = miny; j < height1; j++) {
					/*
					 * 换色
					 * */
					int pixel = bi.getRGB(i, j);
					rgb[0] = (pixel & 0xff0000) >>16;
					rgb[1] = (pixel & 0xff00) >>8;
				 	rgb[2] = (pixel & 0xff) ;
				 	
				 	if (rgb[0]>200 && rgb[1]>200 && rgb[2]>200) {
						bi.setRGB(i, j, 0xBABCC8);
					}
				}
			}
            Color bgcolor = new Color(182,181,194);
			Color bgcolor2 = new Color(197,197,207);
			g2.setBackground(bgcolor);
			g2.clearRect(0, 0, width-width/2, height);
			g2.setBackground(bgcolor2);
			g2.clearRect(width-width/2, 0, width-width/2, height);
			
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setFont(font);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTUp, 15, 25);
			
			
			
			int posy= bi.getHeight()*3/4+5;
			g2.setPaint(bgcolor);
			g2.fillRect(0, posy, bi.getWidth(), bi.getHeight()*1/4);
			g2.setPaint(Color.BLACK);
			g2.drawString(TEXTDown + barcode, 15, posy+20);
			g2.dispose();
			try {
				ImageIO.write(bi, "png", f);
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			
			//合并图片
			File bgfile = new File(rootLocation +"/bg1.jpeg") ;
			BufferedImage big = ImageIO.read(bgfile);
			//int bgwidth = big.getWidth();
			//int bgheight = big.getHeight();
			Graphics2D g = big.createGraphics();
			g.shear(0, -0.024);// 倾斜图像
			g.drawImage(bi,580,850,bi.getWidth()+30,bi.getHeight()+35,null);
			g.dispose();
			ImageIO.write(big, "jpeg", new File(rootLocation + "/merge.jpeg"));
			
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
