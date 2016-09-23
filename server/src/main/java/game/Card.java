package game;


public class Card {

    private String symbolicValue;
    private int numericValue;

    protected Card(String symbolicValue, int numericValue) {
        this.symbolicValue = symbolicValue;
        this.numericValue = numericValue;
    }

    protected String getSymbolicValue() {
        return symbolicValue;
    }

    protected int getNumericValue() {
        return numericValue;
    }

}
