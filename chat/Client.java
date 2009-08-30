package chat;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface Client extends Remote,Serializable {
	
	public Client register(String sip, String password) throws Exception;

	public void unregister() throws Exception;
	
	public String getAll() throws Exception;

	public void newmessage() throws Exception;
	
	public boolean connected() throws Exception;
	
	public void disconnect() throws Exception;
	
	public String getName() throws Exception;
	
	public String getStatus() throws Exception;
	
	public void participant(String action, Client participant) throws Exception;
	
	public List<Client> getServerClientList() throws Exception;
	
	public void updateStatus(boolean onlineFlag) throws Exception;
	
	public boolean compatibilityCheck(String sip, String password, String version) throws Exception;
}
