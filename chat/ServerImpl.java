package chat;

import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements Server {
	private static final long serialVersionUID = 8439803111496557864L;

	private List<Client> clientList;
	private List<String> nameList;
	private List<String> messageList;
	private List<String> statusList;
	private boolean on;
	private ChatGUI parent;
	private ServerConnectionThread connectionThread;

	public ServerImpl (ChatGUI newParent) throws Exception {
		super();
		clientList = new ArrayList<Client>();
		nameList = new ArrayList<String>();
		messageList = new ArrayList<String>();
		statusList = new ArrayList<String>();
		parent = newParent;
		on = true;					//Software starts in server mode by default
		connectionThread = new ServerConnectionThread(this);
		connectionThread.start();
	}

	// Notifies all listening clients of new message
	public void send (String message, String sourceName) throws Exception {
		if(on == true){
			addToLog(message,sourceName);
			for(Client c: clientList) {
				c.newmessage();	//Now client holds server
			}
		}
	}
	
	public void addToLog(String message,String source) throws Exception{
		messageList.add(source + ": " + message);
	}


	public void off() throws Exception {
		on = false;
		connectionThread = null;
		clientList.clear();
		nameList.clear();
		messageList.clear();
		statusList.clear();
	}

	public void on() throws Exception {
		on = true;
		connectionThread = new ServerConnectionThread(this);
		connectionThread.start();
		messageList.add(Strings.MESSAGE_SERVER_RUNNING);
	}

	// Returns latest message - blank if server is off
	public String get () throws Exception {
		return messageList.get(messageList.size()-1);
	}
	
	//Returns all messages
	public String getAll() throws Exception {
		String output = Strings.MESSAGE_SERVER_OWNER1 + parent.name + Strings.MESSAGE_SERVER_OWNER2 + "\n";
		for(String s: messageList) {
			if(s != ""){
				output = output + s;
			}
		}
		return output;
	}

	// Register as a listening client
	public Client register (Client registree) throws Exception {
		clientList.add(registree);
		nameList.add(registree.getName());	//nameList is necessary because unregistrations could be caused by a connection failure
		statusList.add(registree.getStatus());
		for(Client c: clientList) {
			c.participant("add",registree);	//Now client holds server
		}
		send(registree.getName() + Strings.MESSAGE_JOIN, "Emptosoft Chat");
		return registree;
	}

	// Unregister as a listening client
	public void unregister (Client registree) throws Exception {
		int index = clientList.indexOf(registree);
		clientList.remove(registree);
		String name = nameList.remove(index);
		for(Client c: clientList) {
			c.participant("remove",registree);	//Now client holds server
		}
		if((statusList.get(index)).equals("Offline")){
			send(name + Strings.MESSAGE_LEAVE, "Emptosoft Chat");
		}
		statusList.remove(index);
	}
	
	public List<Client> getClients() throws Exception {
		return clientList;
	}
	
	public boolean connected() throws Exception {
		return true;
	}
	
	public void updateStatus(Client client, Boolean flag) throws Exception {
		if(on == true){
			for(Client c: clientList) {
				try {
					c.participant("update",client);
				} catch (Exception e) {
				}
			}
			if(client.getStatus().equals("Offline")){
				send(client.getName() + Strings.MESSAGE_LEAVE, "Emptosoft Chat");
			}
			if(flag){
				send(client.getName() + Strings.MESSAGE_JOIN, "Emptosoft Chat");
			}
		}
	}
	
}
