package co.dictionary.client;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class RequestModel implements Serializable{
	
	private static final long serialVersionUID = 1732306531794707308L;
	
	private String operationType;
	private String word;
	private ArrayList<String> listMeaning;
	

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getListMeaning() {
		return listMeaning;
	}

	public void setListMeaning(ArrayList<String> listMeaning) {
		this.listMeaning = listMeaning;
	}

	@Override
	public String toString() {
		return "RequestModel [operationType=" + operationType + ", word=" + word + ", listMeaning=" + listMeaning + "]";
	}
	
	
	
	
}
