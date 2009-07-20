package chat;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.io.Serializable;

public class LogPanel extends JPanel implements Serializable {

	private static final long serialVersionUID = -3782824800343079708L;
	private TextArea area;

	public LogPanel(){
		super();
		setLayout(new BorderLayout());
		area = new TextArea("Loading...\n",1,1,TextArea.SCROLLBARS_VERTICAL_ONLY);
		area.setEditable(false);
		this.add(area,BorderLayout.CENTER);
	}

	protected void addMessage(String message) {
		area.append(message);
	}



	public void clear() {
		area.setText("");
	}
}
