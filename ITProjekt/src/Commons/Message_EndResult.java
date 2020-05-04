package Commons;

public class Message_EndResult extends Message{
	
	private int teamID;
	private int pointsTeamI;
	private int pointsTeamII;
	private int pointsTeamIII;
	private int pointsTeamIV;
	
	public Message_EndResult(int teamID, int pointsTeamI, int pointsTeamII, int pointsTeamIII, int pointsTeamIV) {
		super();
		this.teamID = teamID;
		this.pointsTeamI = pointsTeamI;
		this.pointsTeamII = pointsTeamII;
		this.pointsTeamIII = pointsTeamIII;
		this.pointsTeamIV = pointsTeamIV;
	}

	public int getTeamID() {
		return teamID;
	}

	public int getPointsTeamI() {
		return pointsTeamI;
	}

	public int getPointsTeamII() {
		return pointsTeamII;
	}

	public int getPointsTeamIII() {
		return pointsTeamIII;
	}

	public int getPointsTeamIV() {
		return pointsTeamIV;
	}
	
}
