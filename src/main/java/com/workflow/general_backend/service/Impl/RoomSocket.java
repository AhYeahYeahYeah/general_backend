package com.workflow.general_backend.service.Impl;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.entity.Room;
import com.workflow.general_backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/roomsocket/{account}")
@Component
public class RoomSocket {
    static Log log = LogFactory.get(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, RoomSocket> roomSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收用户account
     */
    private String account = "";

    private static ConcurrentHashMap<String, Room> roomConcurrentHashMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Room> getRoomConcurrentHashMap() {
        return roomConcurrentHashMap;
    }

    public ConcurrentHashMap<String, RoomSocket> getWebSocketMap() {
        return roomSocketMap;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account) {
        this.session = session;
        this.account = account;
        if (roomSocketMap.containsKey(account)) {
            roomSocketMap.remove(account);
            roomSocketMap.put(account, this);
            //加入set中
        } else {
            roomSocketMap.put(account, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户端account连接:" + account + ",当前在线的用户端数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户端:" + account + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (roomSocketMap.containsKey(account)) {
            roomSocketMap.remove(account);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户端退出:" + account + ",当前在线用户端数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //以string接收json
        log.info("用户端account" + account + "报文:" + message);

        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
//                jsonObject.put("fromOid", this.account);
                String path = jsonObject.getString("path");
                JSONObject data = jsonObject.getJSONObject("data");
                String account = data.getString("account");
                String token = data.getString("token");

                Jws<Claims> claimsJws;
                // 校验JWT字符串
                claimsJws = JwtUtils.decode(token);
                // 取出playload
                Claims claims = claimsJws.getBody();
                // 取出playload中的数据(如果是自己自定义的键值可以通过get()方法取出，假设储存的值可以转换String;如果是playload中的标准字段可以直接通过函数取出)
                // 取出用户账号即account
                String accountGet = (String) claims.get("account");
                if (!accountGet.equals(account)) {
                    System.out.println("1-false");
                } else {
                    System.out.println("1-true");
                }

                switch (path) {
                    case "V1/Auth/Connect": {
                        log.info(account + "connect success");
                        break;
                    }
                    case "V1/Room/Create": {
                        JSONObject room = data.getJSONObject("room");
                        Room aRoom = new Room();
                        aRoom.setSid(room.getString("id"));
                        aRoom.setName(room.getString("name"));
                        aRoom.setPassword(room.getString("password"));
                        roomConcurrentHashMap.put(room.getString("id"), aRoom);
                        break;
                    }
                    case "V1/Room/Join": {
                        JSONObject room = data.getJSONObject("room");
                        Room aRoom = new Room();
                        String id = room.getString("id");
                        JSONObject json = new JSONObject();
                        if (roomConcurrentHashMap.containsKey(id)) {
                            aRoom = roomConcurrentHashMap.get(id);
                            if (aRoom.getPassword().equals(room.getString("password"))) {
                                aRoom.getAccountList().add(account);
                                log.info("account " + account + " join accept");
                                json.put("path", "V1/Room/Join");
                                json.put("result", "Success");
                                json.put("msg", "");

                            } else {
                                json.put("path", "V1/Room/Join");
                                json.put("result", "Failed");
                                json.put("msg", "Wrong password");
                            }

                        } else {
                            json.put("path", "V1/Room/Join");
                            json.put("result", "Failed");
                            json.put("msg", "Wrong RoomId");

                        }
                        sendInfo(String.valueOf(json), account);
                        break;
                    }
                    case "V1/Room/Query": {


                        break;
                    }
                    case "V1/Data/Edit": {


                        break;
                    }
                    default: {
                        log.info("unknown path");
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户端错误:" + this.account + ",原因:" + error.getMessage());
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
     */
    public static void sendInfo(String message, @PathParam("account") String account) throws IOException {
        log.info("用户端:" + account + "，报文:" + message);

        if (StringUtils.isNotBlank(account) && roomSocketMap.containsKey(account)) {
            log.info("用户端" + account + "后端创建消息完成！");
            roomSocketMap.get(account).sendMessage(message);
        } else {
            log.error("用户端" + account + ",不在线！");
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        RoomSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        RoomSocket.onlineCount--;
    }


}
