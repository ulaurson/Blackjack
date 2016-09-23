package controller;


import game.Table;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import client.Client;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    private Map<String, Client> clients = new HashMap<>();


    @RequestMapping(value="/login", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String login(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        String clientName = json.getString("name");
        Client client = new Client();
        clients.put(clientName, client);

        String message = "Welcome " + clientName + "!";
        return makeResponse("addMoney", message).toString();
    }

    @RequestMapping(value="/addingMoney", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String addingMoney(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);

        int moneyToAdd = json.getInt("addingMoneySize");
        client.addMoney(moneyToAdd);

        String message = client.gameMoneyScore();

        return makeResponse("placeBet", message).toString();
    }

    @RequestMapping(value="/withdrawingMoney", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String withdrawingMoney(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);

        int moneyToWithdraw = json.getInt("withdrawingMoneySize");

        String message;
        if(client.isEnoughForWithdraw(moneyToWithdraw)) {
            client.withdrawMoney(moneyToWithdraw);
            message = client.gameMoneyScore();
        } else{
            message = client.gameMoneyScore();
            message += "You do not have enough money to withdraw that sum!";
        }

        return makeResponse("placeBet", message).toString();
    }

    @RequestMapping(value="/placingBet", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String placingBet(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);

        String messageType = "hitOrStand";
        String message;

        int betSize = json.getInt("betSize");

        if(client.isEnoughForBet(betSize)) {
            client.setBetSize(betSize);

            Table table = client.getTable();

            table.dealerTakesCard();
            table.clientTakesCard();
            table.clientTakesCard();

            String winnerMessage = "";
            if(table.getClientHand().hasBlackjack()){
                String winner = table.checkWhoWon();
                client.addBetMoney(winner);
                client.setBetSize(0);
                messageType = "wantEndGame";
                winnerMessage += table.makeMessageWhoWon(winner);
            }

            message = client.gameMoneyScore();
            message += table.getTableStateString();
            message += winnerMessage;
        } else {
            message = client.gameMoneyScore();
            message += "You do not have enough money to bet this sum!";
            messageType = "addMoney";
        }

        return makeResponse(messageType, message).toString();
    }

    @RequestMapping(value="/hitting", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String hitting(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);
        Table table = client.getTable();

        table.clientTakesCard();

        String messageType = "hitOrStand";
        String message = client.gameMoneyScore();
        message += table.getTableStateString();

        String winner = table.checkClientHand();
        if(!winner.equals("nobody")){
            messageType = "wantEndGame";
            client.addBetMoney(winner);
            client.setBetSize(0);
            message = client.gameMoneyScore();
            message += table.getTableStateString();
            message += table.makeMessageWhoWon(winner);
        }

        return makeResponse(messageType, message).toString();
    }

    @RequestMapping(value="/standing", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String standing(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);
        Table table = client.getTable();


        String winner = table.checkWhoWon();
        client.addBetMoney(winner);
        client.setBetSize(0);

        String message = client.gameMoneyScore();
        message += table.getTableStateString();
        message += table.makeMessageWhoWon(winner);

        String messageType = "wantEndGame";

        return makeResponse(messageType, message).toString();
    }

    @RequestMapping(value="/endingGame", method=POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String endingGame(@RequestBody String jsonString){
        JSONObject json = new JSONObject(jsonString);
        Client client = getCurrentClient(json);

        int endMoney = client.withdrawMoney();

        return makeResponse("endGame", "Now you have: " + endMoney + " euros!").toString();
    }

    private Client getCurrentClient(JSONObject json) {
        String clientName = json.getString("name");
        return clients.get(clientName);
    }

    private JSONObject makeResponse(String messageType, String message) {
        JSONObject response = new JSONObject();
        response.put("messageType", messageType);
        response.put("message", message);

        logger.info("SentMessage: {}", response.toString());
        return response;
    }
}
