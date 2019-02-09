package boassoft.common;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageUtil {

	/** log */
    protected static final Log LOG = LogFactory.getLog(ImageUtil.class);
	
	/**
	 * 이미지 회전 (정방향)
	 * @param source
	 * @throws Exception
	 */
	public static HashMap<String, Object> rotate(String source) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Image imgSource = new ImageIcon(source).getImage();
		int imgWidth = imgSource.getWidth(null);
		int imgHeight = imgSource.getHeight(null);
		
		File imgFile = new File(source);
		
		int orientation = getOrientation(imgFile);

        AffineTransform affineTransform = new AffineTransform();

        switch (orientation) {
        case 1: // [Top, left side (Horizontal / normal)]
            break;
        case 2: // Flip X [Top, right side (Mirror horizontal)]
            affineTransform.scale(-1.0, 1.0);
            affineTransform.translate(-imgWidth, 0);
            break;
        case 3: // PI rotation [Bottom, right side (Rotate 180)]
            affineTransform.translate(imgWidth, imgHeight);
            affineTransform.rotate(Math.PI);
            break;
        case 4: // Flip Y [Bottom, left side (Mirror vertical)]
            affineTransform.scale(1.0, -1.0);
            affineTransform.translate(0, -imgHeight);
            break;
        case 5: // - PI/2 and Flip X [Left side, top (Mirror horizontal and rotate 270 CW)]
            affineTransform.rotate(-Math.PI / 2);
            affineTransform.scale(-1.0, 1.0);
            break;
        case 6: // -PI/2 and -imgWidth [Right side, top (Rotate 90 CW)]
            affineTransform.translate(imgHeight, 0);
            affineTransform.rotate(Math.PI / 2);
            break;
        case 7: // PI/2 and Flip [Right side, bottom (Mirror horizontal and rotate 90 CW)]
            affineTransform.scale(-1.0, 1.0);
            affineTransform.translate(-imgHeight, 0);
            affineTransform.translate(0, imgWidth);
            affineTransform.rotate(3 * Math.PI / 2);
            break;
        case 8: // PI / 2 [Left side, bottom (Rotate 270 CW)]
            affineTransform.translate(0, imgWidth);
            affineTransform.rotate(3 * Math.PI / 2);
            break;
        default:
            break;
        }
        
        if (orientation > 1) {
	        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
	        BufferedImage originalImage = ImageIO.read(imgFile);
	        BufferedImage destinationImage;
	        
	        if (orientation > 4) {
	        	destinationImage = new BufferedImage(imgHeight, imgWidth, originalImage.getType());
	        	resultMap.put("imgWidth", imgHeight);
	        	resultMap.put("imgHeight", imgWidth);
	        } else {
	        	destinationImage = new BufferedImage(imgWidth, imgHeight, originalImage.getType());
	        	resultMap.put("imgWidth", imgWidth);
	        	resultMap.put("imgHeight", imgHeight);
	        }
	        
	        destinationImage = affineTransformOp.filter(originalImage, destinationImage);
	        ImageIO.write(destinationImage, "jpg", new File(source));
        }
        
        return resultMap;
	}
	
	/**
	 * 이미지 파일의 회전 방향을 구함.
	 * @param source
	 * @return
	 */
	public static int getOrientation(String source) throws Exception {
		File file = new File(source);
		
		return getOrientation(file);
	}
	public static int getOrientation(File file) throws Exception {
		int orientation = 1;
		
		try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            for (Directory directory : metadata.getDirectoriesOfType(ExifIFD0Directory.class)) {

                orientation = directory.getInteger(ExifIFD0Directory.TAG_ORIENTATION);
            }            
        } catch (Exception e) {
        	LOG.debug(e.toString());
        	orientation = 1;
        }
		
		return orientation;
	}
	
	/*public static void main(String[] args) throws Exception
    {
        ImageUtil.rotate("D:/bbb/20170228_093534.jpg");
        
        System.out.println("OK");
    }*/
}
