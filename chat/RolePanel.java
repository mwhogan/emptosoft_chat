package chat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public abstract class RolePanel extends JPanel implements Serializable {

	private static final long serialVersionUID = 8946567436508260817L;
	private JRadioButton current;
	private JRadioButton server;
	private JRadioButton client;
	private JLabel ip;
	private String cip;
	protected abstract boolean setRoleLinky(boolean newRole);

	public RolePanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		server = new JRadioButton(Strings.BUTTON_ROLE_SERVER, true);
		client = new JRadioButton(Strings.BUTTON_ROLE_CLIENT, true);
		server.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setRoleLinky(false)){
					//successful: server started
					current = server;
				}else{
					//unsuccessful: re-enable previous source choice
					current.setSelected(true); 
				}
			}
		});
  		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setRoleLinky(true)){
					//successful: connected to a server
					current = client;
				}else{
					//unsuccessful: re-enable previous source choice
				current.setSelected(true); 
				}
			}
		});
	//add RadioButtons to this JPanel
	add(server);
	add(client);
	//create a ButtonGroup containing all buttons
	//Only one Button in a ButtonGroup can be selected at once
	ButtonGroup group = new ButtonGroup();
	group.add(server);
	group.add(client);
	current = server;
	add(Box.createVerticalStrut(10));
	
	//Get IP
	cip = "0.0.0.0";
	try {
		java.net.InetAddress i;
		i = java.net.InetAddress.getLocalHost();
		cip = i.getHostAddress();
	} catch (Exception e) {}
	
	ip = new JLabel("<html>" + Strings.PANEL_ROLE_CONNECTED + Strings.PANEL_ROLE_SERVER + "<p>" + Strings.PANEL_ROLE_IP + cip + "</html>");
	add(ip);
	}
	
	public void setIP(String newIP){
		ip.setText("<html>" + Strings.PANEL_ROLE_CONNECTED + newIP + "<p>" + Strings.PANEL_ROLE_IP + cip + "</html>");
	}
}
