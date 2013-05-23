package org.atmosphere.ext;

public class GroupBean {
	private int runner_id;
	private String GroupName;

	private double Longitude;
	private double Latitude; 
	
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


	public double getLatitude(){
		return Latitude;
	}
	
	public void setLatitude(double val){
		this.Latitude = val;
	}
	

	public void setLongitude(double val){
		this.Latitude = val;
	}
	
	
	public double getLongitude(){
		return Longitude;
	}

	
}
