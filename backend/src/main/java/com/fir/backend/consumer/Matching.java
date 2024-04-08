package com.fir.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.fir.backend.consumer.utils.Game;
import com.fir.backend.consumer.utils.Player;
import com.fir.backend.pojo.User;

import java.util.concurrent.CopyOnWriteArrayList;

public class Matching extends Thread{
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //消耗队列

            if(WebSocketServer.matchPool.size() >= 2)consumer();

        }
    }
    private void consumer(){
        CopyOnWriteArrayList<User> copyPool = new CopyOnWriteArrayList<>();
        Boolean []used = new  Boolean[WebSocketServer.matchPool.size()];
        for(int i = 0 ; i < used.length; i ++)used[i] = false;

        for(int i  = 0; i < used.length; i ++){
            if(used[i])continue;
            for(int j = 0; j < used.length; j ++){
                if(used[j])continue;
                if(i != j){
                    User userA = WebSocketServer.matchPool.get(i);
                    User userB = WebSocketServer.matchPool.get(j);
                    if(userA.getRating() - userB.getRating() <= 150 && userA != null && userB != null){
                        startGame(userA,userB);
                        used[i] = used[j] = true;
                    }
                }

            }
        }
        for(int i = 0; i < used.length; i ++){
            if(!used[i])copyPool.add(WebSocketServer.matchPool.get(i));
        }
        WebSocketServer.matchPool = copyPool;
    }
    private void startGame(User userA,User userB){
        System.out.println(userA.getId()+" vs "+ userB.getId()+ " start game ");



        Game game = new Game(13,13,new Player(userA.getId(),null)
                ,new Player(userB.getId(),null));

        WebSocketServer.users.get(userA.getId()).setGame(game);
        WebSocketServer.users.get(userB.getId()).setGame(game);
        game.start();

        JSONObject respA = new JSONObject();
        JSONObject respB = new JSONObject();
        respA.put("event","start-matching");
        respB.put("event","start-matching");
        respA.put("a_id",userA.getId());
        respA.put("b_id",userB.getId());
        respA.put("next",userA.getId());
        respB.put("a_id",userA.getId());
        respB.put("b_id",userB.getId());
        respB.put("next",userA.getId());
        respA.put("opponent_username",userB.getUsername());
        respA.put("opponent_photo",userB.getPhoto());
        respA.put("opponent_rating",userB.getRating());
        respA.put("rating",userA.getRating());
        respB.put("opponent_username",userA.getUsername());
        respB.put("opponent_photo",userA.getPhoto());
        respB.put("opponent_rating",userA.getRating());
        respB.put("rating",userB.getRating());

        WebSocketServer.users.get(userA.getId()).sendMessage(respA.toJSONString());
        WebSocketServer.users.get(userB.getId()).sendMessage(respB.toJSONString());
    }
}
