package chat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.AccessControlException;
import java.security.Permission;
import java.util.List;
import java.util.Scanner;

public class ChatConsole implements Chat {
	private static final long serialVersionUID = -4734351929459053907L;
	private boolean role;				//0 = Server, 1 = Client
	private String serverip;			//IP of server. IP of self if server.
	private RolePanel rolePanel;
	protected MessagePanel messagePanel;
	private Client client;
	private Server server;
	private String password;
	public String name;
	public String status = "Online";
	private boolean diagnosticMode = false;
	protected String interfaceVersion = null;

	public ChatConsole(String newInterfaceVersion) {
		interfaceVersion = newInterfaceVersion;
		//Get Display Name
		Scanner in = new Scanner(System.in);
		Boolean dnloop = true;
		while(dnloop){
			System.out.println(Strings.INPUT_NAME);
			name = in.nextLine();
			try{
				if(name.equals("Diagnostic Mode")){
					diagnosticMode = true;
					System.out.println(Strings.MESSAGE_DIAGNOSTIC_MODE_ON);
					name = null;
				} else {
					dnloop = false;
				}
			} catch(Exception e){
				dnloop = false;
			}
		}
		if(name == null || name.equals("")){
			//Use IP as name
			try {
				java.net.InetAddress i = java.net.InetAddress.getLocalHost();
				name = i.getHostAddress();
			} catch (Exception e){
				name = "Anonymous";
			}
		}
		
		System.out.println(Strings.MESSAGE_SETTING_SECURITY);
		//Set security policy
		URL url = this.getClass().getResource("Chat.policy");
		System.setProperty("java.security.policy",url.toString());
		
		//Set up server:
		// Assign a security manager, in the event that dynamic
		// classes are loaded
		if (System.getSecurityManager() == null){
			System.setSecurityManager (new SecurityManager());
		}
		try{
			System.out.println(Strings.MESSAGE_STARTING_RMI);
			//Start RMI Registry...
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println(Strings.MESSAGE_STARTED_RMI);
			
			System.out.println(Strings.MESSAGE_STARTING_SERVER);
			// Create an instance of chat server ...
			server = new ServerImpl(this, interfaceVersion);
			Server stubs = (Server) UnicastRemoteObject.exportObject(server, 0);
			
			//Ask for a server access password?
			password = "Chat Server";
			
			// ... and bind it with the RMI Registry
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			registry.rebind(password, stubs);
			newMessage("Emptosoft Chat: " + Strings.MESSAGE_SERVER_RUNNING);
			
			System.out.println(Strings.MESSAGE_STARTING_CLIENT);
			//Set up client:
			// Assign security manager
			if (System.getSecurityManager() == null){
				System.setSecurityManager(new SecurityManager());
			}
			client = new ClientImpl(this);
			Client stubc = (Client) UnicastRemoteObject.exportObject(client, 0);
			registry = LocateRegistry.getRegistry("localhost", 1099);
			registry.rebind("Chat Client", stubc);
			newMessage("Emptosoft Chat: " + Strings.MESSAGE_CLIENT_RUNNING);
		} catch (AccessControlException e){
			Permission temp = e.getPermission();
			System.out.println(Strings.MESSAGE_FAILED_TO_START);
			System.out.println(temp.getName());
			System.exit(1);
		} catch (Exception e){
			System.out.println(Strings.MESSAGE_FAILED_TO_START);
			System.out.println(getStackTrace(e));
			System.exit(1);
		}
		System.out.println(Strings.MESSAGE_REGISTERING_CLIENT);
		@SuppressWarnings("unused") //It may be useful to have a remote reference to the client in the future
		Client self = null;
		try{
			self = client.register(java.net.InetAddress.getLocalHost().getHostAddress(), "Chat Server");
		} catch (Exception e){
			System.out.println(Strings.MESSAGE_FAILED_TO_START);
			System.out.println(getStackTrace(e));
			System.exit(1);
		}
		refresh();
		String message = null;
		int run = 1;
		while(run == 1){
			message = in.nextLine();
			if(message.equals("Quit")){
				run = 0;
			} else {
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry(serverip, 1099);
					Server server = (Server) registry.lookup(password);
					server.send(message + "\n",name);
				} catch (Exception e) {
					System.out.println(Strings.MESSAGE_SEND_FAILED);
				}
			}
		}
		quit();
	}
		
	public void quit(){
	    try{
	    	if(role == true){
	    		List<Client> clientList;
	    		try {
	    			clientList = server.getClients();
	    		} catch (Exception e) {
	    			clientList = null;
	    		}
	    		for(Client c: clientList){
	    			try{
	    				c.disconnect();
	    			} catch (Exception e){}
	    		}
	    	} else {
	    		try{
	    			client.unregister();
	    		} catch (Exception e){}
	    	}
	    } catch (Exception e){}
	    System.exit(0);
    }
		
	
    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

	public boolean getRole(){
		return role;
	}
	
	public String getServerip(){
		return serverip;
	}
	
	protected String getPassword(){
		return password;
	}

	public boolean setRole(boolean newRole, String newServerip){
		diagnosticMode(Strings.MESSAGE_CLIENT_CHECKING_COMPATIBILITY + "\n");
		try{
			if(client.compatibilityCheck(newServerip, password, interfaceVersion)){
				//compatible
			} else {
				//incompatible - abort
				diagnosticMode(Strings.MESSAGE_INCOMPATIBLE + "\n");
				return false;
			}
		} catch (Exception e){
			diagnosticMode(Strings.MESSAGE_UNKNOWN_COMPATIBILITY + "\n");
		}
		//Switching role
		if(newRole == true){				//if going into server mode, serverip will be null
			diagnosticMode(Strings.MESSAGE_TURNING_SERVER_OFF + "\n");
			try {
				server.off();
			} catch (Exception e) {
				diagnosticMode(Strings.MESSAGE_SERVER_OFF_FAILED + "\n");
				return false;
			}
			diagnosticMode(Strings.MESSAGE_TURNING_CLIENT_ON + "\n");
			try{
				client.register(newServerip, password);
			} catch (Exception e) {
				diagnosticMode(Strings.MESSAGE_CLIENT_ON_FAILED + "\n");
				diagnosticMode(Strings.MESSAGE_TURNING_SERVER_ON + "\n");
				try{
					server.on();
					java.net.InetAddress i = java.net.InetAddress.getLocalHost();
					String sip = i.getHostAddress();
					client.register(sip, "Chat Server");
				} catch (Exception f) {
					diagnosticMode(Strings.MESSAGE_CLIENT_ON_FAILED_SERVER_ON_FAILED + "\n");
					System.exit(1);
				}
			}
			role = newRole;
			serverip = newServerip;
			clearParticipants();
			populateParticipants();
			refresh();
			rolePanel.setIP(serverip);
			diagnosticMode(Strings.DIAGNOSTIC_ROLE_SET);
			return true;
		} else {
			diagnosticMode(Strings.MESSAGE_TURNING_CLIENT_OFF + "\n");
			try{
				client.unregister();
			} catch (Exception e) {
				diagnosticMode(Strings.MESSAGE_CLIENT_OFF_FAILED + "\n");
				return false;
			}
			diagnosticMode(Strings.MESSAGE_TURNING_SERVER_ON + "\n");
			try {
				server.on();
				java.net.InetAddress i = java.net.InetAddress.getLocalHost();
				String sip = i.getHostAddress();
				client.register(sip, "Chat Server");
			} catch (Exception e) {
				diagnosticMode(Strings.MESSAGE_SERVER_ON_FAILED + "\n");
				System.exit(1);
			}
			role = newRole;
			serverip = newServerip;
			clearParticipants();
			addParticipant(client);
			refresh();
			rolePanel.setIP(Strings.PANEL_ROLE_CONNECTED + Strings.PANEL_ROLE_SERVER);
			return true;
		}
	}

	public void newMessage(String message){
		System.out.println(message);
	}

	public boolean refresh() {
		//Refresh logPanel and participantsPanel
		//Can decrease load by checking for changes - message count? - ABANDONED, do in v2.0?
		diagnosticMode(Strings.DIAGNOSTIC_REFRESH);
		System.out.println("-----");
		try {
			String messages = client.getAll();
			System.out.println(messages);
		} catch (Exception e) {
			diagnosticMode(Strings.MESSAGE_REFRESH_FAILURE + "\n");
			return false;
		}
		diagnosticMode(Strings.DIAGNOSTIC_REFRESH_SUCCESS);
		return true;
	}
	
	public void updateParticipantStatus(Client updatedClient){}
	
	public void addParticipant(Client newClient){}
	
	public void removeParticipant(Client oldClient){}
	
	public void clearParticipants(){}
	
	public void populateParticipants(){}
	
	public void diagnosticMode(String message){
		if(diagnosticMode){
			System.out.println(message);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatus(){
		return status;
	}
}
