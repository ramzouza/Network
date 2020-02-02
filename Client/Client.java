package Client;

//Blocking Classes
import java.net.Socket;
import java.net.ServerSocket;

//Non Blocking Classes
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import Network.Builder.*;


import java.nio.ByteBuffer;
import java.io.PrintWriter;
//Misc Classes
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URI;


public class Client {


    private RequestBuilder req;
    private String address;

    Client(RequestBuilder newRequest, String newAddress) {
        this.req = newRequest;
        this.address = newAddress;
    }


    public void request (){
      try{ 
        InetAddress web = InetAddress.getByName(address);
        Socket sock = new Socket(web, 80);
        PrintWriter out = new PrintWriter(sock.getOutputStream());
        Scanner in = new Scanner(sock.getInputStream());
        out.write(req.toString());
        out.flush();

        while ( in .hasNextLine()) {
            System.out.println( in .nextLine());
        }

        out.close(); in .close();
        sock.close();
        } 
    catch (Exception e) {
        System.out.println(e);
    }
}


    public static void main(String[] args) {
        RequestBuilder post = new POSTRequestBuilder("GET ", "/status/418", "User-Agent: Hello", "");
        Client net = new Client(post, "www.httpbin.org");
        net.request();
    }

}