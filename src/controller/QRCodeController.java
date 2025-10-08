package controller;

import model.QRCodeModel;
import model.UserProfile;
import view.QRCodeView;
import utils.QRCodeGeneratorUtil;
import utils.PDFGeneratorUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.io.File;

public class QRCodeController {
    private QRCodeModel model;
    private QRCodeView view;

    public QRCodeController(QRCodeModel model, QRCodeView view) {
        this.model = model;
        this.view = view;

        setupListeners();
        initializeFromModel();
    }

    private void setupListeners() {
        view.addGenerateQRListener(e -> generateQRCodePreview());
        view.addGeneratePDFListener(e -> generatePDF());
        view.addBrowseListener(e -> browseFile());
        view.addTextColorListener(e -> chooseTextColor());
        view.addBackgroundColorListener(e -> chooseBackgroundColor());
        view.addLogoBrowseListener(e -> browseLogo());
    }

    private void initializeFromModel() {
        UserProfile profile = model.getCurrentProfile();
        if (profile != null) {
            view.setSelectedFont(profile.getFontName());
            view.setFontSize(profile.getFontSize());
            view.setTextColor(profile.getTextColor());
            view.setBackgroundColor(profile.getBackgroundColor());
        }
    }

    private void generateQRCodePreview() {
        try {
            if (view.getText().trim().isEmpty()) {
                view.showError("Veuillez saisir un texte ou un lien");
                return;
            }

            // Chemin temporaire valide
            String tempPath = System.getProperty("java.io.tmpdir") + File.separator + "preview_qr.png";

            // Génération du QR Code avec ZXing
            QRCodeGeneratorUtil.generateQRCode(
                view.getText(),
                tempPath,
                view.getWidthValue(),
                view.getHeightValue()
            );

            // Affichage dans l'aperçu
            ImageIcon qrImage = new ImageIcon(tempPath);
            view.updatePreview(qrImage);
            view.setStatus("Aperçu du QR Code généré");
        } catch (Exception ex) {
            view.setStatus("Erreur lors de la génération de l'aperçu");
            view.showError("Erreur: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void generatePDF() {
        try {
            if (view.getText().trim().isEmpty()) {
                view.showError("Veuillez saisir un texte ou un lien");
                return;
            }

            if (view.getFilePath().trim().isEmpty()) {
                view.showError("Veuillez spécifier un chemin pour le document");
                return;
            }

            view.setStatus("Génération du document en cours...");
            view.showProgress(true);

            updateModelFromView();

            String tempDir = System.getProperty("java.io.tmpdir");
            String qrCodePath = tempDir + File.separator + "pdf_qr_" + System.currentTimeMillis() + ".png";

            // Génération QR Code pour le PDF
            QRCodeGeneratorUtil.generateQRCode(
                model.getText(),
                qrCodePath,
                200,
                200
            );

            // Génération du PDF avec QR Code
            String outputPath = PDFGeneratorUtil.generateImageWithQRCode(
                model.getText(),
                model.getFilePath(),
                qrCodePath,
                model.getCurrentProfile(),
                view.getLogoPath()
            );

            // Suppression du QR temporaire
            new File(qrCodePath).delete();

            view.setStatus("Document généré avec succès: " + outputPath);
            view.showSuccess("Document généré avec succès!\nFichier: " + outputPath);
        } catch (Exception ex) {
            view.setStatus("Erreur lors de la génération du document");
            view.showError("Erreur: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            view.showProgress(false);
        }
    }

    private void updateModelFromView() {
        model.setText(view.getText());
        model.setFilePath(view.getFilePath());
        model.setWidth(view.getWidthValue());
        model.setHeight(view.getHeightValue());

        UserProfile profile = model.getCurrentProfile();
        profile.setFontName(view.getSelectedFont());
        profile.setFontSize(view.getFontSize());
        profile.setTextColor(view.getTextColor());
        profile.setBackgroundColor(view.getBackgroundColor());
        profile.setLogoPath(view.getLogoPath());
    }

    private void browseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder le document");
        fileChooser.setSelectedFile(new File("qrcode.pdf")); // PDF par défaut

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier PDF (*.pdf)", "pdf");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!selectedPath.toLowerCase().endsWith(".pdf")) {
                selectedPath += ".pdf";
            }
            view.setFilePath(selectedPath);
        }
    }

    private void chooseTextColor() {
        Color chosenColor = JColorChooser.showDialog(view, "Choisir la couleur du texte", view.getTextColor());
        if (chosenColor != null) {
            view.setTextColor(chosenColor);
        }
    }

    private void chooseBackgroundColor() {
        Color chosenColor = JColorChooser.showDialog(view, "Choisir la couleur de fond", view.getBackgroundColor());
        if (chosenColor != null) {
            view.setBackgroundColor(chosenColor);
        }
    }

    private void browseLogo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une image de logo");

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"
        );
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            view.setLogoPath(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}
