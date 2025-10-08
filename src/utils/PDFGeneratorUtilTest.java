package utils;

import model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFGeneratorUtilTest {

    private final String outputDir = System.getProperty("java.io.tmpdir");

    @Test
    void testGeneratePDFWithQRCode() throws Exception {
        String qrPath = outputDir + "/temp_qr.png";
        String pdfPath = outputDir + "/test_pdf.pdf";
        String text = "Hello PDF";

        // Générer un QR Code simple pour le test
        QRCodeGeneratorUtil.generateQRCode(text, qrPath, 200, 200);

        UserProfile profile = new UserProfile();
        profile.setFontName("SansSerif");
        profile.setFontSize(12);

        String resultPdf = PDFGeneratorUtil.generateImageWithQRCode(text, pdfPath, qrPath, profile, null);

        File pdfFile = new File(resultPdf);
        assertTrue(pdfFile.exists(), "Le PDF généré doit exister");
        assertTrue(pdfFile.length() > 0, "Le PDF généré ne doit pas être vide");

        // Cleanup
        new File(qrPath).delete();
        pdfFile.delete();
    }
}
