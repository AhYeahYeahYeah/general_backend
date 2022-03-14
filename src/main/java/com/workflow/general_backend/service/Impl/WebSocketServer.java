package com.workflow.general_backend.service.Impl;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;


/**
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/websocket/{oid}")
@Component
public class WebSocketServer {

    static Log log= LogFactory.get(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收订单oid*/
    private String oid="";
    private static ConcurrentHashMap<String,String> webMessage=new ConcurrentHashMap<>();

    public ConcurrentHashMap<String,WebSocketServer> getWebSocketMap(){
        return webSocketMap;
    }
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("oid") String oid) {
        this.session = session;
        this.oid=oid;
        if(webSocketMap.containsKey(oid)){
            webSocketMap.remove(oid);
            webSocketMap.put(oid,this);
            //加入set中
        }else{
            webSocketMap.put(oid,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("订单连接:"+oid+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("订单:"+oid+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(oid)){
            webSocketMap.remove(oid);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+oid+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //以string接收json
        log.info("接受前端订单id"+message);
        String getOid=message;
        if(webMessage.containsKey(getOid)){
            if(StringUtils.isNotBlank(getOid)&&webSocketMap.containsKey(getOid)){
                String getMessage=webMessage.get(getOid);
                log.info("发送订单消息:"+getOid+",报文:"+getMessage);
                webSocketMap.get(getOid).sendMessage(getMessage);
            }else{
                log.error("订单"+getOid+",不在线！");
            }
        }else{
            log.error("订单"+getOid+",不在线！");
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
        log.error("订单错误:"+this.oid+",原因:"+error.getMessage());
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
    public static void sendInfo(String message, @PathParam("oid") String oid) throws IOException {
        log.info("发送消息到:"+oid+"，报文:"+message);
        webMessage.put(oid,message);
//        if(StringUtils.isNotBlank(oid)&&webSocketMap.containsKey(oid)){
//            log.info("订单"+oid+"后端创建消息完成！");
//            webSocketMap.get(oid).sendMessage("OK");
//        }else{
//            log.error("订单"+oid+",不在线！");
//        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}

