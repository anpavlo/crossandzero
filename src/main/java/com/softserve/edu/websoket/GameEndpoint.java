/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.softserve.edu.websoket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;





@ServerEndpoint(value = "/websocket/game")
public class GameEndpoint {

    private static final Log log = LogFactory.getLog(GameEndpoint.class);

    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger CONNECTION_IDS = new AtomicInteger(0);
    private static final Map<Integer,GameEndpoint> CONNECTIONS = new ConcurrentHashMap<Integer, GameEndpoint>();
    private static final SortedSet<Player> PLAYERS = new TreeSet<Player>();
    private  static final CommandHandler COMMANDHANDLER = new CommandHandler();

    
    private final Integer idConnection;
    private Session session;
    private Player player;

    public GameEndpoint() {
        this.idConnection = CONNECTION_IDS.getAndIncrement();
    }

    private Player createPlayer() {
        Player player = new Player(idConnection, GUEST_PREFIX + idConnection);
        player.setIsFree(true);
        return player ;
    }
    
    
    @OnOpen
    public void start(Session session) {
        this.setSession(session);
        this.player = createPlayer();
        PLAYERS.add(this.player);
        CONNECTIONS.put(getIdConnection(), this);
        String message = String.format("* %s has joined.", player.getNik());
        broadcast(message);
        updatePlayers();
        sendMe();
    }


    @OnClose
    public void end() {
        PLAYERS.remove(player);
        CONNECTIONS.remove(getIdConnection());
        String message = String.format("* %s has been disconnected.",player.getNik());
        broadcast(message);
        updatePlayers();
    }


    @OnMessage
    public void incoming(String jsonMessage) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = null;
        try {
            message = mapper.readValue(jsonMessage, Message.class);
            COMMANDHANDLER.processMessage(message,this.player);
        } catch (IOException e) {
            log.error("Couldn't read message from WebSocket client");
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Error: " + t.toString(), t);
    }
    
    
    
    private void sendMe(){
        Message msg = new Message();
        msg.setCmd(Cmd.ME);
        msg.setPlayer(player);
        ObjectMapper mapper = new ObjectMapper();
        try {
            synchronized (this) {
                session.getBasicRemote().sendText(mapper.writeValueAsString(msg));
            }
        }catch (IOException e) {
            log.debug("Error: Failed to send message to client", e);
            CONNECTIONS.remove(this.getIdConnection());
            try {
                this.getSession().close();
            } catch (IOException e1) {
                log.debug("Error: Failed to close session", e1);
            }
            String message = String.format("* %s has been disconnected.",this.player.getNik());
            broadcast(message);
        }
    }
    
    
    static void broadcast(String msg) {
 
        for (Map.Entry<Integer,GameEndpoint> entry : CONNECTIONS.entrySet()){
            GameEndpoint client = entry.getValue();
            try {
                synchronized (client) {
                   
                    Message message = new Message();
                    message.setCmd(Cmd.BROADCAST);
                    message.setValue(msg);
                    ObjectMapper mapper = new ObjectMapper();
                    client.getSession().getBasicRemote().sendText(mapper.writeValueAsString(message));
                }
            } catch (IOException e) {
                log.debug("Error: Failed to send message to client", e);
                CONNECTIONS.remove(client.getIdConnection());
                try {
                    client.getSession().close();
                } catch (IOException e1) {
                    log.debug("Error: Failed to close session", e1);
                }
                String message = String.format("* %s has been disconnected.",client.player.getNik());
                broadcast(message);
            }
        }
    }
    
    static void updatePlayers(){
   
        for (Map.Entry<Integer,GameEndpoint> entry : CONNECTIONS.entrySet()){
            GameEndpoint client = entry.getValue();
            try {
                synchronized (client) {
                   
                    Message message = new Message();
                    message.setCmd(Cmd.UPDATE_PLAYERS);
                    SortedSet<Player> playersSet = new TreeSet<Player>(PLAYERS);
                    playersSet.remove(client.player);
                    
                    List<Player> playerList = new ArrayList<Player>(playersSet);
                    
                    message.setPlayerList(playerList);
                    ObjectMapper mapper = new ObjectMapper();
                    client.getSession().getBasicRemote().sendText(mapper.writeValueAsString(message));
                }
            } catch (IOException e) {
                log.debug("Error: Failed to send message to client", e);
                CONNECTIONS.remove(client.getIdConnection());
                try {
                    client.getSession().close();
                } catch (IOException e1) {
                    log.debug("Error: Failed to close session", e1);
                }
                String message = String.format("* %s has been disconnected.",client.player.getNik());
                broadcast(message);
            }
        }
    }

    public Integer getIdConnection() {
        return idConnection;
    }


    public static Map<Integer, GameEndpoint> getConnections() {
        return CONNECTIONS;
    }


    public Session getSession() {
        return session;
    }


    public void setSession(Session session) {
        this.session = session;
    }


    public Player getPlayer() {
        return player;
    }

}
