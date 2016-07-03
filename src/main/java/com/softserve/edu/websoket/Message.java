package com.softserve.edu.websoket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Message implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4537874744149202578L;
    
    private String value;
    private Player player; 
    private Cmd cmd;
    private GameRes gameRes;
    private List<Player> playerList;
    
    
    
    public Message(String value, Cmd cmd) {
        super();
        this.value = value;
        this.cmd = cmd;
      
    }
    
  
    public Message() {}


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public Cmd getCmd() {
        return cmd;
    }


    public void setCmd(Cmd cmd) {
        this.cmd = cmd;
    }

    public GameRes getGameRes() {
        return gameRes;
    }


    public void setGameRes(GameRes gameRes) {
        this.gameRes = gameRes;
    }


    public Player getPlayer() {
        return player;
    }




    public void setPlayer(Player player) {
        this.player = player;
    }


    public List<Player> getPlayerList() {
        return playerList;
    }


    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
    
    
    
    
    
    


}
