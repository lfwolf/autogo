package sz.fengzfeng.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 



@Controller
@RequestMapping("/poster")
public class PosterController {

	@Autowired
	private Environment env;
	protected String suffix = "poster";
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static Path rootLocation = Paths.get("src/main/resources/static/images/poster/");
	
    @RequestMapping("/")
    public String index() {

    	
    	return suffix + "/index";
    }
    
    @ResponseBody
    @RequestMapping(value="/gen", method = RequestMethod.POST)
    public JSONObject gen(@RequestBody JSONObject jsonParam) {
    	
    	String address = jsonParam.getString("address");
    	int picindex = jsonParam.getIntValue("picindex");
    	String titlepos = jsonParam.getString("titlepos");
    	String[] titleposArray = titlepos.split(",");
    	int[] fontparas = new int[] {
        		Integer.parseInt(titleposArray[0]),
        		Integer.parseInt(titleposArray[1]),
        		Integer.parseInt(titleposArray[2])};
    	logger.info(address);
    	String name =  this.genPoster(address,picindex,fontparas);
    	JSONObject ret = new JSONObject();
    	ret.put("code", 0);
    	ret.put("url", "/images/poster/" + name);
 
    	return ret;
    	//return suffix + "/index";
    }
    
    private String genPoster(String address,int picindex,int[] fontParas) {
    	if(address == null ) {
    		address = "https://dictjson.tope365.com/ipeiyin/student/327307/3730/201909301201_share_ovi9gaoaep_15524656839485.html?studentID=327307";
    	}
    	int titleposX = fontParas[1];
    	int titleposY = fontParas[2];
    	int fontSize = fontParas[0];
    	List<String> imgs = new ArrayList<String>();
    	Document doc = null;
    	String strtime = null;
    	String bookid = null;
    	String booktitle = null;
    	String bookname = null;
        try {
            doc = Jsoup.connect(address).get();
            //logger.info("doc:" + doc);
            
            Element eleTitle = doc.getElementById("1");
            booktitle = eleTitle.text();
            logger.info(booktitle);
            
            Pattern p = Pattern.compile("studentEbookID\\s+=\\s+(\\d+);");
            Matcher m = p.matcher(doc.toString()); // 获取 matcher 对象
            if(m.find()) {
            	 logger.info("booid:" + m.group(1));
            	 bookid = m.group(1);
            }
            
            Document doc2 = Jsoup.connect("https://readappc.tope365.com/engApi/front/student_ebook/view/"+bookid).get();
            String retData = doc2.getElementsByTag("body").text().replaceFirst("jsonpCallback\\(", "");
            retData = retData.substring(0,retData.length()-1);
            JSONObject json = JSONObject.parseObject(retData);
            JSONObject jsonData = JSONObject.parseObject(json.getString("data"));
            
            
            //Element timeSpan = doc.getElementById("createTime");
            //logger.info("eCtime:" + timeSpan.text());

            strtime = jsonData.getString("createTime").substring(0,10);
            bookname = jsonData.getString("ebookName");
            logger.info("bookName:" + bookname);
            logger.info("createTime:" + strtime);
            
            
            Elements container = doc.getElementsByClass("img_box");
            Elements imgList = container.select("img");
            int i = 0;
            for(Element e : imgList) {
            	imgs.add(e.attr("src"));
            	logger.info(i + ":" + e.attr("src"));
            }
            
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }
       
        if(imgs.size() > 0 ) {
        	String imgurl = imgs.get(picindex-1);
        	String rootLocation2 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        	rootLocation2 += "static/images/poster";
        	String tmpPath = rootLocation2 + "/template.png";
        	String path = rootLocation2+ "/bg.jpeg";
        	String qrcode = rootLocation2+"/qrcode.jpeg";
        	String video = rootLocation2+"/video.png";
        	
        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMddHHmmss");
        	
        	SimpleDateFormat sdf =new SimpleDateFormat("yyyy.MM.dd");
        	
        	String time = strtime.length() >= 10 ? strtime.substring(0,10):sdf.format(new Date());
        	
        	logger.info("bookname len:",bookname);
        	String mergeName = booktitle.replace(' ', '-').replaceAll("\\?", "") +"-"+ simpleDateFormat.format(new Date()) + ".png";
        	String level = "";
        	if(bookname.length()> 6 &&  bookname.startsWith("Level ")  ) {
        		
        		level = "LV"+bookname.substring(6,8);
        		
        	}
        	mergeName = level + mergeName;
        	String merge = rootLocation2 + "/" + mergeName;
        	/*
        	logger.info("tmpPath:" + tmpPath);
        	logger.info("bg:" + path);
        	logger.info("qrcode:" + qrcode);
        	logger.info("merge:" + merge);
        	*/
        	orCode(address,qrcode);
        	
        	downloadPicture(imgurl,path);
        	File tmpfile = new File(tmpPath) ;
        	File bgfile = new File(path) ;
        	File qrfile = new File(qrcode);
        	File vdfile = new File(video);
			try {
				BufferedImage tmpBi = ImageIO.read(tmpfile);
				BufferedImage bgBi = ImageIO.read(bgfile);
				BufferedImage qrBi = ImageIO.read(qrfile);
				BufferedImage qrVideo = ImageIO.read(vdfile);

				
				Graphics2D g = tmpBi.createGraphics();
				
				//g.setPaint(new Color(248,253,247));
				//g.fillRect(281, 255, 200, 288);
				

				
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.drawImage(qrBi,380,30,150,150,null);
				g.drawImage(qrVideo,440,90,30,30,null);
				g.drawImage(bgBi,0,235,tmpBi.getWidth(),460,null);
				
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setPaint(Color.BLACK);
				Font font = new Font("Courier", Font.TRUETYPE_FONT, 22);
				g.setFont(font);
				//env.getProperty(key)
				g.drawString(time, 120, 180);
				if (picindex>1) {
					Font f = new Font("Arial Black", Font.TRUETYPE_FONT, fontSize);
					
					//g.setFont(f);
					
					//g.drawString(booktitle, titleposX, titleposY);
					
					FontRenderContext frc = new FontRenderContext(new AffineTransform(), false, false);
				    GlyphVector v = f.createGlyphVector(frc, booktitle);
				    Shape shape = v.getOutline();
				 
				    //Rectangle bounds = shape.getBounds();
				 
				    Graphics2D gg = (Graphics2D) g;
				    
				    gg.translate(titleposX, titleposY);
				    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				    gg.setColor(Color.WHITE);
				    gg.fill(shape);
				    gg.setColor(Color.BLUE.darker().darker());
				    gg.setStroke(new BasicStroke(1));
				    gg.draw(shape);
				    
				    
				}
				ImageIO.write(tmpBi, "png", new File(merge));
				g.dispose();
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				logger.error(e.getMessage(),e);
			}
			return mergeName;
        }
        return null;
    }
    
    
    private static boolean orCode(String content, String path) {
        /*
         * 图片的宽度和高度
         */
        int width = 300;
        int height = 300;
        // 图片的格式
        String format = "png";
        // 二维码内容
        // String content = "hello,word";

        // 定义二维码的参数
        HashMap hints = new HashMap();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 写入到本地
            Path file = new File(path).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	
            return false;
        }

    }
    
  //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
 
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
