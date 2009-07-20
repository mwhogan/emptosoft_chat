package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientImpl implements Client {
	
	static final long serialVersionUID = 5810748830071717465L;
	private ChatGUI parent;
	public Server server;
	private ClientConnectionThread connectionThread;

	public ClientImpl(ChatGUI newParent) throws RemoteException {
		super();
		parent = newParent;
	};



	public Client register(String sip, String password) throws Exception {
		// Call registry for Chat service
		Registry registry = LocateRegistry.getRegistry(sip, 1099);
		server = (Server) registry.lookup(password);
		Client self = null;
		self = server.register(this);
		connectionThread = new ClientConnectionThread(this,server);
		connectionThread.start();
		return self;
	}

	public void unregister() throws Exception {
		server.unregister(this);
	}
	
	public String getAll() throws Exception {
		return server.getAll();
	}

	public void newmessage() throws Exception {
		parent.newMessage(server.get());						//Eventually want to enclose this in try-catch block
	}
	
	public boolean connected() throws Exception{
		return true;
	}
	
	public void disconnect(){
		parent.newMessage("Emptosoft Chat: " + Strings.MESSAGE_SERVER_DISCONNECTED);
	}
	
	public String getName(){
		return parent.name;
	}
	
	public String getStatus(){
		return parent.status;
	}

	public void participant(String action, Client participant){
		if(action == "add"){
			parent.addParticipant(participant);
		} else if (action == "remove"){
			parent.removeParticipant(participant);
		} else {
			parent.updateParticipantStatus(participant);
		}
	}
	
	public List<Client> getServerClientList() throws Exception {
		return server.getClients();
	}
}
