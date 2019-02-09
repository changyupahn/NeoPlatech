package boassoft.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Thumbnail {

	/**
	 * 썸네일 생성 ( 이미지의 비율도 유지하며, 사이즈도 고정 )
	 * @param imgSource
	 * @param target
	 * @param newW
	 * @param newH
	 * @throws Exception
	 */
	public static void createThumbnail(String source, String target, int newW, int newH, HashMap<String,Object> rotateMap) throws Exception
    {
		BufferedImage buffer_original_image = ImageIO.read(new File(source));

        createThumbnail(buffer_original_image, target, newW, newH, rotateMap);
    }

	public static void createThumbnail(BufferedImage buffer_original_image, String target, int newW, int newH, HashMap<String,Object> rotateMap) throws Exception
    {
        int imgWidth = buffer_original_image.getWidth(null);
		int imgHeight = buffer_original_image.getHeight(null);
		int targetW = newW;
        int targetH = newH;
        int startX = 0;
		int startY = 0;

		if (rotateMap != null) {
			int rotateWidth = StringUtil.nvl(rotateMap.get("imgWidth"),0);
			int rotateHeight = StringUtil.nvl(rotateMap.get("imgHeight"),0);

			if (rotateWidth > 0 && rotateHeight > 0) {
				imgWidth = rotateWidth;
				imgHeight = rotateHeight;
			}
		}

		if( imgWidth >= imgHeight && imgWidth > newW ) { //가로형 이미지 썸네일 사이즈 구하기
			targetW = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgHeight);
		}else if( imgWidth < imgHeight && imgHeight > newH ) { //세로형 이미지 썸네일 사이즈 구하기
			targetW = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgHeight);
		}else {
			targetW = imgWidth;
			targetH = imgHeight;
		}

		if( targetW > newW ) { //세로형 이미지 너비 초과시
			targetW = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgHeight);
		}else if( targetH > newH ) { //가로형 이미지 높이 초과시
			targetW = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgHeight);
		}

		startX = (int)((newW/2) - (targetW/2)); // 이미지 가로 여백
		startY = (int)((newH/2) - (targetH/2)); // 이미지 세로 여백

		Image imgTarget = buffer_original_image.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
		int pixels[] = new int[targetW * targetH];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, targetW, targetH, pixels, 0, targetW);
		try {
			pg.grabPixels();
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage buffer_thumbnail_image = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = buffer_thumbnail_image.createGraphics();
		graphic.setBackground(Color.WHITE);
		graphic.clearRect(0, 0, newW, newH);
		graphic.dispose();
		buffer_thumbnail_image.setRGB(startX, startY, targetW, targetH, pixels, 0, targetW);

        ImageIO.write(buffer_thumbnail_image, "jpg", new File(target));
    }

	/**
	 * 썸네일 생성 ( 이미지의 비율을 유지하며, 사이즈는 비율에 따라 틀리게 나옴 )
	 * @param imgSource
	 * @param target
	 * @param newW
	 * @param newH
	 * @throws Exception
	 */
	public static CommonMap createThumbnail2(String source, String target, int newW, int newH, HashMap<String,Object> rotateMap) throws Exception
    {
		BufferedImage buffer_original_image = ImageIO.read(new File(source));

        return createThumbnail2(buffer_original_image, target, newW, newH, rotateMap);
    }

	public static CommonMap createThumbnail2(BufferedImage buffer_original_image, String target, int newW, int newH, HashMap<String,Object> rotateMap) throws Exception
    {
		CommonMap resultMap = new CommonMap();

        int imgWidth = buffer_original_image.getWidth(null);
		int imgHeight = buffer_original_image.getHeight(null);
		int targetW = newW;
        int targetH = newH;

        if (rotateMap != null) {
			int rotateWidth = StringUtil.nvl(rotateMap.get("imgWidth"),0);
			int rotateHeight = StringUtil.nvl(rotateMap.get("imgHeight"),0);

			if (rotateWidth > 0 && rotateHeight > 0) {
				imgWidth = rotateWidth;
				imgHeight = rotateHeight;
			}
        }

		if( imgWidth >= imgHeight && imgWidth > newW ) { //가로형 이미지 썸네일 사이즈 구하기
			targetW = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgHeight);
		}else if( imgWidth < imgHeight && imgHeight > newH ) { //세로형 이미지 썸네일 사이즈 구하기
			targetW = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgHeight);
		}else {
			targetW = imgWidth;
			targetH = imgHeight;
		}

		if( targetW > newW ) { //세로형 이미지 너비 초과시
			targetW = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newW / (double)imgWidth) * (double)imgHeight);
		}else if( targetH > newH ) { //가로형 이미지 높이 초과시
			targetW = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgWidth);
			targetH = (int)Math.floor(((double)newH / (double)imgHeight) * (double)imgHeight);
		}

		Image imgTarget = buffer_original_image.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
		int pixels[] = new int[targetW * targetH];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, targetW, targetH, pixels, 0, targetW);
		try {
			pg.grabPixels();
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage buffer_thumbnail_image = new BufferedImage(targetW, targetH, BufferedImage.TYPE_3BYTE_BGR);
		buffer_thumbnail_image.setRGB(0, 0, targetW, targetH, pixels, 0, targetW);
        ImageIO.write(buffer_thumbnail_image, "jpg", new File(target));

        resultMap.put("width", targetW);
        resultMap.put("height", targetH);

        return resultMap;
    }
}
