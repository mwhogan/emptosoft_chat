package chat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Popup extends JDialog {
	private static final long serialVersionUID = 8482739423628841940L;
	private JLabel messageLabel;
		
	public Popup(ChatGUI parent, String title, String message){
		super(parent, title, true);
		setSize(200,75);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JPanel messagePane = new JPanel();
		getContentPane().add(messagePane);
		messageLabel = new JLabel(message);
	   	messagePane.add(messageLabel);
	   	setLocationRelativeTo(null);
	}
	
	public void setMessage(String message){
		messageLabel.setText(message);
	}

}
