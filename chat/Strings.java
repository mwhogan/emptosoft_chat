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
	public static final String MESSAGE_SERVER_DISCONNECTED = "The connection with the server has been lost.\n";
	public static final String MESSAGE_SERVER_RUNNING = "The server is up and running. :D\n";
	public static final String MESSAGE_CLIENT_RUNNING = "The client is now running too. :D\n";
	public static final String MESSAGE_SEND_FAILED = "Emptosoft Chat: Unable to send message.\n";
	public static final String MESSAGE_REFRESH_FAILURE = "Unable to refresh.";
	public static final String MESSAGE_REFRESH = "Refreshing...";
	public static final String MESSAGE_PLEASE_WAIT = "Please wait...";
	public static final String MESSAGE_DIAGNOSTIC_MODE_ON = "Diagnostic mode has been activated.";
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
	public static final String MESSAGE_CLIENT_CHECKING_COMPATIBILITY = "Checking compatibility with server.";
	public static final String MESSAGE_INCOMPATIBLE = "This client is incompatible with the server - either this client, the server, or both, are not the latest versions.";
	public static final String MESSAGE_UNKNOWN_COMPATIBILITY = "Unable to check if this client is compatible with the server - the server may not be responding, or it may be very old or much newer than this client.";
	public static final String DIAGNOSTIC_MODE = "Diagnostic Mode: ";
	public static final String DIAGNOSTIC_CLIENT_CONNECTION_CHECK = "Checking connection to server.\n";
	public static final String DIAGNOSTIC_CLIENT_CONNECTION_SUCCESS = "Connection to server is OK.\n";
	public static final String DIAGNOSTIC_CLIENT_CONNECTION_FAIL = "Connection to server has failed. Trying to disconnect...\n";
	public static final String DIAGNOSTIC_CLIENT_DISCONNECTION_SUCCESS = "Disconnection successful.\n";
	public static final String DIAGNOSTIC_CLIENT_DISCONNECTION_FAIL = "Disconnection failed.\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_GET_CLIENT_LIST = "Getting client list...\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_GET_CLIENT_LIST_FAIL = "Failed to get client list.\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_CHECK = "Checking connection to clients...\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_CHECK_NUMBER = "Checking connection: client ";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_CHECK_SUCCESS = "Connection to client is OK.\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_CHECK_FAIL = "Connection to client has failed: unregistering client...\n";
	public static final String DIAGNOSTIC_CLIENT_CONNECTION_PING = "The server checked its connection to this client - appropriate response sent.\n";
	public static final String DIAGNOSTIC_SERVER_CONNECTION_PING = "A client checked its connection to this server - appropriate response sent.\n";
	public static final String DIAGNOSTIC_POPULATING_PARTICIPANTS = "Populating participants...\n";
	public static final String DIAGNOSTIC_POPULATING_PARTICIPANTS_FAIL = "Failed to populate participants. Server unavailable.\n";
	public static final String DIAGNOSTIC_POPULATING_PARTICIPANTS_NUMBER = "Populating: client ";
	public static final String DIAGNOSTIC_CONNECTION_TO_SERVER_FAILED = "Connection to server failed. Stopping connection check thread.\n";
	public static final String DIAGNOSTIC_TYPE_SET = "Mode change successful.\n";
	public static final String DIAGNOSTIC_REFRESH = "Refreshing participants and message log.\n";
	public static final String DIAGNOSTIC_REFRESH_SUCCESS = "Refreshes successful.\n";
	public static final String DIAGNOSTIC_CLIENT_NEW_MESSAGE = "This client has been made aware of a new message on the server - retrieving message...\n";
	public static final String DIAGNOSTIC_CLIENT_NEW_MESSAGE_SUCCESS = "New message retrieved and added to log.\n";
	public static final String DIAGNOSTIC_CLIENT_NEW_MESSAGE_FAIL = "Unable to retrieve new message and add it to the log.\n";
	public static final String DIAGNOSTIC_SERVER_SEND = "Recieved new message from a client. Adding message to log and notifying other clients. Message source: ";
	public static final String DIAGNOSTIC_SERVER_SEND_NUMBER = "Notifying: client ";
	public static final String DIAGNOSTIC_SERVER_SEND_SUCCESS = "Successfully notified client.\n";
	public static final String DIAGNOSTIC_SERVER_SEND_FAIL = "Failed to notify client.\n";
	public static final String DIAGNOSTIC_SERVER_OFF = "Server (mode) is off.\n";
	public static final String DIAGNOSTIC_SERVER_ON = "Server (mode) is on.\n";
	public static final String DIAGNOSTIC_SERVER_GET = "A client requested the latest message. Sending message.\n";
	public static final String DIAGNOSTIC_SERVER_GETALL = "A client requested all messages. Sending messages.\n";
	public static final String DIAGNOSTIC_SERVER_REGISTER = "A client has requested registration with the server. Registering and notifying other clients. Name of new client: ";
	public static final String DIAGNOSTIC_SERVER_REGISTER_NUMBER = "Notifying: client ";
	public static final String DIAGNOSTIC_SERVER_REGISTER_SUCCESS = "Successfully notified client.\n";
	public static final String DIAGNOSTIC_SERVER_REGISTER_FAIL = "Failed to notify client.\n";
	public static final String DIAGNOSTIC_SERVER_REGISTER_MESSAGE = "Sending message to clients about new registration.\n";
	public static final String DIAGNOSTIC_SERVER_UNREGISTER = "Unregistering a client: ";
	public static final String DIAGNOSTIC_SERVER_UNREGISTER_NUMBER = "Notifying: client ";
	public static final String DIAGNOSTIC_SERVER_UNREGISTER_SUCCESS = "Successfully notified client.\n";
	public static final String DIAGNOSTIC_SERVER_UNREGISTER_FAIL = "Failed to notify client.\n";
	public static final String DIAGNOSTIC_SERVER_UNREGISTER_MESSAGE = "Sending message to clients about unregistration.\n";
	public static final String DIAGNOSTIC_SERVER_GETCLIENTS = "A list of this server's clients has been requested. Sending list.\n";
	public static final String DIAGNOSTIC_CLIENT_STATUS_QUERIED = "The status of this client has been requested - status sent.\n";
	public static final String DIAGNOSTIC_CLIENT_PARTICIPANT_UPDATE_1 = "The server has made this client aware of the fact that it needs to ";
	public static final String DIAGNOSTIC_CLIENT_PARTICIPANT_UPDATE_2 = " a client to/in/from the participants list, and will now do so.\n";
	public static final String DIAGNOSTIC_CLIENT_CLIENT_LIST = "Getting a list of participants from the server.\n";
	public static final String DIAGNOSTIC_CLIENT_NAME_REQUEST = "The name of this client has been requested - name sent.\n";
	public static final String DIAGNOSTIC_CLIENT_UNREGISTER = "Unregistering from server and ceasing checks of the connection to the server.\n";
	public static final String DIAGNOSTIC_CLIENT_REGISTER = "Registering with server.\n";
	public static final String DIAGNOSTIC_CLIENT_GET_ALL_MESSAGES = "Requesting all messages on server.\n";
	public static final String DIAGNOSTIC_SERVER_STATUS_UPDATE = "A client has updated its status - notifying clients.\n";
}
