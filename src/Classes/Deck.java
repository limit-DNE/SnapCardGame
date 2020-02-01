package Classes;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    private Card[] cards;
    private ArrayList<Image> cardImages;
    private Map<Card, Image> imageCardMap;
    private Map<Image, Card> cardImageMap;

    protected Deck(){
        this.cards = new Card[52];
        setCards();
        this.cardImages = new ArrayList<>();
        loadImages();
        this.imageCardMap = new LinkedHashMap<>();
        this.cardImageMap = new LinkedHashMap<>();
        setImageCardMap();
    }

    protected void shuffleDeck() {
        for (int i = 0; i < this.cards.length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, this.cards.length);
            Card swap = this.cards[randomIndex];
            this.cards[randomIndex] = this.cards[i];
            this.cards[i] = swap;
        }
    }

    protected ArrayList<Card> cardsToArrayList() {
        return new ArrayList<>(Arrays.asList(this.cards));
    }

    private void addImagesToList() throws FileNotFoundException {
        for (int i = 0; i < 52; i++) {
            this.cardImages.add(new Image(new FileInputStream("src/Images/" + (i + 1) + ".png")));
        }
    }

    protected Image getImageForCard(Card card) {
        return this.imageCardMap.get(card);
    }

    private void loadImages() {
        try {
            addImagesToList();
        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
    }

    private void setCards(){
        int count = 0;
        for (char suit : new char[]{'♥', '♠', '♦', '♣'}) {
            for (String value : new String[]{"ACE", "2", "3", "4", "5", "6", "7", "8", "9", "10", "JACK", "QUEEN", "KING"}) {
                this.cards[count] = new Card(suit, value);
                count++;
            }
        }
    }

    private void setImageCardMap(){
        int count = 0;
        for (Card card : this.cards) {
            this.imageCardMap.put(card, this.cardImages.get(count));
            this.cardImageMap.put(this.cardImages.get(count), card);
            count++;
        }
    }

    public Card getValue(Object object){
       return cardImageMap.get(object);
    }
}
