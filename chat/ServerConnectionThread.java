package chat;

import java.util.ArrayList;
import java.util.List;

public class ServerConnectionThread extends Thread {
	
	private Server parent;
	
	public ServerConnectionThread(Server newParent){
		super();
		parent = newParent;
	}
	
	public void run(){
		List<Client> clientList = null;
		List<Client> newClientList = new ArrayList<Client>();
		while(true){
			try{
				clientList = parent.getClients();
				newClientList.clear();
				for(Client c: clientList){
					newClientList.add(c);
				}
			}catch (Exception e){}
			for(Client c: newClientList) {
				try{
					c.connected();
					Thread.sleep(1000/newClientList.size());
				} catch (Exception e) {
					try {
						parent.unregister(c);
					} catch (Exception f) {}
				}
			}
		}
	}
}
