package chat;

public class PopupThread extends Thread {
	
	private Popup popup;
	
	public PopupThread(ChatGUI parent){
		super();
		popup = new Popup(parent, Strings.MESSAGE_PLEASE_WAIT,Strings.MESSAGE_PLEASE_WAIT);
	}
	
	public void run(){
		popup.setVisible(true);
	}
	
	public Popup getPopup(){
		return popup;
	}
}
