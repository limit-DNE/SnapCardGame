package GameModelView;

import Classes.GameTable;
import Classes.Player;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameController {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox vBoxTop;
    @FXML
    private VBox vBoxBottom;
    @FXML
    private VBox vBoxMiddle;

    private GameTable gameTable;
    private Label labelBottom;
    private Player player1;
    private Player player2;
    private int playerTurn;

    public void initialize() throws FileNotFoundException {
        gameTable = new GameTable();
        player1 = new Player(PlayerDataController.getPlayer1Name());
        player2 = new Player(PlayerDataController.getPlayer2Name());
        labelBottom = new Label();
        labelBottom.setStyle("-fx-padding: 20px, 0, 0, 0; -fx-font-size: 28px; -fx-text-fill: #FFFFFF;");
        setPlayerTurn(1);
        start();
    }

    private void setPlayerTurn(int playerTurn){
        this.playerTurn = playerTurn;
    }

    private int getPlayerTurn(){
        return playerTurn;
    }

    public void displayTable() throws FileNotFoundException {
        if (gameTable.getGameStack().size() > 1){
            TilePane tilePane = new TilePane();
            Image image = gameTable.getCardAsImage(gameTable.getGameStack().get(gameTable.getGameStack().size() - 1));
            ImageView imageView = new ImageView(image);
            imageView.fitHeightProperty().setValue(120);
            imageView.fitWidthProperty().setValue(120);
            tilePane.setAlignment(Pos.TOP_CENTER);
            ImageView backView = getBackView();
            backView.fitWidthProperty().setValue(120);
            backView.fitHeightProperty().setValue(120);
            tilePane.getChildren().addAll(backView,imageView);
            vBoxMiddle.getChildren().add(tilePane);
        } else if (gameTable.getGameStack().size() == 1){
            TilePane tilePane = new TilePane();
            Image image = gameTable.getCardAsImage(gameTable.getGameStack().get(gameTable.getGameStack().size() - 1));
            ImageView imageView = new ImageView(image);
            imageView.fitHeightProperty().setValue(120);
            imageView.fitWidthProperty().setValue(120);
            tilePane.setAlignment(Pos.TOP_CENTER);
            tilePane.getChildren().add(imageView);
            vBoxMiddle.getChildren().add(tilePane);
        }
    }

    public ImageView getBackView() throws FileNotFoundException {
        Image backView = new Image(new FileInputStream("src/Images/red_back.png"));
        ImageView imageView = new ImageView(backView);
        return imageView;
    }

    public void start() throws FileNotFoundException {
        if (gameTable.cardsLeftInGame() > 0){
            displayTable();
            if (getPlayerTurn() == 1){
                setPlayerTurn(2);
                displayPlayerHand(player1);
                displayOpponentHand(player2);
            } else if (getPlayerTurn() == 2){
                setPlayerTurn(1);
                displayPlayerHand(player2);
                displayOpponentHand(player1);
            }
        } else {
            loadScoreboard();
        }
    }

    public void displayPlayerHand(Player player){
        if (player.getPlayerHand().size() > 0){
            TilePane tilePane = new TilePane();
            for (int i = 0; i < player.getPlayerHand().size(); i++){
                Image image = gameTable.getCardAsImage(player.getPlayerHand().get(i));
                ImageView imageView = new ImageView(image);
                imageView.fitHeightProperty().setValue(100);
                imageView.fitWidthProperty().setValue(100);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        disPlaySelectedCard(imageView, player);
                    }
                });
                tilePane.getChildren().add(imageView);
                tilePane.setAlignment(Pos.CENTER);
                tilePane.setStyle("-fx-padding: 20px, 0, 0, 0;");
            }
            labelBottom.setText(player.getPlayerName());
            vBoxBottom.getChildren().addAll(labelBottom, tilePane);
        } else {
            gameTable.dealNewHand(player);
            displayPlayerHand(player);
        }
    }

    public void disPlaySelectedCard(ImageView imageView, Player player){
        vBoxBottom.getChildren().clear();
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setStyle("-fx-padding: 20px, 0, 0, 0;");
        tilePane.getChildren().add(imageView);
        labelBottom.setText(player.getPlayerName());
        vBoxBottom.getChildren().addAll(labelBottom, tilePane);
        compareCards(imageView, player);
    }

    public void compareCards(ImageView imageView, Player player){
        if (gameTable.getGameStack().size() > 1){
            if (gameTable.getGameStack().get(gameTable.getGameStack().size() - 1).getValue().equals(gameTable.selectedCard(imageView.getImage()).getValue())){
                gameTable.addCardToGameStack(gameTable.selectedCard(imageView.getImage()));
                gameTable.giveCardsToPlayer(player);
                player.removeCard(gameTable.selectedCard(imageView.getImage()));
            } else if (gameTable.selectedCard(imageView.getImage()).getValue().equals("JACK")){
                gameTable.addCardToGameStack(gameTable.selectedCard(imageView.getImage()));
                gameTable.giveCardsToPlayer(player);
                player.removeCard(gameTable.selectedCard(imageView.getImage()));
            } else {
                gameTable.addCardToGameStack(gameTable.selectedCard(imageView.getImage()));
                player.removeCard(gameTable.selectedCard(imageView.getImage()));
            }
        } else if (gameTable.getGameStack().size() == 1 && gameTable.getGameStack().get(gameTable.getGameStack().size() - 1).getValue().equals(gameTable.selectedCard(imageView.getImage()).getValue())){
            gameTable.clearStack();
            player.removeCard(gameTable.selectedCard(imageView.getImage()));
            player.updateScore(10);
        } else {
            gameTable.addCardToGameStack(gameTable.selectedCard(imageView.getImage()));
            player.removeCard(gameTable.selectedCard(imageView.getImage()));
        }
        updateTable();
    }

    public void updateTable(){
        vBoxMiddle.getChildren().clear();
        vBoxBottom.getChildren().clear();
        vBoxTop.getChildren().clear();
        try {
            start();
        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public void loadScoreboard(){
        Stage scoreStage = new Stage();
        Stage currentStage = (Stage) borderpane.getScene().getWindow();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Scoreboard.fxml"));
            Parent scoreRoot = fxmlLoader.load();
            ScoreboardController scoreboardController = fxmlLoader.getController();
            if (noGameTie()){
                scoreboardController.displayWinner(getWinner());
            } else {
                scoreboardController.gameTie();
            }
            scoreStage.setTitle("Scoreboard");
            scoreStage.setScene(new Scene(scoreRoot, 900, 600));
            scoreStage.show();
            transition(scoreStage);
            currentStage.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private boolean noGameTie(){
        player1.calculateEarnedPoints();
        player2.calculateEarnedPoints();
        if (player1.getPlayerScore() == player2.getPlayerScore()){
            return false;
        } else {
            return true;
        }
    }

    private Player getWinner(){
        if (player1.getPlayerScore() > player2.getPlayerScore()){
            return player1;
        } else {
            return player2;
        }
    }

    private void transition(Stage stage){
        stage.getScene().setFill(Color.BLACK);
        final ScaleTransition st = new ScaleTransition();
        st.setFromX(0.0);
        st.setToX(1.0);
        st.setDuration(Duration.millis(600));
        st.setNode(stage.getScene().getRoot());
        st.setOnFinished(actionEvent -> stage.show());
        st.play();
    }

    private void displayOpponentHand(Player player) throws FileNotFoundException {
        if (player.getPlayerHand().size() > 0){
            TilePane tilePane = new TilePane();
            for (int i = 0; i < player.getPlayerHand().size(); i++){
                ImageView backView = getBackView();
                backView.fitWidthProperty().setValue(90);
                backView.fitHeightProperty().setValue(90);
                tilePane.getChildren().add(backView);
            }
            tilePane.setAlignment(Pos.CENTER);
            vBoxTop.getChildren().add(tilePane);
        }
    }
}
