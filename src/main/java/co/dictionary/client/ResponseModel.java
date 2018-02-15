package co.dictionary.client;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class ResponseModel implements Serializable{

	private static final long serialVersionUID = 4162242904018795089L;
	
	private ArrayList<String> meaningList;
	private boolean status;
	private ArrayList<String> errorList; 
	private boolean isConnected;

	public ArrayList<String> getMeaningList() {
		return meaningList;
	}

	public void setMeaningList(ArrayList<String> meaningList) {
		this.meaningList = meaningList;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ArrayList<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<String> errorList) {
		this.errorList = errorList;
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	@Override
	public String toString() {
		return "ResponseModel [meaningList=" + meaningList + ", status=" + status + ", errorList=" + errorList
				+ ", isConnected=" + isConnected + "]";
	}

}
