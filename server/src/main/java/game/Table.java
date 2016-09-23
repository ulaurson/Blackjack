package game;

import java.util.ArrayList;


public class Table {

    private Hand dealerHand = new Hand();
    private Hand clientHand = new Hand();
    private Deck deck = new Deck();

    public Table() {}

    public String checkWhoWon(){
        String whoWonString = "dealer";
        if(!clientHand.isBusted()) {
            dealerTurn();
            if(dealerHand.isBusted()) {
                whoWonString = "client";
            } else {
                int dealerScore = dealerHand.getScore();
                int clientScore = clientHand.getScore();
                if(clientScore > dealerScore){
                    whoWonString = "client";
                } else if(clientScore == dealerScore){
                    whoWonString = "draw";
                }
            }
        }
        return whoWonString;
    }

    private void dealerTurn(){
        int dealerScore = dealerHand.getScore();
        while(dealerScore < 17){
            dealerTakesCard();
            dealerScore = dealerHand.getScore();
        }
    }

    public void dealerTakesCard() {
        Card card = deck.getDeck().remove(0);
        dealerHand.addCard(card);
    }

    public void clientTakesCard() {
        Card card = deck.getDeck().remove(0);
        clientHand.addCard(card);
    }

    private void cleanTable() {
        ArrayList<Card> currentDeck = deck.getDeck();

        ArrayList<Card> dealerCards = dealerHand.getCards();
        currentDeck.addAll(dealerCards);
        dealerHand = new Hand();

        ArrayList<Card> clientCards = clientHand.getCards();
        currentDeck.addAll(clientCards);
        clientHand = new Hand();

        deck.shuffleDeck();
    }

    public String getTableStateString() {
        String dealerHandString = dealerHand.getHandStateString();
        String clientHandString = clientHand.getHandStateString();
        return String.format("Dealer hand: \n" +
                "%1sClient hand: \n%2s", dealerHandString,  clientHandString);

    }

    public Hand getClientHand() {
        return clientHand;
    }

    public String checkClientHand(){
        String winner = "nobody";
        if(clientHand.isBusted() || clientHand.hasBlackjack()){
            winner = checkWhoWon();
        }
        return winner;
    }

    public String makeMessageWhoWon (String winner) {
        String message = "\n You won! \n";
        if(winner.equals("dealer")){
            message = "\n You lost! \n";
        } else if (winner.equals("draw")){
            message = "\n Draw! \n";
        }
        cleanTable();
        return message;
    }
}
