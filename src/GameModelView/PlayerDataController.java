package GameModelView;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;;
import java.io.IOException;

public class PlayerDataController {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Label labelPlayer;
    @FXML
    private TextField textFieldPlayer;

    private static String player1Name;
    private static String player2Name;

    public void initialize(){
        startDataReading();
    }

    private boolean checkName(String name){
        if (!name.isEmpty() && name.matches("[a-zA-Z,ç,Ç,ğ,Ğ,ı,İ,ö,Ö,ş,Ş,ü,Ü,\\s]+$") && name != null){
            return true;
        } else {
            return false;
        }
    }

    private void displayDialog(String labelTop, String labelBottom){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderpane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("DialogPane.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
            DialogPaneController dialogPaneController = fxmlLoader.getController();
            dialogPaneController.setLabels(labelTop, labelBottom);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again",  ButtonBar.ButtonData.LEFT));
        dialog.setTitle("System Feedback");
        dialog.showAndWait();
    }

    private void setPlayer1Name(String player1Name){
        PlayerDataController.player1Name = player1Name;
    }

    protected static String getPlayer1Name(){
        return player1Name;
    }

    private void setPlayer2Name(String player2Name){
        PlayerDataController.player2Name = player2Name;
    }

    protected static String getPlayer2Name(){
        return player2Name;
    }

    private void askPlayerName1(){
        labelPlayer.setText("Player 1");
        textFieldPlayer.clear();
        textFieldPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setPlayer1Name(textFieldPlayer.getText());
                if (checkName(getPlayer1Name())){
                    askPlayerName2();
                } else {
                    displayDialog("Invalid Name", "Use letters only");
                    askPlayerName1();
                }
            }
        });
    }

    private void askPlayerName2(){
        labelPlayer.setText("Player 2");
        textFieldPlayer.clear();
        textFieldPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setPlayer2Name(textFieldPlayer.getText());
                if (checkName(getPlayer2Name())){
                    loadGame();
                } else {
                    displayDialog("Invalid Name", "Use letters only");
                    askPlayerName2();
                }
            }
        });
    }

    private void loadGame(){
        Stage gameStage = new Stage();
        Stage currentStage = (Stage) borderpane.getScene().getWindow();
        try{
            Parent gameRoot = FXMLLoader.load(getClass().getResource("Game.fxml"));
            gameStage.setTitle("Snap");
            gameStage.setScene( new Scene(gameRoot, 900, 600));
            gameStage.show();
            transition(gameStage);
            currentStage.close();
        } catch (IOException exception){
            displayDialog("Cannot Start Game", "Check player information");
            exception.printStackTrace();
        }
    }

    private void startDataReading(){
        askPlayerName1();
    }

    private void transition(Stage stage){
        stage.getScene().setFill(Color.DARKGREEN);
        final ScaleTransition st = new ScaleTransition();
        st.setFromX(0.0);
        st.setToX(1.0);
        st.setDuration(Duration.millis(600));
        st.setNode(stage.getScene().getRoot());
        st.setOnFinished(actionEvent -> stage.show());
        st.play();
    }
}
