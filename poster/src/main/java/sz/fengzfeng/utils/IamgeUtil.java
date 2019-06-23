package sz.fengzfeng.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IamgeUtil {
	private static Logger logger = LoggerFactory.getLogger(IamgeUtil.class);
	public static void RemoveWhiteBackground(String path) {
        try {  
            BufferedImage image = ImageIO.read(new File(path));  
            ImageIcon imageIcon = new ImageIcon(image);  
            BufferedImage bufferedImage = new BufferedImage(  
                    imageIcon.getIconWidth(), imageIcon.getIconHeight(),  
                    BufferedImage.TYPE_4BYTE_ABGR);  
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();  
            g2D.drawImage(imageIcon.getImage(), 0, 0,  
                    imageIcon.getImageObserver());  
            int alpha = 0;  
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage  
                    .getHeight(); j1++) {  
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage  
                        .getWidth(); j2++) {  
                    int rgb = bufferedImage.getRGB(j2, j1);
                    Color color = new Color(rgb, true);
                    // 如果已经透明了就不再处理
                    if(color.getAlpha() == 0){
                        continue;
                    }
                    if (colorInRange(rgb))  
                        alpha = 0;  
                    else  
                        alpha = 255;  
                    rgb = (alpha << 24) | (rgb & 0x00ffffff);  
                    bufferedImage.setRGB(j2, j1, rgb);  
                }  
            }  
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());  
            // 生成图片为PNG  
            String outFile = path.substring(0, path.lastIndexOf("."));  
            ImageIO.write(bufferedImage, "png", new File(outFile + ".png"));  
        } catch (IOException e) {
           logger.error(e.getMessage());
        }  
    }  
    
    public static boolean colorInRange(int color) {  
        int red = (color & 0xff0000) >> 16;  
        int green = (color & 0x00ff00) >> 8;  
        int blue = (color & 0x0000ff);  
        if (red >= color_range && green >= color_range && blue >= color_range)  
            return true;  
        return false;  
    }  
    // 根据需要自己调整这个阈值，如果只需要去掉纯白色背景就设为255
    public static int color_range = 200;

}
