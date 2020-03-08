package Network.Server;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import Network.Builder.GETRequestBuilder;
import Network.Builder.POSTRequestBuilder;
import Network.Builder.RequestBuilder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class Server {

	private int port = 8080;
	private String path; 
	private ServerSocket server= null;
	private static Socket socket;
	static InputStreamReader inputReader;
	static BufferedReader br;
	static PrintWriter PrintWriter;
	private String requestType;
	static RequestBuilder req;

	public Server(int newPort, String newPath) {
		this.port = newPort;
		this.path = newPath;
	}

	public void initSocket() {
		try {
			if (availableAndAllowed(port)) {
				server = new ServerSocket();
				String s = "127.0.0.1";
				SocketAddress binAddress = new InetSocketAddress(s, port);
				server.bind(binAddress);
			} else {
				System.exit(0);
			}

		} catch (IOException e) {
			System.out.println("Creation of the server socket has crashed unexpedectly");
		}
	}

	private boolean availableAndAllowed(int port) {
		if (port < 1024 || port > 65535) {
			System.out.println("--------------Port " + port + " is outside the allowed range");
			return false;
		}

		System.out.println("--------------Testing port " + port);
		Socket s = null;
		try {
			s = new Socket("localhost", port);

			// If the code makes it this far without an exception it means
			// something is using the port and has responded.
			System.out.println("--------------Port " + port + " is not available");
			return false;
		} catch (IOException e) {
			return true;
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					throw new RuntimeException("You should handle this error.", e);
				}
			}
		}
	}

	public void accept() {
		try {
			socket = server.accept();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		Server newServer = new Server(8080, "localhost");
		newServer.initSocket();
		newServer.accept();
		System.out.println("client connected");
		String info = "";
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			Scanner in = new Scanner(socket.getInputStream());

			// while ( in .hasNextLine()) {
			// info +=in.nextLine() +"\n";
			// System.out.println("-----------------");
			// System.out.println(info);
			// }
			evaluateFirstline(in.nextLine());
			req.buildRequest(in);
			System.out.println(req.toString()); 

			System.out.println("did I crash");
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}

	}

	public void clientRequest(String request, Scanner in) {

		if (requestType.equals("GET ")) {
			req = new GETRequestBuilder();
			req.setMethod("GET ");
		} else if (requestType.equals("POST ")) {
			req = new POSTRequestBuilder();
			req.setMethod("POST ");
		} else {
			System.out.println("The request Type is not accepted");
			System.exit(0);
		}

	}

	public static void evaluateFirstline(String content) {
		String[] values = content.split(" ");
		
		if (values[0].equals("GET")) {
			req = new GETRequestBuilder();
		}   
	   else if(values[0].equals("POST")){
	   req = new POSTRequestBuilder();
	   }
	   req.setURL(values[1]);
	   req.setVersion(values[2]);
	   req.setMethod(values[0] + " ");        
    }

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		Server.socket = socket;
	}

	public static RequestBuilder getReq() {
		return req;
	}



}
