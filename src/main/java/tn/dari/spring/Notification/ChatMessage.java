package tn.dari.spring.Notification;

public class ChatMessage {
    private String text;
    private String username;
    private int avatar;

    public ChatMessage(){

    }

    public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public ChatMessage(String text, String username, int avatar) {
		super();
		this.text = text;
		this.username = username;
		this.avatar = avatar;
	}

	public ChatMessage(String text ,String username) {
        this.text = text;
        this.username = username;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

}
