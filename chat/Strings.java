package chat;

import java.io.Serializable;

public class Strings implements Serializable {
	private static final long serialVersionUID = -6623952311366289178L;
	public static final String PANEL_LOG = "Message log";
	public static final String PANEL_MESSAGE = "Send a new message";
	public static final String PANEL_TYPE = "Mode";
	public static final String PANEL_TYPE_CONNECTED = "Connected to: ";
	public static final String PANEL_TYPE_IP = "Your IP: ";
	public static final String PANEL_TYPE_SERVER = "(Self: Server mode)";
	public static final String PANEL_PARTICIPANTS = "Participants";
	public static final String PANEL_STATUS = "Status";
	public static final String BUTTON_TYPE_SERVER = "Server";
	public static final String BUTTON_TYPE_CLIENT = "Client";
	public static final String BUTTON_STATUS_ONLINE = "Online";
	public static final String BUTTON_STATUS_BUSY = "Busy";
	public static final String BUTTON_STATUS_AWAY = "Away";
	public static final String BUTTON_STATUS_OFFLINE = "Appear Offline";
	public static final String INPUT_NAME = "Type your name as you want others to see it:";
	public static final String INPUT_SERVERIP = "Enter the IP address of the server you would like to connect to:";
	public static final String INPUT_TRY_CONNECT_AGAIN = "Could not connect to the server. Would you like to try again?";
	public static final String INPUT_TRY_CONNECT_AGAIN_TITLE = "Connection error";
	public static final String MESSAGE_SERVER_OWNER1 = "You are connected to ";
	public static final String MESSAGE_SERVER_OWNER2 = "'s server.";
	public static final String MESSAGE_JOIN = " has joined the conversation...\n";
	public static final String MESSAGE_LEAVE = " has left the conversation...\n";
	public static final String MESSAGE_SERVER_DISCONNECTED = "The connection with the server has been lost.";
	public static final String MESSAGE_SERVER_RUNNING = "The server is up and running. :D\n";
	public static final String MESSAGE_CLIENT_RUNNING = "The client is now running too. :D\n";
	public static final String MESSAGE_SEND_FAILED = "Unable to send message.";
	public static final String MESSAGE_REFRESH_FAILURE = "Unable to refresh.";
	public static final String MESSAGE_REFRESH = "Refreshing...";
	public static final String MESSAGE_PLEASE_WAIT = "Please wait...";
	public static final String MESSAGE_SETTING_SECURITY = "Setting security policy...";
	public static final String MESSAGE_STARTING_RMI = "Starting RMI Registry...";
	public static final String MESSAGE_STARTING_SERVER = "Starting server...";
	public static final String MESSAGE_STARTING_CLIENT = "Starting client...";
	public static final String MESSAGE_STARTING_GUI = "Starting GUI...";
	public static final String MESSAGE_REGISTERING_CLIENT = "Registering client...";
	public static final String MESSAGE_TURNING_SERVER_OFF = "Turning server off...";
	public static final String MESSAGE_TURNING_CLIENT_ON = "Connecting...";
	public static final String MESSAGE_TURNING_SERVER_ON = "Turning server on...";
	public static final String MESSAGE_TURNING_CLIENT_OFF = "Disconnecting...";
	public static final String MESSAGE_FAILED_TO_START = "Emptosoft Chat failed to start.";
	public static final String MESSAGE_STARTED_RMI = "The RMI Registry has been started. Your firewall may shortly ask if you want to unblock it - please do unblock it, otherwise this software will not work.";
	public static final String MESSAGE_CLIENT_OFF_FAILED = "Failed to disengage from the server.";
	public static final String MESSAGE_CLIENT_ON_FAILED = "Failed to connect to the server."; //Note: Cannot connect to self.
	public static final String MESSAGE_SERVER_OFF_FAILED = "Failed to turn the server off.";
	public static final String MESSAGE_SERVER_ON_FAILED = "Fatal error: Failed to turn the server on.";
	public static final String MESSAGE_CLIENT_ON_FAILED_SERVER_ON_FAILED = "Fatal error: Now the server won't turn back on.";
	public static final String MESSAGE_STATUS_UPDATE_FAILED = "Failed to update status.";
}
