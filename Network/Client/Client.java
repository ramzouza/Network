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

    public void request (){
      try{ 
          InetAddress web = null;
          int port = 0;
          try {
            URL url = new URL(address);
            web = InetAddress.getByName(url.getHost());
            port = url.getPort();

          } catch (Exception e) {
            System.out.println("URL provided is not reachable by the Network library, request cancelled");
            System.exit(0);
          }
     
          if (port == 0)
          {
              port = 80;
          }

        Socket sock = new Socket(web, port);
        PrintWriter out = new PrintWriter(sock.getOutputStream());
        out.write(req.toString());
        out.flush();
        

        Scanner in = new Scanner(sock.getInputStream());
        buildResponse(in);        
        in.close();
        out.close(); 
        redirect();
        } 
    catch (Exception e) {
        System.out.println(e);
    }
}

    public void buildResponse (Scanner in){

        if(in.hasNext() && in.hasNextLine())
        {
            evaluateFirstline(in.nextLine());
        }
        String header = "";
        String entity = "";

        while ( in .hasNextLine()) {
        String temp = (String)in.nextLine();   
      
        if (temp.equals(""))
        {
                res.setHeader(header);
                while ( in .hasNextLine()) {
                    return;
                   // entity += (String)in.nextLine() + "\r\n";
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
        if (values.length > 2)
        {
            phrase = values[2];
        }

        for (int i = 3; i < values.length; i++) {
            phrase += " " + values[i];
        }

        res = new Response(values[0], values[1], phrase, "", "");
    }

    public static Response getRes() {
        return res;
    }

    public static void setRes(Response res) {
        Client.res = res;
    }

    public void redirect() {
        if (res.getCode().matches("3\\d?\\d?")){
            String headers = res.getHeader();          
            String[] parts =  headers.split("\r\n");
             
            for (String string : parts) {
                if (string.contains("Location"))
                {
                    String [] redirectAddress = string.split(": ");
                    this.address = redirectAddress[1];
                    req.setURL(this.address);
                }
            }
           // System.out.println(this.res.verboseToString());
            this.request();
           
        }
    }
    
}