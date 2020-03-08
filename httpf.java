import java.io.PrintWriter;
import java.util.Scanner;


import Network.Server.Server;

public class httpf{
     
    public static void main(String[] args) {
        Server server = new Server(8080, "localhost");
        server.initSocket();
    while(true)
    {	
		server.accept();
		System.out.println("client connected");
		String info = "";
		try {
            PrintWriter out = new PrintWriter(server.getSocket().getOutputStream());
			Scanner in = new Scanner(server.getSocket().getInputStream());
			server.evaluateFirstline(in.nextLine());
			server.getReq().buildRequest(in);
			System.out.println(server.getReq().toString()); 

        } catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
    }    
        
    }



}