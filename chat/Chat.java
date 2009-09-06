package chat;

public interface Chat {

	public boolean setType(boolean newType, String newServerip);

	public void newMessage(String message);

	public boolean refresh();
	
	public void updateParticipantStatus(Client updatedClient);
	
	public void addParticipant(Client newClient);
	
	public void removeParticipant(Client oldClient);
	
	public void clearParticipants();
	
	public void populateParticipants();
	
	public void diagnosticMode(String message);
	
	public String getName();
	
	public String getStatus();
}
