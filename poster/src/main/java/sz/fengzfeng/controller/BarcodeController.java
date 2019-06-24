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

import sz.fengzfeng.datamodel.BarcodeModel;
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

		List<BarcodeModel> modelList = new ArrayList<BarcodeModel>();
		BarcodeModel in = new BarcodeModel();
		in.setText_up("KF-35G(35392)NhAa-3");
		in.setFontsize(33);
		in.setReplace_start_x(580);
		in.setReplace_start_y(850);
		in.setBgpath(Paths.get("src/main/resources/static/images/bg1.jpeg").toString());
		in.setShear(-0.021f);
		in.setBarcode(barcode);
		in.setName("in");
		modelList.add(in);
		
		
		BarcodeModel out = new BarcodeModel();
		out.setText_up("KFR-26W/NB01-3");
		out.setFontsize(28);
		out.setReplace_start_x(460);
		out.setReplace_start_y(750);
		out.setShear(0.025f);
		out.setBgpath("/Users/lifeng/Documents/work/二维码项目/图片模版/20190624/bg2.jpg");
		out.setBarcode(barcode);
		out.setName("out");
		modelList.add(out);

		modelList.forEach(m ->{
			try {
				generate(m);
			} catch (InvalidAtributeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		/*
		if ( model != null ) {
		model.addAttribute("textup", TEXTUp);
		model.addAttribute("imgsrc", "/images/"+barcode+".png");
		model.addAttribute("barcode", barcode);
		model.addAttribute("codenames", codenames.toArray());
		model.addAttribute("imgs", imgs);
		}
		 */
		return suffix + "/index";
	}



	private void generate(BarcodeModel barcode) throws InvalidAtributeException, IOException {
		List<String> imgs = new ArrayList<String>();
		List<String> codenames = new ArrayList<String>();

		BarcodeModel demo = barcode;

		BarcodeCreater creater = new BarcodeCreater();
		creater.setDefaultSize();
		String TEXTUp = demo.getText_up();
	    String TEXTDown = demo.getText_down();
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

			//添加文字  
			BufferedImage bi = creater.toBufferedImage(barcode.getBarcode());
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			//
			int fontsize = demo.getFontsize();
			int fontheight = bi.getHeight()*1/4;
			int fontpos_x1 = 18;
			int fontpos_y1 = 30;
			int fontpos_x2 = 25;
			int fontpos_y2 = bi.getHeight() -5;
			 
			
			Font font = new Font("817-CAI978", Font.PLAIN, fontsize);
			//设置透明背景
			//bi = g2.getDeviceConfiguration().createCompatibleImage(bi.getWidth(), bi.getWidth(), Transparency.TRANSLUCENT);  
			//g2=bi.createGraphics();  
			
			fontpos_x1 += 5;
			
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
			g2.drawString(TEXTDown + barcode.getBarcode(), fontpos_x2, fontpos_y2);
			
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
			int replace_start_x = demo.getReplace_start_x();
			int replace_start_y = demo.getReplace_start_y();
			
			//for bg2;
			
			float replace_angel = -0.35f; //旋转角度。
			float replace_shear = demo.getShear(); //向上倾斜
			File bgfile = new File(demo.getBgpath()) ;
			BufferedImage big = ImageIO.read(bgfile);
			//int bgwidth = big.getWidth();
			//int bgheight = big.getHeight();
			Graphics2D g = big.createGraphics();
			g.shear(0, replace_shear);// 倾斜图像
			//g.rotate(Math.toRadians(replace_angel), replace_start_x,replace_start_y);
			g.drawImage(barcodeimg,replace_start_x,replace_start_y,bi.getWidth(),bi.getHeight(),null);
			g.dispose();
			ImageIO.write(big, "jpeg", new File("/Users/lifeng/Documents/work/二维码项目/图片模版/20190624/"+ barcode.getName() +".jpg"));
			
			String code = creater.toBase64(barcode.getBarcode());
			if (code != null) {

				imgs.add(code);
				codenames.add(e.toString());
			}
		}
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
