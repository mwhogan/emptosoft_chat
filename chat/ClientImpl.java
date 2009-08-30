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
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_REGISTER);
		// Call registry for Chat service
		Registry registry = LocateRegistry.getRegistry(sip, 1099);
		server = (Server) registry.lookup(password);
		Client self = null;
		self = server.register(this);
		connectionThread = new ClientConnectionThread(this,server,parent);
		connectionThread.start();
		return self;
	}

	public void unregister() throws Exception {
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_UNREGISTER);
		server.unregister(this);
		connectionThread.die();
		wait(1000);
	}
	
	public String getAll() throws Exception {
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_GET_ALL_MESSAGES);
		return server.getAll();
	}

	public void newmessage() throws Exception {
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_NEW_MESSAGE);
		try{
			parent.newMessage(server.get());
			parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_NEW_MESSAGE_SUCCESS);
		} catch (Exception e){
			parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_NEW_MESSAGE_FAIL);
		}
	}
	
	public boolean connected() throws Exception{
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CONNECTION_PING);
		return true;
	}
	
	public void disconnect(){
		parent.newMessage("Emptosoft Chat: " + Strings.MESSAGE_SERVER_DISCONNECTED);
		parent.diagnosticMode(Strings.DIAGNOSTIC_CONNECTION_TO_SERVER_FAILED);
		connectionThread.die();
		try{
			wait(1000);
		} catch (Exception e){}
	}
	
	public String getName(){
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_NAME_REQUEST);
		return parent.name;
	}
	
	public String getStatus(){
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_STATUS_QUERIED);
		return parent.status;
	}

	public void participant(String action, Client participant){
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_PARTICIPANT_UPDATE_1 + action + Strings.DIAGNOSTIC_CLIENT_PARTICIPANT_UPDATE_2);
		if(action.equals("add")){
			parent.addParticipant(participant);
		} else if (action.equals("remove")){
			parent.removeParticipant(participant);
		} else {
			parent.updateParticipantStatus(participant);
		}
	}
	
	public List<Client> getServerClientList() throws Exception {
		parent.diagnosticMode(Strings.DIAGNOSTIC_CLIENT_CLIENT_LIST);
		return server.getClients();
	}
	
	public void updateStatus(boolean onlineFlag) throws Exception {
		server.updateStatus(this, onlineFlag);
	}
	
	public boolean compatibilityCheck(String sip, String password, String interfaceVersion) throws Exception {
		Registry registry = LocateRegistry.getRegistry(sip, 1099);
		server = (Server) registry.lookup(password);
		if(interfaceVersion.equals(server.getInterfaceVersion())){
			return true;
		} else{
			return false;
		}
	}
}
