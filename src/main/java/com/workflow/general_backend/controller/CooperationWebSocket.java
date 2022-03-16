package com.workflow.general_backend.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.entity.Room;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;


/**
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/roomsocket/{account}")
@Component
public class CooperationWebSocket {

    static Log log= LogFactory.get(CooperationWebSocket.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, CooperationWebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收用户account*/
    private String account="";
    //保存房间中的信息
    private ConcurrentHashMap<String,Room> roomConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("account") String account) {
        this.session = session;
        this.account=account;
        if(webSocketMap.containsKey(account)){
            webSocketMap.remove(account);
            webSocketMap.put(account,this);
            //加入set中
        }else{
            webSocketMap.put(account,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:"+account+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:"+account+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(account)){
            webSocketMap.remove(account);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+account+",当前在线人数为:" + getOnlineCount());
    }

    //查询所有房间
    public List<Room> QueryRooms(){
        List<Room> rooms = new ArrayList<>();
        for(Map.Entry<String,Room> entry:roomConcurrentHashMap.entrySet()){
            Room room = new Room();
            room.setSid(entry.getValue().getSid());
            room.setName(entry.getValue().getName());
            rooms.add(room);
        }
        return rooms;
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //以string接收json
        log.info("前端信息："+message);
        //解析发送的报文
        JSONObject jsonObject = JSON.parseObject(message);
        //追加发送人(防止串改)
//                jsonObject.put("fromOid", this.account);
        String path = jsonObject.getString("path");
        JSONObject data = jsonObject.getJSONObject("data");
        String account = data.getString("account");
        String token = data.getString("token");
        switch (path){
            case "V1/Room/Query":
                JSONObject response = new JSONObject();
                response.put("path","V1/Room/Query");
                List<Room> rooms=QueryRooms();
                JSONObject roomJsonObject=new JSONObject();
                roomJsonObject.put("rooms",rooms);
                response.put("data",roomJsonObject);
                sendInfo(response.toString(),account);
                break;
            case "V1/Data/Edit":

                break;
        }
        //可以群发消息
        //消息保存到数据库、redis
//        if(StringUtils.isNotBlank(message)){
//            try {
//                //解析发送的报文
//                JSONObject jsonObject = JSON.parseObject(message);
//                //追加发送人(防止串改)
//                jsonObject.put("fromOid",this.oid);
//                String toUserId=jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
//                }else{
//                    log.error("请求的userId:"+toUserId+"不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.account+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message, @PathParam("account") String account) throws IOException {
        log.info("发送消息到:"+account+"，报文:"+message);

        if(StringUtils.isNotBlank(account)&&webSocketMap.containsKey(account)){
            log.info("用户"+account+"后端创建消息完成！");
            webSocketMap.get(account).sendMessage(message);
        }else{
            log.error("用户"+account+",不在线！");
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}


