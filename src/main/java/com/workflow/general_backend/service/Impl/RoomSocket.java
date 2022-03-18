package com.workflow.general_backend.service.Impl;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/roomsocket/{account}")
@Component
public class RoomSocket {
    static Log log = LogFactory.get(RoomSocket.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, RoomSocket> clientsHashMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收用户account
     */
    private String account = "";
    //保存用户当前所在房间id
    private String currentRoomId = "";

    private static ConcurrentHashMap<String, Room> roomsHashMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Room> getRoomConcurrentHashMap() {
        return roomsHashMap;
    }

    public ConcurrentHashMap<String, RoomSocket> getWebSocketMap() {
        return clientsHashMap;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account) {
        this.session = session;
        this.account = account;
        if (clientsHashMap.containsKey(account)) {
            clientsHashMap.remove(account);
            clientsHashMap.put(account, this);
            //加入set中
        } else {
            clientsHashMap.put(account, this);
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
    public void onClose() throws IOException {
        if (clientsHashMap.containsKey(account)) {
            clientsHashMap.remove(account);
            //从set中删除
            subOnlineCount();
        }
        JSONObject response = new JSONObject();
        response.put("path", "V1/Room/Quit");
        response.put("accountList","");
        List<String> list=new ArrayList<>();
        int flag=0;
        for (String i:roomsHashMap.keySet()
             ) {
            Room aRoom = roomsHashMap.get(i);
            List<String> accountList=aRoom.getAccountList();
            for(int j=0;j<accountList.size();j++){
                if(accountList.get(j).equals(account)){
                    accountList.remove(j);
                    list=accountList;
                    flag=1;
                    break;
                }
            }
            if(flag==1)
                break;
        }
        response.put("accountList",list);
        for (String i:list
             ) {
            response.put("account",i);
            sendInfo(response.toString(),i);
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
                        roomsHashMap.put(room.getString("id"), aRoom);
                        this.currentRoomId = room.getString("id");
                        break;
                    }
                    case "V1/Room/Join": {
                        JSONObject room = data.getJSONObject("room");
                        Room aRoom;
                        String id = room.getString("id");
                        this.currentRoomId = id;
                        JSONObject json = new JSONObject();
                        if (roomsHashMap.containsKey(id)) {
                            aRoom = roomsHashMap.get(id);
                            if (aRoom.getPassword().equals(room.getString("password"))) {
                                List<String> first = new ArrayList<>();
                                if (aRoom.getAccountList() != null)
                                    first = aRoom.getAccountList();
                                first.add(account);
                                aRoom.setAccountList(first);
                                log.info("account " + account + " join accept");
                                json.put("path", "V1/Room/Join");
                                json.put("result", "Success");
                                json.put("msg", "");
                                json.put("accountList", first.toString().replace(" ",""));
                                if (aRoom.getFlow() != null)
                                    json.put("msg", aRoom.getFlow());

                            } else {
                                json.put("path", "V1/Room/Join");
                                json.put("result", "Failed");
                                json.put("msg", "Wrong password");
                                json.put("accountList", "");
                            }

                        } else {
                            json.put("path", "V1/Room/Join");
                            json.put("result", "Failed");
                            json.put("msg", "Wrong RoomId");
                            json.put("accountList", "");

                        }
                        List<String> list = roomsHashMap.get(id).getAccountList();
                        for (String i : list) {
                            json.put("account",i);
                            sendInfo(json.toString(), i);
                        }
//                        sendInfo(json.toString(), account);
                        break;
                    }
                    case "V1/Room/Query": {
                        JSONObject response = new JSONObject();
                        response.put("path", "V1/Room/Query");
                        List<Room> rooms = QueryRooms();
                        JSONObject roomJsonObject = new JSONObject();
                        roomJsonObject.put("rooms", rooms);
                        response.put("data", roomJsonObject);
                        sendInfo(response.toString(), account);
                        break;
                    }
                    case "V1/Room/Quit": {
                        JSONObject response = new JSONObject();
                        response.put("path", "V1/Room/Quit");
                        JSONObject room = data.getJSONObject("room");
                        String roomid = room.getString("id");
                        if (roomsHashMap.containsKey(roomid)) {
                            List<String> accountlist = new ArrayList<>();
                            accountlist = roomsHashMap.get(roomid).getAccountList();
                            accountlist.remove(account);
                            roomsHashMap.get(roomid).setAccountList(accountlist);
//                            if(roomsHashMap.get(roomid).getAccountList().isEmpty()){
//                                roomsHashMap.remove(roomid);
//                            }
                            log.info("account " + account + " quit success");
                            response.put("result", "Success");
                            response.put("msg", "");
                            response.put("accountList",accountlist.toString().replace(" ",""));
                        } else {
                            response.put("result", "Failed");
                            response.put("msg", "Quit Error");
                            response.put("accountList","");
                        }
                        this.currentRoomId = "";
                        List<String> list = roomsHashMap.get(roomid).getAccountList();
                        for (String i : list) {
                            response.put("account",i);
                            sendInfo(response.toString(), i);
                        }
//                        sendInfo(response.toString(), account);
                        break;
                    }
                    case "V1/Data/Edit": {
                        JSONObject room = data.getJSONObject("room");
                        String id = room.getString("id");
                        String timeStamp = data.getString("timeStamp");
                        JSONArray flow = data.getJSONArray("flow");
                        JSONObject json = new JSONObject();
                        roomsHashMap.get(id).setFlow(flow.toString());
                        if (roomsHashMap.containsKey(id)) {
                            Room aRoom = roomsHashMap.get(id);
                            List<String> list = aRoom.getAccountList();
                            json.put("path", "V1/Data/Edit");
                            JSONObject da = new JSONObject();
                            JSONObject oneroom = new JSONObject();
                            oneroom.put("id", id);
                            for (String i : list) {
                                da.put("account", i);
                                da.put("flow", flow);
                                da.put("room", oneroom);
                                json.put("data", da);
                                sendInfo(json.toString(), i);
                            }
                        }

                        break;
                    }
                    case "V1/Room/Delete": {
                        JSONObject response = new JSONObject();
                        response.put("path", "V1/Room/Delete");
                        JSONObject room = data.getJSONObject("room");
                        String roomid = room.getString("id");
                        String password = room.getString("password");

                        if (!roomsHashMap.containsKey(roomid)) {
                            response.put("result", "Failed");
                            response.put("msg", "Delete Error: id not found");
                        } else {
                            if (password.equals("Success") || password.equals(roomsHashMap.get(roomid).getPassword())) {

                                roomsHashMap.remove(roomid);

                                log.info("account " + account + " delete success");
                                response.put("result", "Success");
                                response.put("msg", "");
                            } else {
                                response.put("result", "Failed");
                                response.put("msg", "Delete Error: password incorrect");
                            }
                        }
                        this.currentRoomId = "";
                        sendInfo(response.toString(), account);
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

    //查询所有房间
    public List<Room> QueryRooms() {
        List<Room> rooms = new ArrayList<>();
        for (Map.Entry<String, Room> entry : roomsHashMap.entrySet()) {
            Room room = new Room();
            room.setSid(entry.getValue().getSid());
            room.setName(entry.getValue().getName());
            rooms.add(room);
        }
        return rooms;
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("account") String account) throws IOException {
        log.info("用户端:" + account + "，报文:" + message);

        if (StringUtils.isNotBlank(account) && clientsHashMap.containsKey(account)) {
            log.info("用户端" + account + "后端创建消息完成！");
            clientsHashMap.get(account).sendMessage(message);
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
