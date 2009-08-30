package chat;

public class ClientConnectionThread extends Thread {
	
	private Client parent;
	private Server server;
	private ChatGUI chatGUI;
	private boolean on;
	
	public ClientConnectionThread(Client newParent, Server newServer,ChatGUI newChatGUI){
		super();
		parent = newParent;
		server = newServer;
		chatGUI = newChatGUI;
	}
	
	public void run(){
		on = true;
		while(on == true){
			try{
				chatGUI.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_CHECK);
				if(server.connected()){
					chatGUI.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_SUCCESS);
					Thread.sleep(1000);
				} else {
					parent.disconnect();
					on = false;
				}
			} catch (Exception e) {
				chatGUI.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_FAIL);
				try {
					parent.disconnect();
					chatGUI.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_DISCONNECTION_SUCCESS);
					on = false;
				} catch (Exception f) {
					chatGUI.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_DISCONNECTION_FAIL);
				}
			}
		}
		parent.notify();
	}
	
	public void die(){
		on = false;
	}
}
