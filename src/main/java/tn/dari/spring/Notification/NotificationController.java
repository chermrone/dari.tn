package tn.dari.spring.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// The chat message-handling Controller
@Controller
public class NotificationController {

    @MessageMapping("/sendmsg")
    // the return value is broadcast to all subscribers of /chat/messages
    @SendTo("/topic/messages")
    public ChatMessage chat(ChatMessage message) throws Exception {System.out.println("entred "+message);
        Thread.sleep(1000); // simulated delay
        return new ChatMessage(message.getText(), message.getUsername(), message.getAvatar());
    }
	  // mapped to handle chat messages to the /sendmsg destination
	//The @MessageMapping annotation ensures that,
	//if a message is sent to the /sendmsg destination, 
	//the greeting() method is called.
	/*@MessageMapping("/sendmsg")
  // the return value is broadcast to all subscribers of /notification/messages
  @SendTo("/notification/topic")
  public String chat(String ad) throws Exception {
		System.out.println("socket");
      Thread.sleep(1000); // simulated delay
      return ad;*/
	/*   private  SimpMessagingTemplate template;

	    @Autowired
	    void WebSocketController(SimpMessagingTemplate template){
	        this.template = template;
	    }

	    @MessageMapping("/send/message")
	    public void sendMessage(String message){
	        System.out.println(message);
	        this.template.convertAndSend("/message",  message);
	    }

	   */
	  // mapped to handle chat messages to the /sendmsg destination
    }
    

