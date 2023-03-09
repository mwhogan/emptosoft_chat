package chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.AccessControlException;
import java.security.Permission;
import java.util.List;

import javax.swing.JOptionPane;



public class ChatGUI  extends JFrame implements Serializable, Chat {
	private static final long serialVersionUID = -4734351929459053907L;
	private boolean role;				//0 = Server, 1 = Client
	private String serverip;			//IP of server. IP of self if server.
	private RolePanel rolePanel;
	private StatusPanel statusPanel;
	private ParticipantsPanel participantsPanel;
	private LogPanel logPanel;
	protected MessagePanel messagePanel;
	private Client client;
	private Server server;
	private String password;
	public String name;
	public String status = "Online";
	public static String version = "1.1c";
	public static String interfaceVersion = "1.1";
	private boolean diagnosticMode = false;
	protected static ChatGUI gui = null;
	protected static ChatConsole nogui = null;

	public static void main(String[] args){
		try{
			if(args[0].equals("-c")){
				System.out.println("Emptosoft Chat v" + version);
				nogui = new ChatConsole(interfaceVersion);
				System.exit(0);
			} else {
				gui = new ChatGUI();
				gui.setVisible(true);
			}
		} catch (ArrayIndexOutOfBoundsException e){
			gui = new ChatGUI();
			gui.setVisible(true);
		}
	}
	
	
	
