package Classes;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameTable {

    private ArrayList<Card> gameStack;
    private ArrayList<Card> gameDeck;
    private Deck deck;

    public GameTable(){
        deck = new Deck();
        deck.shuffleDeck();
        gameDeck = new ArrayList<>(deck.cardsToArrayList());
        gameStack = new ArrayList<>();
        startTable();
    }

    private void startTable(){
        gameStack.addAll(gameDeck.subList(0, 4));
        gameDeck.subList(0, 4).clear();
    }

    public void dealNewHand(Player player){
        if (cardsLeftInGame() > 0){
            for (Card card : gameDeck.subList(0, 4)) {
                player.addCardToHand(card);
            }
            gameDeck.subList(0, 4).clear();
        }
    }

    public void addCardToGameStack(Card card){
        gameStack.add(card);
    }

    public void giveCardsToPlayer(Player player){
        for (Card cards : gameStack) {
            player.addToCardsEarned(cards);
        }
        clearStack();
    }

    public void clearStack(){
        gameStack.clear();
    }

    public int cardsLeftInGame(){
        return gameDeck.size();
    }

    public ArrayList<Card> getGameStack(){
        return gameStack;
    }

    public Image getCardAsImage(Card card){
        return deck.getImageForCard(card);
    }

    public Card selectedCard(Object object) {
        return deck.getValue(object);
    }
}
