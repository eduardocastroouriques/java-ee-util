package br.com.auttar.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.EnumMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.jersey.core.util.Base64;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.errorhandling.AppException;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class QRCodeUtils {

	private static Logger logger = LogManager.getLogger(LogConstants.UTIL_LOGGER_NAME);

	public static String generateQRCode(String myCodeText) throws AppException {
		int size = 70;
		String encodedFile = "";
		
		if (myCodeText == null || myCodeText.isEmpty()) return "";
		
		try {
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// Now with zxing version 3.2.1 you could change border size (white
			// border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1);
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", stream);
			stream.flush();
			
			byte data[] = stream.toByteArray();
			encodedFile = new String(Base64.encode(data), "UTF-8");

		} catch (Exception e) {
	    	logger.error(e.getMessage(), e);
			throw new AppException(Status.INTERNAL_SERVER_ERROR,
					AppConstants.RESPONSE_CODE_ERROR_QRCODE, AppConstants.RESPONSE_MESSAGE_ERROR_QRCODE);
		}

		return encodedFile;
	}
}
