package tn.dari.spring.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

  /*  private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/message")
    public void sendMessage(String message){
        System.out.println(message+" entred ");
        this.template.convertAndSend("/topic/hello",  message);  System.out.println(message+" entred fin ");
    }

  */
}