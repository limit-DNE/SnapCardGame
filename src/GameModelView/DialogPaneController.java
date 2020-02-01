package GameModelView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DialogPaneController {

    @FXML
    private Label labelDialog;
    @FXML
    private Label labelType;

    protected void setLabels(String label1, String label2){
        labelType.setText(label1);
        labelDialog.setText(label2);
    }
}
