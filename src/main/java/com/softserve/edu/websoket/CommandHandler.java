package com.softserve.edu.websoket;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.edu.websoket.Cmd;
import com.softserve.edu.websoket.GameEndpoint;
import com.softserve.edu.websoket.Message;
import com.softserve.edu.websoket.Player;

public class CommandHandler {
    
    
    public CommandHandler() {
        System.out.println("CommandHandler constr");
    }


    private static final Log log = LogFactory.getLog(CommandHandler.class);
    
    private Map<Integer,GameEndpoint> connections = GameEndpoint.getConnections();
    
    
    public void processMessage(Message message, Player player){
        switch(message.getCmd()){
        case CHALLENGE:
            challenge(message,player);
            break;
        case ACCEPT_CHALLENGE:
            acceptChallenge(message,player);
            break;
        case REJECT_CHALLENGE:
            rejectChalleng(message,player);
            break;
        case MOVE:
            move(message,player);
            break;
        case END_GAME:
            endGame(message,player);
            break;
        default: log.debug("Error: Invalid command");
        
        }
        
    }
    
    public void endGame(Message msg, Player playerwhichMadeLastMove) {
        Player playerFirst= connections.get(msg.getPlayer().getId()).getPlayer();
        Message message = new Message();
        message.setCmd(Cmd.END_GAME);
        message.setGameRes(msg.getGameRes());
        message.setValue(msg.getValue());
        message.setPlayer(playerwhichMadeLastMove);
        sendMessageToPlayer(message, playerFirst);
        
        playerFirst.setIsFree(true);
        playerwhichMadeLastMove.setIsFree(true);
        GameEndpoint.updatePlayers();
                 
    }

    private void move(Message msg, Player playerWhichMadeMove) {
        Player playerToSendMove = connections.get(msg.getPlayer().getId()).getPlayer();
        Message message = new Message();
        message.setCmd(Cmd.MOVE);
        message.setValue(msg.getValue());
        message.setPlayer(playerWhichMadeMove);
        sendMessageToPlayer(message, playerToSendMove);
        
    }

    private void challenge(Message msg, Player playerWhichChallenge) {
                
        Player playerToChallenge = connections.get(msg.getPlayer().getId()).getPlayer();

        if(playerToChallenge.getIsFree()){
            playerToChallenge.setIsFree(false);
                    
            playerWhichChallenge.setIsFree(false);
            GameEndpoint.updatePlayers();
            
            Message message = new Message();
            message.setCmd(Cmd.CHALLENGE);
            message.setPlayer(playerWhichChallenge);
    
            sendMessageToPlayer(message, playerToChallenge);
            System.out.println("send message to player:" + playerToChallenge.getNik());
            log.info("send message to player:" + playerToChallenge.getNik());
        }else{
            Message message = new Message();
            message.setCmd(Cmd.PLAYER_IS_BUSY);
            message.setPlayer(playerToChallenge);
            playerWhichChallenge.setIsFree(true);
            
            sendMessageToPlayer(message, playerWhichChallenge);
            GameEndpoint.updatePlayers();
            log.info("send message to player:" + playerWhichChallenge.getNik());
        }


    }
    

    private void rejectChalleng(Message message, Player playerWhichReject) {
        Player playerWhichChallenge= connections.get(message.getPlayer().getId()).getPlayer();
        playerWhichReject.setIsFree(true);
        playerWhichChallenge.setIsFree(true);
        message = new Message();
        message.setCmd(Cmd.REJECT_CHALLENGE);
        
        message.setPlayer(playerWhichReject);
        sendMessageToPlayer(message, playerWhichChallenge);
        GameEndpoint.updatePlayers();
        
    }
    
    private void acceptChallenge(Message message, Player playerCecond) {
        Player playerFirst= connections.get(message.getPlayer().getId()).getPlayer();
        message = new Message();
        message.setCmd(Cmd.START_GAME);
        message.setValue("zero");
        message.setPlayer(playerCecond);
        sendMessageToPlayer(message, playerFirst);
        
        message.setValue("cross");
        message.setPlayer(playerFirst);
        sendMessageToPlayer(message, playerCecond);
        
    }

 
    public void sendMessageToPlayer(Message msg, Integer playerId){

        GameEndpoint client = connections.get(playerId);
        try {
            synchronized (client) {
                ObjectMapper mapper = new ObjectMapper();
                client.getSession().getBasicRemote().sendText(mapper.writeValueAsString(msg));
            }
        } catch (IOException e) {
            log.debug("Error: Failed to send message to client", e);
            connections.remove(client.getIdConnection());
            try {
                client.getSession().close();
            } catch (IOException e1) {
                // Ignore
            }
            String message = String.format("* %s %s",
                    client.getPlayer().getNik(), "has been disconnected.");
            GameEndpoint.broadcast(message);
        }
    }
    

    public void sendMessageToPlayer(Message msg, Player player){
        sendMessageToPlayer(msg, player.getId());
    }
    
    
   
}
