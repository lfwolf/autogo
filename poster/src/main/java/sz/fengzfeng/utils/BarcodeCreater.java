package sz.fengzfeng.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.CodabarEncoder;
import org.jbarcode.encode.Code11Encoder;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.Code39ExtEncoder;
import org.jbarcode.encode.Code93Encoder;
import org.jbarcode.encode.Code93ExtEncoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.encode.Interleaved2of5Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.encode.MSIPlesseyEncoder;
import org.jbarcode.encode.PostNetEncoder;
import org.jbarcode.encode.Standard2of5Encoder;
import org.jbarcode.encode.UPCAEncoder;
import org.jbarcode.encode.UPCEEncoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.HeightCodedPainter;
import org.jbarcode.paint.UPCATextPainter;
import org.jbarcode.paint.UPCETextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import sun.misc.BASE64Encoder;

/**
 * 条形码创建，需添加jar包：jbarcode-0.2.8.jar
 * 
 * @author jianggujin
 * 
 */
public class BarcodeCreater {
	/** 用于生成条形码的对象 **/
	private JBarcode barcode = null;

	/**
	 * 构造方法
	 */
	public BarcodeCreater() {
		barcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(),
				EAN13TextPainter.getInstance());
		barcode.setBarHeight(17);
		barcode.setShowText(true);
		barcode.setCheckDigit(true);
		barcode.setShowCheckDigit(true);
	}

	/**
	 * 生成条形码文件
	 * 
	 * @param code 条形码内容
	 * @param file 生成文件
	 * @throws InvalidAtributeException
	 * @throws IOException
	 */
	public void write(String code, File file) throws IOException, InvalidAtributeException {
		ImageIO.write(toBufferedImage(code), "JPEG", file);
	}

	/**
	 * 生成条形码并写入指定输出流
	 * 
	 * @param code 条形码内容
	 * @param os   输出流
	 * @throws IOException
	 * @throws InvalidAtributeException
	 */
	public void write(String code, OutputStream os) throws IOException, InvalidAtributeException {
		ImageIO.write(toBufferedImage(code), "PNG", os);
	}

	/**
	 * 创建条形码的BufferedImage图像
	 * 
	 * @param code 条形码内容
	 * @return image
	 * @throws InvalidAtributeException
	 */
	public BufferedImage toBufferedImage(String code) throws InvalidAtributeException {
		return barcode.createBarcode(code);
	}

	public String toBase64(String code) {
		BufferedImage bi;
		try {
			bi = this.toBufferedImage(code);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", outputStream);
			BASE64Encoder encoder = new BASE64Encoder();

			return encoder.encode(outputStream.toByteArray());
		} catch (InvalidAtributeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public void setDefaultSize() throws InvalidAtributeException {
        // 尺寸，面积，大小
		barcode.setXDimension(Double.valueOf(0.65).doubleValue());
		// 高度 10.0 = 1cm 默认1.5cm
		barcode.setBarHeight(Double.valueOf(40).doubleValue());
        // 宽度率
		barcode.setWideRatio(Double.valueOf(2.0).doubleValue());
		barcode.setShowText(false);
	}

	/**
	 * 设置编码
	 * 
	 * @param encoder
	 */
	public void setEncoder(BarcodeEncoder encoder) {
		int val = encoder.ordinal();
		switch (val) {
		case 0:
			barcode.setEncoder(EAN13Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(EAN13TextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(false);
			barcode.setShowCheckDigit(false);
			break;
		case 1:
			barcode.setEncoder(UPCAEncoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(UPCATextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(false);
			barcode.setShowCheckDigit(true);
			break;
		case 2:
			barcode.setEncoder(EAN8Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(EAN8TextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			//barcode.setCheckDigit(false);
			//barcode.setShowCheckDigit(true);
			break;
		case 3:
			barcode.setEncoder(UPCEEncoder.getInstance());
			barcode.setTextPainter(UPCETextPainter.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(false);
			//barcode.setShowCheckDigit(true);
			break;
		case 4:
			barcode.setEncoder(CodabarEncoder.getInstance());
			barcode.setPainter(WideRatioCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			//barcode.setCheckDigit(true);
			//barcode.setShowCheckDigit(true);
			break;
		case 5:
			barcode.setEncoder(Code11Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(true);
			break;
		case 6:
			barcode.setEncoder(Code39Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			//barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(false);
			barcode.setShowCheckDigit(false);
			break;
		case 7:
			barcode.setEncoder(Code39ExtEncoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(false);
			barcode.setShowCheckDigit(false);
			break;
		case 8:
			barcode.setEncoder(Code93Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(GREETextPainter.getInstance());
			//barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(false);
			break;
		case 9:
			barcode.setEncoder(Code93ExtEncoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			//barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(false);
			break;
		case 10:
			barcode.setEncoder(Code128Encoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(false);
			break;
		case 11:
			barcode.setEncoder(MSIPlesseyEncoder.getInstance());
			barcode.setPainter(WidthCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(true);
			break;
		case 12:
			barcode.setEncoder(Standard2of5Encoder.getInstance());
			barcode.setPainter(WideRatioCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(false);
			break;
		case 13:
			barcode.setEncoder(Interleaved2of5Encoder.getInstance());
			barcode.setPainter(WideRatioCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(17);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(true);
			break;
		case 14:
			barcode.setEncoder(PostNetEncoder.getInstance());
			barcode.setPainter(HeightCodedPainter.getInstance());
			barcode.setTextPainter(BaseLineTextPainter.getInstance());
			barcode.setBarHeight(6);
			barcode.setShowText(true);
			barcode.setCheckDigit(true);
			barcode.setShowCheckDigit(false);
			break;
		}
	}

	/**
	 * 条形码编码方式
	 * 
	 * @author jianggujin
	 * 
	 */
	public enum BarcodeEncoder {
		EAN13, UPCA, EAN8, UPCE, Codabar, Code11, Code39, Code39Ext, Code93, Code93Ext, Code128, MSIPlessey,
		Standard2of5, Interleaved2of5, PostNet
	}
}