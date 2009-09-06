package chat;

import java.util.ArrayList;
import java.util.List;

public class ServerConnectionThread extends Thread {
	
	private Server parent;
	private Chat chat;
	private boolean on;
	
	public ServerConnectionThread(Server newParent, Chat newChat){
		super();
		parent = newParent;
		chat = newChat;
	}
	
	public void run(){
		on = true;
		List<Client> clientList = null;
		List<Client> newClientList = new ArrayList<Client>();
		while(on == true){
			try{
				chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_GET_CLIENT_LIST);
				clientList = parent.getClients();
				newClientList.clear();
				for(Client c: clientList){
					newClientList.add(c);
				}
			}catch (Exception e){
				chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_GET_CLIENT_LIST_FAIL);
			}
			chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_CHECK);
			int temp = 1;
			for(Client c: newClientList) {
				try{
					chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_CHECK_NUMBER + temp++ + "/" + newClientList.size() + ".\n");
					c.connected();
					chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_CHECK_SUCCESS);
					Thread.sleep(1000/newClientList.size());
				} catch (Exception e) {
					try {
						chat.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_CHECK_FAIL);
						parent.unregister(c);
					} catch (Exception f) {}
				}
			}
		}
		parent.notify();
	}
	
	public void die(){
		on = false;
	}
}
