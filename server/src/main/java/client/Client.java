package client;

import game.Table;


public class Client {

    private int money = 0;
    private int betSize = 0;
    private int endMoney = 0;
    private Table table = new Table();

    public Client() {}

    public Table getTable() {
        return table;
    }

    public void addMoney(int addedMoney) {

        money += addedMoney;
        endMoney -= addedMoney;

    }

    public int withdrawMoney() { return endMoney += money; }

    public void withdrawMoney(int moneyToWithdraw) {
        money -= moneyToWithdraw;
        endMoney += moneyToWithdraw;
    }

    public void setBetSize(int betSize) {

        this.betSize = betSize;
        money -= betSize;
    }

    public void addBetMoney(String winner) {
        if(winner.equals("client")){
            money += betSize * 2;
        } else if (winner.equals("draw")){
            money += betSize;
        }
    }

    public String gameMoneyScore() {
        return String.format("Current money: %1$d \n" +
                "Current bet: %2$d \n", money, betSize);
    }

    public boolean isEnoughForBet(int betSize){
        boolean isEnoughMoney = true;
        if(betSize > money){
            isEnoughMoney = false;
        }
        return isEnoughMoney;
    }

    public boolean isEnoughForWithdraw(int withdrawSize){
        boolean isEnoughMoney = true;
        if(withdrawSize > money){
            isEnoughMoney = false;
        }
        return isEnoughMoney;
    }
}
