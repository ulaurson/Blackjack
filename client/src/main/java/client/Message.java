package client;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Message {

    private String serverAddress = new client.Properties().getServerAddress();
    private Logger logger = LoggerFactory.getLogger(Message.class);

    private String userName;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    protected Message(String userName) throws IOException {
        this.userName = userName;
    }

    protected void sendMessage(String messageType, JSONObject message) throws IOException {

        message.put("name", userName);
        HttpMethod httpMethod = HttpMethod.POST;
        String url = serverAddress + messageType;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application","json"));
        RestTemplate restTemplate = new RestTemplate();

        logger.info("SentMessage: {}", message.toString());

        HttpEntity<String> requestEntity = new HttpEntity<>(message.toString(), requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                httpMethod, requestEntity, String.class);

        interpretMessage(new JSONObject(responseEntity.getBody()));
    }

    private void interpretMessage(JSONObject receivedMessage) throws IOException {

        String messageType = receivedMessage.getString("messageType");
        String message = receivedMessage.getString("message");
        System.out.println(message);

        switch(messageType) {
            case "addMoney": {
                addingMoney();
                break;
            }

            case "placeBet": {
                placingBet();
                break;
            }

            case "hitOrStand": {
                hittingOrStanding();
                break;
            }

            case "wantEndGame": {
                wantEndGame();
                break;
            }

            case "endGame": {
                break;
            }
        }
    }

    private void addingMoney() throws IOException {
        System.out.println("Enter amount of money that you want to add into the game: ");
        int addingMoneySize = Integer.parseInt(br.readLine());
        JSONObject message = new JSONObject();
        message.put("addingMoneySize", addingMoneySize);
        sendMessage("addingMoney", message);
    }

    private void withdrawingMoney() throws IOException {
        System.out.println("Enter amount of money that you want to withdraw: ");
        int addingMoneySize = Integer.parseInt(br.readLine());
        JSONObject message = new JSONObject();
        message.put("withdrawingMoneySize", addingMoneySize);
        sendMessage("withdrawingMoney", message);
    }

    private void placingBet() throws IOException {
        System.out.println("Enter amount of money that you want to bet: ");
        int betSize = Integer.parseInt(br.readLine());
        JSONObject message = new JSONObject();
        message.put("betSize", betSize);
        sendMessage("placingBet", message);
    }

    private void hittingOrStanding() throws IOException {
        System.out.println("Do you want to hit(h) or stand(s): ");
        String decision = br.readLine();
        JSONObject message = new JSONObject();
        String messageType = "hitting";
        if(decision.equals("s")){
            messageType = "standing";
        }
        sendMessage(messageType, message);
    }

    private void wantEndGame() throws IOException {
        System.out.println("Do you want to continue playing? (y/n)");
        String decision = br.readLine();
        JSONObject message = new JSONObject();
        if(decision.equals("n")){
            sendMessage("endingGame", message);
        } else {
            askingToWithdrawMoney();
        }
    }

    private void askingToAddMoney() throws IOException {
        System.out.println("Do you want to addMoney? (y/n) ");
        String decision = br.readLine();
        if(decision.equals("n")){
            placingBet();
        } else {
            addingMoney();
        }
    }

    private void askingToWithdrawMoney() throws IOException {
        System.out.println("Do you want to withdrawMoney? (y/n) ");
        String decision = br.readLine();
        if(decision.equals("n")){
            askingToAddMoney();
        } else {
            withdrawingMoney();
        }
    }

}
