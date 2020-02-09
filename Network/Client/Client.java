package Network.Client;

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
    private static Response res;

    public Client(RequestBuilder newRequest, String newAddress) {
        this.req = newRequest;
        this.address = newAddress;
    }

    public static void main(String[] args) {
        RequestBuilder post = new POSTRequestBuilder("GET ", "/status/  418", "User-Agent: Hello", "");
        Client net = new Client(post, "www.httpbin.org");
        net.request();
        System.out.println(res); 
    }

    public void run ()
    {       
        Client net = new Client(req, address);
        net.request();
        System.out.println(res); 
    }


    public void request (){
      try{ 
        InetAddress web = InetAddress.getByName(new URL(address).getHost());
        Socket sock = new Socket(web, 80);
        PrintWriter out = new PrintWriter(sock.getOutputStream());
        Scanner in = new Scanner(sock.getInputStream());
        out.write(req.toString());
        out.flush();

        buildResponse(in);
        
        out.close(); in .close();
        sock.close();
        } 
    catch (Exception e) {
        System.out.println(e);
    }
}

    public void buildResponse (Scanner in){

        evaluateFirstline(in.nextLine());
        String header = "";
        String entity = "";

        while ( in .hasNextLine()) {
        String temp = (String)in.nextLine();   
      
        if (temp.equals(""))
        {
                res.setHeader(header);
                while ( in .hasNextLine()) {
                    entity += (String)in.nextLine() + "\r\n";
                   }
                   res.setEntityBody(entity);
        }
        else{
            header += temp + "\r\n";
        }
           
    }
   
}

    public void evaluateFirstline (String content)
    {
        String [] values = content.split(" ");
        String phrase = "";
        for (int i = 2; i < values.length; i++) {
            phrase += values[i] + " ";
        }

        res = new Response(values[0], values[1], phrase, "", "");
    }

    public static Response getRes() {
        return res;
    }

    public static void setRes(Response res) {
        Client.res = res;
    }

    
}