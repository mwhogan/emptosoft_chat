package chat;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public abstract class StatusPanel extends JPanel implements Serializable {

	private static final long serialVersionUID = 8946567436508260817L;
	private JRadioButton online;
	private JRadioButton busy;
	private JRadioButton away;
	private JRadioButton offline;
	private JRadioButton current;
	private String currentS;
	protected abstract boolean setStatusLinky(String newStatus, String oldStatus);

	public StatusPanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		online = new JRadioButton(Strings.BUTTON_STATUS_ONLINE, true);
		busy = new JRadioButton(Strings.BUTTON_STATUS_BUSY, true);
		away = new JRadioButton(Strings.BUTTON_STATUS_AWAY, true);
		offline = new JRadioButton(Strings.BUTTON_STATUS_OFFLINE, true);
		online.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setStatusLinky("Online",currentS)){
					current = online;
					currentS = "Online";
				}else{
					//unsuccessful: re-enable previous source choice
					current.setSelected(true); 
				}
			}
		});
  		busy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setStatusLinky("Busy",currentS)){
					current = busy;
					currentS = "Busy";
				}else{
					//unsuccessful: re-enable previous source choice
				current.setSelected(true); 
				}
			}
		});
  		away.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setStatusLinky("Away",currentS)){
					current = away;
					currentS = "Away";
				}else{
					//unsuccessful: re-enable previous source choice
				current.setSelected(true); 
				}
			}
		});
  		offline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setStatusLinky("Offline",currentS)){
					current = offline;
					currentS = "Offline";
				}else{
					//unsuccessful: re-enable previous source choice
				current.setSelected(true); 
				}
			}
		});
	//add RadioButtons to this JPanel
	add(online);
	add(busy);
	add(away);
	add(offline);
	//create a ButtonGroup containing all buttons
	//Only one Button in a ButtonGroup can be selected at once
	ButtonGroup group = new ButtonGroup();
	group.add(online);
	group.add(busy);
	group.add(away);
	group.add(offline);
	current = online;
	currentS = "Online";
	}
}
