package chat;

public class ClientConnectionThread extends Thread {
	
	private Client parent;
	private Server server;
	private Chat chat;
	private boolean on;
	
	public ClientConnectionThread(Client newParent, Server newServer,Chat newChat){
		super();
		parent = newParent;
		server = newServer;
		chat = newChat;
	}
	
	public void run(){
		on = true;
		while(on == true){
			try{
				chat.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_CHECK);
				if(server.connected()){
					chat.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_SUCCESS);
					Thread.sleep(1000);
				} else {
					parent.disconnect();
					on = false;
				}
			} catch (Exception e) {
				chat.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_FAIL);
				try {
					parent.disconnect();
					chat.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_DISCONNECTION_SUCCESS);
					on = false;
				} catch (Exception f) {
					chat.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_DISCONNECTION_FAIL);
				}
			}
		}
		parent.notify();
	}
	
	public void die(){
		on = false;
	}
}
