package Network;
import Parsers.*;

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



public class RequestBuilder {
private String method; 
private String URL;
final private String version = "HTTP/1.0"; 
private String header = "";
private String entityBody = "";

    RequestBuilder(String method, String URL, String header, String entityBody){
        this.method = method;
        this.URL = URL;
        this.header = header;
        this.entityBody = entityBody;
    }

    @Override
    public String toString() {
        return this.method + this.URL + " " + this.version + "\r\n" +  this.header + "\r\n"  + "\r\n" ;

    }



}