package fight_land.lobby;

public class PlayerLobby {

	private String name;
	private int graphicsNumber;
	private int teamNumber;

	public PlayerLobby(String name, int graphicsNumber) {
		this.setName(name);
		this.setGraphicsNumber(graphicsNumber);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGraphicsNumber() {
		return this.graphicsNumber;
	}

	public void setGraphicsNumber(int graphicsNumber) {
		this.graphicsNumber = graphicsNumber;
	}

	public int getTeamNumber() {
		return this.teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

}
