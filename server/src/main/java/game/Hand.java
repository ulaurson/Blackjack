package game;

import java.util.ArrayList;


public class Hand {

    private ArrayList<Card> cards = new ArrayList<>();
    private boolean handContainsAce = false;
    private ArrayList<String> cardSymbolicValues = new ArrayList<>();
    private int score = 0;
    private boolean busted = false;
    private boolean blackjack = false;


    public Hand() {}

    protected void addCard(Card card) {
        cards.add(card);
        if(card.getNumericValue() == 1) {
            handContainsAce = true;
        }
        cardSymbolicValues.add(card.getSymbolicValue());
        score += card.getNumericValue();
        if(score > 21) {
            busted = true;
        } else if(score == 21){
            blackjack = true;
        }
    }

    protected int getScore() {
        if(score < 12 & handContainsAce){
            return score + 10;
        } else {
            return score;
        }
    }

    protected boolean isBusted() {
        return busted;
    }

    protected ArrayList<Card> getCards() {
        return cards;
    }

    protected String getHandStateString() {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(score);
        if(handContainsAce) {
            scores.add(score + 10);
        }
        return String.format("   Cards: %1s \n   Score: %2s \n",
                cardSymbolicValues.toString(), scores.toString());

    }

    public boolean hasBlackjack() {
        return blackjack;
    }

}
