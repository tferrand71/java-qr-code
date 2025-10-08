package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class QRCodeView extends JFrame {

    private JTextField textField;
    private JTextField filePathField;
    private JTextField logoPathField;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JComboBox<String> fontComboBox;
    private JSpinner fontSizeSpinner;
    private JButton browseButton;
    private JButton logoBrowseButton;
    private JButton textColorButton;
    private JButton backgroundColorButton;
    private JButton generateQRButton;
    private JButton generatePDFButton;

    private JLabel qrPreviewLabel; // Aperçu du QR Code
    private JLabel statusLabel;    // Messages de statut
    private JProgressBar progressBar; // Barre de progression

    public QRCodeView() {
        setTitle("Générateur de QR Code");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Paramètres du QR Code",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                Color.DARK_GRAY
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        int row = 0;

        // Texte ou lien
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Texte ou lien :"), c);
        c.gridx = 1;
        textField = new JTextField(30);
        formPanel.add(textField, c);
        row++;

        // Chemin du fichier
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Chemin du fichier :"), c);
        c.gridx = 1;
        filePathField = new JTextField(30);
        formPanel.add(filePathField, c);
        c.gridx = 2;
        browseButton = new JButton("Parcourir...");
        formPanel.add(browseButton, c);
        row++;

        // Logo
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Logo (optionnel) :"), c);
        c.gridx = 1;
        logoPathField = new JTextField(30);
        formPanel.add(logoPathField, c);
        c.gridx = 2;
        logoBrowseButton = new JButton("Parcourir logo...");
        formPanel.add(logoBrowseButton, c);
        row++;

        // Largeur et hauteur
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Largeur :"), c);
        c.gridx = 1;
        widthSpinner = new JSpinner(new SpinnerNumberModel(200, 50, 1000, 10));
        formPanel.add(widthSpinner, c);
        row++;

        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Hauteur :"), c);
        c.gridx = 1;
        heightSpinner = new JSpinner(new SpinnerNumberModel(200, 50, 1000, 10));
        formPanel.add(heightSpinner, c);
        row++;

        // Police et taille
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Police :"), c);
        c.gridx = 1;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();
        Arrays.sort(fonts);
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setSelectedItem("SansSerif");
        formPanel.add(fontComboBox, c);
        row++;

        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Taille du texte :"), c);
        c.gridx = 1;
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(14, 8, 72, 1));
        formPanel.add(fontSizeSpinner, c);
        row++;

        // Couleurs
        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Couleur du texte :"), c);
        c.gridx = 1;
        textColorButton = new JButton("Choisir couleur texte");
        textColorButton.setBackground(Color.BLACK);
        textColorButton.setForeground(Color.WHITE);
        formPanel.add(textColorButton, c);
        row++;

        c.gridx = 0; c.gridy = row; formPanel.add(new JLabel("Couleur du fond :"), c);
        c.gridx = 1;
        backgroundColorButton = new JButton("Choisir couleur fond");
        backgroundColorButton.setBackground(Color.WHITE);
        backgroundColorButton.setForeground(Color.BLACK);
        formPanel.add(backgroundColorButton, c);
        row++;

        // Boutons
        c.gridx = 0; c.gridy = row; c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        generateQRButton = new JButton("Générer le QR Code");
        generateQRButton.setBackground(new Color(0, 120, 255));
        generateQRButton.setForeground(Color.WHITE);
        generateQRButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(generateQRButton, c);
        row++;

        c.gridx = 0; c.gridy = row; c.gridwidth = 3;
        generatePDFButton = new JButton("Générer le PDF");
        generatePDFButton.setBackground(new Color(60, 140, 60));
        generatePDFButton.setForeground(Color.WHITE);
        generatePDFButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(generatePDFButton, c);

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(formPanel, gbc);

        // Aperçu QR
        qrPreviewLabel = new JLabel();
        qrPreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(qrPreviewLabel, gbc);

        // Statut
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        mainPanel.add(statusLabel, gbc);

        // Barre de progression
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(progressBar, gbc);

        add(mainPanel);
        setVisible(true);
    }

    // --- Méthodes d'écoute ---
    public void addGenerateQRListener(ActionListener listener) { generateQRButton.addActionListener(listener); }
    public void addGeneratePDFListener(ActionListener listener) { generatePDFButton.addActionListener(listener); }
    public void addBrowseListener(ActionListener listener) { browseButton.addActionListener(listener); }
    public void addLogoBrowseListener(ActionListener listener) { logoBrowseButton.addActionListener(listener); }
    public void addTextColorListener(ActionListener listener) { textColorButton.addActionListener(listener); }
    public void addBackgroundColorListener(ActionListener listener) { backgroundColorButton.addActionListener(listener); }

    // --- Getters corrigés pour controller ---
    public String getText() { return textField.getText(); }
    public String getFilePath() { return filePathField.getText(); }
    public String getLogoPath() { return logoPathField.getText(); }
    public int getWidthValue() { return (Integer) widthSpinner.getValue(); }
    public int getHeightValue() { return (Integer) heightSpinner.getValue(); }
    public String getSelectedFont() { return (String) fontComboBox.getSelectedItem(); }
    public int getFontSize() { return (Integer) fontSizeSpinner.getValue(); }
    public Color getTextColor() { return textColorButton.getBackground(); }
    public Color getBackgroundColor() { return backgroundColorButton.getBackground(); }

    // --- Setters ---
    public void setSelectedFont(String fontName) { fontComboBox.setSelectedItem(fontName); }
    public void setFontSize(int size) { fontSizeSpinner.setValue(size); }
    public void setTextColor(Color color) { textColorButton.setBackground(color); }
    public void setBackgroundColor(Color color) { backgroundColorButton.setBackground(color); }
    public void setLogoPath(String path) { logoPathField.setText(path); }
    public void setFilePath(String path) { filePathField.setText(path); }

    // --- Méthodes utiles pour controller ---
    public void showError(String message) { JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE); }
    public void showSuccess(String message) { JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE); }
    public void setStatus(String message) { statusLabel.setText(message); }
    public void updatePreview(ImageIcon qrImage) { qrPreviewLabel.setIcon(qrImage); }
    public void showProgress(boolean visible) { progressBar.setVisible(visible); }

    public void openFileChooser(JTextField targetField, String title, String... extensions) {
        JFileChooser fileChooser = new JFileChooser();
        if (extensions.length > 0)
            fileChooser.setFileFilter(new FileNameExtensionFilter(String.join(", ", extensions), extensions));
        fileChooser.setDialogTitle(title);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            targetField.setText(file.getAbsolutePath());
        }
    }

    public Color chooseColor(String title) {
        return JColorChooser.showDialog(this, title, Color.BLACK);
    }
}
