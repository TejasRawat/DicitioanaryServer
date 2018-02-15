package co.dictionary.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import co.dictionary.client.RequestModel;
import co.dictionary.client.ResponseModel;
import co.dictionary.util.JsonUtils;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class WorkerRunnable implements Runnable{

    private Socket clientSocket;
    private HashMap<String, ArrayList<String>> dictionary; 
    private JsonUtils jsonUtils = new JsonUtils();
    private String filePath;
	
	public WorkerRunnable(Socket clientSocket, HashMap<String, ArrayList<String>> map, String filePath) {
		this.dictionary   = map;
		this.clientSocket = clientSocket; 
		this.filePath  = filePath;
	}	

	@Override
	public void run() {
		processClientRequest(clientSocket);
	}

	private void processClientRequest(Socket clientSocket) {

			DataInputStream ipStream;
			DataOutputStream opStream;

			try {
				ipStream = new DataInputStream(clientSocket.getInputStream());
				opStream = new DataOutputStream(clientSocket.getOutputStream());

				RequestModel requestModel = (RequestModel) jsonUtils.convertJsonIntoClass(ipStream.readUTF(),
																							RequestModel.class);

				ResponseModel responseModel = null;

				switch (requestModel.getOperationType()) {
				case "GET":
					
					
					responseModel = new ResponseModel();

					Iterator<Entry<String, ArrayList<String>>> itrMap1 = dictionary.entrySet().iterator();
					while (itrMap1.hasNext()) {
						Entry<String, ArrayList<String>> entry = itrMap1.next();
						if (entry.getKey().toUpperCase().equals(requestModel.getWord().toUpperCase())) {
							responseModel.setStatus(true);
							responseModel.setMeaningList(entry.getValue());
							break;
						}
					}

					if (!responseModel.isStatus()) {
						ArrayList<String> errorList = new ArrayList<String>();
						errorList.add("Word Not FOUND");
						responseModel.setErrorList(errorList);
					}

					opStream.writeUTF(jsonUtils.convertToJSON(responseModel));
					

					opStream.close();
					ipStream.close();

					break;

				case "POST":
					responseModel = new ResponseModel();
					
					
					if( (requestModel.getWord()!= null && !requestModel.getWord().isEmpty()) &&
							(requestModel.getListMeaning()!= null && !requestModel.getListMeaning().isEmpty())) {
						boolean result = updateDictionary(requestModel.getWord(), requestModel.getListMeaning());
						
						if(result){
							responseModel.setStatus(true);
						}else {
							ArrayList<String> errorList = new ArrayList<>();
							errorList.add("Word Already Present");
							responseModel.setErrorList(errorList);
						}
					}else {
						ArrayList<String> errorList = new ArrayList<>();
						errorList.add("Invalid Input");
						responseModel.setErrorList(errorList);
					}
					opStream.writeUTF(jsonUtils.convertToJSON(responseModel));

					
					opStream.close();
					ipStream.close();

					break;

				case "DELETE":
					
					responseModel = new ResponseModel();
					
					
					if(requestModel.getWord()!= null && !requestModel.getWord().isEmpty()) {
						
						boolean isDeleted = deleteEntryFromDictionary(requestModel.getWord());
						responseModel.setStatus(isDeleted);
					}else {
						responseModel.setStatus(false);
					}
					opStream.writeUTF(jsonUtils.convertToJSON(responseModel));

					
					opStream.close();
					ipStream.close();
					
					break;
					
				case "PING":
					
					
					responseModel = new ResponseModel();
					responseModel.setConnected(true);
					responseModel.setStatus(true);
					
					opStream.writeUTF(jsonUtils.convertToJSON(responseModel));

					ipStream.close();
					opStream.close();

				default:
					responseModel = new ResponseModel();
					
					
					ArrayList<String> errorList = new ArrayList<String>();
					errorList.add("Unable to retrieve response from server end");
					responseModel.setErrorList(errorList);

					opStream.writeUTF(jsonUtils.convertToJSON(responseModel));

					
					ipStream.close();
					opStream.close();

					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	private synchronized boolean deleteEntryFromDictionary(String word) {
		boolean isDeleted = false;
		
		Iterator<Entry<String, ArrayList<String>>> itrMap1 = dictionary.entrySet().iterator();
		while (itrMap1.hasNext()) {
			Entry<String, ArrayList<String>> entry = itrMap1.next();
			if (entry.getKey().toUpperCase().equals(word.toUpperCase())) {
				itrMap1.remove();
				isDeleted = true;
				break;
			}
		}
		
		try {
			udpateFile(dictionary,filePath);
		} catch (IOException e) {
			System.err.println("Unable to update file");
			e.printStackTrace();
		}
		
		return isDeleted;
	}
	
	public synchronized boolean updateDictionary(String word, ArrayList<String> meaning) {
		
		Iterator<Entry<String, ArrayList<String>>> itrMap1 = dictionary.entrySet().iterator();
		while (itrMap1.hasNext()) {
			Entry<String, ArrayList<String>> entry = itrMap1.next();
			if (entry.getKey().toUpperCase().equals(word.toUpperCase())) {
				return false;
			}
		}
		
		this.dictionary.put(word, meaning);
		
		try {
			udpateFile(dictionary,filePath);
		} catch (IOException e) {
			System.err.println("Unable to update file");
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	public synchronized boolean udpateFile(HashMap<String, ArrayList<String>> map, String filePath) throws IOException {
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
		
		StringBuilder builder = new StringBuilder();
		
		Iterator<Entry<String, ArrayList<String>>> itr = map.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<String, ArrayList<String>> entry = itr.next();
			
			String word = entry.getKey();
			
			builder.append(word);
			builder.append(":");
			
			ArrayList<String> meaningList = entry.getValue();
			
			for(String meaning : meaningList) {
				builder.append(meaning);
				builder.append(",");
			}
			builder.append("\n");
		}
		
		bufferedWriter.write(builder.toString());
		
		bufferedWriter.close();
		
		System.out.println("builder print start");
		System.out.println(builder);
		System.out.println("builder print end");

		
		
		return false;
	}


}
