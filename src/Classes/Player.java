package Classes;

import java.util.ArrayList;

public class Player {

    private String playerName;
    private int playerScore;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> cardsEarned;

    public Player(String playerName){
        setPlayerName(playerName);
        setPlayerScore(0);
        playerHand = new ArrayList<>();
        cardsEarned = new ArrayList<>();
    }

    public String getPlayerName(){
        return playerName;
    }

    private void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public int getPlayerScore(){
        return playerScore;
    }

    protected void setPlayerScore(int playerScore){
        this.playerScore = playerScore;
    }

    protected void addCardToHand(Card card){
        playerHand.add(card);
    }

    public ArrayList<Card> getPlayerHand(){
        return playerHand;
    }

    public void removeCard(Card card){
        playerHand.remove(card);
    }


    protected void addToCardsEarned(Card cards){
        cardsEarned.add(cards);
    }

    public void updateScore(int addScore){
        setPlayerScore( getPlayerScore() + addScore);
    }

    public ArrayList<Card> getCardsEarned(){
        return cardsEarned;
    }

    public void calculateEarnedPoints(){
        for (Card card : cardsEarned) {
            if (card.getValue().equals("ACE")){
                updateScore(1);
            } else if (card.getValue().equals("2") && card.getSuit() == '♣'){
                updateScore(2);
            } else if (card.getValue().equals("10") && card.getSuit() == '♦'){
                updateScore(3);
            } else if (card.getValue().equals("JACK")){
                updateScore(1);
            }
        }
    }
}
