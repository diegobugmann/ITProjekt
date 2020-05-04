package Commons;

public class Message_EndResult extends Message{
	
	private int winningTeamID;
	private int pointsTeamI;
	private int pointsTeamII;
	private int pointsTeamIII;
	private int pointsTeamIV;
	
	/**
	 * @author digib
	 * @param winningTeamID, pointsTeamI, pointsTeamII
	 * used for Differenzler
	 */
	public Message_EndResult(int winningTeamID, int pointsTeamI, int pointsTeamII, int pointsTeamIII, int pointsTeamIV) {
		super();
		this.winningTeamID = winningTeamID;
		this.pointsTeamI = pointsTeamI;
		this.pointsTeamII = pointsTeamII;
		this.pointsTeamIII = pointsTeamIII;
		this.pointsTeamIV = pointsTeamIV;
	}
	
	/**
	 * @author digib
	 * @param winningTeamID, pointsTeamI, pointsTeamII
	 * used for Schieber
	 */
	public Message_EndResult(int winningTeamID, int pointsTeamI, int pointsTeamII) {
		super();
		this.winningTeamID = winningTeamID;
		this.pointsTeamI = pointsTeamI;
		this.pointsTeamII = pointsTeamII;
		this.pointsTeamIII = -1;
		this.pointsTeamIV = -1;
	}

	public int getWinningTeamID() {
		return winningTeamID;
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
