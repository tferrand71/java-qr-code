package utils;

import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeGeneratorUtilTest {

    private final String outputDir = System.getProperty("java.io.tmpdir");

    @Test
    void testGenerateQRCodeWithoutLogo() throws IOException, WriterException {
        String filePath = outputDir + "/test_qr.png";
        String text = "https://www.example.com";

        String generatedFile = QRCodeGeneratorUtil.generateQRCode(text, filePath, 300, 300);
        File f = new File(generatedFile);

        assertTrue(f.exists(), "Le fichier QR Code doit exister");
        assertTrue(f.length() > 0, "Le fichier QR Code ne doit pas être vide");

        f.delete();
    }

    @Test
    void testGenerateQRCodeWithLogo() throws IOException, WriterException {
        String filePath = outputDir + "/test_qr_logo.png";
        String logoPath = "src/test/resources/logo.png"; // mettre un petit logo test ici
        String text = "Hello World";

        // Créer un fichier logo temporaire si nécessaire
        File logoFile = new File(logoPath);
        if (!logoFile.exists()) {
            logoFile.getParentFile().mkdirs();
            logoFile.createNewFile();
        }

        String generatedFile = QRCodeGeneratorUtil.generateQRCode(text, filePath, 300, 300, logoPath);
        File f = new File(generatedFile);

        assertTrue(f.exists(), "Le fichier QR Code avec logo doit exister");
        assertTrue(f.length() > 0, "Le fichier QR Code avec logo ne doit pas être vide");

        f.delete();
    }

    @Test
    void testIsValidText() {
        assertTrue(QRCodeGeneratorUtil.isValidText("Test valide"));
        assertFalse(QRCodeGeneratorUtil.isValidText(""));
        assertFalse(QRCodeGeneratorUtil.isValidText(null));

        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 5000; i++) longText.append("a");
        assertFalse(QRCodeGeneratorUtil.isValidText(longText.toString()));
    }
}
