package chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ParticipantsPanel extends JPanel {
	private static final long serialVersionUID = -6258136015118162245L;
	private JLabel list;
	private List<Client> clientList;
	private List<String> nameList;
	private List<String> statusList;
	
	public ParticipantsPanel(){
		list = new JLabel(Strings.MESSAGE_PLEASE_WAIT);
		clientList = new ArrayList<Client>();
		nameList = new ArrayList<String>();
		statusList = new ArrayList<String>();
		this.add(list);
		this.setBounds(0,121,120,120);
		//this.setSize(220,120);
		//this.setPreferredSize(new Dimension(220, 120));
		//this.setMinimumSize(new Dimension(220, 120));
		//this.setMaximumSize(new Dimension(220, 120));
	}
	
	public void refresh(){
		list.setText(Strings.MESSAGE_REFRESH);
		String output = "";
		int visible = 0;
		for(int temp = 0; temp < clientList.size(); temp++){
			if(statusList.get(temp).equals("Offline")){
				//Hide
			} else {
					output = output + "<p>" + nameList.get(temp) + " (" + statusList.get(temp) + ")";
				visible++;
			}
		}
		list.setText("<html>Total: " + visible + output + "</html>");
	}
	
	public void add(Client client) throws Exception {
		clientList.add(client);
		nameList.add(client.getName());
		statusList.add(client.getStatus());
	}
	
	public void remove(Client client){
		int index = clientList.indexOf(client);
		clientList.remove(index);
		nameList.remove(index);
		statusList.remove(index);
	}
	
	public void updateStatus(Client client) throws Exception {
		int index = clientList.indexOf(client);
		statusList.set(index, client.getStatus());
	}
	
	public void clear(){
		clientList.clear();
		nameList.clear();
		statusList.clear();
	}
}
