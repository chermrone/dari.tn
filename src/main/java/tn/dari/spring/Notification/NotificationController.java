package tn.dari.spring.Notification;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.service.AdService;
import tn.dari.spring.service.UIadService;

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
	@MessageMapping("/sendnotif")
    // the return value is broadcast to all subscribers of /notification/messages
    @SendTo("/topic/users")
    public String chat(String adid) throws Exception {
  		System.out.println("socket");
        Thread.sleep(1000); // simulated delay
        return adid;}
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
	    @Autowired SimpMessagingTemplate template;

	/*  
	    void WebSocketController(SimpMessagingTemplate template){
	        this.template = template;
	    }
*/
	    @Autowired
		UIadService Adserv;
		@Scheduled(cron = "0 21 14 * * *")
	    @MessageMapping("/send/notifLeast")
	    public void sendMessageRENT(){
	    	List<Ad> ads=Adserv.MostCheapestAdRENT();
	        System.out.println(ads);
	        for (Ad ad2 : ads) {
	        	String str = "" + ad2.getAdId();

	        	 this.template.convertAndSend("/topic/users",str);
			}	       
	    }
			@Scheduled(cron = "0 21 14 * * *")
		    @MessageMapping("/send/notifLeast/buy")
		    public void sendMessageSELL(){
		    	List<Ad> ads=Adserv.MostCheapestAdSELL();
		        System.out.println(ads);
		        for (Ad ad2 : ads) {
		        	String str = "" + ad2.getAdId();

		        	 this.template.convertAndSend("/topic/users",str);
				}	
		    }

	  // mapped to handle chat messages to the /sendmsg destination
    }
    

