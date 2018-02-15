package co.dictionary.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class ThreadPoolServer implements Runnable{

	private int serverPort             = 8080;
	private ServerSocket serverSocket  = null;
	private boolean isStopped          = false;
	private Thread runningThread       = null;
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private String filePath;
 
	private HashMap<String, ArrayList<String>> map;

	public ThreadPoolServer(int port, HashMap<String, ArrayList<String>> map, String filePath) {
		this.serverPort = port;
		this.map        = map;
		this.filePath   = filePath;
	}
	
	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					break;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			this.threadPool.execute(new WorkerRunnable(clientSocket, map,filePath));
		}
		
		this.threadPool.shutdown();
		System.out.println("Server Stopped.");
	}


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

}
