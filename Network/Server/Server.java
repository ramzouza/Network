package Network.Server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Server {

	public static void main(String[] args) {
		get();
    }
    
    public static void get(){
        		
		try {
			ServerSocket server = new ServerSocket(8080);
			
			System.out.println("Listening for connection on port" + 8080);
			Socket clientSocket  = server.accept();
			System.out.println("Server accepted connection");
			
			InputStream inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();
			
			StringBuilder request = new StringBuilder();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}