package GameModelView;

import Classes.Card;
import Classes.GameTable;
import Classes.Player;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ScoreboardController {

    @FXML
    private Label labelWinner;
    @FXML
    private Label labelScore;
    @FXML
    private Label labelName;
    @FXML
    private Button restartButton;
    @FXML
    private BorderPane borderpane;

    public void initialize(){

    }

    public void displayWinner(Player player){
        labelWinner.setText("WINNER");
        labelName.setText(player.getPlayerName());
        labelScore.setText("Score: " + player.getPlayerScore());
        buttonTransition();
        getRestart();
    }

    public void gameTie(){
        labelName.setText("Tie Game!");
        labelScore.setText("No points granted for this round");
        buttonTransition();
        getRestart();
    }

    public void buttonTransition(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600), restartButton);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    public void restartGame(){
        Stage playerDataStage = new Stage();
        Stage currentStage = (Stage) borderpane.getScene().getWindow();
        try{
            Parent playerDataRoot = FXMLLoader.load(getClass().getResource("PlayerData.fxml"));
            playerDataStage.setTitle("Player Information");
            playerDataStage.setScene(new Scene(playerDataRoot, 800, 600));
            playerDataStage.show();
            transition(playerDataStage);
            currentStage.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private void transition(Stage stage){
        stage.getScene().setFill(Color.DARKSEAGREEN);
        final ScaleTransition st = new ScaleTransition();
        st.setFromX(0.0);
        st.setToX(1.0);
        st.setDuration(Duration.millis(600));
        st.setNode(stage.getScene().getRoot());
        st.setOnFinished(actionEvent -> stage.show());
        st.play();
    }

    public void getRestart(){
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restartGame();
            }
        });
    }
}
