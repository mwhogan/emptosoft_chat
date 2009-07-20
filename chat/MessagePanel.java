package chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MessagePanel extends JPanel implements Serializable,ActionListener {
	
	private static final long serialVersionUID = -8345474477218136080L;
	
	protected JTextField field;
	private ChatGUI parent;
	private String name;

	public MessagePanel(ChatGUI newParent, String newName){
		super();
		setLayout(new BorderLayout());
		parent = newParent;
		name = newName;
		field = new JTextField("Type your messages here, and press ENTER to send them.",40);
		field.addActionListener(this);
		this.add(field,BorderLayout.CENTER);
	}

	public void disable(){
		field.setText("");
		field.setEditable(false);
	}
	
	public void enable(){
		field.setEditable(true);
	}

	private void send() throws Exception {
		// Call registry for Chat service
		Registry registry = LocateRegistry.getRegistry(parent.getServerip(), 1099);
		Server server = (Server) registry.lookup(parent.getPassword());

		//Send message
		server.send(field.getText() + "\n",name);
	}

	public void actionPerformed(ActionEvent e) {
		try{
			send();
			//Clear text area
			field.setText("");
		} catch(Exception f){
			parent.newMessage(Strings.MESSAGE_SEND_FAILED);
		}
	}
}
