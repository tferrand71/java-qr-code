package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeGeneratorUtil {

    /**
     * Génère un QR Code lisible avec ZXing
     * @param text le texte ou le lien à encoder
     * @param filePath chemin complet du fichier de sortie (PNG)
     * @param width largeur de l'image
     * @param height hauteur de l'image
     * @param logoPath chemin d'un logo optionnel à insérer au centre (null si aucun)
     */
    public static String generateQRCode(String text, String filePath, int width, int height, String logoPath)
            throws WriterException, IOException {

        if (!filePath.toLowerCase().endsWith(".png")) {
            filePath += ".png";
        }

        // Préparer ZXing QRCodeWriter
        QRCodeWriter qrWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // Convertir BitMatrix en BufferedImage
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();
        Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        // Ajouter le logo si fourni
        if (logoPath != null && !logoPath.isEmpty()) {
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                BufferedImage logo = ImageIO.read(logoFile);
                int logoWidth = qrImage.getWidth() / 5;
                int logoHeight = qrImage.getHeight() / 5;
                int x = (qrImage.getWidth() - logoWidth) / 2;
                int y = (qrImage.getHeight() - logoHeight) / 2;
                graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
            }
        }

        graphics.dispose();

        // Sauvegarder l'image
        File outputFile = new File(filePath);
        ImageIO.write(qrImage, "PNG", outputFile);

        return filePath;
    }

    /**
     * Surcharge simple sans logo
     */
    public static String generateQRCode(String text, String filePath, int width, int height)
            throws WriterException, IOException {
        return generateQRCode(text, filePath, width, height, null);
    }

    /**
     * Vérifie si le texte est valide pour la génération de QR Code
     */
    public static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty() && text.length() <= 4000;
    }
}
