package com.fir.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.fir.backend.consumer.utils.Chess;
import com.fir.backend.consumer.utils.Game;
import com.fir.backend.consumer.utils.JwtAuthentication;
import com.fir.backend.consumer.utils.Player;
import com.fir.backend.mapper.RecordMapper;
import com.fir.backend.mapper.UserMapper;
import com.fir.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    final public static ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();


    public static CopyOnWriteArrayList<User> matchPool = new CopyOnWriteArrayList<>();
    Session session = null;
    private User user;

    private Game game;
    public static RecordMapper recordMapper;
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }

    public static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    public void setGame(Game game){
        this.game = game;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        // 建立连接
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        if(userId == -1){
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            this.user = userMapper.selectById(userId);
            System.out.println("USER ID: "+this.user.getId()+" connected");
            users.put(userId,this);
        }
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        CopyOnWriteArrayList<User> temp = new CopyOnWriteArrayList<>();
        if(this.user != null){
            for(int i = 0; i < matchPool.size();i ++){
                if(!matchPool.get(i).equals(this.user))temp.add(matchPool.get(i));
            }
            matchPool = temp;
            System.out.println("USER ID: "+this.user.getId()+" closed");
            users.remove(this.user.getId());
        }


    }
    public void startMatching(){

        if(this.user != null){
            for(int i = 0; i < matchPool.size();i ++){
                if(matchPool.get(i).equals(this.user))return;
            }
            matchPool.add(user);
            System.out.println("USER ID: "+this.user.getId()+" start matching");
            System.out.println("matching number: "+matchPool.size());
        }
    }

    public void stopMatching(){
        if(this.user != null){
            matchPool.remove(user);
            System.out.println("USER ID:"+user.getId()+" cancel matching");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)){
            startMatching();

        }else if("stop-matching".equals(event)){
            stopMatching();

        }else if("move".equals(event)){


            Integer x = data.getInteger("chess_x");
            Integer y = data.getInteger("chess_y");
            Integer id = data.getInteger("chess_id");
            Chess c = new Chess(x, y, id);

            game.setNextChess(c);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {

        error.printStackTrace();
    }
    public void sendMessage(String message){
        synchronized (this.session){
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

