
//Blocking Classes
import java.net.Socket;
import java.net.ServerSocket;

//Non Blocking Classes
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.io.PrintWriter;
//Misc Classes
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URI;

import Network.Client.*;

class sample {

    public static void main(String[] args) {
       
        try {
            InetAddress web = InetAddress.getByName("www.httpbin.org");
             Socket sock = new Socket(web,80);
             PrintWriter out = new PrintWriter(sock.getOutputStream());
             Scanner in = new Scanner(sock.getInputStream());        
             out.write(" GET  HTTP/1.0\r\nUser-Agent: Hello\r\n\r\n");
             out.flush();
     
             while(in.hasNextLine()) {
             System.out.println(in.nextLine());
             }
     
             out.close();
             in.close();
             sock.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

}
