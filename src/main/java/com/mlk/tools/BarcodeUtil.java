package com.mlk.tools;

import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.pdf.BarcodePDF417;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 条形码工具类
 *
 * @author tangzz
 * @createDate 2015年9月17日
 *
 */
public class BarcodeUtil {

	/**
	 * 生成文件
	 *
	 * @param msg
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static File generateFile(String msg, String path) throws FileNotFoundException, IOException {
		File file = new File(path);
		/*
		 * try { generate(msg, new FileOutputStream(file)); } catch
		 * (FileNotFoundException e) { throw new RuntimeException(e); }
		 */
		generateBarCode(msg, file);
		return file;
	}

	public static File generateBarCode(String msg, File file) throws FileNotFoundException, IOException {
		// File file = new File(path);
		BarcodePDF417 pdf = new BarcodePDF417();
		pdf.setText(msg.getBytes("GBK"));
		Image pdfImg = pdf.createAwtImage(Color.black, Color.white);
		BufferedImage img = new BufferedImage((int) pdfImg.getWidth(null), (int) pdfImg.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();

		g.drawImage(pdfImg, 0, 0, Color.white, null);
		// OutputStream os = new BufferedOutputStream(new
		// FileOutputStream("d:/pdf417.bmp"));
		ImageIO.write(img, "PNG", new FileOutputStream(file));

		return null;
	}

	/**
	 * 生成字节
	 *
	 * @param msg
	 * @return
	 */
	public static byte[] generate(String msg) {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		generate(msg, ous);
		return ous.toByteArray();
	}

	/**
	 * 生成到流
	 *
	 * @param msg
	 * @param ous
	 */
	public static void generate(String msg, OutputStream ous) {
		if (StrUtil.isEmpty(msg) || ous == null) {
			return;
		}

		Code39Bean bean = new Code39Bean();

		// 精细度
		final int dpi = 150;
		// module宽度
		final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

		// 配置对象
		bean.setModuleWidth(moduleWidth);
		bean.setWideFactor(3);
		bean.doQuietZone(false);

		String format = "image/png";
		try {

			// 输出到流
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
					false, 0);

			// 生成条形码
			bean.generateBarcode(canvas, msg);

			// 结束绘制
			canvas.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String msg = "jgfsfa";
		String path = "barcode.png";
		//generateFile(msg, path);
	}

}