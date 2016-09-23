package game;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {

    private final String[] frenchSuits = new String[]{"C", "D", "H", "S"};
    private final String[] symbolSuits = new String[]{"J", "Q", "K"};
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        this.deck = makeDeck();
    }

    private ArrayList<Card> makeDeck() {
        Card card;
        for(int i = 0; i < 4;i++){
            String frenchSuit = frenchSuits[i];
            String cardString;
            for(int number = 2; number <= 10;number++){
                cardString = frenchSuit + "-" + number;
                card = new Card(cardString, number);
                deck.add(card);
            }
            for(int symbolIndex = 0; symbolIndex < 3;symbolIndex++){
                cardString = frenchSuit + "-" + symbolSuits[symbolIndex];
                card = new Card(cardString, 10);
                deck.add(card);
            }
            cardString = frenchSuit + "-" + "A";
            card = new Card(cardString, 1);
            deck.add(card);
        }
        shuffleDeck();
        return deck;
    }

    protected void shuffleDeck(){
        Collections.shuffle(deck);
    }

    protected ArrayList<Card> getDeck() {
        return deck;
    }
}
