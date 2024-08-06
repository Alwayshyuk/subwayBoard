package me.sanghyuk.subwayboard.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatService extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        list.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        list.remove(session);
    }
}