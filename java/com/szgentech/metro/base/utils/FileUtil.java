package com.szgentech.metro.base.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * @author hank
 *
 *         2016年4月14日
 */
public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	/**
	 * 读文本内容
	 * 
	 * @param filePath
	 *            eg://d:\aa\bb.txt
	 * @return filePath为空或解析异常返回null，否则返回文本内容
	 */
	public static String getContent(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		FileSystemResource resource = new FileSystemResource(filePath);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读文本内容:"+e.getMessage());
		}
		return null;
	}

	/**
	 * 文件上传至目标文件夹下的当前日期的子目录下
	 * 
	 * @param file
	 *            文件上传对象
	 * @param destDirectory
	 *            目标文件夹
	 * @return
	 */
	public static String fileUpload(MultipartFile file, String destDirectory) {
		String subdestDirectory = new SimpleDateFormat("yyyyMMdd").format(new Date());
		File f = new File(destDirectory + "/" + subdestDirectory);
		f.mkdirs();
		String formFilename = file.getOriginalFilename();
		int index = formFilename.lastIndexOf(".");
		String filename = "";
		if (index > 0) {
			String suffix = formFilename.substring(formFilename.lastIndexOf("."));
			filename = (System.currentTimeMillis() + "-" + (int) (Math.random() * 100000)) + suffix;
			try {
				InputStream is = file.getInputStream();
				OutputStream output = new FileOutputStream(destDirectory + "/" + subdestDirectory + "/" + filename);
				int bytesReader = 0;
				byte[] readbuffer = new byte[8192];
				while ((bytesReader = is.read(readbuffer, 0, 8192)) != -1) {
					output.write(readbuffer, 0, bytesReader);
				}
				output.close();
			} catch (IOException e) {
				logger.error("文件上传至目标文件夹下的当前日期的子目录下:"+e.getMessage());
				return null;
			}
		} else {
			return null;
		}
		return destDirectory + "/" + subdestDirectory + "/" + filename;
	}

	/**
	 * 按原图宽高比例生成缩略图
	 * 
	 * @param filename
	 *            源图片路径 eg:d:/ss/xx.jpg
	 * @param smallPicNode
	 *            存放小图片的文件夹名字 eg：small，那么小图片路径就为d:/ss/small/xx.jpg
	 * @param smallWidth
	 *            小图片宽度
	 * @param smallHeight
	 *            小图片高度
	 * @return 返回小图相对路径
	 */
	public static String createSmallPic(String bigPicPath, String filename, int smallWidth, int smallHeight) {

		// 创建小图路径
		File sPath = new File(filename);
		if (!sPath.exists())
			sPath.mkdir();

		try {
			File fi = new File(bigPicPath); // 大图文件
			File fo = new File(filename); // 将要转换出的小图文件

			AffineTransform transform = new AffineTransform();
			BufferedImage bis;

			bis = ImageIO.read(fi);

			int w = bis.getWidth();
			int h = bis.getHeight();

			double sx = (double) smallWidth / w;
			double sy = (double) smallHeight / h;
			// 判断是横向图形还是坚向图形
			if (w > h) { // 横向图形

				if ((int) (sx * h) > smallHeight) { // 比较高不符合高度要求,就按高度比例

					sx = sy;
					smallWidth = (int) (w * sx);
				} else {
					sy = sx;
					smallHeight = (int) (h * sy);
				}
			} else {
				if ((int) (sy * w) > smallWidth) {
					sy = sx;
					smallHeight = (int) (h * sy);
				} else {
					sx = sy;
					smallWidth = (int) (w * sx);
				}
			}

			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(smallWidth, smallHeight, BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "JPEG", fo);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("按原图宽高比例生成缩略图:"+e.getMessage());
			return "";
		}
		return filename;
	}

	/**
	 * 根据路径创建目录
	 * 
	 * @param path
	 */
	public static void createDir(String path) {
		if (!StringUtils.isEmpty(path)) {
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param mf
	 * @param savePath
	 * @param dateStr
	 * @param fileName
	 * @return
	 */
	public static Boolean uploadImg(MultipartFile mf, String savePath, String dateStr, String filename) {

		// 保存路径
		FileUtil.createDir(savePath + "/" + dateStr);

		try {
			saveFileFromInputStream(mf.getInputStream(), savePath, dateStr, filename);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传图片:"+e.getMessage());
			return false;
		}
	}

	/**
	 * 上传文件 MultipartFile
	 * 
	 * @param mf  MultipartFile
	 * @param savePath  保存路径，文件名结尾
	 * @param filename  保存文件名，包含后缀
	 * @return
	 * @throws Exception
	 */
	public static Boolean uploadFile(MultipartFile mf, String savePath, String filename) throws Exception {
		FileUtil.createDir(savePath);
		String picPath = savePath + "/" + filename;
		FileOutputStream fs = null;
		InputStream stream = mf.getInputStream();
		try {
			fs = new FileOutputStream(picPath);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传文件 MultipartFile:"+e.getMessage());
			return false;
		} finally {
			if (fs != null) {
				fs.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 上传文件 InputStream
	 * 
	 * @param stream     InputStream
	 * @param savePath   保存路径，文件名结尾
	 * @param filename   保存文件名，包含后缀
	 * @return
	 * @throws Exception
	 */
	public static Boolean uploadFile(InputStream stream, String savePath, String filename) throws Exception {
		FileUtil.createDir(savePath);
		String picPath = savePath + "/" + filename;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(picPath);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传文件 InputStream:"+e.getMessage());
			return false;
		} finally {
			if (fs != null) {
				fs.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static void saveFileFromInputStream(InputStream stream, String path, String dateStr, String filename)
			throws IOException {
		String bigPicPath = path + "/" + dateStr + "/" + filename;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(bigPicPath);
			byte[] buffer = new byte[1024 * 1024];
			// int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				// bytesum+=byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (Exception e) {
			logger.error("saveFileFromInputStream:"+e.getMessage());
		} finally {
			if (fs != null)
				fs.close();
			if (stream != null)
				stream.close();
		}
		String smallPicPath = path + "/" + "small" + "/" + dateStr + "/" + filename;
		FileUtil.createDir(path + "/" + "small" + "/" + dateStr);
		FileUtil.createSmallPic(bigPicPath, smallPicPath, 115, 63);
	}

	/**
	 * 检查图片后缀是否正确
	 * 
	 * @param fileExt
	 * @return
	 */
	public static boolean checkImageFileExt(String fileExt) {
		String[] allowExt = new String[] { "gif", "jpg", "png", "PNG" };
		for (int i = 0; i < allowExt.length; i++) {
			if (allowExt[i].equals(fileExt)) {
				return true;
			}
		}
		return false;
	}

}
