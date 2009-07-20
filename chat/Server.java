package chat;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface Server extends Remote,Serializable {
	// Notifies all listening clients of new message
	public void send (String message, String nip) throws Exception;
	
	//Quietly adds a message to the log
	public void addToLog (String source, String message) throws Exception;

	// Returns latest message
	public String get () throws Exception;
	
	//Returns all messages
	public String getAll() throws Exception;

	// Register as a listening client
	public Client register (Client registree) throws Exception;

	// Unregister as a listening client
	public void unregister (Client registree) throws Exception;

	//Turn server on
	public void on () throws Exception;

	//Turn server off
	public void off () throws Exception;
	
	//Return list of clients
	public List<Client> getClients() throws Exception;
	
	public boolean connected() throws Exception;
	
	public void updateStatus(Client client, Boolean flag) throws Exception;
}
