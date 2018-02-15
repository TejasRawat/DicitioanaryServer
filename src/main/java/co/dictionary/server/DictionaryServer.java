package co.dictionary.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class DictionaryServer {
	public static void main(String args[]) throws IOException {
		
		
		int serverPort = 9000;
		String filePath = "dictionary";
		
		if(args.length == 2 && (args[0]!=null && !args[0].isEmpty()) 
											&&(args[1]!=null && !args[1].isEmpty())) {
			 serverPort = Integer.parseInt(args[0]); 
			 filePath   = args[1];
		}else {
			System.err.println("Invalid command parameters");
			System.exit(1);
		}

		HashMap<String, ArrayList<String>> map = null;
		
		try {
			map = getMapFromDictionaryFile(filePath);
			System.out.println("Server Started");
		}catch(Exception exception) {
			System.err.println("Unable to Read data from file");
			System.exit(1);
		}
		
		//SingleThreadedServer server = new SingleThreadedServer(9000,map);
		
		ThreadPoolServer  threadPoolServer = new ThreadPoolServer (serverPort,map,filePath);
		
		//new Thread(server).start();

		new Thread(threadPoolServer).start();

		try {
			Thread.sleep(10 * 100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Stopping Server");
		//server.stop();
	}

	private static HashMap<String, ArrayList<String>> getMapFromDictionaryFile(String filePath) throws IOException {

		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(":", 2);
			if (parts.length >= 2) {
				String key = parts[0];
				String value = parts[1];

				ArrayList<String> listValue = new ArrayList<String>();

				StringTokenizer stringTokenizer = new StringTokenizer(value, ",");
				while (stringTokenizer.hasMoreElements()) {
					listValue.add(stringTokenizer.nextToken());
				}
				map.put(key, listValue);
			} else {
				System.out.println("ignoring line: " + line);
			}
		}
		reader.close();

		return map;
	}
}
