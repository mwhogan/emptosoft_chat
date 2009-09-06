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
	private Chat parent;
	private ServerConnectionThread connectionThread;
	private String interfaceVersion;

	public ServerImpl (Chat newParent, String newInterfaceVersion) throws Exception {
		super();
		clientList = new ArrayList<Client>();
		nameList = new ArrayList<String>();
		messageList = new ArrayList<String>();
		statusList = new ArrayList<String>();
		parent = newParent;
		interfaceVersion = newInterfaceVersion;
		on = true;					//Software starts in server mode by default
		connectionThread = new ServerConnectionThread(this, parent);
		connectionThread.start();
	}

	// Notifies all listening clients of new message
	public void send (String message, String sourceName) throws Exception {
		if(on == true){
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_SEND + sourceName + ".\n");
			addToLog(message,sourceName);
			int temp = 1;
			for(Client c: clientList) {
				parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_SEND_NUMBER + temp++ + "/" + clientList.size() + ".\n");
				try{
					c.newmessage();	//Now client holds server
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_SEND_SUCCESS);
				} catch (Exception e) {
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_SEND_FAIL);
				}
			}
		}
	}
	
	public void addToLog(String message,String source) throws Exception{
		if(on == true){
			messageList.add(source + ": " + message);
		}
	}


	public void off() throws Exception {
		on = false;
		connectionThread.die();
		wait(1000);
		clientList.clear();
		nameList.clear();
		messageList.clear();
		statusList.clear();
		parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_OFF);
	}

	public void on() throws Exception {
		on = true;
		connectionThread = new ServerConnectionThread(this, parent);
		connectionThread.start();
		messageList.add(Strings.MESSAGE_SERVER_RUNNING);
		parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_ON);
	}

	// Returns latest message - blank if server is off
	public String get () throws Exception {
		if(on == true){
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_GET);
			return messageList.get(messageList.size()-1);
		} else {
			return null;
		}
	}
	
	//Returns all messages
	public String getAll() throws Exception {
		if(on == true){
			String output = Strings.MESSAGE_SERVER_OWNER1 + parent.getName() + Strings.MESSAGE_SERVER_OWNER2 + "\n";
			for(String s: messageList) {
				if(s != ""){
					output = output + s;
				}
			}
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_GETALL);
			return output;
		} else {
			return null;
		}
	}

	// Register as a listening client
	public Client register (Client registree) throws Exception {
		if(on == true){
			clientList.add(registree);
			nameList.add(registree.getName());	//nameList is necessary because unregistrations could be caused by a connection failure
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_REGISTER + registree.getName() + ".\n");
			statusList.add(registree.getStatus());
			int temp = 1;
			for(Client c: clientList) {
				parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_REGISTER_NUMBER + temp++ + "/" + clientList.size() + ".\n");
				try{
					c.participant("add",registree);	//Now client holds server
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_REGISTER_SUCCESS);
				} catch (Exception e){
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_REGISTER_FAIL);
				}
			}
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_REGISTER_MESSAGE);
			send(registree.getName() + Strings.MESSAGE_JOIN, "Emptosoft Chat");
			return registree;
		} else {
			return null;
		}
	}

	// Unregister as a listening client
	public void unregister (Client registree) throws Exception {
		if(on == true){
			int index = clientList.indexOf(registree);
			clientList.remove(registree);
			String name = nameList.remove(index);
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_UNREGISTER + name + ".\n");
			int temp = 1;
			for(Client c: clientList) {
				parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_UNREGISTER_NUMBER + temp++ + "/" + clientList.size() + ".\n");
				try {
					c.participant("remove",registree);	//Now client holds server
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_UNREGISTER_SUCCESS);
				} catch (Exception e){
					parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_UNREGISTER_FAIL);
				}
			}
			if((statusList.get(index)).equals("Offline")){
				//Quiet?
			} else {
				parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_UNREGISTER_MESSAGE);
				send(name + Strings.MESSAGE_LEAVE, "Emptosoft Chat");
			}
			statusList.remove(index);
		}
	}
	
	public List<Client> getClients() throws Exception {
		if(on == true){
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_GETCLIENTS);
			return clientList;
		} else {
			return null;
		}
	}
	
	public boolean connected() throws Exception {
		if(on == true){
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_CONNECTION_PING);
			return true;
		} else {
			return false;
		}
	}
	
	public void updateStatus(Client client, Boolean flag) throws Exception {
		if(on == true){
			parent.diagnosticMode(Strings.DIAGNOSTIC_SERVER_STATUS_UPDATE);
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
	
	public String getInterfaceVersion() throws Exception {
		return interfaceVersion;
	}
	
}
