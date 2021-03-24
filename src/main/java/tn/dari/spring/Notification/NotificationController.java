package tn.dari.spring.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// The chat message-handling Controller
@Controller
public class NotificationController {
	  // mapped to handle chat messages to the /sendmsg destination
    @MessageMapping("/sendmsg")
    // the return value is broadcast to all subscribers of /chat/messages
    @SendTo("/notification/messages")
    public Notification chat(Notification message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Notification(message.getTitle(), message.getAd());
    }
}
