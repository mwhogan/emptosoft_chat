package chat;

public class ClientConnectionThread extends Thread {
	
	private Client parent;
	private Server server;
	private boolean run;
	
	public ClientConnectionThread(Client newParent, Server newServer){
		super();
		parent = newParent;
		server = newServer;
	}
	
	public void run(){
		run = true;
		while(run == true){
			try{
				server.connected();
				Thread.sleep(1000);
			} catch (Exception e) {
				try {
					parent.disconnect();
					run = false;
				} catch (Exception f) {}
			}
		}
	}
}
