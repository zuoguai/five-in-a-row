package com.fir.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.fir.backend.consumer.WebSocketServer;
import com.fir.backend.pojo.Record;
import com.fir.backend.pojo.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    private  Integer rows;
    private  Integer cols;
    private int [][] g;
    private Player playerA ,playerB;
    private String status = "playing" ; //playing/finished
    private String loser; // all,a,b
    private ReentrantLock lock = new ReentrantLock();
    public Player nextPlayer;
    private Chess nextChess;
    private List<Chess> allChess = new ArrayList<>();

    public Game(Integer rows, Integer cols, Player playerA, Player playerB) {
        this.rows = rows;
        this.cols = cols;
        this.g = new int[rows][cols];
        this.playerA = playerA;
        this.playerB = playerB;
        this.nextPlayer = playerA;
        this.nextChess = null;
        this.loser = "none";
    }

    public void setNextChess(Chess chess){
        lock.lock();
        try{
            this.nextChess = chess;
        }finally {
            lock.unlock();
        }
    }

    //这种情况在前端就应该排除
    private boolean check_valid(){
        for (Chess c : allChess) {
            if (c.getX().equals(nextChess.getX()) && c.getY().equals(nextChess.getY())) {
                return false;
            }
        }
        Integer id = 1;
        if(id.equals(nextChess.getId())) {
            if (!nextPlayer.getId().equals(playerA.getId())) return false;
        }else {
            if (!nextPlayer.getId().equals(playerB.getId())) return false;
        }
        return true;

    }
    private boolean judge(){
        if(!check_valid()){
            if(nextPlayer.getId().equals(playerA.getId())){
                nextPlayer = playerB;
            }else{
                nextPlayer = playerA;
            }
            setNextChess(new Chess(99,99,99));
            return true;
        }

        if(nextChess.getId() != 99){
            allChess.add(nextChess);
        }

        for (Chess c : allChess) {
            g[c.getX()][c.getY()] = c.getId();
        }
        for(int i = 0; i < cols; i ++){
            for(int j = 0; j < rows; j ++) {
                if(g[i][j] != 1 && g[i][j] != 2)g[i][j] = 9;
            }
        }


        int last = 9;
        int sum = 1;
        //判断列
        for (int i = 1; i < this.cols; i++) {
            for (int j = 1; j < this.rows; j++) {
                if (g[i][j] != 9) {
                    if (last == g[i][j]) sum++;
                    else sum = 1;
                } else sum = 1;
                last = g[i][j];
                if (sum == 5) {
                    status = "finished";
                    if(nextPlayer.getId().equals(playerA.getId()))loser="B";
                    else loser="A";
                    return true;
                }
            }
        }

        last = 9;
        sum = 1;
        //判断行
        for (int i = 1; i < this.cols; i++) {
            for (int j = 1; j < this.rows; j++) {
                if (g[j][i] != 9) {
                    if (last == g[j][i]) sum++;
                    else sum = 1;
                } else sum = 1;
                last = g[j][i];
                if (sum == 5) {


                    status="finished";
                    if(nextPlayer.getId().equals(playerA.getId()))loser="B";
                    else loser="A";
                    return true;
                }
            }
        }

        last = 9;
        sum = 1;
        //判断反对角线
        for (int i = 6; i < 21; i++) {
            for (int j = 1; j < i && j <= 12; j++) {
                if (i - j > 12) continue;
                if (g[j][i - j] != 9) {
                    if (last == g[j][i - j]) sum++;
                    else sum = 1;
                } else sum = 1;
                last = g[j][i - j];
                if (sum == 5) {


                    status="finished";
                    if(nextPlayer.getId().equals(playerA.getId()))loser="B";
                    else loser="A";
                    return true;
                }
            }
        }

        last = 9;
        sum = 1;
        //判断对角线
        for (int i = -7; i < 8; i++) {
            for (int j = 12; j >= 1; j--) {
                if (j + i < 1 || j + i > 12) continue;
                if (g[j][j + i] != 9) {
                    if (last == g[j][i + j]) sum++;
                    else sum = 1;
                } else sum = 1;
                last = g[j][i + j];
                if (sum == 5) {


                    status="finished";
                    if(nextPlayer.getId().equals(playerA.getId()))loser="B";
                    else loser="A";
                    return true;
                }
            }
        }
        return true;
    }
    private void sendMove(){
        lock.lock();
        try{
            JSONObject resp = new JSONObject();
            resp.put("event","move");
            if(nextPlayer.getId().equals(playerA.getId())){
                nextPlayer = playerB;
                resp.put("next", playerB.getId());
            }else{
                nextPlayer = playerA;
                resp.put("next",playerA.getId());
            }
            if(nextChess != null){
                resp.put("chess_x",nextChess.getX());
                resp.put("chess_y",nextChess.getY());
                resp.put("chess_id",nextChess.getId());
                nextChess = null;

            }
            sendALL(resp.toJSONString());
        }finally {
            lock.unlock();
        }


    }
    private void sendResult(){
        JSONObject resp = new JSONObject();
        sendMove();
        resp.put("event","result");
        resp.put("loser",loser);
        if("A".equals(loser)){
            saveToDatabase(playerA.getId(),-1);
            saveToDatabase(playerB.getId(),3);
        }else{
            saveToDatabase(playerA.getId(),3);
            saveToDatabase(playerB.getId(),-1);
        }
        saveRecord();
        sendALL(resp.toJSONString());

    }
    private void saveToDatabase(Integer id, Integer rating){
        User user = WebSocketServer.userMapper.selectById(id);
        if(user != null){
            if(rating > 0){
                user.setWin(user.getWin() + 1);
            }else {
                user.setLose(user.getLose() + 1);
            }
            user.setRating(user.getRating() + rating);
            WebSocketServer.userMapper.updateById(user);
        }

    }
    private void saveRecord(){
        Record record = new Record(null,
                playerA.getId(),
                playerB.getId(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }
    private void sendALL(String message){
        if(WebSocketServer.users.get(playerA.getId()) != null){
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        }
        if(WebSocketServer.users.get(playerB.getId()) != null){
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);
        }
    }
    private boolean nextStep(){
        for(int i = 0; i < 250; i ++){
            try{
                Thread.sleep(100);
                lock.lock();
                try{
                    if("finished".equals(loser))return false;

                    if(nextChess != null)return true;
                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(nextPlayer.getId().equals(playerA.getId()))loser="A";
        else loser="B";
        return false;
    }
    @Override
    public void run() {
        for(int i = 0; i < 1000; i ++){

            if(nextStep()){
                judge();

                if("playing".equals(status)){
                    sendMove();
                }else{
                    sendResult();
                    break;
                }
            }else if("finished".equals(status)){
                sendResult();
            } else{
                status = "finished";
                System.out.println("finished game");
                lock.lock();
                try{
                    if(nextPlayer.getId().equals(playerA.getId())){
                        loser = "A";
                    }else {
                        loser = "B";
                    }
                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }

    }
}
