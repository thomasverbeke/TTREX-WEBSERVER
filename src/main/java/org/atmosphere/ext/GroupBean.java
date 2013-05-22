package org.atmosphere.ext;

public class GroupBean {
	private int runner_id;
	private String GroupName;
	private double StartPos;
	
	public GroupBean(int ID){
		this.runner_id = ID;
	}

	public int getID() {
		return runner_id;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public double getStartPos() {
		return StartPos;
	}

	public void setStartPos(double startPos) {
		StartPos = startPos;
	}
	
}
