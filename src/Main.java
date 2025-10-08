import model.QRCodeModel;
import view.QRCodeView;
import controller.QRCodeController;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            QRCodeModel model = new QRCodeModel();
            QRCodeView view = new QRCodeView();
            new QRCodeController(model, view);
            view.setVisible(true);
        });
    }
}