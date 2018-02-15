package co.dictionary.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import co.dictionary.exception.ServerException;
import co.dictionary.util.JsonUtils;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class ClientServerInteraction {
	
	private ConnectServerArgsDTO connectArgsDTO;
	private JsonUtils jsonUtils = new JsonUtils();

	public boolean ping(ConnectServerArgsDTO connectArgsDTO) {
		boolean isConected  = false;
		this.connectArgsDTO = connectArgsDTO;

		Socket socket = null;
		
		DataOutputStream dout = null;
		DataInputStream  din  = null;

		try {
			socket = new Socket(connectArgsDTO.getHostName(), connectArgsDTO.getPort());

			din   = new DataInputStream(socket.getInputStream());
			dout  = new DataOutputStream(socket.getOutputStream());

			
			RequestModel requestModel = new RequestModel();
			requestModel.setOperationType("PING");
			
			dout.writeUTF(jsonUtils.convertToJSON(requestModel));
			System.out.println("---- Client request: "+jsonUtils.convertToJSON(requestModel));
			
			 dout.flush();
			 
			String serverMessage = din.readUTF();
			System.out.println("---- Server response: "+serverMessage);
			
			ResponseModel responseModel = (ResponseModel) jsonUtils.convertJsonIntoClass(
																		serverMessage, ResponseModel.class);
			
			if (responseModel.isConnected()) {
				isConected = true;
			}
			
			dout.close();  
			socket.close(); 
			
		} catch (IOException e) {
			
			if(dout != null){
				try {
					dout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			ServerException connectionException = new ServerException();
			connectionException.setErrorCode(1000);
			connectionException.setErrorMessage("Unable to connect HOST");
			System.err.println("Unable to connect HOST KIndly verify the IP & HOST properly");

		//	e.printStackTrace();
		}
		
		

		return isConected;
	}

	public ResponseModel read(RequestModel requestModel) throws ClassNotFoundException {

		Socket socket = null;
		ResponseModel responseModel = null;
		DataOutputStream dout  = null;
		
		try {
			socket = new Socket(connectArgsDTO.getHostName(), connectArgsDTO.getPort());
			DataInputStream din = new DataInputStream(socket.getInputStream());

			dout = new DataOutputStream(socket.getOutputStream());
			
			dout.writeUTF(jsonUtils.convertToJSON(requestModel));
			System.out.println("---- Client request: "+jsonUtils.convertToJSON(requestModel));
			dout.flush();
			
			String responseString = din.readUTF();
			
			responseModel  = (ResponseModel) jsonUtils.convertJsonIntoClass(responseString, ResponseModel.class);
			
			System.out.println("---- Server response: "+responseString);
			
			dout.close();  
			socket.close(); 
			
		} catch (IOException e) {

			if(dout != null){
				try {
					dout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			ServerException connectionException = new ServerException();
			connectionException.setErrorCode(1001);
			connectionException.setErrorMessage("Error searching meaning from dictionary");

			responseModel = new ResponseModel();
			
			e.printStackTrace();
		}
		
		return responseModel;
	}

	public ResponseModel add(RequestModel requestModel) {
		
		Socket socket = null;
		ResponseModel responseModel = null;
		DataOutputStream dout  = null;
		
		try {
			socket = new Socket(connectArgsDTO.getHostName(), connectArgsDTO.getPort());
			
			DataInputStream din = new DataInputStream(socket.getInputStream());

			dout = new DataOutputStream(socket.getOutputStream());
			
			dout.writeUTF(jsonUtils.convertToJSON(requestModel));
			System.out.println("---- Client request: "+jsonUtils.convertToJSON(requestModel));
			dout.flush();
			
			String responseString = din.readUTF();
			System.out.println("---- Server response: "+responseString);

			
			responseModel = (ResponseModel) jsonUtils.convertJsonIntoClass(responseString, ResponseModel.class);
			
		}catch(IOException e) {
			ServerException connectionException = new ServerException();
			connectionException.setErrorCode(1002);
			connectionException.setErrorMessage("Unable to Add Word in dictionary");

			responseModel = new ResponseModel();
			
			e.printStackTrace();
		}
		
		return responseModel;
	}

	public ResponseModel delete(RequestModel requestModel) {
		
		Socket socket = null;
		ResponseModel responseModel = null;
		DataOutputStream dout  = null;
		
		try {
			socket = new Socket(connectArgsDTO.getHostName(), connectArgsDTO.getPort());
			
			DataInputStream din = new DataInputStream(socket.getInputStream());

			dout = new DataOutputStream(socket.getOutputStream());
			
			dout.writeUTF(jsonUtils.convertToJSON(requestModel));
			
			System.out.println("---- Client request: "+jsonUtils.convertToJSON(requestModel));
			dout.flush();
			
			String responseString = din.readUTF();
			System.out.println("---- Server response: "+responseString);
			
			responseModel  = (ResponseModel) jsonUtils.convertJsonIntoClass(responseString, ResponseModel.class);
			
		}catch(IOException e) {
			ServerException connectionException = new ServerException();
			connectionException.setErrorCode(1002);
			connectionException.setErrorMessage("Unable to Add Word in dictionary");

			responseModel = new ResponseModel();
			
			e.printStackTrace();
		}
		
		return responseModel;
	}

	public ConnectServerArgsDTO getConnectArgsDTO() {
		return connectArgsDTO;
	}

	public void setConnectArgsDTO(ConnectServerArgsDTO connectArgsDTO) {
		this.connectArgsDTO = connectArgsDTO;
	}


}
