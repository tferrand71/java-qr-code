package utils;

import model.UserProfile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class PDFGeneratorUtil {
    
    /**
     * Génère un document PDF simple (en réalité un fichier texte formaté)
     * Cette version simplifiée crée un document texte formaté au lieu d'un vrai PDF
     */
    public static String generatePDFWithQRCode(String text, String filePath, 
                                             String qrCodePath, UserProfile profile,
                                             String additionalImagePath) 
            throws IOException {
        
        // S'assurer que le chemin a l'extension .txt (au lieu de .pdf pour simplifier)
        String outputPath = filePath;
        if (outputPath.toLowerCase().endsWith(".pdf")) {
            outputPath = outputPath.substring(0, outputPath.length() - 4) + ".txt";
        }
        if (!outputPath.toLowerCase().endsWith(".txt")) {
            outputPath += ".txt";
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(outputPath))) {
            // Écrire l'en-tête du document
            writer.println("===============================================");
            writer.println("           QR CODE GÉNÉRÉ");
            writer.println("===============================================");
            writer.println();
            
            // Ajouter des informations sur le profil
            writer.println("Profil utilisé: " + profile.getProfileName());
            writer.println("Police: " + profile.getFontName() + " (taille: " + profile.getFontSize() + ")");
            writer.println("Couleur du texte: " + colorToString(profile.getTextColor()));
            writer.println("Couleur de fond: " + colorToString(profile.getBackgroundColor()));
            writer.println();
            
            // Ajouter l'image supplémentaire si fournie
            if (additionalImagePath != null && !additionalImagePath.trim().isEmpty()) {
                writer.println("Logo: " + new File(additionalImagePath).getName());
                writer.println();
            }
            
            // Ajouter le contenu encodé
            writer.println("Contenu encodé dans le QR Code:");
            writer.println("--------------------------------");
            writer.println(text);
            writer.println();
            
            // Ajouter des informations sur l'image QR Code
            writer.println("QR Code généré:");
            writer.println("---------------");
            writer.println("Fichier image: " + new File(qrCodePath).getName());
            writer.println("Dimensions: " + getImageDimensions(qrCodePath));
            writer.println();
            
            // Ajouter des instructions
            writer.println("Instructions:");
            writer.println("------------");
            writer.println("1. Scannez le QR Code avec votre smartphone");
            writer.println("2. Le contenu encodé sera affiché");
            writer.println("3. Pour les URLs, vous serez redirigé automatiquement");
            writer.println();
            
            // Pied de page
            writer.println("===============================================");
            writer.println("Généré avec QR Code Generator");
            writer.println("Date: " + java.time.LocalDateTime.now());
            writer.println("===============================================");
        }
        
        return outputPath;
    }
    
    /**
     * Convertit une couleur en chaîne de caractères
     */
    private static String colorToString(Color color) {
        if (color == null) return "Non défini";
        return String.format("RGB(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
    }
    
    /**
     * Récupère les dimensions d'une image
     */
    private static String getImageDimensions(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (image != null) {
                return image.getWidth() + "x" + image.getHeight() + " pixels";
            }
        } catch (IOException e) {
            return "Dimensions inconnues";
        }
        return "Dimensions inconnues";
    }
    
    /**
     * Alternative: Crée une image composite avec le texte et le QR Code
     */
    public static String generateImageWithQRCode(String text, String filePath, 
                                               String qrCodePath, UserProfile profile,
                                               String additionalImagePath) 
            throws IOException {
        
        // S'assurer que le chemin a l'extension .png
        String outputPath = filePath;
        if (!outputPath.toLowerCase().endsWith(".png")) {
            outputPath += ".png";
        }

        // Créer une image composite
        BufferedImage compositeImage = createCompositeImage(text, qrCodePath, profile, additionalImagePath);
        
        // Sauvegarder l'image
        File outputFile = new File(outputPath);
        ImageIO.write(compositeImage, "PNG", outputFile);
        
        return outputPath;
    }
    
    /**
     * Crée une image composite avec texte et QR Code
     */
    private static BufferedImage createCompositeImage(String text, String qrCodePath, 
                                                    UserProfile profile, String additionalImagePath) 
            throws IOException {
        
        // Dimensions de l'image finale
        int width = 800;
        int height = 600;
        
        BufferedImage composite = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = composite.createGraphics();
        
        // Rendre l'antialiasing plus lisse
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Fond
        g2d.setColor(profile.getBackgroundColor());
        g2d.fillRect(0, 0, width, height);
        
        // Couleur du texte
        g2d.setColor(profile.getTextColor());
        
        // Charger et dessiner le QR Code
        try {
            BufferedImage qrImage = ImageIO.read(new File(qrCodePath));
            if (qrImage != null) {
                int qrSize = 200;
                int qrX = (width - qrSize) / 2;
                int qrY = height - qrSize - 50;
                g2d.drawImage(qrImage, qrX, qrY, qrSize, qrSize, null);
            }
        } catch (IOException e) {
            // Dessiner un rectangle de remplacement si l'image ne peut pas être chargée
            g2d.setColor(Color.RED);
            g2d.fillRect(width/2 - 100, height - 250, 200, 200);
            g2d.setColor(Color.WHITE);
            g2d.drawString("QR Code non disponible", width/2 - 80, height - 100);
        }
        
        // Police et texte
        Font font = new Font(profile.getFontName(), Font.BOLD, profile.getFontSize() + 8);
        g2d.setFont(font);
        
        // Titre
        String title = "QR Code Généré";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);
        
        // Contenu encodé
        font = new Font(profile.getFontName(), Font.PLAIN, profile.getFontSize());
        g2d.setFont(font);
        
        String contentLabel = "Contenu encodé:";
        fm = g2d.getFontMetrics();
        int labelX = (width - fm.stringWidth(contentLabel)) / 2;
        g2d.drawString(contentLabel, labelX, 100);
        
        // Texte encodé (tronqué si trop long)
        String displayText = text.length() > 50 ? text.substring(0, 47) + "..." : text;
        fm = g2d.getFontMetrics();
        int textX = (width - fm.stringWidth(displayText)) / 2;
        g2d.drawString(displayText, textX, 130);
        
        // Instructions
        font = new Font(profile.getFontName(), Font.ITALIC, profile.getFontSize() - 2);
        g2d.setFont(font);
        String instruction = "Scannez le QR Code avec votre smartphone";
        fm = g2d.getFontMetrics();
        int instX = (width - fm.stringWidth(instruction)) / 2;
        g2d.drawString(instruction, instX, height - 20);
        
        g2d.dispose();
        return composite;
    }
}