	public ChatGUI() {
		super();
		setSize(640, 480);
		setMinimumSize(new Dimension(400,400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent ev) {
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
		        
		    public void windowActivated(WindowEvent ev) {
	            messagePanel.field.requestFocus();
	        }
		    
		    public void windowOpened(WindowEvent ev) {
	            messagePanel.field.requestFocus();
	        }
		});
		
		//Get Display Name
		Boolean dnloop = true;
		while(dnloop){
			name = JOptionPane.showInputDialog(this,Strings.INPUT_NAME,"Emptosoft Chat v" + version,JOptionPane.QUESTION_MESSAGE);
			try{
				if(name.equals("Diagnostic Mode")){
					diagnosticMode = true;
					JOptionPane.showMessageDialog(this,Strings.MESSAGE_DIAGNOSTIC_MODE_ON,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
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
		this.setTitle("Emptosoft Chat v" + version + ": " + name);
		
		//Create feedback popup
		PopupThread popupThread = new PopupThread(null);
		popupThread.start();
		Popup popup = popupThread.getPopup();
		//popup.setVisible(true); ///This hangs
		
		popup.setMessage(Strings.MESSAGE_SETTING_SECURITY);
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
			popup.setMessage(Strings.MESSAGE_STARTING_RMI);
			//Start RMI Registry...
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			JOptionPane.showMessageDialog(this,Strings.MESSAGE_STARTED_RMI,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
			
			popup.setMessage(Strings.MESSAGE_STARTING_SERVER);
			// Create an instance of chat server ...
			server = new ServerImpl(this, interfaceVersion);
			Server stubs = (Server) UnicastRemoteObject.exportObject(server, 0);
			
			//Ask for a server access password?
			password = "Chat Server";
			
			// ... and bind it with the RMI Registry
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			registry.rebind(password, stubs);
			newMessage("Emptosoft Chat: " + Strings.MESSAGE_SERVER_RUNNING);
			
			popup.setMessage(Strings.MESSAGE_STARTING_CLIENT);
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
			JOptionPane.showMessageDialog(this,Strings.MESSAGE_FAILED_TO_START,"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(this,temp.getName(),"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this,Strings.MESSAGE_FAILED_TO_START,"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(this,getStackTrace(e),"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		//Now create GUI
		popup.setMessage(Strings.MESSAGE_STARTING_GUI);
		add(createOptionsPanel(), BorderLayout.WEST);
		add(createLogPanel(), BorderLayout.CENTER);
		add(createMessagePanel(), BorderLayout.SOUTH);
		//Now register client
		popup.setMessage(Strings.MESSAGE_REGISTERING_CLIENT);
		@SuppressWarnings("unused") //It may be useful to have a remote reference to the client in the future
		Client self = null;
		try{
			self = client.register(java.net.InetAddress.getLocalHost().getHostAddress(), "Chat Server");
		} catch (Exception e){
			JOptionPane.showMessageDialog(this,Strings.MESSAGE_FAILED_TO_START,"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(this,getStackTrace(e),"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		refresh();
		popup.dispose();
		messagePanel.field.selectAll();
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
	
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);

	}

	public boolean setRole(boolean newRole, String newServerip){
		//PopupThread popupThread = new PopupThread(this);
		//popupThread.start();
		//Popup popup = popupThread.getPopup();
		//popup.setMessage(Strings.MESSAGE_CLIENT_CHECKING_COMPATIBILITY);
		diagnosticMode(Strings.MESSAGE_CLIENT_CHECKING_COMPATIBILITY + "\n");
		//popup.repaint(10);
		//Checking compatibility
		try{
			if(client.compatibilityCheck(newServerip, password, interfaceVersion)){
				//compatible
			} else {
				//incompatible - abort
				JOptionPane.showMessageDialog(this,Strings.MESSAGE_INCOMPATIBLE,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
				diagnosticMode(Strings.MESSAGE_INCOMPATIBLE + "\n");
				//popup.dispose();
				return false;
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(this,Strings.MESSAGE_UNKNOWN_COMPATIBILITY,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
			diagnosticMode(Strings.MESSAGE_UNKNOWN_COMPATIBILITY + "\n");
			//popup.dispose();
		}
		//Switching role
		if(newRole == true){				//if going into server mode, serverip will be null
			//popup.setMessage(Strings.MESSAGE_TURNING_SERVER_OFF);
			diagnosticMode(Strings.MESSAGE_TURNING_SERVER_OFF + "\n");
			try {
				server.off();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,Strings.MESSAGE_SERVER_OFF_FAILED,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
				diagnosticMode(Strings.MESSAGE_SERVER_OFF_FAILED + "\n");
				//popup.dispose();
				return false;
			}
			//popup.setMessage(Strings.MESSAGE_TURNING_CLIENT_ON);
			diagnosticMode(Strings.MESSAGE_TURNING_CLIENT_ON + "\n");
			try{
				client.register(newServerip, password);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,Strings.MESSAGE_CLIENT_ON_FAILED,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
				diagnosticMode(Strings.MESSAGE_CLIENT_ON_FAILED + "\n");
				diagnosticMode(Strings.MESSAGE_TURNING_SERVER_ON + "\n");
				//popup.setMessage(Strings.MESSAGE_TURNING_SERVER_ON);
				try{
					server.on();
					java.net.InetAddress i = java.net.InetAddress.getLocalHost();
					String sip = i.getHostAddress();
					client.register(sip, "Chat Server");
				} catch (Exception f) {
					JOptionPane.showMessageDialog(this,Strings.MESSAGE_CLIENT_ON_FAILED_SERVER_ON_FAILED,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
					diagnosticMode(Strings.MESSAGE_CLIENT_ON_FAILED_SERVER_ON_FAILED + "\n");
					//popup.dispose();
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
			//popup.dispose();
			return true;
		} else {
			//popup.setMessage(Strings.MESSAGE_TURNING_CLIENT_OFF);
			diagnosticMode(Strings.MESSAGE_TURNING_CLIENT_OFF + "\n");
			try{
				client.unregister();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,Strings.MESSAGE_CLIENT_OFF_FAILED,"Emptosoft Chat v" + version,JOptionPane.INFORMATION_MESSAGE);
				diagnosticMode(Strings.MESSAGE_CLIENT_OFF_FAILED + "\n");
				//popup.dispose();
				return false;
			}
			//popup.setMessage(Strings.MESSAGE_TURNING_SERVER_ON);
			diagnosticMode(Strings.MESSAGE_TURNING_SERVER_ON + "\n");
			try {
				server.on();
				java.net.InetAddress i = java.net.InetAddress.getLocalHost();
				String sip = i.getHostAddress();
				client.register(sip, "Chat Server");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,Strings.MESSAGE_SERVER_ON_FAILED,"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
				diagnosticMode(Strings.MESSAGE_SERVER_ON_FAILED + "\n");
				//popup.dispose();
				System.exit(1);
			}
			role = newRole;
			serverip = newServerip;
			clearParticipants();
			addParticipant(client);
			refresh();
			rolePanel.setIP(Strings.PANEL_ROLE_CONNECTED + Strings.PANEL_ROLE_SERVER);
			//popup.dispose();
			return true;
		}
	}

	public void newMessage(String message){
		if(logPanel != null){
			logPanel.addMessage(message);
		}
	}
		

	private JComponent createOptionsPanel() {
		JPanel result = new JPanel(new BorderLayout());
		result.setMaximumSize(new Dimension(200,100000));
		result.setMinimumSize(new Dimension(200,300));
		result.setPreferredSize(new Dimension(200,480));
		result.add(createRolePanel(),BorderLayout.NORTH);		//Client or server
		result.add(createParticipantsPanel(),BorderLayout.CENTER);	//People in the current conversation
		result.add(createStatusPanel(),BorderLayout.SOUTH);	//Status (e.g. online, away)
		return result;
	}

	private JComponent createRolePanel() {
		rolePanel = new RolePanel(){
			private static final long serialVersionUID = 1L;
			protected boolean setRoleLinky(boolean newRole) {
				String ip = null;
				if(newRole == true){
					ip = JOptionPane.showInputDialog(this,Strings.INPUT_SERVERIP,"Emptosoft Chat v" + version,JOptionPane.QUESTION_MESSAGE);
					password = "Chat Server";
					if(ip == null){
						return false;
					}
				}
				return setRole(newRole, ip);
			}
		};
		addBorder(rolePanel,Strings.PANEL_ROLE);
		return rolePanel; 
	}

	private JComponent createParticipantsPanel() {
		participantsPanel = new ParticipantsPanel();
		//JComponent result = new JPanel();
		addBorder(participantsPanel,Strings.PANEL_PARTICIPANTS);
		return participantsPanel;
	}

	private JComponent createStatusPanel() {
		statusPanel = new StatusPanel(){
			private static final long serialVersionUID = 1L;
			protected boolean setStatusLinky(String newStatus, String oldStatus) {
				String temp = status;
				status = newStatus;
				boolean onlineFlag = false;			//True when coming from appear offline to another status and online message needs to be sent
				if(oldStatus.equals("Offline")){
					onlineFlag = true;
				}
				try{
					client.updateStatus(onlineFlag);
					if(newStatus.equals("Offline")){
						messagePanel.disable();
					}
					if(onlineFlag){
						messagePanel.enable();
					}
					return true;
				} catch (Exception e){
					status = temp;
					try{
						client.updateStatus(onlineFlag);
					} catch(Exception f){}
					if(newStatus.equals("Offline")){
						messagePanel.enable();
					}
					if(onlineFlag){
						messagePanel.disable();
					}
					JOptionPane.showMessageDialog(this,Strings.MESSAGE_STATUS_UPDATE_FAILED,"Emptosoft Chat v" + version,JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		};
		addBorder(statusPanel,Strings.PANEL_STATUS);
		return statusPanel;
	}
	
	private JComponent createLogPanel() {
		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());
		addBorder(holder,Strings.PANEL_LOG);
		logPanel = new LogPanel();
		holder.add(logPanel, BorderLayout.CENTER);
		return new JScrollPane(holder);
	}

	private JComponent createMessagePanel() {
		JPanel holder = new JPanel();
		addBorder(holder,Strings.PANEL_MESSAGE);
		messagePanel = new MessagePanel(this,name);
		holder.add(messagePanel);
		return new JScrollPane(holder);
	}
	
	public boolean refresh() {
		//Refresh logPanel and participantsPanel
		//Can decrease load by checking for changes - message count? - ABANDONED, do in v2.0?
		diagnosticMode(Strings.DIAGNOSTIC_REFRESH);
		participantsPanel.refresh();
		logPanel.clear();
		try {
			String messages = client.getAll();
			logPanel.addMessage(messages);
		} catch (Exception e) {
			logPanel.addMessage(Strings.MESSAGE_REFRESH_FAILURE);
			diagnosticMode(Strings.MESSAGE_REFRESH_FAILURE + "\n");
			return false;
		}
		diagnosticMode(Strings.DIAGNOSTIC_REFRESH_SUCCESS);
		return true;
	}
	
	public void updateParticipantStatus(Client updatedClient){
		try{
			participantsPanel.updateStatus(updatedClient);
			participantsPanel.refresh();
		} catch (Exception e){}
	}
	
	public void addParticipant(Client newClient){
		try{
			participantsPanel.add(newClient);
			participantsPanel.refresh();
		} catch (Exception e){}
	}
	
	public void removeParticipant(Client oldClient){
		participantsPanel.remove(oldClient);
		participantsPanel.refresh();
	}
	
	public void clearParticipants(){
		participantsPanel.clear();
		participantsPanel.refresh();
	}
	
	public void populateParticipants(){
		List<Client> clientList = null;
		diagnosticMode(Strings.DIAGNOSTIC_POPULATING_PARTICIPANTS);
		try{
			clientList = client.getServerClientList();
		} catch (Exception e) {
			diagnosticMode(Strings.DIAGNOSTIC_POPULATING_PARTICIPANTS_FAIL);
			try{
				client.disconnect();
			} catch (Exception f){}
		}
		int temp = 1;
		for (Client c: clientList){
			diagnosticMode(Strings.DIAGNOSTIC_POPULATING_PARTICIPANTS_NUMBER + temp++ + "/" + clientList.size() + ".\n");
			addParticipant(c);
		}
	}
	
	public void diagnosticMode(String message){
		if(diagnosticMode){
			newMessage(Strings.DIAGNOSTIC_MODE + message);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatus(){
		return status;
	}
}

